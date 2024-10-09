import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;

import org.junit.Test;

/* 
 * Class to test Heap.java using JUnit.
 */

public class HeapTest {
	// Testing remove (poll) when heap is empty.
	@Test (expected = NoSuchElementException.class)
	public void testHeapFunctionality_checkAndRemoveEmpty() {
		Heap<Integer, Square> test = new Heap<Integer, Square>(Integer::compare);
		assertTrue(test.isEmpty());
		test.poll();
	}
	// Testing peek to check proper max return.
	@Test
	public void testHeapFunctionality_peekAddMax() {
		Heap<Integer, Square> test = new Heap<Integer, Square>(Integer::compare);
		test.add(1, new Square(0,0,false,0));
		test.add(2, new Square(0,0,false,0));
		test.add(3, new Square(0,0,false,0));
		assertEquals(3, test.peek().key, 0);
		// Adds a new max
		test.add(4, new Square(0,0,false,0));
		assertEquals(4, test.peek().key, 0);
	}
	// Testing remove (poll) with peek.
	@Test
	public void testHeapFunctionality_removeMax() {
		Heap<Integer, Square> test = new Heap<Integer, Square>(Integer::compare);
		test.add(1, new Square(0,0,false,0));
		test.add(2, new Square(0,0,false,0));
		test.add(3, new Square(0,0,false,0));
		// Remove max twice
		test.poll();
		test.poll();
		assertEquals(1, test.peek().key, 0);
	}
}
