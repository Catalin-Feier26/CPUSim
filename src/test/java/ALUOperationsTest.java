import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import lowComponents.AirthmeticLogicUnit;

public class ALUOperationsTest {
    // Values to be tested
    int a = 5;
    int b = 3;

    @Test
    public void testAdd() {
        assertEquals(8, AirthmeticLogicUnit.add(a, b));
        assertEquals(Integer.MAX_VALUE, AirthmeticLogicUnit.add(Integer.MAX_VALUE, 0), "Adding 0 to MAX_VALUE");
        assertEquals(Integer.MIN_VALUE, AirthmeticLogicUnit.add(Integer.MIN_VALUE, 0), "Adding 0 to MIN_VALUE");
        assertEquals(-2, AirthmeticLogicUnit.add(-a, b));
    }

    @Test
    public void testSub() {
        assertEquals(2, AirthmeticLogicUnit.sub(a, b));
        assertEquals(Integer.MAX_VALUE, AirthmeticLogicUnit.sub(Integer.MAX_VALUE, 0), "Subtracting 0 from MAX_VALUE");
        assertEquals(-8, AirthmeticLogicUnit.sub(-a, b));
    }

    @Test
    public void testAnd() {
        assertEquals(1, AirthmeticLogicUnit.and(a, b));
        assertEquals(0, AirthmeticLogicUnit.and(a, 0), "AND with zero");
        assertEquals(a, AirthmeticLogicUnit.and(a, Integer.MAX_VALUE), "AND with MAX_VALUE");
    }

    @Test
    public void testOr() {
        assertEquals(7, AirthmeticLogicUnit.or(a, b));
        assertEquals(a, AirthmeticLogicUnit.or(a, 0), "OR with zero");
        assertEquals(Integer.MAX_VALUE, AirthmeticLogicUnit.or(a, Integer.MAX_VALUE), "OR with MAX_VALUE");
    }

    @Test
    public void testNand() {
        assertEquals(-2, AirthmeticLogicUnit.nand(a, b));
        assertEquals(-1, AirthmeticLogicUnit.nand(a, 0), "NAND with zero");
    }

    @Test
    public void testNor() {
        assertEquals(-8, AirthmeticLogicUnit.nor(a, b));
        assertEquals(-6, AirthmeticLogicUnit.nor(a, 1), "NOR with 1");
    }

    @Test
    public void testXor() {
        assertEquals(6, AirthmeticLogicUnit.xor(a, b));
        assertEquals(a, AirthmeticLogicUnit.xor(a, 0), "XOR with zero");
        assertEquals(-a - 1, AirthmeticLogicUnit.xor(a, -1), "XOR with -1");
    }

    @Test
    public void testMult() {
        int[] result = AirthmeticLogicUnit.mult(a, b);
        assertEquals(0, result[0], "High bits of multiplication result should be 0");
        assertEquals(15, result[1], "Low bits of multiplication result should be 15");

        result = AirthmeticLogicUnit.mult(Integer.MAX_VALUE, 2);
        assertEquals(0, result[0], "High bits of multiplication with MAX_VALUE and 2");

        // Test with negative numbers
        result = AirthmeticLogicUnit.mult(-a, b);
        assertEquals(-1, result[0], "High bits of multiplication result with negative values should be -1");
        assertEquals(-15, result[1], "Low bits of multiplication result with negative values should be -15");

        result = AirthmeticLogicUnit.mult(a, -b);
        assertEquals(-1, result[0], "High bits of multiplication result with negative second operand should be -1");
        assertEquals(-15, result[1], "Low bits of multiplication result with negative second operand should be -15");

        result = AirthmeticLogicUnit.mult(-a, -b);
        assertEquals(0, result[0], "High bits of multiplication result with both operands negative should be 0");
        assertEquals(15, result[1], "Low bits of multiplication result with both operands negative should be 15");
    }

    @Test
    public void testDiv() {
        int[] result = AirthmeticLogicUnit.div(a, b);
        assertEquals(1, result[0], "Quotient should be 1");
        assertEquals(2, result[1], "Remainder should be 2");

        result = AirthmeticLogicUnit.div(Integer.MAX_VALUE, 1);
        assertEquals(Integer.MAX_VALUE, result[0], "Quotient should be MAX_VALUE when dividing MAX_VALUE by 1");

        // Test with negative numbers
        result = AirthmeticLogicUnit.div(-a, b);
        assertEquals(-1, result[0], "Quotient should be -1 when dividing negative numerator by positive denominator");
        assertEquals(-2, result[1], "Remainder should be -2 when dividing negative numerator by positive denominator");

        result = AirthmeticLogicUnit.div(a, -b);
        assertEquals(-1, result[0], "Quotient should be -1 when dividing positive numerator by negative denominator");
        assertEquals(2, result[1], "Remainder should be 2 when dividing positive numerator by negative denominator");

        result = AirthmeticLogicUnit.div(-a, -b);
        assertEquals(1, result[0], "Quotient should be 1 when dividing negative numerator by negative denominator");
        assertEquals(-2, result[1], "Remainder should be -2 when dividing negative numerator by negative denominator");
    }


    @Test
    public void testSlt() {
        assertEquals(0, AirthmeticLogicUnit.slt(a, b), "5 is not less than 3");
        assertEquals(1, AirthmeticLogicUnit.slt(Integer.MIN_VALUE, 0), "MIN_VALUE is less than 0");
    }

    @Test
    public void testSL() {
        assertEquals(40, AirthmeticLogicUnit.SL(a, b));
        assertEquals(0, AirthmeticLogicUnit.SL(0, b), "0 << 3 should be 0");
        assertEquals(0, AirthmeticLogicUnit.SL(a, 32), "Shift beyond 31 bits should be 0");
    }

    @Test
    public void testSR() {
        assertEquals(0, AirthmeticLogicUnit.SR(a, b));
        assertEquals(Integer.MAX_VALUE >>> 1, AirthmeticLogicUnit.SR(Integer.MAX_VALUE, 1), "MAX_VALUE >>> 1");
    }

    @Test
    public void testSRA() {
        assertEquals(0, AirthmeticLogicUnit.SRA(a, b));
        assertEquals(-1, AirthmeticLogicUnit.SRA(-a, 3), "-5 >> 3 should be -1");
    }
}
