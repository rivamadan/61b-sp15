package ngordnet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class YearlyRecord {
    private HashMap<String, Integer> yearRecord;
    
    /** Creates a new empty YearlyRecord. */
    public YearlyRecord() {
        yearRecord = new HashMap<String, Integer>();
    }

    /** Creates a YearlyRecord using the given data. */
    public YearlyRecord(HashMap<String, Integer> otherCountMap) {
        yearRecord = new HashMap<String, Integer>(otherCountMap);
    } 

    /** Returns the number of times WORD appeared in this year. */
    public int count(String word) {
        return yearRecord.get(word);
        
    }

    /** Records that WORD occurred COUNT times in this year. */
    public void put(String word, int count) {
        yearRecord.put(word, count);
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
        Collection<Integer> counts = yearRecord.values();
        Collection<Number> countsNum = new ArrayList<Number>();
        for (Number eachCount : counts) {
            countsNum.add(eachCount);
        }
        return countsNum;
        
    }

    /** Returns rank of WORD. Most common word is rank 1. 
      * If two words have the same rank, break ties arbitrarily. 
      * No two words should have the same rank.
      */
    public int rank(String word) {
        return 0;
        
    }
} 
