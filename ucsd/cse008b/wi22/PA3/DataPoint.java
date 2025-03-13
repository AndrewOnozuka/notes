///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:    PA3Tester.java
// File:               DataPoint.java
// Quarter:            Winter 2022
//
// Author:             Ryo Andrew Onozuka
// Email:              ronozuka@ucsd.edu
// Instructor's Name:  Prof. Gregory Miranda
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
// Pair Partner:        N/A
// Email:               N/A
// Instructors's Name:  N/A
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


/**
 * Stores important information like the date, state, total cases, and cases by
 * race in data points so that we can use it to make calculations in
 * CovidCalculator.java and test it in PA3Tester.java.
 *
 * Bugs: N/A
 *
 * @ryoandrewonozuka
 */
public class DataPoint
{
    // The number and races represented in this data point.  DO NOT CHANGE.
    public int numRaces = 8;
    private String[] races = {"White", "Black", "LatinX", "Asian", "AIAN",
                              "NHPI", "Multiracial", "Other"};
    // ADDED private member variables here.
    private String date;
    private String state;
    private int totalCases;
    private int[] casesByRace;

    /**
    * Initializes data points for the constructor
    *
    * @param dateIn initializes date
    * @param stateIn initializes state
    * @param totalCasesIn initializes totalCases
    * @param casesByRaceIn initializes casesByRace
    */
    public DataPoint(String dateIn, String stateIn,
                    int totalCasesIn, int[] casesByRaceIn)
    {
        // DONE
        this.date = dateIn;
        this.state = stateIn;
        this.totalCases = totalCasesIn;
        this.casesByRace = casesByRaceIn;
    }

    /**
    * Gets the date for a given data point
    *
    * @return the date of the implicit reference
    */
    public String getDate()
    {
        // CHANGED the line below
        return this.date;
    }

    /**
    * Gets the state for a given data point
    *
    * @return the state of the implicit reference
    */
    public String getState()
    {
        // CHANGED the line below
        return this.state;
    }

    /**
    * Out of an array of expected cases for each race, getCasesByRace returns
    * the number of cases for a race using it's index position.
    *
    * @param raceIndex describes each race with an index
    * @return the number of cases in the implicit reference for the race
    * corresponding to the given index
    */
    public int getCasesByRace(int raceIndex)
    {
        // CHANGED the line below and add more code.
        return this.casesByRace[raceIndex];
    }

    /**
    * Gets the total cases for a given data point
    *
    * @return totalCases of "this" implicit reference
    */
    public int getTotalCases()
    {
        // CHANGED the line below
        return this.totalCases;
    }

    /**
    * Return the race name associated with the given index in this data point.
    * Preconditions: index is between 0(inclusive) and numRaces (8)(exclusive)
    *
    * DO NOT CHANGE.
    *
    * @param index The index of the race.
    * @return The name of the race (e.g. "White" or "LatinX")
    */
    public String getRaceName(int index)
    {
        return this.races[index];
    }
}
