package com.gbreed.flashcards;

public class CardId {
    private final int id;
    private final int value;

    CardId(int id, int value)
    {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public int getValue() {
        return value;
    }
}
