///////////////////////////////////////////////////////////////////////////////
// Main Class File:    EasterEggTester.java
// File:               EasterEgg.java
// Quarter:            CSE 8B Winter 2022
//
// Author:             Ryo Andrew Onozuka
// Email:              ronozuka@ucsd.edu
// Instructor's Name:  Prof. Gregory Miranda
//////////////////////////// 80 columns wide //////////////////////////////////

import java.io.IOException;
import java.util.*;

/**
 * A class that helps strategize 
 * for the EasterEgg race.
 *
 * Bugs: Something is weird about `recursiveSum`...
 *
 * @author Ryo Andrew Onozuka
 */
public class EasterEgg {
	
    /**
     * Generates an array of scores based 
     * on the elements in the eggs ArrayList
     * 
     * @param eggs - an ArrayList of Strings that denote the type of egg
     */
	public int[] calculateScores(ArrayList<String> eggs) throws Exception {
        int scoreArray [] = new int [eggs.size()];
        try {
            for (int i = 0; i < eggs.size(); i++) {
                if (eggs.get(i).equals("Chocolate")) {
                    scoreArray[i] = (i+1)*10;
                }
                if (eggs.get(i).equals("Golden")) {
                    scoreArray[i] = (i+1)*20;
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid Easter Egg");
        }
        return scoreArray;
	}
    
    /**
     * Recursively calculates the highest score possible
     * 
     * @param scores[] - integer array of all the scores of each egg
     * @param idx - index of first or index of last element of scores
     *  (depending on your implementation.)
     */
    public int maxScore(int[] scores, int idx) {
        if (idx == scores.length - 1) {
            return scores[idx];
        }
        if (idx == scores.length - 2) {
            return Math.max(scores[idx + 1], scores[idx]);
        } else {
            return Math.max(maxScore(scores, (idx + 1)), scores[idx] + maxScore(scores, (idx + 2)));
        }
    }

    /**
     * Recursively calculates the sum of numbers from 1 to the 
     * parameter `num`, but there is an error...
     * 
     * @param num - number up to which the sum is calculated 
     */
    public int recursiveSum(int num) {
        if (num == 0) {
            return num;
        }
        return num + recursiveSum(num - 1);
    }
}
