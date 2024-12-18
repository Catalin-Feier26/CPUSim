package mips;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import model.*;
/**
 * The HazardDetection class is responsible for identifying and resolving hazards in a list of MIPS instructions.
 * It provides functionality to detect RAW hazards, insert NOP instructions to resolve hazards, and write the updated
 * instruction list to a file.
 */
public class HazardDetection {

    private List<String> instructionList;
    private List<String> updatedInstructionList;
    private boolean nopsAdded=false;
    /**
     * Constructs a HazardDetection instance with the provided list of instructions.
     *
     * @param instructionList the list of instructions to analyze and resolve hazards for.
     */
    public HazardDetection(List<String> instructionList) {
        this.instructionList = instructionList;
        this.updatedInstructionList = new ArrayList<>();
    }
    /**
     * Constructs a HazardDetection instance by loading instructions from a file.
     *
     * @param fileName the name of the file containing the instructions.
     */
    public HazardDetection(String fileName){
        this.instructionList=loadInstructions(fileName);
        this.updatedInstructionList=new ArrayList<>();
    }
    /**
     * Loads instructions from a file.
     *
     * @param fileName the name of the file to load instructions from.
     * @return the list of instructions read from the file.
     */
    public List<String> loadInstructions(String fileName) {
        try {
            instructionList = Files.readAllLines(Paths.get(fileName));
        } catch (IOException e) {
            System.err.println("Error loading instructions from file: " + e.getMessage());
            e.printStackTrace();
        }
        return instructionList;
    }
    /**
     * Writes detected hazards to a file.
     *
     * @param filename the name of the file where detected hazards will be written.
     */
    public void writeDetectedHazardsToFile(String filename) {
        System.out.println("Starting to write hazards into this file: " + filename);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < instructionList.size(); i++) {
                String instruction = instructionList.get(i).toUpperCase();
                String match = getDestination(instruction);

                if (writeInRegister(match.split(" ")[0])) {
                    int nr = Math.min(3, instructionList.size() - i - 1); // Look ahead max 3 or remaining instructions

                    for (int lookAhead = 1; lookAhead <= nr; lookAhead++) {
                        if (i + lookAhead < instructionList.size()) { // Check bounds here
                            String nextInstruction = instructionList.get(i + lookAhead).toUpperCase();

                            if (destinationMatchesSources(nextInstruction.split(" "), match)) {
                                writer.write("RAW Hazard between: " + instruction + " and " + nextInstruction + " because of the " + match.split(" ")[0] + " register");
                                System.out.println("RAW Hazard between: " + instruction + " and " + nextInstruction + " because of the " + match.split(" ")[0] + " register");
                                writer.newLine();
                                break; // Break out of the lookAhead loop
                            }
                        }
                    }
                }
            }
            System.out.println("Detected hazards successfully written to " + filename);
        } catch (IOException e) {
            System.err.println("Error writing detected hazards to file: " + e.getMessage());
        }
    }
    /**
     * Detects and resolves hazards by inserting NOP instructions where necessary.
     * Updates the instruction list to include resolved instructions.
     */
    public void detectAndSolveHazards() {
        List<String> currentInstructionList = new ArrayList<>(instructionList);
        int iteration = 0; // To track the number of iterations
        do {
            System.out.println("Iteration: " + iteration);
            nopsAdded = false;
            updatedInstructionList.clear(); // Clear the updated list for each iteration

            for (int i = 0; i < currentInstructionList.size(); i++) {
                String instruction = currentInstructionList.get(i).toUpperCase();
                //System.out.println("Processing instruction: " + instruction + " at index " + i);
                updatedInstructionList.add(instruction);
                String match = getDestination(instruction);
                //System.out.println("Destination register for " + instruction + ": " + match);

                if (writeInRegister(match.split(" ")[0])) {
                    int nr = Math.min(3, currentInstructionList.size() - i - 1); // Look ahead max 3 or remaining instructions
                    //System.out.println("Looking ahead " + nr + " instructions.");

                    for (int lookAhead = 1; lookAhead <= nr; lookAhead++) {
                        if (i + lookAhead < currentInstructionList.size()) { // Check bounds here
                            String nextInstruction = currentInstructionList.get(i + lookAhead).toUpperCase();
                            //System.out.println("Looking at next instruction: " + nextInstruction + " at index " + (i + lookAhead));

                            if (destinationMatchesSources(nextInstruction.split(" "), match)) {
                                //System.out.println("Hazard detected between: " + instruction + " and " + nextInstruction);
                                updatedInstructionList.add("NOP");
                                nopsAdded = true;
                                //System.out.println("NOP added. Continuing to process the next instructions.");
                                break; // Break out of the lookAhead loop
                            }
                        }
                    }
                }
            }
            iteration++; // Increment the iteration counter
            System.out.println("Updated instruction list: " + updatedInstructionList);
            currentInstructionList = new ArrayList<>(updatedInstructionList); // Update currentInstructionList for the next iteration
        } while (nopsAdded);
    }
    /**
     * Writes the updated instruction list after hazard resolution to a file.
     *
     * @param filename the name of the file where updated instructions will be written.
     */
    public void writeUpdatedInstructionsToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String instruction : updatedInstructionList) {
                writer.write(instruction);
                writer.newLine();
            }
            System.out.println("Updated instructions successfully written to " + filename);
        } catch (IOException e) {
            System.err.println("Error writing updated instructions to file: " + e.getMessage());
        }
    }
    /**
     * Determines if the destination register of an instruction matches any source registers
     * of a subsequent instruction.
     *
     * @param parts the components of the subsequent instruction.
     * @param match the destination register of the current instruction.
     * @return true if a match is found, false otherwise.
     */
    private boolean destinationMatchesSources(String[] parts, String match){
        switch (parts[0]){
            case "NOP", "MFHI", "MFLO", "J": return false;
            case "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "SLLV", "SRLV", "SLT":
                if (match.equals(parts[2]) || match.equals(parts[3]))
                    return  true;
                break;
            case "MULT", "DIV", "BEQ", "BNE", "BGEZ", "BLTZ":
                if(match.equals(parts[1])||match.equals(parts[2]))
                    return true;
                break;
            case "MOV", "SLL", "SRL", "SRA", "ADDI", "SUBI","ANDI","ORI","XORI","SLTI":
                if(match.equals(parts[2]))
                    return true;
                break;
            case "JR", "MTHI", "MTLO":
                if(match.equals(parts[1]))
                    return true;
                break;
            case "JAL":
                if(match.equals("R31"))
                    return true;
            case "LW":
                if (parts.length >= 3 && parts[2].contains("(")) {
                    String[] offsetAndBase = parts[2].split("[()]");
                    if (offsetAndBase.length == 2 && match.equals(offsetAndBase[1])) {
                        return true; // Match with the base register
                    }
                } else if (parts.length >= 2 && match.equals(parts[1])) {
                    return true; // Match with the destination register (rt)
                }
                break;
            case "SW":
                if (parts.length >= 3 && parts[2].contains("(")) {
                    String[] offsetAndBase = parts[2].split("[()]");
                    if (offsetAndBase.length == 2 && (match.equals(offsetAndBase[1]) || match.equals(parts[1]))) {
                        return true; // Match with base register or source register
                    }
                } else if (parts.length >= 2 && match.equals(parts[1])) {
                    return true; // Match with the source register
                }
                break;


        }
        return false;
    }
    /**
     * Extracts the destination register from a given instruction.
     *
     * @param instruction the instruction to decode.
     * @return the destination register or "R0" if none exists.
     */
    private String getDestination(String instruction){
        String [] parts = instruction.toUpperCase().split(" ");
        try{
            Enum.valueOf(RInstructions.class, parts[0]);
            return decodeRType(parts);
        }catch(IllegalArgumentException e1){
            try{
                Enum.valueOf(IInstructions.class, parts[0]);
                return decodeIType(parts);
            }catch(IllegalArgumentException e2){
                try{
                    Enum.valueOf(JInstructionctions.class, parts[0]);
                    return decodeJType(parts);
                }catch (IllegalArgumentException e3){
                    System.out.println("Invalid decoding for instruction in Hazard Solving");
                }
            }
        }
        return "R0";
    }
    /**
     * Decodes the destination register for R-type instructions.
     *
     * @param parts the components of the instruction.
     * @return the destination register or "R0" if none exists.
     */
    private String decodeRType(String[] parts){
        switch (parts[0]){
            case "NOP", "MULT", "DIV", "MTHI", "MTLO", "JR": return "R0";
            case "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR":
            case "SLT","SLLV","SRLV", "SLL", "SRL","SRA", "MOV":
                return parts[1];
        }
        return "R0";
    }
    /**
     * Decodes the destination register for J-type instructions.
     *
     * @param parts the components of the instruction.
     * @return the destination register or "R0" if none exists.
     */
    private String decodeJType(String[] parts){
        switch (parts[0]){
            case "JR": return "R31";
        }
        return "R0";
    }
    /**
     * Decodes the destination register for I-type instructions.
     *
     * @param parts the components of the instruction.
     * @return the destination register or "R0" if none exists.
     */
    private String decodeIType(String[] parts){
        return switch (parts[0]) {
            case "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "SLTI" -> parts[1];
            default -> "R0";
        };
    }
    /**
     * Determines if a register is writable.
     *
     * @param tag the register to check.
     * @return true if the register is writable, false otherwise.
     */
    private boolean writeInRegister(String tag) {
        if (tag == null) {
            return false;
        }
        switch (tag.toUpperCase()) {
            case "NOP":
            case "MULT":
            case "DIV":
            case "JR":
            case "MTHI":
            case "MTLO":
            case "SW":
            case "BEQ":
            case "BNE":
            case "BGEZ":
            case "BLTZ":
            case "J":
                return false;
            default:
                return true;
        }
    }
    /**
     * Retrieves the updated list of instructions after hazard resolution.
     *
     * @return the updated instruction list.
     */
    public List<String> getUpdatedInstructionList() {
        return updatedInstructionList;
    }
}
