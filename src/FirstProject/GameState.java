package FirstProject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameState {
    private String randomWord;
    private int remainedError;
    private StringBuilder hiddenWord;
    private List<Character> usedChars;

    public GameState(String randomWord) {
        this.randomWord = randomWord;
        this.remainedError = 6;
        this.hiddenWord = new StringBuilder(String.join("", Collections.nCopies(randomWord.length(), "*")));
        this.usedChars = new ArrayList<>();
    }

    public void decreaseRemainedError() {
        remainedError--;
    }

    public void setHiddenWord(StringBuilder hiddenWord) {
        this.hiddenWord = hiddenWord;
    }

    public String getRandomWord() {
        return randomWord;
    }

    public int getRemainedError() {
        return remainedError;
    }

    public StringBuilder getHiddenWord() {
        return hiddenWord;
    }

    public List<Character> getUsedChars() {
        return usedChars;
    }
}
