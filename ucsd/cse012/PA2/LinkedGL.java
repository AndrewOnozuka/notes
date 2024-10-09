public class LinkedGL<E> implements MyList<E> {

    class Node {
        E value;
        Node next;

        public Node(E value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    Node front;
    int size;

    public LinkedGL(E[] contents) {
//    	if (contents.length > 0 && contents != null) {
//    		this.front = new Node(contents[0], null);
//    		Node curr = this.front;
//    		size = 1;
//    		while (curr.next == null && contents.length > size) {
//    			curr.next = new Node(contents[size], null);
//    			curr = curr.next;
//    			size++;
//    		}
//    	}
        this.front = new Node(null, null);
        size = 1;
//        this.size = contents.length;
        Node curr = this.front;
        for (int i = 0; i < contents.length; i++) {
            Node next = new Node(contents[i], null);
            curr.next = next;
            curr = curr.next;
            size++;
//            if (this.front == null) {
//                this.front = node;
//            } else {
//                Node temp = this.front;
//                while (temp.next != null) {
//                    temp = temp.next;
//                }
//                temp.next = node;
//            }
        }
    }

	public boolean isEmpty() {
		if (this.size - 1 == 0) {
			return true;
		} return false;
	}

    public E[] toArray() {
    	E[] arr = (E[]) new Object[this.size-1]; 
        Node curr = this.front.next;
//        int idx = 0;
//        while (idx < size) {
//        	arr[idx] = curr.value;
//        	curr = curr.next;
//        	idx++;
//        } return arr;
    	for (int i = 0; i < arr.length; i++) {
    		arr[i] = curr.value;
            curr = curr.next;
    	}
    	return arr;
    }
    
    public void transformAll(MyTransformer mt) {
		Node curr = this.front.next;
		for (int i = 0; i < this.size - 1; i++) {
			if (curr.value != null) {
				curr.value = (E) mt.transformElement(curr.value);
			} curr = curr.next;
		}
//		while (curr != null) {
//			if (curr.value != null) {
//				curr.value = (E) mt.transformElement(curr.value);
//				curr = curr.next;
//			} else  {
//				curr = curr.next;
//			}
//		}
	}
    
    public void chooseAll(MyChooser mc) {
    	Node curr = this.front;
    	while (curr.next != null) {
    		if (curr.next.value == null) {
    			curr.next = curr.next.next;
    			size--;
    			continue;
    		}
    		if (mc.chooseElement(curr.next.value) == true) {
    			curr = curr.next;
    		} else {
    			curr.next = curr.next.next;
    			size--;
    		}
    	}
//    	if (this.front == null) {
//    		return;
//    	}
//    	while (this.front.value == null || !mc.chooseElement(this.front.value)) {
//    		this.front = this.front.next;
//    		size--;
//    	}
//    	Node curr = this.front;
//    	while (curr != null) {
//    		if (curr.next != null && curr.next.value != null && !mc.chooseElement(curr.next.value)) {
//    			curr.next = curr.next.next;
//    			size--;
//    		} else if (curr.next != null && curr.next.value == null) {
//    			curr.next = curr.next.next;
//    			size--;
//    		} else {
//    			curr = curr.next;
//    		}
//    	}
    }
}