// Import JUnit
import static org.junit.Assert.*;
import org.junit.*;

/* 
 * Class for testing FileData.java.
 * Runs JUnit tests to confirm the desired behavior.
 * 
 * @author Andrew Onozuka
 */

public class FileDataTest {
	// Test for the constructor.
	@Test
    public void testFileDataConstructor() {
        FileData fd = new FileData("test.txt", "test", "01/01/2000");
    }
	// Test for the toString() method.
    @Test
    public void testToString() {
        FileData fd = new FileData("test.txt", "test", "01/01/2000");
        assertEquals("{Name: test.txt, Directory: test, Modified Date: 01/01/2000}", fd.toString());
    }
    
}
