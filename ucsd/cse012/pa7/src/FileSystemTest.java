// Import packages
import static org.junit.Assert.*;
import java.util.List;
import org.junit.*;

/* 
 * Class for testing FileSystem.java.
 * Runs JUnit tests to confirm the desired behavior.
 * 
 * @author Andrew Onozuka
 * received help from Benjamin Liang
 */

public class FileSystemTest {
	// Testing adding name map.
	@Test
	public void testAddNameMap()
	{
		FileSystem system = new FileSystem("input.txt");
		System.out.println(system.findFileNamesByDate("2021/02/01"));
	}
	// Testing adding name map with duplicate names, dir, and dates.
	@Test
	public void testAddNameMapDuplicateNameAndDirAndDate()
	{
		FileSystem system = new FileSystem("input.txt");
		system.add("mySample.txt", "/home", "2021/02/01");
		List<String> nameOutput = List.of(
				"mySample.txt: {Name: mySample.txt, Directory: /home, Modified Date: 2021/02/01}", 
				"mySample1.txt: {Name: mySample1.txt, Directory: /root, Modified Date: 2021/02/01}", 
				"mySample2.txt: {Name: mySample2.txt, Directory: /user, Modified Date: 2021/02/06}");
		assertEquals(system.outputNameTree(), nameOutput);
	}
	// Testing adding name map with duplicate names and dir but diff dates.
	@Test
	public void testAddNameMapDuplicateNameDirDiffDate()
	{
		FileSystem system = new FileSystem("input.txt");
		system.add("mySample.txt", "/home", "2021/03/01");
		List<String> nameOutput = List.of(
				"mySample.txt: {Name: mySample.txt, Directory: /home, Modified Date: 2021/03/01}", 
				"mySample1.txt: {Name: mySample1.txt, Directory: /root, Modified Date: 2021/02/01}", 
				"mySample2.txt: {Name: mySample2.txt, Directory: /user, Modified Date: 2021/02/06}");
		assertEquals(system.outputNameTree(), nameOutput);
	}
	// Testing adding name map with duplicate name, date, diff dir.
	@Test
	public void testAddNameMapDuplicateNameAndDateDiffDir()
	{
		FileSystem system = new FileSystem("input.txt");
		system.add("mySample.txt", "/downloads", "2021/02/01");
		List<String> nameOutput = List.of(
				"mySample.txt: {Name: mySample.txt, Directory: /home, Modified Date: 2021/02/01}", 
				"mySample1.txt: {Name: mySample1.txt, Directory: /root, Modified Date: 2021/02/01}", 
				"mySample2.txt: {Name: mySample2.txt, Directory: /user, Modified Date: 2021/02/06}");
		assertEquals(system.outputNameTree(), nameOutput);
	}
	// Testing adding name map with duplicate name, diff dir and date.
	@Test
	public void testAddNameMapDuplicateNameDiffDirDate()
	{
		FileSystem system = new FileSystem("input.txt");
		system.add("mySample.txt", "/downloads", "2021/03/01");
		List<String> nameOutput = List.of(
				"mySample.txt: {Name: mySample.txt, Directory: /downloads, Modified Date: 2021/03/01}", 
				"mySample1.txt: {Name: mySample1.txt, Directory: /root, Modified Date: 2021/02/01}", 
				"mySample2.txt: {Name: mySample2.txt, Directory: /user, Modified Date: 2021/02/06}");
		assertEquals(system.outputNameTree(), nameOutput);
	}
	// Testing adding name map with duplicate date.
	@Test
	public void testAddNameMapDuplicateRecentDate()
	{
		FileSystem system = new FileSystem("input.txt");
		system.add("mySample.txt", "/downloads", "2020/03/01");
		List<String> nameOutput = List.of(
				"mySample.txt: {Name: mySample.txt, Directory: /home, Modified Date: 2021/02/01}", 
				"mySample1.txt: {Name: mySample1.txt, Directory: /root, Modified Date: 2021/02/01}", 
				"mySample2.txt: {Name: mySample2.txt, Directory: /user, Modified Date: 2021/02/06}");
		assertEquals(system.outputNameTree(), nameOutput);
	}
	// testing adding date map with duplicates.
	@Test
	public void testAddDateMapDuplicate()
	{
		FileSystem system = new FileSystem("input.txt");
		system.add("mySample.txt", "/downloads", "2021/03/01");
		List<String> dateOutput = List.of(
				"2021/03/01: {Name: mySample.txt, Directory: /downloads, Modified Date: 2021/03/01}",
				"2021/02/06: {Name: mySample2.txt, Directory: /user, Modified Date: 2021/02/06}", 
				"2021/02/01: {Name: mySample1.txt, Directory: /root, Modified Date: 2021/02/01}");
		System.out.println(system.outputDateTree());
		assertEquals(system.outputDateTree(), dateOutput);
	}
	// Testing outputNameTree method.
	@Test
	public void testOutputNameTree()
	{
		FileSystem system = new FileSystem("input.txt");
		List<String> nameOutput = List.of(
				"mySample.txt: {Name: mySample.txt, Directory: /home, Modified Date: 2021/02/01}", 
				"mySample1.txt: {Name: mySample1.txt, Directory: /root, Modified Date: 2021/02/01}", 
				"mySample2.txt: {Name: mySample2.txt, Directory: /user, Modified Date: 2021/02/06}");
		assertEquals(system.outputNameTree(), nameOutput);
	}
	// Testing outputDateTree method.
	@Test
	public void testOutputDateTree()
	{
		FileSystem system = new FileSystem("input.txt");
		List<String> dateOutput = List.of(
				"2021/02/06: {Name: mySample2.txt, Directory: /user, Modified Date: 2021/02/06}", 
				"2021/02/01: {Name: mySample1.txt, Directory: /root, Modified Date: 2021/02/01}", 
				"2021/02/01: {Name: mySample.txt, Directory: /home, Modified Date: 2021/02/01}");
		assertEquals(system.outputDateTree(), dateOutput);
	}
}