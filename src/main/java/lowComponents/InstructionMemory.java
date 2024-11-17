package lowComponents;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
/**
 * This class represents the Instruction Memory of the MIPS architecture
 * It contains the instructions of the program
 */
public class InstructionMemory {
    private List<String> instructions;

    /**
     * Constructor for the InstructionMemory class that loads instructions from a file.
     * @param fileName The path to the file containing the instructions.
     */
    public InstructionMemory(String fileName) {
        instructions = new ArrayList<>();
        loadInstructions(fileName);  // Automatically load instructions from the file
    }
    /**
     * Loads instructions from a file into the Instruction Memory.
     * Each line in the file represents a single instruction.
     * @param fileName The path to the file containing the instructions.
     * @return List<String> The list of loaded instructions.
     */
    public List<String> loadInstructions(String fileName) {
        try {
            // Read all lines from the file and store them in the instructions list
            instructions = Files.readAllLines(Paths.get(fileName));
        } catch (IOException e) {
            System.err.println("Error loading instructions from file: " + e.getMessage());
            e.printStackTrace();
        }
        return instructions;
    }

    /**
     * Constructor for the InstructionMemory class.
     * Initializes the instructions to an empty array.
     */
    public InstructionMemory() {
        instructions = new ArrayList<>();
    }
    /**
     * Resets the content of the Instruction Memory to an empty array.
     */
    public void reset(){
        instructions.clear();
    }
    /**
     * Sets the instructions of the program.
     * @param instructions The instructions to be set.
     */
    public void setInstructions(List<String> instructions) {
        this.instructions = new ArrayList<>(instructions);
    }
    /**
     * Gets the instruction at a given address.
     * @param address The address of the instruction.
     * @return String The instruction at the given address.
     * @throws IllegalArgumentException if the address is invalid.
     */
    public String getInstruction(int address) throws IllegalArgumentException {
        if(address < 0 || address >= instructions.size()){
            address=address%instructions.size();
        }
        return instructions.get(address);
    }
    /**
     * Gets the number of instructions in the program.
     * @return int The number of instructions.
     */
    public int getInstructionCount() {
        return instructions.size();
    }

    /**
     * Gets the instructions of the program.
     * @return List<String> The instructions of the program.
     */
    public List<String> getInstructions(){
        return Collections.unmodifiableList(instructions);
    }
    /**
     * Adds an instruction to the program.
     * @param instruction The instruction to be added.
     * @return boolean True if the instruction was added successfully.
     */
    public boolean addInstruction(String instruction) {
        return instructions.add(instruction);
    }

    /**
     * Overrides the toString method to return the string representation of the Instruction Memory.
     * @return String The string representation of the Instruction Memory.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String instruction : instructions) {
            sb.append(instruction).append("\n");
        }
        return sb.toString();
    }

}
