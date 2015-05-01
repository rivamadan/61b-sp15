import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class AlphabetSort {
    private static Trie ourTrie = new Trie();
    //private static ArrayList<Character> alphabetSet = new ArrayList<Character>();
    private static char[] alphaArr;
    private static HashSet<Character> alphabetSet = new HashSet<Character>();

    /* add all the words to a trie */
    private static void addToTrie(HashSet<String> words) {
        for (String word : words) {
            ourTrie.insert(word);
        }
    }

    /* saves the alphabet */
    private static void makeAlphabet(String alphabet) {
        alphaArr = new char[alphabet.length()];
        for (int i = 0; i < alphabet.length(); i++) {
            char c = alphabet.charAt(i);
            if (alphabetSet.contains(c)) {
                throw new IllegalArgumentException("Duplicate letters in alphabet");
            }
            alphabetSet.add(c);
            alphaArr[i] = c;
        }
    }

    /* prints the words in sorted order */
    private static void sort() {
        ourTrie.sort(alphaArr);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNext()) {
            throw new IllegalArgumentException("No alphabet given.");
        }
        String alphabet = sc.nextLine();
        makeAlphabet(alphabet);
        if (!sc.hasNext()) {
            throw new IllegalArgumentException("No words given.");
        }
        HashSet<String> words = new HashSet<String>();
        while (sc.hasNext()) {
            words.add(sc.nextLine());
        }
        addToTrie(words);
        sort();
    }

}
