/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uiowa.cs.similarity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author User
 */
public class Vector implements GenericVector<String, Integer> {
    private HashMap<String, Integer> map;
    private ArrayList<ArrayList<String>> wordsGrid;
    private String baseWord;
    private boolean containsBaseWord = false;
    

    public Vector(ArrayList<ArrayList<String>> words, String vectorWord)
    {
        wordsGrid = words;
        baseWord = vectorWord;
        map = this.mapForOneWord();
        
    }
    
    
    public HashMap<String, Integer> getVector()
    {
        return map;
    }
    
    public String getWord()
    {
        return baseWord;
    }
    // Use this to calculate the cosine similarity between two vectors
    public double cosineSimilarity(Vector comparisonVec)
    {
        //Do the dot product for the numerator
        double simSum = 0;
        Iterator it = map.entrySet().iterator();
        while(it.hasNext())
        {
            // Iterate through the words in the map of this class.  Multiply the element 
            // values by the values associated with the same words in comparisonVec
            Map.Entry<String, Integer> pair = (Map.Entry) it.next();
            simSum += pair.getValue() * comparisonVec.getVector().get(pair.getKey());
        }
        
        // Find the maginitude of this vector for part of the denominator
        double thisMagnitude = 0;
        Iterator itThisMag = map.entrySet().iterator();
        while(itThisMag.hasNext())
        {
            // Find the magnitude of the map in this class by finding the sum 
            // of the squares of the values of each element in the map
            Map.Entry<String, Integer> pair = (Map.Entry) itThisMag.next();
            thisMagnitude += pair.getValue()*pair.getValue();
        }
        
        // Find the magnitude of the vector we are comparing for the other part of the denominator
        double compMagnitude = 0;
        Iterator itCompMag = map.entrySet().iterator();
        while(itCompMag.hasNext())
        {
            // Find the magnitude of the map in the comparisonVec by finding the sum 
            // of the squares of the values of each element in comparisonVec's map
            Map.Entry<String, Integer> pair = (Map.Entry) itCompMag.next();
            compMagnitude += pair.getValue()*pair.getValue();
        }
        return simSum/Math.sqrt(thisMagnitude*compMagnitude);
    }
    
    @Override
    public HashMap<String, Integer> mapForOneWord() {
        HashMap<String, Integer> map = new HashMap<>();
        for (ArrayList<String> sentence: wordsGrid)
        {
            if (sentence.contains(baseWord))
            {
                // We now know that the text does in fact contain the word 
                containsBaseWord = true;
                
                // If the current sentence contains the word we are creating
                // the vector for, add all of the words in the sentence to 
                // the word's hashmap (vector)
                for(String wordInSentence: sentence)
                {
//                    if (!wordInSentence.equals(""))
//                    {
                        if (!wordInSentence.equals(baseWord))
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
                            map.put(baseWord, 0);
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
