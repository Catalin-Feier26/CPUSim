import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import lowComponents.AirthmeticLogicUnit;
public class ALUOperationsTest {
    //Values to be tested
    int a = 5;
    int b = 3;
    @Test
    public void testAdd() {
        assertEquals(AirthmeticLogicUnit.add(a, b), 8);
    }
    @Test
    public void testSub() {
        assertEquals(AirthmeticLogicUnit.sub(a, b), 2);
    }
    @Test
    public void testAnd() {
        assertEquals(AirthmeticLogicUnit.and(a, b), 1);
    }
    @Test
    public void testOr() {
        assertEquals(AirthmeticLogicUnit.or(a, b), 7);
    }
    @Test
    public void testNand() {
        assertEquals(AirthmeticLogicUnit.nand(a, b), -2);
    }
    @Test
    public void testNor() {
        assertEquals(AirthmeticLogicUnit.nor(a, b), -8);
    }
    @Test
    public void testXor() {
        assertEquals(AirthmeticLogicUnit.xor(a, b), 6);
    }
    @Test
    public void testAddWithNegative() {
        assertEquals(AirthmeticLogicUnit.add(-a, b), -2);
    }
    @Test
    public void testSubWithNegative() {
        assertEquals(AirthmeticLogicUnit.sub(-a, b), -8);
    }
    @Test
    public void testMult() {
        int[] result = AirthmeticLogicUnit.mult(a, b);
        assertEquals(0, result[0], "High bits of multiplication result should be 0");
        assertEquals(15, result[1], "Low bits of multiplication result should be 15");

        int largeA = 1_000_000;
        int largeB = 1_000_000;
        result = AirthmeticLogicUnit.mult(largeA, largeB);
        assertEquals(232, result[0], "High bits of multiplication result should match");
        assertEquals(-727379968, result[1], "Low bits of multiplication result should match");

        result = AirthmeticLogicUnit.mult(-a, b);
        assertEquals(-1, result[0], "High bits of multiplication result with negative values should match");
        assertEquals(-15, result[1], "Low bits of multiplication result with negative values should match");
    }
    @Test
    public void testDiv() {
        // Normal division
        int[] result = AirthmeticLogicUnit.div(a, b); // 5 / 3
        assertEquals(1, result[0], "Quotient should be 1");
        assertEquals(2, result[1], "Remainder should be 2");

        // Division by zero
        result = AirthmeticLogicUnit.div(a, 0);
        assertEquals(0, result[0], "Quotient should be 0 when dividing by zero");
        assertEquals(0, result[1], "Remainder should be 0 when dividing by zero");

        // Negative division
        result = AirthmeticLogicUnit.div(-a, b); // -5 / 3
        assertEquals(-1, result[0], "Quotient should be -1");
        assertEquals(-2, result[1], "Remainder should be -2");
    }
    @Test
    public void testSlt() {
        assertEquals(0, AirthmeticLogicUnit.slt(a, b), "5 is not less than 3");
        assertEquals(1, AirthmeticLogicUnit.slt(b, a), "3 is less than 5");
        assertEquals(0, AirthmeticLogicUnit.slt(a, a), "5 is not less than 5");
        assertEquals(1, AirthmeticLogicUnit.slt(-a, b), "-5 is less than 3");
    }
    @Test
    public void testSL() {
        assertEquals(40, AirthmeticLogicUnit.SL(a, b), "5 << 3 should be 40");
        assertEquals(0, AirthmeticLogicUnit.SL(0, b), "0 << 3 should be 0");
        assertEquals(5 << 31, AirthmeticLogicUnit.SL(a, 31), "5 << 31 should be valid");
    }
    @Test
    public void testSR() {
        assertEquals(0, AirthmeticLogicUnit.SR(a, b), "5 >>> 3 should be 0");
        assertEquals(0, AirthmeticLogicUnit.SR(0, b), "0 >>> 3 should be 0");
        assertEquals(536870911, AirthmeticLogicUnit.SR(-a, 3), "-5 >>> 3 should be 536870911");
    }
    @Test
    public void testSRA() {
        assertEquals(0, AirthmeticLogicUnit.SRA(a, b), "5 >> 3 should be 0");
        assertEquals(-1, AirthmeticLogicUnit.SRA(-a, 3), "-5 >> 3 should be -1");
        assertEquals(0, AirthmeticLogicUnit.SRA(0, b), "0 >> 3 should be 0");
    }



}
