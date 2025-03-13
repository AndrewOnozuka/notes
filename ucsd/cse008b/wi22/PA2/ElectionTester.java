///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:              CSE8B_PA2
// Files:              Candidate.java, ElectionTester.java, Election.java
// Quarter:            CSE8B Winter 2022
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
// Persons:          N/A
//
// Online sources:   The Official Java Documentation (used to undersand "Random" library)
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.util.Scanner; 
/*
 * This Election + Voting program includes 3 classes: Candidate, Election, and ElectionTester.
 * Candidate is responsible for setting and getting presidential candidate data like votes, names, and parties. 
 * Election handles the actual operation of the Election, gathering votes from the user and ultimately determining a winner.
 * (Ties / Run off elections are included)
 * Election Tester runs the election class, using 3 pre-defined candidates with which the user will interact with.
 * The user will be given a total tally of the votes and a winner or initiate a run off election.
 *
 * @Ryo Andrew Onozuka & Kameron Bahmanyar Gano
 */
public class ElectionTester {
    
    public static void main(String[] args) {
        Candidate andrewOno = new Candidate( "Andrew Onozuka", "Warren Party" ); 
        Candidate kamGano = new Candidate( "Kameron Gano", "Revelle Party" );

        // Print stats on andrewOno
        System.out.print( "Candidate " + andrewOno.getName() + ", " );
        System.out.print( "representative of the " + andrewOno.getParty() + "." );
        System.out.println( " Votes: " + andrewOno.getVotes() );

        // Print stats on kamGano
        System.out.print( "Candidate " + kamGano.getName() + ", " );
        System.out.print( "representative of the " + kamGano.getParty() + "." );
        System.out.println( " Votes: " + kamGano.getVotes() );

        // Change stats on andrewOno
        andrewOno.setParty( "Birthday Party" );
        andrewOno.incrementVotes();
        andrewOno.incrementVotes();

        // Print NEW stats on andrewOno
        System.out.print( "Update to candidate " + andrewOno.getName() + ", " );
        System.out.print( "representative of the " + andrewOno.getParty() + "." );
        System.out.println( " Votes: " + andrewOno.getVotes() );


        // Begin the two election runs
        Election electionTest1 = new Election();
        Election electionTest2 = new Election();
        
        Scanner scan = new Scanner(System.in);

        System.out.println( "---------------------------------------------" );
        System.out.println( "ELECTION 1" );
        electionTest1.runElection(scan);

        System.out.println( "---------------------------------------------" );
        System.out.println( "ELECTION 2" );
        electionTest2.runElection(scan);

        scan.close();
    }    
}