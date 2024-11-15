///////////////////////////////////////////////////////////////////////////////
// Title:              ServerQueueTester
// Files:              ServerQueueTester.java, User.java, BasicUser.java,
//                      ServerQueue.java, VIP.java
// Quarter:            CSE 8B Winter 2022
//
// Author:             Ryo Andrew Onozuka
// Email:              ronozuka@ucsd.edu
// Instructor's Name:  Prof. Gregory Miranda
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * A class to test the ServerQueue.
 *
 * Bugs: None known
 *
 * @author Ryo Andrew Onozuka
 */
public class ServerQueueTester {
    
    /**
     * Execution point of testing code for the RaggedArray class.
     * 
     * DO NOT MODIFY METHOD DECLARATION.
     */
    public static void main(String[] args) {
       
        // Creating two server queues...
        ServerQueue sq1 = new ServerQueue("Mari");
        ServerQueue sq2 = new ServerQueue("Valtan");

        // Creating four users - two per queue...
        User u1 = new BasicUser("Berserker");
        User u2 = new VIP("Paladin", false);
        User u3 = new BasicUser("Bard");
        User u4 = new VIP("Deputy", true);

        sq1.appendToQueue(u1);
        sq1.appendToQueue(u2);

        sq2.appendToQueue(u3);
        sq2.appendToQueue(u4);

        // Merging `sq1` and `sq2`
        ServerQueue.mergeQueue(sq1, sq2);

        // The "2nd" index of `sq1` should be `u3`.
        if (sq1.getQueue().get(2).getName().equals("Bard")) {
            System.out.println("TEST 1 PASSED");
        }
        // System.out.println(sq1.getQueue().get(2).getInformation());

        // `sq2` should be empty.
        if (sq2.getQueue().size() == 0) {
            System.out.println("TEST 2 PASSED");
        }

        // `sq1.removeFromQueue()` should return `u4`.
        if (sq1.removeFromQueue().getName().equals("Deputy")) {
            System.out.println("TEST 3 PASSED");
        }
        // */

        // You should write more tests to ensure complete functionality!
        /*
        ServerQueue sq1 = new ServerQueue("NA West");
        ServerQueue sq2 = new ServerQueue("NA East");

        User u1 = new BasicUser("April");
        User u2 = new VIP("Bryant", false);
        User u3 = new VIP("Charles", true);

        sq1.appendToQueue(u1);
        sq2.appendToQueue(u2);
        sq2.appendToQueue(u3);

        // BEFORE
        ServerQueue.mergeQueue(sq1, sq2); // Line 9
        // AFTER

        /*
        System.out.println(sq1.getQueue().get(0).getInformation());
        System.out.println(sq1.getQueue().get(1).getInformation());
        System.out.println(sq1.getQueue().get(2).getInformation());
        // */
    }
}
