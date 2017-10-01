///////////////////////////////////////////////////////////////////////////////
// Title:            Huffman Coding
// Files:            FrequencyParser.java
//					 MinPriorityQueue.java
// Semester:         CS367 Fall 2016
//
// Author:           Jiayue Sheng
// Email:            jsheng7@wisc.edu
// CS Login:         jsheng
// Lecturer's Name:  Deb Deppeler
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
// Pair Partner:     Conrad Chen
// Email:            wchen283@wisc.edu
// CS Login:         conradc
// Lecturer's Name:  Deb Deppeler
//

import java.io.*;
import java.util.*;

public class FrequencyParser{
    /**
     * This method takes a text file containing ASCII characters only and
     * returns an int array of length 128 which counts the occurrences of each
     * character.  
     * 
     * The entry at index i is the count of the character with ASCII
     * value i 'Start of Heading' (ASCII value 1) and null character (ASCII
     * value 0) should both have count 0.
     * 
     * @param file 
     *  the name of the file containing the mapping from symbols to codes
     * 
     * @return the array of frequencies of each character
     * 
     * @throws FileNotFoundException 
     *  if the file does not exist
     */
    public static int[] getFrequencies(String file)throws FileNotFoundException{
       
        // TODO
        if(!new File(file).exists()){
        	throw new FileNotFoundException();
        }  // If the file is not found, throw a FileNotFoundException
        Scanner input = new Scanner(new File(file));
        int[] result = new int[128]; // The result array to be returned
        String temp = "";     
        // The string that stores one line of file content
        
        // Count the occurence of each character and add them to the result array
        while(input.hasNextLine()){
        	temp = input.nextLine();
        	result[10]++;
        	//increment the frequency for new line character
        	for(int i = 0;i < temp.length();i ++){ 
        		// Check each character in the line
        		char check = temp.charAt(i); 
        		// The character to be checked
    			int asciiVal = (int)check;  
    			// The Ascii value of the character
        		result[asciiVal]++;//increment frequency for the checked char

        		}
        	}  
        
        return result;
    }
}
