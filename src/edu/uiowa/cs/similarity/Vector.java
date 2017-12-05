/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uiowa.cs.similarity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author User
 */
public class Vector implements GenericVector<String, Integer> {
    private HashMap<String, Integer> map;
//    private ArrayList<ArrayList<String>> wordsGrid;
    private HashSet<HashSet<String>> wordsGridSet;
    private String baseWord;
    private boolean containsBaseWord = false;
    private double magnitude;
    

//    public Vector(ArrayList<ArrayList<String>> words, String vectorWord)
//    {
//        wordsGrid = words;
//        baseWord = vectorWord;
//        map = this.mapForOneWord();
//        
//    }
    
    public Vector(HashSet<HashSet<String>> words, String vectorWord)
    {
        magnitude = 0;
        wordsGridSet = words;
        baseWord = vectorWord;
        map = this.mapForOneWord();      
    }
    
    
    public HashMap<String, Integer> getVector()
    {
        return map;
    }
    
    public boolean containsBaseWord()
    {
        return containsBaseWord;
    }
    
    public String getWord()
    {
        return baseWord;
    }
    
    public double getMagnitude()
    {
        int mag = 0;
        for (Map.Entry<String, Integer> entry : map.entrySet())
        {
            if (entry.getValue() != 0)
                mag+= entry.getValue()*entry.getValue();
        }
        return mag;
    }
    
    // Use this to calculate the cosine similarity between two vectors
    public double cosineSimilarity(Vector comparisonVec)
    {
        //Do the dot product for the numerator
        double simSum = 0;

        for (Map.Entry<String, Integer> entry : map.entrySet())
        {
            if (entry.getValue() != 0)
            {
                if (comparisonVec.getVector().containsKey(entry.getKey()) && comparisonVec.getVector().get(entry.getKey()) != 0)
                {
                    simSum += entry.getValue() * comparisonVec.getVector().get(entry.getKey());
                }
            }
        }
        
   
        System.out.println(comparisonVec.getWord() + " " + simSum + "/sqrt(" + magnitude + "*" + comparisonVec.getMagnitude() + ") = " + simSum/Math.sqrt(magnitude*comparisonVec.getMagnitude()));
        
        if (this.getMagnitude()== 0 || comparisonVec.getMagnitude() == 0)
            return 0;
        else
            return simSum/Math.sqrt(this.getMagnitude()*comparisonVec.getMagnitude());
    }
    
    // Use this to calculate the negative Euclidean distance between vectors
    public double negEuclideanDist(Vector comparisonVec)
    {
        //Do the dot product for the numerator
        double magnitudeSum = 0;
        double vecMag = 0;
        double compVecMag = 0;
        HashMap<String, Integer> compMap = comparisonVec.getVector();

        for (Map.Entry<String, Integer> entry : map.entrySet())
        {
            if (compMap.containsKey(entry.getKey()))
            {
                // Find the value of the given entry in the comparison vector
                compVecMag = compMap.get(entry.getKey());
                // Remove the entry in the comparison vector so we know what entries have not been considered yet
                compMap.remove(entry.getKey());
            }            
            else
            {
                compVecMag = 0;
            }
            magnitudeSum += (compVecMag-entry.getValue())*(compVecMag-entry.getValue());
        }
        // The comparisonVec will hold some entries map did not, and we have to add them to the magnitude (map's value would be 0 for this entry)
        for (Map.Entry<String, Integer> entry : compMap.entrySet())
        {
            magnitudeSum += (entry.getValue())*(entry.getValue());
        }
        
        
        return -1*Math.sqrt(magnitudeSum);
    }
    
    
    @Override
    public HashMap<String, Integer> mapForOneWord() {
        HashMap<String, Integer> map = new HashMap<>();
        for (HashSet<String> sentence: wordsGridSet)
        {
            if (sentence.contains(baseWord))  // This should be tremendously more efficient for a HashSet than a ArrayList
            {
                // We now know that the text does in fact contain the word we are making the vector for
                containsBaseWord = true;
                
                // If the current sentence contains the word we are creating
                // the vector for, add all of the words in the sentence to 
                // the word's hashmap (vector)
                for(String wordInSentence: sentence)
                {
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
//                            magnitude++;
                        }
                        magnitude++;
                    }

                    else
                    {
                        // The value of the word we are making the vector for 
                        // should be zero
                        map.put(baseWord, 0);
                    }
                }                
            }
        }
        return map;
    } 
}
