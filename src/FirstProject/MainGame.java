package FirstProject;

import java.util.Scanner;

public class MainGame {
    private static final char NEW_GAME = 'n';
    private static final char END_GAME = 'e';

    public static void main(String[] args) {
        boolean isGonnaPlay = isGonnaPlay();
        while (isGonnaPlay){
            Game game = new Game();
            game.mainGame();
            isGonnaPlay = isGonnaPlay();
        }


    }

    private static boolean isGonnaPlay(){
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("[N]ew Game or [E]nd Game: ");
            char startOrEnd = scanner.next().toLowerCase().charAt(0);
            if (startOrEnd ==NEW_GAME) return true;
            else if (startOrEnd == END_GAME) return false;
            else System.out.println("Please enter correct letter");
        }
    }
}
