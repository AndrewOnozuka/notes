import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.*;

public class MyHashMapTest {
	
	private FileReader filereader;
	private DefaultMap<Integer, Student> testMap; // use this for basic tests

	@Before
	public void setUp() {
		filereader = new FileReader("src/input.txt");
		testMap = new MyHashMap<>();
	}



	//Write testcase for checking max score of 2 sections

	@Test
	public void checkSectionA() {
		Student sangeetha = new Student("Sangeetha", 'A', 85);
		Student derek = new Student("Derek", 'A', 93);
		Student james = new Student("James", 'A', 94);
		Student michael = new Student("Michael", 'A', 80);
		Student sasha = new Student("Sasha", 'A', 85);
		assertFalse(sangeetha.marks == filereader.getMaxOfSection('A').marks);
		assertFalse(derek.marks == filereader.getMaxOfSection('A').marks);
		assertTrue(james.marks == filereader.getMaxOfSection('A').marks);
		assertFalse(michael.marks == filereader.getMaxOfSection('A').marks);
		assertFalse(sasha.marks == filereader.getMaxOfSection('A').marks);
	}
	@Test
	public void checkSectionB() {
		Student alex = new Student("Alex", 'B', 87);
		Student dia = new Student("Dia", 'B', 30);
		Student ria = new Student("Ria", 'B', 88);
		assertFalse(alex.marks == filereader.getMaxOfSection('B').marks);
		assertFalse(dia.marks == filereader.getMaxOfSection('B').marks);
		assertTrue(ria.marks == filereader.getMaxOfSection('B').marks);
	}
	@Test
	public void checkSectionC() {
		Student charles = new Student("Charles", 'C', 67);
		Student peter = new Student("Peter", 'C', 70);
		Student tina = new Student("Tina", 'C', 50);
		assertFalse(charles.marks == filereader.getMaxOfSection('C').marks);
		assertTrue(peter.marks == filereader.getMaxOfSection('C').marks);
		assertFalse(tina.marks == filereader.getMaxOfSection('C').marks);
	}
}