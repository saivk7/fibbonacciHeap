
import java.util.*;
import java.io.*;
/*
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
*/
public class hashtagcounter{

	public static void main(String[] args) {

		/*
		 	Initializing the MaxFibonacciHeap and Hashtable data structures
		*/
		MaxFibonacciHeap fibHeap = new MaxFibonacciHeap();
		Hashtable<String, Node> hashtag = new Hashtable<>();

		/*
			Initializing the Map DataStructure to map a hashtag and the corresponding node the MaxFibonacciHeap
		*/
		Map<String, Integer> map = null;

		
		String inFile = args[0];				/* storing the input File in inFile string variable */
		String outFile="";		/* storing the output File into outFile string variable */
		if(args.length==2) {
			outFile = args[1];
		}

		String inputLine = null;				/*  inputLine string variable to store each line from the input File */
		

		try {
			
			FileReader fileReader = new FileReader(inFile);/* Initalizing the FileReader to read input files.*/
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("output.txt"),"utf-8"));
			
			if(!outFile.equals("")) {
				bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"utf-8"));
			}
			/*While loop to read each line from input File and to detect "STOP" in input file. */
		
			while ((inputLine = bufferedReader.readLine()).equalsIgnoreCase(new String("STOP")) == false) {

				map = new HashMap<String, Integer>(); 
				
				String[] splitLine = inputLine.split(" ");  /* Spliting the  input Line to extract hashtag and its respective frequency */

				char hashCheck = splitLine[0].charAt(0);	/* If condition to check if '#' is present or not*/
				if (hashCheck == '#') {
					/* If '#' is present in the input Line, Discard '#' from the hashtag */
					String hashTag = splitLine[0].substring(1); 
					
					int frequency = Integer.parseInt(splitLine[1]);/* Parsing the frequency from input string line to integer*/

					// Update : Increase frequency of the hashtag if already
					// present in the hashtable
					if (hashtag.containsKey(hashTag)) {
						// Parse the hashtable to get the address of the
						// corresponding hashtag
						Node key = hashtag.get(hashTag);
						// Increase the frequency of the hashtag and exit
						// from loop
						fibHeap.increaseKey(key, (key.getValue() + frequency));
					}
					// Add the new hashtag into the hashtable along with the
					// address to a node with its frequency in the Fibonacci
					// Heap
					else {
						Node adres = fibHeap.createNode(frequency);//
						hashtag.put(hashTag, adres);
					}
				}

				// Display hashtags with most frequency

					
				else if (splitLine[0].matches("\\d+")) { /* Using regex to check if the input Line has digits */
					Node adres = null;
					
					int numOfHashtags = Integer.parseInt(splitLine[0]); /*Parsing the digit and storing in numOfHashtags integer variable */

					
					for (int i = 0; i < numOfHashtags - 1; i++) { /* Running for loop to */
						adres = fibHeap.removeMax();

						// Get hashtag corresponding to the frequency and write
						// into the output file - comma separated
						for (String key : hashtag.keySet()) {
							if (hashtag.get(key) == adres) {
								if(outFile.equals("")) {
									System.out.print(key + ",");
								}
								else {
									bufferedWriter.write(key +",");
								}
								
								map.put(key, adres.getValue());
								break;
							}
						}
						// Store the hashtag along with its frequency to
						// reinsert

					}
					adres = fibHeap.removeMax();
					for (String key : hashtag.keySet()) {
						if (hashtag.get(key) == adres) {
							// Write newline for next query
							if(outFile.equals("")) {
								System.out.print(key + "\n");
							}
							else {
								bufferedWriter.write(key + "\n");
							}
							
							map.put(key, adres.getValue());
							hashtag.remove(key);
							break;
						}
					}
					adres = null;

				
					for (String s : map.keySet()){/*Reinserting the hashtags that are removed using removeMax for next query*/
						adres = fibHeap.createNode(map.get(s));
						hashtag.put(s, adres);
					}

					map = null; 				/* Clearing map to accomidate the query next  */
				} else
					continue;
			}

			bufferedReader.close();				/* closing the bufferedReader,bufferedWriter */
			bufferedWriter.close();

		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + inFile + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + inFile + "'");
		}
	}

}
