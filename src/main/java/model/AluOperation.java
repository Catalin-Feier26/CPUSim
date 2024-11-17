package model;

/**
 * This ENUM represents all the ALU operations that can be performed by the MIPS
 * Because most Instructions can be represented by an ALU operation, this ENUM is used
 * to determine the operation to be performed by the ALU
 */
public enum AluOperation {
    UNSPECIFIED,   // Operation to be determined by the FUNC field
    ADD,    // Addition (e.g., ADD, ADDI)
    SUB,    // Subtraction (e.g., SUB, SUBI, BEQ, BNE)
    AND,    // AND operation (e.g., AND, ANDI)
    OR,     // OR operation (e.g., OR, ORI)
    NAND,   // NAND operation
    XOR,    // XOR operation (e.g., XOR, XORI)
    NOR,    // NOR operation
    MULT,   // Multiplication (stores result in Hi and Lo)
    DIV,    // Division (stores result in Hi and Lo)
    SL,     // Shift left (for both SLL and SLLV)
    SR,     // Shift right logical (for both SRL and SRLV)
    SRA,    // Shift right arithmetic (preserves sign)
    SLT     // Set on less than (e.g., SLT, SLTI)
};
