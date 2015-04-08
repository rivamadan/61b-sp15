import static org.junit.Assert.*;

import org.junit.Test;

public class TestFileUtils {

    @Test
    public void testCreateFolder() {
        boolean test0 = FileUtils.createFolder("test", null);
        assertEquals(true, test0);
        boolean test1 = FileUtils.createFolder("withinFolder", "test");
        assertEquals(true, test1);
    }
    
    /* Run the unit tests in this file. */
    public static void main(String... args) {
        jh61b.junit.textui.runClasses(TestFileUtils.class);
    }
}
