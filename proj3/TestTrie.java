import static org.junit.Assert.*;
import org.junit.Test;

public class TestTrie {

    @Test
    public void test() {
        Trie t = new Trie();
        t.insert("hello");
        t.insert("hey");
        t.insert("goodbye");
        assertEquals(true,t.find("hell", false));
        assertEquals(true,t.find("hello", true));
        assertEquals(true,t.find("good", false));
        assertEquals(false,t.find("bye", false));
        assertEquals(false,t.find("heyy", false));
        assertEquals(false,t.find("hell", true));   
    }

    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestTrie.class);
    }
}