package ngordnet;

import java.util.Set;
import ngordnet.WordNet;
import static org.junit.Assert.*;
import org.junit.Test;

public class WordNetTest {

    @Test
    public void testConstructor() {
        WordNet wn = new WordNet("./p1data/wordnet/synsets11.txt",
                "./p1data/wordnet/hyponyms11.txt");
    }

    @Test
    public void testIsNoun() {
        WordNet wn = new WordNet("./p1data/wordnet/synsets11.txt",
                "./p1data/wordnet/hyponyms11.txt");
        assertEquals(true,(wn.isNoun("jump")));
        assertEquals(true,(wn.isNoun("leap")));
        assertEquals(true,(wn.isNoun("nasal_decongestant")));
    }

    @Test
    public void testNouns() {
        WordNet wn = new WordNet("./p1data/wordnet/synsets11.txt",
                "./p1data/wordnet/hyponyms11.txt");
        for (String noun : wn.nouns()) {
            System.out.println(noun);
        }
        System.out.println();
    }
    
    @Test
    public void testHyponyms() {
        WordNet wn2 = new WordNet("./p1data/wordnet/synsets14.txt", "./p1data/wordnet/hyponyms14.txt");
        Set<String> hyponyms = wn2.hyponyms("change");
        for (String noun : hyponyms) {
            System.out.println(noun);
        } 
    }

    /* Run the unit tests in this file. */
    public static void main(String... args) {
        jh61b.junit.textui.runClasses(WordNetTest.class);
    }
}
