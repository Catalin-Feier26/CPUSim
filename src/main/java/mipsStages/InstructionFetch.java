package mipsStages;

import lowComponents.ProgramCounter;
import lowComponents.InstructionMemory;
import pipelineRegisters.IFIDRegister;
import java.io.*;

/**
 * The InstructionFetch class represents the Instruction Fetch (IF) stage of the MIPS pipeline.
 * It retrieves instructions from memory and updates the program counter (PC).
 * The fetched instruction is stored in the IF/ID pipeline register for subsequent stages.
 */
public class InstructionFetch {
    private ProgramCounter pc;
    private InstructionMemory instructionMemory;
    private IFIDRegister ifidRegister;

    private String instruction;
    public int instructionIndex;

    // Made for Testing
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE="\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";

    /**
     * Constructs an InstructionFetch object, initializing its dependencies and state.
     *
     * @param pc the ProgramCounter object for managing the PC
     * @param instructionMemory the InstructionMemory object containing the instructions
     * @param ifidRegister the IFIDRegister object for storing fetched instructions and PC value
     */
    public InstructionFetch(ProgramCounter pc, InstructionMemory instructionMemory, IFIDRegister ifidRegister){
        this.pc = pc;
        this.instructionMemory=instructionMemory;
        this.ifidRegister=ifidRegister;
        this.instructionIndex=-1;
        this.instruction= "";
    }
    /**
     * Fetches the next instruction from the instruction memory based on the current value of the PC.
     * If the PC exceeds the available instruction count, an empty instruction is fetched.
     *
     * @return the fetched instruction as a string
     */
    public String fetchInstruction(){
        if(pc.getPC() >instructionMemory.getInstructionCount())
        {
            this.instruction="";
            instructionIndex=-1;
        }
        else
        {
            this.instruction=instructionMemory.getInstruction(pc.getPC());
            //instructionIndex=pc.getPC();
        }
        pc.incrementPC();
        return instruction;
    }
    /**
     * Sets up the IFIDRegister with the fetched instruction and updated PC value.
     */
    public void setUpIFIDRegister(){
        ifidRegister.setInstruction(instruction);
        ifidRegister.setPC(pc.getPC());
        ifidRegister.instructionIndex=instructionIndex;
    }
    /**
     * Executes the Instruction Fetch stage, retrieving the next instruction, updating the PC,
     * and storing the instruction in the IFID pipeline register.
     *
     * @return a formatted string summarizing the Instruction Fetch stage's output
     */
    public String execute(){
        instructionIndex=pc.getPC();
        fetchInstruction();
        setUpIFIDRegister();
        return pretty();
    }
    /**
     * Provides a formatted representation of the Instruction Fetch stage's output,
     * including the current instruction and PC value, with ANSI color formatting for the console.
     *
     * @return a formatted string summarizing the Instruction Fetch stage's outputs
     */
    public String pretty() {
        return ANSI_RED + "INSTRUCTION FETCH PHASE\n" + ANSI_RESET +
                ANSI_BLUE + "Instruction: " + ANSI_RESET + ANSI_YELLOW + instruction + ANSI_RESET + "\n" +
                ANSI_BLUE + "PC: " + ANSI_RESET + ANSI_YELLOW + pc.getPC() + ANSI_RESET + "\n" +
                "---------------------------------------------------\n";
    }
    /**
     * Resets the state of the Instruction Fetch stage, clearing the current instruction,
     * resetting the PC to 0, and resetting the IFID pipeline register.
     */
    public void reset() {
        instruction = "";
        pc.setPC(0);
        ifidRegister.reset();
        instructionIndex=-1;
    }
    /**
     * Retrieves the current IFID pipeline register.
     *
     * @return the IFIDRegister object
     */
    public IFIDRegister getIfidRegister(){
        return ifidRegister;
    }
    /**
     * Sets the IFID pipeline register.
     *
     * @param ifidRegister the IFIDRegister object to set
     */
    public void setIfidRegister(IFIDRegister ifidRegister){
        this.ifidRegister=ifidRegister;
    }
    /**
     * Retrieves the current program counter (PC).
     *
     * @return the ProgramCounter object
     */
    public ProgramCounter getPc(){
        return  pc;
    }
    /**
     * Sets the program counter (PC).
     *
     * @param pc the ProgramCounter object to set
     */
    public void setPc(ProgramCounter pc){
        this.pc = pc;
    }
    /**
     * Retrieves the current instruction fetched in the Instruction Fetch stage.
     *
     * @return the current instruction as a string
     */
    public String getInstruction(){
        return  instruction;
    }
}
