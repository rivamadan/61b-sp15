import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.princeton.cs.introcs.In;

/* methods for gitlet's commands */

public class Commands {
	private static final String separator = File.separator;

	/* Initializes gitlet. */
	public static void initialize() {
		if (.exists()) {
			System.out.println("A gitlet version control system already exits in the current directory.");
		}
		createFolder(".gitlet");
		Commit commitList = new Commit(getDateTime());
		saveCommitList(commitList);
	}

	/* Creates or modified a text file to record files added with the add command. 
	 * If the file doesn't exist or the file hasn't been modified since the last commit, 
	 * it aborts and prints an error message.*/
	public static void add(String file) throws IOException {
		String currDir = System.getProperty("user.dir");
		Path addFilePath = Paths.get(currDir, file);
		if (!Files.exists(addFilePath)) {
			System.out.println("File does not exist.");
			return;
		}
		
		Commit commitList = loadCommitList();
		int prevCommit = commitList.getPrevCommitNum();
		Path oldFile = Paths.get(currDir, ".gitlet" + separator + "commit" + prevCommit + separator + file);			
		if (Files.exists(oldFile) && isContentEqual(oldFile, addFilePath)) {
			System.out.println("File has not been modified since the last commit.");
			return;
		}
		
		File filesToAdd = new File(currDir, ".gitlet" + separator
				+ "filesToAdd.txt");

		if (!filesToAdd.exists()) {
			filesToAdd.createNewFile();
		}

		FileWriter fw = new FileWriter(filesToAdd, true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(file + "\n");
		bw.close();
	}
	
	/*Checks to see if the contents of two files are the same.*/
	private static boolean isContentEqual(Path oldFile, Path newFile) throws IOException {
		byte[] prevFile = Files.readAllBytes(oldFile);
		byte[] currFile = Files.readAllBytes(newFile);
		return currFile.equals(prevFile);
	}

	/* need to add failure cases */
	public static void commit(String msg) throws IOException {
		Commit commitList = loadCommitList();
		commitList.addCommit(msg, getDateTime());
		String commitFolder = ".gitlet" + separator + "commit"
				+ commitList.getCurrCommitNum();
		createFolder(commitFolder);

		In filesToAdd = new In(".gitlet" + separator + "filesToAdd.txt");

		while (filesToAdd.hasNextLine()) {
			String file = filesToAdd.readLine();
			copyFile(
					file,
					".gitlet" + separator + "commit"
							+ commitList.getCurrCommitNum() + separator + file);
		}
		File file = new File(".gitlet" + separator + "filesToAdd.txt");
		file.delete();

		saveCommitList(commitList);
	}

	/* need to add other types of checkout */
	public static void checkout(String file) throws IOException {
		Commit commitList = loadCommitList();
		/* need to add way to get commit id for the file */
		copyFile(".gitlet" + separator + "commit1" + separator + file, file);
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
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"dd-MM-yyyy HH:mm:SS");
		Date date = new Date();
		return dateFormat.format(date);
	}

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

	/*
	 * Creates a new folder in the current directory. If the new folder already
	 * exists, it throw a runtime exception.
	 */
	public static void createFolder(String name) {
		String currDir = System.getProperty("user.dir");
		File newFolder = new File(currDir, name);
		newFolder.mkdirs();
		}
	}

}
