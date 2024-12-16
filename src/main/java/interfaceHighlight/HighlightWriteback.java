package interfaceHighlight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HighlightWriteback {

    private static final Map<String, List<String>> instructionMap = new HashMap<>();

    static {
        instructionMap.put("cd8qKU7NTefS5XSN2jCu-17", List.of("X", "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "X", "X", "MFHI", "MFLO", "MOV", "SLL", "SRL", "SLLV", "SRLV", "SLT", "X", "MTHI", "MTLO", "SRA", "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "X", "X", "X", "X", "X", "SLTI", "X", "JAL"));
        instructionMap.put("cd8qKU7NTefS5XSN2jCu-19", List.of("X", "X", "X", "X", "X", "X", "X", "X", "MULT", "DIV", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "MTLO", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X"));
        instructionMap.put("cd8qKU7NTefS5XSN2jCu-20", List.of("X", "X", "X", "X", "X", "X", "X", "X", "MULT", "DIV", "X", "X", "X", "X", "X", "X", "X", "X", "X", "MTHI", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X"));
        instructionMap.put("cd8qKU7NTefS5XSN2jCu-2", List.of("X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "LW", "X", "X", "X", "X", "X", "X", "X", "X"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-82", List.of("X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "LW", "X", "X", "X", "X", "X", "X", "X", "X"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-95", List.of("NOP", "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "MULT", "DIV", "MFHI", "MFLO", "MOV", "SLL", "SRL", "SLLV", "SRLV", "SLT", "X", "MTHI", "MTLO", "SRA", "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "X", "X", "X", "X", "X", "SLTI", "X", "JAL"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-93", List.of("NOP", "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "MULT", "DIV", "MFHI", "MFLO", "MOV", "SLL", "SRL", "SLLV", "SRLV", "SLT", "X", "MTHI", "MTLO", "SRA", "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "X", "X", "X", "X", "X", "SLTI", "X", "JAL"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-91", List.of("NOP", "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "X", "X", "MFHI", "MFLO", "MOV", "SLL", "SRL", "SLLV", "SRLV", "SLT", "X", "X", "X", "SRA", "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "X", "X", "X", "X", "X", "SLTI", "X", "JAL"));
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
    public static List<String> componentsToNotHighlight(String instruction) {
        List<String> highlightComponents = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : instructionMap.entrySet()) {
            if (!entry.getValue().contains(instruction)) {
                highlightComponents.add(entry.getKey());
            }
        }
        return highlightComponents;
    }
}