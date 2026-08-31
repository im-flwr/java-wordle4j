package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeAll;
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

    @BeforeAll
    static void init() {
    }

    @BeforeEach
    void setUp() {
        List<String> words = new ArrayList<>();
        words.add("герой");
        words.add("гонец");
        dictionary = new WordleDictionary(words);
        log = new PrintWriter(new ByteArrayOutputStream());
        game = new WordleGame(dictionary, log);
    }

    @Test
    void testDictionary() {
        assertTrue(dictionary.contains("герой"));
        assertFalse(dictionary.contains("несуществующее"));
        assertEquals(2, dictionary.getWords().size());
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
}