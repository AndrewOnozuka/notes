// Import packages
import static org.junit.Assert.*;
import java.util.List;
import org.junit.*;

/* 
 * Class for testing BST.java.
 * Runs JUnit tests to confirm the desired behavior.
 * 
 * @author Andrew Onozuka
 * received help from Benjamin Liang
 */

public class BSTTest {
	// Testing put and containsKey.
	@Test
	public void testPutAndContainsKey() {
		BST<String, Integer> tree = new BST<String, Integer>();
		tree.put("a", 10);
		tree.put("c", 80);
		tree.put("b", 200);
		tree.put("g", 200);
		assertTrue(tree.containsKey("a"));
	}
	// Testing put and replace.
	@Test
	public void testReplace() {
		BST<String, Integer> tree = new BST<String, Integer>();
		tree.put("a", 10);
		tree.put("c", 80);
		tree.put("b", 200);
		tree.put("g", 200);
		assertTrue(tree.replace("a", 20));
		assertEquals(tree.get("a"), (Object)20);
	}
	// Testing put and get.
	@Test
	public void testGet() {
		BST<String, Integer> tree = new BST<String, Integer>();
		tree.put("a", 10);
		tree.put("c", 80);
		tree.put("b", 200);
		tree.put("g", 200);
		assertEquals(tree.get("a"), (Object) 10);
	}
	// Testing set and remove.
	@Test
	public void testRemoveTrue() {
		BST<String, Integer> tree = new BST<String, Integer>();
		tree.set("a", 10);
		tree.set("c", 80);
		tree.set("b", 200);
		tree.set("g", 200);
		assertTrue(tree.remove("a"));
		System.out.println(tree.keys());
	}
	@Test
	public void testRemoveTrue2() {
		BST<String, Integer> tree = new BST<String, Integer>();
		tree.set("a", 10);
		tree.set("c", 80);
		tree.set("b", 200);
		tree.set("g", 200);
		tree.set("f", 100);
		tree.set("h", 300);
		tree.set("j", 300);
		tree.set("l", 300);
		tree.set("m", 300);
		tree.set("n", 300);
		assertTrue(tree.remove("c"));
		System.out.println(tree.keys());
	}
	// Testing put and keys.
	@Test
	public void testKeys() {
		BST<String, Integer> tree = new BST<String, Integer>();
		tree.put("a", 10);
		tree.put("c", 80);
		tree.put("b", 200);
		tree.put("g", 200);
		assertEquals(tree.keys(), List.of("a", "b", "c", "g"));
	}
	@Test
	public void testKeys2() {
		BST<String, Integer> tree = new BST<String, Integer>();
		tree.put("a", 10);
		tree.put("b", 80);
		tree.put("c", 200);
		tree.put("d", 200);
		assertEquals(tree.keys(), List.of("a", "b", "c", "d"));
	}
	@Test
	public void testKeys3() {
		BST<String, Integer> tree = new BST<String, Integer>();
		tree.put("d", 10);
		tree.put("a", 80);
		tree.put("c", 200);
		tree.put("b", 200);
		assertEquals(tree.keys(), List.of("a", "b", "c", "d"));
	}
}
