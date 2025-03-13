import java.nio.channels.NonReadableChannelException;

///////////////////////////////////////////////////////////////////////////////
// Main Class File:    CovidConditionTester.java
// File:               CovidCondition.java
// Quarter:            CSE 8B Winter 2022
//
// Author:             Ryo Andrew Onozuka
// Email:              ronozuka@ucsd.edu
// Instructor's Name:  Prof. Gregory Miranda
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * A class to represent the current condition of a State with respect to COVID.
 *
 * Bugs: None known
 *
 * @ryoandrewonozuka
 */
public class CovidCondition {
    // DO NOT CHANGE THE CODE FOR ANY OF THESE VARIABLES.
    private String stateName; // The name of the US State.
    private int avgCases; // The number of average cases per day (in thousands).

    /**
     * Constructor to initialize the CovidCondition of a state.
     * DO NOT MODIFY CONSTRUCTOR DECLARATION.
     *
     * @param stateName the name of the state.
     * @param avgCases the average amount of cases per day (in thousands).
     */
    public CovidCondition(String stateName, int avgCases)
    {
        // TODO: Complete this constructor.
        this.stateName = stateName;
        this.avgCases = avgCases;
    }

    /**
     * Report the details of this object as a String.
     *
     * @return a string with the state name
     *   and the average number of cases.
     */
    public String getDetails() {
        // TODO: Implement this method.
        // System.out.println(this.stateName + " currently has a daily average of " + this.avgCases + " thousand cases");
        return (this.stateName + " currently has a daily average of " + this.avgCases + " thousand cases");
    }

    /**
     * Update the average number of cases.
     *
     * @param numOfCases - number of cases to be updated.
     *
     */
    public void updateAvgCases(int numOfCases)
    {
        // TODO: Implement this method.
        this.avgCases = numOfCases;
    }

    /**
     * Check which tier the state belongs to.
     * There are 4 tiers: Yellow, Orange, Red, and Purple
     *
     * @return the current tier for the state
     */
    public String checkTier() {
        // TODO: Implement this method.
        if (this.avgCases < 10) {
            // System.out.println("Yellow");
            return "Yellow";
        }
        else if (10 <= this.avgCases & this.avgCases <= 50) {
            // System.out.println("Orange");
            return "Orange";
        }
        else if (51 <= this.avgCases & this.avgCases <= 100) {
            // System.out.println("Red");
            return "Red";
        }
        else {
            // System.out.println("Purple");
            return "Purple";
        }
    }
}