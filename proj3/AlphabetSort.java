
public class AlphabetSort {
    private static String alphabet;
    
    //make a map each letter from the alphabet as key, and 0,1,2... as value
    //use regular trie but need to convert each word into the corresponding value (0,1,2..) before entering it in the trie
    //so "hello" for alphabet "lohe" becomes "23001"
    //get rid of words with letters not in alphabet, b/c need to do containsKey()
    //iwith a normal Trie, you would iteratively sort at each depth like MSD sort, sorting per print call???????
    
    public static void main(String[] args) {
        if (args == null) {
            throw new IllegalArgumentException();
        }
        alphabet = args[0];
        // TODO Auto-generated method stub

    }

}

