import java.util.ArrayList;
import java.util.Objects;


public class WordUtils {
    //TODO
    // Parse JSON response from API call

    static String word;

    public static void getJSON(String response){
        String parsed = response.replaceAll("[\\[\\]\"']", "");
        word = parsed;
    } // Parses the JSON response from the API call and sets the word
    public static String getWord(){
        return word;
    }

    static ArrayList<String> splitWord = new ArrayList<>();
    static ArrayList<String> splitGuess = new ArrayList<>();
    public static void splitWord(String word){
        for (int i = 0; i < word.length(); i++) {
            splitWord.add(word.substring(i, i + 1));
        }
    } // Splits the word into an ArrayList for easier comparison

    public static void getSplitWord(){
        for (int i = 0; i < splitWord.size(); i++) {
            System.out.println(splitWord.get(i));
        }
    }

    public static void setGuess(String guess){
        for (int i = 0; i < guess.length(); i++) {
            splitGuess.add(guess.substring(i, i + 1));
        }
    } // Splits the guess into an ArrayList for easier comparison

//    public static void getSplitGuess(){
//        for (int i = 0; i < splitGuess.size(); i++) {
//            System.out.println(splitGuess.get(i));
//        }
//    }

    public static String compareWords() {
        ArrayList<String> compResult = new ArrayList<String>();
        ArrayList<Integer> matchedIndices = new ArrayList<Integer>();
        ArrayList<String> compResultLettered = new ArrayList<String>();

        for (int i = 0; i < splitGuess.size(); i++) {
            String currentChar = splitGuess.get(i);
            boolean foundMatch = false;

            for (int y = 0; y < splitWord.size(); y++) {
                if (!matchedIndices.contains(y) && Objects.equals(currentChar, splitWord.get(y))) {
                    if (i == y) {
                        compResult.add("\u001B[32m" + currentChar + "\u001B[0m");
                        compResultLettered.add("G");
                    } else {
                        compResult.add("\u001B[33m" + currentChar + "\u001B[0m");
                        compResultLettered.add("Y");
                    }
                    foundMatch = true;
                    matchedIndices.add(y);
                    break;
                }
            }

            if (!foundMatch) {
                compResult.add("\u001B[0m" + currentChar + "\u001B[0m");
                compResultLettered.add("W");
            }
        }

        StringBuilder compResultS = new StringBuilder();
        for (String result : compResult) {
            compResultS.append(result);
        }
        StringBuilder compResultLetteredS = new StringBuilder();
        for (String result : compResultLettered) {
            compResultLetteredS.append(result);
        }


        return compResultS.toString() + "," + compResultLetteredS.toString();
    } // Compares the word with the guess and returns the result in ANSI color codes

    public static String getWordResults(String result){
        String[] split = compareWords().split(",");
        return split[0];
    }

    public static String getWordResultsLettered(String result){
        String[] split = compareWords().split(",");
        return split[1];
    }

    public static String cleanWord(String word) {
        String cleanedWord = word.replaceAll("\u001B\\[.*?m", "");
        return cleanedWord;
    } // Cleans the word from ANSI color codes to compare with the original word

    public static void clearCompResult(){
        splitGuess.clear();
    } // Clears the comparison result for the next guess so they don't stack up
}


