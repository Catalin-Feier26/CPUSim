package org.example.cpusim;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import model.clockType;

@RestController
public class ApiController {

    private clockType selectedClockType;

    @GetMapping("/api/hello")
    public String sayHello() {
        return "MIPS 32 BITS PIPELINE ARCHITECTURE IS SUPERIOR!";
    }

    @GetMapping("/api/instructions")
    public String getInstructions(){
        try{
            String filePath = "C:/Users/Zach/Desktop/ComputerS/year3/sem1/structure of computer systems/CPUSim/src/main/resources/instructions.txt";
            String instructions = new String(Files.readAllBytes(Paths.get(filePath)));

            return instructions;
        }catch (IOException e){
            e.getMessage();
            return "Error reading instructions file.";
        }
    }
    @GetMapping("/api/syntax")
    public String getSyntax(){
        try{
            String filePath="src/main/resources/syntax.txt";
            String syntax = new String(Files.readAllBytes(Paths.get(filePath)));
            return syntax;
        }catch (IOException e){
            e.getMessage();
            return "Error fetching the Instructions Syntax";
        }
    }
    @PostMapping("api/setClockType")
    public String setClockType(@RequestBody ClockTypeRequest request){
        this.selectedClockType = request.getClockType();
        System.out.println("Clock type set to " + selectedClockType);
        return "Clock type set to " + selectedClockType;
    }
    @PostMapping("api/newInstructions")
    public String saveNewInstructions(@RequestBody String instructions){
        String filePath ="src/main/resources/NewInstructions.txt";
        try{
            Files.write(Paths.get(filePath), instructions.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
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
