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

        NGramMap ngm = new NGramMap(wordFile, countFile);
        WordNet wn = new WordNet(synsetFile, hyponymFile);
        int startDate = -19283123;
        int endDate = 857173;

        while (true) {
            System.out.print("> ");
            String line = StdIn.readLine();
            String[] rawTokens = line.split(" ");
            String command = rawTokens[0];
            String[] tokens = new String[rawTokens.length - 1];
            System.arraycopy(rawTokens, 1, tokens, 0, rawTokens.length - 1);

            switch (command) {
            case "count":
                try {
                    System.out.println(ngm.countInYear(tokens[0], Integer.parseInt(tokens[1])));
                } catch (NullPointerException e) {
                    System.err.println(tokens[0] + " does not exist: " + e);
                } catch (NumberFormatException e) {
                    System.err.println(tokens[1] + " is not valid input: " + e);
                }
                break;
            case "hyponyms":
                try {
                    System.out.println(wn.hyponyms(tokens[0]));
                } catch (NullPointerException e) {
                    System.err.println(tokens[0] + " does not exist: " + e);
                }
                break;
            case "history":
                try {
                    Plotter.plotAllWords(ngm, tokens, startDate, endDate);
                } catch (IllegalArgumentException e) {
                    System.err.println("Command could not be processed:" + e);
                } catch (NullPointerException e) {
                    System.err.println("Command could not be processed:" + e);
                }
                break;
            case "hypohist":
                try {
                    Plotter.plotCategoryWeights(ngm, wn, tokens, startDate, endDate);
                } catch (IllegalArgumentException e) {
                    System.err.println("Command could not be processed:" + e);
                } catch (NullPointerException e) {
                    System.err.println("Command could not be processed:" + e);
                }
                break;
            case "wordlength":
                YearlyRecordProcessor yrp = new WordLengthProcessor();
                Plotter.plotProcessedHistory(ngm, startDate, endDate, yrp);
                break;
            case "zipf":
                try {
                    Plotter.plotZipfsLaw(ngm, Integer.parseInt(tokens[0]));
                } catch (NumberFormatException e) {
                    System.err.println(tokens[0] + " is not valid input: " + e);
                }
                break;
            case "quit":
                return;
            case "help":
                In inHelp = new In("help.txt");
                String helpStr = in.readAll();
                System.out.println(helpStr);
                break;
            case "range":
                try {
                    startDate = Integer.parseInt(tokens[0]);
                    endDate = Integer.parseInt(tokens[1]);
                    System.out.println("Start date: " + startDate);
                    System.out.println("End date: " + endDate);
                } catch (NumberFormatException e) {
                    System.err.println(tokens[0] + " or " + tokens[1] + " are not valid input: " + e);
                }
                break;
            default:
                System.out.println("Invalid command.");
                break;
            }
        }
    }

}
