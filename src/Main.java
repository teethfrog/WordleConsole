import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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
        do {
            String guess = scanner.nextLine();
            int tries = 5;
            if (guess.length() !=  5 || !guess.matches("[a-zA-Z]+")) {
                System.out.println("Invalid input. Please write a 5-letter word that contains only letters.");
                continue;
            }
            do {
                WordUtils.setGuess(guess);
                String result = WordUtils.compareWords();
                System.out.println(result);
                if (WordUtils.cleanWord(result).equals(WordUtils.getWord())) {
                    System.out.println("Congratulations! You guessed the word in " + (6 - tries) + " tries!");
                    break;
                } else {
                    WordUtils.clearCompResult();
                    tries--;
                    guess = scanner.nextLine();
                }
            } while (tries > 0);
            if (tries == 0) {
                System.out.println("You lost! The word was " + WordUtils.getWord());
                break;
            }
        } while (true);
    }
}