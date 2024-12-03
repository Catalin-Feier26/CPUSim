package lowComponents;

import java.util.HashMap;

/**
 * This class represents the Data Memory of the MIPS architecture
 * It contains the memory addresses and their values
 *
 */
public class DataMemory {
    private HashMap<Integer, Integer> memory;
    /**
     * Constructor for the DataMemory class.
     * Initializes the memory to an empty HashMap.
     */
    public DataMemory() {
        memory = new HashMap<>();
    }
    /**
     * Resets the content of the Data Memory to an empty HashMap.
     */
    public void clearMemory() {
        memory.clear();
    }
    /**
     * Sets the value of a memory address.
     * @param address The memory address to be set.
     * @param value The value to be set.
     */
    public void writeMemory(int address, int value) {
        memory.put(address, value);
    }

    /**
     * Gets the value of a memory address.
     * @param address
     * @return int The value of the memory address.
     * @throws IllegalArgumentException if the memory address does not exist.
     */
    public int readMemory(int address) throws IllegalArgumentException {
        if(!memory.containsKey(address))
            throw new IllegalArgumentException("Memory address does not exist");
        return memory.get(address);
    }

    /**
     * Getter for the memory.
     * @return HashMap<Integer,Integer> - The memory is represented as a map.
     */
    public HashMap<Integer,Integer> getMemory(){
        return memory;
    }
}
