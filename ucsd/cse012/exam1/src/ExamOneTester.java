
//
//import static org.junit.Assert.*;
//
//import java.util.Arrays;
//import java.util.Collection; 
//import java.util.NoSuchElementException;
//
//import org.junit.*;
//import org.junit.runner.RunWith;
//import org.junit.runners.Parameterized;
//
//@RunWith(Parameterized.class)
//public class ExamOneTester{
//	
//	public static Collection<Object[]> LISTNUMS =
//			Arrays.asList(new Object[][] { {"Linked"}, {"Array"} });
//	private String listType;
//
//	public ExamOneTester(String listType) {
//		super();
//		this.listType = listType;
//	}
//
//	@Parameterized.Parameters(name = "{0}List")
//	public static Collection<Object[]> bags() {
//		return LISTNUMS;
//	}
//
//	private <E> Exam_List<E> makeList() {
//		switch (this.listType) {
//		case "Linked":
//			return new Exam_LList<E>();
//		case "Array":
//			return new Exam_AList<E>();
//		}
//		return null;
//	}
//
//	}
//	@Test
//	public void flipAtfirst() {
//		Exam_List<Integer> ints = makeList();
//		Integer[] flipped = {2,3,4,5,1};
//		ints.add(1);
//		ints.add(2);
//		ints.add(3);
//		ints.add(4);
//		ints.add(5);
//		ints.flipAround(1);
//		assertArrayEquals(flipped, ints.toArray());
//	}
//	@Test
//	public void flipAtLast() {
//		Exam_List<Integer> ints = makeList();
//		Integer[] flipped = {5,1,2,3,4};
//		ints.add(1);
//		ints.add(2);
//		ints.add(3);
//		ints.add(4);
//		ints.add(5);
//		ints.flipAround(5);
//		assertArrayEquals(flipped, ints.toArray());
//	}
//	@Test
//	public void String() {
//		Exam_List<String> ints = makeList();
//		String[] str_input = {"apple","banana","pear","melon","orange"};
//		ints.add("apple");
//		ints.add("banana");
//		ints.add("pear");
//		ints.add("melon");
//		ints.add("orange");
//		assertArrayEquals(str_input, ints.toArray());
//	}
//	@Test
//	public void StringFlipped() {
//		Exam_List<String> ints = makeList();
//		String[] flipped = {"banana","pear","melon","orange","apple"};
//		ints.add("apple");
//		ints.add("banana");
//		ints.add("pear");
//		ints.add("melon");
//		ints.add("orange");
//		ints.flipAround("apple");
//		assertArrayEquals(flipped, ints.toArray());
//	}
//}
//

//
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ExamOneTester {
	@Test 
	public void addIntAL() {
		Integer[] int_input = {0,1,1,2,null};
		Exam_AList<Integer> ilist = new Exam_AList<Integer>();
		ilist.add(0);
		ilist.add(1);
		ilist.add(1);
		ilist.add(2);
		ilist.add(null);
		assertArrayEquals(int_input, ilist.toArray());
	}
	@Test
	public void addStrAL() {
		Exam_AList<String> slist = new Exam_AList<String>();
		String[] str_input = {"apple","banana","pear","melon","orange"};
		slist.add("apple");
		slist.add("banana");
		slist.add("pear");
		slist.add("melon");
		slist.add("orange");
		assertArrayEquals(str_input, slist.toArray());
	}
	@Test 
	public void addIntLL() {
		Integer[] int_input = {0,1,1,2,null};
		Exam_LList<Integer> ilist = new Exam_LList<Integer>();
		ilist.add(0);
		ilist.add(1);
		ilist.add(1);
		ilist.add(2);
		ilist.add(null);
		assertArrayEquals(int_input, ilist.toArray());
	}
	@Test
	public void addStrLL() {
		Exam_LList<String> slist = new Exam_LList<String>();
		String[] str_input = {"apple","banana","pear","melon","orange"};
		slist.add("apple");
		slist.add("banana");
		slist.add("pear");
		slist.add("melon");
		slist.add("orange");
		assertArrayEquals(str_input, slist.toArray());
	}
	@Test
	public void flipFirstIntAL() {
		Exam_AList<Integer> ilist = new Exam_AList<Integer>();
		Integer[] int_input = {0,1,2,3,4};
		Integer[] expected = {1,2,3,4,0};
		ilist.add(0);
		ilist.add(1);
		ilist.add(2);
		ilist.add(3);
		ilist.add(4);
		ilist.flipAround(0);
		assertArrayEquals(expected, ilist.toArray());
	}
	@Test
	public void flipFirstStrAL() {
		Exam_AList<String> slist = new Exam_AList<String>();
		String[] str_input = {"a","b","c","d","e"};
		String[] expected = {"b","c","d","e","a"};
		slist.add("a");
		slist.add("b");
		slist.add("c");
		slist.add("d");
		slist.add("e");
		slist.flipAround("a");
		assertArrayEquals(expected, slist.toArray());
	}
	@Test
	public void flipFirstIntLL() {
		Exam_LList<Integer> ilist = new Exam_LList<Integer>();
		Integer[] int_input = {0,1,2,3,4};
		Integer[] expected = {1,2,3,4,0};
		ilist.add(0);
		ilist.add(1);
		ilist.add(2);
		ilist.add(3);
		ilist.add(4);
		ilist.flipAround(0);
		assertArrayEquals(expected, ilist.toArray());
	}
	@Test
	public void flipFirstStrLL() {
		Exam_LList<String> slist = new Exam_LList<String>();
		String[] str_input = {"a","b","c","d","e"};
		String[] expected = {"b","c","d","e","a"};
		slist.add("a");
		slist.add("b");
		slist.add("c");
		slist.add("d");
		slist.add("e");
		slist.flipAround("a");
		assertArrayEquals(expected, slist.toArray());
	}
	@Test
	public void flipLastIntAL() {
		Exam_AList<Integer> ilist = new Exam_AList<Integer>();
		Integer[] int_input = {0,1,2,3,4};
		Integer[] expected = {4,0,1,2,3};
		ilist.add(0);
		ilist.add(1);
		ilist.add(2);
		ilist.add(3);
		ilist.add(4);
		ilist.flipAround(4);
		assertArrayEquals(expected, ilist.toArray());
	}
	@Test
	public void flipLastStrAL() {
		Exam_AList<String> slist = new Exam_AList<String>();
		String[] str_input = {"a","b","c","d","e"};
		String[] expected = {"e","a","b","c","d"};
		slist.add("a");
		slist.add("b");
		slist.add("c");
		slist.add("d");
		slist.add("e");
		slist.flipAround("e");
		assertArrayEquals(expected, slist.toArray());
	}
	@Test
	public void flipLastIntLL() {
		Exam_LList<Integer> ilist = new Exam_LList<Integer>();
		Integer[] int_input = {0,1,2,3,4};
		Integer[] expected = {4,0,1,2,3};
		ilist.add(0);
		ilist.add(1);
		ilist.add(2);
		ilist.add(3);
		ilist.add(4);
		ilist.flipAround(4);
		assertArrayEquals(expected, ilist.toArray());
	}
	@Test
	public void flipLastStrLL() {
		Exam_LList<String> slist = new Exam_LList<String>();
		String[] str_input = {"a","b","c","d","e"};
		String[] expected = {"e","a","b","c","d"};
		slist.add("a");
		slist.add("b");
		slist.add("c");
		slist.add("d");
		slist.add("e");
		slist.flipAround("e");
		assertArrayEquals(expected, slist.toArray());
	}
}
//  @Test
//  public void addStrAL() {
//	  Exam_AList<String> slist = new Exam_AList<String>();
//	  slist.add("banana");
//	  slist.add("apple");
//	  assertEquals("banana", slist.get(0));
//	  assertEquals("apple", slist.get(1));
//  }
//  @Test
//  public void addThenSizeAL() {
//	  Exam_AList<Integer> ilist = new Exam_AList<Integer>();
//    ilist.add(500);
//    ilist.add(12);
//    assertEquals(2, ilist.size());
//  }
//  @Test
//  public void addIntLL() {
//	  Integer[] expected = {1,2,4,5,6,8};
//	  Exam_LList<Integer> ilist = new Exam_LList<Integer>();
//	  ilist.add(1);
//	  ilist.add(2);
//	  ilist.add(4);
//	  ilist.add(5);
//	  ilist.add(6);
//	  ilist.add(8);
//	  assertArrayEquals(expected, ilist.toArray());
//  }
//  @Test
//  public void addStrLL() {
//	  String[] expected = {"banana","apple"};
//	  Exam_LList<String> slist = new Exam_LList<String>();
//	  slist.add("banana");
//	  slist.add("apple");
//	  System.out.println(slist.get(0));
//	  System.out.println(slist.get(1));
//	  assertArrayEquals(expected, slist.toArray());
//  }
//  @Test
//  public void addGetPrependLL() {
//	  Exam_LList<Integer> ilist = new Exam_LList<Integer>();
//	  ilist.add(1);
//	  ilist.add(2);
//	  ilist.add(3);
//	  ilist.add(4);
//	  ilist.add(5);
//	  System.out.println(ilist.get(0));
//	  System.out.println(ilist.get(1));
//	  System.out.println(ilist.get(2));
//	  System.out.println(ilist.get(3));
//	  System.out.println(ilist.get(4));
//	  System.out.println(ilist.get(-1));
//	  ilist.prepend(9);
//	  System.out.println(ilist.get(0));
//	  System.out.println(ilist.get(1));
//	  System.out.println(ilist.get(2));
//	  System.out.println(ilist.get(3));
//	  System.out.println(ilist.get(4));
//	  System.out.println(ilist.get((Integer) null));
//  }
//  @Test
//  public void flipAroundLL() {
//    Exam_AList<Integer> ilist = new Exam_AList<Integer>();
//    ilist.add(500);
//    ilist.add(12);
//    assertEquals(2, ilist.size());
//  }
//  
//  
//  
//  @Test
//  public void testListOfLists() {
//
//    // Fill in declaration of bllist
//	//List<List<String>> bllist = new List<List<String>>();		//A
//	//List<String> bllist = new AList<String>();					//B
//	//List<List<String>> bllist = new AList<AList<String>>();		//C
//	//List<List<String>> bllist = new List<AList<String>>();		//D
//	List<List<String>> bllist = new AList<List<String>>();		//E
//
//    bllist.add(new AList<String>());
//    bllist.add(new AList<String>());
//    bllist.get(0).add("a");
//    bllist.get(0).add("b");
//    bllist.get(1).add("c");
//    bllist.get(1).add("d");
//    bllist.get(1).add("e");
//
//    assertEquals("a", bllist.get(0).get(0));
//    assertEquals("b", bllist.get(0).get(1));
//    assertEquals("c", bllist.get(1).get(0));
//    assertEquals("d", bllist.get(1).get(1));
//    assertEquals("e", bllist.get(1).get(2));
//  }
// }
