import java.io.Serializable;
import java.util.HashMap;

/* data structure for holding commits */

public class Commit implements Serializable {
    
    public String currHead;
    public HashMap<String, Node> branches = new HashMap<String, Node>();
    private int id;
    private static final long serialVersionUID = 1677399628323362926L;

    private class Node implements Serializable {
        private static final long serialVersionUID = -714753247872804263L;
        public Node parent, left, right;
        public String branch;
        public String message;
        public int commitNum;
        public String dateTime;

        public Node(Node parent, Node left, Node right, String branch, String message, int commitNum, String dateTime) {
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.branch = branch;
            this.message = message;
            this.commitNum = commitNum;
            this.dateTime = dateTime;
            
        }
    }

    public Commit(String dateTime) {
        Node master = new Node(null, null, null, "master", "initial commit", 0, dateTime);
        updateBranchPointers("master", master);
        currHead = "master";
    }

    private Node getCurrHeadNode() {
        return branches.get(currHead);
    }

    private void updateBranchPointers(String nodeName, Node newHeadNode) {
        branches.put(nodeName, newHeadNode);
    }
    
    private int generateID() {
        id++;
        return id;
    }

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
    
    public int getCommitNum() {
        return getCurrHeadNode().commitNum;    
    }
    
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
      
    public void createBranch(String name) {
        updateBranchPointers(name, getCurrHeadNode());
        currHead = name;
    }

    public void changeBranch(String name) {
        currHead = name;
    }
       

}
