import java.util.ArrayList;


public class WordUtils {
    //TODO
    // Parse JSON response from API call

    static String word;

    public static void getJSON(String response){
        String parsed = response.replaceAll("[\\[\\]\"']", "");
        word = parsed;
    }
    public static String getWord(){
        return word;
    }

    static ArrayList<String> splitWord = new ArrayList<>();
    static ArrayList<String> splitGuess = new ArrayList<>();
    public static void splitWord(String word){
        for (int i = 0; i < word.length(); i++) {
            splitWord.add(word.substring(i, i + 1));
        }
    }

    public static void getSplitWord(){
        for (int i = 0; i < splitWord.size(); i++) {
            System.out.println(splitWord.get(i));
        }
    }

    public static void setGuess(String guess){
        for (int i = 0; i < guess.length(); i++) {
            splitGuess.add(guess.substring(i, i + 1));
        }
    }

    public static void getSplittedGuess(){
        for (int i = 0; i < splitGuess.size(); i++) {
            System.out.println(splitGuess.get(i));
        }
    }








    // Compare input against decided word
    // Return result of comparison
}
