package edu.uiowa.cs.similarity;

import opennlp.tools.stemmer.*;
import org.apache.commons.cli.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import static java.util.Collections.reverseOrder;
import java.util.Comparator;
import static java.util.Comparator.comparing;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import javafx.util.Pair;

public class Main {
// first part, the regex is useDelimiter("[.?!]");
    // also works with "\\.||\\?||\\!"
    public static void main(String[] args) throws FileNotFoundException {
        Options options = new Options();
        options.addRequiredOption("f", "file", true, "input file to process");
        options.addOption("h", false, "print this help message");
	options.addOption("s", false, "print sentences");
        options.addOption("v", false, "print semantic description vectors");
        options.addOption("t", "word", true, "top j semantic matches");
        options.addOption("e", "word", true, "compute the negative euclidean distance");
        
        CommandLineParser parser = new DefaultParser();

        
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            new HelpFormatter().printHelp("Main", options, true);
            System.exit(1);
        }

        String filename = cmd.getOptionValue("f");
        if (!new File(filename).exists()) {
                System.err.println("file does not exist "+filename);
                System.exit(1);
        }
                
        File file = new File(filename);
	    
                

        if (cmd.hasOption("h")) {
            HelpFormatter helpf = new HelpFormatter();
            helpf.printHelp("Main", options, true);
            System.exit(0);
        }
        
	List<String> stopWords = new ArrayList<>();
	
	
	File stopWordFile = new File("../stopwords.txt");
        Scanner scanProhib = new Scanner(stopWordFile);
        
        scanProhib.useDelimiter("\n");
        while (scanProhib.hasNext())
        {
            stopWords.add(scanProhib.next());
        }
        
        Scanner sc = new Scanner(file);
	String words = "";
        while(sc.hasNext())
        {
	    words += sc.next() + " ";
        }
        sc.close();
        words = words.toLowerCase();


        words = words.replaceAll(",|, |--|:|;|\"|'", "");

        for (int i=0; i<stopWords.size(); i++)
        {	
            words = words.replaceAll("\\s*\\b"+stopWords.get(i)+"\\b\\s*", " ");
            stopWords.set(i, stopWords.get(i).replaceAll("'", ""));
        }
        for (int i=0; i<stopWords.size(); i++)
        {	
            words = words.replaceAll("\\s*\\b"+stopWords.get(i)+"\\b\\s*", " ");
        }
       
	// Add all of the sentences from the text to an ArrayList
        // Add all of the sentences from the text to a HashMap
        String[] wordsList = words.split("[.?!]");
        
        HashSet<String> sentenceSet = new HashSet<>();
	for(int i = 0; i < wordsList.length-1; i++){
                if (!wordsList[i].equals("")) // Don't know why but ""'s are still being added
                {
                    sentenceSet.add(wordsList[i]);
                }
	}
	
        PorterStemmer ps = new PorterStemmer();

        // Get the stem of all the words in all the sentences and put them into HashSets of sentences within setOfWordsInSentences
        HashSet<HashSet<String>> setOfWordsInSentences  = new HashSet<>();
        for (String entry : sentenceSet)
        {
            Scanner scanSentences = new Scanner(entry);
        
            scanSentences.useDelimiter(" ");
            HashSet<String> currSentence = new HashSet<>();
            while (scanSentences.hasNext())
            {
                String nextWord = ps.stem(scanSentences.next());
                    currSentence.add(nextWord);
               
            }
            setOfWordsInSentences.add(currSentence);
        }
        
        
	
	
	 if (cmd.hasOption("s")) {  
            for (HashSet<String> currSentence: setOfWordsInSentences)
            {
                System.out.println(currSentence);
            }
	    
            System.out.println("Num sentences: " + setOfWordsInSentences.size());
            
        }
        
// FIX VECTORS NEXT!!!!


    
        HashMap vectors = new HashMap<String, HashMap>();
        // This for each loop that uses the vector constructor is what takes so long
        for (HashSet<String> sentence: setOfWordsInSentences)
        {
            for (String word: sentence)
            {
                Vector vect = new Vector(setOfWordsInSentences, word);
                vectors.put(word, vect.getVector());
            }
        }
        
        
        
        
        
        
        
        
        
        
        
        
        
        // THIS IS FOR TESTING!!!!
//        if (cmd.hasOption("e"))
//        {
//            String argWord= cmd.getOptionValue("e");
//            argWord = argWord.toLowerCase();
//            argWord = ps.stem(argWord);
//            System.out.println(argWord);
//
//            Vector vec = new Vector(setOfWordsInSentences, argWord);
//            if (!vec.containsBaseWord())
//            {
//                // If we can't find the base word the user wants to find, let them know
// //                System.out.println("Cannot compute top-" + jNum + " similarity to " + argWord + ".");
//                System.out.println("Can't find " + argWord);
//            }
//            else
//            {
//                Iterator it = vectors.entrySet().iterator();
//                while(it.hasNext())
//                {
//  //                   // Make a vector for every word 
//                    Map.Entry<String, Integer> entryPair = (Map.Entry) it.next();
//                    Vector compVec = new Vector(setOfWordsInSentences, entryPair.getKey());
//                    System.out.println("Negative Euclidean distance between " + vec.getWord() + " and " + compVec.getWord() + " is " + vec.negEuclideanDist(compVec));
//                 }
//            }
//        }
        
        
        
        
        
        
        
        
        
        
        
        
        if (cmd.hasOption("v")){
            Iterator it = vectors.entrySet().iterator();
            while(it.hasNext())
            {
                // Make a vector for every word 
                Map.Entry<String, Integer> entryPair = (Map.Entry) it.next();
                System.out.println("Semantic desriptor vector for " + entryPair.getKey() + ":\n" + entryPair.getValue() + "\n");
            }
        }
         
        if (cmd.hasOption("t"))
        {
            String jTest= cmd.getOptionValue("t");
            String[] jTestSeparated = jTest.split(",");
            String argWord = jTestSeparated[0];
            int jNum = Integer.parseInt(jTestSeparated[1]);
            argWord = argWord.toLowerCase();
            argWord = ps.stem(argWord);
            System.out.println(argWord);
            System.out.println(jNum);

            String similarityMeasureType = "";
            if (cmd.hasOption("m"))
            {
                similarityMeasureType = cmd.getOptionValue("m");
            }
            
//            topK(function, Vector, HashMap);
            
            Vector vec = new Vector(setOfWordsInSentences, argWord);
            if (!vec.containsBaseWord())
            {
                // If we can't find the base word the user wants to find, let them know
                System.out.println("Cannot compute top-" + jNum + " similarity to " + argWord + ".");
            }
            else
            {
                ArrayList<Pair<String, Double>> similarityRanking = new ArrayList<>();

               Iterator it = vectors.entrySet().iterator();
               while(it.hasNext())
               {
                   // Make a vector for every word 
                   Map.Entry<String, Integer> entryPair = (Map.Entry) it.next();
                   if (!entryPair.getKey().equals(vec.getWord()))
                   {
                        Vector compVec = new Vector(setOfWordsInSentences, entryPair.getKey());
                        // A pair holds a word and its corresponding semantic descriptor score
                        Pair<String, Double> similarityPair = new Pair<String, Double>(entryPair.getKey(), vec.cosineSimilarity(compVec.getVector()));
                        if (similarityPair != null)
                        {
                            // Add to the list of pairs that hold a word and its semantic descriptor score
                             similarityRanking.add(similarityPair);
                        }
                   }
               }


               // Create a comparator to order the elements of the similarity rankings based on their double values
               final Comparator<Pair<String, Double>> c = reverseOrder(comparing(Pair::getValue));
               // Sort the values
               Collections.sort(similarityRanking, c);
               // similarityRanking now contains the pairs ranked by how similar they are to the word
               // given in the command prompt.  similarityRanking is now in the format as follows
               // [wolf=0.8, tiger=0.8, fox=0.8, squirrel=0.8, dog=0.8, banana=0.0, nine=0.0, parslei=0.0 ........ ]
               
//               System.out.println(similarityRanking);
               ArrayList<Pair<String, Double>> topJNumSimilarityRanking = new ArrayList<>();
                for (int i=0; i<jNum; i++)
                {
                    topJNumSimilarityRanking.add(similarityRanking.get(i));
                }
                System.out.println(topJNumSimilarityRanking);
            }
        }
    }
}
