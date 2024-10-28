///////////////////////////////////////////////////////////////////////////////
// Main Class File:    ServerQueueTester.java
// File:               VIP.java
// Quarter:            CSE 8B Winter 2022
//
// Author:             Ryo Andrew Onozuka
// Email:              ronozuka@ucsd.edu
// Instructor's Name:  Prof. Gregory Miranda
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * A CONCRETE class that maintains VIP user information.
 *
 * Bugs: None found.
 *
 * @author Ryo Andrew Onozuka
 */
public class VIP extends User {

    // HINT: You should use these final Strings in `getInformation()`.
    public static final String PRIORITY_STR = " - PRIORITY";
    public static final String NO_PRIORITY_STR = " - NO PRIORITY";

    // DO NOT CHANGE THE CODE FOR THE PRIVATE MEMBER VARIABLE.
    private boolean hasPriority;

    /**
     * Constructor to initialize a VIP User.
     * 
     * DO NOT MODIFY THE CONSTRUCTOR DECLARATION.
     *
     * @param name - the name of the VIP User.
     * @param hasPriority - determines if the VIP User has priority in
     *                      the queue.
     */
    public VIP(String name, boolean hasPriority) {
        this.setName(name);
        this.hasPriority = hasPriority;
    }

    /**
     * Returns if the VIP User has priority.
     * 
     * @return this.hasPriority
     */
    public boolean getPriority() {
        return this.hasPriority;
    }
    
    /**
     * Getter method to return information about the VIP User.
     * 
     * DO NOT MODIFY THE CONSTRUCTOR DECLARATION.
     *
     * @return a string that gives some information about the VIP User.
     */
    public String getInformation() {
        if (hasPriority == true) {
            // String info = "VIP: " + this.getName() + PRIORITY_STR;
            // return info;
            return "VIP: " + this.getName() + PRIORITY_STR;
        }
        else {
            // String info = "VIP: " + this.getName() + NO_PRIORITY_STR;
            // return info;
            return "VIP: " + this.getName() + NO_PRIORITY_STR;
        }
    }
}
