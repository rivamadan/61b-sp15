import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Prefix-Trie. Supports linear time find() and insert(). 
 * Should support determining whether a word is a full word in the 
 * Trie or a prefix.
 * @author Riva Madan
 */
public class Trie {
    
	private class Node {
		private boolean end;
		private Map<Character, Node> children;

		public Node() {
			children = new HashMap<Character, Node>();
			end = false;
		}
		
//		public Map<Character, Node> getChildren() {
//			return children;
//		}
//		
//		public boolean isEnd() {
//			return end;
//		}
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
    	if (!(x.children.containsKey(c))) {
    		return false;
    	}

    	if (s.length() == 1) {
    		if (isFullWord) {
    			if (x.children.get(c).end) {
    				return true;
    			} else {
    				return false;
    			}
    		} else {
    			return true;
    		}
    	}

    	String next = s.substring(1);
    	return find(x.children.get(c), next, isFullWord);
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
    	x.children.put(c, insert(x.children.get(c), s, i+1));
    	return x;
    }

	public void sort(ArrayList<Character> alphabetSet) {
    		sort(root, alphabetSet, new StringBuilder());
	}

	private void sort(Node x, ArrayList<Character> alphabetSet, StringBuilder string) {
		for (char eachChar : alphabetSet) {
			if (x.children.containsKey(eachChar)) {
				if (x.children.get(eachChar).end) {
					string.append(eachChar);
					System.out.println(string);
				} else {
					sort(x.children.get(eachChar), alphabetSet, string.append(eachChar));
					if (x == root) {
						string = new StringBuilder();
					} else {
						string.deleteCharAt(string.length()-1);
					}
				}
			}
			
		}
	}
	
    // get root node DO_FUNC(rootnode, ""<-string)
    // for i < alphabetSet.size(), eachChar = alphabetSet.get(i) or just for (char eachChar : alphabetSet)
    // if rootnode.chlidren.contains(eachChar) -> if rootnode.children.getValue(eachChar).isEnd() == true,
    // System.out.println(string); or add(string)?
    // else DO_FUNC(rootnode.children.getValue(eachChar), "eachChar"<- string)
    // new stringbuilder(), string.append()
}


