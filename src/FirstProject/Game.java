package FirstProject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private static final String[] ARRAY_OF_HANGMAN = {"\t0\n|", "  /", "|", "\\", "\n|  /", " \\"};
    GameState gameState = new GameState(chooseRandomWord());
    private boolean usedChar = false;

    public void mainGame() {
        while (!winnerWinnerChickenDinner()) {
            draw(gameState.getRemainedError());
            System.out.println("Word: " + gameState.getHiddenWord());
            System.out.println("CHANCE TO ERROR = " + gameState.getRemainedError());
            if (usedChar) {
                System.out.println("You already write this letter.");
                usedChar = false;
            }
            System.out.println("Used chars: " + gameState.getUsedChars());
            System.out.println("Enter a russian letter: ");
            Scanner scanner = new Scanner(System.in);
            char validLetter = checkForValidLetter(scanner.next());
            System.out.println(checkForValidChar(validLetter));
        }
    }


    private String checkForValidChar(char guessLetter) {
        if (!gameState.getUsedChars().contains(guessLetter)) {
            gameState.getUsedChars().add(guessLetter);
            return checkIfExist(guessLetter);
        }
        usedChar = true;
        return "bruh";
    }

    private String checkIfExist(char guessLetter) {
        if (gameState.getRandomWord().indexOf(guessLetter) != -1) {
            replaceAllSimilarities(findAllIndices(gameState.getRandomWord(), guessLetter),guessLetter);
            return "GOOOD OOONNNEE";
        }
        gameState.decreaseRemainedError();
        return "Nooooooo";
    }


    private static void draw(int chanceToError) {
        System.out.println("------");
        System.out.print("|\t|\n|");
        for (int i = 0; i < 6 - chanceToError; i++) {
            System.out.print(ARRAY_OF_HANGMAN[i]);
        }
        for (int i = 0; i < chanceToError - 3; i++) {
            System.out.print("\n|");
        }
        switch (chanceToError) {
            case 4, 1, 0 -> System.out.println("\n|");
            case 3, 2 -> System.out.println("\n|\n|");
            default -> System.out.println();
        }


        System.out.println("--------");


    }

    private static String chooseRandomWord() {
        Random random = new Random();
        try {
            List<String> list = Files.readAllLines(Path.of("src/FirstProject/WordsForHangman.txt"));
            return list.get(random.nextInt(list.size())).toLowerCase();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isRussianLetter(char ch) {
        return (ch >= 'а' && ch <= 'я');
    }


    private static List<Integer> findAllIndices(String randomWord, char targetChar) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < randomWord.length(); i++) {
            if (randomWord.charAt(i) == targetChar) {
                indices.add(i);
            }
        }
        return indices;
    }

    private static char checkForValidLetter(String guessingString) {
        char guessletter = guessingString.toLowerCase().charAt(0);
        boolean russianLetter = isRussianLetter(guessletter);
        Scanner scanner = new Scanner(System.in);

        while (guessingString.length() > 1 || !russianLetter) {
            System.out.println("Enter a valid russian letter: ");
            guessingString = scanner.next();
            if (guessingString.length() == 1) {
                guessletter = guessingString.toLowerCase().charAt(0);
                russianLetter = isRussianLetter(guessletter);
            }
        }
        return guessletter;
    }

    private void replaceAllSimilarities(List<Integer> indexesOfLetter, char guessletter) {
        StringBuilder hiddenWord = gameState.getHiddenWord();
        if (!indexesOfLetter.isEmpty()) {
            for (Integer index : indexesOfLetter) {
                hiddenWord.setCharAt(index, guessletter);
            }
            gameState.setHiddenWord(hiddenWord);
        }
    }

    private boolean winnerWinnerChickenDinner(){
        if(gameState.getRandomWord().equalsIgnoreCase(gameState.getHiddenWord().toString())){
            System.out.println("YOOOOOOOOOOOUUUUUUUUUUUUUUUU WIIIIIIIIIIIIIIINNNNNNNNNNN");
            System.out.println("\n\n Word was: " + gameState.getRandomWord());
            return true;
        } else if (gameState.getRemainedError() == 0) {
            System.out.println("You Lose(((");
            System.out.println("\n\n Word was: " + gameState.getRandomWord());
            return true;
        }
        return false;
    }
}