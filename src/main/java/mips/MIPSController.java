package mips;

import lowComponents.*;
import pipelineRegisters.*;
import mipsStages.*;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class MIPSController {
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
        instructionMemory = new InstructionMemory("C:/Users/Zach/Desktop/ComputerS/year3/sem1/structure of computer systems/CPUSim/src/main/resources/instructions.txt");
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
    }

    public static void main(String[] args) {
        MIPSController mipsController = new MIPSController();
        Scanner scanner = new Scanner(System.in);
        Queue<Runnable> pipeline = new LinkedList<>();

        while (true) {
            System.out.println("Press 1 to advance the pipeline, or any other key to exit.");
            String input = scanner.nextLine();

            if (!"1".equals(input)) {
                break;
            }

            // Fetch Stage
            pipeline.add(() -> {
                System.out.println("FETCHING PHASE ------------------------------------------");
                mipsController.instructionFetch.execute();
            });

            // Decode Stage
            pipeline.add(() -> {
                System.out.println("DECODING PHASE ------------------------------------------");
                mipsController.instructionDecode.execute();
            });

            // Execute Stage
            pipeline.add(() -> {
                System.out.println("EXECUTION PHASE ------------------------------------------");
                mipsController.instructionExecute.execute();
            });

            // Memory Stage
            pipeline.add(() -> {
                System.out.println("MEMORY PHASE ------------------------------------------");
                mipsController.memoryStage.execute();
            });

            // Write Back Stage
            pipeline.add(() -> {
                System.out.println("WRITE BACK PHASE ------------------------------------------");
                mipsController.writeBackStage.execute();
            });

            // Execute the next stage in the pipeline
            if (!pipeline.isEmpty()) {
                pipeline.poll().run();
            }
        }
        scanner.close();
    }
}