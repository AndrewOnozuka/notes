///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:    PA6.java
// File:               RedditDataPoint.java
// Quarter:            Winter 2022
//
// Author:             Kameron Bahmanyar Gano, kgano@ucsd.edu
// Instructor's Name:  Prof. Gregory Miranda
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
// Pair Partner:       Ryo Andrew Onozuka
// Email:              ronozuka@ucsd.edu
// Instructor's Name:  Prof. Gregory Miranda
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   fully acknowledge and credit all sources of help,
//                   other than Instructors and TAs.
//
// Persons:          N/A
//
// Online sources:   N/A
//
//////////////////////////// 80 columns wide //////////////////////////////////


/**
 * RedditDataPoint class
 * @author: Kameron Gano, Andrew Onozuka
 * Date: February 11th, 2022
 */

public class RedditDataPoint {

    private String name = "";
    private String text = "";
    private double lex_liwc_i = 60.0;
    private double lex_liwc_we = 0.0;
    private double lex_liwc_shehe = 0.0;
    
    /**
     * Assigns implicit references to the corresponding data point from reddit.
     *
     * @param name
     * @param text
     * @param lex_liwc_i
     * @param lex_liwc_we
     * @param lex_liwc_shehe
     * @return N/A
     */
    public RedditDataPoint(String name, String text, double lex_liwc_i, 
                           double lex_liwc_we, double lex_liwc_shehe) {

        this.name = name;
        this.text = text;
        this.lex_liwc_i = lex_liwc_i;
        this.lex_liwc_we = lex_liwc_we;
        this.lex_liwc_shehe = lex_liwc_shehe;
    }

    /**
     * gets the name of the thread for the post
     *
     * @return name of reddit thread
     */
    public String getName(){
        return this.name;
    }

    /**
     * gets what was said in the post
     *
     * @return text of the reddit post
     */
    public String getText(){
        return this.text;
    }

    /**
     * gets the lexical score of pronoun "I" in the post
     *
     * @return lexical score of pronoun "I"
     */
    public double getLexLiwcI() {
        return this.lex_liwc_i;
    }

    /**
     * gets the lexical score of pronoun "we" in the post
     *
     * @return lexical score of pronoun "we"
     */
    public double getLexLiwcWe() {
        return this.lex_liwc_we;
    }

    /**
     * gets the lexical score of pronoun "she/he" in the post
     *
     * @return lexical score of pronouns "she/he"
     */
    public double getLexLiwcShehe() {
        return lex_liwc_shehe;
    }
}