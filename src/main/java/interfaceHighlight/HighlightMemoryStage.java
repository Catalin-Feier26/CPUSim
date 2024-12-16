package interfaceHighlight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HighlightMemoryStage {

    private static final Map<String, List<String>> instructionMap = new HashMap<>();

    static {
        instructionMap.put("cd8qKU7NTefS5XSN2jCu-1", List.of("NOP", "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "MULT", "DIV", "MFHI", "MFLO", "MOV", "SLL", "SRL", "SLLV", "SRLV", "SLT", "X", "MTHI", "MTLO", "SRA", "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "SW", "X", "X", "X", "X", "SLTI", "X", "JAL"));
        instructionMap.put("cd8qKU7NTefS5XSN2jCu-11", List.of("X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "JR", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "J", "JAL"));
        instructionMap.put("cd8qKU7NTefS5XSN2jCu-7", List.of("X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "BEQ", "BNE", "BGEZ", "BLTZ", "X", "X", "X"));
        instructionMap.put("cd8qKU7NTefS5XSN2jCu-13", List.of("X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "SW", "X", "X", "X", "X", "X", "X", "X"));
        instructionMap.put("cd8qKU7NTefS5XSN2jCu-15", List.of("X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "LW", "X", "X", "X", "X", "X", "X", "X", "X"));
        instructionMap.put("cd8qKU7NTefS5XSN2jCu-38", List.of("X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "JR", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "BEQ", "BNE", "BGEZ", "BLTZ", "X", "J", "JAL"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-79", List.of("NOP", "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "MULT", "DIV", "MFHI", "MFLO", "MOV", "SLL", "SRL", "SLLV", "SRLV", "SLT", "X", "MTHI", "MTLO", "SRA", "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "SW", "X", "X", "X", "X", "SLTI", "X", "JAL"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-80", List.of("X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "SW", "X", "X", "X", "X", "X", "X", "X"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-94", List.of("NOP", "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "MULT", "DIV", "MFHI", "MFLO", "MOV", "SLL", "SRL", "SLLV", "SRLV", "SLT", "X", "MTHI", "MTLO", "SRA", "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "X", "X", "X", "X", "X", "SLTI", "X", "JAL"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-105", List.of("X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "BEQ", "BNE", "BGEZ", "BLTZ", "X", "X", "X"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-104", List.of("X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "BEQ", "BNE", "BGEZ", "BLTZ", "X", "X", "X"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-81", List.of("X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "LW", "X", "X", "X", "X", "X", "X", "X", "X"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-90", List.of("NOP", "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "X", "X", "MFHI", "MFLO", "MOV", "SLL", "SRL", "SLLV", "SRLV", "SLT", "X", "X", "X", "SRA", "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "X", "X", "X", "X", "X", "SLTI", "X", "JAL"));
        instructionMap.put("cd8qKU7NTefS5XSN2jCu-34", List.of("X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "JR", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "BEQ", "BNE", "BGEZ", "BLTZ", "X", "J", "JAL"));
    }

    public static List<String> componentsToHighlight(String instruction) {
        List<String> highlightComponents = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : instructionMap.entrySet()) {
            if (entry.getValue().contains(instruction)) {
                highlightComponents.add(entry.getKey());
            }
        }
        return highlightComponents;
    }
    public static List<String> componentsToNotHighlight(String instruction){
        List<String> highlightComponents = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : instructionMap.entrySet()) {
            if (!entry.getValue().contains(instruction)) {
                highlightComponents.add(entry.getKey());
            }
        }
        return highlightComponents;
    }
}