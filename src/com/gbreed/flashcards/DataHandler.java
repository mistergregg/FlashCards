package com.gbreed.flashcards;

import java.util.*;
import java.util.stream.Collectors;

public class DataHandler {
    private static Data data;
    private static List<CardId> totalScore;
    private static int currentCard;

    DataHandler(int screenWidth) {
        if (data == null)
        {
            data = new Data("C://Users/Greg/Desktop/interview questions.txt", screenWidth);
            totalScore = new ArrayList<>();

            for (int i = 0; i < data.getSize(); i++)
            {
                totalScore.add(new CardId(i, 0));
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

        currentCard = tmpList.get((int) (tmpList.size() * Math.random())).getId();

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
        totalScore.set(currentCard, new CardId(currentCard, number));
    }
}
