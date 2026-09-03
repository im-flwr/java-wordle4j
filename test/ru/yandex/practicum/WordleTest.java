package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordleTest {

    private WordleDictionary dictionary;
    private WordleGame game;
    private PrintWriter log;

    @BeforeEach
    void setUp() {
        List<String> words = new ArrayList<>();
        words.add("герой");
        words.add("гонец");
        words.add("домой");
        words.add("столб");
        words.add("абзац");
        words.add("белка");
        dictionary = new WordleDictionary(words);
        log = new PrintWriter(new ByteArrayOutputStream());
        game = new WordleGame(dictionary, log);
    }

    @Test
    void testDictionary() {
        assertTrue(dictionary.contains("герой"));
        assertFalse(dictionary.contains("несуществующее"));
        assertEquals(6, dictionary.getWords().size());
    }

    @Test
    void testGameResult() {
        String answer = game.getAnswer();
        String result = game.analyze(answer);
        assertTrue(result.equals("+++++"));
    }

    @Test
    void testHint() {
        String hint = game.getHint();
        assertNotNull(hint);
        assertFalse(hint.isEmpty());
    }

    @Test
    void testMakeGuessWithInvalidWord() {
        try {
            game.makeGuess("стол");
        } catch (WordNotFoundInDictionary e) {
        } catch (InvalidWordLength e) {
        }
    }

    @Test
    void testGameOverAfterSixGuesses() {
        String[] guesses = {"герой", "гонец", "домой", "столб", "абзац", "белка"};
        try {
            for (String guess : guesses) {
                game.makeGuess(guess);
            }
        } catch (GameOverException e) {
        } catch (WordNotFoundInDictionary e) {
            fail("Слово должно быть в словаре и не вызывать исключение");
        } catch (InvalidWordLength e) {
            fail("Слово должно иметь 5 букв, исключение не должно появиться");
        } catch (InvalidGuess e) {
            fail("Слово не должно быть повторным вводом");
        }
        assertTrue(game.isGameOver());
    }
}