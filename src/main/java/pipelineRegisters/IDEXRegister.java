package pipelineRegisters;

import model.AluOperation;
import model.Register;
/**
 * This class represents the IDEX Register of the MIPS architecture
 * It contains the control signals for the datapath
 *
 */
public class IDEXRegister {
    public String instruction="";

    private boolean pcSrc;
    private boolean regDst;
    private boolean branch;
    private boolean memRead;
    private boolean memToReg;
    private AluOperation aluOp;
    private boolean memWrite;
    private boolean aluSrc;
    private boolean regWrite;
    private boolean jump;
    private boolean hiWrite;
    private boolean loWrite;
    private boolean hiSrc;
    private boolean loSrc;

    private int hi;
    private int lo;
    private int readData1;
    private int readData2;
    private int signExtend;
    private Register rt;
    private Register rd;
    private int sa;
    private int PC;
    private int immediate;
    private int jumpAddress;

    /**
     * Constructor for the IDEXRegister class.
     * Initializes all control signals to false.
     */
    public IDEXRegister() {
        reset();
    }

    /**
     * Constructor for the IDEXRegister class. Initializes the IDEX Register with the given values.
     * @param pcSrc The source of the Program Counter
     * @param regDst Determines which register to write to in the Register File
     * @param branch Flag indicating if a branch operation is occurring.
     * @param memRead Flag indicating if a memory read operation is requested.
     * @param memToReg Flag indicating if memory data should be written to the register.
     * @param aluOp The operation to be performed by the ALU
     * @param memWrite Flag indicating if a memory write operation is requested.
     * @param aluSrc Flag indicating if the second ALU operand should come from the immediate field
     * @param regWrite Controls whether the register file should be written to
     * @param jump Flag indicating if a jump operation is occurring.
     * @param hiWrite Flag indicating if the HI register should be written to.
     * @param loWrite Flag indicating if the LO register should be written to.
     * @param readData1 The first read data value from the register file
     * @param readData2 The second read data value from the register file
     * @param signExtend The sign-extended immediate value
     * @param rt The target register
     * @param rd The destination register
     * @param sa The shift amount
     * @param PC The program counter value
     * @param immediate The immediate value
     * @param jumpAddress The jump address
     */
    public IDEXRegister(boolean pcSrc, boolean regDst, boolean branch, boolean memRead, boolean memToReg, AluOperation aluOp, boolean memWrite, boolean aluSrc, boolean regWrite, boolean jump, boolean hiWrite, boolean loWrite, int readData1, int readData2, int signExtend, Register rt, Register rd, int sa, int PC, int immediate, int jumpAddress) {
        this.pcSrc = pcSrc;
        this.regDst = regDst;
        this.branch = branch;
        this.memRead = memRead;
        this.memToReg = memToReg;
        this.aluOp = aluOp;
        this.memWrite = memWrite;
        this.aluSrc = aluSrc;
        this.regWrite = regWrite;
        this.jump = jump;
        this.hiWrite = hiWrite;
        this.loWrite = loWrite;
        this.readData1 = readData1;
        this.readData2 = readData2;
        this.signExtend = signExtend;

        this.rt = rt;
        this.rd = rd;
        this.sa = sa;
        this.PC = PC;
        this.immediate = immediate;
        this.jumpAddress = jumpAddress;
    }
    /**
     * Resets the content of the IDEX Register to 0.
     * This method is called by the constructor.
     * @see IDEXRegister#IDEXRegister()
     */
    public void reset(){
        pcSrc = false;
        regDst = false;
        branch = false;
        memRead = false;
        memToReg = false;
        aluOp = AluOperation.UNSPECIFIED;
        memWrite = false;
        aluSrc = false;
        regWrite = false;
        jump = false;
        hiWrite = false;
        loWrite = false;
        readData1 = 0;
        readData2 = 0;
        signExtend = 0;
        rt = Register.R0;
        rd = Register.R0;
        sa = 0;
        PC = 0;
        immediate = 0;
        jumpAddress = 0;

        hiSrc=false;
        loSrc=false;
    }

    public void setLoSrc(boolean loSrc) {
        this.loSrc = loSrc;
    }

    public void setHiSrc(boolean hiSrc) {
        this.hiSrc = hiSrc;
    }
    public boolean getHiSrc(){
        return hiSrc;
    }
    public boolean getLoSrc(){
        return  loSrc;
    }

    /**
     * Gets the value of the PC Source control signal.
     * @return boolean The value of the PC Source control signal.
     */
    public boolean getPcSrc() {
        return pcSrc;
    }
    /**
     * Sets the value of the PC Source control signal.
     * @param pcSrc The value to be set.
     */
    public void setPcSrc(boolean pcSrc) {
        this.pcSrc = pcSrc;
    }

    /**
     * Gets the value of the RegDst control signal.
     * @return boolean The value of the RegDst control signal.
     */
    public boolean getRegDst() {
        return regDst;
    }
    /**
     * Sets the value of the RegDst control signal.
     * @param regDst The value to be set.
     */
    public void setRegDst(boolean regDst) {
        this.regDst = regDst;
    }
    /**
     * Gets the value of the Branch control signal.
     * @return boolean The value of the Branch control signal.
     */
    public boolean getBranch() {
        return branch;
    }
    /**
     * Sets the value of the Branch control signal.
     * @param branch The value to be set.
     */
    public void setBranch(boolean branch) {
        this.branch = branch;
    }
    /**
     * Gets the value of the MemRead control signal.
     * @return boolean The value of the MemRead control signal.
     */
    public boolean getMemRead() {
        return memRead;
    }

    /**
     * Sets the value of the MemRead control signal.
     * @param memRead
     */
    public void setMemRead(boolean memRead) {
        this.memRead = memRead;
    }
    /**
     * Gets the value of the MemToReg control signal.
     * @return boolean The value of the MemToReg control signal.
     */
    public boolean getMemToReg() {
        return memToReg;
    }
    /**
     * Sets the value of the MemToReg control signal.
     * @param memToReg The value to be set.
     */
    public void setMemToReg(boolean memToReg) {
        this.memToReg = memToReg;
    }
    /**
     * Gets the value of the ALU Operation control signal.
     * @return AluOperation The value of the ALU Operation control signal.
     */
    public AluOperation getAluOp() {
        return aluOp;
    }

    /**
     * Sets the value of the ALU Operation control signal.
     * @param aluOp The value to be set.
     */
    public void setAluOp(AluOperation aluOp) {
        this.aluOp = aluOp;
    }
    /**
     * Gets the value of the MemWrite control signal.
     * @return boolean The value of the MemWrite control signal.
     */
    public boolean getMemWrite() {
        return memWrite;
    }
    /**
     * Sets the value of the MemWrite control signal.
     * @param memWrite The value to be set.
     */
    public void setMemWrite(boolean memWrite) {
        this.memWrite = memWrite;
    }

    /**
     * Gets the value of the ALU Source control signal.
     * @return boolean The value of the ALU Source control signal.
     */
    public boolean getAluSrc() {
        return aluSrc;
    }

    /**
     * Sets the value of the ALU Source control signal.
     * @param aluSrc
     */
    public void setAluSrc(boolean aluSrc) {
        this.aluSrc = aluSrc;
    }

    /**
     * Gets the value of the Register Write control signal.
     * @return boolean The value of the Register Write control signal.
     */
    public boolean getRegWrite() {
        return regWrite;
    }

    /**
     * Sets the value of the Register Write control signal.
     * @param regWrite  The value to be set.
     */
    public void setRegWrite(Boolean regWrite) {
        this.regWrite = regWrite;
    }

    /**
     * Gets the value of the Jump control signal.
     * @return boolean The value of the Jump control signal.
     */
    public boolean getJump() {
        return jump;
    }

    /**
     * Sets the value of the Jump control signal.
     * @param jump The value to be set.
     */
    public void setJump(Boolean jump) {
        this.jump = jump;
    }

    /**
     * Gets the value of the HiWrite control signal.
     * @return boolean The value of the HiWrite control signal.
     */
    public boolean getHiWrite() {
        return hiWrite;
    }

    /**
     * Sets the value of the HiWrite control signal.
     * @param hiWrite The value to be set.
     */
    public void setHiWrite(boolean hiWrite) {
        this.hiWrite = hiWrite;
    }

    /**
     * gets the value of the LoWrite control signal. Used for the mult, div, mtlo instructions.
     * @return boolean The value of the LoWrite control signal.
     */
    public boolean getLoWrite() {
        return loWrite;
    }

    /**
     * Sets the value of the LoWrite control signal. Used for the mult, div, mtlo instructions.
     * @param loWrite
     */
    public void setLoWrite(boolean loWrite) {
        this.loWrite = loWrite;
    }

    /**
     * Gets the value of the Read Data 1. RD1
     * @return int The value of the Read Data 1.
     */
    public int getReadData1() {
        return readData1;
    }
    /**
     * Sets the value of the Read Data 1. RD1
     * @param readData1 The value to be set.
     */
    public void setReadData1(int readData1) {
        this.readData1 = readData1;
    }

    /**
     * Gets the value of the Read Data 2. RD2
     * @return int The value of the Read Data 2.
     */
    public int getReadData2() {
        return readData2;
    }

    /**
     * Sets the value of the Read Data 2. RD2
     * @param readData2
     */
    public void setReadData2(int readData2) {
        this.readData2 = readData2;
    }

    /**
     * Gets the value of the Sign Extend.
     * @return
     */
    public int getSignExtend() {
        return signExtend;
    }
    /**
     * Sets the value of the Sign Extend.
     * @param signExtend The value to be set.
     */
    public void setSignExtend(int signExtend) {
        this.signExtend = signExtend;
    }

    /**
     * Gets the value of the Rt, where Rt is the target register.
     * @return int The value of the Rt.
     */
    public Register getRt() {
        return rt;
    }

    /**
     * Sets the value of the Rt, where Rt is the target register.
     * @param rt The value to be set.
     */
    public void setRt(Register rt) {
        this.rt = rt;
    }

    /**
     * Gets the value of the Rd, where Rd is the destination register.
     * @return int The value of the Rd.
     */
    public Register getRd() {
        return rd;
    }
    /**
     * Sets the value of the Rd.
     * @param rd The value to be set.
     */
    public void setRd(Register rd) {
        this.rd = rd;
    }

    /**
     * Gets the value of the Sa, where SA is the shift amount.
     * @return int The value of the Sa.
     */
    public int getSa() {
        return sa;
    }
    /**
     * Sets the value of the Sa.
     * @param sa The value to be set.
     */
    public void setSa(int sa) {
        this.sa = sa;
    }
    /**
     * Gets the value of the PC.
     * @return int The value of the PC.
     */
    public int getPC() {
        return PC;
    }
    /**
     * Sets the value of the PC.
     * @param PC The value to be set.
     */
    public void setPC(int PC) {
        this.PC = PC;
    }
    /**
     * Gets the value of the Immediate.
     * @return int The value of the Immediate.
     */
    public int getImmediate() {
        return immediate;
    }
    /**
     * Sets the value of the Immediate.
     * @param immediate The value to be set.
     */
    public void setImmediate(int immediate) {
        this.immediate = immediate;
    }

    public void setHi(int hi) {
        this.hi = hi;
    }

    public int getHi() {
        return hi;
    }

    public void setLo(int lo) {
        this.lo = lo;
    }

    public int getLo() {
        return lo;
    }

    /**
     * Gets the value of the Jump Address.
     * @return int The value of the Jump Address.
     */
    public int getJumpAddress() {
        return jumpAddress;
    }
    /**
     * Sets the value of the Jump Address.
     * @param jumpAddress The value to be set.
     */
    public void setJumpAddress(int jumpAddress) {
        this.jumpAddress = jumpAddress;
    }
    /**
     * Returns a string representation of the IDEX Register.
     * @return String The string representation of the IDEX Register.
     */
    @Override
    public String toString(){
        return "IDEXRegister{" +
                "pcSrc=" + pcSrc +
                ", regDst=" + regDst +
                ", branch=" + branch +
                ", memRead=" + memRead +
                ", memToReg=" + memToReg +
                ", aluOp=" + aluOp +
                ", memWrite=" + memWrite +
                ", aluSrc=" + aluSrc +
                ", regWrite=" + regWrite +
                ", jump=" + jump +
                ", hiWrite=" + hiWrite +
                ", loWrite=" + loWrite +
                ", readData1=" + readData1 +
                ", readData2=" + readData2 +
                ", signExtend=" + signExtend +
                ", rt=" + rt +
                ", rd=" + rd +
                ", sa=" + sa +
                ", PC=" + PC +
                ", immediate=" + immediate +
                ", jumpAddress=" + jumpAddress +
                "}";
    }
}
