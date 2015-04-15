import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

/**
 * Class that provides JUnit tests for Gitlet, as well as a couple of utility
 * methods.
 * 
 * @author Joseph Moghadam
 * 
 *         Some code adapted from StackOverflow:
 * 
 *         http://stackoverflow.com/questions
 *         /779519/delete-files-recursively-in-java
 * 
 *         http://stackoverflow.com/questions/326390/how-to-create-a-java-string
 *         -from-the-contents-of-a-file
 * 
 *         http://stackoverflow.com/questions/1119385/junit-test-for-system-out-
 *         println
 * 
 */
public class GitletPublicTest {
    private static final String GITLET_DIR = ".gitlet/";
    private static final String TESTING_DIR = "test_files/";

    /* matches either unix/mac or windows line separators */
    private static final String LINE_SEPARATOR = "\r\n|[\r\n]";

    /**
     * Deletes existing gitlet system, resets the folder that stores files used
     * in testing.
     * 
     * This method runs before every @Test method. This is important to enforce
     * that all tests are independent and do not interact with one another.
     */
    @Before
    public void setUp() {
        File f = new File(GITLET_DIR);
        if (f.exists()) {
            recursiveDelete(f);
        }
        f = new File(TESTING_DIR);
        if (f.exists()) {
            recursiveDelete(f);
        }
        f.mkdirs();
    }

    /**
     * Tests that init creates a .gitlet directory. Does NOT test that init
     * creates an initial commit, which is the other functionality of init.
     */
    @Test
    public void testBasicInitialize() {
        gitlet("init");
        File f = new File(GITLET_DIR);
        assertTrue(f.exists());
    }

    /**
     * Tests that checking out a file name will restore the version of the file
     * from the previous commit. Involves init, add, commit, and checkout.
     */
    @Test
    public void testBasicCheckout() {
        String wugFileName = TESTING_DIR + "wug.txt";
        String wugText = "This is a wug.";
        createFile(wugFileName, wugText);
        gitlet("init");
        gitlet("add", wugFileName);
        gitlet("commit", "added wug");
        writeFile(wugFileName, "This is not a wug.");
        gitlet("checkout", wugFileName);
        assertEquals(wugText, getText(wugFileName));
    }

    /**
     * Tests that log prints out commit messages in the right order. Involves
     * init, add, commit, and log.
     */
    @Test
    public void testBasicLog() {
        gitlet("init");
        String commitMessage1 = "initial commit";

        String wugFileName = TESTING_DIR + "wug.txt";
        String wugText = "This is a wug.";
        createFile(wugFileName, wugText);
        gitlet("add", wugFileName);
        String commitMessage2 = "added wug";
        gitlet("commit", commitMessage2);

        String logContent = gitlet("log");
        assertArrayEquals(new String[] { commitMessage2, commitMessage1 },
                extractCommitMessages(logContent));
    }
    
    @Test
    public void testStatus() {
        gitlet("init");
        gitlet("branch", "b1");
        gitlet("checkout", "b1");
        //String statusContent = gitlet("status");
        //System.out.println(statusContent);
        gitlet("checkout", "master");
        //statusContent = gitlet("status");
        //System.out.println(statusContent);

    }
    
    @Test
    public void testRemoveStaged() {
    	String wugFileName = TESTING_DIR + "wug.txt";
        createFile(wugFileName, "This is a nothing.");
    	gitlet("init");
        gitlet("add", wugFileName);
        //String statusContent = gitlet("status");
        //System.out.println(statusContent);
        gitlet("remove", wugFileName);
        //statusContent = gitlet("status");
        //System.out.println(statusContent);
    }
    
    @Test
    public void testMarkForRemoval() {
    	String wugFileName = TESTING_DIR + "wug.txt";
        createFile(wugFileName, "This is a nothing.");
        gitlet("init");
        //String output = gitlet("remove", wugFileName);
        //System.out.println(output);
        gitlet("add", wugFileName);
        gitlet("commit", "added wug");
        String testFileName = TESTING_DIR + "t1.txt";
        createFile(testFileName, "tester");
        gitlet("add", testFileName);
        gitlet("commit", testFileName);
        gitlet("rm", wugFileName);
        //String statusContent = gitlet("status");
        //System.out.println(statusContent);
        gitlet("commit", "remove wug");
        //String logContent = gitlet("log");
        //System.out.println(logContent);
        //statusContent = gitlet("status");
        //System.out.println(statusContent);

    }
    
    @Test
    public void testBranchCheckout() {
    	String wugFileName = TESTING_DIR + "wug.txt";
        createFile(wugFileName, "This is a nothing.");
        gitlet("init");
        gitlet("add", wugFileName);
        gitlet("commit", "added wug");
        writeFile(wugFileName, "This is a wug.");
        gitlet("add", wugFileName);
        gitlet("commit", "changed wug");
        gitlet("branch", "b1");
        gitlet("checkout", "b1");
        writeFile(wugFileName, "This is not a wug.");
        gitlet("add", wugFileName);
        gitlet("commit", "changed wug");
        gitlet("checkout", "master");
        assertEquals("This is a wug.", getText(wugFileName));
    }
    
    @Test
    public void testBranchCheckoutTwice() {
    	String wugFileName = TESTING_DIR + "wug.txt";
        createFile(wugFileName, "This is a nothing.");
        gitlet("init");
        gitlet("add", wugFileName);
        gitlet("commit", "added wug");
        writeFile(wugFileName, "This is a wug.");
        gitlet("add", wugFileName);
        gitlet("commit", "changed wug");
        gitlet("branch", "b1");
        gitlet("checkout", "b1");
        writeFile(wugFileName, "This is not a wug.");
        gitlet("add", wugFileName);
        gitlet("commit", "changed wug");
        gitlet("checkout", "master");
        writeFile(wugFileName, "This is definitely a wug.");
        gitlet("add", wugFileName);
        gitlet("commit", "changed wug");
        assertEquals("This is definitely a wug.", getText(wugFileName));
    }
    
    @Test
    public void testFindAndReset() {
    	String wugFileName = TESTING_DIR + "wug.txt";
        createFile(wugFileName, "This is a nothing.");
        gitlet("init");
        gitlet("add", wugFileName);
        gitlet("commit", "added wug");
        writeFile(wugFileName, "This is a wug.");
        gitlet("add", wugFileName);
        gitlet("commit", "changed wug");
        writeFile(wugFileName, "This is not a wug.");
        gitlet("add", wugFileName);
        gitlet("commit", "changed wug");
        writeFile(wugFileName, "This is definitely a wug.");
        gitlet("add", wugFileName);
        gitlet("commit", "changed wug");
        //String output = gitlet("find", "changed wug");
        //System.out.println(output);
        gitlet("reset", "2");                                                             
        assertEquals("This is a wug.", getText(wugFileName));
    }
    
    public void testCheckout2Args() {
    	String wugFileName = TESTING_DIR + "wug.txt";
        createFile(wugFileName, "This is a wug.");
        gitlet("init");
        gitlet("add", wugFileName);
        gitlet("commit", "added wug");       
        writeFile(wugFileName, "This is a BIG wug.");
        String notWugFileName = TESTING_DIR + "notwug.txt";
        createFile(notWugFileName, "This is not a wug.");
        gitlet("add", wugFileName);
        gitlet("add", notWugFileName);
        gitlet("commit", "changed wug and added not wug");
        gitlet("checkout", "1", "wug.txt");
        assertEquals("This is a wug.", getText(wugFileName));
        assertEquals("This is not a wug.", getText(notWugFileName));
    }
    
    @Test
    public void testMerge() {
        String wugFileName = TESTING_DIR + "wug.txt";
        String testFileName = TESTING_DIR + "test.txt";
        createFile(wugFileName, "This is a nothing.");
        createFile(testFileName, "nothing");
        gitlet("init");
        gitlet("add", wugFileName);
        gitlet("add", testFileName);
        gitlet("commit", "added");
        gitlet("branch", "b1");
        gitlet("checkout", "b1");
        writeFile(testFileName, "test");
        gitlet("add", testFileName);
        gitlet("commit", "changed wug");
        writeFile(testFileName, "test test");
        gitlet("add", testFileName);
        gitlet("commit", "changed wug");
        gitlet("checkout", "master");
        writeFile(wugFileName, "This is a BIG wug.");
        gitlet("add", wugFileName);
        gitlet("commit", "changed wug");
        writeFile(wugFileName, "This is a REALLY BIG wug.");
        gitlet("add", wugFileName);
        gitlet("commit", "changed wug");
        gitlet("merge", "b1");
        assertEquals("This is a REALLY BIG wug.", getText(wugFileName));
        assertEquals("test test", getText(testFileName));
    }
    
    @Test
    public void testMergeConflict() {
        String wugFileName = TESTING_DIR + "wug.txt";
        String wugConflict = TESTING_DIR + "wug.txt.conflicted";
        createFile(wugFileName, "This is a nothing.");
        gitlet("init");
        gitlet("add", wugFileName);
        gitlet("commit", "added");
        gitlet("branch", "b1");
        gitlet("checkout", "b1");
        writeFile(wugFileName, "This is a wug.");
        gitlet("add", wugFileName);
        gitlet("commit", "changed wug");
        writeFile(wugFileName, "This is not a wug.");
        gitlet("add", wugFileName);
        gitlet("commit", "changed wug");
        gitlet("checkout", "master");
        writeFile(wugFileName, "This is a BIG wug.");
        gitlet("add", wugFileName);
        gitlet("commit", "changed wug");
        writeFile(wugFileName, "This is a REALLY BIG wug.");
        gitlet("add", wugFileName);
        gitlet("commit", "changed wug");
        gitlet("merge", "b1");
        assertEquals("This is a REALLY BIG wug.", getText(wugFileName));
        assertEquals("This is not a wug.", getText(wugConflict));
    }
    
    @Test
    public void testRebase() {
        String wugFileName = TESTING_DIR + "wug.txt";
        createFile(wugFileName, "This is a nothing.");
        gitlet("init");
        gitlet("add", wugFileName);
        gitlet("commit", "1");
        gitlet("branch", "b1");
        writeFile(wugFileName, "This is a wug.");        
        gitlet("add", wugFileName);
        gitlet("commit", "2");
        writeFile(wugFileName, "This is not a wug.");
        gitlet("add", wugFileName);
        gitlet("commit", "3"); 
        gitlet("checkout", "b1");
        writeFile(wugFileName, "This is a BIG wug.");
        gitlet("add", wugFileName);
        gitlet("commit", "4");
        writeFile(wugFileName, "This is a REALLY BIG wug.");
        gitlet("add", wugFileName);
        gitlet("commit", "5");     
        gitlet("rebase", "master");
        assertEquals("This is a REALLY BIG wug.", getText(wugFileName));
        //String output = gitlet("global-log");
        //System.out.println(output);
        //output = gitlet("log");
        //System.out.println(output);
    }
    
    @Test
    public void testRebaseComplex() {
        gitlet("init");
        String PII = "yourtestFiles/PII.txt";
        String common1 = "yourtestFiles/common1.txt";
        String common2 = "yourtestFiles/common2.txt";
        String common3 = "yourtestFiles/common3.txt";
        String PI = "yourtestFiles/PI.txt";
        createFile(PII, "PII: file that's very old");
        createFile(common1, "commmon1: Apple WATCH PRICE: 20,000USD. Are you going to buy?");
        createFile(common2, "commmon2: This Summer: 61C Class sucks");
        createFile(common3, "commmon3: Should You go to MIT instead?");
        createFile(PI, "PI: file that's no one knows what it is");
        gitlet("add", common1);
        gitlet("add", common2);
        gitlet("add", common3);
        gitlet("add", PI);
        gitlet("add", PII);
        gitlet("commit", "ORIGIN");
        gitlet("branch", "F");
        gitlet("rm", PII);
        writeFile(common2, "commmon2: This Summer: 61C Class rocks!");
        gitlet("add", common2);
        gitlet("commit", "PI");
        gitlet("branch", "C");
        String GI = "yourtestFiles/GII.txt";
        createFile(GI, "GI: file that's miracle");
        gitlet("add", GI);
        gitlet("commit", "GI");
        String GII = "yourtestFiles/GII.txt";
        createFile(GII, "GII: file that's changing everything");
        writeFile(common1, "commmon1: Apple WATCH PRICE: 20,000USD. Buy the cheap one you idiot!");
        gitlet("add", common1);
        gitlet("commit", "GII");
        gitlet("branch", "D");
        gitlet("rm", common3);
        gitlet("commit", "MI");
        String MII = "yourtestFiles/MII.txt";
        createFile(MII, "MII: file that's improving over G");
        gitlet("add", MII);
        gitlet("commit", "MII");
        gitlet("add", common3);
        gitlet("commit", "MIII");
        String HI = "yourtestFiles/HI.txt";
        createFile(HI, "HI: I added a new app to this pool");
        gitlet("checkout", "D");
        gitlet("add", HI);
        writeFile(common2, "commmon2: This Summer: 61C Don't need it any more , take the OS class instead!");
        gitlet("add", common2);
        gitlet("commit", "HII");
        String DI = "yourtestFiles/DI.txt";
        createFile(DI, "DI: Revenue increased by this new feature!");
        gitlet("branch", "E");
        gitlet("add", DI);
        gitlet("rm", common2);
        gitlet("commit", "DI");
        String EI = "yourtestFiles/EI.txt";
        createFile(EI, "EI: ok we overestimated the revenue");
        writeFile(common2, "common2: Our final Vow towards merge");
        gitlet("checkout", "E");
        gitlet("add", EI);
        gitlet("add", common2);
        gitlet("commit", "EI");
        String CI = "yourtestFiles/CI.txt";
        createFile(EI, "CI: this is a garbage branch. Level 1");
        gitlet("checkout", "C");
        gitlet("add", CI);
        gitlet("commit", "CI");
        gitlet("rm", common2);
        gitlet("commit", "CII");
        gitlet("add", common2);
        gitlet("commit", "CIII");
        String CIV = "yourtestFiles/CIV.txt";
        createFile(CIV, "CIV: this is a 'garbage' branch. Level 4");
        gitlet("add", CIV);
        gitlet("commit", "CIV");
        gitlet("checkout", "F");
        gitlet("rm", PI);
        writeFile(common1, "commmon1: We go bankrupt, no Apple Watches");
        writeFile(common2, "commmon2: the department is shutting");
        writeFile(common3, "commmon3: find jobs in Microsoft now.");
        gitlet("add", common1);
        gitlet("add", common2);
        gitlet("add", common3);
        gitlet("commit", "PII");
        gitlet("branch", "A");
        String FI = "yourtestFiles/FI.txt";
        createFile(FI, "FI: Luckily we are still alive");
        gitlet("rm", PII);
        gitlet("add", FI);
        gitlet("commit", "FI");
        String QI = "yourtestFiles/QI.txt";
        createFile(QI, "QI: I am who is still living");
        gitlet("checkout", "A");
        gitlet("add", QI);
        writeFile(common2,"commmon2: a small group of people survived and we are making a new game for product!");
        gitlet("add", common2);
        gitlet("commit", "QI");
        gitlet("branch", "B");
        gitlet("rm", common2);
        gitlet("commit", "AI");
        gitlet("rm", common3);
        writeFile(common1,"commmon1: funding stage arrived! we are hiring..");
        gitlet("add", common1);
        gitlet("commit", "AII");
        String AIII = "yourtestFiles/AIII.txt";
        createFile(AIII, "AIII: sunny day!");
        gitlet("add", AIII);
        gitlet("commit", "AIII");
        gitlet("checkout", "B");
        gitlet("rm", common1);
        gitlet("commit", "BI");
        String BII = "yourtestFiles/BII.txt";
        createFile(AIII, "BII: sleeping, huh? I guess this is the end of our branch!");
        gitlet("rm", common2);
        gitlet("add", BII);
        gitlet("commit", "BII");
        gitlet("add", common1);
        gitlet("commit", "BIII");
        gitlet("checkout", "D");
        gitlet("merge", "E");
    }

    /**
     * Convenience method for calling Gitlet's main. Anything that is printed
     * out during this call to main will NOT actually be printed out, but will
     * instead be returned as a string from this method.
     * 
     * Prepares a 'yes' answer on System.in so as to automatically pass through
     * dangerous commands.
     * 
     * The '...' syntax allows you to pass in an arbitrary number of String
     * arguments, which are packaged into a String[].
     */
    private static String gitlet(String... args) {
        PrintStream originalOut = System.out;
        InputStream originalIn = System.in;
        ByteArrayOutputStream printingResults = new ByteArrayOutputStream();
        try {
            /*
             * Below we change System.out, so that when you call
             * System.out.println(), it won't print to the screen, but will
             * instead be added to the printingResults object.
             */
            System.setOut(new PrintStream(printingResults));

            /*
             * Prepares the answer "yes" on System.In, to pretend as if a user
             * will type "yes". You won't be able to take user input during this
             * time.
             */
            String answer = "yes";
            InputStream is = new ByteArrayInputStream(answer.getBytes());
            System.setIn(is);

            /* Calls the main method using the input arguments. */
            Gitlet.main(args);

        } finally {
            /*
             * Restores System.out and System.in (So you can print normally and
             * take user input normally again).
             */
            System.setOut(originalOut);
            System.setIn(originalIn);
        }
        return printingResults.toString();
    }

    /**
     * Returns the text from a standard text file (won't work with special
     * characters).
     */
    private static String getText(String fileName) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(fileName));
            return new String(encoded, StandardCharsets.UTF_8);
        } catch (IOException e) {
            return "";
        }
    }

    /**
     * Creates a new file with the given fileName and gives it the text
     * fileText.
     */
    private static void createFile(String fileName, String fileText) {
        File f = new File(fileName);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        writeFile(fileName, fileText);
    }

    /**
     * Replaces all text in the existing file with the given text.
     */
    private static void writeFile(String fileName, String fileText) {
        FileWriter fw = null;
        try {
            File f = new File(fileName);
            fw = new FileWriter(f, false);
            fw.write(fileText);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Deletes the file and all files inside it, if it is a directory.
     */
    private static void recursiveDelete(File d) {
        if (d.isDirectory()) {
            for (File f : d.listFiles()) {
                recursiveDelete(f);
            }
        }
        d.delete();
    }

    /**
     * Returns an array of commit messages associated with what log has printed
     * out.
     */
    private static String[] extractCommitMessages(String logOutput) {
        String[] logChunks = logOutput.split("====");
        int numMessages = logChunks.length - 1;
        String[] messages = new String[numMessages];
        for (int i = 0; i < numMessages; i++) {
            //System.out.println(logChunks[i + 1]);
            String[] logLines = logChunks[i + 1].split(LINE_SEPARATOR);
            messages[i] = logLines[3];
        }
        return messages;
    }
}
