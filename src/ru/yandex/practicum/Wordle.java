package ru.yandex.practicum;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Wordle {

    public static void main(String[] args) {
        try (PrintWriter log = new PrintWriter(new FileWriter("wordle.log", true))) {
            WordleDictionaryLoader loader = new WordleDictionaryLoader();
            WordleDictionary dictionary = loader.load("words_ru.txt");
            WordleGame game = new WordleGame(dictionary, log);
            Scanner scanner = new Scanner(System.in);
            while (!game.isGameOver()) {
                System.out.println("Осталось попыток: " + game.getSteps());
                System.out.println("Введите слово (или пустую строку для подсказки): ");
                String input = scanner.nextLine();
                if (input.isEmpty()) {
                    String hint = game.getHint();
                    System.out.println("Подсказка: " + hint);
                    log.println("Подсказка: " + hint);
                    try {
                        game.makeGuess(hint);
                        System.out.println("Анализ подсказки: " + game.analyze(hint));
                    } catch (GameOverException e) {
                        System.out.println(e.getMessage());
                    } catch (WordNotFoundInDictionary e) {
                        System.out.println(e.getMessage());
                    } catch (InvalidWordLength e) {
                        System.out.println(e.getMessage());
                    }
                    continue;
                }
                try {
                    game.makeGuess(input);
                    System.out.println(game.analyze(input));
                } catch (GameOverException e) {
                    System.out.println(e.getMessage());
                } catch (WordNotFoundInDictionary e) {
                    System.out.println(e.getMessage());
                } catch (InvalidWordLength e) {
                    System.out.println(e.getMessage());
                } catch (InvalidGuess e) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.println("Игра окончена. Загаданное слово: " + game.getAnswer());
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}