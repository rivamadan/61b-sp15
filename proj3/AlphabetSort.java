import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Uses a Trie to sort words based on a given alphabet.
 * 
 * @author Riva Madan
 *
 */
public class AlphabetSort {
    private Trie ourTrie = new Trie();

    /**
     * Ddds all the words to a trie.
     */
    private void addToTrie(HashSet<String> words) {
        for (String word : words) {
            ourTrie.insert(word);
        }
    }

    /**
     * Prints the words in sorted order.
     */
    private void sort(ArrayList<Character> alphabet) {
        ourTrie.sort(alphabet);
    }

    /**
     * Takes input for alphabet and words from stdin. Sorts the given words
     * based on the alphabet and prints them on stdout.
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNext()) {
            throw new IllegalArgumentException("No alphabet given.");
        }
        String alphabet = sc.nextLine();
        ArrayList<Character> alphabetSet = new ArrayList<Character>();
        for (int i = 0; i < alphabet.length(); i++) {
            char c = alphabet.charAt(i);
            if (alphabetSet.contains(c)) {
                throw new IllegalArgumentException("Duplicate letters in alphabet");
            }
            alphabetSet.add(c);
        }
        if (!sc.hasNext()) {
            throw new IllegalArgumentException("No words given.");
        }
        HashSet<String> words = new HashSet<String>();
        while (sc.hasNext()) {
            words.add(sc.nextLine());
        }

        AlphabetSort sorter = new AlphabetSort();
        sorter.addToTrie(words);
        sorter.sort(alphabetSet);
    }

}
