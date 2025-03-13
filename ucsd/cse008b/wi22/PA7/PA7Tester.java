///////////////////////////////////////////////////////////////////////////////
// Main Class File:    BarChartRacer.java
// File:               PA7Tester.java
// Quarter:            CSE 8B Winter 2022
//
//
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * Sample tests to check if other classes work as intended
 *
 * Bugs: None known
 *
 * @author your name
 */

import java.util.*;

public class PA7Tester {
    // sample client
    public static void main(String[] args) {

        // BAR.JAVA TESTS
        System.out.println("*****TESTING BAR.JAVA*****");
        Bar test1 = new Bar("San Diego, California", 10, "Southwest");
        System.out.println("\nFirst bar data: ");
        System.out.println(test1.getName() + ", " + test1.getCategory() + ", " + test1.getValue());

        Bar test2 = new Bar("Plymouth Rock, Massachussetts", 11, "Northeast");
        System.out.println("\nSecond bar data: ");
        System.out.println(test2.getName() + ", " + test2.getCategory() + ", " + test2.getValue());

        Bar test3 = new Bar("Atlanta, Georgia", 10, "Southeast");
        System.out.println("\nThird bar data: ");
        System.out.println(test3.getName() + ", " + test3.getCategory() + ", " + test3.getValue());

        System.out.println("\nTesting compareTo");
        System.out.println("Comparing bar test 1 (value 10) to bar test 2 (value 11)");
        if ( test1.compareTo(test2) != -1) {
            System.out.println("1st compareTo test failed!");
        }
        else {
            System.out.println("Result: " + test1.compareTo(test2));
        }
        
        System.out.println("Comparing bar test 1 (value 10) to bar test 3 (value 10)");
        if ( test1.compareTo(test3) != 0) {
            System.out.println("2nd compareTo test failed!");
        }
        else {
            System.out.println("Result: " + test1.compareTo(test3));
        }

        System.out.println("Comparing bar test 2 (value 11) to bar test 3 (value 10)");
        if ( test2.compareTo(test3) != 1) {
            System.out.println("3rd compareTo test failed!");
        }
        else {
            System.out.println("Result: " + test2.compareTo(test3));
        }


        // TODO: Add BarChart.java tests
        // BARCHART.JAVA TESTS
        System.out.println("*****TESTING BARCHART.JAVA*****");

        BarChart testChart = new BarChart("CSE8B", "Fun", "Source: me.com");

        testChart.setCaption("Dopest class");

        testChart.add(test1.getName(), test1.getValue(), 0);
        testChart.add(test2.getName(), test2.getValue(), 1);
        testChart.add(test3.getName(), test3.getValue(), 2);

        //testChart.draw();

        testChart.reset();

        // RESETTING testChart, ADDING NEW BAR OBJECTS
        Bar testChartBar1 = new Bar("Newark, New Jersey", 5000, "North east");
        Bar testChartBar2 = new Bar("New Haven, Connecticut", 3500, "North east");
        Bar testChartBar3 = new Bar("Seattle, Washington", 3000, "Northwest");

        testChart.setCaption("US Cities - Test");
        
        testChart.add(testChartBar1.getName(), testChartBar1.getValue(), 17);
        testChart.add(testChartBar2.getName(), testChartBar2.getValue(), 18);
        testChart.add(testChartBar3.getName(), testChartBar3.getValue(), 19);

        testChart.draw();
        
        // BarChartRacer.java tests can directly be run in it's own main method
        // once Bar and BarChart work correctly.
    }
}
