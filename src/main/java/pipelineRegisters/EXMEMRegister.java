package pipelineRegisters;

import model.Register;

/**
 * This class represents the EX/MEM Register of the MIPS architecture
 * It contains the control signals for the datapath in the EX/MEM pipeline stage
 */
public class EXMEMRegister {
    public String instruction="";
    public int instructionIndex=-1;

    private static final int DEFAULT_VALUE = 0;
    private static final boolean DEFAULT_FLAG = false;
    private boolean jump;
    private boolean hiWrite;
    private boolean loWrite;
    private boolean regWrite;
    private boolean memToReg;
    private boolean memWrite;
    private boolean memRead;
    private boolean branch;

    private Register writeRegister;
    private boolean zero; // This is a flag to indicate if the result of the ALU operation is zero for the branch instructions
    private int aluResult; // The result of the ALU operation
    private int writeData; // The data to be written to the register file
    private int PC;
    private int hi;
    private int lo;
    private int targetAddress;
    /**
     * Constructor for the EXMEMRegister class.
     */
    public EXMEMRegister() {
        reset();
    }

    /**
     * Constructor for the EXMEMRegister class. Initializes the EX/MEM Register with the given values.
     * @param regWrite Flag indicating if the register should be written to.
     * @param memToReg Flag indicating if memory data should be written to the register.
     * @param memWrite Flag indicating if a memory write operation is requested.
     * @param memRead Flag indicating if a memory read operation is requested.
     * @param branch Flag indicating if a branch operation is occurring.
     * @param zero Flag indicating if the ALU result is zero.
     * @param aluResult The result from the ALU.
     * @param writeData The data to be written to the register file.
     * @param PC The current value of the Program Counter.
     */

    public EXMEMRegister(boolean jump, boolean hiWrite, boolean loWrite, boolean regWrite, boolean memToReg, boolean memWrite, boolean memRead, boolean branch, boolean zero, int aluResult, int writeData, int PC, int hi, int lo, Register writeRegister, int targetAddress) {
        this.jump = jump;
        this.hiWrite = hiWrite;
        this.loWrite = loWrite;
        this.regWrite = regWrite;
        this.memToReg = memToReg;
        this.memWrite = memWrite;
        this.memRead = memRead;
        this.branch = branch;
        this.zero = zero;
        this.aluResult = aluResult;
        this.writeData = writeData;
        this.PC = PC;
        this.hi = hi;
        this.lo = lo;
        this.writeRegister=writeRegister;
        this.targetAddress=targetAddress;
    }
    /**
     * Resets the content of the EX/MEM Register to 0.
     * This method is called by the constructor.
     * @see EXMEMRegister#EXMEMRegister()
     */
    public void reset() {
        this.jump = DEFAULT_FLAG;
        this.hiWrite = DEFAULT_FLAG;
        this.loWrite = DEFAULT_FLAG;
        this.regWrite = DEFAULT_FLAG;
        this.memToReg = DEFAULT_FLAG;
        this.memWrite = DEFAULT_FLAG;
        this.memRead = DEFAULT_FLAG;
        this.branch = DEFAULT_FLAG;
        this.zero = DEFAULT_FLAG;
        this.aluResult = DEFAULT_VALUE;
        this.writeData = DEFAULT_VALUE;
        this.PC = DEFAULT_VALUE;
        this.hi = DEFAULT_VALUE;
        this.lo = DEFAULT_VALUE;
        this.writeRegister=Register.R0;
        this.targetAddress=DEFAULT_VALUE;

    }
    public int getHi() {
        return hi;
    }

    public void setHi(int hi) {
        this.hi = hi;
    }

    public int getLo() {
        return lo;
    }

    public void setLo(int lo) {
        this.lo = lo;
    }
    // Getters and Setters
    /**
     * Gets the value of the regWrite flag.
     * @return boolean The value of the regWrite flag.
     */
    public boolean getRegWrite() { return regWrite; }

    /**
     * Sets the value of the regWrite flag.
     * @param regWrite The value to be set.
     */
    public void setRegWrite(boolean regWrite) { this.regWrite = regWrite; }

    /**
     * Gets the value of the memToReg flag.
     * @return boolean The value of the memToReg flag.
     */
    public boolean getMemToReg() { return memToReg; }

    /**
     * Sets the value of the memToReg flag.
     * @param memToReg The value to be set.
     */
    public void setMemToReg(boolean memToReg) { this.memToReg = memToReg; }

    /**
     * Gets the value of the memWrite flag.
     * @return boolean The value of the memWrite flag.
     */
    public boolean getMemWrite() { return memWrite; }
    /**
     * Sets the value of the memWrite flag.
     * @param memWrite The value to be set.
     */
    public void setMemWrite(boolean memWrite) { this.memWrite = memWrite; }

    /**
     * Gets the value of the memRead flag.
     * @return boolean The value of the memRead flag.
     */
    public boolean getMemRead() { return memRead; }
    /**
     * Sets the value of the memRead flag.
     * @param memRead The value to be set.
     */
    public void setMemRead(boolean memRead) { this.memRead = memRead; }
    /**
     * Gets the value of the branch flag.
     * @return boolean The value of the branch flag.
     */
    public boolean getBranch() { return branch; }

    /**
     * Sets the value of the branch flag. Used for the branch instructions
     * @param branch
     */
    public void setBranch(boolean branch) { this.branch = branch; }

    /**
     * gets the value of the zero flag
     * @return boolean The value of the zero flag.
     */
    public boolean getZero() { return zero; }

    /**
     * Sets the value of the zero flag. Used for the branch instructions
     * @param zero The value to be set.
     */
    public void setZero(boolean zero) { this.zero = zero; }

    /**
     * Gets the value of the ALU result.
     * @return int The value of the ALU result.
     */
    public int getAluResult() { return aluResult; }

    /**
     * Sets the value of the ALU result. Used for the ALU operations
     * @param aluResult The value to be set.
     */
    public void setAluResult(int aluResult) { this.aluResult = aluResult; }

    /**
     * Gets the value of the data to be written to the register file.
     * @return int The value of the data to be written to the register file.
     */
    public int getWriteData() { return writeData; }

    /**
     * Sets the value of the data to be written to the register file.
     * @param writeData The value to be set.
     */
    public void setWriteData(int writeData) { this.writeData = writeData; }

    /**
     * Gets the value of the Program Counter.
     * @return int The value of the Program Counter.
     */
    public int getPC() { return PC; }

    /**
     * Sets the value of the Program Counter.
     * @param PC The value to be set.
     * @throws IllegalArgumentException if the PC is negative.
     */
    public void setPC(int PC) throws IllegalArgumentException {
        if(PC < 0)
            throw new IllegalArgumentException("Invalid PC value");
        this.PC = PC; }

    public boolean getJump() {
        return jump;
    }
    public void setJump(boolean jump) {
        this.jump = jump;
    }
    public boolean getHiWrite() {
        return hiWrite;
    }
    public void setHiWrite(boolean hiWrite) {
        this.hiWrite = hiWrite;
    }
    public boolean getLoWrite() {
        return loWrite;
    }
    public void setLoWrite(boolean loWrite) {
        this.loWrite = loWrite;
    }
    public void setWriteRegister(Register register){
        writeRegister=register;
    }
    public Register getWriteRegister(){
        return writeRegister;
    }
    public void setTargetAddress(int targetAddress){
        this.targetAddress=targetAddress;
    }
    public int getTargetAddress(){
        return  targetAddress;
    }
    /**
     * Returns a string representation of the EX/MEM Register.
     * @return String The string representation of the EX/MEM Register.
     */
    @Override
    public String toString() {
        return "EXMEMRegister{" +
                "regWrite=" + regWrite +
                ", memToReg=" + memToReg +
                ", memWrite=" + memWrite +
                ", memRead=" + memRead +
                ", branch=" + branch +
                ", zero=" + zero +
                ", aluResult=" + aluResult +
                ", writeData=" + writeData +
                ", PC=" + PC +
                '}';
    }
}
