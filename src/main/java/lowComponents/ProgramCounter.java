package lowComponents;

/**
 * This class represents the Program Counter of the MIPS architecture
 * It contains the current value of the Program Counter
 */
public class ProgramCounter {
    private int PC;
    private int instructionCount;
    /**
     * Constructor for the ProgramCounter class.
     * Initializes the Program Counter to 0.
     */
    public ProgramCounter(int instructionCount){
        this.instructionCount = instructionCount;
        reset();
    }
    /**
     * Resets the content of the Program Counter to 0.
     * This method is called by the constructor.
     */
    public void reset(){
        this.PC=0;
    }
    /**
     * Gets the value of the Program Counter.
     * @return int The value of the Program Counter.
     */
    public int getPC() {
        return PC;
    }
    /**
     * Sets the value of the Program Counter.
     * @param PC The value to be set.
     */
    public void setPC(int PC) {
        this.PC = PC;
    }
    /**
     * Increments the value of the Program Counter by 1.
     * Because the instructions are 4 bytes long. 1 is an int so it is 4 bytes.
     */
    public void incrementPC(){
        this.PC+=1;
    }
}
