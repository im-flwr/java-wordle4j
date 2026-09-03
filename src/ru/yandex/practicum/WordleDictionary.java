package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.List;

public class WordleDictionary {

    private static final int WORD_LENGTH = 5;

    private List<String> words;

    public WordleDictionary(List<String> words) {
        List<String> filteredWords = new ArrayList<>();
        for (String word : words) {
            word = word.toLowerCase();
            word = word.replace('ё', 'e');
            if (word.length() == WORD_LENGTH) {
                filteredWords.add(word);
            }
        }
        this.words = filteredWords;
    }

    public List<String> getWords() {
        return words;
    }

    public boolean contains(String word) {
        return words.contains(word);
    }

    public String getRandomWord() {
        return words.get((int) (Math.random() * words.size()));
    }

    public List<String> filterByMask(String mask) {
        List<String> result = new ArrayList<>();
        for (String word : words) {
            if (matchesMask(word, mask)) {
                result.add(word);
            }
        }
        return result;
    }

    private boolean matchesMask(String word, String mask) {
        for (int i = 0; i < mask.length(); i++) {
            char maskChar = mask.charAt(i);
            if (maskChar == '-') {
                if (word.contains(String.valueOf(maskChar))) {
                    return false;
                }
            }
            if (maskChar == '+') {
                if (word.charAt(i) != maskChar) {
                    return false;
                }
            }
            if (maskChar == '^') {
                if (!word.contains(String.valueOf(maskChar)) || word.charAt(i) == maskChar) {
                    return false;
                }
            }
        }
        return true;
    }
}