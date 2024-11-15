///////////////////////////////////////////////////////////////////////////////
// Title:              School
// Files:              SchoolTester.java, School.java, Student.java
// Quarter:            CSE 8B Winter 2022
//
// Author:             (YOUR NAME)
// Email:              (YOUR EMAIL ADDRESS)
// Instructor's Name:  (NAME OF YOUR INSTRUCTOR)
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * A class to test the School and Student class.
 *
 * Bugs: None known
 *
 * @author (YOUR NAME)
 */
public class SchoolTester {
    /**
     * Execution point of testing code for the School and Student class.
     * 
     * DO NOT MODIFY METHOD DECLARATION.
     */
    public static void main(String[] args) {
        Student stu1 = new Student("John Doe", new int[] {98, 94, 84});
        Student stu2 = new Student("Jane Doe", new int[] {100, 95, 90});
        Student stu3 = new Student();
        Student[] stuList = new Student[] {stu1, stu2, stu3};
        School sch1 = new School("UCSD", stuList);
        sch1.meanScore(3);
        System.out.println(sch1.meanScore(3));

        // Testing for simple assignment within constructor.
        // We do NOT want a shallow copy - we want a deep copy.
        // As such, if both arrays have the same reference, then this test fails.
        if (sch1.getStudents() == stuList) {
            System.out.println("Test Failed! Shallow copy detected.");
        }

        // Testing `meanScore()` for `sch1`.
        double[] output = sch1.meanScore();
        double[] expected = new double[] {92.0, 95.0, -1.0};
        // Ensure that both arrays are the same length.
        if (output.length != expected.length) {
            System.out.println("Test Failed! Array lengths do not match.");
        }
        // Ensure that each element of `output` matches the respective element from `expected`.
        for (int i = 0; i < output.length; i += 1) {
            if (output[i] != expected[i]) {
                System.out.println("Test Failed! Array elements at index " + i + " do not match.");
                break;
            }
        }

        // You should write more tests to ensure deep copy (and copy constructor) work properly,
        // and to test `meanScore()` and `meanScore(int idx)`.
    }
}
