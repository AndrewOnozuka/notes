import java.util.ArrayList;

class Ticket {
    private String ticketStatus;

    public Ticket() {
        ticketStatus = "";
    }

    public void accept() {
        ticketStatus = "accepted";
    }

    public void cancel() {
        ticketStatus = "canceled";
    }

   @Override
    public String toString() {
        if (ticketStatus.equals("")) {
            return "Ticket on Autograder";
        } else {
            return ("Ticket has been " + ticketStatus);
        }
    }
}

public class Autograder {
    private Exam_SQ<Ticket> agTickets;
    
    public Autograder(Exam_SQ<Ticket> agTickets) {
        this.agTickets = agTickets;
    }

    public void add(Ticket ticket) {
        agTickets.add(ticket);
    }

    public Ticket accept() throws Exception {
        if (agTickets.empty()) {
            throw new Exception("Autograder is empty");
        }
        Ticket temp = agTickets.pop();
        temp.accept();
        return temp;
    }

    public Ticket cancel() throws Exception {
        if (agTickets.empty()) {
            throw new Exception("Autograder is empty");
        }
        Ticket temp = agTickets.pop();
        temp.cancel();
        return temp;
    }
}

// Provided Code - Do not change
interface Exam_SQ<E> {
    void add(E element);
    E pop();
    boolean empty();
}

class Exam_Stack<E> implements Exam_SQ<E> {
    ArrayList<E> stack;

    public Exam_Stack() {
        stack = new ArrayList<E>();
    }

    public void add(E element) {
        stack.add(element);
    }

    public E pop() {
        if (stack.size() == 0) {
            return null;
        }
        return stack.remove(stack.size() - 1);
    }

    public boolean empty() {
        return stack.size() == 0;
    }
}

class Exam_Queue<E> implements Exam_SQ<E> {
    ArrayList<E> queue;

    public Exam_Queue() {
        queue = new ArrayList<E>();
    }

    public void add(E element) {
        queue.add(element);
    }

    public E pop() {
        return queue.remove(0);
    }

    public boolean empty() {
        return queue.size() == 0;
    }
}
