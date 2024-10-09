// Import JUnit
import static org.junit.Assert.*;
import org.junit.*;

/* 
 * Class for testing FileSystem.java.
 * Runs JUnit tests to confirm the desired behavior.
 * 
 * @author Andrew Onozuka
 * received help from Benjamin Liang
 */

public class FileSystemTest {
	// Testing add(), expecting false to be returned.
	@Test
	public void testAddFalse() {
		FileSystem a = new FileSystem("/Users/ryoandrewonozuka/Downloads/cse12-pa6-HashMap/pa6-starter/src/input.txt");
		assertFalse(a.add("mySample.txt", "/user", "02/08/2021"));
	}
	// Testing findFile().
	@Test
	public void testFindFile() {
		FileSystem a = new FileSystem("/Users/ryoandrewonozuka/Downloads/cse12-pa6-HashMap/pa6-starter/src/input.txt");
		FileData d = a.findFile("mySample.txt", "/user");
	}
	// Testing findFilesInMultDir().
	@Test 
	public void testFindMultDir() {
		FileSystem a = new FileSystem("/Users/ryoandrewonozuka/Downloads/cse12-pa6-HashMap/pa6-starter/src/input.txt");
		System.out.println(a.findFilesInMultDir("01/01/01"));
	}
	// Testing remove().
	@Test
	public void testRemove() {
		FileSystem a = new FileSystem("/Users/ryoandrewonozuka/Downloads/cse12-pa6-HashMap/pa6-starter/src/input.txt");
		System.out.println(a.findFilesByDate("03/01/01"));
		a.removeByName("another.txt");
		System.out.println(a.findFilesByDate("03/01/01"));
	}
}
