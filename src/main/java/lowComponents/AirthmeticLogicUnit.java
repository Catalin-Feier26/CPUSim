package lowComponents;

/**
 * This class represents the Arithmetic Logic Unit of the MIPS architecture
 * It contains the arithmetic and logic operations of the MIPS architecture
 *
 */
public class AirthmeticLogicUnit {
    /**
     * Adds two integers
     * @param a The first operand
     * @param b The second operand
     * @return int The result of the addition
     */
    public static int add(int a, int b) {
        return a + b;
    }

    /**
     *  Subtracts two integers
     * @param a The first operand
     * @param b The second operand
     * @return int The result of the subtraction
     */
    public static int sub(int a, int b) {
        return a - b;
    }

    /**
     *  The bitwise AND operation
     * @param a The first operand
     * @param b The second operand
     * @return int The result of the AND operation
     */
    public static int and(int a, int b) {
        return a & b;
    }

    /**
     * The bitwise OR operation
     * @param a The first operand
     * @param b The second operand
     * @return int The result of the OR operation
     */
    public static int or(int a, int b) {
        return a | b;
    }
    /**
     * The bitwise NAND operation
     * @param a The operand
     * @param b The operand
     * @return int The result of the NAND operation
     */
    public static int nand(int a, int b) {
        return ~(a & b);
    }

    /**
     * The bitwise NOR operation
     * @param a The first operand
     * @param b The second operand
     * @return int The result of the NOR operation
     */
    public static int nor(int a, int b) {
        return ~(a | b);
    }

    /**
     * The bitwise XOR operation
     * @param a The first operand
     * @param b The second operand
     * @return int The result of the XOR operation
     */
    public static int xor(int a, int b) {
        return a ^ b;
    }

    /**
     * The multiplication operation between two integers
     * @param a The first operand
     * @param b The second operand
     * @return int[] The result of the multiplication the Hi and Lo registers
     */
    public static int[] mult(int a, int b) {
        int[] result = new int[2];
        long res= (long) a * b;
        result[0] = (int) (res >> 32);
        result[1] = (int) res;
        return result;
    }

    /**
     *  The division operation between two integers
     * @param a The first operand
     * @param b The second operand
     * @return int[] The result of the division the quotient and the remainder
     */
    public static int[] div(int a, int b) {
        if (b == 0){
            int[] result = new int[2];
            result[0] = 0;
            result[1] = 0;
            return result;
        }
        int[] result = new int[2];
        result[0] = a / b;
        result[1] = a % b;
        return result;
    }

    /**
     * The set less than operation
     * @param a the first operand
     * @param b the second operand
     * @return int 1 if a < b, 0 otherwise
     */
    public static int slt(int a, int b) {
        return a < b ? 1 : 0;
    }
    /**
     *  The shift left operation
     * @param a The number to be shifted
     * @param b The number of bits to shift left
     * @return int The result of the shift left operation
     */
    public static int SL(int a, int b) {
        return a << b;
    }
    /**
     * The shift right operation
     * @param a The number to be shifted
     * @param b The number of bits to shift right
     * @return int The result of the shift right operation
     */
    public static int SR(int a, int b) {
        return a >>> b;
    }
    /**
     * The shift right arithmetic operation
     * @param a The number to be shifted
     * @param b The number of bits to shift right
     * @return int The result of the shift right arithmetic operation
     */
    public static int SRA(int a, int b) {
        return a >> b;
    }

}
