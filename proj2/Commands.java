import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.princeton.cs.introcs.In;

/* methods for gitlet's commands */

public class Commands {

    /* initializes gitlet */
    public static void initialize() {
        FileUtils.createFolder(".gitlet");
        Commit commitList = new Commit(getDateTime());
        saveCommitList(commitList);
    }
    
    public static void add(String file) throws IOException {
        String currDir = System.getProperty("user.dir");
        String separator = File.separator;
        File filesToAdd = new File(currDir, ".gitlet" + separator + "filesToAdd.txt");
        System.out.println(filesToAdd);

        if(!filesToAdd.exists()){
            filesToAdd.createNewFile();
        }

        FileWriter fw = new FileWriter(filesToAdd, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(file + "\n");
        bw.close();
    }
    

    public static void commit(String msg) throws IOException {
        Commit commitList = loadCommitList();
        commitList.addCommit(msg, getDateTime());
        String separator = File.separator;
        String commitFolder = "commit" + commitList.getCommitNum();
        FileUtils.createFolder(commitFolder);
        
        In filesToAdd = new In(".gitlet" + separator + "filesToAdd.txt");
        
        while (filesToAdd.hasNextLine()) {
            String file = filesToAdd.readLine();
            FileUtils.copyFile(file, "commit" + commitList.getCommitNum());
        }
        saveCommitList(commitList);
    }

    public static void checkout(String string) throws IOException {
        Commit commitList = loadCommitList();
        String separator = File.separator;
        FileUtils.copyFile("commit1" + separator + string, System.getProperty("user.dir"));
    }

    public static void log() {
        Commit commitList = loadCommitList();
        commitList.log();      
    }
    
    public static void branch(String name) {
        Commit commitList = loadCommitList();
        commitList.createBranch(name);
        saveCommitList(commitList);
    }

    private static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:SS");
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    private static Commit loadCommitList() {
        Commit allCommits = null;
        String separator = File.separator;
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

    private static void saveCommitList(Commit allCommits) {
        if (allCommits == null) {
            return;
        }
        try {
            String separator = File.separator;
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
    
}
