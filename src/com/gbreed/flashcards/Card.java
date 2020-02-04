package com.gbreed.flashcards;

public class Card {
    private final String definition;
    private final String question;

    Card(String definition, String question)
    {
        this.definition = definition;
        this.question = question;
    }

    public String getDefinition() {
        return definition;
    }

    public String getQuestion() {
        return question;
    }

    @Override
    public String toString() {
        return "Card{" +
                "definition='" + definition + '\'' +
                ", question='" + question + '\'' +
                '}';
    }
}
