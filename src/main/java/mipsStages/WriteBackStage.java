package mipsStages;

import lowComponents.RegisterFile;
import pipelineRegisters.MEMWBRegister;
import model.Register;

public class WriteBackStage{
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE="\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";

    private MEMWBRegister memwbRegister;
    private RegisterFile registerFile;
    public String instruction="";
    private boolean regWrite;
    private boolean memToReg;
    private boolean loWrite, hiWrite;
    private Register writeRegister;

    private int aluResult;
    private int hi, lo;
    private int readData;
    private int writeData;

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
    }
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
    }
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
    }

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