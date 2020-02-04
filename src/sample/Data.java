package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Data {
    static private List<Card> terms;

    Data(String fileName) {
        terms = new ArrayList<>();

        loadData(fileName);
    }

    static private void loadData(String fileName)
    {
        try {
            File file = new File(fileName);
            Scanner sc = new Scanner(file);

            boolean startCard = true;

            StringBuilder question = new StringBuilder();
            String definition = "";
            while (sc.hasNextLine())
            {
                String next = sc.nextLine();

                if(!next.equals(""))
                {
                    if(startCard)
                    {
                        definition = next;
                        startCard = false;
                    } else {
                        question.append(next).append("\n");
                    }
                } else {
                    addDef(question.toString(), definition);

                    startCard = true;
                    question = new StringBuilder();
                    definition = "";
                }
            }

            addDef(question.toString(), definition);

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
