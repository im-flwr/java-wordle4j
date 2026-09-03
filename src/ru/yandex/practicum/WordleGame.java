package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class WordleGame {

    private static final int WORD_LENGTH = 5;

    private String answer;
    private int steps;
    private WordleDictionary dictionary;
    private PrintWriter log;
    private List<String> guesses = new ArrayList<>();

    public WordleGame(WordleDictionary dictionary, PrintWriter log) {
        this.dictionary = dictionary;
        this.log = log;
        this.answer = dictionary.getRandomWord();
        this.steps = 6;
    }

    public String getAnswer() {
        return answer;
    }

    public int getSteps() {
        return steps;
    }

    public boolean isGameOver() {
        return steps <= 0 || isWon();
    }

    public boolean isWon() {
        return !guesses.isEmpty() && guesses.get(guesses.size() - 1).equals(answer);
    }

    public void makeGuess(String input) throws WordNotFoundInDictionary, InvalidWordLength {
        input = input.toLowerCase().replace('ё', 'e');
        if (input.isEmpty()) {
            throw new EmptyWordInput("Введена пустая строка");
        }
        if (input.length() != WORD_LENGTH) {
            throw new InvalidWordLength("Слово должно быть из " + WORD_LENGTH + " букв");
        }
        boolean hasLatinLetters = false;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                hasLatinLetters = true;
                break;
            }
        }
        if (hasLatinLetters) {
            throw new InvalidWordInput("Ввод должен содержать только русские буквы");
        }
        if (guesses.contains(input)) {
            throw new InvalidGuess("Это слово уже было введено");
        }
        if (!dictionary.contains(input)) {
            throw new WordNotFoundInDictionary("Слово не найдено в словаре");
        }
        guesses.add(input);
        if (input.equals(answer)) {
            steps = 0;
            log.println("Пользователь угадал слово: " + input);
            throw new GameOverException("Игра окончена");
        } else {
            steps--;
            log.println("Ввод: " + input);
            if (steps == 0) {
                throw new GameOverException("Игра окончена");
            }
        }
    }

    public String analyze(String word) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i);
            if (letter == answer.charAt(i)) {
                result.append('+');
            } else if (answer.contains(String.valueOf(letter))) {
                result.append('^');
            } else {
                result.append('-');
            }
        }
        return result.toString();
    }

    public String getHint() {
        List<String> candidates = dictionary.getWords();
        for (String guess : guesses) {
            candidates = filterCandidates(candidates, guess);
        }
        if (candidates.isEmpty()) {
            return "Нет подходящих слов";
        }
        return candidates.get((int) (Math.random() * candidates.size()));
    }

    private List<String> filterCandidates(List<String> candidates, String guess) {
        List<String> result = new ArrayList<>();
        for (String word : candidates) {
            if (matchesGuess(word, guess)) {
                result.add(word);
            }
        }
        return result;
    }

    private boolean matchesGuess(String candidate, String guess) {
        for (int i = 0; i < guess.length(); i++) {
            char letter = guess.charAt(i);
            if (letter == answer.charAt(i)) {
                if (candidate.charAt(i) != letter) {
                    return false;
                }
            } else if (answer.contains(String.valueOf(letter))) {
                if (!candidate.contains(String.valueOf(letter))) {
                    return false;
                }
            } else {
                if (candidate.contains(String.valueOf(letter))) {
                    return false;
                }
            }
        }
        return true;
    }
}