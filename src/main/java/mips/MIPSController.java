package mips;

import lowComponents.*;
import model.Register;
import pipelineRegisters.*;
import mipsStages.*;
import interfaceHighlight.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import model.clockType;
import org.springframework.stereotype.Service;
/**
 * The {@code MIPSController} class orchestrates the execution of a simulated MIPS pipeline.
 * It manages pipeline stages, handles clock cycles in manual and automatic modes, and
 * resolves pipeline hazards.
 */
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
    /**
     * Constructs a new {@code MIPSController} and initializes all pipeline stages,
     * registers, and components.
     */
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
    /**
     * Resets all pipeline registers, stages, and components to their initial states.
     */
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
    /**
     * Detects and resolves pipeline hazards by introducing NOP instructions and updating
     * the instruction memory accordingly.
     */
    public void solveHazards(){
        System.out.println("Calling from the new method :U");
        hazardDetection.writeDetectedHazardsToFile("HazardLog.txt");
        hazardDetection.detectAndSolveHazards();
        hazardDetection.writeUpdatedInstructionsToFile("UpdatedInstructions.txt");
        instructionMemory.setInstructions(hazardDetection.getUpdatedInstructionList());
        instructionMemory.loadInstructions("FinalInstructionList.txt");
    }
    /**
     * Retrieves the current state of the register file as a map of registers and their values.
     *
     * @return A {@code HashMap} containing registers as keys and their values as integers.
     */
    public HashMap<Register,Integer> getRegisterFileData(){
        return this.registerFile.getRegisters();
    }
    /**
     * Executes a single clock cycle by invoking each stage of the MIPS pipeline.
     * Outputs the details of the cycle's execution to the console.
     */
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
        instructionInInstructionFetch();
        instructionInInstructionDecode();
        instructionInInstructionExecute();
        instructionInMemoryStage();
        instructionInWriteBackStage();
    }
    /**
     * Executes the pipeline in manual mode, requiring user interaction to proceed to the next cycle.
     * The thread waits for notifications to advance.
     */
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
    /**
     * Notifies the thread to proceed to the next clock cycle in manual mode.
     */
    public synchronized void nextIsPressed() {
        notifyAll();
    }
    /**
     * Executes the pipeline in automatic mode, advancing through clock cycles
     * at fixed intervals until interrupted.
     */
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
    /**
     * The main entry point for the {@code MIPSController} thread.
     * Determines whether to run the pipeline in manual or automatic mode.
     */
    public void run(){
        instructionMemory.loadInstructions("src/main/resources/SimulationInstructions.txt");
        if(clockType== model.clockType.MANUAL){
            runManualMips();
        }else
            runAutomaticMips();
    }
    /**
     * Retrieves execution details from the {@code InstructionExecute} stage.
     *
     * @return A {@code String} containing execution details of the current instruction.
     */
    public String getExecutionDetails(){
        return instructionExecute.getExecutionDetails();
    }
    /**
     * Sets the clock type to either manual or automatic mode.
     *
     * @param clockType The desired {@code clockType}.
     */
    public void setClockType(model.clockType clockType) {
        this.clockType = clockType;
    }
    /**
     * Retrieves the current state of the data memory.
     *
     * @return A {@code HashMap} containing memory addresses as keys and their values as integers.
     */
    public HashMap<Integer,Integer> getMemory(){
        return this.memoryStage.getDataMemory();
    }
    /**
     * Sets the running state of the pipeline.
     *
     * @param isRunning {@code true} to start the pipeline, {@code false} to stop it.
     */
    public void setIsRunning(boolean isRunning){
        this.isRunning=isRunning;
    }
    /**
     * Updates the instruction memory with instructions from a new file.
     *
     * @param fileName The name of the file containing the new instructions.
     */
    public void updateInstructionMemort(String fileName){
        instructionMemory=new InstructionMemory(fileName);
    }
    /**
     * Retrieves the indexes of instructions currently active in each pipeline stage.
     *
     * @return A {@code List<Integer>} containing the active instruction indexes.
     */
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
    public Integer activeIF(){
        return instructionFetch.instructionIndex;
    }
    public Integer activeID(){
        return instructionDecode.instructionIndex;
    }
    public Integer activeEX(){
        return instructionExecute.instructionIndex;
    }
    public Integer activeMEM(){
        return memoryStage.instructionIndex;
    }
    public Integer activeWB(){
        return writeBackStage.instructionIndex;
    }
    /**
     * Retrieves the current instruction in the instruction fetch stage.
     * @return A {@code String} The current instruction in the instruction fetch stage.
     */
    public String instructionInInstructionFetch(){
        if(instructionFetch.getInstruction()==null)
            return "";
        String local=instructionFetch.getInstruction();
        local=local.toUpperCase();
        System.out.println(local);
        return local.split(" ")[0];
    }
    /**
     * Retrieves the current instruction in the instruction decode stage.
     * @return A {@code String} The current instruction in the instruction decode stage.
     */
    public String instructionInInstructionDecode(){
        if(instructionDecode.getInstruction()==null)
            return "";
        String local=instructionDecode.getInstruction();
        local=local.toUpperCase();
        System.out.println(local);
        return local.split(" ")[0];
    }
    /**
     * Retrieves the current instruction in the instruction execute stage.
     * @return A {@code String} The current instruction in the instruction execute stage.
     */
    public String instructionInInstructionExecute(){
        if(instructionExecute.getInstruction()==null)
            return "";
        String local=instructionExecute.getInstruction();
        local=local.toUpperCase();
        System.out.println(local);
        return local.split(" ")[0];
    }
    /**
     * Retrieves the current instruction in the memory stage.
     * @return A {@code String} The current instruction in the memory stage.
     */
    public String instructionInMemoryStage(){
        if(memoryStage.getInstruction()==null)
            return "";
        String local=memoryStage.getInstruction();
        local=local.toUpperCase();
        System.out.println(local);
        return local.split(" ")[0];
    }
    /**
     * Retrieves the current instruction in the write back stage.
     * @return A {@code String} The current instruction in the write back stage.
     */
    public String instructionInWriteBackStage(){
        if(writeBackStage.getInstruction()==null)
            return "";
        String local=writeBackStage.getInstruction();
        local=local.toUpperCase();
        System.out.println(local);
        return local.split(" ")[0];
    }
    public List<String> highlightIF(){
        return HighlightInstructionFetch.componentsToHighlight();
    }
    public List<String> highlightID(){
        return HighlightInstructionDecode.componentsToHighlight(instructionDecode.getInstruction());
    }
    public List<String> notHighlightID(){
        return HighlightInstructionDecode.componentsToNotHighlight(instructionDecode.getInstruction());
    }
    public List<String> highlightEX(){
        return HighlightInstructionExecute.componentsToHighlight(instructionExecute.getInstruction());
    }
    public List<String> notHighlightEX(){
        return HighlightInstructionExecute.componentsToNotHighlight(instructionExecute.getInstruction());
    }
    public List<String> highlightMEM(){
        return HighlightMemoryStage.componentsToHighlight(memoryStage.getInstruction());
    }
    public List<String> notHighlightMEM(){
        return HighlightMemoryStage.componentsToNotHighlight(memoryStage.getInstruction());
    }
    public List<String> highlightWB(){
        return HighlightWriteback.componentsToHighlight(writeBackStage.getInstruction());
    }
    public List<String> notHighlightWB(){
        return HighlightWriteback.componentsToNotHighlight(writeBackStage.getInstruction());
    }

}