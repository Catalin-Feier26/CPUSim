package mipsStages;

import lowComponents.AirthmeticLogicUnit;
import model.AluOperation;
import model.Register;
import pipelineRegisters.IDEXRegister;
import pipelineRegisters.EXMEMRegister;
/**
 * The InstructionExecute class represents the Execute stage of the MIPS pipeline.
 * It processes instructions by performing arithmetic or logic operations, calculating branch targets,
 * and passing data to the next pipeline stage.
 */
public class InstructionExecute {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE="\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";

    private IDEXRegister idexRegister;
    private EXMEMRegister exmemRegister;

    public String instruction;
    public int instructionIndex;

    private int ALURes;
    private boolean zero;
    private int targetAddress;
    private Register writeRegister;
    private int hi;
    private int lo;
    private AluOperation aluOperation;
    private int PC;
    private Register destinationRegister;
    private Register targetRegister;

    private int writeData;
    private int operand1;
    private int operand2;

    /**
     * Constructs an InstructionExecute object, initializing its state and linking pipeline registers.
     *
     * @param idexRegister the IDEX pipeline register
     * @param exmemRegister the EXMEM pipeline register
     */
    public InstructionExecute(IDEXRegister idexRegister, EXMEMRegister exmemRegister){
        this.idexRegister=idexRegister;
        this.exmemRegister=exmemRegister;
        ALURes=0;
        zero=false;
        targetAddress=0;
        writeRegister=Register.R0;
        targetRegister=Register.R0;
        destinationRegister=Register.R0;
        hi=exmemRegister.getHi();
        lo=exmemRegister.getLo();
        PC=0;
        aluOperation=AluOperation.UNSPECIFIED;
    }
    /**
     * Fetches instruction details and control signals from the IDEX pipeline register.
     */
    private void fetchFromIDEXRegister(){
        instructionIndex=idexRegister.instructionIndex;
        this.PC=idexRegister.getPC();
        this.targetRegister=idexRegister.getRt();
        this.destinationRegister=idexRegister.getRd();
        this.targetAddress=idexRegister.getJumpAddress();
    }
    /**
     * Sets the operands and control values required for ALU operations, based on the current instruction.
     */
    private void setOperands(){
        instruction= idexRegister.instruction;
        instructionIndex= idexRegister.instructionIndex;;
        writeData= idexRegister.getReadData2();
        operand1=idexRegister.getReadData1();
        aluOperation=idexRegister.getAluOp();
        operand2=idexRegister.getAluSrc() ? idexRegister.getImmediate() : idexRegister.getReadData2();
        if(idexRegister.getLoSrc()){
            operand2=idexRegister.getLo();
        }
        if(idexRegister.getHiSrc()){
            operand2=idexRegister.getHi();
        }
        writeRegister= idexRegister.getRegDst() ? idexRegister.getRd() : idexRegister.getRt();
    }
    /**
     * Passes the calculated results and control signals to the EXMEM pipeline register.
     */
    private void passControls(){
        exmemRegister.setJump(idexRegister.getJump());
        exmemRegister.setHiWrite(idexRegister.getHiWrite());
        exmemRegister.setLoWrite(idexRegister.getLoWrite());
        exmemRegister.setRegWrite(idexRegister.getRegWrite());
        exmemRegister.setMemToReg(idexRegister.getMemToReg());
        exmemRegister.setMemWrite(idexRegister.getMemWrite());
        exmemRegister.setMemRead(idexRegister.getMemRead());
        exmemRegister.setBranch(idexRegister.getBranch());

        exmemRegister.setZero(zero);
        exmemRegister.setAluResult(ALURes);
        exmemRegister.setHi(hi);
        exmemRegister.setLo(lo);
        exmemRegister.setWriteData(writeData);
        exmemRegister.setWriteRegister(writeRegister);
        exmemRegister.setTargetAddress(idexRegister.getJumpAddress());

        exmemRegister.instruction=instruction;
        exmemRegister.instructionIndex=instructionIndex;
    }
    /**
     * Resets the state of the Execute stage and clears all intermediate values.
     */
    public void reset() {
        instruction = "";
        PC = 0;
        instructionIndex=-1;
        ALURes = 0;
        zero = false;
        hi = 0;
        lo = 0;

        targetAddress = 0;
        writeRegister = Register.R0;
        targetRegister = Register.R0;
        destinationRegister = Register.R0;

        writeData = 0;
        operand1 = 0;
        operand2 = 0;
        exmemRegister.reset(); // Assuming that EXMEMRegister has a reset method
    }
    /**
     * Retrieves the current instruction being processed in the Execute stage.
     *
     * @return the current instruction as a string
     */
    public String getInstruction(){
        return instruction;
    }
    /**
     * Executes the ALU operations and other computations for the current instruction,
     * and passes the results to the next pipeline stage.
     *
     * @return a formatted string summarizing the execution stage's output
     */
    public String execute() {
        int aluResult = 0;

        setOperands();

        switch (idexRegister.getAluOp()) {
            case ADD:
                aluResult = AirthmeticLogicUnit.add(operand1, operand2);
                break;
            case SUB:
                aluResult = AirthmeticLogicUnit.sub(operand1, operand2);

                break;
            case AND:
                aluResult = AirthmeticLogicUnit.and(operand1, operand2);
                break;
            case OR:
                aluResult = AirthmeticLogicUnit.or(operand1, operand2);
                break;
            case NAND:
                aluResult = AirthmeticLogicUnit.nand(operand1, operand2);
                break;
            case NOR:
                aluResult = AirthmeticLogicUnit.nor(operand1, operand2);
                break;
            case XOR:
                aluResult = AirthmeticLogicUnit.xor(operand1, operand2);
                break;
            case SLT:
                aluResult = AirthmeticLogicUnit.slt(operand1, operand2);
                break;
            case SL:
                aluResult = AirthmeticLogicUnit.SL(operand1, operand2);
                break;
            case SR:
                aluResult = AirthmeticLogicUnit.SR(operand1, operand2);
                break;
            case SRA:
                aluResult = AirthmeticLogicUnit.SRA(operand1, operand2);
                break;
            case MULT:
                int[] multResult = AirthmeticLogicUnit.mult(operand1, operand2);
                hi = multResult[0];
                lo = multResult[1];
                break;
            case DIV:
                int[] divResult = AirthmeticLogicUnit.div(operand1, operand2);
                hi = divResult[0];
                lo = divResult[1];
                break;
            case UNSPECIFIED:
                return pretty();
            default:
                throw new IllegalArgumentException("Unsupported ALU operation: " + idexRegister.getAluOp());

        }
        zero = (aluResult == 0);
        ALURes=aluResult;

        if(!instruction.isEmpty()){
            String parts[] = instruction.toUpperCase().split(" ");
            switch (parts[0]) {
                case "BLTZ" -> zero = operand1 < 0;
                case "BGTZ" -> zero = operand1 > 0;
                case "BEQ" -> zero = operand1 == operand2;
                case "BNE" -> zero = operand1 != operand2;
                case "SLL" -> ALURes = AirthmeticLogicUnit.SL(operand1,idexRegister.getShamt());
                case "SRL" -> ALURes = AirthmeticLogicUnit.SR(operand1,idexRegister.getShamt());
                case "SRA" -> ALURes = AirthmeticLogicUnit.SRA(operand1,idexRegister.getShamt());
            }
        }
        passControls();
        return pretty();
    }
    /**
     * Retrieves detailed execution information such as operands, ALU operation, and result.
     *
     * @return a string containing the execution details
     */
    public String getExecutionDetails(){
        return "Operand1:\t" + operand1 + "\n" +
                "Operand2:\t" + operand2 + "\n" +
                "ALUOP:\t" + aluOperation.name()+ "\n"+
                "Result:\t" + ALURes + "\n";
    }
    /**
     * Returns a formatted string representing the state and outputs of the Execute stage,
     * with colored formatting for console display.
     *
     * @return a string summarizing the execution stage's outputs
     */
    public String pretty() {
        return ANSI_RED + "INSTRUCTION EXECUTE\n" + ANSI_RESET +
                ANSI_YELLOW + "Instruction: " + ANSI_RESET + ANSI_BLUE + instruction + ANSI_RESET + "\n" +
                ANSI_YELLOW + "PC: " + ANSI_RESET + ANSI_BLUE + idexRegister.getPC() + ANSI_RESET + "\n" +
                ANSI_YELLOW + "Operand1: " + ANSI_RESET + ANSI_BLUE + operand1 + ANSI_RESET + "\n" +
                ANSI_YELLOW + "Operand2: " + ANSI_RESET + ANSI_BLUE + operand2 + ANSI_RESET + "\n" +
                ANSI_YELLOW + "ALU Result: " + ANSI_RESET + ANSI_BLUE + ALURes + ANSI_RESET + "\n" +
                ANSI_YELLOW + "Zero Flag: " + ANSI_RESET + ANSI_BLUE + zero + ANSI_RESET + "\n" +
                ANSI_YELLOW + "Write Register: " + ANSI_RESET + ANSI_BLUE + writeRegister + ANSI_RESET + "\n" +
                ANSI_YELLOW + "HI: " + ANSI_RESET + ANSI_BLUE + hi + ANSI_RESET + "\n" +
                ANSI_YELLOW + "LO: " + ANSI_RESET + ANSI_BLUE + lo + ANSI_RESET + "\n" +
                "---------------------------------------------------\n";
    }
    /**
     * Retrieves the EXMEM pipeline register.
     *
     * @return the EXMEM pipeline register
     */
    public EXMEMRegister getExmemRegister() {
        return exmemRegister;
    }

}