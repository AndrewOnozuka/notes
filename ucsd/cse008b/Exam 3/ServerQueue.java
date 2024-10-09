///////////////////////////////////////////////////////////////////////////////
// Main Class File:    ServerQueueTester.java
// File:               ServerQueue.java
// Quarter:            CSE 8B Winter 2022
//
// Author:             Ryo Andrew Onozuka
// Email:              ronozuka@ucsd.edu
// Instructor's Name:  Prof. Gregory Miranda
//////////////////////////// 80 columns wide //////////////////////////////////

import java.util.ArrayList;

/**
 * A CONCRETE class that maintains a server queue.
 *
 * Bugs: None found.
 *
 * @author Ryo Andrew Onozuka
 */
public class ServerQueue {
    
    private String name;
    private ArrayList<User> queue;

    /**
     * Constructor to initialize a Server Queue.
     * The queue should be initialized to be an
     *  empty ArrayList.
     * 
     * DO NOT MODIFY THE CONSTRUCTOR DECLARATION.
     *
     * @param name - the name of the server.
     */
    public ServerQueue(String name) {
        this.name = name;
        this.queue = new ArrayList<User>();
    }

    /**
     * Clear the queue.
     * This is simply done by setting the queue
     *  to a new, empty ArrayList.
     * 
     * DO NOT MODIFY THE CONSTRUCTOR DECLARATION.
     */
    public void clearQueue() {
        this.queue.clear();
    }

    /**
     * Getter method to return the Server name.
     * 
     * @return the name of the server.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter method to return the queue.
     *
     * @return the queue.
     */
    public ArrayList<User> getQueue() {
        return this.queue;
    }

    /**
     * Appends a user to the queue.
     * 
     * DO NOT MODIFY THE CONSTRUCTOR DECLARATION.
     * 
     * @param user - user to append to queue.
     */
    public void appendToQueue(User user) {
        this.queue.add(user);
    }

    /**
     * Remove a user from the queue.
     * 
     * First, this method should check if there is a VIP user in the queue.
     * If there is a VIP user, then you should remove the first instance of
     *  a VIP user.
     * If there is no VIP user, then you should remove the user at the "0th
     *  index" of the queue.
     * 
     * DO NOT MODIFY THE CONSTRUCTOR DECLARATION.
     * 
     * @return the User (either VIP or BasicUser) that is removed from the
     *  queue.
     */
    public User removeFromQueue() {
        User vipUser;
        for (int i = 0; i < getQueue().size(); i++) {
            if (this.queue.get(i).getPriority() == true) {
                vipUser = this.queue.get(i);
                this.queue.remove(i);
                return vipUser;
            }
        }
        vipUser = this.queue.get(0);
        this.queue.remove(0);
        return vipUser;
    }

    /**
     * Merge all the users from `q2` to `q1`.
     * 
     * After this method completes, `q1` should contain all users from `q1`
     *  and `q2`, and `q2` should be completely empty. 
     * 
     * DO NOT MODIFY THE CONSTRUCTOR DECLARATION.
     * 
     * @param q1 - ServerQueue to transfer all users to.
     * @param q2 - ServerQUeue to transfer all users from.
     */
    public static void mergeQueue(ServerQueue q1, ServerQueue q2) {
        for (int i = 0; i < q2.getQueue().size(); i++) {
            q1.appendToQueue(q2.getQueue().get(i));
        }
        q2.clearQueue();
    }
}
