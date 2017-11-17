package edu.uiowa.cs.similarity;

import opennlp.tools.stemmer.*;
import org.apache.commons.cli.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
// first part, the regex is useDelimiter("[.?!]");
    // also works with "\\.||\\?||\\!"
    public static void main(String[] args) throws FileNotFoundException {
        Options options = new Options();
        options.addRequiredOption("f", "file", true, "input file to process");
        options.addOption("h", false, "print this help message");

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
                
//        String filenameProhibited = cmd.getOptionValue(filename);
//		if (!new File(filenameProhibited).exists()) {
//			System.err.println("file does not exist "+filenameProhibited);
//			System.exit(1);
//		}
                
        File file = new File(filename);
        
//        File fileProhib = new File("C:\\Users\\User\\OneDrive - University of Iowa\\2017 Fall Semester\\Classes\\CS 2\\HW\\FinalProj\\project-team-1\\stopwords.txt");
	File fileProhib = new File("/Users/Harsh/Desktop/3rd Year First Semester/Data Sturctures/git/semantic-similarity/stopwords.txt");
                

        if (cmd.hasOption("h")) {
            HelpFormatter helpf = new HelpFormatter();
            helpf.printHelp("Main", options, true);
            System.exit(0);
        }
        
        Scanner scanProhib = new Scanner(fileProhib);
        List<String> prohibWords = new ArrayList<>();
        
        scanProhib.useDelimiter("\n");
        while (scanProhib.hasNext())
        {
            prohibWords.add(scanProhib.next());
        }
        
        Scanner sc = new Scanner(file);
        String words = "";
        while(sc.hasNext())
        {
            words += sc.next() + " ";
        }
        words = words.toLowerCase();
        words = words.replaceAll("[,\\-\\:\\;\\'\"]", "");
	
        for (int i=0; i<prohibWords.size(); i++)
        {	
            words = words.replaceAll("\\s*\\b"+prohibWords.get(i)+"\\b\\s*", " ");
        }
        
        String[] wordsList = words.split("[.?!]");

        PorterStemmer ps = new PorterStemmer();
        
        //ArrayList<String[]> word = new ArrayList<>();
        
        
        
        for (int i=0; i<wordsList.length; i++)
        {
            wordsList[i] = ps.stem(wordsList[i]);
            System.out.println(wordsList[i]);
        }
        
//        for (int i=0; i<wordsList.length; i++)
//        {
//            word.add(wordsList[i].split(" "));
       // }
        
//        System.out.print("[");
//        for (int i=0; i<word.size(); i++)
//        {
//            
//            String[] currWords = word.get(i);
//            
//            for (int j=0; j<currWords.length; j++)
//            {
//                System.out.print(currWords[j] + ",");
//            }
//            System.out.print("],");
//        }
//        System.out.println("]");
        
//        sc.useDelimiter("[.?!]");
        
//        System.out.println(words);
//        words = words.toLowerCase();
//        
//        words = words.replaceAll(" a ", " ");
//        System.out.println(words);
        
        sc.close();
    }
}
