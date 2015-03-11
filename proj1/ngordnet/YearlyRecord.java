package ngordnet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;

public class YearlyRecord {
    private HashMap<String, Integer> record;
    private TreeMap<String, Integer> rankMap = new TreeMap<String, Integer>();
    private TreeMap<String, Integer> sorted;
    private boolean frozen;

    /** Creates a new empty YearlyRecord. */
    public YearlyRecord() {
        record = new HashMap<String, Integer>();
        frozen = true;
    }

    /** Creates a YearlyRecord using the given data. */
    public YearlyRecord(HashMap<String, Integer> otherCountMap) {
        record = new HashMap<String, Integer>(otherCountMap);
        frozen = false;
    }

    /** Returns the number of times WORD appeared in this year. */
    public int count(String word) {
        return record.get(word);

    }

    /** Records that WORD occurred COUNT times in this year. */
    public void put(String word, int count) {
        record.put(word, count);
        frozen = false;
    }

    /** Returns the number of words recorded this year. */
    public int size() {
        return record.size();

    }

    /** Returns all words in ascending order of count. */
    public Collection<String> words() {
        if (!frozen) {
            sortWords();
        }
        return sorted.keySet();

    }

    /** Returns all counts in ascending order of count. */
    public Collection<Number> counts() {
        if (!frozen) {
            sortWords();
        }
        Collection<Integer> counts = sorted.values();
        Collection<Number> countNum = new ArrayList<Number>();
        for (Integer eachCount : counts) {
            countNum.add(eachCount);
        }
        return countNum;
    }

    private void sortWords() {
        sorted = new TreeMap<String, Integer>(new ValueComparator(record));
        sorted.putAll(record);
        frozen = true;
    }

    /**
     * Returns rank of WORD. Most common word is rank 1. If two words have the
     * same rank, break ties arbitrarily. No two words should have the same
     * rank.
     */
    public int rank(String word) {
        if (!frozen) {
            sortWords();
            rankWords();
        }
        return rankMap.get(word);
    }

    private void rankWords() {
        int i = sorted.size();
        for (String word : sorted.keySet()) {
            rankMap.put(word, i);
            i -= 1;
        }
        frozen = true;
    }

    private class ValueComparator implements Comparator<String> {

        private HashMap<String, Integer> compareMap;

        public ValueComparator(HashMap<String, Integer> yearRecord) {
            this.compareMap = yearRecord;
        }

        public int compare(String a, String b) {
            if (compareMap.get(a) >= compareMap.get(b)) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}
