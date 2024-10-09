/*
 * Class to implement SearchWorklist as a Stack and a Queue.
 * You can use any built-in Java collections for this class.
 */

import java.util.*;

class StackWorklist implements SearchWorklist {
	Stack<Square> stack;
	
	public StackWorklist() {
		this.stack = new Stack<Square>();
	}

	public void add(Square c) {
		this.stack.push(c);
	}
		
	public Square remove() {
		return this.stack.pop();
	}

	public boolean isEmpty() {
		return this.stack.isEmpty();
	}
	
}

class QueueWorklist implements SearchWorklist {
	Queue<Square> queue;

	public QueueWorklist() {
		this.queue = new LinkedList<Square>();
    }

	public void add(Square c) {
		this.queue.add(c);
	}

	public Square remove() {
		return this.queue.remove();
	}

	public boolean isEmpty() {
		return this.queue.isEmpty();
	}
}

public interface SearchWorklist {
	void add(Square c);
	Square remove();
	boolean isEmpty();
}
