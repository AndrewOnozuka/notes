import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Scanner;

public class FileReader {
	
	String filename;
	Comparator<Student> comparator;
	DefaultMap<Character, Student> hashMap;
	
	// https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html
	public FileReader(String name) {
		this.filename = name;
		this.comparator = (Comparator<Student>) (o1, o2) -> ((Double)o1.marks).compareTo(o2.marks);
		this.hashMap = new MyHashMap<Character, Student>(10, this.comparator);
		createHeap();
	}
	
	public void createHeap() {
        //Method to read input.txt using FileInputStream and putting the student entries to the hashMap
		FileInputStream f;
		try {
			f = new FileInputStream(this.filename);
			Scanner scnr = new Scanner(f);
			while (scnr.hasNextLine()) {
				String[] data = scnr.nextLine().split(",");
				this.hashMap.put(data[1].charAt(0),
						new Student(data[0], data[1].charAt(0), Double.parseDouble(data[2])));
			} scnr.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
	}
	
	public Student getMaxOfSection(char section) {
		//Method that returns a maximum scoring student's record given the section
		return this.hashMap.get(section);
	}
}