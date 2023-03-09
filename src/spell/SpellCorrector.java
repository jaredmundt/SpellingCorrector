package spell;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;;
import java.util.Vector;

public class SpellCorrector implements ISpellCorrector
{
    private Trie myTrie = new Trie();
    private Vector<String> distanceOne = new Vector<String>();
    private Vector<String> distanceTwo = new Vector<String>();
    private int editDistance = 1;
    private int bestFrequency = 0;


    @Override
    public void useDictionary(String dictionaryFileName) throws IOException
    {
        File file = new File(dictionaryFileName);
        Scanner in = new Scanner(file);
        while (in.hasNext())
        {
            String nextWord = in.next();
            myTrie.add(nextWord);
        }
    }


    @Override
    public String suggestSimilarWord(String inputWord)
    {
        inputWord = inputWord.toLowerCase();
        String similarWord = search(inputWord);
        if (similarWord != null) return similarWord;


        fillDistanceOne(inputWord);
        bestFrequency = 0;
        for ( String str :
            distanceOne )
        {
            inputWord = search(str);
            if (inputWord != null) similarWord = inputWord;
        }
        if (similarWord != null) return similarWord;


        fillDistanceTwo();
        bestFrequency = 0;
        for ( String str :
                distanceTwo )
        {
            inputWord = search(str);
            if (inputWord != null) similarWord = inputWord;
        }
        return similarWord;
    }




    private String search(String inputWord)
    {
        INode inputNode = myTrie.find(inputWord);
        if (inputNode != null && inputNode.getValue() > bestFrequency)
        {
            bestFrequency = inputNode.getValue();
            return inputWord;
        }
        else return null;
    }


    private void fillDistanceOne(String word)
    {
        editDistance = 1;
        distanceOne.clear();
        addDeletion(word);
        addTransposition(word);
        addAlteration(word);
        addInsertion(word);
    }


    private void fillDistanceTwo()
    {
        editDistance = 2;
        distanceTwo.clear();
        for (String str : distanceOne)
        {
            addDeletion(str);
            addDeletion(str);
            addTransposition(str);
            addAlteration(str);
            addInsertion(str);
        }
    }



    private void addDeletion(String word)
    {
        for (int i = 0; i < word.length(); ++i)
        {
            StringBuilder builder = new StringBuilder(word);
            String modifiedWord = builder.deleteCharAt(i).toString();
            if (editDistance == 1) distanceOne.add(modifiedWord);
            if (editDistance == 2) distanceTwo.add(modifiedWord);
        }
    }


    private void addTransposition(String word)
    {
        for (int i = 0; i < word.length() - 1; ++i)
        {
            StringBuilder builder = new StringBuilder(word);
            char letter = word.charAt(i);
            builder.deleteCharAt(i);
            builder.insert(i + 1, letter);
            String modifiedWord = builder.toString();
            if (editDistance == 1) distanceOne.add(modifiedWord);
            if (editDistance == 2) distanceTwo.add(modifiedWord);

        }
    }


    private void addAlteration(String word)
    {
        for (int i = 0; i < word.length(); ++i)
        {
            for (int j = 0; j < 26; ++j)
            {
                StringBuilder builder = new StringBuilder(word);
                char letter = (char)(j + 'a');
                builder.deleteCharAt(i);
                builder.insert(i, letter);
                String modifiedWord = builder.toString();
                if (editDistance == 1) distanceOne.add(modifiedWord);
                if (editDistance == 2) distanceTwo.add(modifiedWord);

            }
        }
    }


    private void addInsertion(String word)
    {
        for (int i = 0; i < word.length() + 1; ++i)
        {
            for (int j = 0; j < 26; ++j)
            {
                StringBuilder builder = new StringBuilder(word);
                char letter = (char)(j + 'a');
                builder.insert(i, letter);
                String modifiedWord = builder.toString();
                if (editDistance == 1) distanceOne.add(modifiedWord);
                if (editDistance == 2) distanceTwo.add(modifiedWord);

            }
        }
    }





}
