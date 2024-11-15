import java.util.NoSuchElementException;
// import java.util.Arrays; // Strictly for Debugging purposes

interface Exam_List<E> {
    void add(E element);
    E[] toArray();
    void flipAround(E element);
}

class Exam_AList<E> implements Exam_List<E> {
    E[] elements;
    int size;           // How many elements are in the list?

    @SuppressWarnings("unchecked")
    public Exam_AList() {
    	// constructor
        this.elements = (E[]) new Object[2];           // Capacity of 2
        this.size = 0;          // Number of elements added
    }
    
    public void add(E element) {
    	// adds element to array list
        expandCapacity();
        this.elements[this.size] = element;
        this.size++;
    }

    public E get(int index) {
    	// returns element @ index
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException();
        }
        return this.elements[index];
    }

    public int size() {
    	// returns size
        return this.size;
    }

    @SuppressWarnings("unchecked")
    private void expandCapacity() {
    	// expands capacity
        int currentCapacity = this.elements.length;
        if (this.size < currentCapacity) {
            return;
        }
        E[] expanded = (E[]) new Object[currentCapacity * 2];
        for (int i = 0; i < this.size; i++) {
            expanded[i] = this.elements[i];
        }
        this.elements = expanded;
    }

    public int flipIndex (E element) {
    	// checks if element index + if it exists
    	int index = -1;
    	for (int i = 0; i < this.size; i++) {
    		if (this.elements[i].equals(element)) {
    			index = i;
    		}
    	}
    	return index;
    }
    @SuppressWarnings("unchecked")
    public void flipAround(E element) {
    	// flips around element
    	// checks if element exists
    	int flipIndex = flipIndex(element);
    	if (flipIndex(element) == -1) {
    		throw new NoSuchElementException();
    	}
    	
    	// if flip index is not at the end
    	if (flipIndex < (this.size - 1)) {
    		E[] flipAround = (E[]) new Object[this.size];
        	for (int i = (flipIndex + 1); i < this.size; i++) {
        		flipAround[i-(flipIndex + 1)] = elements[i];
        	}
        	for (int j = 0; j < (flipIndex + 1); j++) {
        		flipAround[(this.size - (flipIndex + 1)) + j] = elements[j];
        	}
        	elements = flipAround;
        	
        	// if flip index is at the end
    	} else {
    		E[] flipAround = (E[]) new Object[this.size];
    		flipAround[0] = elements[flipIndex];
        	for (int i = 0; i < flipIndex; i++) {
        		flipAround[1 + i] = elements[i];
        	}
        	elements = flipAround;
    	}
    }

    @SuppressWarnings("unchecked")
    public E[] toArray() {
    	// returns Array
        E[] toReturn = (E[]) new Object[this.size];
        for (int i = 0; i < this.size; i++) {
            toReturn[i] = this.elements[i];
        }
        return toReturn;
    }
}

class Exam_LList<E> implements Exam_List<E> {

    class Node {
        E element;
        Node next;
        public Node(E element, Node next) {
            this.element = element;
            this.next = next;
        }
    }

    int size;
    Node front;
    Node end;

    public Exam_LList() {
    	// constructor
        this.front = null;
        this.end = this.front;
        this.size = 0;
    }
    
    public void prepend(E element) {
    	// adds in front
        this.front = new Node(element, this.front);
        this.size++;
    }

    public E get(int index) {
    	// gets element @ index
        Node temp = this.front;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp.element;
    }

    public void add(E element) {
    	// adds Node
    	Node node = new Node(element, null);
    	if (this.front == null) {
    		this.front = node;
    		this.end = this.front;
    		this.size++;
    	}
    	else {
    		Node temp = this.front;
    		while (temp.next != null) {
    			temp = temp.next;
    		}
    		temp.next = node;
    		this.end = node;
    		this.size++;
    	}
    }

    public void flipAround(E element) {
    	// check if element exists
    	int eExists = -1;
    	E[] toReturn = toArray();
    	for (int i = 0; i < toReturn.length; i++) {
    		if (toReturn[i].equals(element)) {
    			eExists = i;
    		}
    	}
    	if (eExists == -1) {
    		throw new NoSuchElementException();
    	}
    	// three scenarios: if, else if, else
    	// if the front node contains the element:
    	if (this.end.element.equals(element)) {
    		Node front = this.end;
    		Node temp = this.front;
    		front.next = temp;
    		while (temp.next != this.end) {
    			temp = temp.next;
    		}
    		temp.next = null;
    		this.end = temp;
    		this.front = front;
    	// if the end node contains the element:
    	} else if (this.front.element.equals(element)) {
    		Node node = new Node(element, null);
    		this.front = this.front.next;
    		this.end.next = node;
    		node = this.end;
    	// if the element is in the middle:
    	} else {
    		Node next = new Node(element, null);
    		Node temp1 = this.front;
    		Node temp2 = temp1;
    		next.next = temp2;
    		while (!(temp1.element.equals(element))) {
    			temp1 = temp1.next;
    		}
    		Node front = temp1.next;
    		Node temp3 = front;
    		while (!(temp3.next.equals(null))) {
    			temp3 = temp3.next;
    		}
    		temp3.next = next;
    		while (!(temp2.next.element.equals(element))) {
    			temp2 = temp2.next;
    		}
    		temp2.next = null;
    		this.front = temp2;
    		this.front = front;
    	}
    }

    @SuppressWarnings("unchecked")
    public E[] toArray() {
    	// returns Array
        E[] toReturn = (E[]) new Object[this.size];
        Node temp = this.front;
        for (int i = 0; i < this.size; i++) {
            toReturn[i] = temp.element;
            temp = temp.next;
        }
        return toReturn;
    }
}