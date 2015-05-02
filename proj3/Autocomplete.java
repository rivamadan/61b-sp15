import java.util.HashSet;
import java.util.LinkedList;

/**
 * Implements autocomplete on prefixes for a given dictionary of terms and
 * weights.
 */
public class Autocomplete {

    // If a given prefix matches some node's character, we can descend into that
    // node's middle subtrie. If not, we look either to the left or right
    // subtrie, depending on whether or not the character comes before or after
    // the node.

    // All of the matching words are in the subtrie corresponding to the prefix,
    // so the first step is to
    // search for this node, say x. Now, to identify the top k matches, we will
    // use the weight of the
    // node and the maximum weight of its subtries to avoid exploration of
    // useless parts of the subtrie.
    private TST ourTST = new TST();

    /**
     * Initializes required data structures from parallel arrays.
     * 
     * @param terms
     *            Array of terms.
     * @param weights
     *            Array of weights.
     */
    public Autocomplete(String[] terms, double[] weights) {
        if (terms.length != weights.length) {
            throw new IllegalArgumentException();
        }
        HashSet<String> checkDuplicates = new HashSet<String>();
        for (int i = 0; i < terms.length; i++) {
            if (weights[i] < 0) {
                throw new IllegalArgumentException();
            }
            if (checkDuplicates.contains(terms[i])) {
                throw new IllegalArgumentException();
            }
            ourTST.put(terms[i], weights[i]);
            checkDuplicates.add(terms[i]);
        }
    }

    /**
     * Find the weight of a given term. If it is not in the dictionary, return
     * 0.0
     * 
     * @param term
     * @return
     */
    public double weightOf(String term) {
        return ourTST.get(term);
    }

    /**
     * Return the top match for given prefix, or null if there is no matching
     * term.
     * 
     * @param prefix
     *            Input prefix to match against.
     * @return Best (highest weight) matching string in the dictionary.
     */
    public String topMatch(String prefix) {
        return prefix;
    }

    /**
     * Returns the top k matching terms (in descending order of weight) as an
     * iterable. If there are less than k matches, return all the matching
     * terms.
     * 
     * @param prefix
     * @param k
     * @return
     */
    public Iterable<String> topMatches(String prefix, int k) {
        return null;
    }

    /**
     * Returns the highest weighted matches within k edit distance of the word.
     * If the word is in the dictionary, then return an empty list.
     * 
     * @param word
     *            The word to spell-check
     * @param dist
     *            Maximum edit distance to search
     * @param k
     *            Number of results to return
     * @return Iterable in descending weight order of the matches
     */
    public Iterable<String> spellCheck(String word, int dist, int k) {
        LinkedList<String> results = new LinkedList<String>();
        /* YOUR CODE HERE; LEAVE BLANK IF NOT PURSUING BONUS */
        return results;
    }

    /**
     * Test client. Reads the data from the file, then repeatedly reads
     * autocomplete queries from standard input and prints out the top k
     * matching terms.
     * 
     * @param args
     *            takes the name of an input file and an integer k as
     *            command-line arguments
     */
    public static void main(String[] args) {
        // initialize autocomplete data structure
        In in = new In(args[0]);
        int N = in.readInt();
        String[] terms = new String[N];
        double[] weights = new double[N];
        for (int i = 0; i < N; i++) {
            weights[i] = in.readDouble(); // read the next weight
            in.readChar(); // scan past the tab
            terms[i] = in.readLine(); // read the next term
        }

        Autocomplete autocomplete = new Autocomplete(terms, weights);

        // process queries from standard input
        int k = Integer.parseInt(args[1]);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            for (String term : autocomplete.topMatches(prefix, k))
                StdOut.printf("%14.1f  %s\n", autocomplete.weightOf(term), term);
        }
    }
}
