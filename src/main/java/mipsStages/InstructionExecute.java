package mipsStages;

import lowComponents.AirthmeticLogicUnit;
import model.Register;
import pipelineRegisters.IDEXRegister;
import pipelineRegisters.EXMEMRegister;

public class InstructionExecute {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE="\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";

    private IDEXRegister idexRegister;
    private EXMEMRegister exmemRegister;

    public String instruction="";

    private int ALURes;
    private boolean zero;
    private int targetAddress;
    private Register writeRegister;
    private int hi;
    private int lo;
    private int PC;
    private Register destinationRegister;
    private Register targetRegister;

    private int writeData;
    private int operand1;
    private int operand2;

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
    }
    private void fetchFromIDEXRegister(){
        this.PC=idexRegister.getPC();
        this.targetRegister=idexRegister.getRt();
        this.destinationRegister=idexRegister.getRd();
        this.targetAddress=idexRegister.getJumpAddress();
    }
    private void setOperands(){
        instruction= idexRegister.instruction;
        writeData= idexRegister.getReadData2();
        operand1=idexRegister.getReadData1();
        operand2=idexRegister.getAluSrc() ? idexRegister.getImmediate() : idexRegister.getReadData2();
        if(idexRegister.getLoSrc()){
            operand2=idexRegister.getLo();
        }
        if(idexRegister.getHiSrc()){
            operand2=idexRegister.getHi();
        }
        writeRegister= idexRegister.getRegDst() ? idexRegister.getRd() : idexRegister.getRt();
    }
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
    }
    public void reset() {
        instruction = "";
        PC = 0;

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
                return "";
            default:
                throw new IllegalArgumentException("Unsupported ALU operation: " + idexRegister.getAluOp());

        }
        zero = (aluResult == 0);
        ALURes=aluResult;

        passControls();
        return pretty();
    }
    public String getExecutionDetails(){
        return "Operand1:\t" + operand1 + "\n" +
                "Operand2:\t" + operand2 + "\n" +
                "ALUOP:\t" + idexRegister.getAluOp().name()+ "\n"+
                "Result:\t" + ALURes + "\n";
    }

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


    public EXMEMRegister getExmemRegister() {
        return exmemRegister;
    }

}