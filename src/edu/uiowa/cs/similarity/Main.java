package edu.uiowa.cs.similarity;

import opennlp.tools.stemmer.*;
import org.apache.commons.cli.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
// first part, the regex is useDelimiter("[.?!]");
    // also works with "\\.||\\?||\\!"
    public static void main(String[] args) throws FileNotFoundException {
        Options options = new Options();
        options.addRequiredOption("f", "file", true, "input file to process");
        options.addOption("h", false, "print this help message");
	options.addOption("s", false, "print sentences");

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
       
	
        String[] wordsList = words.split("[.?!]");
	String[] sentenceList = new String[wordsList.length-1];
	for(int i = 0; i < wordsList.length-1; i++){
		sentenceList[i] = wordsList[i];
	}
	
        PorterStemmer ps = new PorterStemmer();
       
        ArrayList<String[]> listOfWordsInSentences = new ArrayList<>();
        String[] psWords;
        for (int i=0; i<sentenceList.length; i++)
        {
            psWords = sentenceList[i].split(" ");
            for (int j=0; j<psWords.length; j++)
            {
                psWords[j] = ps.stem(psWords[j]);
            }
            listOfWordsInSentences.add(psWords);
        }
        
	
	
	 if (cmd.hasOption("s")) {
	    for (int i=0; i<listOfWordsInSentences.size(); i++)
	    {
                String[] currSentence = listOfWordsInSentences.get(i);
                for (int j=0; j<currSentence.length; j++)
                {
                    System.out.print(currSentence[j] + " ");
                }
            System.out.println();
        }
	    
            System.out.println("Num sentences: ");
	    System.out.println(sentenceList.length);
        }
        
	sc.close();
    }
}
