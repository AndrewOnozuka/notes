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
///////////////////////////////////////////////////////////////////////////////

import java.util.Scanner;
import java.util.Random;

public class Election {

    // Private member variables and objects
    private Candidate candidate1;
    private Candidate candidate2;
    private Candidate candidate3;

    private int userVote;
    private int maxValue = 0;
    private int winnerLocation = 0;
    private int electionYear = 2020; 

    private int[] votesList = new int[3];
    private String[] namesList = new String[3];
    private String[] twoWayTied = new String[2];


    Random rand = new Random();
    Scanner scan = new Scanner(System.in);


    public Election() {


        candidate1 = new Candidate( "King T'Challa", "Wakandan Party" );
        candidate2 = new Candidate( "Anthony Edward Stark", "Iron Party" );
        candidate3 = new Candidate( "Peter Parker", "Spider-man Party" ); 

        votesList[0] = candidate1.getVotes();
        votesList[1] = candidate2.getVotes();
        votesList[2] = candidate3.getVotes();

        namesList[0] = candidate1.getName();
        namesList[1] = candidate2.getName();
        namesList[2] = candidate3.getName();

    }


    public void runElection(Scanner scan) {

        Election runOff = new Election();

     // Print Candidate stats
        System.out.println("---------------------------------------------"); // FORMATTING
        System.out.println("Welcome to the " + electionYear + " Presidential Election.");
     // Candidate 1 stats

        System.out.print( "1. Candidate " + candidate1.getName() + ", " );
        System.out.println( "representative of the " + candidate1.getParty() + "." );

    
    // Candidate 2 stats

        System.out.print( "2. Candidate " + candidate2.getName() + ", " );
        System.out.println( "representative of the " + candidate2.getParty() + "." );
    
    // Candidate 3 stats
        
        System.out.print( "3. Candidate " + candidate3.getName() + ", " );
        System.out.println( "representative of the " + candidate3.getParty() + "." );

    // Gather votes

        while ( true ) {


            System.out.print( "Please enter the vote for a candidate by their respecitive number, or " );
            System.out.println( "input -1 to end the election: " );

        // User inputs vote selection
            userVote = scan.nextInt();

            if (userVote == 1) {
                candidate1.incrementVotes();
                votesList[0] = candidate1.getVotes();
                continue;
            }

            else if (userVote == 2) {
                candidate2.incrementVotes();
                votesList[1] = candidate2.getVotes();
                continue;
            }

            else if (userVote == 3) {
                candidate3.incrementVotes();
                votesList[2] = candidate3.getVotes();
                continue;
            }

            else if (userVote == -1) {
                break;
            }

        }
        

        // Show final election stats
        System.out.println( "Election results: " );
        for ( int i = 0; i < namesList.length; ++i ) {
            System.out.println( (i + 1) + ". " + namesList[i] + ": " + votesList[i]);
        }

        /* //////////////////////////// DETERMINING WINNER, HANDLING TIES AND RUN OFFS //////////////////////////// */

        // Checks to see if all the values of the votes are unique
        if ( (votesList[0] != votesList[1] ) && ( votesList[1] != votesList[2] ) && (votesList[0] != votesList[1] ) ) {
            
            // Finds max value and its location within the list
            for ( int i = 0; i < votesList.length; ++i ) {
                if ( votesList[i] > maxValue ) {
                    maxValue = votesList[i];
                    winnerLocation = i;
                }
                    
            }
            System.out.println("---------------------------------------------");
            System.out.println( "| " + "The winner is... " + namesList[winnerLocation] + " with " + votesList[winnerLocation] + " vote(s)." + " |" );
        }

        // Checks to see if one value of votes is greater than the rest if the other two are equa/
        else if ( ( (votesList[0] > votesList[1]) && (votesList[1] == votesList[2]) ||
                  (votesList[1] > votesList[0]) && (votesList[0] == votesList[2]) ||
                  (votesList[2] > votesList[1]) && (votesList[0] == votesList[1]) ) ) {
            
            // Finds max value and its location within the list
            for ( int i = 0; i < votesList.length; ++i ) {
                if ( votesList[i] > maxValue ) {
                    maxValue = votesList[i];
                    winnerLocation = i;
                }
            }
            System.out.println("---------------------------------------------");
            System.out.println( "| " + "The winner is... " + namesList[winnerLocation] + " with " + votesList[winnerLocation] + " vote(s)." + " |" );
        }

        // Checks for 3 Way Tie
        else if ( (votesList[0] == votesList[1]) && (votesList[1] == votesList[2]) ) {

            System.out.println( "There has been a 3-way winning tie among: " ); 

            // Prints out all the names
            for ( int j = 0; j < votesList.length; ++j ) {
                    System.out.println( namesList[j] );
                    
            }
            System.out.println( "all with " + votesList[1] + " vote(s), indicating a need for a runoff election!" );
            System.out.println( "+++++++++++++++++++++ RUN OFF +++++++++++++++++++++" );

            runOff.runElection(scan);
        }

        // Checks for 2 Way Tie
        else {

            // Nested for loop determines the the individuals who tied
            for ( int k = 0; k < votesList.length; ++k ) {
                for ( int z = 0; z < votesList.length; ++z ) {

                    if ( (votesList[k] == votesList[z]) && (k != z) ) {
    
                        twoWayTied[0] = namesList[k];
                        twoWayTied[1] = namesList[z];
                    }
                }
            }
            // TWO WAY RUNOFF ELECTION = ELECTORAL COLLEGE INTERVENTION

            System.out.println( "There has been a 2-way tie between: " );
            System.out.println( twoWayTied[0] + " and " + twoWayTied[1] );
            System.out.println( "This necessitates intervention from the Electoral College! They will decide the winner." );
            System.out.println( "++++++++++++++++++++++ TWO WAY TIE ++++++++++++++++++++++" );
            System.out.println( "++++++++++++++ ELECTORAL COLLEGE DECISION +++++++++++++++" );

            System.out.println( "And the winner is... " + twoWayTied[rand.nextInt(1)] ); // Electoral College "chooses" winner

        }
    }
}  
 

