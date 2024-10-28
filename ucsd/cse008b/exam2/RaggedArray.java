///////////////////////////////////////////////////////////////////////////////
// Main Class File:    RaggedArrayTester.java
// File:               RaggedArray.java
// Quarter:            CSE 8B Winter 2022
//
// Author:             Ryo Andrew Onozuka
// Email:              ronozuka@ucsd.edu
// Instructor's Name:  Prof. Gregory Miranda
//////////////////////////// 80 columns wide //////////////////////////////////

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

/**
 * A class that maintains a 2D array. The 2D array could be ragged (i.e. not
 * neccesarily a rectangular array).
 *
 * Bugs: Something is wrong with `findMaxWrong()`...
 *
 * @ryoandrewonozuka
 */
public class RaggedArray {
    // DO NOT CHANGE THE CODE FOR THE PRIVATE MEMBER VARIABLE.
    private int[][] myArray;

    /**
     * Constructor to initialize the RaggedArray via an integer array.
     * DO NOT MODIFY THE CONSTRUCTOR DECLARATION.
     *
     * @param arrayIn the array to DEEP copy into myArray
     */
    public RaggedArray(int[][] arrayIn) {
        
        myArray = new int[arrayIn.length][];
        for (int i = 0; i < arrayIn.length; i++) {
            myArray[i] = arrayIn[i];
        }
    }

    /**
     * Constructor to initialize the RaggedArray via an input file.
     * 
     * NOTE: If initializing RaggedArray with an input file, then
     *  myArray is guaranteed to be a rectangular 2D array.
     * 
     * DO NOT MODIFY THE CONSTRUCTOR DECLARATION.
     *
     * @param inputPath the file to read and create for myArray
     */
    public RaggedArray(String inputPath) throws IOException {

        FileInputStream fileCharStream = null; // file input stream
        Scanner fileScanner = null; // declare scanner object

        // declare num rows & cols
        int num_rows;
        int num_cols;

        fileCharStream = new FileInputStream(inputPath);
        fileScanner =  new Scanner(fileCharStream);

        num_rows = fileScanner.nextInt();
        num_cols = fileScanner.nextInt();

        myArray = new int[num_rows][num_cols];
        for (int i = 0; i < num_rows; i++) {
            for (int j = 0; j < num_cols; j++) {
                myArray[i][j] = fileScanner.nextInt();
            }
        }
        fileCharStream.close();
        fileScanner.close();
    }

    /**
     * Getter method for returning `myArray`.
     * 
     * DO NOT MODIFY THIS METHOD.
     *
     * @return this.myArray
     */
    public int[][] getMyArray() {
        return this.myArray;
    }
    
    /**
     * Finds the maximum element in myArray, but there is an error...
     * Can you find the error?
     * 
     * DO NOT MODIFY THIS METHOD.
     *
     * @return an integer that represents the maximum element from `myArray`
     */
    public int findMaxWrong() {

        int max = 0;
        for (int i = 0; i < myArray.length; i++) {
            for (int j = 0; j < myArray.length; j++) {
                if (myArray[i][j] > max) {
                    max = myArray[i][j];
                }
            }
        }
        return max;
    }

    /**
     * Finds the maximum element in myArray, but the error should be fixed.
     * Use `findMaxWrong()` as template code, and fix the error.
     * 
     * DO NOT MODIFY METHOD DECLARATION.
     *
     * @return an integer that represents the maximum element from `myArray`
     */
    public int findMax() {

        int max = 0;
        for (int i = 0; i < myArray.length; i++) {
            for (int j = 0; j < myArray[i].length; j++) {
                if (myArray[i][j] > max) {
                    max = myArray[i][j];
                }
            }
        }
        return max;
    }
}