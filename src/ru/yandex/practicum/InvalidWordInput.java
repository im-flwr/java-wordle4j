package ru.yandex.practicum;

public class InvalidWordInput extends RuntimeException {
    public InvalidWordInput(String message) {
        super(message);
    }
}