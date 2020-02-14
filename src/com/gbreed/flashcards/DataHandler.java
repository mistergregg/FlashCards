package com.gbreed.flashcards;

import java.util.*;
import java.util.stream.Collectors;

public class DataHandler {
    private static Data data;
    private static List<CardId> totalScore;
    private static int currentCard;
    private static int currentScore;
    private int goodCards;
    private int badCards;
    private int okCards;

    DataHandler(int screenWidth) {
        if (data == null)
        {
            data = new Data(System.getProperty("user.home") + "/Desktop/interview questions.txt", screenWidth);
            totalScore = new ArrayList<>();

            goodCards = 0;
            badCards = 0;
            okCards = 0;

            for (int i = 0; i < data.getSize(); i++)
            {
                totalScore.add(new CardId(i, 0));
                badCards++;
            }
        }
    }

    public Card getRandomCard() {
        currentCard = (int) (data.getSize() * Math.random());
        return data.getCard(currentCard);
    }

    public Card getRandomLowestCard() {
        CardId min = totalScore.stream().min(Comparator.comparing(CardId::getValue)).orElseThrow(NoSuchElementException::new);
        List<CardId> tmpList = totalScore.stream().filter(e -> e.getValue() == min.getValue()).collect(Collectors.toList());

        if(tmpList.size() < totalScore.size() / 2)
        {
            tmpList.addAll(totalScore.stream().filter(e -> e.getValue() == (min.getValue() + 1)).collect(Collectors.toList()));
        }

        CardId tmpCurrCard = tmpList.get((int) (tmpList.size() * Math.random()));

        currentCard = tmpCurrCard.getId();
        currentScore = tmpCurrCard.getValue();

        return data.getCard(currentCard);
    }

    public Card good() {
        modifyScore(2);
        return getRandomLowestCard();
    }

    public Card ok() {
        modifyScore(1);
        return getRandomLowestCard();
    }

    public Card bad() {
        modifyScore(0);
        return getRandomLowestCard();
    }

    private void modifyScore(int number)
    {
        switch (currentScore)
        {
            case 0:
                badCards--;
                break;
            case 1:
                okCards--;
                break;
            case 2:
                goodCards--;
                break;
        }

        switch (number)
        {
            case 0:
                badCards++;
                break;
            case 1:
                okCards++;
                break;
            case 2:
                goodCards++;
                break;
        }

        totalScore.set(currentCard, new CardId(currentCard, number));
    }

    public int getGoodCards() {
        return goodCards;
    }

    public int getBadCards() {
        return badCards;
    }

    public int getOkCards() {
        return okCards;
    }

    public int getCurrentScore() {
        return currentScore;
    }
}
