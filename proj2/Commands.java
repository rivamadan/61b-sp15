import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

/* methods for gitlet's commands */

public class Commands {
    private static final String separator = File.separator;

    /* Initializes gitlet only if gitlet hasn't been already initialized. */
    public static void initialize() {
        String currDir = System.getProperty("user.dir");
        File gitlet = new File(currDir, ".gitlet");
        if (gitlet.exists()) {
            System.out
                    .println("A gitlet version control system already exits in the current directory.");
            return;
        }
        gitlet.mkdir();
        Commit commitList = new Commit(getDateTime());
        saveCommitList(commitList);
    }

    /*
     * Creates or modified a text file to record files added with the add
     * command. If the file doesn't exist or the file hasn't been modified since
     * the last commit, it aborts and prints an error message.
     */
    public static void add(String file) throws IOException {
        String currDir = System.getProperty("user.dir");
        Path addFilePath = Paths.get(currDir, file);
        if (!Files.exists(addFilePath)) {
            System.out.println("File does not exist.");
            return;
        }

        Commit commitList = loadCommitList();
        int prevCommit = commitList.getPrevCommitNum();
        Path oldFile = Paths.get(currDir, ".gitlet" + separator + prevCommit + separator + file);
        if (Files.exists(oldFile) && isContentEqual(oldFile, addFilePath)) {
            System.out.println("File has not been modified since the last commit.");
            return;
        }

        HashSet<String> filesToAdd = commitList.getFilesToAdd();
        filesToAdd.add(file);
        saveCommitList(commitList);
    }

    /* Checks to see if the contents of two files are the same. */
    private static boolean isContentEqual(Path oldFile, Path newFile) throws IOException {
        byte[] prevFile = Files.readAllBytes(oldFile);
        byte[] currFile = Files.readAllBytes(newFile);
        return currFile.equals(prevFile);
    }

    /*
     * Adds commit to the commit tree structure and creates a folder of the
     * commit number with copies of the added files.
     */
    public static void commit(String msg) throws IOException {
        Commit commitList = loadCommitList();
        commitList.addCommit(msg, getDateTime());

        String currDir = System.getProperty("user.dir");
        File commitFolder = new File(currDir, ".gitlet" + separator + commitList.getCurrCommitNum());
        commitFolder.mkdir();

        HashSet<String> filesToAdd = commitList.getFilesToAdd();
        if (filesToAdd.isEmpty()) {
            System.out.println(" No changes added to the commit.");
            return;
        }

        for (String addFile : filesToAdd) {
            commitList.recordAdded(addFile);
            copyFile(addFile, ".gitlet" + separator + commitList.getCurrCommitNum() + separator
                    + addFile);
        }
        filesToAdd.clear();
        saveCommitList(commitList);
    }

    /* Mark file for removal or unstage it if it is staged. */
    public static void remove(String file) {
        Commit commitList = loadCommitList();
        HashSet<String> filesToAdd = commitList.getFilesToAdd();
        HashMap<String, Integer> fileCommitAssoc = commitList.getFileAndCommit();
        if (filesToAdd.contains(file)) {
            filesToAdd.remove(file);
        }
        else if (fileCommitAssoc.containsKey(file)) {
            fileCommitAssoc.remove(file);
        } else {
            System.out.println("No reason to remove the file.");
        }
        saveCommitList(commitList);
    }

    /*
     * Displays information about each commit only on the current branch,
     * beginning with the most recent and going backwards.
     */
    public static void log() {
        Commit commitList = loadCommitList();
        commitList.log();
    }

    /* Displays information about all commits ever made. */
    public static void globalLog() {
        Commit commitList = loadCommitList();
        commitList.globalLog();
    }

    /* need to add other types of checkout */
    public static void checkout(String file) throws IOException {
        Commit commitList = loadCommitList();
        /* need to add way to get commit id for the file */
        copyFile(".gitlet" + separator + "1" + separator + file, file);
    }

    /* Create a new branch */
    public static void branch(String name) {
        Commit commitList = loadCommitList();
        commitList.createBranch(name);
        saveCommitList(commitList);
    }

    /* Helper method to get current date and time */
    private static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:SS");
        Date date = new Date();
        return dateFormat.format(date);
    }

    /* Helper method to load commit serializable. */
    private static Commit loadCommitList() {
        Commit allCommits = null;
        File commitFile = new File(".gitlet" + separator + "allCommits.ser");
        if (commitFile.exists()) {
            try {
                FileInputStream fileIn = new FileInputStream(commitFile);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                allCommits = (Commit) objectIn.readObject();
                objectIn.close();
            } catch (IOException e) {
                String msg = "IOException while loading.";
                System.out.println(msg);
            } catch (ClassNotFoundException e) {
                String msg = "ClassNotFoundException while loading.";
                System.out.println(msg);
            }
        }
        return allCommits;
    }

    /* Helper method to save commit serializable. */
    private static void saveCommitList(Commit allCommits) {
        if (allCommits == null) {
            return;
        }
        try {
            File commitFile = new File(".gitlet" + separator + "allCommits.ser");
            FileOutputStream fileOut = new FileOutputStream(commitFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(allCommits);
            objectOut.close();
        } catch (IOException e) {
            String msg = "IOException while saving.";
            System.out.println(msg);
        }
    }

    /*
     * Copies the file from it's source location relative to .gitlet to the
     * destination folder
     */
    public static void copyFile(String src, String dst) throws IOException {
        String currDirectory = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        Path fileToCopy = Paths.get(currDirectory + separator + src);
        Path destination = Paths.get(currDirectory + separator + dst);
        File dest = new File(dst);
        dest = new File(dest.getParent());
        if (!dest.exists()) {
            dest.mkdirs();
        }
        Files.copy(fileToCopy, destination, StandardCopyOption.REPLACE_EXISTING);
    }

}
