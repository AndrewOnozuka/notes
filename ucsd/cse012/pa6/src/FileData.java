/*
 * Class for the FileData object.
 * Used for understanding important details about files including:
 * name, directory it's located in, date last modified.
 * 
 * @author Andrew Onozuka
 */

public class FileData {

	// Instance Variables
    public String name;
    public String dir;
    public String lastModifiedDate;

    /* 
     * Constructor that creates an instance of FileData object and
     * initializes its instance variables.
     * 
     * @param name
     * @param directory
     * @param modifiedDate
     */
    
    public FileData(String name, String directory, String modifiedDate) {
    	this.name = name;
        this.dir = directory;
        this.lastModifiedDate = modifiedDate;
    }

    // toString method, returns a string with instance variables
    public String toString() {
    	return 	"{Name: " + name +
    			", Directory: " + dir +
    			", Modified Date: " + lastModifiedDate + "}";
    }
}
