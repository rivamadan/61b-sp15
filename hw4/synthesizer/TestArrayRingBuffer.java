package synthesizer;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    ArrayRingBuffer arb1;
    ArrayRingBuffer arb2;
    ArrayRingBuffer arb3;

    @Before
	public void setUp()
	{
		arb1 = new synthesizer.ArrayRingBuffer(1);
        arb2 = new synthesizer.ArrayRingBuffer(7);
        arb3 = new synthesizer.ArrayRingBuffer(4);
	}

    @Test
    public void enqueueTest() {
        arb1.enqueue(5);
    	assertEquals(false, arb1.isEmpty());
    	assertEquals(true, arb2.isEmpty());
    	assertEquals(true, arb1.isFull());
    	assertEquals(false, arb2.isFull());

    }

    @Test
    public void dequeueTest() {
    	arb2.enqueue(33.1); // 33.1     0     0
        arb2.enqueue(44.8); // 33.1  44.8     0
        arb2.enqueue(62.3); // 33.1  44.8  62.3
        assertEquals(33.1, arb2.dequeue(), 0.001); //    0  44.8  62.3
        arb2.enqueue(-3.4); // -3.4  44.8  62.3
        assertEquals(44.8, arb2.dequeue(), 0.001); // -3.4     0  62.3
        arb2.enqueue(5.6); // -3.4    5.6  62.3
        assertEquals(62.3, arb2.dequeue(), 0.001); // -3.4   5.6     0
        arb2.enqueue(1.2); // -3.4   5.6    1.2
        assertEquals(-3.4, arb2.dequeue(), 0.001); //    0   5.6   1.2
        assertEquals(5.6, arb2.dequeue(), 0.001); //    0     0   1.2
    }

    @Test
    public void peekTest() {
        arb3.enqueue(1.1);
        arb3.enqueue(2.2);
        arb3.enqueue(3.3);
        assertEquals(1.1, arb3.peek(), 0.001);
        arb3.dequeue();
        assertEquals(2.2, arb3.peek(), 0.001);
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 