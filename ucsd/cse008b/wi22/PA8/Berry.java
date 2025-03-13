///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:    PA8Tester.java
// File:               Berry.java
// Quarter:            CSE 8B Winter 2022
//
// Author:             Kameron Gano
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
 * The Berry class extends the Item class and allows us to set and get the
 * necessary information associated with each type of Berry.
 *
 * Bugs: N/A
 *
 * @author Kameron Gano
 * @author Andrew Onozuka
 */
public class Berry extends Item {
    private int patienceIncrement;
    private int speedDecrement;

    /**
     * Berry method with no parameters. Calls the constructor from the
     * parent class, initializes patienceIncrement and speedDecrement both
     * with 0.
     *
     * @return N/A
     */
    public Berry() {
        super(); // accesses the superclass
        this.patienceIncrement = 0; // initializes with 0
        this.speedDecrement = 0; // initializes with 0
    }

    /**
     * Berry method with parameters. Still calls the parent class
     * constructor and initializes values with given parameters.
     *
     * @param berryName name of berry
     * @param patienceInc patience increment
     * @param speedDec speed decrement
     * @return N/A
     */
    public Berry (String berryName, int patienceInc, int speedDec) {
        super(berryName); // accesses the superclass for berryName
        this.patienceIncrement = patienceInc; //initializes with patienceInc
        this.speedDecrement = speedDec; // initializes with speedDec
    }

    /**
     * getPatienceIncrement method. Returns the value of increment.
     *
     * @return this.patienceIncrement patience increment of implicit ref.
     */
    public int getPatienceIncrement() {
        return this.patienceIncrement; // returns implicit patience increment
    }

    /**
     * getSpeedDecrement method. Returns the value of decrement.
     *
     * @return this.speedDecrement speed decrement of implicit ref.
     */
    public int getSpeedDecrement() {
        return this.speedDecrement; // returns implicit speed decrement
    }

    /**
     * Override toString method that returns the name of berry alongside its
     * patience increment and speed decrement stats.
     *
     * @return showBerryName name of berry
     * @return showPatInc value of patience increment
     * @return showSpdDec value of speed decrement
     */
    @Override
    public String toString() {
        String showBerryName = this.getName() + "\n";
        String showPatInc = "patienceIncrement: " +
                            this.getPatienceIncrement() + "\n";
        String showSpdDec = "speedDecrement: " + this.getSpeedDecrement() + "\n";
        return showBerryName + showPatInc + showSpdDec; // returns string
    }
}
