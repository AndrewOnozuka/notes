///////////////////////////////////////////////////////////////////////////////
// Main Class File:    PA5Tester
// File:               PA5Tester.java
// Quarter:            Winter 2020
//
// Author:             Rachel Chung
// Instructor's Name:  Christine Alvarado, Greg Miranda
//
//////////////////////////// 80 columns wide //////////////////////////////////

///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:              PA5Tester.java
// Files:              Constants2048.java, Gui2048.java, bin/, lib/,
//                     Board.java, PA5Tester.java
// Quarter:            Winter 2022
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
// Pair Partner:        Andrew Onozuka
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

import java.util.Arrays;
import java.util.Random;

/**
 * The PA5Tester class runs manual tests and returns their results so that
 * we can see if our code is working properly.
 *
 * Bugs: No bugs known
 *
 * @author Kameron Gano
 * @author Andrew Onozuka
 */
public class PA5Tester {

    // Seed passed to random generator to match the expected output
    private static int SEED = 2017;

    /**
    * Main method runs a series of test on the Board object.
    *
    * @param args (not used)
    */
    public static void main(String[] args)
    {

        PA5Tester solver = new PA5Tester();

        System.out.println("\n\n*************** Testing PA5 *****************");

        int score = 0;
        score += solver.testPrintBoard();
        score += solver.testCanMove();
        score += solver.testMove();

        System.out.println("*************************************************");
    }

    /************************ TEST PRINT BOARD ************************/

    /**
     * Test the board's printBoard  method.
     *
     * @return 1 if the tests pass, 0 if they fail
     */
    private int testPrintBoard()
    {
        System.out.println("Testing printBoard method below.");

        Board board1 = new Board(new Random(SEED), new int[][]{
                {2, 4, 0, 0},
                {0, 0, 8, 4},
                {0, 16, 0, 4},
                {1024, 2, 4, 256}});

        System.out.println("\nPrinting current board: \n");
        board1.printBoard();
        System.out.println("\nPrinting board after moving UP: \n");
        board1.printBoard(board1.UP);
        System.out.print("\nPrinting board to see previous ");
        System.out.println("board remains: \n");
        board1.printBoard();
        System.out.println("\nPrinting board after moving LEFT: \n");
        board1.printBoard(board1.LEFT);
        System.out.print("\nPrinting board to see previous ");
        System.out.println("board remains: \n");
        board1.printBoard();
        System.out.println("\n***********************************************");
        return 0;
    }

    /************************ TEST CAN MOVE ************************/

    /**
     * Test the board's canMove method.
     * @return 1 if the tests pass, 0 if they fail
     */
    private int testCanMove()
    {

        //////////// CANMOVELEFT TEST ////////////////
        System.out.print("Testing canMoveLeft method (can move left): ");
        Board board1 = new Board(new Random(SEED), new int[][]{
                {0, 0, 0, 4},
                {0, 0, 0, 4},
                {0, 0, 0, 0},
                {0, 0, 0, 0}});

        if (board1.canMove(board1.LEFT)) {
            // Passed, do nothing.
            System.out.println("PASSED.");
        }
        else {
            System.out.println("FAILED!\nBoard should be able to move left:");
            board1.printBoard();
            return 0;
        }
        System.out.print("Testing canMoveLeft method (cannot move left): ");
        Board board2 = new Board(new Random(SEED), new int[][]{
                {2, 4, 2, 4},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}});
        if (!board2.canMove(board2.LEFT)) {
            // Passed, do nothing.
            System.out.println("PASSED.");
        }
        else {
            System.out.println("FAILED!\nBoard should NOT be able to move left:");
            board2.printBoard();
            return 0;
        }
        System.out.print("Testing canMoveLeft method (move left by combining): ");
        Board board3 = new Board(new Random(SEED), new int[][]{
                {2, 4, 2, 4},
                {2, 2, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}});
        if (board3.canMove(board3.LEFT)) {
            // Passed, do nothing.
            System.out.println("PASSED.");
        }
        else {
            System.out.println("FAILED!\nBoard should be able to move left:");
            board3.printBoard();
            return 0;
        }
        //////////// CANMOVERIGHTUPDOWN TEST ////////////////
        System.out.print("\nTesting canMoveRight,Up,Down method (can move up & down but not right): ");
        if (board1.canMove(board1.UP) && !board1.canMove(board1.RIGHT) &&
        board1.canMove(board1.DOWN)) {
            // Passed, do nothing.
            System.out.println("PASSED.");
        }
        else {
            System.out.println("FAILED!");
            if (!board1.canMove(board1.UP)) {
                System.out.println("Board should be able to move UP:");
            }
            if (board1.canMove(board1.RIGHT)) {
                System.out.println("Board should NOT be able to move RIGHT:");
            }
            if (!board1.canMove(board1.DOWN)) {
                System.out.println("Board should be able to move DOWN:");
            }
            board1.printBoard();
            return 0;
        }
        //////////// CANMOVERIGHT TEST ////////////////
        System.out.print("Testing canMoveRight method (can move right): ");
        if (board3.canMove(board3.RIGHT)) {
            // Passed, do nothing.
            System.out.println("PASSED.");
        }
        else {
            System.out.println("FAILED!\nBoard should be able to move left:");
            board3.printBoard();
            return 0;
        }
        System.out.print("Testing canMoveUp method (cannot move up): ");
        if (!board2.canMove(board2.UP)) {
            // Passed, do nothing.
            System.out.println("PASSED.");
        }
        else {
            System.out.println("FAILED!\nBoard should be able to move left:");
            board3.printBoard();
            return 0;
        }
        System.out.print("Testing canMoveDown method (cannot move down): ");
        Board board7 = new Board(new Random(SEED), new int[][]{
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {4, 0, 0, 0}});
        if (!board7.canMove(board7.DOWN)) {
            // Passed, do nothing.
            System.out.println("PASSED.");
        }
        else {
            System.out.println("FAILED!\nBoard should be able to move left:");
            board7.printBoard();
            return 0;
        }
        //////////// CANMOVERIGHTUPDOWN TEST ////////////////
        Board board4 = new Board(new Random(SEED), new int[][]{
                {4, 0, 0, 0},
                {4, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}});
        System.out.print("\nTesting canMoveRight,Up,Down method (can move up, down, right): ");
        if (board4.canMove(board4.UP) && board4.canMove(board4.RIGHT) &&
                board4.canMove(board4.DOWN)) {
            // Passed, do nothing.
            System.out.println("PASSED.");
        }
        else {
            System.out.println("FAILED!");
            if (!board4.canMove(board4.UP)) {
                System.out.println("Board should be able to move UP:");
            }
            if (!board4.canMove(board4.RIGHT)) {
                System.out.println("Board should NOT be able to move RIGHT:");
            }
            if (!board4.canMove(board4.DOWN)) {
                System.out.println("Board should be able to move DOWN:");
            }
            board4.printBoard();
            return 0;
        }

        //////////// CANMOVELEFT TEST ////////////////
        System.out.print("Testing canMoveLeft method: ");
        Board board5 = new Board(new Random(SEED), new int[][]{
                {8, 2, 8, 0},
                {4, 4, 4, 0},
                {0, 0, 2, 0},
                {0, 0, 0, 0}});
        if (board5.canMove(board5.LEFT)) {
            // Passed, do nothing.
            System.out.println("PASSED.");
        }
        else {
            System.out.println("FAILED!\nBoard should be able to move left:");
            board5.printBoard();
            return 0;
        }
        System.out.print("Testing canMoveRight,Down,Up method (can move right & down but not up): ");
        if (!board5.canMove(board5.UP) && board5.canMove(board5.RIGHT) &&
                board5.canMove(board5.DOWN)) {
            // Passed, do nothing.
            System.out.println("PASSED.");
        }
        else {
            System.out.println("FAILED!");
            if (board5.canMove(board5.UP)) {
                System.out.println("Board should NOT be able to move UP:");
            }
            if (!board5.canMove(board5.RIGHT)) {
                System.out.println("Board should be able to move RIGHT:");
            }
            if (!board5.canMove(board5.DOWN)) {
                System.out.println("Board should be able to move DOWN:");
            }
            board5.printBoard();
            return 0;
        }
        System.out.println("All canMove Tests Passed!");
        System.out.println("\n***********************************************");
        return 1;
    }

    /************************ TEST MOVE ************************/
    /**
     * Test the board's move method.
     * @return 1 if the tests pass, 0 if they fail
     */
    public int testMove()
    {
        System.out.println("Testing move method.........................");
        int passed = 1;

        /////////////////BEGIN BOARD LEFT TESTS/////////////////
        Board board_left = new Board(new Random(SEED), new int[][]{
            {0, 2, 2, 0},
            {0, 0, 16, 8},
            {2, 128, 128, 32},
            {16, 16, 16, 16}});
        System.out.println("------Move left test 1------");
        System.out.print("\n");
        board_left.printBoard();
        System.out.println("-----------------------------------");
        board_left.printBoard(board_left.LEFT);
        board_left.move(board_left.LEFT);
        if (!isEqualArray(boardToArray(board_left), new int[][]{
                {4, 0, 0, 0},
                {16, 8, 0, 0},
                {2, 256, 32, 0},
                {32, 32, 0, 0}})) {
            System.out.println("FAILED!");
            System.out.println("move(Board.LEFT) implemented incorrectly");
            return 0;
        }

        Board board_left1 = new Board(new Random(SEED), new int[][]{
            {0, 2, 2, 2},
            {0, 16, 16, 8},
            {2, 256, 0, 256},
            {16, 0, 16, 0}});
        System.out.println("------Move left test 2------");
        System.out.print("\n");
        board_left1.printBoard();
        System.out.println("-----------------------------------");
        board_left1.printBoard(board_left1.LEFT);
        board_left1.move(board_left1.LEFT);
        if (!isEqualArray(boardToArray(board_left1), new int[][]{
                {4, 2, 0, 0},
                {32, 8, 0, 0},
                {2, 512, 0, 0},
                {32, 0, 0, 0}})) {
            System.out.println("FAILED!");
            System.out.println("move(Board.LEFT) implemented incorrectly");
            return 0;
        }

        /////////////////BEGIN BOARD RIGHT TESTS/////////////////
        Board board_right = new Board(new Random(SEED), new int[][]{
                {2, 4, 8, 2},
                {2, 4, 8, 2},
                {2, 2, 2, 2},
                {2, 4, 8, 2}});
        System.out.println("------Move right test 1------");
        System.out.print("\n");
        board_right.printBoard();
        System.out.println("-----------------------------");
        board_right.printBoard(board_right.RIGHT);
        board_right.move(board_right.RIGHT);
        if (!isEqualArray(boardToArray(board_right), new int[][]{
                {2, 4, 8, 2},
                {2, 4, 8, 2},
                {0, 0, 4, 4},
                {2, 4, 8, 2}})) {
            System.out.println("FAILED!");
            System.out.println("move(Board.RIGHT) is implemented incorrectly");
            return 0;
        }

        Board board_right1 = new Board(new Random(SEED), new int[][]{
            {8, 0, 8, 4},
            {16, 16, 8, 2},
            {2, 2, 8, 0},
            {8, 16, 0, 16}});
        System.out.println("------Move right test 2------");
        System.out.print("\n");
        board_right1.printBoard();
        System.out.println("-----------------------------------");
        board_right1.printBoard(board_right1.RIGHT);
        board_right1.move(board_right1.RIGHT);
        if (!isEqualArray(boardToArray(board_right1), new int[][]{
            {0, 0, 16, 4},
            {0, 32, 8, 2},
            {0, 0, 4, 8},
            {0, 0, 8, 32}})) {
        System.out.println("FAILED!");
        System.out.println("move(Board.RIGHT) is implemented incorrectly");
        return 0;
    }

        /////////////////BEGIN BOARD UP TESTS/////////////////
        Board board_up = new Board(new Random(SEED), new int[][]{
            {0, 8, 4, 8},
            {0, 0, 128, 8},
            {2, 8, 128, 256},
            {0, 0, 2, 64}});

        System.out.println("------Move UP test 1------");
        System.out.print("\n");
        board_up.printBoard();
        System.out.println("----------------------------");
        board_up.printBoard(board_up.UP);
        board_up.move(board_up.UP);

        if (!isEqualArray(boardToArray(board_up), new int[][]{
                {2, 16, 4, 16},
                {0, 0, 256, 256},
                {0, 0, 2, 64},
                {0, 0, 0, 0}})) {
            System.out.println("FAILED!");
            System.out.println("move(Board.UP) implemented incorrectly");
            return 0;
        }

        Board board_up1 = new Board(new Random(SEED), new int[][]{
            {4, 8, 0, 8},
            {4, 4, 128, 8},
            {2, 4, 128, 256},
            {0, 0, 2, 64}});

        System.out.println("------Move UP test 2------");
        System.out.print("\n");
        board_up1.printBoard();
        System.out.println("----------------------------");
        board_up1.printBoard(board_up1.UP);
        board_up1.move(board_up1.UP);

        if (!isEqualArray(boardToArray(board_up1), new int[][]{
            {8, 8, 256, 16},
            {2, 8, 2, 256},
            {0, 0, 0, 64},
            {0, 0, 0, 0}})) {
        System.out.println("FAILED!");
        System.out.println("move(Board.UP) implemented incorrectly");
        return 0;
    }

        /////////////////BEGIN BOARD DOWN TESTS/////////////////
        Board board_down = new Board(new Random(SEED), new int[][]{
            {2, 0, 0, 2},
            {2, 0, 8, 2},
            {2, 4, 8, 0},
            {2, 4, 0, 2}});
        System.out.println("------Move DOWN test 1------");
        System.out.print("\n");
        board_down.printBoard();
        System.out.println("----------------------------");
        board_down.printBoard(board_down.DOWN);
        board_down.move(board_down.DOWN);
        if (!isEqualArray(boardToArray(board_down), new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {4, 0, 0, 2},
                {4, 8, 16, 4}})) {
            System.out.println("FAILED!");
            System.out.println("move(Board.DOWN) implemented incorrectly");
            return 0;
        }

        Board board_down1 = new Board(new Random(SEED), new int[][]{
            {8, 0, 0, 2},
            {8, 8, 16, 2},
            {2, 4, 8, 8},
            {2, 4, 8, 2}});
        System.out.println("------Move DOWN test 2------");
        System.out.print("\n");
        board_down1.printBoard();
        System.out.println("----------------------------");
        board_down1.printBoard(board_down1.DOWN);
        board_down1.move(board_down1.DOWN);
        if (!isEqualArray(boardToArray(board_down1), new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 4},
                {16, 8, 16, 8},
                {4, 8, 16, 2}})) {
            System.out.println("FAILED!");
            System.out.println("move(Board.DOWN) implemented incorrectly");
            return 0;
        }
        System.out.println("Passed!");
        // System.out.println("");
        // System.out.println("");
        // System.out.println("");
        // System.out.println("");
        // System.out.println("");
        return 1;
    }

    /**
     * Creates int[][] given a board object.  Helper method to make sure
     * tests are working correctly.
     *
     * @return the array representation of the board's grid.
     */
    private int[][] boardToArray(Board board) {
        if (board == null) {
            return null;
        }
        int[][] array = new int[board.GRID_SIZE][board.GRID_SIZE];
        for (int r = 0; r < board.GRID_SIZE; r++) {
            for (int c = 0; c < board.GRID_SIZE; c++) {
                array[r][c] = board.getTileValue(r, c);
            }
        }
        return array;
    }

    /**
     * Compares two 2D int arrays.  Helper method to make sure
     * tests are working correctly.
     *
     * Precondition: The arrays are the same size and not null.
     *
     * @return true if the arrays contain all the same values, false otherwise
     */
    private boolean isEqualArray(int[][] grid1, int[][] grid2) {
        for (int r = 0; r < grid1.length; ++r) {
            for (int c = 0; c < grid1.length; ++c) {
                if (grid1[r][c] != grid2[r][c])
                    return false;
            }
        }
        return true;
    }
}