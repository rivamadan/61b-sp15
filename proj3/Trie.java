import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Prefix-Trie. Supports linear time find() and insert(). Should support
 * determining whether a word is a full word in the Trie or a prefix.
 * 
 * @author Riva Madan
 */
public class Trie {

    /**
     * Data structure that holds information for each character in the trie/
     */
    private class Node {
        private boolean end;
        private Map<Character, Node> children;
        private String word;

        /**
         * node constructor
         */
        public Node() {
            children = new HashMap<Character, Node>();
            end = false;
        }
    }

    private Node root = new Node();

    /**
     * Returns whether or not a string is in the trie.
     * 
     * @param s
     *            String to find
     * @param isFullWord
     *            Whether or not to look for only the full word
     * @return Whether or not the word is found
     */
    public boolean find(String s, boolean isFullWord) {
        return find(root, s, isFullWord);
    }

    /**
     * helper method for find
     * 
     * @param x
     *            Current node
     * @param s
     *            String to find
     * @param isFullWord
     *            Whether or not to look for only the full word
     * @return Whether or not the word is found
     */
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
                }
            } else {
                return true;
            }
        }

        String next = s.substring(1);
        return find(x.children.get(c), next, isFullWord);
    }

    /**
     * Inserts a string into the trie.
     * 
     * @param s
     *            String to insert
     */
    public void insert(String s) {
        insert(root, s, 0);
    }

    /**
     * helper method for insert
     * 
     * @param x
     *            Current node
     * @param s
     *            String to insert
     * @param i
     *            Index of which character to look at
     * @return Node last node that was inserted
     */
    private Node insert(Node x, String s, int i) {
        if (s.equals("") || s == null) {
            throw new IllegalArgumentException();
        }

        if (x == null) {
            x = new Node();
        }

        if (i == s.length()) {
            x.end = true;
            x.word = s;
            return x;
        }

        char c = s.charAt(i);
        x.children.put(c, insert(x.children.get(c), s, i + 1));
        return x;
    }

    /**
     * Prints out words in trie in sorted order according to a given alphabet.
     * 
     * @param alphabetSet
     *            Order to sort characters
     */
    public void sort(ArrayList<Character> alphabetSet) {
        sort(root, alphabetSet);
    }

    /**
     * helper method for sort
     * 
     * @param x
     *            Current node
     * @param alphabetSet
     *            Order to sort characters
     */
    private void sort(Node x, ArrayList<Character> alphabetSet) {
        for (char eachChar : alphabetSet) {
            if (x.children.containsKey(eachChar)) {
                if (x.children.get(eachChar).end) {
                    System.out.println(x.children.get(eachChar).word);
                }
                sort(x.children.get(eachChar), alphabetSet);
            }
        }
    }

}
