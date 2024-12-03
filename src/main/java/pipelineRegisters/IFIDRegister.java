package pipelineRegisters;

/**
 * This class represents the IF/ID Register of the MIPS architecture
 * It contains the PC and the instruction
 *
 */
public class IFIDRegister {
    private int PC;
    private String instruction;
    public int instructionIndex;

    /**
     * Constructor for the IFIDRegister class.
     * Initializes the PC to 0 and the instruction to an empty string.
     * Calls the reset method.
     * @see IFIDRegister#reset()
     */
    public IFIDRegister(){
        reset();
    }

    /**
     * Constructor for the IFIDRegister class.
     * @param PC  The Program Counter value
     * @param instruction  The instruction field formatted as a string
     */
    public IFIDRegister(int PC, String instruction){
        this.PC = PC;
        this.instruction = instruction;
    }
    /**
     * Resets the content of the IF/ID Register to 0 and an empty string.
     */
    public void reset(){
        instructionIndex=-1;
        this.PC = 0;
        this.instruction = "";
    }

    /**
     * Gets the value of the PC.
     * @return int The value of the current PC.
     */
    public int getPC() {
        return PC;
    }

    /**
     * Sets the value of the PC.
     * @param PC
     * @throws IllegalArgumentException if the PC is negative.
     */
    public void setPC(int PC) throws IllegalArgumentException {
        if(PC < 0)
            throw new IllegalArgumentException("Invalid PC value");
        this.PC = PC;
    }
    /**
     * Gets the instruction.
     * @return String The instruction.
     */
    public String getInstruction() {
        return instruction;
    }
    /**
     * Sets the instruction.
     * @param instruction The instruction to be set.
     */
    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    /**
     * Returns a string representation of the IF/ID Register.
     * @return String The string representation of the IF/ID Register.
     */
    @Override
    public String toString() {
        return "IFIDRegister{" +
                "PC=" + PC +
                ", instruction='" + instruction + '\'' +
                '}';
    }
}
