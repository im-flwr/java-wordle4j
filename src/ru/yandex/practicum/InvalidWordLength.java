package ru.yandex.practicum;

public class InvalidWordLength extends RuntimeException {
    public InvalidWordLength(String message) {
        super(message);
    }
}