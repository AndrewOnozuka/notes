///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:              CSE8B_PA4
// Files:              PA4.java, MazePoint.java, input1, input2, input3.txt,
//                     input4.txt
// Quarter:            CSE 8B WINTER 2022
//
// Author:             Kameron Gano
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
// Persons:          Prajwala Thatha-Manjunatha (TA)
//                   - Helped us correct crucial error with our readMaze method
//                   - Helped us conclude that we must initialize each MazePoint
//                   in list prior to setting each Char to a wall or empty slot
//
// Online sources:   Avoid web searches to solve your problems, but if you do
//                   search, be sure to include Web URLs and description of
//                   of any information you find.
//////////////////////////// 80 columns wide //////////////////////////////////

import java.io.IOException;
import java.io.FileInputStream;
import java.util.Scanner;
import java.io.File;

/**
 * The PA4 class contains methods that read in a maze from a file and solve it,
 * as well as methods that test the read and the solution against expected 
 * outputs. The main method initiate a series of 4 tests on 4 unique files,
 * and contains a while loop that will solve a maze from any file (provided
 * that it exists in the folder).
 *
 * Bugs: None known
 *
 * @author Kameron Gano and Andrew Onozuka
 */
public class PA4 {

    /**
    * Maze constructor which reads in a list of X's and -'s 
    * from an array read in from the param fileToRead and stores 
    * them in an array of MazePoints
    *
    *
    * @param fileToRead File storing maze to be read
    * @return Maze read from fileToRead
    */
    public MazePoint[][] readMaze(String fileToRead) throws IOException
    {

        // File input stream
        // Declare scanner object
        FileInputStream fileCharStream = null;
        Scanner fileScanner = null;
        
        // Declare number of rows and columns in maze
        int NUM_ROWS;
        int NUM_COLS;


        // OPEN FILE
        fileCharStream = new FileInputStream(fileToRead);

        File fileInput = new File(fileToRead);

        fileScanner = new Scanner(fileCharStream);

        if ( !fileScanner.hasNextInt() ) {
            MazePoint[][] maze = null;
            System.out.println("ERROR: NULL MAZE; MAZE FILE MISSING DIMENSIONS");
            fileScanner.close();
            return maze;
        }
        
        // DEFINE NUM_ROWS and NUM_COLS
        NUM_ROWS = fileScanner.nextInt();
        NUM_COLS = fileScanner.nextInt();

        // Define 2D Array of MazePoints
        MazePoint[][] maze = new MazePoint[NUM_ROWS][NUM_COLS];

        

        //Assumes the only things present are the two integers at beginning or less
        if ( fileInput.length() <= 4 ) { 
            maze = null;
            System.out.println("ERROR: NULL MAZE");
            fileScanner.close();
            return maze;
        }
        

        

  
        // Gather the characters in each line
        for ( int i = 0; i < NUM_ROWS; i++ ) {
            for ( int j = 0; j < NUM_COLS; j++ ) {
                
                char mazeChar = fileScanner.next().charAt(0);
                // Initialize each value in maze
                MazePoint point = new MazePoint(false);
                maze[i][j] = point;
                if ( mazeChar == 'X' ) {
                    maze[i][j].setToWall();
                }    
                else {
                    maze[i][j].setToEmpty();

                }
                
            } 
        }
    
        fileCharStream.close();
        fileScanner.close();


        return maze;
    }

    /**
    * Solve the maze passed as a parameter.
    * Handles edge cases of null maze, 
    * maze of length zero, and mazes that are invalid
    * due to their start/end.
    *
    * @param maze The maze to be solved.
    */
    public void escapeFromMaze(MazePoint [][] maze){

        // Check if maze is null and return error if it is
        if ( maze == null ) {
            System.out.println("ERROR: MAZE NULL");
            System.out.println("Please try again with a valid input file. ");
            return;
        }

        // Check if maze has length zero and return error if it s
        else if ( maze.length == 0 || maze[0].length == 0 ) {
            System.out.println("ERROR: Maze must not have a length of zero. ");
            System.out.println("Maze was not altered.");
            return;
        }

        // Check if maze is invalid (It starts or ends with a wall)
        else if ( maze[0][0].isWall() || maze[maze.length - 1][maze[0].length - 1].isWall() ) {
            System.out.println("ERROR: First point (TOP LEFT) and last point (BOTTOM RIGHT) must be empty to be solveable. ");
            System.out.println("Maze was not altered.");
            return;
        }

        // If check for special cases fail, begin to solve maze.        
        for ( int r = 0; r <= maze.length - 1; r++ ) {
            for ( int c = 0; c < maze[r].length; c++ ) {

                // Check to see if a point is not a wall. If it is not,
                // set it to the path
                if ( !maze[r][c].isWall() ) {
                    maze[r][c].setToPath();
                }
                // If current point is a wall, go back a column, as that one was 
                // an open space
                if ( maze[r][c].isWall() ) {
                    c--;
                }
                // When on the last row, set the remaining characters to the path,
                // as it will always end on bottom right corner
                if ( r == maze.length - 1 ) {
                    maze[r][c].setToPath();
                }
                // If it is not on the last row and the row below the current point is free,
                // then move the path down a row, and set the new point to the path.
                else if ( r <= maze.length - 2 && !maze[r + 1][c].isWall() ) {
                    r++;
                    maze[r][c].setToPath();
            
                }

            }
        }
    }

    /**
    * Print the maze as a 2D grid.  You should call this method from
    * testRead and testEscape, as well as from main.
    *
    * Precondition: The maze is not null.
    * Postcondition: The maze has been printed and is unmodified.
    *
    * @param maze The maze to be printed.
    */
    private void printMaze(MazePoint[][] maze) 
    {
        
        for ( int r = 0; r < maze.length; r++ ) {
            for ( int c = 0; c < maze[r].length; c++ ) {
                
                maze[r][c].printSymbol();
                System.out.print(" ");

            }
            System.out.println(" ");
        }
    }

    /**
    * Compare two maze arrays.  Return true if they have .  You should call
    * this method from testRead and testEscape.
    *
    * Precondition: The mazes are not null and have the same size.
    * Postcondition: The mazes are not modified.
    *
    * @param maze1 the first maze to compare.
    * @param maze2 the second maze to compare.
    * @return true if the mazes contain all the same symbols and false otherwise
    */
    private boolean mazeMatch(MazePoint[][] maze1, MazePoint[][] maze2)
    {
        // -- USED FOR UNIT TESTING PURPOSES
        // Returns true if both mazes are null
        /*
        if ( maze1 == null && maze2 == null ) {
            return true;
        }

        // Returns false if maze1 is null but maze2 is not
        else if ( maze1 == null && maze2 != null ) {
            return false;
        }

        // Returns false if maze2 is null but maze1 is not
        else if ( maze1 != null && maze2 == null ) {
            return false;
        }
        */

        // If all special cases fail, check each character in maze for
        // a mismatch. If one is present, return false. Otherwise,
        // assume the mazes match.
        for ( int r = 0; r < maze1.length; r++ ) {
            for ( int c = 0; c < maze1[r].length; c++ ) {
                if  (maze1[r][c].isWall() != maze2[r][c].isWall() ) {
                    return false;
                }
            }
        }
        
        return true;
    }

    /**
    * Ensures that the maze read from a file is the same as
    * the expected output. Otherwise, return false, as 
    * they are not the same.
    *
    *
    * @param fileToRead File to be read
    * @param expected The maze with which to compare the maze in fileToRead.
    * @return false or true depending on whether or not the mazes are the same.
    */
    public boolean testRead(String fileToRead, MazePoint[][] expected) throws
                            IOException
    {
        if ( mazeMatch(readMaze(fileToRead), expected) && 
             (expected != null) &&
             (readMaze(fileToRead) != null )) {
            printMaze(expected);
            return true;
        }
        else if ( mazeMatch(readMaze(fileToRead), expected) && 
        (expected == null) &&
        (readMaze(fileToRead) == null )) {       
            return true;
        }
        else {
            return false;
        }

    }

    /**
    * Ensures the solvedmaze given from file input matches
    *
    * @param maze Solved maze from file input
    * @param expectedSolution Solved maze expected from file input
    * @return false if the paths do not match; otherwise, return true
    */
    public boolean testEscape(MazePoint[][] maze,
                              MazePoint[][] expectedSolution)
    {

        escapeFromMaze(maze);
        if ( maze == null && expectedSolution == null ) {
            return true;
        }

        // Returns false if maze1 is null but maze2 is not
        else if ( maze == null && expectedSolution != null ) {
            return false;
        }

        // Returns false if maze2 is null but maze1 is not
        else if ( maze != null && expectedSolution == null ) {
            return false;
        }
        /*
        printMaze(maze);
        */
        

        for ( int r = 0; r < maze.length; r++ ) {
            for ( int c = 0; c < maze[r].length; c++ ) {

                if ( maze[r][c].isPath() != expectedSolution[r][c].isPath() ) {
                    System.out.println("Maze did not print the expected solution: ");
                    printMaze(expectedSolution);
                    return false;
                }
            }
        }
        return true;
    }

    /**
    * Run unit tests.  You will add to this method.
    * Prints a message indicating whether all tests passed or failed.
    * The method will abort on the first failed test.
    * @return true if all unit tests pass.  false if at least one test fails.
    */
    public boolean unitTests() throws IOException {
        // TEST 1
        // Manually create the expected maze to match file input1

        System.out.println("***TEST 1***\n");

        MazePoint[][] expected = new MazePoint[3][3];
        expected[0][0] = new MazePoint(false);
        expected[0][1] = new MazePoint(false);
        expected[0][2] = new MazePoint(true);
        expected[1][0] = new MazePoint(true);
        expected[1][1] = new MazePoint(false);
        expected[1][2] = new MazePoint(false);
        expected[2][0] = new MazePoint(true);
        expected[2][1] = new MazePoint(true);
        expected[2][2] = new MazePoint(false);

        if (!this.testRead("input1", expected)) {
            System.out.println("Read test 1 failed.");
            return false;
        }
        else {
          System.out.println("Read test 1 passed!");
        }

        // At this point readMaze is working, so we can use it.

        MazePoint[][] maze1 = this.readMaze("input1");
        

        // Modify the expected maze from above to have the path
        expected[0][0].setToPath();
        expected[0][1].setToPath();
        expected[1][1].setToPath();
        expected[1][2].setToPath();
        expected[2][2].setToPath();

        System.out.println("Expected solution: ");
        printMaze(expected);
        
        if (!this.testEscape(maze1, expected)) {
            System.out.println("Escape test 1 failed.");
            return false;
        }
        else {
          System.out.println("Escape test 1 passed!\nACTUAL:");
          printMaze(maze1);
          System.out.println("--------------------------------");
        }

        // TEST 2
        // Manually create the expected maze to match file input2

        System.out.println("***TEST 2***\n");

        MazePoint[][] expected2 = new MazePoint[4][8];
        //Row 1
        expected2[0][0] = new MazePoint(false);
        expected2[0][1] = new MazePoint(true);
        expected2[0][2] = new MazePoint(true);
        expected2[0][3] = new MazePoint(false);
        expected2[0][4] = new MazePoint(true);
        expected2[0][5] = new MazePoint(true);
        expected2[0][6] = new MazePoint(true);
        expected2[0][7] = new MazePoint(true);
        //Row 2
        expected2[1][0] = new MazePoint(false);
        expected2[1][1] = new MazePoint(false);
        expected2[1][2] = new MazePoint(false);
        expected2[1][3] = new MazePoint(false);
        expected2[1][4] = new MazePoint(true);
        expected2[1][5] = new MazePoint(false);
        expected2[1][6] = new MazePoint(true);
        expected2[1][7] = new MazePoint(false);
        //Row 3
        expected2[2][0] = new MazePoint(true);
        expected2[2][1] = new MazePoint(true);
        expected2[2][2] = new MazePoint(true);
        expected2[2][3] = new MazePoint(false);
        expected2[2][4] = new MazePoint(true);
        expected2[2][5] = new MazePoint(true);
        expected2[2][6] = new MazePoint(true);
        expected2[2][7] = new MazePoint(false);
        //Row 4
        expected2[3][0] = new MazePoint(false);
        expected2[3][1] = new MazePoint(true);
        expected2[3][2] = new MazePoint(false);
        expected2[3][3] = new MazePoint(false);
        expected2[3][4] = new MazePoint(false);
        expected2[3][5] = new MazePoint(false);
        expected2[3][6] = new MazePoint(false);
        expected2[3][7] = new MazePoint(false);

        if (!this.testRead("input2", expected2)) {
            System.out.println("Read test 2 failed.");
            return false;
        }
        else {
          System.out.println("Read test 2 passed!");
        }

        // At this point readMaze is working, so we can use it.

        MazePoint[][] maze2 = this.readMaze("input2");
        

        // Modify the expected maze from above to have the path
        expected2[0][0].setToPath();
        expected2[1][0].setToPath();
        expected2[1][1].setToPath();
        expected2[1][2].setToPath();
        expected2[1][3].setToPath();
        expected2[2][3].setToPath();
        expected2[3][3].setToPath();
        expected2[3][4].setToPath();
        expected2[3][5].setToPath();
        expected2[3][6].setToPath();
        expected2[3][7].setToPath();

        System.out.println("Expected solution: ");
        printMaze(expected2);

        if (!this.testEscape(maze2, expected2)) {
            System.out.println("Escape test 2 failed.");
            return false;
        }
        else {
          System.out.println("Escape test 2 passed!\nACTUAL:");
          printMaze(maze2);
          System.out.println("--------------------------------");
        }

        // TEST 3
        // Manually create the expected maze to match file input1
        System.out.println("***TEST 3***\n");

        MazePoint[][] expected3 = new MazePoint[6][9];

        //Row 1
        expected3[0][0] = new MazePoint(false);
        expected3[0][1] = new MazePoint(false);
        expected3[0][2] = new MazePoint(false);
        expected3[0][3] = new MazePoint(false);
        expected3[0][4] = new MazePoint(false);
        expected3[0][5] = new MazePoint(true);
        expected3[0][6] = new MazePoint(true);
        expected3[0][7] = new MazePoint(true);
        expected3[0][8] = new MazePoint(true);
        //Row 2
        expected3[1][0] = new MazePoint(true);
        expected3[1][1] = new MazePoint(true);
        expected3[1][2] = new MazePoint(true);
        expected3[1][3] = new MazePoint(true);
        expected3[1][4] = new MazePoint(false);
        expected3[1][5] = new MazePoint(true);
        expected3[1][6] = new MazePoint(false);
        expected3[1][7] = new MazePoint(true);
        expected3[1][8] = new MazePoint(false);
        //Row 3
        expected3[2][0] = new MazePoint(true);
        expected3[2][1] = new MazePoint(true);
        expected3[2][2] = new MazePoint(true);
        expected3[2][3] = new MazePoint(true);
        expected3[2][4] = new MazePoint(false);
        expected3[2][5] = new MazePoint(true);
        expected3[2][6] = new MazePoint(true);
        expected3[2][7] = new MazePoint(false);
        expected3[2][8] = new MazePoint(true);
        //Row 4
        expected3[3][0] = new MazePoint(true);
        expected3[3][1] = new MazePoint(false);
        expected3[3][2] = new MazePoint(false);
        expected3[3][3] = new MazePoint(false);
        expected3[3][4] = new MazePoint(false);
        expected3[3][5] = new MazePoint(true);
        expected3[3][6] = new MazePoint(true);
        expected3[3][7] = new MazePoint(true);
        expected3[3][8] = new MazePoint(true);
        //Row 5
        expected3[4][0] = new MazePoint(true);
        expected3[4][1] = new MazePoint(true);
        expected3[4][2] = new MazePoint(true);
        expected3[4][3] = new MazePoint(true);
        expected3[4][4] = new MazePoint(false);
        expected3[4][5] = new MazePoint(false);
        expected3[4][6] = new MazePoint(false);
        expected3[4][7] = new MazePoint(true);
        expected3[4][8] = new MazePoint(true);
        //Row 6
        expected3[5][0] = new MazePoint(true);
        expected3[5][1] = new MazePoint(true);
        expected3[5][2] = new MazePoint(true);
        expected3[5][3] = new MazePoint(true);
        expected3[5][4] = new MazePoint(true);
        expected3[5][5] = new MazePoint(true);
        expected3[5][6] = new MazePoint(false);
        expected3[5][7] = new MazePoint(false);
        expected3[5][8] = new MazePoint(false);

        if (!this.testRead("input3.txt", expected3)) {
            System.out.println("Read test 3 failed.");
            return false;
        }
        else {
          System.out.println("Read test 3 passed!");
        }

        // At this point readMaze is working, so we can use it.

        MazePoint[][] maze3 = this.readMaze("input3.txt");
        

        // Modify the expected maze from above to have the path
        expected3[0][0].setToPath();
        expected3[0][1].setToPath();
        expected3[0][2].setToPath();
        expected3[0][3].setToPath();
        expected3[0][4].setToPath();
        expected3[1][4].setToPath();
        expected3[2][4].setToPath();
        expected3[3][4].setToPath();
        expected3[4][4].setToPath();
        expected3[4][5].setToPath();
        expected3[4][6].setToPath();
        expected3[5][6].setToPath();
        expected3[5][7].setToPath();
        expected3[5][8].setToPath();

        System.out.println("Expected solution: ");
        printMaze(expected3);
        if (!this.testEscape(maze3, expected3)) {
            System.out.println("Escape test 3 failed.");
            return false;
        }
        else {
          System.out.println("Escape test 3 passed!\nACTUAL:");
          printMaze(maze3);
          System.out.println("--------------------------------");
        }
        // TEST 4 - NULL INPUT

        System.out.println("***TEST 4 - NULL INPUT***");
        
        MazePoint[][] maze4 = this.readMaze("input4.txt");
        MazePoint[][] expected4 = new MazePoint[2][2];
        expected4 = null;


        
        if (!(maze4 == null)) {
            System.out.println("Read test 4 failed.");
            return false;
        }
        else {
          System.out.println("Read test 4 passed!");
        }

        System.out.println("Expected:\nERROR: MAZE NULL");
        System.out.println("ACTUAL: ");

        // ADDED 4th TEST ESCAPE 
        testEscape(maze4, expected4);
       
        // Should return an NULL MAZE ERROR

        

        return true;
    }


    /**
     * 
     * Main method runs 4 tests on 4 input
     * files by calling unitTests. Then, it enters
     * a while loop that prompts the user to select a maze,
     * in which case, it solves it for them.
     * 
     */
    public static void main(String[] args) throws IOException
    {
        PA4 solver = new PA4();

        // Define member variables for Maze Solver Loop

        String input1 = "input1";
        String input2 = "input2";
        String input3 = "input3.txt";
        String input4 = "input4.txt";
        String userFileSelection;
        MazePoint[][] userMaze;

        
        // Perform unitTest first
        
        if(solver.unitTests()) {
            System.out.println("All unit tests passed.\n");
        } else {
            System.out.println("Failed test.\n");
            return;
        }

        Scanner scnr = new Scanner(System.in);
        
        // Maze solver loop
        while ( true ) {
            System.out.println("Enter file to read: ");

            // Prompt user to select maze file
            userFileSelection = scnr.nextLine();
            
            // VALIDITY CHECH: Checks if inputted string is an "end" statement
            // or if the input is valid or not.
            if ( userFileSelection.equalsIgnoreCase("end") ) {
                break;
            }
            else if ( !(userFileSelection.equalsIgnoreCase(input1) ||
                 userFileSelection.equalsIgnoreCase(input2) ||
                 userFileSelection.equalsIgnoreCase(input3) ||
                 userFileSelection.equalsIgnoreCase(input4)) ) {
                    System.out.println("Ensure selection is one of the following: ");
                    System.out.println(input1);
                    System.out.println(input2);
                    System.out.println(input3);
                    System.out.println(input4);
                    System.out.println("ERROR: Please try again with a valid input file. ");
                    continue;
            }
            
            System.out.println("Reading file... ");
            // If it passes VALIDITY CHECK, read the maze
            userMaze = solver.readMaze(userFileSelection);

            // Display unique error message if Maze turns out to be null
            if ( userMaze == null ) {
                System.out.println("The maze file is null. Please ensure the file contains valid characators: X or -.");
                System.out.println("Try again with a valid input file.");
                continue;
            }

            System.out.println("File successfully read.");
            System.out.println("Maze read from file: ");

            // If maze is not null, print it out
            solver.printMaze(userMaze);

            System.out.println("Solved maze: ");

            // Show the user the solved maze
            solver.escapeFromMaze(userMaze);
            solver.printMaze(userMaze);
            
        }

        scnr.close();

    }
}
