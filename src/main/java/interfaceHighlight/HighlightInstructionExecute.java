package interfaceHighlight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HighlightInstructionExecute {

    private static final Map<String, List<String>> instructionMap = new HashMap<>();

    static {
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-109", List.of("NOP", "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "MULT", "DIV", "MFHI", "MFLO", "MOV", "SLL", "SRL", "SLLV", "SRLV", "SLT", "JR", "MTHI", "MTLO", "SRA", "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "SW", "BEQ", "BNE", "BGEZ", "BLTZ", "SLTI", "J", "JAL"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-110", List.of("NOP", "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "MULT", "DIV", "MFHI", "MFLO", "MOV", "SLL", "SRL", "SLLV", "SRLV", "SLT", "JR", "MTHI", "MTLO", "SRA", "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "SW", "BEQ", "BNE", "BGEZ", "BLTZ", "SLTI", "J", "JAL"));
        instructionMap.put("cd8qKU7NTefS5XSN2jCu-32", List.of("X", "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "X", "X", "MFHI", "MFLO", "MOV", "SLL", "SRL", "SLLV", "SRLV", "SLT", "X", "X", "X", "SRA", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X"));
        instructionMap.put("cd8qKU7NTefS5XSN2jCu-28", List.of("NOP", "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "MULT", "DIV", "MFHI", "MFLO", "MOV", "SLL", "SRL", "SLLV", "SRLV", "SLT", "JR", "MTHI", "MTLO", "SRA", "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "SW", "BEQ", "BNE", "BGEZ", "BLTZ", "SLTI", "X", "JAL"));
        instructionMap.put("cd8qKU7NTefS5XSN2jCu-25", List.of("X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "SLL", "SRL", "X", "X", "X", "X", "X", "X", "X", "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "SW", "X", "X", "X", "X", "SLTI", "X", "X"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-68", List.of("NOP", "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "MULT", "DIV", "MFHI", "MFLO", "MOV", "SLL", "SRL", "SLLV", "SRLV", "SLT", "JR", "MTHI", "MTLO", "SRA", "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "SW", "BEQ", "BNE", "BGEZ", "BLTZ", "SLTI", "J", "JAL"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-84", List.of("X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "JR", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "BEQ", "BNE", "BGEZ", "BLTZ", "X", "J", "JAL"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-87", List.of("NOP", "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "MULT", "DIV", "MFHI", "MFLO", "MOV", "SLL", "SRL", "SLLV", "SRLV", "SLT", "X", "MTHI", "MTLO", "SRA", "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "SW", "BEQ", "BNE", "BGEZ", "BLTZ", "SLTI", "X", "JAL"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-89", List.of("NOP", "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "MULT", "DIV", "X", "X", "X", "X", "X", "SLLV", "SRLV", "SLT", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "SW", "BEQ", "BNE", "BGEZ", "BLTZ", "X", "X", "X"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-112", List.of("X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "SLL", "SRL", "X", "X", "X", "X", "X", "X", "SRA", "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "SW", "X", "X", "X", "X", "SLTI", "X", "JAL"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-113", List.of("NOP", "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "MULT", "DIV", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "SW", "X", "X", "X", "X", "X", "X", "X"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-73", List.of("NOP", "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "MULT", "DIV", "MFHI", "MFLO", "MOV", "SLL", "SRL", "SLLV", "SRLV", "SLT", "JR", "MTHI", "MTLO", "SRA", "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "SW", "BEQ", "BNE", "BGEZ", "BLTZ", "SLTI", "J", "JAL"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-111", List.of("X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "SLL", "SRL", "X", "X", "X", "JR", "X", "X", "SRA", "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "SW", "BEQ", "BNE", "BGEZ", "BLTZ", "SLTI", "J", "JAL"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-76", List.of("NOP", "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "MULT", "DIV", "X", "X", "X", "SLL", "SRL", "SLLV", "SRLV", "SLT", "X", "X", "X", "SRA", "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "SW", "BEQ", "BNE", "BGEZ", "BLTZ", "SLTI", "X", "JAL"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-85", List.of("X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "JR", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "BEQ", "BNE", "BGEZ", "BLTZ", "X", "J", "JAL"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-70", List.of("NOP", "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "MULT", "DIV", "X", "X", "X", "X", "X", "SLLV", "SRLV", "SLT", "X", "X", "X", "X", "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "X", "X", "X", "X", "X", "SLTI", "X", "X"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-71", List.of("NOP", "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "MULT", "DIV", "MFHI", "MFLO", "MOV", "SLL", "SRL", "SLLV", "SRLV", "SLT", "X", "X", "X", "X", "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "X", "X", "X", "X", "X", "SLTI", "X", "X"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-74", List.of("NOP", "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "MULT", "DIV", "MFHI", "MFLO", "MOV", "SLL", "SRL", "SLLV", "SRLV", "SLT", "JR", "MTHI", "MTLO", "SRA", "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "SW", "BEQ", "BNE", "BGEZ", "SLTI", "X", "JAL"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-78", List.of("X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "BEQ", "BNE", "BGEZ", "BLTZ", "X", "X", "X"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-77", List.of("NOP", "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "MULT", "DIV", "MFHI", "MFLO", "MOV", "SLL", "SRL", "SLLV", "SRLV", "SLT", "X", "MTHI", "MTLO", "SRA", "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "SW", "X", "X", "X", "X", "SLTI", "X", "JAL"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-69", List.of("NOP", "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "X", "X", "MFHI", "MFLO", "MOV", "SLL", "SRL", "SLLV", "SRLV", "SLT", "X", "X", "X", "SRA", "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "X", "X", "X", "X", "X", "SLTI", "X", "JAL"));
    }

    public static List<String> componentsToHighlight(String instruction) {
        List<String> highlightComponents = new ArrayList<>();
        instructionMap.replaceAll((key,value) -> value.stream()
                .filter(instr -> !instr.equals("X"))
                .toList());
        for (Map.Entry<String, List<String>> entry : instructionMap.entrySet()) {
            if (entry.getValue().contains(instruction)) {
                highlightComponents.add(entry.getKey());
            }
        }
        return highlightComponents;
    }
}