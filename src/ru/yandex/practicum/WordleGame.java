package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class WordleGame {

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

    public void makeGuess(String input) {
        input = input.toLowerCase().replace('ё', 'e');
        if (input.isEmpty()) {
            throw new EmptyWordInput("Введена пустая строка");
        }
        if (input.length() != 5) {
            throw new InvalidWordLength("Слово должно быть из 5 букв");
        }
        if (!dictionary.contains(input)) {
            throw new WordNotFoundInDictionary("Слово не найдено в словаре");
        }
        guesses.add(input);
        String result = analyze(input);
        log.println("Ввод: " + input + " Результат: " + result);
        if (input.equals(answer)) {
            steps = 0;
            throw new GameOverException("Игра окончена");
        }
        steps--;
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
        StringBuilder maskBuilder = new StringBuilder("-----");
        for (String guess : guesses) {
            String result = analyze(guess);
            for (int i = 0; i < maskBuilder.length(); i++) {
                char currentChar = maskBuilder.charAt(i);
                char resultChar = result.charAt(i);
                if (currentChar == '-' || currentChar == '^') {
                    if (resultChar == '+') {
                        maskBuilder.setCharAt(i, resultChar);
                    } else if (resultChar == '^') {
                        maskBuilder.setCharAt(i, resultChar);
                    }
                }
            }
        }
        String mask = maskBuilder.toString();
        List<String> candidates = dictionary.filterByMask(mask);
        if (candidates.isEmpty()) {
            return "Нет подходящих слов";
        }
        return candidates.get((int) (Math.random() * candidates.size()));
    }
}