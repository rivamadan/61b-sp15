import static org.junit.Assert.*;
import org.junit.Test;

public class IntListTest {

    /** Example test that verifies correctness of the IntList.list static 
     *  method. The main point of this is to convince you that 
     *  assertEquals knows how to handle IntLists just fine.
     */

    @Test 
    public void testList() {
        IntList one = new IntList(1, null);
        IntList twoOne = new IntList(2, one);
        IntList threeTwoOne = new IntList(3, twoOne);

        IntList x = IntList.list(3, 2, 1);
        assertEquals(threeTwoOne, x);
    }

    @Test
    public void testdSquareList() {
      IntList L = IntList.list(1, 2, 3);
      IntList.dSquareList(L);
      assertEquals(IntList.list(1, 4, 9), L);
    }

    /** Do not use the new keyword in your tests. You can create
     *  lists using the handy IntList.list method.  
     * 
     *  Make sure to include test cases involving lists of various sizes
     *  on both sides of the operation. That includes the empty list, which
     *  can be instantiated, for example, with 
     *  IntList empty = IntList.list(). 
     *
     *  Keep in mind that dcatenate(A, B) is NOT required to leave A untouched.
     *  Anything can happen to A. 
     */

    //TODO:  Create testSquareListRecursive()
    @Test
    public void testSquareListRecursive() {
	IntList L2 = IntList.list(1, 2, 3);
	assertEquals(IntList.list(1, 4, 9), IntList.squareListRecursive(L2));
	assertEquals(IntList.list(1, 2, 3), L2);
    }

    //TODO:  Create testDcatenate and testCatenate
    @Test
    public void testDcatenate() {
	IntList L3 = IntList.list(1, 2, 3);
	IntList L4 = IntList.list(4, 5, 6);
	IntList.dcatenate(L3, L4);
	assertEquals(IntList.list(1, 2, 3, 4, 5, 6), L3);

	IntList L10 = IntList.list(7, 8);
	IntList.dcatenate(null, L10);
	assertEquals(IntList.list(7, 8), L10);
	IntList.dcatenate(L10, null);
	assertEquals(IntList.list(7, 8), L10);

	IntList L11 = IntList.list(1, 2);
	IntList L12 = IntList.list(3, 4, 5, 6);
	IntList.dcatenate(L11, L12);
	assertEquals(IntList.list(1, 2, 3, 4, 5, 6), L11);
    }
    
    @Test
    public void testCatenate() {
	IntList L7 = IntList.list(7, 8);
	assertEquals(IntList.list(7, 8), IntList.catenate(L7, null));
	assertEquals(IntList.list(7, 8), IntList.catenate(null, L7));
	assertEquals(IntList.list(7, 8), L7);

	IntList L5 = IntList.list(1, 2, 3);
	IntList L6 = IntList.list(4, 5, 6);
	assertEquals(IntList.list(1, 2, 3, 4, 5, 6), IntList.catenate(L5, L6));
	assertEquals(IntList.list(1, 2, 3), L5);
	assertEquals(IntList.list(4, 5, 6), L6);
	
	IntList L8 = IntList.list(1, 2);
	IntList L9 = IntList.list(3, 4, 5, 6);
	assertEquals(IntList.list(1, 2, 3, 4, 5, 6), IntList.catenate(L8, L9));
	assertEquals(IntList.list(1, 2), L8);
	assertEquals(IntList.list(3, 4, 5, 6), L9);
    }
    
    /* Run the unit tests in this file. */
    public static void main(String... args) {
        jh61b.junit.textui.runClasses(IntListTest.class);
    }       
}   
