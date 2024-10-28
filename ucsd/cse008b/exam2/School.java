///////////////////////////////////////////////////////////////////////////////
// Main Class File:    SchoolTester.java
// File:               School.java
// Quarter:            CSE 8B Winter 2022
//
// Author:             Ryo Andrew Onozuka
// Email:              ronozuka@ucsd.edu
// Instructor's Name:  Prof. Gregory Miranda
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * A class that maintains the attributes of a school. This includes the name
 * of the school and a list of students.
 *
 * Bugs: None found.
 *
 * @ryoandrewonozuka
 */
public class School {
    // DO NOT CHANGE THE CODE FOR THE PRIVATE MEMBER VARIABLES.
    private String name;
    private Student[] students;

    /**
     * Constructor to initialize the School object.
     * You should be performing a deep copy.
     * 
     * DO NOT MODIFY THE CONSTRUCTOR DECLARATION.
     *
     * @param name the name to (deep) initialize with
     * @param students the students to (deep) copy from
     */
    public School(String name, Student[] students) {

        this.name = new String(name);
        this.students = new Student[students.length];
        for(int i = 0; i < students.length; i++) {
            this.students[i] = students[i];
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
     * Getter method to return `students`.
     * 
     * DO NOT MODIFY THIS METHOD.
     * 
     * @return this.students
     */
    public Student[] getStudents() {
        return this.students;
    }

    /**
     * This method calculates the average (mean) score per student as a
     *  double. If the student name is "N/A", or if the student has
     *  no scores, their corresponding element should be `-1.0`.
     * 
     * No need to round the double to any decimal point - just leave it
     *  as is.
     * 
     * DO NOT MODIFY THE METHOD DECLARATION.
     * 
     * @return an array where each element represents the average score
     *  per student
     */
    public double[] meanScore() {

        Student[] stus = this.getStudents();
        double[] meanScores = new double[stus.length];
        int[][] scores = new int[stus.length][stus[0].getScores().length];
        for(int i = 0; i < stus.length; i++) {
            scores[i] = stus[i].getScores();
        }
        
        for(int i = 0; i < scores.length; i++) {
            int sum = 0;
            int cnt = 0;
            for(int j = 0; j < scores[i].length; j++) {
                sum += scores[i][j];
                cnt++;
            }
            if (scores[i].length == 0) {
                meanScores[i] = -1.0;
            } else {
                meanScores[i] = (double) sum / (double) cnt; 
            }   
        }
        return meanScores;
    }

    /**
     * This method calculates the average (mean) score as a double for the\
     *  specified student. If the student name is "N/A", or if the student has
     *  no scores, then you should return `-1`. Be sure to check other
     *  edge cases.
     * 
     * No need to round the double to any decimal point - just leave it
     *  as is.
     * 
     * DO NOT MODIFY THE METHOD DECLARATION.
     * 
     * @return the average (mean) score of student at parameter `idx`.
     */
    public double meanScore(int idx) {

        Student[] stus = this.getStudents();
        if(this.students.length == 0 || idx >= this.students.length) {
            return -1.0;
        } 
        Student selected = stus[idx];
        int[] stuScores = selected.getScores();

        int sum = 0;
        int cnt = 0;
            for (int i = 0; i < stuScores.length; i++) {
                sum += stuScores[i];
                cnt++;
            }
        return (double) sum / (double) cnt;
    }
}
