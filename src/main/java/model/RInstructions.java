package model;
/**
 * The {@code RInstructions} enum represents the various R-type instructions in the MIPS architecture.
 * These instructions are typically used for arithmetic, logical, and control operations that involve registers.
 * <p>
 * The R-type instructions include:
 * </p>
 * <ul>
 *     <li>{@code NOP} - No operation.</li>
 *     <li>{@code ADD} - Addition of two registers.</li>
 *     <li>{@code SUB} - Subtraction of two registers.</li>
 *     <li>{@code AND} - Bitwise AND operation.</li>
 *     <li>{@code OR} - Bitwise OR operation.</li>
 *     <li>{@code NAND} - Bitwise NAND operation.</li>
 *     <li>{@code XOR} - Bitwise XOR operation.</li>
 *     <li>{@code NOR} - Bitwise NOR operation.</li>
 *     <li>{@code MULT} - Multiplication of two registers.</li>
 *     <li>{@code DIV} - Division of two registers.</li>
 *     <li>{@code MFHI} - Move from HI register.</li>
 *     <li>{@code MFLO} - Move from LO register.</li>
 *     <li>{@code MOV} - Move data between registers.</li>
 *     <li>{@code SLL} - Shift left logical operation.</li>
 *     <li>{@code SRL} - Shift right logical operation.</li>
 *     <li>{@code SLLV} - Shift left logical variable operation.</li>
 *     <li>{@code SRLV} - Shift right logical variable operation.</li>
 *     <li>{@code SLT} - Set less than comparison between two registers.</li>
 *     <li>{@code JR} - Jump to address contained in a register.</li>
 *     <li>{@code MTHI} - Move to HI register.</li>
 *     <li>{@code MTLO} - Move to LO register.</li>
 *     <li>{@code SRA} - Shift right arithmetic operation.</li>
 * </ul>
 */
public enum RInstructions {
    NOP,
    ADD,
    SUB,
    AND,
    OR,
    NAND,
    XOR,
    NOR,
    MULT,
    DIV,
    MFHI,
    MFLO,
    MOV,
    SLL,
    SRL,
    SLLV,
    SRLV,
    SLT,
    JR,
    MTHI,
    MTLO,
    SRA
}
