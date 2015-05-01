import java.util.ArrayList;

public class AlphabetSort {
	private static Trie ourTrie = new Trie();
    private static ArrayList<Character> alphabetSet = new ArrayList<Character>();
    
    private static void addToTrie(String[] words) {
    	for (String word : words) {
    		ourTrie.insert(word);
    	}
    }
    
    private static void makeAlphabet(String alphabet) {
    	for(int i = 0; i < alphabet.length(); i++) {
    		char c = alphabet.charAt(i);
    		if (alphabetSet.contains(c)) {
    			throw new IllegalArgumentException();
    		}
    		alphabetSet.add(c);
    	}
    }
    
    private static void sort() {
    	ourTrie.sort(alphabetSet);
    }
    
    public static void main(String[] args) {
        if (args == null) {
            throw new IllegalArgumentException();
        }
        String alphabet = args[0];
        makeAlphabet(alphabet);
        String[] words = new String[args.length-1];
        System.arraycopy(args, 1, words, 0, args.length-1);
        addToTrie(words);
        sort();
    }

}

