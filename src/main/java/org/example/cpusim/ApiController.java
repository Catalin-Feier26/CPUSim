package org.example.cpusim;

import model.Register;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.clockType;
import mips.MIPSController;

@RestController
@ComponentScan(basePackages = {"org.example.cpusim", "mips"})
public class ApiController {

    private clockType selectedClockType;
    private MIPSController mips;
    private boolean isRunning=false;
    private Thread simulationThread;

    @Autowired
    public ApiController(MIPSController mips){
        this.mips=mips;
    }

    @GetMapping("/api/instructions")
    public String getInstructions(){
        try{
            String filePath = "C:/Users/Zach/Desktop/ComputerS/year3/sem1/structure of computer systems/CPUSim/src/main/resources/instructions.txt";

            return new String(Files.readAllBytes(Paths.get(filePath)));
        }catch (IOException e){
            return e.getMessage();
        }
    }

    @GetMapping("/api/currentInstructionIndex")
    public List<Integer> getActiveInstructions() {
        return mips.activeInstructionsIndexes();
    }

    @GetMapping("api/registerFile")
    public HashMap<String, Integer> getRegisterFile() {
        HashMap<String, Integer> registerMap = new HashMap<>();
        HashMap<Register, Integer> registerFileData = mips.getRegisterFileData();
        for (Map.Entry<Register, Integer> entry : registerFileData.entrySet()) {
            registerMap.put(entry.getKey().name(), entry.getValue());
        }
        return registerMap;
    }

    @GetMapping("/api/memoryData")
    public HashMap<String, Integer> getMemoryData() {
        HashMap<String, Integer> memoryMap = new HashMap<>();
        HashMap<Integer, Integer> memoryData = mips.getMemory();

        for (Map.Entry<Integer, Integer> entry : memoryData.entrySet()) {
            memoryMap.put("Address " + entry.getKey(), entry.getValue());
        }

        return memoryMap;
    }


    @GetMapping("/api/syntax")
    public String getSyntax(){
        try{
            String filePath="src/main/resources/syntax.txt";
            return new String(Files.readAllBytes(Paths.get(filePath)));
        }catch (IOException e){
            e.getMessage();
            return "Error fetching the Instructions Syntax";
        }
    }

    @GetMapping("api/hazardLogs")
    public String getHazardLogs(){
        MIPSController mipsController=new MIPSController();
        mipsController.solveHazards();
        try{
            String filePath="HazardLog.txt";
            String logs = new String(Files.readAllBytes(Paths.get(filePath)));
            return logs;
        }catch (IOException e){
            e.getMessage();
            return "Error getting the hazards logs!";
        }
    }

    @PostMapping("api/start")
    public String startSimulation() {
        mips.resetMips();
        mips.updateInstructionMemort("FinalInstructionList.txt");
        if (simulationThread == null || !simulationThread.isAlive()) {
            mips.setClockType(selectedClockType);
            mips.setIsRunning(true);
            simulationThread = new Thread(mips);
            simulationThread.start();
            System.out.println("Simulation started running");
            return "Simulation started in ";
        } else {
            System.out.println("Simulation is already running");
            return "Simulation is already running.";
        }
    }

    @PostMapping("api/stop")
    public String stopMips() {
        if (simulationThread != null && simulationThread.isAlive()) {
            mips.setIsRunning(false);
            simulationThread.interrupt();
            System.out.println("Simulation Stopped");
            return "Simulation stopped.";
        } else {
            System.out.println("Simulation is not running");
            return "Simulation is not running.";
        }
    }


    @PostMapping("api/nextCycle")
    public String nextCycle() {
        if (simulationThread != null && simulationThread.isAlive()) {
            mips.nextIsPressed();
            System.out.println("NEXT CYCLE");
            return "Next cycle executed.";
        } else {
            System.out.println("Simulation is NOT RUNNING or NOT in MANUAL mode");
            return "Simulation is not running or not in manual mode.";
        }
    }

    @GetMapping("api/updatedInstructions")
    public String getUpdatedInstructions(){
        try{
            String filePath="UpdatedInstructions.txt";
            String updatedInstructions= new String(Files.readAllBytes(Paths.get(filePath)));
            return updatedInstructions;
        }catch (IOException e){
            e.getMessage();
            return "Some error occured when fetching updated Instructions";
        }
    }

    @GetMapping("api/arrayInstructions")
    public List<String> getInstructionsAsArray(){
        List<String> instructions;
        try{
            String filePath="FinalInstructionList.txt";
            instructions=Files.readAllLines(Paths.get(filePath));
            return instructions;
        }catch (IOException e){
            System.err.println("Error fetching the instructions");
            return null;
        }
    }

    @GetMapping("api/finalInstructions")
    public String getFinalInstructions(){
        try{
            String filePath="FinalInstructionList.txt";
            String finalInstructions=new String(Files.readAllBytes(Paths.get(filePath)));
            return finalInstructions;
        }catch (IOException e){
            e.getMessage();
            return "Error fetching the final instructions";
        }
    }

    @GetMapping("api/aluData")
    public String getAluData() {
        return mips.getExecutionDetails();
    }

    @PostMapping("api/setClockType")
    public ResponseEntity<Map<String, String>> setClockType(@RequestBody ClockTypeRequest request) {
        this.selectedClockType = request.getClockType();
        System.out.println("Clock type set to " + selectedClockType.name());
        mips.setClockType(selectedClockType);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Clock type set to " + selectedClockType.name());
        return ResponseEntity.ok(response);
    }


    @PostMapping ("api/updateInstructions")
    public String updatedInstructions(@RequestBody String instructions){
        String filePath="FinalInstructionList.txt";
        String filePath2="src/main/resources/SimulationInstructions.txt";
        try{
            Files.write(Paths.get(filePath),instructions.getBytes());
            Files.write(Paths.get(filePath2),instructions.getBytes());
            return "Instructions Saved Successfully!";
        }catch (IOException e){
            e.getMessage();
            System.out.println("Error saving the new instructions");
            return "Something went VERY wrong!";
        }
    }

    @PostMapping("api/newInstructions")
    public String saveNewInstructions(@RequestBody String instructions){
        String filePath ="src/main/resources/NewInstructions.txt";
        String instructionsFilePath = "src/main/resources/instructions.txt";
        try{
            Files.write(Paths.get(filePath), instructions.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            Files.write(Paths.get(instructionsFilePath), instructions.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            return "Instruction saved successfully!";
        }catch (IOException e){
            e.getMessage();
            return  "Error saving instructions.";
        }
    }


    public static class ClockTypeRequest{
        private clockType clockType;
        public clockType getClockType(){
            return clockType;
        }
        public void setClockType(clockType clockType){
            this.clockType= clockType;
        }
    }
}
