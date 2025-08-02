package com.example.backend.model;

public enum Difficulty {
    EASY(10),
    MEDIUM(20),
    HARD(30);

    private final int value;

    Difficulty(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}