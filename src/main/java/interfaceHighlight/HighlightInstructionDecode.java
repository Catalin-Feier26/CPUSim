package interfaceHighlight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HighlightInstructionDecode {

    private static final Map<String, List<String>> instructionMap = new HashMap<>();

    static {
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-66", List.of("NOP", "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "MULT", "DIV", "MFHI", "MFLO", "MOV", "SLL", "SRL", "SLLV", "SRLV", "SLT", "JR", "MTHI", "MTLO", "SRA", "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "SW", "BEQ", "BNE", "BGEZ", "BLTZ", "SLTI", "J", "JAL"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-97", List.of("NOP", "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "MULT", "DIV", "MFHI", "MFLO", "MOV", "SLL", "SRL", "SLLV", "SRLV", "SLT", "JR", "MTHI", "MTLO", "SRA", "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "SW", "BEQ", "BNE", "BGEZ", "BLTZ", "SLTI", "J", "JAL"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-98", List.of("NOP", "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "MULT", "DIV", "X", "X", "MOV", "SLL", "SRL", "SLLV", "SRLV", "SLT", "JR", "MTHI", "MTLO", "SRA", "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "SW", "BEQ", "BNE", "BGEZ", "BLTZ", "SLTI", "X", "X"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-99", List.of("NOP", "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "MULT", "DIV", "X", "X", "X", "X", "X", "SLLV", "SRLV", "SLT", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "BEQ", "BNE", "BGEZ", "BLTZ", "X", "X", "X"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-100", List.of("NOP", "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "MULT", "DIV", "MFHI", "MFLO", "MOV", "SLL", "SRL", "SLLV", "SRLV", "SLT", "JR", "MTHI", "MTLO", "SRA", "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "SW", "BEQ", "BNE", "BGEZ", "BLTZ", "SLTI", "J", "JAL"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-102", List.of("NOP", "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "MULT", "DIV", "X", "X", "X", "X", "X", "SLLV", "SRLV", "SLT", "X", "MTHI", "MTLO", "SRA", "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "SW", "BEQ", "BNE", "BGEZ", "BLTZ", "SLTI", "X", "JAL"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-103", List.of("NOP", "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "MULT", "DIV", "MFHI", "MFLO", "MOV", "SLL", "SRL", "SLLV", "SRLV", "SLT", "X", "MTHI", "MTLO", "SRA", "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "SW", "BEQ", "BNE", "BGEZ", "BLTZ", "SLTI", "X", "JAL"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-72", List.of("X", "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "MULT", "DIV", "MFHI", "MFLO", "MOV", "SLL", "SRL", "SLLV", "SRLV", "SLT", "JR", "MTHI", "MTLO", "SRA", "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "SW", "BEQ", "BNE", "BGEZ", "BLTZ", "SLTI", "J", "JAL"));
        instructionMap.put("cd8qKU7NTefS5XSN2jCu-23", List.of("X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "SW", "BEQ", "BNE", "BGEZ", "BLTZ", "SLTI", "X", "X"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-86", List.of("NOP", "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "MULT", "DIV", "X", "X", "MOV", "SLL", "SRL", "SLLV", "SRLV", "SLT", "JR", "MTHI", "MTLO", "SRA", "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "SW", "BEQ", "BNE", "BGEZ", "BLTZ", "SLTI", "X", "X"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-88", List.of("NOP", "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "MULT", "DIV", "X", "X", "X", "X", "X", "SLLV", "SRLV", "SLT", "JR", "X", "X", "X", "X", "X", "X", "X", "X", "X", "X", "BEQ", "BNE", "BGEZ", "BLTZ", "X", "X", "X"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-106", List.of("NOP", "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "MULT", "DIV", "MFHI", "MFLO", "MOV", "SLL", "SRL", "SLLV", "SRLV", "SLT", "JR", "MTHI", "MTLO", "SRA", "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "SW", "BEQ", "BNE", "BGEZ", "BLTZ", "SLTI", "J", "JAL"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-107", List.of("NOP", "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "MULT", "DIV", "MFHI", "MFLO", "MOV", "SLL", "SRL", "SLLV", "SRLV", "SLT", "JR", "MTHI", "MTLO", "SRA", "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "SW", "BEQ", "BNE", "BGEZ", "BLTZ", "SLTI", "J", "JAL"));
        instructionMap.put("EqLG6SQoEO7LvZT7sfC5-108", List.of("NOP", "ADD", "SUB", "AND", "OR", "NAND", "XOR", "NOR", "MULT", "DIV", "MFHI", "MFLO", "MOV", "SLL", "SRL", "SLLV", "SRLV", "SLT", "JR", "MTHI", "MTLO", "SRA", "ADDI", "SUBI", "ANDI", "ORI", "XORI", "LW", "SW", "BEQ", "BNE", "BGEZ", "BLTZ", "SLTI", "J", "JAL"));
    }

    /**
     * Returns the components that should be highlighted for the given instruction
     * @param instruction The instruction to be decoded
     * @return List<String> The components to be highlighted
     */
    public static List<String> componentsToHighlight(String instruction) {
        List<String> highlightComponents = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : instructionMap.entrySet()) {
            if (entry.getValue().contains(instruction.toUpperCase().split(" ")[0])) {
                highlightComponents.add(entry.getKey());
            }
        }
        return highlightComponents;
    }

    /**
     * Returns the components that should not be highlighted for the given instruction
     * @param instruction The instruction to be decoded
     * @return List<String> The components to not be highlighted
     */
    public static List<String> componentsToNotHighlight(String instruction){
        List<String> highlightComponents = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : instructionMap.entrySet()) {
            if (!entry.getValue().contains(instruction.toUpperCase().split(" ")[0])) {
                highlightComponents.add(entry.getKey());
            }
        }
        return highlightComponents;
    }
}