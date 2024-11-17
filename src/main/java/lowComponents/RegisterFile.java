package lowComponents;

import java.util.HashMap;
import model.Register;

/**
 * This class represents the Register File of the MIPS architecture
 * It contains 32 registers, with the additional Hi and Lo registers
 * @see Register
 *
 */
public class RegisterFile {

    private HashMap<Register, Integer> registers;
    private int Hi;
    private int Lo;

    /**
     * Constructor for the RegisterFile class.
     * Initializes all registers to 0.
     */
    public RegisterFile() {
        reset();
    }

    /**
     * Resets the content of the RegisterFile to 0.
     * This method is called by the constructor.
     * @see RegisterFile#RegisterFile()
     */
    public void reset(){
        registers= new HashMap<>();
        for(Register register: Register.values()) {
            registers.put(register, 0);
        }
        Hi = 0;
        Lo = 0;
    }
    /**
     * Sets the value of a register.
     * @param register The register to be set.
     * @param value The value to be set.
     */
    public void setRegister(Register register, int value){
        if(register == Register.R0){
            registers.put(register, 0);
        }
        registers.put(register, value);
    }
    /**
     * Gets the value of a register.
     * @param register The register to be read.
     * @return int The value of the register.
     */
    public int getRegister(Register register){
        return registers.get(register);
    }
    /**
     * Sets the value of the Hi register.
     * @param value The value to be set.
     */
    public void setHi(int value) {
        Hi = value;
    }
    /**
     * Gets the value of the Hi register.
     * @return int The value of the Hi register.
     */
    public int getHi() {
        return Hi;
    }
    /**
     * Sets the value of the Lo register.
     * @param value The value to be set.
     */
    public void setLo(int value) {
        Lo = value;
    }
    /**
     * Gets the value of the Lo register.
     * @return int The value of the Lo register.
     */
    public int getLo() {
        return Lo;
    }
}
