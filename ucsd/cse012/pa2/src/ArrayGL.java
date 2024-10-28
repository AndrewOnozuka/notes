public class ArrayGL<E> implements MyList<E> {

    E[] elements;
    int size;

    public ArrayGL(E[] initialElements) {
        this.elements = initialElements;
        this.size = initialElements.length;
    }
    
    public boolean isEmpty() {
        return this.size == 0;
    }
    
    public E[] toArray() {
    	E[] arr = (E[]) new Object[this.size]; 
    	for (int i = 0; i < arr.length; i++) {
    		arr[i] = this.elements[i];
    	}
    	return arr;
    }
    
    public void transformAll(MyTransformer mt) {
    	for (int i = 0; i < this.size; i++) {
    		if (this.elements[i] == null) {
    			continue;
    		}
    		this.elements[i] = (E)mt.transformElement(elements[i]);
        }
    }

    public void chooseAll(MyChooser mc) {
    	E[] choose = (E[]) new Object[this.size];
    	int newSize = size;
    	int idx = 0;
    	
    	for (int i = 0; i < this.size; i++) {
    		if (this.elements[i] == null || !mc.chooseElement(this.elements[i])) {
    			newSize--;
    			continue;
    		} else {
    			choose[idx] = this.elements[i];
    			idx++;
    		}
    	}
    	this.elements = (E[]) new Object[newSize];
    	System.arraycopy(choose, 0, this.elements, 0, idx);
    	this.size = newSize;
    }
}