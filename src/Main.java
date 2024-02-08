import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    private static final Object ANSI_GREEN = ("\u001B[32m");
    private static final Object ANSI_YELLOW = ("\u001B[33m");
    private static final Object ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) throws IOException, InterruptedException {

        // API call to get random word
        HttpClient client = HttpClient.newHttpClient();
        String url = "https://random-word-api.herokuapp.com/word?length=5";

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(url))
                .build();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to " + ANSI_GREEN + "WordleConsole" + ANSI_RESET + "!");
        System.out.println("Write rules to display the rules or press enter to start the game.");
        String choice;
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

        System.out.println("The word has been chosen. Good luck!");
        System.out.println("You have 6 tries to guess the five letter word.");
        System.out.println("Write your guess below:");
        String guess = scanner.nextLine();
        WordUtils.setGuess(guess);
        WordUtils.getSplittedGuess();

    }
}