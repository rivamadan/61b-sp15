package ngordnet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

public class YearlyRecord {
    private TreeMap<String, Integer> yearRecord;
    private TreeMap<String, Integer> rankMap;
    private boolean frozen = true;
    
    /** Creates a new empty YearlyRecord. */
    public YearlyRecord() {
        yearRecord = new TreeMap<String, Integer>();
    }

    /** Creates a YearlyRecord using the given data. */
    public YearlyRecord(HashMap<String, Integer> otherCountMap) {
        yearRecord = new TreeMap<String, Integer>(otherCountMap);
    } 

    /** Returns the number of times WORD appeared in this year. */
    public int count(String word) {
        return yearRecord.get(word);
        
    }

    /** Records that WORD occurred COUNT times in this year. */
    public void put(String word, int count) {
        yearRecord.put(word, count);
        frozen = false;
    }

    /** Returns the number of words recorded this year. */
    public int size() {
        return yearRecord.size();
        
    }

    /** Returns all words in ascending order of count. */
    public Collection<String> words() {
        return yearRecord.keySet();
        
    }

    /** Returns all counts in ascending order of count. */
    public Collection<Number> counts() {
		return null;
    }

    /** Returns rank of WORD. Most common word is rank 1. 
      * If two words have the same rank, break ties arbitrarily. 
      * No two words should have the same rank.
      */
    public int rank(String word) {
        if (!frozen) {
        	rankWords();
        }
        return rankMap.get(word);
    }

	private void rankWords() {
		HashMap<String, Integer> tempMap = new HashMap<String, Integer>();
		ValueComparator compare = new ValueComparator(yearRecord);
		TreeMap<String, Integer> sorted = new TreeMap<String, Integer>(compare);
		int i = 1;
		for (String word: sorted) {
			tempMap.put(word, i++);
		}
		frozen = true;
	}
	
	private class ValueComparator implements Comparator<Integer> {
  
	    public int compare(Integer a, Integer b) {
	    	return a - b;
	    }
} 
