
public class CircularArrayList<T> implements ArrayListADT<T>{
	
	int capacity;
	int size;
	int front;
	int rear;
	T[] arrayList;
	
	@SuppressWarnings("unchecked")
	public CircularArrayList() {
		
		//Fill in the constructor for the circularArrayList
		this.capacity = 100;
		this.front = -1;	// base case
		this.rear = -1;		// base case
		this.arrayList = (T[]) new Object[capacity];
	}
	
	@SuppressWarnings("unchecked")
	public CircularArrayList(int initialCapacity) {
		
		//Fill in the constructor for the circularArrayList
		this.capacity = initialCapacity;
		this.front = -1;	// base case
		this.rear = -1;		// base case
		this.arrayList = (T[]) new Object[capacity];
	}
	
	@Override
	public void addRear(T element) {
		//Method to add element at the rear of the arraylist
		expandCapacity();
		if (rear == -1) {
			front = 0;
			rear = 0;
			arrayList[rear] = element;
			this.size++;
		} else if (rear == capacity-1) {
			rear = 0;
			arrayList[rear] = element;
			this.size++;
		} else {
			rear++;
			arrayList[rear] = element;
			this.size++;
		}
	}

	@Override
	public void addFront(T element) {
		
		//Method to add element at the front of the arraylist i.e. towards start
		expandCapacity();
		if (front == -1) {
			front = 0;
			rear = 0;
			arrayList[front] = element;
			this.size++;
		} else if (front == 0) {
			front = capacity-1;
			arrayList[front] = element;
			this.size++;
		} else {
			front--;
			arrayList[front] = element;
			this.size++;
		}
	}

	public T get(int index) throws Exception {
		
		//Method to get element at a given index of the circularArrayList
		if (index >= size || index < 0) {
			throw new Exception("Given Index Does Not Exist");
		} return arrayList[((front + index) % capacity)];
	}
	


	@Override
	public T remove() {
		
		//Method to remove the element from the front
		if (size == 0) { return null; }
		if (front == rear) {
			int temp = front;
			front = -1;
			rear = -1;
			this.size--;
			assert size == 0;
			return arrayList[temp];
		} else if (front == capacity-1) {
			front = 0;
			this.size--;
			return arrayList[front];
		} else {
			front++;
			this.size--;
			return arrayList[front];
		}
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return this.size;
	}

	@Override
	public int getRear() {
		// TODO Auto-generated method stub
		return this.rear;
	}

	@Override
	public int getFront() {
		// TODO Auto-generated method stub
		return this.front;
	}

	@Override
	public int getCapacity() {
		// TODO Auto-generated method stub
		return this.capacity;
	}
	
	public void expandCapacity() {
		if (this.capacity > this.size) {
			return;
		}
		@SuppressWarnings("unchecked")
		T[] newArray = (T[]) new Object[this.capacity*2];
		for (int i = 0; i < size; i++) {
			try {
				newArray[i] = get(i);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.capacity = capacity*2;
		this.arrayList = newArray;
		this.front = 0;
		this.rear = size-1;
	}
	
}
