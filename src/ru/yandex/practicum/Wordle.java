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
            while (true) {
                System.out.println("Осталось попыток: " + game.getSteps());
                System.out.println("Введите слово (или пустую строку для подсказки): ");
                String input = scanner.nextLine();
                if (input.isEmpty()) {
                    System.out.println("Подсказка: " + game.getHint());
                    continue;
                }
                try {
                    game.makeGuess(input);
                    String result = game.analyze(input);
                    System.out.println(result);
                    if (result.equals("+++++")) {
                        System.out.println("Поздравляю! Вы угадали слово!");
                        break;
                    }
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}