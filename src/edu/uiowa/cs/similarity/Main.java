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
        
        CommandLineParser parser = new DefaultParser();

//        HashMap<String, Integer> hm1 = new HashMap<>();
//        HashMap<String, Integer> hm2 = new HashMap<>();
//        
//        hm1.put("test1", 1);
//        hm1.put("test2", 2);
//        hm1.put("test3", 3);
//        System.out.println(hm1);
//        hm2 = hm1;
//        System.out.println(hm2);
//        hm2.put("test2", 5);
//        System.out.println(hm1);
//        System.out.println(hm2);
//        
//        for (Map.Entry<String, Integer> entry : hm1.entrySet())
//        {
//            System.out.println(entry.getKey());
//        }
        
       
        
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
	
	//Used the Arraylist and Arrays API
	//Thamer told us to hard code in the stop words
	//However we figured out how to open files
	/*
	prohibWords.addAll(Arrays.asList("a","about","above","after","again","against","all","am","an","and","any","are","aren't","as","at","be","because","been","before","being","below","between","both","but","by","can't","cannot","could","couldn't","did","didn't","do","does","doesn't","doing","don't","down","during","each","few","for","from","further","had","hadn't","has","hasn't","have","haven't","having","he","he'd","he'll","he's","her","here","here's","hers","herself","him","himself","his","how","how's","i","i'd","i'll","i'm","i've","if","in","into","is","isn't","it","it's","its","itself","let's","me","more","most","mustn't","my","myself","no","nor","not","of","off","on","once","only","or","other","ought","our","ours","ourselves","out","over","own","same","shan't","she","she'd","she'll","she","should","shouldn't","so","some","such","than","that","that's","the","their","theirs","them","themselves","then","there","there's","these","they","they'd","they'll","they're","they've","this","those","through","to","too","under","until","up","very","was","wasn't","we","we'd","we'll","we're","we've","were","weren't","what","what's","when","when's","where","where's","which","while","who","who's","whom","why","why's","with","won't","would","wouldn't","you","you'd","you'll","you're","you've","your","yours","yourself","yourselves"));
	*/
	
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

        words = words.replaceAll("[,\\:\\;\\'\"]", "");
        words = words.replaceAll("--", "");
	//words = words.replaceAll("-", ""); for night-cap to nightcap, sample output and the dash in there

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
        
//	ArrayList<String> sentenceList = new ArrayList<>();
        HashSet<String> sentenceSet = new HashSet<>();
	for(int i = 0; i < wordsList.length-1; i++){
//		sentenceList.add(wordsList[i]);
                if (!wordsList[i].equals("")) // Don't know why but ""'s are still being added
                {
                    sentenceSet.add(wordsList[i]);
                }
	}
	
        PorterStemmer ps = new PorterStemmer();
       // Get the stem of all the words in the sentences and put them into ArrayLists
       // Get the stem of all the words in the sentences and put them into HashMaps
//        ArrayList<ArrayList<String>> listOfWordsInSentences = new ArrayList<>();
//        String[] psWords;
//        for (int i=0; i<sentenceList.size(); i++)
//        {
//            psWords = sentenceList.get(i).split(" ");
//            for (int j=0; j<psWords.length; j++)
//            {
//                psWords[j] = ps.stem(psWords[j]);
//            }
//            ArrayList<String> currSentence = new ArrayList<String>(Arrays.asList(psWords));
//            listOfWordsInSentences.add(currSentence);
//        }
//        HashMap<HashMap<String, Integer>, Integer> mapOfWordsInSentences = new HashMap<>();
//        for (Map.Entry<String, Integer> entry : hm1.entrySet())

        // Get the stem of all the words in all the sentences and put them into HashSets of sentences within setOfWordsInSentences
        HashSet<HashSet<String>> setOfWordsInSentences  = new HashSet<>();
        for (String entry : sentenceSet)
        {
            Scanner scanSentences = new Scanner(entry);
        
            scanSentences.useDelimiter(" ");
            HashSet<String> currSentence = new HashSet<>();
            while (scanSentences.hasNext())
            {
                // Add all root words to the current sentence but only if it is not ""
                String nextWord = ps.stem(scanSentences.next());
//                if (!"".equals(nextWord))
//                {
                    currSentence.add(nextWord);
//                }                
            }
            setOfWordsInSentences.add(currSentence);
        }
//        System.out.println(setOfWordsInSentences);
//        for ()
        
//        for (int i=0; i<listOfWordsInSentences.size(); i++)
//        {
//            for(int j=0; j<listOfWordsInSentences.get(i).size(); j++)
//            {
//                if(listOfWordsInSentences.get(i).get(j).equals(""))
//                    listOfWordsInSentences.get(i).remove(j);
//            }
//        }
//        String test = "glum";
//        System.out.println(test.equals(""));
//        int i=0;

//        for (HashSet<String> hs: setOfWordsInSentences)
//        {
//            for (String s: hs)
//            {
//                if (s == null || test.equals(s) || test.compareTo(s) == 0 || s == " ")
//                {
//                    hs.remove(s);
//                    
//                }
//                i++;
//            }
//        }
        
        
	
	
	 if (cmd.hasOption("s")) {
//	    for (int i=0; i<listOfWordsInSentences.size(); i++)
//	    {
//                ArrayList<String> currSentence = listOfWordsInSentences.get(i);
//                for (int j=0; j<currSentence.size(); j++)
//                {
//                    System.out.print(currSentence.get(j) + " ");
//                }
//            System.out.println();
//            }
            for (HashSet<String> currSentence: setOfWordsInSentences)
            {
                System.out.println(currSentence);
            }
	    
            System.out.println("Num sentences: " + setOfWordsInSentences.size());
//	    System.out.println(sentenceList.size());
            
        }
        
        
//        System.out.println(vect.mapForOneWord("know"));
// 

// FIX VECTORS NEXT!!!!



        HashMap vectors = new HashMap<String, HashMap>();
        for (HashSet<String> sentence: setOfWordsInSentences)
        {
            for (String word: sentence)
            {
//                System.out.println("Semantic descriptor vector for " + word + ":");
//                System.out.println(vect.mapForOneWord(word) + "\n");
                Vector vect = new Vector(setOfWordsInSentences, word);
                vectors.put(word, vect.getVector());
            }
        }
        
        
         if (cmd.hasOption("v")){
             vectors.forEach((key, value) -> System.out.println("Semantic desriptor vector for " + key + ":\n" + value + "\n"));
//             for (HashMap<String, HashMap> nameOfVector: vectors.entrySet())
//             {
                 
//                 (HashMap<String, HashMap>) nov = nameOfVector;
//                 System.out.println("Semantic descriptor vector for " + (HashMap<String, HashMap>) nameOfVector.getKey() + ":");
//                 System.out.println(nameOfVector.getValue() + "\n");
//             }
                 
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

            Vector vec = new Vector(setOfWordsInSentences, argWord);
            if (!vec.containsBaseWord())
            {
                System.out.println("Cannot compute top-" + jNum + " similarity to " + argWord + ".");
            }
            else
            {
                ArrayList<Pair<String, Double>> similarityRanking = new ArrayList<>();

               Iterator it = vectors.entrySet().iterator();
               while(it.hasNext())
               {
                   Map.Entry<String, Integer> entryPair = (Map.Entry) it.next();
                   if (!entryPair.getKey().equals(vec.getWord()))
                   {
                       Vector compVec = new Vector(setOfWordsInSentences, entryPair.getKey());
                       Pair<String, Double> similarityPair = new Pair<String, Double>(entryPair.getKey(), vec.cosineSimilarity(compVec));
                       similarityRanking.add(similarityPair);
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
