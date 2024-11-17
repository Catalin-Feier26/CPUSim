package mipsStages;

import lowComponents.RegisterFile;
import pipelineRegisters.MEMWBRegister;
import model.Register;

public class WriteBackStage {
    private MEMWBRegister memwbRegister;
    private RegisterFile registerFile;

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
    }

    public void execute() {
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
        prettyPrint();
    }
    public void prettyPrint() {
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
    }
}