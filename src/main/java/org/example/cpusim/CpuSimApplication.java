package org.example.cpusim;

import lowComponents.*;
import pipelineRegisters.*;
import mipsStages.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class CpuSimApplication {

    public static void main(String[] args) {
        SpringApplication.run(CpuSimApplication.class, args);

    }
}