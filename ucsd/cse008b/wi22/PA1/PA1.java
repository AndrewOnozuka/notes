///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:              CSE8B_PA1
// Files:              CSE8B_PA1.java
// Quarter:            CSE8B Winter 2022
//
// Author:             Ryo Andrew Onozuka
// Email:              ronozuka@ucsd.edu
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
// Pair Partner:        Kameron Bahmanyar Gano
// Email:               kgano@ucsd.edu
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
/*
 * This PA1 file asks the user a set of trivia questions to be answered in the terminal.
 * We made our questions regarding Lisa Su, a prominent female in the STEM world.
 * Our program checks each response to see if it is correct, and displays messages accordingly.
 * The user will be given an updated score count as well as a unique final message upon completion.
 *
 * @Ryo Andrew Onozuka & Kameron Bahmanyar Gano
 */
public class CSE8B_PA1 {
    public static void main(String[] args) {
        // Initializing Variables
        String userInput1;
        String userInput2;
        String userInput3;
        int score = 0; // Set initial score to 0
        Scanner scnr = new Scanner(System.in);

        // Start Message
        System.out.println("Welcome to our game! Today you'll be answering 3 questions about Lisa Su!");

        // Question 1 Prompt
        System.out.println("1. What college did Lisa Su attend?");
        userInput1 = scnr.nextLine();
        System.out.println("You guessed: " + userInput1);
        // Question 1 Answer
        if (userInput1.equalsIgnoreCase("MIT")) {
            score += 1;
            System.out.println("You got it right! Your score is: " + score);
        }
        else {
            System.out.println("You got it wrong. The correct answer was: MIT. Your score is: " + score);
        }

        // Question 2 Prompt
        System.out.println("2. What did Lisa Su major in?");
        userInput2 = scnr.nextLine();
        System.out.println("You guessed: " + userInput2);
        // Question 2 Answer
        if (userInput2.equalsIgnoreCase("Electrical Engineering")) {
            score += 1;
            System.out.println("You got it right! Your score is: " + score);
        }
        else {
            System.out.println("You got it wrong. The correct answer was: Electrical Engineering. Your score is: " + score);
        }

        // Question 3 Prompt
        System.out.println("3. What company is Lisa Su the CEO of now?");
        userInput3 = scnr.nextLine();
        System.out.println("You guessed: " + userInput3);
        // Question 3 Answer
        if (userInput3.equalsIgnoreCase("AMD")) {
            score += 1;
            System.out.println("You got it right! Your score is: " + score);
        }
        else {
            System.out.println("You got it wrong. The correct answer was: AMD. Your score is: " + score);
        }

        scnr.close(); // Close Scanner

        // Final Message
        if (score == 3) { 
            System.out.println("Congratulations, you're a genius! You got everything right. Thanks for playing our game!");
        }
        else if (score == 2) { 
            System.out.println("Almost there! You got 2 out of 3 right. Thanks for playing our game!");
        }
        else if (score == 1) { 
            System.out.println("Better luck next time! You got 1 question right. Thanks for playing our game!");
        }
        else { 
            System.out.println("Where you even trying? You got everything wrong. Thanks for playing our game!");
        }
    }
}
