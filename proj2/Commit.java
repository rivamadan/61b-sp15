import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/* data structure for holding commits */

public class Commit implements Serializable {

	private Node firstCommit;
	private String currBranch;
	private HashMap<String, Node> branches = new HashMap<String, Node>();
	private int id;
	private HashMap<String, HashSet<Integer>> commitMsgs = new HashMap<String, HashSet<Integer>>();
	private HashSet<String> filesToAdd = new HashSet<String>();
	private HashSet<String> filesToRemove = new HashSet<String>();
	private static final long serialVersionUID = 1677399628323362926L;

	private class Node implements Serializable {
		private static final long serialVersionUID = -714753247872804263L;
		private Node parent, left, right;
		private String branch;
		private String message;
		private int commitNum;
		private String dateTime;
		private HashMap<String, Integer> fileAndCommit;

		public Node(Node parent, Node left, Node right, String branch,
				String message, int commitNum, String dateTime) {
			this.parent = parent;
			this.left = left;
			this.right = right;
			this.branch = branch;
			this.message = message;
			this.commitNum = commitNum;
			this.dateTime = dateTime;
		}
	}

	/* Initialize commit instance with commit 0 */
	public Commit(String dateTime) {
		Node master = new Node(null, null, null, "master", "initial commit", 0,
				dateTime);
		HashSet<Integer> commits = new HashSet<Integer>();
		commits.add(0);
		commitMsgs.put("initial commit", commits);
		firstCommit = master;
		updateBranchPointers("master", master);
		currBranch = "master";
	}

	/* Get head pointer of the current branch. */
	public Node getHeadNode() {
		return branches.get(currBranch);
	}

	/* Helper method to update what the head pointers of each branch are */
	private void updateBranchPointers(String branchName, Node newHeadNode) {
		branches.put(branchName, newHeadNode);
	}

	/* Generate commit ID */
	private int generateID() {
		id++;
		return id;
	}

	/* Returns set of files to be added */
	public HashSet<String> getFilesToAdd() {
		return filesToAdd;
	}

	/* Returns set of files marked for removal */
	public HashSet<String> getFilesToRemove() {
		return filesToRemove;
	}

	/* Returns commit ids associated with commit messages */
	public HashSet<Integer> findIDs(String msg) {
		return commitMsgs.get(msg);
	}

	/* Adds a node to the commit tree and associates the message with the commit */
	public void addCommit(String msg, String dateTime) {
		Node headNode = getHeadNode();
		int nextID = generateID();

		HashSet<Integer> commits;
		if (commitMsgs.containsKey(msg)) {
			commits = commitMsgs.get(msg);
		} else {
			commits = new HashSet<Integer>();
		}
		commits.add(nextID);
		commitMsgs.put(msg, commits);

		if (headNode.right != null) {
			headNode.right = new Node(headNode, null, null, currBranch, msg,
					nextID, dateTime);
			updateBranchPointers(currBranch, headNode.right);
		} else {
			headNode.left = new Node(headNode, null, null, currBranch, msg,
					nextID, dateTime);
			updateBranchPointers(currBranch, headNode.left);
		}
	}

	/* Updates added files to be associated with the current commit. */
	public void recordAdded(String addFile) {
		HashMap<String, Integer> inheritedFiles;
		Node currNode = getHeadNode();
		if (currNode.parent != null) {
			inheritedFiles = new HashMap<String, Integer>();
		} else {
			inheritedFiles = currNode.parent.fileAndCommit;
		}
		inheritedFiles.put(addFile, currNode.commitNum);
		currNode.fileAndCommit = inheritedFiles;
	}

	/*
	 * Returns files in the previous commit as map of all files commited and the
	 * most recent commit it was added in.
	 */
	public HashMap<String, Integer> getFileAndCommit() {
		return getHeadNode().fileAndCommit;

	}

	/* Returns previous commit id number. */
	public int getLastCommitNum() {
		return getHeadNode().commitNum;
	}

	/* Traverses through current branch and prints information for each commit */
	public void log() {
		Node curr = getHeadNode();
		while (curr != null) {
			System.out.println("====");
			System.out.println("Commit" + curr.commitNum);
			System.out.println(curr.dateTime);
			System.out.println(curr.message + "\n");
			curr = curr.parent;
		}
	}

	/* Prints information from all commit nodes */
	public void globalLog() {
		traverse(firstCommit);
	}

	/* Helper method to traverse through all commit and print information */
	private void traverse(Node node) {
		if (node != null) {
			System.out.println("====");
			System.out.println("Commit" + node.commitNum);
			System.out.println(node.dateTime);
			System.out.println(node.message + "\n");
		}
		if (node.left != null) {
			traverse(node.left);
		}
		if (node.right != null) {
			traverse(node.right);
		}
	}

	/* Returns set of branch names */
	public Set<String> getBranches() {
		return branches.keySet();
	}

	/* Returns the current branch */
	public String getCurrBranch() {
		return currBranch;
	}

	/* Creates new branch */
	public void createBranch(String name) {
		updateBranchPointers(name, getHeadNode());
		currBranch = name;
	}

	/* Changes branch */
	public void changeBranch(String name) {
		currBranch = name;
	}

}
