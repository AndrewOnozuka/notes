///////////////////////////////////////////////////////////////////////////////
// Main Class File:    PointTester.java
// File:               Point.java
// Quarter:            CSE 8B Winter 2022
//
// Author:             Ryo Andrew Onozuka
// Email:              ronozuka@.ucsd.edu
// Instructor's Name:  Prof. Gregory Miranda
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * A class to test the Point class.
 *
 * Bugs: None known
 *
 * @ryoandrewonozuka
 */
public class PointTester {
    /**
     * Execution point of testing code for the point class.
     * DO NOT MODIFY METHOD DECLARATION.
     */
    public static void main(String[] args) {
        Point home = new Point(1, 20, "Home");
        Point store1 = new Point(55, 30, "Store");
        Point store2 = new Point(200, 10, "Store");
        Point school = new Point(32, 411, "School");
        // Point temp = new Point(100,5,"Temporary");

        // Checking home
        if (home.getX() == 1) {
            System.out.println("Test 1.1 Passed!");
        }
        if (home.getY() == 20) {
            System.out.println("Test 1.2 Passed!");
        }
        if (home.getType() == "Home") {
            System.out.println("Test 1.3 Passed!");
        }

        // Checking store1
        if (store1.getX() == 55) {
            System.out.println("Test 1.4 Passed!");
        }
        if (store1.getY() == 30) {
            System.out.println("Test 1.5 Passed!");
        }
        if (store1.getType() == "Store") {
            System.out.println("Test 1.6 Passed!");
        }

        // Checking store2
        if (store2.getX() == 200) {
            System.out.println("Test 1.7 Passed!");
        }
        if (store2.getY() == 10) {
            System.out.println("Test 1.8 Passed!");
        }
        if (store2.getType() == "Store") {
            System.out.println("Test 1.9 Passed!");
        }

        // Checking school
        if (school.getX() == 32) {
            System.out.println("Test 1.10 Passed!");
        }
        if (school.getY() == 411) {
            System.out.println("Test 1.11 Passed!");
        }
        if (school.getType() == "School") {
            System.out.println("Test 1.12 Passed!");
        }

        // Calculating Manhattan Distance
        if (home.manhattanDistance(store1) == 64) {
            System.out.println("Test 2.1 Passed!");
        }
        if (home.manhattanDistance(store2) == 209) {
            System.out.println("Test 2.2 Passed!");
        }
        if (home.manhattanDistance(school) == 422) {
            System.out.println("Test 2.3 Passed!");
        }
        if (store1.manhattanDistance(home) == 64) {
            System.out.println("Test 2.4 Passed!");
        }
        if (store1.manhattanDistance(store2) == 165) {
            System.out.println("Test 2.5 Passed!");
        }
        if (store1.manhattanDistance(school) == 404) {
            System.out.println("Test 2.6 Passed!");
        }
        if (store2.manhattanDistance(home) == 209) {
            System.out.println("Test 2.7 Passed!");
        }
        if (store2.manhattanDistance(store1) == 165) {
            System.out.println("Test 2.8 Passed!");
        }
        if (store2.manhattanDistance(school) == 569) {
            System.out.println("Test 2.9 Passed!");
        }
        if (school.manhattanDistance(home) == 422) {
            System.out.println("Test 2.10 Passed!");
        }
        if (school.manhattanDistance(store1) == 404) {
            System.out.println("Test 2.11 Passed!");
        }
        if (school.manhattanDistance(store2) == 569) {
            System.out.println("Test 2.12 Passed!");
        }
        /**
        if (store2.manhattanDistance(temp) == 105) {
            System.out.println("Test 2.13 Passed!");
        }
        */

        // You should write more tests to ensure proper functionality of your code.
        // Test cases for checkSameType
        if (store1.checkSameType(store2) == true) {
            System.out.println("Test 3.1 Passed!");
        }
        if (home.checkSameType(school) == false) {
            System.out.println("Test 3.2 Passed!");
        }
        if (school.checkSameType(store1) == false) {
            System.out.println("Test 3.3 Passed!");
        }
        if (store2.checkSameType(store1) == true) {
            System.out.println("Test 3.4 Passed!");
        }

        // Test cases for checkIfNearby
        if (home.checkIfNearby(store1, 100) == true) {
            System.out.println("Test 4.1 Passed!");
        }
        if (home.checkIfNearby(store1, 50) == false) {
            System.out.println("Test 4.2 Passed!");
        }
        if (home.checkIfNearby(school, 400) == false) {
            System.out.println("Test 4.3 Passed!");
        }
        if (home.checkIfNearby(school, 500) == true) {
            System.out.println("Test 4.4 Passed!");
        }
    }
}
