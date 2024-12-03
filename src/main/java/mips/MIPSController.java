package mips;

import lowComponents.*;
import model.Register;
import pipelineRegisters.*;
import mipsStages.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import model.clockType;
import org.springframework.stereotype.Service;

@Service
public class MIPSController implements Runnable {
    private clockType clockType;
    private volatile boolean isRunning=false;

    private ProgramCounter pc;
    private InstructionMemory instructionMemory;
    private RegisterFile registerFile;
    private IFIDRegister ifidRegister;
    private IDEXRegister idexRegister;
    private EXMEMRegister exmemRegister;
    private MEMWBRegister memwbRegister;
    private ControlUnit controlUnit;

    private InstructionFetch instructionFetch;
    private InstructionDecode instructionDecode;
    private InstructionExecute instructionExecute;
    private MemoryStage memoryStage;
    private WriteBackStage writeBackStage;

    private HazardDetection hazardDetection;

    public MIPSController() {
        clockType= model.clockType.AUTOMATIC;
        instructionMemory = new InstructionMemory();
        pc = new ProgramCounter(instructionMemory.getInstructionCount());
        registerFile = new RegisterFile();
        ifidRegister = new IFIDRegister();
        idexRegister = new IDEXRegister();
        exmemRegister = new EXMEMRegister();
        memwbRegister = new MEMWBRegister();
        controlUnit = new ControlUnit();

        instructionFetch = new InstructionFetch(pc, instructionMemory, ifidRegister);
        instructionDecode = new InstructionDecode(ifidRegister, idexRegister, registerFile, controlUnit);
        instructionExecute = new InstructionExecute(idexRegister, exmemRegister);
        memoryStage = new MemoryStage(exmemRegister, memwbRegister, new DataMemory(), pc);
        writeBackStage = new WriteBackStage(memwbRegister, registerFile);
        instructionMemory.loadInstructions("src/main/resources/SimulationInstructions.txt");
         hazardDetection = new HazardDetection(new InstructionMemory("src/main/resources/NewInstructions.txt").getInstructions());
    }
    public void resetMips(){
        registerFile.reset();
        pc.reset();
        ifidRegister.reset();
        idexRegister.reset();
        exmemRegister.reset();
        memwbRegister.reset();
        controlUnit.reset();
        instructionFetch.reset();
        instructionDecode.reset();
        instructionExecute.reset();
        memoryStage.reset();
        writeBackStage.reset();
    }
    public void solveHazards(){
        System.out.println("Calling from the new method :U");
        hazardDetection.writeDetectedHazardsToFile("HazardLog.txt");
        hazardDetection.detectAndSolveHazards();
        hazardDetection.writeUpdatedInstructionsToFile("UpdatedInstructions.txt");
        instructionMemory.setInstructions(hazardDetection.getUpdatedInstructionList());
        instructionMemory.loadInstructions("FinalInstructionList.txt");
    }
    public HashMap<Register,Integer> getRegisterFileData(){
        return this.registerFile.getRegisters();
    }
    public void executeClockCycle(){
        System.out.println("\n===== Clock Cycle Start =====");
        StringBuilder cycleOutput = new StringBuilder();

        cycleOutput.append(writeBackStage.execute());
        cycleOutput.append(memoryStage.execute());
        cycleOutput.append(instructionExecute.execute());
        cycleOutput.append(instructionDecode.execute());
        cycleOutput.append(instructionFetch.execute());
        System.out.println(cycleOutput);
        System.out.println("===== Clock Cycle End =====\n");
    }
    public synchronized void runManualMips() {
        while (isRunning) {
            try {
                wait();
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Manual mode interrupted, stopping...");
                    break;  // Stop the loop if interrupted
                }
                executeClockCycle();
            } catch (InterruptedException e) {
                System.out.println("Manual mode interrupted: " + e.getMessage());
                break;  // Exit the loop on interrupt
            }
        }
    }

    public synchronized void nextIsPressed() {
        notifyAll();
    }

    public void runAutomaticMips() {
        while (isRunning) {
            try {
                executeClockCycle();
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Automatic mode interrupted, stopping...");
                    break;  // Exit if interrupted
                }
                Thread.sleep(1000);  // Simulate a 1-second cycle
            } catch (InterruptedException e) {
                System.out.println("Some thread error occurred: " + e.getMessage());
                break;  // Exit the loop if interrupted
            }
        }
    }

    public void run(){
        instructionMemory.loadInstructions("src/main/resources/SimulationInstructions.txt");
        if(clockType== model.clockType.MANUAL){
            runManualMips();
        }else
            runAutomaticMips();
    }
    public String getExecutionDetails(){
        return instructionExecute.getExecutionDetails();
    }

    public void setClockType(model.clockType clockType) {
        this.clockType = clockType;
    }

    public HashMap<Integer,Integer> getMemory(){
        return this.memoryStage.getDataMemory();
    }
    public void setIsRunning(boolean isRunning){
        this.isRunning=isRunning;
    }
    public void updateInstructionMemort(String fileName){
        instructionMemory=new InstructionMemory(fileName);
    }
    public List<Integer> activeInstructionsIndexes(){
        List<Integer> activeIndexes= new ArrayList<>();
        if(instructionFetch.instructionIndex != -1)
            activeIndexes.add(instructionFetch.instructionIndex);
        if(instructionDecode.instructionIndex -1!= -1)
            activeIndexes.add(instructionDecode.instructionIndex-1);
        if(instructionExecute.instructionIndex != -1)
            activeIndexes.add(instructionExecute.instructionIndex);
        if(memoryStage.instructionIndex != -1)
            activeIndexes.add(memoryStage.instructionIndex);
        if(writeBackStage.instructionIndex != -1)
            activeIndexes.add(writeBackStage.instructionIndex);

        return activeIndexes;
    }

}