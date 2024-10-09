///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:              PA6.java
// Files:              PA6.java, RedditDataPoint.java, Reddit_Data.csv
// Quarter:            Winter 2022
//
// Author:             Kameron Bahmanyar Gano
// Email:              kgano@ucsd.edu
// Instructor's Name:  Prof. Gregory Miranda
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
//                  CHECK ASSIGNMENT PAGE TO see IF PAIR-PROGRAMMING IS ALLOWED
//                  If pair programming is allowed:
//                  1. Read PAIR-PROGRAMMING policy
//                  2. Choose a partner wisely
//                  3. Complete this section for each program file
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

import java.util.Scanner;

import java.io.*;
import java.util.*;
import java.io.BufferedReader;

/**
 * PA6.java works to sort through a large csv file where there is a lot of
 * information. The goal is to return relevant information to back meaningful
 * analysis of the data. In this PA we are looking at the frequency of
 * certain pronouns and their relation to what type of post it was classified
 * under.
 *
 * @kamgano 
 * @ryoandrewonozuka
 * 
 */
public class PA6 {
    // PROVIDED CONSTANT
    private static final String DELIMITER = ",";        // CSV file delimiter 
    private static final int NAME_INDEX = 0;            //A
    private static final int TEXT_INDEX = 114;          //DK
    private static final int LEX_LIWC_I_INDEX = 19;     //T
    private static final int LEX_LIWC_WE_INDEX = 20;    //U
    private static final int LEX_LIWC_SHEHE_INDEX = 22; //W

    /**
     * this method reads through all of the data on a given csv file and
     * created a new ArrayList to store the data.
     *
     * @param fileName
     * @param categories
     * @return data
     */
    public static ArrayList<RedditDataPoint> readData(String fileName, 
                            ArrayList<String> categories) throws IOException{
        // Create new ArrayList to store the data from the file

        Scanner fileScanner = null;
        FileInputStream fileByteStream = null;
        ArrayList<RedditDataPoint> data = new ArrayList<RedditDataPoint>();
        
        try {

            fileByteStream = new FileInputStream(fileName);
            fileScanner = new Scanner(fileByteStream);
            fileScanner.nextLine(); // Skip titles

            while ( fileScanner.hasNext() ) {
               String[] splitCSVRow = fileScanner.nextLine().split(DELIMITER);
               String rowName = splitCSVRow[NAME_INDEX];

               if ( !categories.contains(rowName)) {
                   categories.add(rowName);
               } // if the category does not repeat, add

               data.add(new RedditDataPoint(splitCSVRow[NAME_INDEX],
                                            splitCSVRow[TEXT_INDEX], 
                        Double.parseDouble(splitCSVRow[LEX_LIWC_I_INDEX]), 
                        Double.parseDouble(splitCSVRow[LEX_LIWC_WE_INDEX]), 
                        Double.parseDouble(splitCSVRow[LEX_LIWC_SHEHE_INDEX])));

            }
        } catch ( IOException e ) {
            System.out.println("IO Error :(");
        }
        return data;       
    }

    /**
     * this method prints the number of total posts for each reddit thread.
     *
     * @param data
     * @param categories
     * @return N/A since there is a print statement in the for loop.
     */
    public static void printTotalPosts(ArrayList<RedditDataPoint> data,
                                       ArrayList<String> categories) {
        // Select  a category out of the categories list
       for ( int categoriesIndex = 0; categoriesIndex < categories.size();
                                      categoriesIndex++ ) {
           String currentCategory = categories.get(categoriesIndex);
           int postCount = 0;
           // Select a row of data from index of data ArrayList
           for ( int dataIndex = 0; dataIndex < data.size(); dataIndex++ ) {
               String currentSubReddit = data.get(dataIndex).getName();

               // Check if select category matches the name associated
               // with row of data Array list
               if ( currentCategory.equals(currentSubReddit) ) {
                   postCount++; // increment count
               }
           }
           System.out.println("r/" + currentCategory + 
                              " total posts: " + postCount); //print result
       }
      
    }

    /**
     * this method prints the number of total posts for each reddit thread
     * with the use of pronoun "I".
     *
     * @param data
     * @param categories
     * @return N/A since there is a print statement in the for loop.
     */
    public static void printTotalLexLiwcI(ArrayList<RedditDataPoint> data,
                                          ArrayList<String> categories) {
        // Select  a category out of the categories list
        for ( int categoriesIndex = 0; categoriesIndex < categories.size(); 
                                       categoriesIndex++ ) {
            String currentCategory = categories.get(categoriesIndex);
            int postCount = 0;
            // Select a row of data from index of data ArrayList
            for ( int dataIndex = 0; dataIndex < data.size(); dataIndex++ ) {
                String currentSubReddit = data.get(dataIndex).getName();
                Double currentLex_LIWC_I = data.get(dataIndex).getLexLiwcI();
                                            // get lexical score of "I"
                // Check if select category matches the name associated
               // with row of data Array list and I, me, or mine occurs in
               // post
                if ( currentCategory.equals(currentSubReddit) &&
                     currentLex_LIWC_I > 0 ) {
                    postCount++; // increment count
                }
            }
            System.out.println("r/" + currentCategory + 
                               " total posts: " + postCount); //print result
        }
    }

    /**
     * this method prints the number of total posts for each reddit thread
     * with the use of pronoun "we".
     *
     * @param data
     * @param categories
     * @return N/A since there is a print statement in the for loop.
     */
    public static void printTotalLexLiwcWe(ArrayList<RedditDataPoint> data, 
                                           ArrayList<String> categories) {
        // Select  a category out of the categories list
        for ( int categoriesIndex = 0; categoriesIndex < categories.size(); 
                                       categoriesIndex++ ) {
            String currentCategory = categories.get(categoriesIndex);
            int postCount = 0;
            // Select a row of data from index of data ArrayList
            for ( int dataIndex = 0; dataIndex < data.size(); dataIndex++ ) {

                String currentSubReddit = data.get(dataIndex).getName();
                Double currentLex_LIWC_WE = data.get(dataIndex).getLexLiwcWe();
                                            // get lexical score of "we"
                // Check if select category matches the name associated
               // with row of data Array list and We occurs in
               // post
                if ( currentCategory.equals(currentSubReddit) &&
                     currentLex_LIWC_WE > 0 ) {
                    postCount++; // increment count
                }
            }
            System.out.println("r/" + currentCategory + 
                              " total posts: " + postCount); //print result
        }
        
    }

    /**
     * this method prints the number of total posts for each reddit thread
     * with the use of pronoun "she/he".
     *
     * @param data
     * @param categories
     * @return N/A since there is a print statement in the for loop.
     */
    public static void printTotalLexLiwcShehe(ArrayList<RedditDataPoint> data, 
                                              ArrayList<String> categories) {
        // Select  a category out of the categories list
        for ( int categoriesIndex = 0; categoriesIndex < categories.size(); 
                                       categoriesIndex++ ) {
            String currentCategory = categories.get(categoriesIndex);
            int postCount = 0;
            // Select a row of data from index of data ArrayList
            for ( int dataIndex = 0; dataIndex < data.size(); dataIndex++ ) {

                String currentSubReddit = data.get(dataIndex).getName();
                Double currentLex_LIWC_SHEHE = 
                                        data.get(dataIndex).getLexLiwcShehe();
                                        // get lexical score of "she/he"
                // Check if select category matches the name associated
               // with row of data Array list and She/he occurs in
               // post
                if ( currentCategory.equals(currentSubReddit) &&
                     currentLex_LIWC_SHEHE > 0 ) {
                    postCount++; // increment count
                }
            }
            System.out.println("r/" + currentCategory + 
                               " total posts: " + postCount); //print result
        }
    }

    /*
     * Starpoint OPTIONAL
     */
    public static void printStarPointAverage(ArrayList<RedditDataPoint> data, 
                                             ArrayList<String> categories) {
       
    }
    
    /**
     * Runs all methods in the PA6 class.
     *
     * @param (args) Not used
     * @return Void
     */
    public static void main(String args[]) throws IOException{
        
        ArrayList<String> categories = new ArrayList<String>();

        ArrayList<RedditDataPoint> data = readData("Reddit_Data.csv", 
                                                   categories);

        printTotalPosts(data, categories);
        System.out.println(" ");
        printTotalLexLiwcI(data, categories);
        System.out.println(" ");
        printTotalLexLiwcWe(data, categories);
        System.out.println(" ");
        printTotalLexLiwcShehe(data, categories);

        /*
        // ***************TESTING DATA POINT 1*************** //
        RedditDataPoint testPoint1  = new RedditDataPoint("r/banana", "I am monkey", 40.0, 50.0, 60.0);    
        System.out.println("***********Testing RedditDataPoint 1***********");

        System.out.println("testPoint 1, getName():\nExpected: r/banana\nActual: ");
        System.out.println(testPoint1.getName());

        System.out.println("testPoint 1, getText():\nExpected: I am monkey\nActual: ");
        System.out.println(testPoint1.getText());

        System.out.println("testPoint 1, getLexLiwcI():\nExpected: 40.0\nActual: ");
        System.out.println(testPoint1.getLexLiwcI());

        System.out.println("testPoint 1, getLexLiwcWe():\nExpected: 50.0\nActual: ");
        System.out.println(testPoint1.getLexLiwcWe());

        System.out.println("testPoint 1, getLexLiwcShehe():\nExpected: 60.0\nActual: ");
        System.out.println(testPoint1.getLexLiwcShehe() + "\n");

        // ***************TESTING DATA POINT 2*************** //
        RedditDataPoint testPoint2  = new RedditDataPoint("r/apple", "I am Isaac Newton", 79.0, 89.0, 99.0);    
        System.out.println("***********Testing RedditDataPoint 2***********");

        System.out.println("testPoint 2, getName():\nExpected: r/apple\nActual: ");
        System.out.println(testPoint2.getName());

        System.out.println("testPoint 2, getText():\nExpected: I am Isaac Newton\nActual: ");
        System.out.println(testPoint2.getText());

        System.out.println("testPoint 2, getLexLiwcI():\nExpected: 79.0\nActual: ");
        System.out.println(testPoint2.getLexLiwcI());

        System.out.println("testPoint 2, getLexLiwcWe():\nExpected: 89.0.0\nActual: ");
        System.out.println(testPoint2.getLexLiwcWe());

        System.out.println("testPoint 2, getLexLiwcShehe():\nExpected: 99.0.0\nActual: ");
        System.out.println(testPoint2.getLexLiwcShehe());
        */
    }

}