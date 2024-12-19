package pipelineRegisters;

import model.Register;

/**
 * This class represents the MEM/WB Register of the MIPS architecture
 * It contains the control signals for the datapath in the MEM/WB pipeline stage
 */
public class MEMWBRegister {
    public String instruction="";
    public int instructionIndex=-1;

    private boolean regWrite;
   private boolean memToReg;
   private boolean loWrite;
   private boolean hiWrite;
   private Register writeRegister;

   private int aluResult;
   private int hi, lo;
   private int readData;


    /**
     *  Constructor for the MEMWBRegister class.
     *  Initializes the MEM/WB Register with default values.
     *  Calls the reset method.
     */
    public MEMWBRegister() {
        reset();
    }

    /**
     *  Constructor for the MEMWBRegister class. Initializes the MEM/WB Register with the given values.
     * @param regWrite Flag indicating if the register should be written to.
     * @param memToReg Flag indicating if memory data should be written to the register.
     * @param aluResult The result from the ALU.
     * @param readData The data read from memory.
     */
    public MEMWBRegister(boolean regWrite, boolean memToReg, boolean loWrite, boolean hiWrite, int aluResult, int readData) {
        this.regWrite = regWrite;
        this.memToReg = memToReg;
        this.loWrite = loWrite;
        this.hiWrite = hiWrite;
        this.aluResult = aluResult;
        this.readData = readData;
    }

    /**
     * Resets the content of the MEM/WB Register to 0.
     * This method is called by the constructor.
     * @see MEMWBRegister#MEMWBRegister()
     */
    public void reset() {
        regWrite = false;
        memToReg = false;
        aluResult = 0;
        readData = 0;

        instruction="";
        instructionIndex=-1;
    }

    /**
     * Sets the instruction in the MEMWBRegister
     * @return String The instruction in the MEMWBRegister
     */
    public boolean getLoWrite() {
        return loWrite;
    }

    /**
     * Sets the instruction in the MEMWBRegister
     * @param loWrite The instruction in the MEMWBRegister
     */
    public void setLoWrite(boolean loWrite) {
        this.loWrite = loWrite;
    }

    /**
     * Sets the instruction in the MEMWBRegister
     * @return String The instruction in the MEMWBRegister
     */
    public boolean getHiWrite() {
        return hiWrite;
    }

    /**
     * Sets the instruction in the MEMWBRegister
     * @param hiWrite The instruction in the MEMWBRegister
     */
    public void setHiWrite(boolean hiWrite) {
        this.hiWrite = hiWrite;
    }
    /**
     * Gets the value of the RegWrite control signal.
     * @return boolean The value of the RegWrite control signal.
     */
    public boolean getRegWrite() {
        return regWrite;
    }

    /**
     * Sets the value of the RegWrite control signal.
     * @param regWrite The value to be set.
     */
    public void setRegWrite(boolean regWrite) {
        this.regWrite = regWrite;
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
     * Gets the value of the ALU result.
     * @return int The value of the ALU result.
     */
    public int getAluResult() {
        return aluResult;
    }

    /**
     * Sets the value of the ALU result.
     * @param aluResult The value to be set.
     */
    public void setAluResult(int aluResult) {
        this.aluResult = aluResult;
    }

    /**
     * Gets the value of the data read from memory.
     * @return int The value of the data read from memory.
     */
    public int getReadData() {
        return readData;
    }

    /**
     * Sets the value of the data read from memory.
     * @param readData
     */
    public void setReadData(int readData) {
        this.readData = readData;
    }

    /**
     * Sets the value of the hi register.
     * @param hi The value to be set.
     */
    public void setHi(int hi) {
        this.hi = hi;
    }

    /**
     * Gets the value of the hi register.
     * @return int The value of the hi register.
     */
    public int getHi(){
        return hi;
    }

    /**
     * Sets the value of the lo register.
     * @param lo The value to be set.
     */
    public void setLo(int lo){
        this.lo=lo;
    }

    /**
     * Gets the value of the lo register.
     * @return int The value of the lo register.
     */
    public int getLo(){
        return lo;
    }
    /**
     * Sets the register that will be written to.
     * @param writeRegister The register that will be written to.
     */
    public void setWriteRegister(Register writeRegister){
        this.writeRegister=writeRegister;
    }

    /**
     * Returns the register that will be written to.
     * @return Register The register that will be written to.
     */
    public Register getWriteRegister(){
        return  writeRegister;
    }
    /**
     * Returns a string representation of the MEMWBRegister object.
     * @return String The string representation of the MEMWBRegister object.
     */
    @Override
    public String toString() {
        return "MEMWBRegister{" +
                "regWrite=" + regWrite +
                ", memToReg=" + memToReg +
                ", aluResult=" + aluResult +
                ", readData=" + readData +
                '}';
    }
}
