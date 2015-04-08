package gitlet;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

/* data structure for holding commits */

public class Commit implements Serializable{
    private class Node {
    	public Node parent, left, right;
    	public String branch;
    	public String message;
    	public int commitNum;
    	
    	public HashSet<String> filesToAdd;
    	
    	public Node(Node parent, Node left, Node right, String branch, String message, int commitNum) {
    		this.parent = parent;
    		this.left = left;
    		this.right = right;
    		this.branch = branch;
    		this.message = message;
    		this.commitNum = commitNum;
    	}
    }
    
    public String currHead;
    public HashMap<String, Node> branches = new HashMap<String, Node>();
    
    public Commit() {
    	Node master = new Node(null, null, null, "master", "initial commit", 0);
    	updateBranchPointers("master", master);
    	currHead = "master";
    }

	private Node getCurrHeadNode() {
		return branches.get(currHead);
	}
	
	private void updateBranchPointers(String nodeName, Node newHeadNode) {
		branches.put(nodeName, newHeadNode);
	}
    
    public void addCommit(String msg) {
    	Node headNode = getCurrHeadNode();
    	if (headNode.right != null){
    		headNode.right = new Node(headNode, null, null, currHead, msg, headNode.commitNum+1);
    		updateBranchPointers(currHead, headNode.right);
    	} else {
    		headNode.left = new Node(headNode, null, null, currHead, msg, headNode.commitNum+1);
    		updateBranchPointers(currHead, headNode.left);
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
