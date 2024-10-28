
public interface ArrayListADT<T> {
	
	public static final int size = 0;
	public static final int front = 0;
	public static final int rear = 0;
	public static final int capacity = 0;
	public void addFront(T element);
	public void addRear(T element);
	public T get(int index) throws Exception;
	public T remove();
	public int getSize();
	public int getRear();
	public int getFront();
	public int getCapacity();
}
