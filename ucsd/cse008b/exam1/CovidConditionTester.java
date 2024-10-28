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
 * A class to test the CovidCondition class.
 *
 * Bugs: None known
 *
 * @ryoandrewonozuka
 */
public class CovidConditionTester {
    /**
     * Execution point of testing code for the CovidCondition class.
     * DO NOT MODIFY METHOD DECLARATION.
     */
    public static void main(String[] args) {
        CovidCondition cali = new CovidCondition("California", 76);
        CovidCondition oregon = new CovidCondition("Oregon", 23);

        CovidCondition state1 = new CovidCondition("California", 120);
        CovidCondition state2 = new CovidCondition("Utah", 10);

        state1.getDetails();
        if (state1.getDetails().equals("California currently has a daily average of 120 thousand cases")) {
            System.out.println("Video Example 1 Passed!");
        }

        state2.checkTier();
        if (state2.checkTier().equals("Orange")) {
            System.out.println("Video Example 2 Passed!");
        }

        // Checking `cali.getDetails()` is what I expect.
        // Expecting to see "California currently has a daily average of 76 thousand cases".
        // So if the String is anything else, then the test has failed.

        /**
        if (cali.getDetails().equals("California currently has a daily average of 76 thousand cases")) {
            System.out.println("Test 1.1 Passed!");
        }
        if (oregon.getDetails().equals("Oregon currently has a daily average of 23 thousand cases")) {
            System.out.println("Test 1.2 Passed!");
        }

        // Test cases for updateAvgCases()
        cali.updateAvgCases(86);
        if (cali.getDetails().equals("California currently has a daily average of 86 thousand cases")) {
            System.out.println("Test 2.1 Passed!");
        }
        oregon.updateAvgCases(28);
        if (oregon.getDetails().equals("Oregon currently has a daily average of 28 thousand cases")) {
            System.out.println("Test 2.2 Passed!");
        }

        // Checking `oregon.checkTier()` is "Orange".
        // If it is not "Orange", then the test has failed.
        if (oregon.checkTier().equals("Orange")) {
            System.out.println("Test 3.1 Passed!");
        }
        if (cali.checkTier().equals("Red")) {
            System.out.println("Test 3.2 Passed!");
        }

        // You should write more tests to ensure proper functionality of your code.
        oregon.updateAvgCases(-10); 
        if (oregon.getDetails().equals("Oregon currently has a daily average of -10 thousand cases")) {
            System.out.println("Test 4.1 Passed!");
        }
        if (oregon.checkTier().equals("Yellow")) {
            System.out.println("Test 4.2 Passed!");
        }
        */
    }
}
