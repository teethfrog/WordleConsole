import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    //TODO
    // Extra features: Saved stats (wins, losses, average tries, etc.)
    // Repeat game option
    // Check if guess is a real word (API call to dictionary?)



    private static final Object ANSI_GREEN = ("\u001B[32m");
    private static final Object ANSI_YELLOW = ("\u001B[33m");
    private static final Object ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) throws IOException, InterruptedException {

        // API call to get random 5-char word
        HttpClient client = HttpClient.newHttpClient();
        String url = "https://random-word-api.herokuapp.com/word?length=5&lang=en";

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(url))
                .build();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to " + ANSI_GREEN + "WordleConsole" + ANSI_RESET + "!");
        System.out.println("Write rules to display the rules or press enter to start the game.");
        String choice;

        // Command interface
        do {
            choice = scanner.nextLine();
            switch (choice) {
                case "rules":
                    System.out.println("These are the rules:\nIf the letter is in the word, it will be displayed in " + ANSI_YELLOW + "yellow" + ANSI_RESET + ".\nIf the letter is in the word and on the right spot, it will be displayed in " + ANSI_GREEN + "green" + ANSI_RESET + ".\nYou have 6 tries to guess the word.");
                    break;
                case "":
                    HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                    //System.out.println(response.body());

                    WordUtils.getJSON(response.body());
                    WordUtils.splitWord(WordUtils.getWord());

                    System.out.println("The word has been chosen. You have 6 tries to guess the word.");
                    break;
                default:
                    System.out.println("Invalid input. Please write either 'rules' or press enter to start the game.");
                    break;
            }
        } while (!choice.equalsIgnoreCase(""));

        System.out.println("Write your guess below:");
        int tries = 6;
            ArrayList<String> guessSheet = new ArrayList<String>();
            while (tries > 0) {
                String guess = scanner.nextLine();
                if (!guess.matches("[a-zA-Z]{5}$")) {
                    System.out.println("Invalid input. Please write a 5-letter word that contains only letters.");
                } else {
                    WordUtils.setGuess(guess);
                    String result = WordUtils.compareWords();
                    String resultLettered = WordUtils.getWordResultsLettered(result);
                    String resultColored = WordUtils.getWordResults(result);
                    System.out.println(resultColored);
                    for (int i = 0; i < resultLettered.length(); i++) {
                        guessSheet.add(resultLettered.substring(i, i + 1));
                    }
                    if (WordUtils.cleanWord(resultColored).equals(WordUtils.getWord())) {
                        System.out.println("Congratulations! You guessed the word in " + (7 - tries) + " tries!");
                        ImageHandling.setFileName(new StringBuilder());
                        ImageHandling.writeImage(ImageHandling.generateImage(guessSheet));
                        break;
                    } else {
                        WordUtils.clearCompResult();
                        tries--;
                    }
                }
            }
            if (tries == 0) {
                System.out.println("You lost! The word was " + WordUtils.getWord());
//                for (String s : guessSheet) {
//                    System.out.print(s);
//                }

                ImageHandling.setFileName(new StringBuilder());
                ImageHandling.writeImage(ImageHandling.generateImage(guessSheet));
        }
    }
}

//Tack felix :D