package com.gbreed.flashcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Data {
    static private List<Card> terms;

    Data(String fileName, int screenWidth) {
        terms = new ArrayList<>();

        loadData(fileName, screenWidth);
    }

    static private void loadData(String fileName, int screenWidth)
    {
        int maxWordLength = screenWidth / 50;

        try {
            File file = new File(fileName);
            Scanner sc = new Scanner(file);

            boolean startCard = true;

            StringBuilder definition = new StringBuilder();
            String question= "";
            while (sc.hasNextLine())
            {
                String next = sc.nextLine();

                if(!next.equals(""))
                {
                    if(startCard)
                    {
                        if (next.length() > maxWordLength)
                        {
                            String[] sArray = next.split(" ");

                            int sentenceLength = 0;
                            StringBuilder newQ = new StringBuilder();
                            for (String word: sArray)
                            {
                                if(sentenceLength > maxWordLength)
                                {
                                    newQ.append("\n");
                                    sentenceLength = 0;
                                }
                                sentenceLength = sentenceLength + word.length();
                                newQ.append(" ");
                                newQ.append(word);
                            }

                            question = newQ.toString();
                        } else {
                            question = next;
                        }

                        startCard = false;
                    } else {
                        definition.append(next).append("\n");
                    }
                } else {
                    addDef(definition.toString(), question);

                    startCard = true;
                    definition = new StringBuilder();
                    question = "";
                }
            }

            addDef(definition.toString(), question);

        } catch (FileNotFoundException e)
        {
            System.out.println("Could not find file :(");
        }
    }

    private static void addDef(String question, String definition)
    {
        if(!definition.equals("") && !question.equals(""))
        {
            terms.add(new Card(question, definition));
        }
    }

    public List<Card> getData() {
        return terms;
    }

    public int getSize() {
        return terms.size();
    }

    public Card getCard(int i)
    {
        return terms.get(i);
    }
}
