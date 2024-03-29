import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

/* data structure for holding commits */

public class Commit implements Serializable {

    private Node firstCommit;
    private String currBranch;
    private int id;
    private HashMap<String, Node> branches = new HashMap<String, Node>();
    private HashMap<Integer, Node> commitIDs = new HashMap<Integer, Node>();
    private HashMap<String, HashSet<Integer>> commitMsgs = new HashMap<String, HashSet<Integer>>();
    private HashSet<String> filesToAdd = new HashSet<String>();
    private HashSet<String> filesToRemove = new HashSet<String>();
    private static final long serialVersionUID = 1677399628323362926L;

    private class Node implements Serializable {
        private static final long serialVersionUID = -714753247872804263L;
        private Node parent, left, right;
        private String message;
        private int commitNum;
        private String dateTime;
        private HashMap<String, Integer> commitedFiles = new HashMap<String, Integer>();

        public Node(Node parent, Node left, Node right, String message, int commitNum,
                String dateTime) {
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.message = message;
            this.commitNum = commitNum;
            this.dateTime = dateTime;
        }

    }

    /* Initialize commit instance with commit 0 */
    public Commit(String dateTime) {
        Node master = new Node(null, null, null, "initial commit", 0, dateTime);

        HashSet<Integer> commits = new HashSet<Integer>();
        commits.add(0);
        commitMsgs.put("initial commit", commits);

        commitIDs.put(0, master);

        firstCommit = master;
        branches.put("master", master);
        currBranch = "master";
    }

    /* Returns set of files to be added */
    public HashSet<String> getFilesToAdd() {
        return filesToAdd;
    }

    /* Returns set of files marked for removal */
    public HashSet<String> getFilesToRemove() {
        return filesToRemove;
    }

    /* Returns set of branch names */
    public Set<String> getBranches() {
        return branches.keySet();
    }

    /* Returns the current branch name */
    public String getCurrBranch() {
        return currBranch;
    }

    /* Generate commit ID */
    private int generateID() {
        id++;
        return id;
    }

    /* Returns previous commit id number. */
    public int getLastCommitNum() {
        return id;
    }

    /* Get head pointer of the current branch. */
    private Node getHeadNode() {
        return branches.get(currBranch);
    }

    /* Returns commit IDs associated with commit messages */
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

        HashMap<String, Integer> inheritedFiles = headNode.commitedFiles;
        if (headNode.right == null) {
            headNode.right = new Node(headNode, null, null, msg, nextID, dateTime);
            branches.put(currBranch, headNode.right);
            commitIDs.put(nextID, headNode.right);
            headNode.right.commitedFiles = new HashMap<String, Integer>(inheritedFiles);
        } else if (headNode.left == null) {
            headNode.left = new Node(headNode, null, null, msg, nextID, dateTime);
            branches.put(currBranch, headNode.left);
            commitIDs.put(nextID, headNode.left);
            headNode.left.commitedFiles = new HashMap<String, Integer>(inheritedFiles);
        }
    }

    /*
     * Returns a map of each file committed in the head of the given branch and
     * which commit folder each file can be found in.
     */
    public HashMap<String, Integer> getCommitedFilesBranch(String branch) {

        return branches.get(branch).commitedFiles;
    }

    /*
     * Returns a map of each file committed in the commit with the given ID and
     * which commit folder each file can be found in.
     */
    public HashMap<String, Integer> getCommitedFilesID(int id) {
        return commitIDs.get(id).commitedFiles;
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

    /* Creates new branch */
    public void createBranch(String name) {
        if (branches.containsKey(name)) {
            System.out.println("A branch with that name already exists.");
        } else {
            branches.put(name, getHeadNode());
        }
    }

    /* Changes branch */
    public void changeBranch(String name) {
        currBranch = name;
    }

    /* Removes branch pointer */
    public void removeBranch(String name) {
        branches.remove(name);
    }

    public HashMap<String, Integer> getSplitPointFiles(String branch) {
        Node splitNode = findSplitPointNode(branch);
        return splitNode.commitedFiles;

    }

    public TreeMap<Integer, String> getRebaseCommits(String branch) {
        TreeMap<Integer, String> commits = new TreeMap<Integer, String>();
        Node splitNode = findSplitPointNode(branch);
        Node curr = getHeadNode();
        while (curr != null) {
            if (curr.equals(branches.get(branch))) {
                return new TreeMap<Integer, String>();
            }
            if (curr.commitNum == splitNode.commitNum) {
                return commits;
            }
            commits.put(curr.commitNum, curr.message);
            curr = curr.parent;
        }
        return null;
    }

    public void changeBranchPointer(int id, String branch) {
        branches.put(branch, commitIDs.get(id));
    }

    private Node findSplitPointNode(String name) {
        Node curr = getHeadNode();
        HashSet<Node> currBranchHistory = new HashSet<>();
        while (curr != null) {
            currBranchHistory.add(curr);
            curr = curr.parent;
        }
        Node branch = branches.get(name);
        while (branch != null) {
            if (currBranchHistory.contains(branch)) {
                return branch;
            }
            branch = branch.parent;
        }
        return null;
    }

    /* Adds a node to the commit tree and associates the message with the commit */
    public void addRebase(String msg, String dateTime, String movingBranch) {
        Node headNode = branches.get(movingBranch);
        int nextID = generateID();

        HashSet<Integer> commits;
        if (commitMsgs.containsKey(msg)) {
            commits = commitMsgs.get(msg);
        } else {
            commits = new HashSet<Integer>();
        }
        commits.add(nextID);
        commitMsgs.put(msg, commits);

        if (headNode.right == null) {
            headNode.right = new Node(headNode, null, null, msg, nextID, dateTime);
            branches.put(movingBranch, headNode.right);
            commitIDs.put(nextID, headNode.right);
        } else if (headNode.left == null) {
            headNode.left = new Node(headNode, null, null, msg, nextID, dateTime);
            branches.put(movingBranch, headNode.left);
            commitIDs.put(nextID, headNode.left);
        }
    }

    public void printCommit(int id) {
        Node curr = commitIDs.get(id);
        System.out.println("Commit" + curr.commitNum);
        System.out.println(curr.dateTime);
        System.out.println(curr.message + "\n");
    }
}
