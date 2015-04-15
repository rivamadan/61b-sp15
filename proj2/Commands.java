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
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

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

		} else {
			gitlet.mkdir();
			Commit commitList = new Commit(getDateTime());
			saveCommitList(commitList);
		}
	}

	/*
	 * Creates or modified a text file to record files added with the add
	 * command. If the file doesn't exist or the file hasn't been modified since
	 * the last commit, it aborts and prints an error message.
	 */
	public static void add(String file) {
		String currDir = System.getProperty("user.dir");
		Path addFilePath = Paths.get(currDir, file);
		if (!Files.exists(addFilePath)) {
			System.out.println("File does not exist.");
			return;
		}

		Commit commitList = loadCommitList();
		int prevCommit = commitList.getLastCommitNum();
		Path oldFile = Paths.get(currDir, ".gitlet" + separator + prevCommit
				+ separator + file);
		if (Files.exists(oldFile) && isContentEqual(oldFile, addFilePath)) {
			System.out
					.println("File has not been modified since the last commit.");
			return;
		}

		HashSet<String> filesToAdd = commitList.getFilesToAdd();
		filesToAdd.add(file);
		saveCommitList(commitList);
	}

	/*
	 * Adds commit to the commit tree structure and creates a folder of the
	 * commit number with copies of the added files. Removes files marked for
	 * removal.
	 */
	public static void commit(String msg) {
		Commit commitList = loadCommitList();
		HashSet<String> filesToRemove = commitList.getFilesToRemove();
		HashSet<String> filesToAdd = commitList.getFilesToAdd();
		if (filesToAdd.isEmpty() && filesToRemove.isEmpty()) {
			System.out.println(" No changes added to the commit.");
			return;
		}

		commitList.addCommit(msg, getDateTime());

		String currDir = System.getProperty("user.dir");
		File commitFolder = new File(currDir, ".gitlet" + separator
				+ commitList.getLastCommitNum());
		commitFolder.mkdir();

		HashMap<String, Integer> fileCommitAssoc = commitList
				.getCommitedFilesBranch(commitList.getCurrBranch());
		for (String removeFile : filesToRemove) {
			fileCommitAssoc.remove(removeFile);
		}
		filesToRemove.clear();

		for (String addFile : filesToAdd) {
			commitList.updateFileAndCommits(addFile);
			copyFile(addFile,
					".gitlet" + separator + commitList.getLastCommitNum()
							+ separator + addFile);

			filesToAdd.clear();
		}

		saveCommitList(commitList);
	}

	/* Mark file for removal or unstage it if it is staged. NOT WORKING */
	public static void remove(String file) {
		Commit commitList = loadCommitList();
		HashSet<String> filesToAdd = commitList.getFilesToAdd();
		HashMap<String, Integer> fileCommitAssoc = commitList
				.getCommitedFilesBranch(commitList.getCurrBranch());
		HashSet<String> filesToRemove = commitList.getFilesToRemove();
		if (filesToAdd.contains(file)) {
			filesToAdd.remove(file);
			saveCommitList(commitList);
		} else if (fileCommitAssoc.containsKey(file)) {
			filesToRemove.add(file);
			saveCommitList(commitList);
		} else {
			System.out.println("No reason to remove the file.");
		}
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

	/* Displays the IDs of the commits that have the given message */
	public static void find(String msg) {
		Commit commitList = loadCommitList();
		HashSet<Integer> commitIDs = commitList.findIDs(msg);
		if (commitIDs == null) {
			System.out.println("Found no commit with that message.");
		} else {
			for (Integer eachID : commitIDs) {
				System.out.println(eachID);
			}
		}
	}

	/*
	 * Displays branches with current branch indicated by *, staged files, and
	 * files marked for removal
	 */
	public static void status() {
		Commit commitList = loadCommitList();
		Set<String> branches = commitList.getBranches();
		HashSet<String> filesToAdd = commitList.getFilesToAdd();
		HashSet<String> filesToRemove = commitList.getFilesToRemove();

		System.out.println("=== Branches ===");
		for (String branch : branches) {
			String currBranch = commitList.getCurrBranch();
			if (branch.equals(currBranch)) {
				System.out.print("*");
			}
			System.out.println(branch);
		}
		System.out.println();

		System.out.println("=== Staged Files ===");
		for (String addFile : filesToAdd) {
			System.out.println(addFile);
		}
		System.out.println();

		System.out.println("=== Files Marked for Removal ===");
		for (String removeFile : filesToRemove) {
			System.out.println(removeFile);
		}
	}

	/*
	 * Checkout with one argument: determines whether checkout should restore a
	 * file or branch.
	 */
	public static void checkoutOne(String arg) {
		Commit commitList = loadCommitList();
		if (commitList.getBranches().contains(arg)) {
			checkoutBranch(arg);
		} else if (commitList
				.getCommitedFilesBranch(commitList.getCurrBranch())
				.containsKey(arg)) {
			checkoutFile(arg);
		} else {
			System.out
					.println("File does not exist in the most recent commit, or no such branch exists.");
		}
	}

	/*
	 * Restores all files in the working directory to their versions in the
	 * commit at the head of the given branch.
	 */
	private static void checkoutBranch(String branch) {
		Commit commitList = loadCommitList();
		if (branch == commitList.getCurrBranch()) {
			System.out.println("No need to checkout the current branch.");
		} else {
			HashMap<String, Integer> fileCommitAssoc = commitList
					.getCommitedFilesBranch(branch);
			for (String file : fileCommitAssoc.keySet()) {
				Integer commitFolder = fileCommitAssoc.get(file);
				copyFile(".gitlet" + separator + commitFolder + separator
						+ file, file);
			}
			commitList.changeBranch(branch);
			saveCommitList(commitList);
		}
	}

	/*
	 * Restores the given file in the working directory to its state at the
	 * commit at the head of the current branch.
	 */
	private static void checkoutFile(String file) {
		Commit commitList = loadCommitList();
		HashMap<String, Integer> fileCommitAssoc = commitList
				.getCommitedFilesBranch(commitList.getCurrBranch());
		Integer commitFolder = fileCommitAssoc.get(file);
		copyFile(".gitlet" + separator + commitFolder + separator + file, file);
	}

	/*
	 * Checkout with two arguments restores the given file in the working
	 * directory to its state at the given commit.
	 */
	public static void checkoutTwo(String commitID, String file) {
		Commit commitList = loadCommitList();
		HashMap<String, Integer> fileCommitAssoc = commitList
				.getCommitedFilesID(Integer.parseInt(commitID));
		Integer commitFolder = fileCommitAssoc.get(file);
		if (commitFolder == null) {
			System.out.println("File does not exist in that commit.");
		}
		copyFile(".gitlet" + separator + commitFolder + separator + file, file);

	}

	/* Create a new branch */
	public static void branch(String name) {
		Commit commitList = loadCommitList();
		commitList.createBranch(name);
		saveCommitList(commitList);
	}

	public static void removeBranch(String name) {
		Commit commitList = loadCommitList();
		if (!commitList.getBranches().contains(name)) {
			System.out.println("A branch with that name does not exist.");
		} else if (name == commitList.getCurrBranch()) {
			System.out.println("Cannot remove the current branch.");
		} else {
			commitList.removeBranch(name);
		}
		saveCommitList(commitList);
	}

	public static void reset(String commitID) {
		Commit commitList = loadCommitList();
		HashMap<String, Integer> fileCommitAssoc = commitList
				.getCommitedFilesID(Integer.parseInt(commitID));
		for (String file : fileCommitAssoc.keySet()) {
			Integer commitFolder = fileCommitAssoc.get(file);
			copyFile(".gitlet" + separator + commitFolder + separator + file,
					file);
		}
		commitList.changeBranchPointer(Integer.parseInt(commitID),
				commitList.getCurrBranch());
		saveCommitList(commitList);
	}

	public static void merge(String branch) {
		Commit commitList = loadCommitList();
		if (!commitList.getBranches().contains(branch)) {
			System.out.println("A branch with that name does not exist.");
		} else if (branch == commitList.getCurrBranch()) {
			System.out.println("Cannot merge a branch with itself.");
		} else {
			HashMap<String, Integer> splitPointFiles = commitList
					.getSplitPointFiles(branch);
			HashMap<String, Integer> currFiles = commitList
					.getCommitedFilesBranch(commitList.getCurrBranch());
			HashMap<String, Integer> branchFiles = commitList
					.getCommitedFilesBranch(branch);
			for (String file : branchFiles.keySet()) {
				int branchID = branchFiles.get(file);
				if (!currFiles.containsKey(file)) {
					copyFile(".gitlet" + separator + branchID + separator
							+ file, file);
				} else {
					int currID = currFiles.get(file);
					int splitID = splitPointFiles.get(file);
					if (branchID != splitID && currID != splitID) {
						copyFile(".gitlet" + separator + branchID + separator
								+ file + ".conflicted", file);
					}
					if (branchID != splitID && currID == splitID) {
						copyFile(".gitlet" + separator + branchID + separator
								+ file, file);
					}
				}
			}
		}
	}

	public static void rebase(String branch) {
		Commit commitList = loadCommitList();
		if (!commitList.getBranches().contains(branch)) {
			System.out.println("A branch with that name does not exist.");
		} else if (branch == commitList.getCurrBranch()) {
			System.out.println("Cannot rebase a branch onto itself.");
		} else {
			TreeMap<Integer, String> commitsToMove = commitList
					.getRebaseCommits(branch);
			if (commitsToMove.isEmpty()) {
				System.out.println("Already up-to-date.");
				return;
			}
			for (Integer eachCommit : commitsToMove.keySet()) {
				commitList.addCommit(commitsToMove.get(eachCommit),
						getDateTime());

				String currDir = System.getProperty("user.dir");
				File commitFolder = new File(currDir, ".gitlet" + separator
						+ commitList.getLastCommitNum());
				commitFolder.mkdir();

				HashMap<String, Integer> fileCommitAssoc = commitList
						.getCommitedFilesID(eachCommit);
				for (String file : fileCommitAssoc.keySet()) {
					Integer folder = fileCommitAssoc.get(file);
					copyFile(
							".gitlet" + separator + folder + separator + file,
							".gitlet" + separator
									+ commitList.getLastCommitNum() + separator
									+ file);
				}
				commitList.changeBranchPointer(eachCommit, branch);
			}
			checkoutBranch(branch);
			saveCommitList(commitList);
		}
	}

	public static void irebase(String branch) {
		Commit commitList = loadCommitList();
		if (!commitList.getBranches().contains(branch)) {
			System.out.println("A branch with that name does not exist.");
		} else if (branch == commitList.getCurrBranch()) {
			System.out.println("Cannot rebase a branch onto itself.");
		} else {
			TreeMap<Integer, String> commitsToMove = commitList
					.getRebaseCommits(branch);
			if (commitsToMove.isEmpty()) {
				System.out.println("Already up-to-date.");
				return;
			}
			Scanner keyboard = new Scanner(System.in);
			for (Integer eachCommit : commitsToMove.keySet()) {
				System.out.println("Currently replaying:");
				commitList.printCommit(eachCommit);

				String answer = keyboard.next();
				System.out
						.println("Would you like to (c)ontinue, (s)kip this commit, or change this commit's (m)essage?");
				String message;
				if (answer.equals("s")) {
					if () {
						
					}
					continue;
				}
				if (answer.equals("m")) {
					System.out
							.println("Please enter a new message for this commit.");
					message = keyboard.nextLine();
				} else {
					message = commitsToMove.get(eachCommit);
				}

				commitList.addCommit(message, getDateTime());

				String currDir = System.getProperty("user.dir");
				File commitFolder = new File(currDir, ".gitlet" + separator
						+ commitList.getLastCommitNum());
				commitFolder.mkdir();

				HashMap<String, Integer> fileCommitAssoc = commitList
						.getCommitedFilesID(eachCommit);
				for (String file : fileCommitAssoc.keySet()) {
					Integer folder = fileCommitAssoc.get(file);
					copyFile(
							".gitlet" + separator + folder + separator + file,
							".gitlet" + separator
									+ commitList.getLastCommitNum() + separator
									+ file);
				}
				commitList.changeBranchPointer(eachCommit, branch);
			}
			checkoutBranch(branch);
			saveCommitList(commitList);
			keyboard.close();
		}
	}

	/********************** HELPER METHODS *********************/

	/* Checks to see if the contents of two files are the same. */
	private static boolean isContentEqual(Path oldFile, Path newFile) {
		byte[] prevFile = null;
		byte[] currFile = null;
		try {
			prevFile = Files.readAllBytes(oldFile);
			currFile = Files.readAllBytes(newFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return currFile.equals(prevFile);
	}

	/* Helper method to get current date and time */
	private static String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"dd-MM-yyyy HH:mm:SS");
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
	public static void copyFile(String src, String dst) {
		String currDirectory = System.getProperty("user.dir");
		String separator = System.getProperty("file.separator");
		Path fileToCopy = Paths.get(currDirectory + separator + src);
		Path destination = Paths.get(currDirectory + separator + dst);
		File dest = new File(dst);
		dest = new File(dest.getParent());
		if (!dest.exists()) {
			dest.mkdirs();
		}
		try {
			Files.copy(fileToCopy, destination,
					StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
