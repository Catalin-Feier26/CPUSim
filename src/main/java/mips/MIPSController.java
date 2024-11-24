package mips;

import lowComponents.*;
import pipelineRegisters.*;
import mipsStages.*;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.List;
import model.clockType;

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

    public MIPSController() {
        clockType= model.clockType.AUTOMATIC;
        instructionMemory = new InstructionMemory("FinalInstructionList.txt");
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

        // Detect and resolve hazards
        List<String> instructionList = instructionMemory.getInstructions();
        HazardDetection hazardDetection = new HazardDetection(instructionList);
        hazardDetection.writeDetectedHazardsToFile("HazardLog.txt");
        hazardDetection.detectAndSolveHazards();
        hazardDetection.writeUpdatedInstructionsToFile("UpdatedInstructions.txt");

        // Update instruction memory with the new instructions
        // instructionMemory.setInstructions(hazardDetection.getUpdatedInstructionList());
    }
    public static void main(String[] args) {
        MIPSController mipsController = new MIPSController();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Clock Simulation Mode: Choose '1' for Manual mode, '2' for Automatic mode.");
        String clockMode = scanner.nextLine();
        if ("1".equals(clockMode))
            mipsController.clockType = model.clockType.MANUAL;
        else
            mipsController.clockType = model.clockType.AUTOMATIC;

        if (mipsController.clockType == model.clockType.MANUAL)
        {
            System.out.println("Manual Clock Simulation: Press '1' to advance the clock, or any other key to exit.");
            while (true) {
                System.out.println("\n===== Clock Cycle Start =====");

                // Wait for user input
                String input = scanner.nextLine();
                if (!"1".equals(input)) {
                    System.out.println("Exiting simulation...");
                    break;
                }
                // Collect outputs from all stages
                StringBuilder cycleOutput = new StringBuilder();

                cycleOutput.append(mipsController.writeBackStage.execute());
                cycleOutput.append(mipsController.memoryStage.execute());
                cycleOutput.append(mipsController.instructionExecute.execute());
                cycleOutput.append(mipsController.instructionDecode.execute());
                cycleOutput.append(mipsController.instructionFetch.execute());
                System.out.println(cycleOutput);
                System.out.println("===== Clock Cycle End =====\n");
            }
            scanner.close();
        }else{
            System.out.println("AUTOMATIC MIPS PIPELINE SIMULATION WITH A CLOCK OF 1 SECOND");
            while (true){
                System.out.println("\n===== Clock Cycle Start =====");

                StringBuilder cycleOutput = new StringBuilder();
                cycleOutput.append(mipsController.writeBackStage.execute());
                cycleOutput.append(mipsController.memoryStage.execute());
                cycleOutput.append(mipsController.instructionExecute.execute());
                cycleOutput.append(mipsController.instructionDecode.execute());
                cycleOutput.append(mipsController.instructionFetch.execute());

                // Print the output for this clock cycle
                System.out.println(cycleOutput);
                System.out.println("===== Clock Cycle End =====\n");

                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.getMessage();
                    System.out.println("Some Thread Error Occured.");
                    break;
                }
            }
        }
    }


    public static void hazardCaller(){
        String instructionFilePath="src/main/resources/NewInstructions.txt";
        String hazardLogFilePath="HazardLog.txt";

        HazardDetection hazardDetection1 = new HazardDetection(instructionFilePath);
        hazardDetection1.writeDetectedHazardsToFile(hazardLogFilePath);
        hazardDetection1.writeUpdatedInstructionsToFile("UpdatedInstructions.txt");
    }
}