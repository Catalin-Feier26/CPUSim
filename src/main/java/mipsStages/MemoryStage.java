package mipsStages;

import lowComponents.DataMemory;
import lowComponents.ProgramCounter;
import pipelineRegisters.EXMEMRegister;
import pipelineRegisters.MEMWBRegister;

import java.util.HashMap;

/**
 * The MemoryStage class represents the Memory Access (MEM) stage of the MIPS pipeline.
 * This stage handles memory operations such as reading from and writing to memory,
 * as well as determining the next program counter (PC) based on branch and jump conditions.
 */
public class MemoryStage {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE="\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";

    private EXMEMRegister exmemRegister;
    private MEMWBRegister memwbRegister;
    private DataMemory dataMemory;
    private ProgramCounter programCounter;


    public String instruction;
    public int instructionIndex;

    private boolean pcSrc;

    private boolean branch;
    private boolean jump;
    private boolean zero;
    private int aluRes;
    private int writeData;

    private int readData;
    /**
     * Constructs a MemoryStage object using the specified EX/MEM pipeline register.
     * Initializes a new MEM/WB pipeline register and DataMemory for operations.
     *
     * @param exmemRegister the EXMEMRegister object containing data from the previous stage
     */
    public MemoryStage(EXMEMRegister exmemRegister) {
        this.exmemRegister = exmemRegister;
        this.memwbRegister = new MEMWBRegister();
        this.dataMemory = new DataMemory();
        execute();
    }
    /**
     * Constructs a MemoryStage object using the specified dependencies.
     *
     * @param exmemRegister the EXMEMRegister object containing data from the previous stage
     * @param memwbRegister the MEMWBRegister object for passing signals to the next stage
     * @param dataMemory    the DataMemory object for handling memory operations
     * @param programCounter the ProgramCounter object to update the PC if a branch or jump occurs
     */
    public MemoryStage(EXMEMRegister exmemRegister, MEMWBRegister memwbRegister, DataMemory dataMemory, ProgramCounter programCounter){
        this.exmemRegister=exmemRegister;
        this.memwbRegister=memwbRegister;
        this.dataMemory=dataMemory;
        this.programCounter=programCounter;
    }
    /**
     * Fetches data from the EX/MEM register for use in memory operations and control signal evaluation.
     */
    private void getData(){
        branch=exmemRegister.getBranch();
        jump=exmemRegister.getJump();
        zero=exmemRegister.getZero();
        aluRes=exmemRegister.getAluResult();
        writeData=exmemRegister.getWriteData();


        instruction= exmemRegister.instruction;
        instructionIndex=exmemRegister.instructionIndex;
    }
    /**
     * Executes the Memory stage, performing memory read/write operations,
     * determining the next PC based on branch/jump conditions, and passing signals to the next stage.
     *
     * @return a formatted string summarizing the Memory stage's outputs
     */
    public String execute() {
        getData();
        if (exmemRegister.getMemRead()) {
            readData = dataMemory.readMemory(aluRes);
        }
        if (exmemRegister.getMemWrite()) {
            dataMemory.writeMemory(aluRes, writeData);
        }
        pcSrc = (branch && zero) || jump;
        if(pcSrc){
            programCounter.setPC(exmemRegister.getTargetAddress());
        }
        passSignals();
        return pretty();
        //prettyPrint();
    }
    /**
     * Passes signals and data from the Memory stage to the MEM/WB pipeline register for the next stage.
     */
    private void passSignals(){
        memwbRegister.setAluResult(aluRes);
        memwbRegister.setRegWrite(exmemRegister.getRegWrite());
        memwbRegister.setMemToReg(exmemRegister.getMemToReg());
        memwbRegister.setLoWrite(exmemRegister.getLoWrite());
        memwbRegister.setHiWrite(exmemRegister.getHiWrite());
        memwbRegister.setWriteRegister(exmemRegister.getWriteRegister());

        memwbRegister.setHi(exmemRegister.getHi());
        memwbRegister.setLo(exmemRegister.getLo());
        memwbRegister.setReadData(readData);

        memwbRegister.instructionIndex=instructionIndex;
        memwbRegister.instruction=instruction;
    }
    /**
     * Retrieves the MEM/WB pipeline register.
     *
     * @return the MEMWBRegister object
     */
    public MEMWBRegister getMemwbRegister() {
        return memwbRegister;
    }
    /**
     * Retrieves the PC source signal, indicating whether the PC should be updated.
     *
     * @return the PC source signal as a boolean
     */
    public boolean getPcSrc() {
        return pcSrc;
    }
    /**
     * Resets the state of the Memory stage, clearing instruction data and signals.
     */
    public void reset() {
        instruction = "";
        pcSrc = false;
        branch = false;
        jump = false;
        zero = false;
        aluRes = 0;
        writeData = 0;
        readData = 0;

    }
    /**
     * Retrieves the current instruction processed in the Memory stage.
     *
     * @return the current instruction as a string
     */
    public String getInstruction(){
        return instruction;
    }
    /**
     * Retrieves the entire memory state as a map of addresses to data values.
     *
     * @return a HashMap representing the memory content
     */
    public HashMap<Integer,Integer> getDataMemory(){
        return dataMemory.getMemory();
    }
    /**
     * Provides a formatted representation of the Memory stage's output,
     * including the instruction, control signals, and memory operation results,
     * with ANSI color formatting for the console.
     *
     * @return a formatted string summarizing the Memory stage's outputs
     */
    public String pretty() {
        return ANSI_RED + "MEMORY STAGE\n" + ANSI_RESET +
                ANSI_YELLOW + "Instruction: " + ANSI_RESET + ANSI_BLUE + instruction + ANSI_RESET + "\n" +
                ANSI_YELLOW + "Branch: " + ANSI_RESET + ANSI_BLUE + branch + ANSI_RESET + "\n" +
                ANSI_YELLOW + "Jump: " + ANSI_RESET + ANSI_BLUE + jump + ANSI_RESET + "\n" +
                ANSI_YELLOW + "Zero Flag: " + ANSI_RESET + ANSI_BLUE + zero + ANSI_RESET + "\n" +
                ANSI_YELLOW + "ALU Result: " + ANSI_RESET + ANSI_BLUE + aluRes + ANSI_RESET + "\n" +
                ANSI_YELLOW + "Write Data: " + ANSI_RESET + ANSI_BLUE + writeData + ANSI_RESET + "\n" +
                ANSI_YELLOW + "Read Data: " + ANSI_RESET + ANSI_BLUE + readData + ANSI_RESET + "\n" +
                ANSI_YELLOW + "PC Source: " + ANSI_RESET + ANSI_BLUE + pcSrc + ANSI_RESET + "\n" +
                "---------------------------------------------------\n";
    }

}