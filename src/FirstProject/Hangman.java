package FirstProject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class Hangman {
    private static final char NEW_GAME = 'N';
    private static final char END_GAME = 'E';
    private static boolean firstResponse = true;
    private static char startOrEnd;
    private static final String[] ARRAY_OF_HANGMAN ={"\t0\n|","  /" ,"|","\\" ,"\n|  /" ," \\"};

    public static void main(String[] args) throws IOException {
        while (true) {
            startOrEnd = startOfGame();
            if(startOrEnd == END_GAME) break;
            boolean russianLetter;
            String randomWord = chooseRandomWord();
            String guessingString;
            char guessletter;
            int chanceToError = 6;
            StringBuilder showGuessAttempts = new StringBuilder(String.join("", Collections.nCopies(randomWord.length(), "*")));
            List<Character> usedChars = new ArrayList<>();
            boolean usedChar = false;

            while (startOrEnd == NEW_GAME) {
                draw(chanceToError);
                System.out.println("Word: " + showGuessAttempts);
                System.out.println("CHANCE TO ERROR = " + chanceToError);
                if (usedChar) System.out.println("You already write this letter.");
                System.out.println("Enter a russian letter: ");
                Scanner scanner = new Scanner(System.in);
                guessingString = scanner.next();
                guessletter = guessingString.toLowerCase().charAt(0);
                russianLetter = isRussianLetter(guessletter);

                while (guessingString.length() > 1 || !russianLetter) {
                    System.out.println("Enter a valid russian letter: ");
                    guessingString = scanner.next();
                    if (guessingString.length() == 1) {
                        guessletter = guessingString.toLowerCase().charAt(0);
                        russianLetter = isRussianLetter(guessletter);
                    }
                }
                if (usedChars.contains(guessletter)) usedChar = true;
                else {
                    usedChars.add(guessletter);
                    usedChar = false;
                }

                if (!usedChar) {
                    List<Integer> indexesOfLetter = findAllIndices(randomWord, guessletter);

                    if (!indexesOfLetter.isEmpty()) {
                        for (Integer index : indexesOfLetter) {
                            showGuessAttempts.setCharAt(index, guessletter);
                        }
                        if (randomWord.equalsIgnoreCase(showGuessAttempts.toString())) {
                            System.out.println("\n\n\n\nYou win.");
                            System.out.println("Word was " + randomWord.toUpperCase());
                            startOrEnd = END_GAME;
                            firstResponse = true;
                        }
                    } else {
                        chanceToError--;
                        if (chanceToError == 0) {
                            startOrEnd = END_GAME;
                            firstResponse = true;
                            draw(chanceToError);
                            System.out.println("You lose.Word was " + randomWord);
                        }
                    }
                }
            }
        }
    }

    private static char startOfGame() {
        while (firstResponse) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("[N]ew Game or [E]nd Game: ");
            startOrEnd = scanner.next().charAt(0);
            if (startOrEnd == NEW_GAME || startOrEnd == END_GAME) firstResponse = false;
            else System.out.println("Please enter correct letter");
        }
        return startOrEnd;
    }

    private static void draw(int chanceToError) {
        System.out.println("------");
        System.out.print("|\t|\n|");
        for (int i = 0; i < 6 - chanceToError; i++) {
            System.out.print(ARRAY_OF_HANGMAN[i]);
        }
        for (int i = 0; i < chanceToError-3; i++) {
            System.out.print("\n|");
        }
        switch (chanceToError){
            case 4,1,0 -> System.out.println("\n|");
            case 3,2 -> System.out.println("\n|\n|");
            default -> System.out.println();
        }


        System.out.println("--------");


    }

    private static String chooseRandomWord() throws IOException {
        Random random = new Random();
        List<String> list = Files.readAllLines(Path.of("src/FirstProject/WordsForHangman.txt"));
        return list.get(random.nextInt(list.size())).toLowerCase();
    }

    public static boolean isRussianLetter(char ch) {
        return (ch >= 'а' && ch <= 'я');
    }

    public static List<Integer> findAllIndices(String string, char targetChar) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == targetChar) {
                indices.add(i);
            }
        }
        return indices;
    }

}
