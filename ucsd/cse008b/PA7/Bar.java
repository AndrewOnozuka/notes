///////////////////////////////////////////////////////////////////////////////
// Main Class File:    BarChartRacer.java
// File:               Bar.java
// Quarter:            CSE 8B Winter 2022
//
// Author:             Kameron Gano, kgano@ucsd.edu
// Instructor's Name:  Prof Gregory Miranda
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
// Pair Partner:       Andrew Onozuka
// Email:              ronozuka@ucsd.edu
// Instructor's Name:  Prof Gregory Miranda
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   fully acknowledge and credit all sources of help,
//                   other than Instructors and TAs.
//
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
//
// Online sources:   Avoid web searches to solve your problems, but if you do
//                   search, be sure to include Web URLs and description of
//                   of any information you find.
//////////////////////////// 80 columns wide //////////////////////////////////
/**
 * Implementation to create a single Bar in a BarChart
 *
 * Bugs: None known
 *
 * @author Kameron Gano
 * @author Ryo Andrew Onozuka
 */

public class Bar implements Comparable<Bar> {
    private String name;
    private int value;
    private String category;

    /**
     * Bar constructor using the following parameters:
     *
     * @param name
     * @param value
     * @param category
     */
    public Bar(String name, int value, String category) {
        this.name = name;
        this.value = value;
        this.category = category;
        // initializes variables
    }

    /**
     * Returns the name of the Bar.
     *
     * @param name
     */
    public String getName() {
        return this.name; // returns the name of "this" Bar
    }

    /**
     * Returns the value of the Bar.
     *
     * @param value
     */
    public int getValue() {
        return this.value; // returns the value of "this" Bar
    }

    /**
     * Returns the category of the Bar.
     *
     * @param category
     */
    public String getCategory() {
        return this.category; // returns category of "this" Bar
    }

    /**
     * Compares value of "this" to the value of "that".
     * Used to determine which is bigger out of two Bars.
     *
     * @param that
     * @return -1,0,1
     */
    public int compareTo(Bar that) {
        if (this.getValue() < that.getValue()) { 
            return -1; // if "this" greater than "that" return -1
        }
        else if (this.getValue() == that.getValue()) {
            return 0; // if "this" equal to "that" return 0
        }
        else {
            return 1; // if "this" less than "that" return 1
        }
    }
}