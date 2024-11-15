
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection; 
import java.util.NoSuchElementException;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class TestLists {

	public static Collection<Object[]> LISTNUMS =
			Arrays.asList(new Object[][] { {"Linked"}, {"Array"} });
	private String listType;

	public TestLists(String listType) {
		super();
		this.listType = listType;
	}

	@Parameterized.Parameters(name = "{0}List")
	public static Collection<Object[]> bags() {
		return LISTNUMS;
	}

	private <E> MyList<E> makeList(E[] contents) {
		switch (this.listType) {
		case "Linked":
			return new LinkedGL<E>(contents);
		case "Array":
			return new ArrayGL<E>(contents);
		}
		return null;
	}

  // Don't change code above this line, it ensures the autograder works as
  // expected


  // This is a sample test; you can keep it, change it, or remove it as you like.
  // Note that it uses the method `assertArrayEquals`, which you should use to
  // test equality of arrays in this PA.
	@Test
	public void testSimpleToArray() {
		// Using the generic list to create an Integer list
		Integer[] int_input = {1, 2, 3};
		MyList<Integer> int_s = makeList(int_input);
		assertArrayEquals(int_input, int_s.toArray());
		
		// Using the generic list to create a String list
		String[] string_input = {"a", "b", "c"};
		MyList<String> string_s = makeList(string_input);
		assertArrayEquals(string_input, string_s.toArray());
	}
	@Test
	public void testTransformAll() {
		String[] input = {"a", "b", "c"};
		String[] expected = {"A", "B", "C"};
		MyList<String> s = makeList(input);
		s.transformAll(new UpperCaseTransformer());
		assertArrayEquals(expected, s.toArray());
	}

	@Test
	public void testTransformAllNull() {
		String[] input = {"a", null, null};
		String[] expected = {"A", null, null};
		MyList<String> s = makeList(input);
		s.transformAll(new UpperCaseTransformer());
		assertArrayEquals(expected, s.toArray());
	}
	
	@Test
	public void testChooseAll() {
		String[] input = {"longword", "longerword", "short"};
		String[] expected = {"longword", "longerword"};
		MyList<String> s = makeList(input);
		s.chooseAll(new LongWordChooser());
		assertArrayEquals(expected, s.toArray());
	}
	
	@Test
	public void testChooseAllNull() {
		String[] input = {"longword", null, "short", "a"};
		String[] expected = {"longword"};
		MyList<String> s = makeList(input);
		s.chooseAll(new LongWordChooser());
		assertArrayEquals(expected, s.toArray());
	}
	
	@Test
	public void testAChooser()
	{
		String[] input = {"longword", "a", null, "b"};
		String[] expected = {"a"};
		MyList<String> string_s = makeList(input);
		string_s.chooseAll(new HasAChooser());
		assertArrayEquals(expected, string_s.toArray());
	}
	
	@Test
	public void testBChooser() {
		String[] input = {"longword", "a", null, "b"};
		String[] expected = {"b"};
		MyList<String> s = makeList(input);
		s.chooseAll(new HasBChooser());
		assertArrayEquals(expected, s.toArray());
	}
	
	@Test
	public void testReplaceAWithB() {
		String[] input = {"longword", "a", null, "b"};
		String[] expected = {"longword", "b",null, "b"};
		MyList<String> s = makeList(input);
		s.transformAll(new ReplaceAWithB());
		assertArrayEquals(expected, s.toArray());
	}
	
	@Test
	public void testReplaceBWithC() {
		String[] input = {"longword", "a", null, "b"};
		String[] expected = {"longword", "a",null, "c"};
		MyList<String> s = makeList(input);
		s.transformAll(new ReplaceBWithC());
		assertArrayEquals(expected, s.toArray());
	}
	
	@Test
	public void testLinkedGLConstructor() {
		String[] input = {};
		MyList<String> s = makeList(input);
		assertArrayEquals(input, s.toArray());
	}
	
	@Test
	public void chooseEmpty() {
		String[] input = {};
		String[] expected = {};
		MyList<String> s = makeList(input);
		s.chooseAll(new LongWordChooser());
		assertArrayEquals(expected, s.toArray());
	}
}