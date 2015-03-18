import org.junit.Test;
import static org.junit.Assert.*;

public class TestBSTMap {

    @Test
    public void testBasic() {
        BSTMap<String, String> bst = new BSTMap<String, String>();
        bst.put("3", "three");
        bst.printInOrder();
        assertEquals("three", bst.get("3"));

        bst.put("1", "one");
        bst.put("2", "two");
        bst.put("4", "four");
        bst.printInOrder();
        assertEquals(4, bst.size());

        bst.put("3", "number");
        assertEquals("number", bst.get("3"));
        bst.printInOrder();
    }

    /** Runs tests. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestBSTMap.class);
    }
}
