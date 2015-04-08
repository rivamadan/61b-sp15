package gitlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/* methods for gitlet's commands */

public class Commands {
    public static Commit commitList;
	
    /*initializes gitlet*/
    public static void initialize() {
        FileUtils.createFolder(".gitlet", null);
        commitList = new Commit();
    }
    
    public static void branch() {
    	
    }
    
    private static Commit loadCommitList() {
        Commit allCommits = null;
        File commitFile = new File("allCommits.ser");
        if (commitFile.exists()) {
            try {
                FileInputStream fileIn = new FileInputStream(commitFile);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                allCommits = (Commit) objectIn.readObject();
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
            File commitFile = new File("allCommits.ser");
            FileOutputStream fileOut = new FileOutputStream(commitFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(commitFile);
        } catch (IOException e) {
            String msg = "IOException while saving.";
            System.out.println(msg);
        }
    }
}
