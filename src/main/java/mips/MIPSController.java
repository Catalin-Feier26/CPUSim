package mips;

import lowComponents.*;
import model.Register;
import pipelineRegisters.*;
import mipsStages.*;
import java.util.HashMap;
import java.util.Scanner;
import model.clockType;
import org.springframework.stereotype.Service;

@Service
public class MIPSController {
    private clockType clockType;
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
        instructionMemory = new InstructionMemory("src/main/resources/NewInstructions.txt");
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

         hazardDetection = new HazardDetection(instructionMemory.getInstructions());
    }

    public void solveHazards(){
        System.out.println("Calling from the new method :U");
        hazardDetection.writeDetectedHazardsToFile("HazardLog.txt");
        hazardDetection.detectAndSolveHazards();
        hazardDetection.writeUpdatedInstructionsToFile("UpdatedInstructions.txt");
        instructionMemory.setInstructions(hazardDetection.getUpdatedInstructionList());
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
    public void runManualMips(){
        Scanner scanner= new Scanner(System.in);
        while(true){
            String input=scanner.nextLine();
            if(!"1".equals(input)){
                System.out.println("Exiting simulation....");
                break;
            }
            executeClockCycle();
        }
        scanner.close();
    }
    public void runAutomaticMips(){
        while(true){
            executeClockCycle();
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.getMessage();
                System.out.println("Some Thread Error Occured.");
                break;
            }
        }
    }
    public void startMIPS(clockType clockType){
        if(clockType== model.clockType.MANUAL) {
            runManualMips();
            return;
        }
        runAutomaticMips();
    }
    public String getExecutionDetails(){
        return instructionExecute.getExecutionDetails();
    }
    public HashMap<Integer,Integer> getMemory(){
        return this.memoryStage.getDataMemory();
    }
}