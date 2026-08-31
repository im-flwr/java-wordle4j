package ru.yandex.practicum;

public class InvalidGuess extends RuntimeException {
    public InvalidGuess(String message) {
        super(message);
    }
}