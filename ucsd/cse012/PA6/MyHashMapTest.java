// Import JUnit & packages
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.*;

/* 
 * class MyHashMapTest to test MyHashMap.java.
 * Runs JUnit tests to confirm the desired behavior.
 * 
 * @author Andrew Onozuka
 * received help from Benjamin Liang
 */

public class MyHashMapTest {
	
	private DefaultMap<String, String> testMap; // use this for basic tests
	private DefaultMap<String, String> mapWithCap; // use for testing proper rehashing
	public static final String TEST_KEY = "Test Key";
	public static final String TEST_VAL = "Test Value";
	
	@Before
	public void setUp() {
		testMap = new MyHashMap<>();
		mapWithCap = new MyHashMap<>(4, MyHashMap.DEFAULT_LOAD_FACTOR);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPut_nullKey() {
		testMap.put(null, TEST_VAL);
	}

	@Test
	public void testKeys_nonEmptyMap() {
		// You don't have to use array list 
		// This test will work with any object that implements List
		List<String> expectedKeys = new ArrayList<>(5);
		for(int i = 0; i < 5; i++) {
			// key + i is used to differentiate keys since they must be unique
			testMap.put(TEST_KEY + i, TEST_VAL + i);
			expectedKeys.add(TEST_KEY + i);
		}
		List<String> resultKeys = testMap.keys();
		// we need to sort because hash map doesn't guarantee ordering
		Collections.sort(resultKeys);
		assertEquals(expectedKeys, resultKeys);
	}
	
	/* Add more of your tests below */
	
	// Testing containsKey().
	@Test
	public void testContainsKey() {
		DefaultMap<String, String> test = new MyHashMap<>();
		for(int i = 0; i < 5; i++) {
			// key + i is used to differentiate keys since they must be unique
			test.put(TEST_KEY + i, TEST_VAL + i);
		}
		assertTrue(test.containsKey(TEST_KEY + 0));
	}
	// Testing get().
	@Test
	public void testGet() {
		DefaultMap<String, String> test = new MyHashMap<>();
		for(int i = 0; i < 5; i++) {
			// key + i is used to differentiate keys since they must be unique
			test.put(TEST_KEY + i, TEST_VAL + i);
		}
		assertEquals(test.get(TEST_KEY + 0), TEST_VAL + 0);
	}
	// Testing get() and expecting null.
	@Test
	public void testGetEmpty() {
		DefaultMap<String, String> test = new MyHashMap<>();
		assertEquals(test.get(TEST_KEY + 0), null);
	}
	// Testing remove() and expecting to return false.
	@Test
	public void testRemoveKeyDNE() {
		DefaultMap<String, String> test = new MyHashMap<>();
		for(int i = 0; i < 5; i++) {
			// key + i is used to differentiate keys since they must be unique
			test.put(TEST_KEY + i, TEST_VAL + i);
		}
		assertFalse(test.remove(TEST_KEY + 5));
	}
	// Testing set(), containsKey().
	@Test
	public void testSetPut() {
		DefaultMap<String, String> test = new MyHashMap<>();
		test.set(TEST_KEY+0, TEST_VAL + 0);
		assertTrue(test.containsKey(TEST_KEY+0));
	}
}