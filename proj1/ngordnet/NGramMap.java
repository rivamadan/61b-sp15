package ngordnet;

import java.util.Collection;
import java.util.HashMap;
import edu.princeton.cs.introcs.In;

public class NGramMap {
    private TimeSeries<Long> totalCountTs = new TimeSeries<Long>();
    private HashMap<String, TimeSeries<Integer>> allWordTs = 
            new HashMap<String, TimeSeries<Integer>>();
    private HashMap<Integer, YearlyRecord> allYears = new HashMap<Integer, YearlyRecord>();

    /** Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME. */
    public NGramMap(String wordsFilename, String countsFilename) {
        In words = new In(wordsFilename);
        In counts = new In(countsFilename);
        createTotalCountTs(counts);
        createAllWordTsAndAllYears(words);
    }

    private void createAllWordTsAndAllYears(In file) {
        while (file.hasNextLine()) {
            String currLine = file.readLine();
            String[] column = currLine.split("\t");

            String word = column[0];
            int year = Integer.parseInt(column[1]);
            int count = Integer.parseInt(column[2]);

            TimeSeries<Integer> wordTs;
            if (allWordTs.get(word) != null) {
                wordTs = allWordTs.get(word);
            } else {
                wordTs = new TimeSeries<Integer>();
                allWordTs.put(word, wordTs);
            }
            wordTs.put(year, count);

            YearlyRecord words;
            if (allYears.get(year) != null) {
                words = allYears.get(year);
            } else {
                words = new YearlyRecord();
                allYears.put(year, words);
            }
            words.put(word, count);
        }
    }

    private void createTotalCountTs(In file) {
        while (file.hasNextLine()) {
            String currLine = file.readLine();
            String[] column = currLine.split(",");

            int year = Integer.parseInt(column[0]);
            long totalCount = Long.parseLong(column[1]);
            totalCountTs.put(year, totalCount);
        }
    }

    /**
     * Returns the absolute count of WORD in the given YEAR. If the word did not
     * appear in the given year, return 0.
     */
    public int countInYear(String word, int year) {
        if (allWordTs.get(word) == null) {
            return 0;
        } else {
            TimeSeries<Integer> countAllYears = allWordTs.get(word);
            return countAllYears.get(year);
        }
    }

    /** Returns a defensive copy of the YearlyRecord of WORD. */
    public YearlyRecord getRecord(int year) {
        YearlyRecord record = allYears.get(year);
        YearlyRecord copy = new YearlyRecord();
        for (String word : record.words()) {
            int count = record.count(word);
            copy.put(word, count);
        }
        return copy;
    }

    /** Returns the total number of words recorded in all volumes. */
    public TimeSeries<Long> totalCountHistory() {
        return totalCountTs;
    }

    /** Provides the history of WORD between STARTYEAR and ENDYEAR. */
    public TimeSeries<Integer> countHistory(String word, int startYear, int endYear) {
        TimeSeries<Integer> countAllYears = allWordTs.get(word);
        return new TimeSeries<Integer>(countAllYears, startYear, endYear);
    }

    /** Provides a defensive copy of the history of WORD. */
    public TimeSeries<Integer> countHistory(String word) {
        return allWordTs.get(word);
    }

    /** Provides the relative frequency of WORD between STARTYEAR and ENDYEAR. */
    public TimeSeries<Double> weightHistory(String word, int startYear, int endYear) {
        TimeSeries<Double> frequency = weightHistory(word);
        return new TimeSeries<Double>(frequency, startYear, endYear);
    }

    /** Provides the relative frequency of WORD. */
    public TimeSeries<Double> weightHistory(String word) {
        TimeSeries<Integer> wordTs = allWordTs.get(word);
        return wordTs.dividedBy(totalCountTs);
    }

    /**
     * Provides the summed relative frequency of all WORDS between STARTYEAR and
     * ENDYEAR. If a word does not exist, ignore it rather than throwing an
     * exception.
     */
    public TimeSeries<Double> summedWeightHistory(Collection<String> words, int startYear,
            int endYear) {
        TimeSeries<Double> summedfrequency = summedWeightHistory(words);
        return new TimeSeries<Double>(summedfrequency, startYear, endYear);
    }

    /** Returns the summed relative frequency of all WORDS. */
    public TimeSeries<Double> summedWeightHistory(Collection<String> words) {
        TimeSeries<Double> summedTs = new TimeSeries<Double>();
        for (String word : words) {
            if (allWordTs.get(word) != null) {
                summedTs = weightHistory(word).plus(summedTs);
            }
        }
        return summedTs;
    }

    /**
     * Provides processed history of all words between STARTYEAR and ENDYEAR as
     * processed by YRP.
     */
    public TimeSeries<Double> processedHistory(int startYear, int endYear,
            YearlyRecordProcessor yrp) {
        TimeSeries<Double> processed = processedHistory(yrp);
        return new TimeSeries<Double>(processed, startYear, endYear);
    }

    /** Provides processed history of all words ever as processed by YRP. */
    public TimeSeries<Double> processedHistory(YearlyRecordProcessor yrp) {
        TimeSeries<Double> processed = new TimeSeries<Double>();
        for (Integer year : allYears.keySet()) {
            processed.put(year, yrp.process(allYears.get(year)));
        }
        return processed;
    }
}
