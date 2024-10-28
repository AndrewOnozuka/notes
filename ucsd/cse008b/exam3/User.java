$ ssh ///////////////////////////////////////////////////////////////////////////////
// Main Class File:    ServerQueueTester.java
// File:               User.java
// Quarter:            CSE 8B Winter 2022
//
// Author:             Given
// Email:              Given
// Instructor's Name:  Given
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * An ABSTRACT class that maintains simple user information.
 * 
 * DO NOT MODIFY THIS FILE.
 *
 * Bugs: None found.
 *
 * @author Given
 */
public abstract class User {
    
    // DO NOT CHANGE THE CODE FOR THE PRIVATE MEMBER VARIABLE.
    private String name; 

    /**
     * Getter method to return the name.
     *
     * @return the name of the user
     */
    public String getName() {
        return this.name;
    }

    /**
     * Setter method to change the name.
     *
     * @param name - the name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    // To be implemented in subclasses...
    abstract String getInformation();
    abstract boolean getPriority();
}