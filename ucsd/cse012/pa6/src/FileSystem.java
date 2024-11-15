// Import necessary packages
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * Class for the FileSystem object.
 * Used for all types of manipulation of data with in the files,
 * from finding to removing files based on names, dates, etc.
 * 
 * @author Andrew Onozuka
 * received help from Benjamin Liang
 */

public class FileSystem {

    MyHashMap<String, ArrayList<FileData>> nameMap;
    MyHashMap<String, ArrayList<FileData>> dateMap;

    /* 
     * Constructor that creates a new FileSystem object and
     * initializes its instance variable.
     */
    
    public FileSystem() {
    	this.nameMap = new MyHashMap<String, ArrayList<FileData>>();
    	this.dateMap = new MyHashMap<String, ArrayList<FileData>>();
    }

    /* 
     * Constructor that creates a new FileSystem object with
     * the given inputFile that contains the file system info.
     */
    
    public FileSystem(String inputFile) {
    	this.nameMap = new MyHashMap<String, ArrayList<FileData>>();
    	this.dateMap = new MyHashMap<String, ArrayList<FileData>>();
        try {
            File f = new File(inputFile);
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
            	String[] data = sc.nextLine().split(", ");
                add(data[0], data[1], data[2]);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }
    
    /* 
     * add method that creates a FileData object with given file info
     * and adds it to instance variables of FileSystem. Variables:
     * fileName, directory, modifiedDate
     * returns true if file successfully added, false if already exists.
     * Default values:
     * filenName = "", dir = "/", modifiedDate = "01/01/2021".
     */
    
    public boolean add(String fileName, String directory, String modifiedDate) {
    	// Checks if anything is null.
    	if (fileName == null) { fileName = ""; }
    	if (directory == null) { directory = "/"; }
    	if (modifiedDate == null) { modifiedDate = "01/01/2021"; }
    	// New FileData
    	FileData data = new FileData(fileName, directory, modifiedDate);
    	// Checks to make sure file not already in name map.
        if (!this.nameMap.containsKey(fileName)) {
            ArrayList<FileData> fileData = new ArrayList<FileData>();
            fileData.add(data);
            this.nameMap.put(fileName, fileData);
        } else {
        	ArrayList<FileData> curr = this.nameMap.get(fileName);
    		for (FileData e : curr) {
    			if (e.name.equals(fileName) && e.dir.equals(directory)) {
    				return false;
    			}
    		}
    		this.nameMap.get(fileName).add(data);
        }
        // Checks to make sure date not already in date map.
        if (!this.dateMap.containsKey(modifiedDate)) {
            ArrayList<FileData> fileData = new ArrayList<FileData>();
            fileData.add(data);
            this.dateMap.put(modifiedDate, fileData);
        } else {
        	ArrayList<FileData> curr = this.dateMap.get(modifiedDate);
        	for (FileData e : curr) {
        		if (e.name.equals(fileName) && e.dir.equals(directory)) {
    				return false;
    			}
        	}
        	this.dateMap.get(modifiedDate).add(data);
        }
        return true;
    }

    /* 
     * findFile method that returns a single FileData object
     * if the file is found. If not, returns null.
     */
    
    public FileData findFile(String name, String directory) {
    	if (!this.nameMap.containsKey(name)) { return null; }
    	ArrayList<FileData> fileData = this.nameMap.get(name);
    	for (FileData e : fileData) {
    		if (e.dir.equals(directory)) {
    			return new FileData(name, directory, e.lastModifiedDate);
    		}
    	}
        return null;
    }

    /* 
     * findAllFilesName method returns a string array list 
     * representing all unique file names across all directories
     * within fileSystem. Returns an empty array if nothing found.
     */
    
    public ArrayList<String> findAllFilesName() {
    	return (ArrayList<String>) this.nameMap.keys();
    }

    /* 
     * findFilesByName method returns a list of FileData
     * with the given name. Returns an empty list if file DNE.
     */
    
    public ArrayList<FileData> findFilesByName(String name) {
    	if (!this.nameMap.containsKey(name) || name == null) {
    		return new ArrayList<FileData>();
    	} return this.nameMap.get(name);
    }
    
    /* 
     * findFilesByDate method returns a list of FileData
     * with the given modifiedDate. Returns empty list if file DNE.
     */

    public ArrayList<FileData> findFilesByDate(String modifiedDate) {
    	if (!this.dateMap.containsKey(modifiedDate) || modifiedDate == null) {
    		return new ArrayList<FileData>();
    	} return this.dateMap.get(modifiedDate);
    }

    /* 
     * findFilesInMultDir method returns a list of FileData
     * with the given modifiedDate that has at least another
     * file with the same name in a different directory.
     * Returns an empty list if such a file DNE.
     */
    
    public ArrayList<FileData> findFilesInMultDir(String modifiedDate) {
    	// Checks to see if modifiedDate not in dateMap.
    	if (!this.dateMap.containsKey(modifiedDate)) {
    		return new ArrayList<FileData>();
    	}
    	ArrayList<FileData> fileData = this.dateMap.get(modifiedDate);
    	// New list of files in multiple directories.
    	ArrayList<FileData> multDir = new ArrayList<FileData>();
    	for (FileData e : fileData) {
    		if (this.nameMap.get(e.name).size() >= 2) {
    			multDir.add(e);
    		}
    	} return multDir;
    }

    /* 
     * removeByName method removes all the files with the
     * given name in the FileSystem. Returns true if files
     * removed successfully, otherwise returns false.
     */
    
    public boolean removeByName(String name) {
    	// Checks to see if name not in directory
    	if (!this.nameMap.containsKey(name)) { return false; }
    	boolean deleted = false;
    	// Loops to see if there is a file with given name to remove
    	for (String e : this.dateMap.keys()) {
    		for (int i = 0; i < this.dateMap.get(e).size(); i++) {
    			if (this.dateMap.get(e).get(i).name.equals(name)) {
    				this.dateMap.get(e).remove(i);
    				if (this.dateMap.get(e).isEmpty()) {
    					this.dateMap.replace(e, null);
    					break;
    				} i--;
    			}
    		}
    	}
    	deleted = true;
    	return this.nameMap.remove(name) && deleted;
    }

    /* 
     * removeFile method removes a file with the given name
     * and directory. Returns true if success, otherwise false.
     */
    
    public boolean removeFile(String name, String directory) {
    	// Checks to see if name not in directory
        if(!this.nameMap.containsKey(name)) { return false; }
        boolean found = false;
        String date = ""; // default value as covered in add()
        for (FileData data : this.nameMap.get(name)) {
            if (data.dir.equals(directory)) {
                date = data.lastModifiedDate;
                found = true;
            }
        }
        // If not found, return false as the file cannot be removed.
        if (!found) { return false; }
        // Loops to find given name and date in dateMap.
        ArrayList<FileData> dates = this.dateMap.get(date);
        for (int i = 0; i < dates.size(); i++) {
            if (dates.get(i).dir.equals(directory) && dates.get(i).name.equals(name)) {
                dates.remove(i);
                if (dates.isEmpty()) {
                    this.dateMap.remove(date);
                    break;
                }
            }
        }
        // Loops to find which file to remove in nameMap using name and dir.
        ArrayList<FileData> fileData = this.nameMap.get(name);
        for (int i = 0; i < fileData.size(); i++) {
            if (fileData.get(i).dir.equals(directory)) {
            	fileData.remove(i);
                if (fileData.isEmpty()) { 
                    this.nameMap.remove(name);
                }
            }
        }
        return true;
    }
}
