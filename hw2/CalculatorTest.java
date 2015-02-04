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

    /* Run the unit tests in this file. */
    public static void main(String... args) {
        jh61b.junit.textui.runClasses(CalculatorTest.class);
    }       
}