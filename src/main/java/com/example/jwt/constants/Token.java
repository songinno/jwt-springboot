package com.example.jwt.constants;

public enum Token {
    HEADER("Authrization"), PREFIX("Bearer "), TYPE("JWT");

    public final String text;

    private Token(String text) {
        this.text = text;
    }
}