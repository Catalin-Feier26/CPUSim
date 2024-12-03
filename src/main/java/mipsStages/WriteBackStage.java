package mipsStages;

import lowComponents.RegisterFile;
import pipelineRegisters.MEMWBRegister;
import model.Register;

/**
 * The WriteBackStage class represents the Write-Back (WB) stage of the MIPS pipeline.
 * This stage writes results back to the register file or special registers (HI and LO) based on control signals.
 */
public class WriteBackStage{
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE="\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";

    private MEMWBRegister memwbRegister;
    private RegisterFile registerFile;

    public String instruction="";
    public int instructionIndex;

    private boolean regWrite;
    private boolean memToReg;
    private boolean loWrite, hiWrite;
    private Register writeRegister;

    private int aluResult;
    private int hi, lo;
    private int readData;
    private int writeData;
    /**
     * Constructs a WriteBackStage object using the specified MEM/WB pipeline register and RegisterFile.
     *
     * @param memwbRegister the MEMWBRegister object containing data from the previous stage
     * @param registerFile  the RegisterFile object where data is written back
     */
    public WriteBackStage(MEMWBRegister memwbRegister, RegisterFile registerFile) {
        this.memwbRegister = memwbRegister;
        this.registerFile = registerFile;
        regWrite=false;
        memToReg=false;
        loWrite=false;
        hiWrite=false;
        writeRegister=Register.R0;
        aluResult=0;
        hi=0;
        lo=0;
        readData=0;
        writeData=0;
        instructionIndex=-1;
    }
    /**
     * Resets the state of the WriteBackStage, clearing all control signals, register values, and instruction data.
     */
    public void reset() {
        instruction = "";
        regWrite = false;
        memToReg = false;
        loWrite = false;
        hiWrite = false;

        // Reset registers and data
        writeRegister = Register.R0;
        aluResult = 0;
        hi = 0;
        lo = 0;
        readData = 0;
        writeData = 0;

        instructionIndex=-1;
    }
    /**
     * Fetches control signals and data from the MEM/WB pipeline register.
     * This method prepares the stage for execution by loading necessary data.
     */
    private void fetchFromRegister(){
        this.regWrite=memwbRegister.getRegWrite();
        this.memToReg=memwbRegister.getMemToReg();
        this.loWrite=memwbRegister.getLoWrite();
        this.hiWrite=memwbRegister.getHiWrite();
        writeRegister=memwbRegister.getWriteRegister();
        aluResult=memwbRegister.getAluResult();
        hi=memwbRegister.getHi();
        lo=memwbRegister.getLo();
        readData=memwbRegister.getReadData();
        instruction= memwbRegister.instruction;
        instructionIndex=memwbRegister.instructionIndex;
    }
    /**
     * Executes the Write-Back stage, performing the following:
     * - Writes data to the specified register in the register file if RegWrite is active.
     * - Writes to the HI or LO registers if HiWrite or LoWrite signals are active.
     *
     * @return a formatted string summarizing the Write-Back stage's outputs
     */
    public String execute() {
        fetchFromRegister();
        int writeData = memToReg ? readData : aluResult;
        if(regWrite){
            registerFile.setRegister(writeRegister,writeData);
        }
        if(loWrite){
            registerFile.setLo(lo);
        }
        if(hiWrite){
            registerFile.setHi(hi);
        }
        //prettyPrint();
        return pretty();
    }
    /**
     * Retrieves the instruction currently processed in the Write-Back stage.
     *
     * @return the current instruction as a string
     */
    public String getInstruction(){
        return  instruction;
    }
    /**
     * Prints a detailed summary of the Write-Back stage's data and control signals to the console.
     * This method is useful for debugging purposes.
     */
    public void prettyPrint() {
        System.out.println("WRITEBACK STAGE");
        System.out.println("Memory Stage:");
        System.out.println("ALU Result: " + aluResult);
        System.out.println("Write Data: " + writeData);
        System.out.println("Read Data: " + readData);
        System.out.println("HI register: " + hi);
        System.out.println("LO register: " + lo);
        System.out.println("Write in HI: " + hiWrite);
        System.out.println("Write in LO: " + loWrite);
        System.out.println("AluRes or READData ?" + memToReg);
        System.out.println("write in RF: " + regWrite);
        System.out.println("---------------------------------------------------");

    }
    /**
     * Provides a formatted representation of the Write-Back stage's data and control signals.
     * Includes information about the instruction, ALU result, memory data, and control signals.
     * Uses ANSI color formatting for better console readability.
     *
     * @return a formatted string summarizing the Write-Back stage's outputs
     */
    public String pretty() {
        return ANSI_RED + "WRITEBACK STAGE\n" + ANSI_RESET +
                ANSI_YELLOW + "Instruction: " + ANSI_RESET + ANSI_BLUE + instruction + ANSI_RESET + "\n" +
                ANSI_YELLOW + "ALU Result: " + ANSI_RESET + ANSI_BLUE + aluResult + ANSI_RESET + "\n" +
                ANSI_YELLOW + "Write Data: " + ANSI_RESET + ANSI_BLUE + writeData + ANSI_RESET + "\n" +
                ANSI_YELLOW + "Read Data: " + ANSI_RESET + ANSI_BLUE + readData + ANSI_RESET + "\n" +
                ANSI_YELLOW + "Write Register: " + ANSI_RESET + ANSI_BLUE + writeRegister + ANSI_RESET + "\n" +
                ANSI_YELLOW + "HI Register: " + ANSI_RESET + ANSI_BLUE + hi + ANSI_RESET + "\n" +
                ANSI_YELLOW + "LO Register: " + ANSI_RESET + ANSI_BLUE + lo + ANSI_RESET + "\n" +
                ANSI_YELLOW + "Write in HI: " + ANSI_RESET + ANSI_BLUE + hiWrite + ANSI_RESET + "\n" +
                ANSI_YELLOW + "Write in LO: " + ANSI_RESET + ANSI_BLUE + loWrite + ANSI_RESET + "\n" +
                ANSI_YELLOW + "Memory to Register (MemToReg): " + ANSI_RESET + ANSI_BLUE + memToReg + ANSI_RESET + "\n" +
                ANSI_YELLOW + "Write to Register File: " + ANSI_RESET + ANSI_BLUE + regWrite + ANSI_RESET + "\n" +
                "---------------------------------------------------\n";
    }

}