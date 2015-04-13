import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

/* data structure for holding commits */

public class Commit implements Serializable {

    private Node firstCommit;
    private String currHead;
    private HashMap<String, Node> branches = new HashMap<String, Node>();
    private int id;
    private HashSet<String> filesToAdd = new HashSet<String>();
    private static final long serialVersionUID = 1677399628323362926L;

    private class Node implements Serializable {
        private static final long serialVersionUID = -714753247872804263L;
        private Node parent, left, right;
        private String branch;
        private String message;
        private int commitNum;
        private String dateTime;
        private HashMap<String, Integer> fileAndCommit;

        public Node(Node parent, Node left, Node right, String branch, String message,
                int commitNum, String dateTime) {
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.branch = branch;
            this.message = message;
            this.commitNum = commitNum;
            this.dateTime = dateTime;
        }
    }
    
    /* Initialize commit instance with commit 0*/
    public Commit(String dateTime) {
        Node master = new Node(null, null, null, "master", "initial commit", 0, dateTime);
        firstCommit = master;
        updateBranchPointers("master", master);
        currHead = "master";
    }
    
    /* Get head pointer of the current branch.*/
    public Node getCurrHeadNode() {
        return branches.get(currHead);
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
    
    /* Returns list of files to be added */
    public HashSet<String> getFilesToAdd() {
        return filesToAdd;
    }

    /* Adds a node to the commit tree */
    public void addCommit(String msg, String dateTime) {
        Node headNode = getCurrHeadNode();
        if (headNode.right != null) {
            headNode.right = new Node(headNode, null, null, currHead, msg, generateID(), dateTime);
            updateBranchPointers(currHead, headNode.right);
        } else {
            headNode.left = new Node(headNode, null, null, currHead, msg, generateID(), dateTime);
            updateBranchPointers(currHead, headNode.left);
        }
    }

    /* Updates added files to be associated with the current commit. */
    public void recordAdded(String addFile) {
        HashMap<String, Integer> inheritedFiles;
        Node currNode = getCurrHeadNode();
        if (currNode.parent != null) {
            inheritedFiles = new HashMap<String, Integer>();
        } else {
            inheritedFiles = currNode.parent.fileAndCommit;
        }
        inheritedFiles.put(addFile, currNode.commitNum);
        currNode.fileAndCommit = inheritedFiles;
    }

    /* Returns a map of all files tracked and the most recent commit it was added in. */
    public HashMap<String, Integer> getFileAndCommit() {
        return getCurrHeadNode().fileAndCommit;

    }
    
    /* Returns current commit id number. */
    public int getCurrCommitNum() {
        return getCurrHeadNode().commitNum;
    }

    /*
     * Returns the commit id number of the previous commit of the current head
     * node. If the current head node is the initial commit 0 and it has no
     * parent, it returns -1.
     */
    public int getPrevCommitNum() {
        if (getCurrHeadNode().parent != null) {
            return getCurrHeadNode().parent.commitNum;
        }
        return -1;
    }

    /* Traverses through current branch and prints information for each commit */
    public void log() {
        Node curr = getCurrHeadNode();
        while (curr != null) {
            System.out.println("====");
            System.out.println("Commit" + curr.commitNum);
            System.out.println(curr.dateTime);
            System.out.println(curr.message + "\n");
            curr = curr.parent;
        }
    }
    
    /*Prints information from all commit nodes */
    public void globalLog() {
        traverse(firstCommit);
    }
    
    /*Helper method to traverse through all commit and print information*/
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
        updateBranchPointers(name, getCurrHeadNode());
        currHead = name;
    }

    /* Changes branch*/
    public void changeBranch(String name) {
        currHead = name;
    }

}
