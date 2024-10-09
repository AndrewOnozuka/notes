///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:    PA8Tester.java
// File:               Pokeball.java
// Quarter:            CSE 8B Winter 2022
//
// Author:             Kameron Gano
// Instructor's Name:  Prof. Gregory Miranda
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
// Pair Partner:       Ryo Andrew Onozuka
// Email:              ronozuka@ucsd.edu
// Instructor's Name:  Prof. Gregory Miranda
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   fully acknowledge and credit all sources of help,
//                   other than Instructors and TAs.
//
// Persons:          N/A
//
// Online sources:   N/A
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.util.*;

/**
 * The Pokeball class extends the Item class and allows us to set and get the
 * necessary information associated with each Pokeball.
 *
 * Bugs: N/A
 *
 * @author Kameron Gano
 * @author Andrew Onozuka
 */
public class Pokeball extends Item {
    private int performance;

    /**
     * Pokeball method with no parameters. Calls the constructor from the
     * parent class and initializes performance with 0.
     *
     * @return N/A
     */
    public Pokeball() {
        super(); // accesses the superclass
        this.performance = 0; // sets the performance  to 0
    }

    /**
     * Pokeball constructor. Still calls the parent class
     * constructor and initializes values.
     *
     * @param pokeballName name of pokeball
     * @param performanceIn value for performance
     * @return N/A
     */
    public Pokeball(String pokeballName, int performanceIn) {
        super(pokeballName); // accesses the superclass for pokeballName
        this.performance = performanceIn; // initializes with performanceIn
    }

    /**
     * getPerformance method that returns a performance value.
     *
     * @return this.performance performance of given reference
     */
    public int getPerformance() {
        return this.performance; // returns performance
    }

    /**
     * Override toString method that returns the stats of a Pokeball as a
     * string.
     *
     * @return pokeballName name of Pokeball for implicit reference
     * @return showPerformance value of performance of Pokeball
     */
    @Override
    public String toString() {
        String pokeballName = this.getName() + "\n";
        String showPerformance = "performance: " + this.getPerformance() + "\n";
        return pokeballName + showPerformance; // returns string
    }
}