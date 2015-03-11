package ngordnet;

public class WordLengthProcessor implements YearlyRecordProcessor {
    public double process(YearlyRecord yearlyRecord) {
        int totalWords = 0;
        int sumWordLength = 0;
        for (String word : yearlyRecord.words()) {
            int count = yearlyRecord.count(word);
            totalWords += count;
            sumWordLength += (word.length() * count);
        }
        return (double) sumWordLength / (double) totalWords;
    }
}
