/**
 * Prefix-Trie. Supports linear time find() and insert(). 
 * Should support determining whether a word is a full word in the 
 * Trie or a prefix.
 * @author Riva Madan
 */
public class Trie {
    private static final int R = 255;
    
	private class Node {
		private boolean end;
		private Node[] children;

		public Node() {
			children = new Node[R];
			end = false;
		}
	}

	private Node root = new Node();

    public boolean find(String s, boolean isFullWord) {
    	return find(root, s, isFullWord);
    }

    private boolean find(Node x, String s, boolean isFullWord) {
    	if (s == null) {
    		return false;
    	}

    	char c = s.charAt(0);
    	if (x.children[c] == null) {
    		return false;
    	}

    	if (s.length() == 1) {
    		if (isFullWord) {
    			if (x.children[c].end) {
    				return true;
    			} else {
    				return false;
    			}
    		} else {
    			return true;
    		}
    	}

    	String next = s.substring(1);
    	return find(x.children[c], next, isFullWord);
    }

    public void insert(String s) {
    	insert(root, s, 0);
    }

    private Node insert(Node x, String s, int i) {
    	if (s == "" || s == null) {
    		throw new IllegalArgumentException();
    	}

    	if (x == null) {
    		x = new Node();
    	}

    	if (i == s.length()) {
    		x.end= true;
    		return x;
    	}

    	char c = s.charAt(i);
    	x.children[c] = insert(x.children[c], s, i+1);
    	return x;
    }
}


