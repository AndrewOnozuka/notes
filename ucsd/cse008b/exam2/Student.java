///////////////////////////////////////////////////////////////////////////////
// Main Class File:    SchoolTester.java
// File:               Student.java
// Quarter:            CSE 8B Winter 2022
//
// Author:             Ryo Andrew Onozuka
// Email:              ronozuka@ucsd.edu
// Instructor's Name:  Prof. Gregory Miranda
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * A class that maintains an attributes of a student. This includes their name
 * and a list of exam scores.
 *
 * Bugs: None found.
 *
 * @author (YOUR NAME)
 */
public class Student {
    // DO NOT CHANGE THE CODE FOR THE PRIVATE MEMBER VARIABLES.
    private String name;
    private int[] scores;

    /**
     * Default constructor to initialize the Student to "N/A" and an
     *  empty scores array.
     * You do not need to worry about deep copying (since there
     *  is nothing to copy).
     * 
     * DO NOT MODIFY THIS CONSTRUCTOR.
     */
    public Student() {
        name = new String("N/A");
        scores = new int[0];
    }

    /**
     * Constructor to initialize the Student with the given parameters.
     * You should be performing a deep copy.
     * 
     * DO NOT MODIFY CONSTRUCTOR DECLARATION.
     * 
     * @param name name to (deep) initialize with
     * @param scores scores to (deep) initialize with
     */
    public Student(String name, int[] scores) {

        this.name = new String(name);
        this.scores = new int[scores.length];
        for(int i = 0; i < scores.length; i++) {
            this.scores[i] = scores[i];
        }
    }

    /**
     * (Deep) copy constructor to initialize the Student
     *      with the given Student object.
     * You should be performing a deep copy.
     * 
     * DO NOT MODIFY CONSTRUCTOR DECLARATION.
     * 
     * @param student student to (deep) copy from
     */
    public Student(Student student) {

        this.name = new String(student.name);
        for(int i = 0; i < student.scores.length; i++) {
            this.scores[i] = student.scores[i];
        }
    }

    /**
     * Getter method to return `name`.
     * 
     * DO NOT MODIFY THIS METHOD.
     * 
     * @return this.name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter method to return `scores`.
     * 
     * DO NOT MODIFY THIS METHOD.
     * 
     * @return this.scores
     */
    public int[] getScores() {
        return this.scores;
    }
}
