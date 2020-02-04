package sample;

import java.util.HashMap;

public class DataHandler {
    private static Data data;
    private static HashMap<Integer, Integer> totalScore;
    private static int currentCard;

    DataHandler() {
        if (data == null)
        {
            data = new Data("C://Users/Greg/Desktop/interview questions.txt");
            totalScore = new HashMap<>();

            for (int i = 0; i < data.getSize(); i++)
            {
                totalScore.put(i, 0);
            }
        }
    }

    public Card getRandomCard() {
        currentCard = (int) (data.getSize() * Math.random());
        return data.getCard(currentCard);
    }

    public Card good() {
        modifyScore(2);
        return getRandomCard();
    }

    public Card ok() {
        modifyScore(1);
        return getRandomCard();
    }

    public Card bad() {
        return getRandomCard();
    }

    private void modifyScore(int number)
    {
        int score = totalScore.get(currentCard) + number;
        totalScore.put(currentCard, score);
    }
}
