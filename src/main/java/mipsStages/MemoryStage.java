package mipsStages;

import lowComponents.DataMemory;
import lowComponents.ProgramCounter;
import pipelineRegisters.EXMEMRegister;
import pipelineRegisters.MEMWBRegister;

public class MemoryStage {
    private EXMEMRegister exmemRegister;
    private MEMWBRegister memwbRegister;
    private DataMemory dataMemory;
    private ProgramCounter programCounter;

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

    }

    public void execute() {
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
        prettyPrint();
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
    }

    public MEMWBRegister getMemwbRegister() {
        return memwbRegister;
    }

    public boolean getPcSrc() {
        return pcSrc;
    }
    public void prettyPrint() {
        System.out.println("Memory Stage:");
        System.out.println("Branch: " + branch);
        System.out.println("Jump: " + jump);
        System.out.println("Zero: " + zero);
        System.out.println("ALU Result: " + aluRes);
        System.out.println("Write Data: " + writeData);
        System.out.println("Read Data: " + readData);
        System.out.println("PC Source: " + pcSrc);
    }
}