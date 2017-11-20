/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uiowa.cs.similarity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author User
 */
public class Vector implements GenericVector<String, Integer> {
    private HashMap<String, HashMap> vector;
    private ArrayList<ArrayList<String>> wordsGrid;

    public Vector(ArrayList<ArrayList<String>> words)
    {
        wordsGrid = words;
        vector = new HashMap<String, HashMap>();
    }
    
    @Override
    public HashMap<String, Integer> mapForOneWord(String currWord) {
        HashMap<String, Integer> map = new HashMap<>();
        for (ArrayList<String> sentence: wordsGrid)
        {
            if (sentence.contains(currWord))
            {
                // If the current sentence contains the word we are creating
                // the vector for, add all of the words in the sentence to 
                // the word's hashmap (vector)
                for(String wordInSentence: sentence)
                {
//                    if (!wordInSentence.equals(""))
//                    {
                        if (!wordInSentence.equals(currWord))
                        {
                            if(map.containsKey(wordInSentence))
                            {
                                // If the vector already contains this word in the 
                                // key, add to the value
                                map.put(wordInSentence, map.get(wordInSentence) + 1);
                            }
                            else
                            {
                                // If the vector does not have a record of the word
                                // we are currently looking at, make its value 1
                                map.put(wordInSentence, 1);
                            }
                        }
                        else
                        {
                            // The value of the word we are making the vector for 
                            // should be zero
                            map.put(currWord, 0);
                        }
//                    }
                }
            }
            else
            {
                // If the current sentence does not contain the word we are
                // creating the vector for, add words to the vector with a value
                // of zero
                for (String wordInSentence: sentence)
                {
                    if (!map.containsKey(wordInSentence))
                    {
                        map.put(wordInSentence, 0);
                    }
                }
            }
        }
        return map;
    }

    
}
