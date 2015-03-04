import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Iterator;

/** ULLMapTest. You should write additional tests.
 *  @author Josh Hug
 */

public class ULLMapTest {
    @Test
    public void testBasic() {
        ULLMap<String, String> um = new ULLMap<String, String>();
        um.put("Gracias", "Dios Basado");
        assertEquals(um.get("Gracias"), "Dios Basado");

        um.put("h", "e");
        um.put("l", "l");
        um.put("o", "!");
        assertEquals(4, um.size());

        um.put("Gracias", "Danke");
        assertEquals(um.get("Gracias"), "Danke");
    }

    
    @Test
    public void testIterator() {
        ULLMap<Integer, String> um = new ULLMap<Integer, String>();
        um.put(0, "zero");
        um.put(1, "one");
        um.put(2, "two");
        Iterator<Integer> umi = um.iterator();
        assertEquals((Integer) 2, umi.next());
        assertEquals((Integer) 1, umi.next());
        assertEquals((Integer) 0, umi.next());
    }

    @Test
    public void testInvert() {
        ULLMap<Integer, String> um = new ULLMap<Integer, String>();
        um.put(0, "zero");
        um.put(1, "one");
        um.put(2, "two");
        ULLMap ium = ULLMap.invert(um);
        Iterator iumi = ium.iterator();
        assertEquals("zero", iumi.next());
        assertEquals("one", iumi.next());
        assertEquals("two", iumi.next());
    }
    

    /** Runs tests. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(ULLMapTest.class);
    }
} 