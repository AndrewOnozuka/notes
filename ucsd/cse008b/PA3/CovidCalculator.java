///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:    PA3Tester.java
// File:               CovidCalculator.java
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
// Online sources:   Oracle Documents regarding Integer Max and Min values.
//                   https://docs.oracle.com/javase/8/docs/api/java/lang/
//                   Integer.html
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * This class allows us to calculate and analyze covid statistics between
 * different states and races.
 *
 * Bugs: Previously had == instead of .equals, which causes some errors.
 *       FIXED NOW
 *
 * @ryoandrewonozuka
 */
class CovidCalculator
{
    private DataPoint[] points;  // The data points to use in the calculations

    /**
    * Makes a new array of the input entries so that we can calculate
    *
    * @param input the input parameters of DataPoint from DataPoint.java
    *              are used here.
    */
    public CovidCalculator(DataPoint[] input)
    {
        // REMOVED the line below and implemented this method so that
        // it makes a new array for points and copies the entries from input
        // into points.
        points = new DataPoint[input.length];
        for(int i = 0; i < input.length; i++)
        {
            this.points[i] = input[i];
        }  
    }

    /**
    * Checks to see if the date matches before updating the average.
    *
    *  @param date Used to check if the given numbers are consistent.
    * @return 0 if count is 0, otherwise returns double sum/cnt. This allows
    *         us to avoid breaking the code when we have a non-integer
    *         average or if the date is not in the subset (refer to
    *         PA3Tester.java lines 169-182)
    */
    public double findAverageCases(String date)
    {
        // REMOVED line below and implement method.
        int sum = 0;
        int cnt = 0;
        for (int i = 0; i < this.points.length; i++)
        {
            if (this.points[i].getDate().equals(date))
            {
                cnt++;
                sum = sum + this.points[i].getTotalCases();
            }
        }
        if (cnt == 0)
        {
            return 0;
        }
        return (double) sum / cnt;
    }

    /**
    * (Write a succinct description of this method here.)
    *
    * @param date
    * @param state 
    * @return race with the highest cases
    */
    public String findRaceWithHighestCases(String date, String state)
    {
        // REMOVED line below and implement method.
        int idx = 0;
        for(int i = 0; i < this.points.length; i++)
        {
            if(this.points[i].getDate().equals(date) &&
                                    this.points[i].getState().equals(state))
            {
                idx = i;
            }
        }
        DataPoint DP = this.points[idx];
        int max = 0;
        int newIdx = 0;
        for(int i = 0; i < DP.numRaces; i++)
        {
            if(DP.getCasesByRace(i) > max)
            {
                max = DP.getCasesByRace(i);
                newIdx = i;
            }
        }
        return DP.getRaceName(newIdx);
    }
    
    // ADDED method myStat here (with comments)
    public String findRaceWithLowestCases(String date, String state)
    {
        int idx = 0;
        for (int i = 0; i < this.points.length; i++)
        {
            if(this.points[i].getDate().equals(date) &&
                                    this.points[i].getState().equals(state))
            {
                idx = i; // line above checks to see if info consistent
            }
        }
        DataPoint DP = this.points[idx];
        int min = Integer.MAX_VALUE; // sets the min to max java value
        int newIdx = 0;
        for(int i = 0; i < DP.numRaces; i++)
        {
            if(DP.getCasesByRace(i) < min)
            {
                min = DP.getCasesByRace(i);
                newIdx = i; // stores index value of new min
            }
        }
        return DP.getRaceName(newIdx); // returns the lowest case race
    }
}
