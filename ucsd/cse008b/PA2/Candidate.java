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

public class Candidate {

    private String name;
    private String party;
    private int voteCount;

    public Candidate(String candidateName, String candidateParty) {

       name = candidateName;
       party = candidateParty;
       voteCount = 0;

    }

    public String getName() {
        return name;
    }

    public String getParty() {
        return party;

    }
    public int getVotes() {
        return voteCount;

    }
    public void setParty(String newParty) {
        party = newParty;
    }

    public void incrementVotes() {
        voteCount += 1;
    }

}