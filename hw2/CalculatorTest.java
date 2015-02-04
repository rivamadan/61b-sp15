import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CalculatorTest {
    /* Do not change this to be private. For silly testing reasons it is public. */
    public Calculator tester;

    /**
     * setUp() performs setup work for your Calculator.  In short, we 
     * initialize the appropriate Calculator for you to work with.
     * By default, we have set up the Staff Calculator for you to test your 
     * tests.  To use your unit tests for your own implementation, comment 
     * out the StaffCalculator() line and uncomment the Calculator() line.
     **/
    @Before
    public void setUp() {
        // tester = new StaffCalculator(); 
        tester = new Calculator();
    }

    // TASK 1: WRITE JUNIT TESTS
    @Test
    public void testAddition() {
    	int result0 = tester.add(2, 3);
    	assertEquals(5, result0);

        int result13 = tester.add(2147483647, 1);
        assertEquals(-2147483648, result13);
    }
	
	@Test
    public void testAdditionWithNegative() {
    	int result1 = tester.add(5, -1);
    	assertEquals(4, result1);

    	int result2 = tester.add(-1, -1);
    	assertEquals(-2, result2);

    	int result4 = tester.add(-5, 2);
    	assertEquals(-3, result4);

    	int result5 = tester.add(3, -1);
    	assertEquals(2, result5);
    }

    @Test
    public void testAdditoinWithZeros() {
    	int result3 = tester.add(0, 0);
    	assertEquals(0, result3);

    	int result6 = tester.add(0, 2);
    	assertEquals(2, result6);

    	int result7 = tester.add(3, 0);
    	assertEquals(3, result7);
    }

    @Test
    public void testMultiply() {
        int result8 = tester.multiply(3, 4);
        assertEquals(12, result8);

        int result10 = tester.multiply(3, 3);
        assertEquals(9, result10);
    }

    @Test
    public void testMultiplyWithZeros() {
        int result9 = tester.multiply(0, 3);
        assertEquals(0, result9);

        int result13 = tester.multiply(0, 0);
        assertEquals(0, result13);
    }

    @Test
    public void testMultiplyWithNegative() {
        int result11 = tester.multiply(-1, 2);
        assertEquals(-2, result11);

        int result12 = tester.multiply(-3, -2);
        assertEquals(6, result12);
    }

    /* Run the unit tests in this file. */
    public static void main(String... args) {
        jh61b.junit.textui.runClasses(CalculatorTest.class);
    }       
}