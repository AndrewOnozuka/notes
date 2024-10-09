import static org.junit.Assert.*;

import org.junit.*;
public class CircularArrayListTest {

	
	@Test
	public void test_baseCase() throws Exception {

		ArrayListADT<Integer> cal = new CircularArrayList<Integer> (10);
		//Complete testcase to check elements at few positions
		cal.addFront(1);
		cal.addFront(3);
		cal.addRear(9);
		
		// Expecting: 3, 1, 9
		assertEquals((int) 3, (int) cal.get(0));
		assertEquals((int) 1, (int) cal.get(1));
		assertEquals((int) 9, (int) cal.get(2));
		
		cal.remove();
		cal.remove();
		cal.remove();
		cal.addFront(2022);
		
		// Expecting: 2022
		assertEquals((int) 2022, (int) cal.get(0));
		
		cal.remove();
		
		// Used to check that it throws an exception.
//		assertEquals(null, (int) cal.get(0));
	}
	
}
