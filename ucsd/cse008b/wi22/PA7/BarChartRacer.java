
///////////////////////////////////////////////////////////////////////////////
// Main Class File:    BarChartRacer.java
// File:               BarChartRacer.java
// Quarter:            CSE 8B Winter 2022
//
//////////////////////////// 80 columns wide //////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:              CSE8B_PA7
// Files:              BarChartRacer.java, BarChart.java, Bar.java
// Quarter:            CSE8B Winter 2022
//
// Author:             Kameron Gano
// Email:              kgano@ucsd.edu
// Instructor's Name:  Prof Gregory Miranda
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
//                  CHECK ASSIGNMENT PAGE TO see IF PAIR-PROGRAMMING IS ALLOWED
//                  If pair programming is allowed:
//                  1. Read PAIR-PROGRAMMING policy
//                  2. Choose a partner wisely
//                  3. Complete this section for each program file
//
// Pair Partner:        Andrew Onozuka
// Email:               ronozuka@ucsd.edu
// Instructors's Name:  Prof Gregory Miranda
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   must fully acknowledge and credit those sources of help.
//                   Instructors and TAs do not have to be credited here,
//                   but roommates, relatives, strangers, etc do.
//
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
//
// Online sources:   Avoid web searches to solve your problems, but if you do
//                   search, be sure to include Web URLs and description of
//                   of any information you find.
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * Driver to create a video/multiple BarCharts successively and display
 * them to the user.
 * Run command: java BarChartRacer <input_file> <num entries per chart>
 * For example: java BarChartRacer cities-usa.txt 10
 *
 * Bugs: None known
 *
 * @author Kameron Gano
 * @author Ryo Andrew Onozuka
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.*;
import java.io.*;
import java.lang.management.PlatformLoggingMXBean;

public class BarChartRacer {

    // a tracker for the year/date of the current chunk
    private static String caption; // From setCaption

    /**
     * Creates and returns an ArrayList of chunks of data.
     *
     * @param Scanner uses the scanner to get info
     * @return barList
     */
    private static ArrayList<Bar> getBars(Scanner in) {
        ArrayList<Bar> barList = new ArrayList<Bar>();

            int numEntriesInChunk = in.nextInt();
            in.nextLine();
            int j = 0;
            // while loop to iterate through each chunk
            while ( j < numEntriesInChunk ) {
                String rowData = in.nextLine();              
                String[] splitRow = new String[rowData.split(",").length];
                splitRow = rowData.split(",");
                // sets the caption to year/date
                caption = splitRow[0];
                // sets name
                String name = splitRow[1] + ", " + splitRow[2];
                // sets category
                String category = splitRow[4];
                int value = Integer.parseInt(splitRow[3]);
                // System.out.println(caption + " - " + name + " - "
                // + category + " - " + value);
                Bar rowBar = new Bar(name, value, category);
                barList.add(rowBar);
                j++;            
            }
        return barList; 
    }

    /**
     * Adds the ArrayList for all of the bars to the given chart.
     *
     * @param chart used to add bar ArrayLists to chart
     * @param bars used to compare bar sizes
     * @param numEntries checks to see if entries are valid
     */
    private static void createChart(BarChart chart, ArrayList<Bar> bars,
                                    int numEntries) {
        // Ensure the numEntries isnt greater than
        // The amount of bars 
        if ( numEntries > bars.size() ) {
            System.out.println("ERROR: numEntries is greater than size of list.");
            return;
        }
        // Set chart caption
        chart.setCaption(caption);
        // Add Bars from "bars" to BarChart object
        for (int i = bars.size() - 1; i > bars.size() - numEntries - 1; i-- ) {
            String name = bars.get(i).getName();
            int value = bars.get(i).getValue();
            
            chart.add(name, value, i);
        }
    }

    /**
     * Takes input file as a command line argument and uses a Scanner to
     * read in title, xAxisLabel, and dataSource for the graph. The bars are
     * then sorted, so that the chart can be created.
     */
    public static void main(String[] args) throws FileNotFoundException,
                                                  InputMismatchException {
        // do not modify these two lines
        StdDraw.setCanvasSize(1000, 700);
        StdDraw.enableDoubleBuffering();

        // getBars
        if (args.length == 2 ) {
            
            FileInputStream fileByteStream = null;
            Scanner fileScanner = null;
    
            try {
                
                String fileName = args[0];
                int numEntries = Integer.parseInt(args[1]);

                if ( (numEntries <= 0) ) {
                    System.out.println("Ensure numEntries is positive and nonzero");
                    return;
                }

                fileByteStream = new FileInputStream(fileName);
                fileScanner = new Scanner(fileByteStream);
                
                // first three lines are title, xAxisLabel,
                // and dataSource
                String title = fileScanner.nextLine();
                String xAxisLabel = fileScanner.nextLine();
                String dataSource = fileScanner.nextLine();

                // Shift to next line before getting bars
                fileScanner.nextLine();
                
                BarChart chart = new BarChart(title, xAxisLabel, dataSource);
                // For each chunk of data, create a BarChart object and draw it
                while ( fileScanner.hasNextLine() ) {
                    // Get bars for chunk of bars
                    ArrayList<Bar> barChunkList = getBars(fileScanner);
                    // Sort this chunk of bars least to greatest
                    Collections.sort(barChunkList);
                    
                    // create a chart with the barChart, the chunk of bars,
                    // and the amount of entries to display
                    createChart(chart, barChunkList, numEntries);
    
                    // draw the barchart object
                    chart.draw(); // here chart is your BarChart object
                    // show the barchart
                    StdDraw.show();
                    // pause for viewing
                    StdDraw.pause(1);
                    // reset bars for new set of bars
                    chart.reset();
                    
                } 
                fileScanner.close();
                chart.reset();
    
                // do not modify these lines
                // clear and redraw chart (you need to do this for every graph you create)
                
    
    
            } catch ( Exception e ) {
                System.out.print("Could not show bar chart; too many or too few");
                System.out.print(" arguments. Ensure that command line follows the");
                System.out.println(" format:\n");
                System.out.println("\t\t\tjava BarChartRacer [file] [numEntries]\n");
                System.out.println("and that [file] exists.");
            }
        }
    }
}
