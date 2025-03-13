///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:              RecursionWarmup.java
// Files:              RecursionWarmup.java
// Quarter:            CSE 8B Winter 2022
//
// Author:             Kameron Gano
// Email:              kgano@ucsd.edu
// Instructor's Name:  Prof. Gregory Miranda
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Pair Partner:        Ryo Andrew Onozuka
// Email:               ronozuka@ucsd.edu
// Instructors's Name:  Prof. Gregory Miranda
// Lab Section:         N/A
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   must fully acknowledge and credit those sources of help.
//                   Instructors and TAs do not have to be credited here,
//                   but roommates, relatives, strangers, etc do.
//
// Persons:          N/A
//
// Online sources:   N/A
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.util.*;

/**
 * RecursionWarmup class to practice recursion exercises. Prints out numbers
 * in their binary forms.
 *
 * Bugs: None known
 *
 * @author Kameron Gano
 * @author Ryo Andrew Onozuka
 */
public class RecursionWarmup {
   
    private static String binaryString = ""; // initializes empty string
    private static String answer;

    /**
     * binaryString method. Turns given numbers into their corresponding
     * binary codes by taking parameter n.
     *
     * @param n number to turn into binary
     * @return answer returns a string of binary equivalent to the
     *         number n given in the parameter
     */
    public static String binaryString(int n)  {

        if ( n > 0 ) {
            if (n % 2 == 1) {
                n = n / 2;
                binaryString = "1" + binaryString;
                answer = binaryString;
                binaryString(n);
            }
            else if ( n % 2 == 0 ) {
                n = n / 2;
                binaryString = "0" + binaryString;
                answer = binaryString;
                binaryString(n);
            }
        }
        else if ( n < 0 ) {
            return "Paramater n must be greater than" +
                   " or equal to zero!";
        }
        else if ( n == 0 ) {
            return "0";
        }
        binaryString = "";
        return answer;
    }

    /**
     * isSubsetSum method. Takes parameters set and targetNumber to check
     * if there are a subset of integers whose sum is equivalent to the
     * targetNumber.
     *
     * @param set set of numbers
     * @param targetNumber number to check with sum of subset
     * @return boolean true or false
     */
    public static boolean isSubsetSum(ArrayList<Integer> set, int targetNumber) {
        // True or false depending on whether or not sum is zero
        boolean sumFound = (targetNumber == 0);
        // True or false depending on whether the array is empty or not
        boolean emptyArray = (set.size() == 0);

        // BASE CASES -- depend on booleans that precede this.
        // Return "true" is sum is found, OR
        // Return "false" if array is empty
        if (sumFound || emptyArray) {
            return sumFound || !emptyArray;
        }

        // Changed array allows us to use a copy of set without altering 
        // original. Additionally, it resets the changed array.
        ArrayList<Integer> changedArray = new ArrayList<Integer>(set);

        // Remove arbitrary first index
        changedArray.remove(0);
    
        // There are two possible solutions: TRUE (sumFound) OR FALSE
        // emptyList, sum NOT FOUND. Return these results accordingly.
    
        return isSubsetSum(changedArray, targetNumber) ||
            // Check 1st index included vs. excluded
            isSubsetSum(changedArray, targetNumber - set.get(0));
        }

        /**
         * main method. Tests to see if our previous RecursionWarmups are
         * working as intended.
         *
         * @param args
         * @return N/A
         */
        public static void main(String[] args) {

        System.out.println("\n---------------------------------------\n");

        System.out.println(binaryString(-1));
        System.out.println(binaryString(0));
        System.out.println(binaryString(1));
        System.out.println(binaryString(6));
        System.out.println(binaryString(12));
        System.out.println(binaryString(17));
        System.out.println(binaryString(100));
        System.out.println(binaryString(6));
        
        System.out.println("\n---------------------------------------\n");

        ArrayList<Integer> set = new ArrayList<Integer>();

        set.add(3);
        set.add(7);
        set.add(1);
        set.add(8);
        set.add(-3);
        set.add(18);

        System.out.println(isSubsetSum(set, 10));
        System.out.println(isSubsetSum(set, 25));
        System.out.println(isSubsetSum(set, 19));
        System.out.println(isSubsetSum(set, -3));
        System.out.println(isSubsetSum(set, 15));
        System.out.println(isSubsetSum(set, -4));

        System.out.println("\n---------------------------------------\n");
    }
}
