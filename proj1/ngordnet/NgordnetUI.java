package ngordnet;

import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.In;

/**
 * Provides a simple user interface for exploring WordNet and NGram data.
 * 
 * @author Riva Madan
 */
public class NgordnetUI {
    public static void main(String[] args) {
        In in = new In("./ngordnet/ngordnetui.config");
        System.out.println("Reading ngordnetui.config...");

        String wordFile = in.readString();
        String countFile = in.readString();
        String synsetFile = in.readString();
        String hyponymFile = in.readString();
        System.out.println("\nBased on ngordnetui.config, using the following: " + wordFile + ", "
                + countFile + ", " + synsetFile + ", and " + hyponymFile + ".");

        while (true) {
            System.out.print("> ");
            String line = StdIn.readLine();
            String[] rawTokens = line.split(" ");
            String command = rawTokens[0];
            String[] tokens = new String[rawTokens.length - 1];
            System.arraycopy(rawTokens, 1, tokens, 0, rawTokens.length - 1);
            NGramMap ngm = new NGramMap(wordFile, countFile);
            WordNet wn = new WordNet(synsetFile, hyponymFile);
            int startDate = -19283123;
            int endDate = 857173;
            switch (command) {
            case "count":
                ngm.countInYear(tokens[1], Integer.parseInt(tokens[2]));
                break;
            case "hyponyms":
                wn.hyponyms(tokens[1]);
                break;
            case "history":
                Plotter.plotWeightHistory(ngm, tokens[1], startDate, endDate);
                break;
            case "hypohist":
                Plotter.plotCategoryWeights(ngm, wn, tokens, startDate, endDate);
                break;
            case "wordlength":
                YearlyRecordProcessor yrp = new WordLengthProcessor();
                Plotter.plotProcessedHistory(ngm, startDate, endDate, yrp);
                break;
            case "zipf":
                Plotter.plotZipfsLaw(ngm, Integer.parseInt(tokens[1]));
                break;
            case "quit":
                return;
            case "help":
                In inHelp = new In("help.txt");
                String helpStr = in.readAll();
                System.out.println(helpStr);
                break;
            case "range":
                startDate = Integer.parseInt(tokens[0]);
                endDate = Integer.parseInt(tokens[1]);
                System.out.println("Start date: " + startDate);
                System.out.println("End date: " + endDate);
                break;
            default:
                System.out.println("Invalid command.");
                break;
            }
        }
    }

}
