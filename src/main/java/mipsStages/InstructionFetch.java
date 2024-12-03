package mipsStages;

import lowComponents.ProgramCounter;
import lowComponents.InstructionMemory;
import pipelineRegisters.IFIDRegister;
import java.io.*;

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


    public InstructionFetch(ProgramCounter pc, InstructionMemory instructionMemory, IFIDRegister ifidRegister){
        this.pc = pc;
        this.instructionMemory=instructionMemory;
        this.ifidRegister=ifidRegister;
        this.instructionIndex=-1;
        this.instruction= "";
    }
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
    public void setUpIFIDRegister(){
        ifidRegister.setInstruction(instruction);
        ifidRegister.setPC(pc.getPC());
        ifidRegister.instructionIndex=instructionIndex;
    }
    public String execute(){
        instructionIndex=pc.getPC();
        fetchInstruction();
        setUpIFIDRegister();
        return pretty();
    }

    public String pretty() {
        return ANSI_RED + "INSTRUCTION FETCH PHASE\n" + ANSI_RESET +
                ANSI_BLUE + "Instruction: " + ANSI_RESET + ANSI_YELLOW + instruction + ANSI_RESET + "\n" +
                ANSI_BLUE + "PC: " + ANSI_RESET + ANSI_YELLOW + pc.getPC() + ANSI_RESET + "\n" +
                "---------------------------------------------------\n";
    }
    public void reset() {
        instruction = "";
        pc.setPC(0);
        ifidRegister.reset();
        instructionIndex=-1;
    }

    public IFIDRegister getIfidRegister(){
        return ifidRegister;
    }
    public void setIfidRegister(IFIDRegister ifidRegister){
        this.ifidRegister=ifidRegister;
    }
    public ProgramCounter getPc(){
        return  pc;
    }
    public void setPc(ProgramCounter pc){
        this.pc = pc;
    }
    public String getInstruction(){
        return  instruction;
    }
}
