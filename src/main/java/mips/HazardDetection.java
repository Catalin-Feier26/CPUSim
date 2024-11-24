package mips;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import model.*;

public class HazardDetection {

    private List<String> instructionList;
    private List<String> updatedInstructionList;
    private boolean nopsAdded=false;
    public HazardDetection(List<String> instructionList) {
        this.instructionList = instructionList;
        this.updatedInstructionList = new ArrayList<>();
    }
    public HazardDetection(String fileName){
        this.instructionList=loadInstructions(fileName);
        this.updatedInstructionList=new ArrayList<>();
    }
    public List<String> loadInstructions(String fileName) {
        try {
            instructionList = Files.readAllLines(Paths.get(fileName));
        } catch (IOException e) {
            System.err.println("Error loading instructions from file: " + e.getMessage());
            e.printStackTrace();
        }
        return instructionList;
    }
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
    private List<String> addRestInstructions(int i){
        for(; i<instructionList.size(); i++){
            updatedInstructionList.add(instructionList.get(i));
        }
        return new ArrayList<>(updatedInstructionList);
    }
    private boolean destinationMatchesSources(String[] parts, String match){
        switch (parts[0]){
            // -
            case "NOP", "MFHI", "MFLO", "J": return false;
            // 2 3
            case "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "SLLV", "SRLV", "SLT":
                if (match.equals(parts[2]) || match.equals(parts[3]))
                    return  true;
                break;
            //  1 2
            case "MULT", "DIV", "BEQ", "BNE", "BGEZ", "BLTZ":
                if(match.equals(parts[1])||match.equals(parts[2]))
                    return true;
                break;
            // 2
            case "MOV", "SLL", "SRL", "SRA", "ADDI", "SUBI","ANDI","ORI","XORI","SLTI":
                if(match.equals(parts[2]))
                    return true;
                break;
            // 1
            case "JR", "MTHI", "MTLO":
                if(match.equals(parts[1]))
                    return true;
                break;
            case "JAL":
                if(match.equals("R31"))
                    return true;
                break;
        }
        return false;
    }
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
    private String decodeRType(String[] parts){
        switch (parts[0]){
            case "NOP", "MULT", "DIV", "MTHI", "MTLO", "JR": return "R0";
            case "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR":
            case "SLT","SLLV","SRLV", "SLL", "SRL","SRA", "MOV":
                return parts[1];
        }
        return "R0";
    }
    private String decodeJType(String[] parts){
        switch (parts[0]){
            case "JR": return "R31";
        }
        return "R0";
    }
    private String decodeIType(String[] parts){
        switch (parts[0]){
            case "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "SLTI": return parts[1];
        }
        return "R0";
    }
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

    public List<String> getUpdatedInstructionList() {
        return updatedInstructionList;
    }
}
