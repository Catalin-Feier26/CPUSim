
package mipsStages;

import lowComponents.*;
import model.AluOperation;
import model.ext;
import pipelineRegisters.IDEXRegister;
import pipelineRegisters.IFIDRegister;
import model.*;

/**
 * Represents the Instruction Decode stage in a MIPS pipeline simulation.
 * Responsible for parsing and decoding the current instruction, reading register values,
 * and passing decoded information and control signals to the next pipeline stage (ID/EX).
 */
public class InstructionDecode {
    private IFIDRegister ifidRegister;
    private IDEXRegister idexRegister;
    private RegisterFile registerFile;
    private ControlUnit controlUnit;

    private String instruction;
    public int instructionIndex;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE="\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";

    private int readData1;
    private int readData2;
    private int signExtended;
    private Register rs;
    private Register rt;
    private Register rd;
    private int shamt;
    private int funct;
    private int immediate;
    private int address;

    private int pc;
    /**
     * Constructs an InstructionDecode instance with the given pipeline registers, register file, and control unit.
     *
     * @param ifidRegister  The IF/ID pipeline register.
     * @param idexRegister  The ID/EX pipeline register.
     * @param registerFile  The register file used by the CPU.
     * @param controlUnit   The control unit generating control signals.
     */
    public InstructionDecode(IFIDRegister ifidRegister, IDEXRegister idexRegister, RegisterFile registerFile, ControlUnit controlUnit) {
        this.ifidRegister=ifidRegister;
        this.idexRegister=idexRegister;
        this.registerFile=registerFile;
        this.controlUnit=controlUnit;
        //Set other values to default:
        instruction="";
        instructionIndex=-1;

        readData1=0;
        readData2=0;
        signExtended=0;
        rs=Register.R0;
        rt=Register.R0;
        rd=Register.R0;
        shamt=0;
        funct=0;
        immediate=0;
        address=0;
        pc=0;
    }
    /**
     * Resets the stage, clearing the current instruction and resetting all fields to default values.
     */
    public void reset(){
        instruction="";
        this.pc=0;
        setValuesToDefault();;
        readData1=0;
        readData2=0;
    }
    /**
     * Sets all instruction-related fields to their default values.
     */
    private void setValuesToDefault(){
        instructionIndex=pc;
        readData1=0;
        readData2=0;
        signExtended=0;
        rs=Register.R0;
        rt=Register.R0;
        rd=Register.R0;
        shamt=0;
        funct=0;
        immediate=0;
        address=0;
    }
    /**
     * Sets the current instruction from the IF/ID pipeline register.
     */
    private void setInstruction(){
        this.instruction=this.ifidRegister.getInstruction();
        instructionIndex=pc-1;
    }
    /**
     * Sets the program counter (PC) from the IF/ID pipeline register.
     */
    private void setPc(){
        this.pc = this.ifidRegister.getPC();
    }
    /**
     * Decodes the control signals for the current instruction.
     */
    private void decodeControlSignals(){
        controlUnit.generateControlSignals(this.instruction);
    }
    /**
     * Reads data from the register file for the source registers specified in the instruction.
     */
    private void readFromRegisterFile(){
        readData1 = registerFile.getRegister(rs);
        readData2 = registerFile.getRegister(rt);
    }
    /**
     * Passes control signals and other decoded values to the ID/EX pipeline register.
     */
    private void passControlSignals(){
        idexRegister.setPcSrc(controlUnit.getPcSrc());
        idexRegister.setRegDst(controlUnit.getRegDst());
        idexRegister.setBranch(controlUnit.getBranch());
        idexRegister.setMemRead(controlUnit.getMemRead());
        idexRegister.setMemToReg(controlUnit.getMemToReg());
        idexRegister.setAluOp(controlUnit.getAluOp());
        idexRegister.setMemWrite(controlUnit.getMemWrite());
        idexRegister.setAluSrc(controlUnit.getAluSrc());
        idexRegister.setRegWrite(controlUnit.getRegWrite());
        idexRegister.setJump(controlUnit.getJump());
        idexRegister.setHiWrite(controlUnit.getHiWrite());
        idexRegister.setLoWrite(controlUnit.getLoWrite());

        idexRegister.setReadData1(readData1);
        idexRegister.setReadData2(readData2);
        idexRegister.setSignExtend(immediate);
        idexRegister.setImmediate(immediate);
        idexRegister.setRt(rt);
        idexRegister.setRd(rd);
        idexRegister.setSa(shamt);
        idexRegister.setPC(pc);
        idexRegister.setJumpAddress(address);

        idexRegister.setLo(registerFile.getLo());
        idexRegister.setHi(registerFile.getHi());

        idexRegister.instruction=instruction;
        idexRegister.instructionIndex=instructionIndex-1;

    }
    /**
     * Retrieves the current instruction being processed.
     *
     * @return the current instruction as a string
     */
    public String getInstruction(){
        return instruction;
    }
    /**
     * Executes the Instruction Decode stage by processing the current instruction,
     * decoding its components, and passing data and control signals to the next stage.
     *
     * @return a formatted string summarizing the decode stage's output
     */
    public String execute(){
        setInstruction();
        if(instruction.equals("")) {
            setValuesToDefault();
            return pretty();
        }
        setPc();
        decodeControlSignals();
        decodeInstruction();
        readFromRegisterFile();
        passControlSignals();
        return pretty();
        //prettyPrint();
    }
    /**
     * Decodes the current instruction by determining its type (R-type, I-type, J-type)
     * and parsing its components accordingly.
     */
    public void decodeInstruction() {
        String[] parts = instruction.toUpperCase().split(" ");
        setValuesToDefault();
        try {
            Enum.valueOf(RInstructions.class, parts[0]);
            decodeRType(parts);
        } catch (IllegalArgumentException e1) {
            try {
                Enum.valueOf(IInstructions.class, parts[0]);
                decodeIType(parts);
            } catch (IllegalArgumentException e2) {
                try {
                    Enum.valueOf(JInstructionctions.class, parts[0]);
                    decodeJType(parts);
                } catch (IllegalArgumentException e3) {
                    System.out.println("Invalid instruction " + parts[0]);
                }
            }
        }
    }
    /**
     * Decodes an R-type instruction and extracts its components.
     *
     * @param parts the parts of the R-type instruction as a string array
     */
    private void decodeRType(String[] parts) {
        switch (parts[0]) {
            case "NOP":
                rs = Register.R0;
                rt = Register.R0;
                rd = Register.R0;
                shamt = 0;
                funct = 0;
                break;
            case "ADD":
            case "SUB":
            case "AND":
            case "OR":
            case "NAND":
            case "XOR":
            case "NOR":
            case "SLT", "SLLV", "SRLV":
                rd = Enum.valueOf(Register.class, parts[1]);
                rs = Enum.valueOf(Register.class, parts[2]);
                rt = Enum.valueOf(Register.class, parts[3]);
                shamt = 0;
                funct = 0;
                break;
            case "MULT":
            case "DIV":
                rs = Enum.valueOf(Register.class, parts[1]);
                rt = Enum.valueOf(Register.class, parts[2]);
                rd = Register.R0;
                shamt = 0;
                funct = 0;
                break;
            case "MFHI":
            case "MFLO":
                rd = Enum.valueOf(Register.class, parts[1]);
                rs = Register.R0;
                rt = Register.R0;
                shamt = 0;
                funct = 0;
                break;
            case "MOV":
                rd = Enum.valueOf(Register.class, parts[1]);
                rs = Enum.valueOf(Register.class, parts[2]);
                rt = Register.R0;
                shamt = 0;
                funct = 0;
                break;
            case "SLL":
            case "SRL":
            case "SRA":
                rd = Enum.valueOf(Register.class, parts[1]);
                rt = Enum.valueOf(Register.class, parts[2]);
                shamt = Integer.parseInt(parts[3]);
                rs = Register.R0;
                funct = 0;
                break;
            case "MTHI", "MTLO":
                rs = Enum.valueOf(Register.class, parts[1]);
                rt = Register.R0;
                rd = Register.R0;
                shamt = 0;
                funct = 0;
                break;
            case "JR":
                rs = Enum.valueOf(Register.class, parts[1]);
                rt = Register.R0;
                rd = Register.R0;
                shamt = 0;
                funct = 0;
                address=registerFile.getRegister(rs);
                break;

        }
    }
    /**
     * Decodes an I-type instruction and extracts its components.
     *
     * @param parts the parts of the I-type instruction as a string array
     */
    private void decodeIType(String[] parts) {
        switch (parts[0]) {
            case "ADDI":
            case "SUBI":
            case "ANDI":
            case "ORI":
            case "XORI":
            case "SLTI":
                rt = Enum.valueOf(Register.class, parts[1]);
                rs = Enum.valueOf(Register.class, parts[2]);
                immediate = Integer.parseInt(parts[3]);
                break;
            case "LW":
            case "SW":
                rt = Enum.valueOf(Register.class, parts[1]);
                if (parts[2].contains("(")) {
                    String[] offsetAndBase = parts[2].split("[()]");
                    immediate = Integer.parseInt(offsetAndBase[0]);
                    rs = Enum.valueOf(Register.class, offsetAndBase[1]);
                } else {
                    try {
                        rs = Enum.valueOf(Register.class, parts[2]);
                        immediate = 0; // Default offset
                    } catch (IllegalArgumentException e) {
                        immediate = Integer.parseInt(parts[2]);
                        rs = Register.R0; // Default base register
                    }
                }
                break;
            case "BEQ":
            case "BNE":
                rs = Enum.valueOf(Register.class, parts[1]);
                rt = Enum.valueOf(Register.class, parts[2]);
                immediate = Integer.parseInt(parts[3]);
                address=immediate;
                break;
            case "BGEZ":
            case "BLTZ":
                rs = Enum.valueOf(Register.class, parts[1]);
                rt = Register.R0; // Default value for rt in these instructions
                immediate = Integer.parseInt(parts[2]);
                address=immediate;
                break;
            default:
                throw new IllegalArgumentException("Invalid I-type instruction: " + parts[0]);
        }
    }
    /**
     * Decodes a J-type instruction and extracts its components.
     *
     * @param parts the parts of the J-type instruction as a string array
     */
    private void decodeJType(String[] parts) {
        switch (parts[0]) {
            case "J":
                address = Integer.parseInt(parts[1]);
                break;
            case "JAL":
                address = Integer.parseInt(parts[1]);
                rd = Register.R31;
                break;
        }
    }
    /**
     * Prints the decoded instruction details to the console in a human-readable format.
     */
    public void prettyPrint() {
        System.out.println("INSTRUCTION DECODE");

        System.out.println("The current instruction is: " + instruction);
        System.out.println("PC: " + pc);
        System.out.println("RS: " + rs);
        System.out.println("RT: " + rt);
        System.out.println("RD: " + rd);
        System.out.println("Shamt: " + shamt);
        System.out.println("Funct: " + funct);
        System.out.println("Immediate: " + immediate);
        System.out.println("Jump/Branch Address: " + address);
        System.out.println("Read Data 1: " + readData1);
        System.out.println("Read Data 2: " + readData2);
        System.out.println("Sign Extended: " + signExtended);
        System.out.println("---------------------------------------------------");

    }
    /**
     * Formats the decoded instruction details as a colored string for display.
     *
     * @return a formatted string summarizing the decode stage's output with color
     */
    public String pretty() {
        return ANSI_RED + "INSTRUCTION DECODE\n" + ANSI_RESET +
                ANSI_YELLOW + "Instruction: " + ANSI_RESET + ANSI_BLUE + instruction + ANSI_RESET + "\n" +
                ANSI_YELLOW + "PC: " + ANSI_RESET + ANSI_BLUE + pc + ANSI_RESET + "\n" +
                ANSI_YELLOW + "RS: " + ANSI_RESET + ANSI_BLUE + rs + ANSI_RESET + "\n" +
                ANSI_YELLOW + "RT: " + ANSI_RESET + ANSI_BLUE + rt + ANSI_RESET + "\n" +
                ANSI_YELLOW + "RD: " + ANSI_RESET + ANSI_BLUE + rd + ANSI_RESET + "\n" +
                ANSI_YELLOW + "Shamt: " + ANSI_RESET + ANSI_BLUE + shamt + ANSI_RESET + "\n" +
                ANSI_YELLOW + "Immediate: " + ANSI_RESET + ANSI_BLUE + immediate + ANSI_RESET + "\n" +
                ANSI_YELLOW + "Jump/Branch Address: " + ANSI_RESET + ANSI_BLUE + address + ANSI_RESET + "\n" +
                ANSI_YELLOW + "Read Data 1: " + ANSI_RESET + ANSI_BLUE + readData1 + ANSI_RESET + "\n" +
                ANSI_YELLOW + "Read Data 2: " + ANSI_RESET + ANSI_BLUE + readData2 + ANSI_RESET + "\n" +
                "---------------------------------------------------\n";
    }
    /**
     * Retrieves the current IF/ID pipeline register.
     *
     * @return the IF/ID register
     */
    public IFIDRegister getIfidRegister() {
        return ifidRegister;
    }
    /**
     * Sets the IF/ID pipeline register.
     *
     * @param ifidRegister the IF/ID register to set
     */
    public void setIfidRegister(IFIDRegister ifidRegister) {
        this.ifidRegister = ifidRegister;
    }
    /**
     * Retrieves the current ID/EX pipeline register.
     *
     * @return the ID/EX register
     */
    public IDEXRegister getIdexRegister() {
        return idexRegister;
    }
    /**
     * Sets the ID/EX pipeline register.
     *
     * @param idexRegister the ID/EX register to set
     */
    public void setIdexRegister(IDEXRegister idexRegister) {
        this.idexRegister = idexRegister;
    }
    /**
     * Retrieves the current register file.
     *
     * @return the register file
     */
    public RegisterFile getRegisterFile() {
        return registerFile;
    }
    /**
     * Sets the register file.
     *
     * @param registerFile the register file to set
     */
    public void setRegisterFile(RegisterFile registerFile) {
        this.registerFile = registerFile;
    }
    /**
     * Retrieves the current control unit.
     *
     * @return the control unit
     */
    public ControlUnit getControlUnit() {
        return controlUnit;
    }
    /**
     * Sets the control unit.
     *
     * @param controlUnit the control unit to set
     */
    public void setControlUnit(ControlUnit controlUnit) {
        this.controlUnit = controlUnit;
    }
}

