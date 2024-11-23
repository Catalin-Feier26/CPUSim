package org.example.cpusim;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class ApiController {

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
}
