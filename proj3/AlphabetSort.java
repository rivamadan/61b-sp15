import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class AlphabetSort {
    private Trie ourTrie = new Trie();

    /* add all the words to a trie */
    private void addToTrie(HashSet<String> words) {
        for (String word : words) {
            ourTrie.insert(word);
        }
    }

    /* prints the words in sorted order */
    private void sort(ArrayList<Character> alphabet) {
        ourTrie.sort(alphabet);
    }

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

