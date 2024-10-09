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
        this.elements = (E[]) new Object[2];           // Capacity of 2
        this.size = 0;          // Number of elements added
    }
    
    public void add(E element) {
        expandCapacity();
        this.elements[this.size] = element;
        this.size++;
    }

    public E get(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException();
        }
        return this.elements[index];
    }

    public int size() {
        return this.size;
    }

    @SuppressWarnings("unchecked")
    private void expandCapacity() {
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

    // @SuppressWarnings("unchecked")
    public void flipAround(E element) {
        boolean eExists = false;
        int position = 0;
        for (int i = 0; i < this.size; i++) {
            if (this.elements[i] == element) {
                eExists = true;
                position = i;
            }
        }
        if (eExists == false) {
            throw new NoSuchElementException();
        }
        E[] clone = this.elements.clone();
        int j = 0;
        for (int i = position + 1; i < clone.length; i++) {
            this.elements[j] = clone[i];
            j++;
        }
        for (int i = 0; i < position; i++) {
            this.elements[clone.length - position + i] = clone[i];
        }
        this.elements[clone.length - position - 1] = clone[position];
    }

    @SuppressWarnings("unchecked")
    public E[] toArray() {
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

    public Exam_LList() {
        this.front = new Node(null, null);
        this.size = 0;
    }
    
    public void prepend(E element) {
        this.front.next = new Node(element, this.front.next);
        this.size++;
    }

    public E get(int index) {
        Node temp = this.front.next;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp.element;
    }

    public void add(E element) {
    	if (this.size == 0) {
    		this.front = new Node(element, null);
    		this.size++;
    	}
    	else {
    		Node temp = this.front;
    		while (temp.next != null) {
    			temp = temp.next;
    		}
    		temp.next = new Node(element, null);
    		this.size++;
    	}
    }

    public void flipAround(E element) {
        boolean eExists = false;
        Node curr = this.front;
        Node headNode = this.front.next;

        while (curr.next != null) {
            if (curr.element.equals(element)) {
                eExists = true;
                break;
            }
            curr = curr.next;
        }

        if (eExists == false) {
            throw new NoSuchElementException();
        }
        Node atElement = curr;
        Node afterElement = curr.next;

        while (curr != null) {
            if (curr.next == null) {
            break;
            }
            curr = curr.next;
        }
        Node tailNode = curr;
        
        curr = this.front;
         while (curr.next != null) {
            if (curr.next.element.equals(element)) {
                break;
            }
            curr = curr.next;
        }
        Node beforeElement = curr;

        this.front.next = afterElement;
        tailNode.next = atElement;
        atElement.next = headNode;
        beforeElement.next = null;
    }

    @SuppressWarnings("unchecked")
    public E[] toArray() {
        E[] toReturn = (E[]) new Object[this.size];
        Node curr = this.front;
        for (int i = 0; i < this.size; i++) {
            toReturn[i] = curr.element;
            curr = curr.next;
        }
        return toReturn;
    }
}