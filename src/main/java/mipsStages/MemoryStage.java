package mipsStages;

import lowComponents.DataMemory;
import lowComponents.ProgramCounter;
import pipelineRegisters.EXMEMRegister;
import pipelineRegisters.MEMWBRegister;

import java.util.HashMap;

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

    private boolean pcSrc;

    private boolean branch;
    private boolean jump;
    private boolean zero;
    private int aluRes;
    private int writeData;

    private int readData;

    public MemoryStage(EXMEMRegister exmemRegister) {
        this.exmemRegister = exmemRegister;
        this.memwbRegister = new MEMWBRegister();
        this.dataMemory = new DataMemory();
        execute();
    }
    public MemoryStage(EXMEMRegister exmemRegister, MEMWBRegister memwbRegister, DataMemory dataMemory, ProgramCounter programCounter){
        this.exmemRegister=exmemRegister;
        this.memwbRegister=memwbRegister;
        this.dataMemory=dataMemory;
        this.programCounter=programCounter;
    }
    private void getData(){
        branch=exmemRegister.getBranch();
        jump=exmemRegister.getJump();
        zero=exmemRegister.getZero();
        aluRes=exmemRegister.getAluResult();
        writeData=exmemRegister.getWriteData();
        instruction= exmemRegister.instruction;
    }

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
        memwbRegister.instruction=instruction;
    }

    public MEMWBRegister getMemwbRegister() {
        return memwbRegister;
    }

    public boolean getPcSrc() {
        return pcSrc;
    }
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
    public HashMap<Integer,Integer> getDataMemory(){
        return dataMemory.getMemory();
    }

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