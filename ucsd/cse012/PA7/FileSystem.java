// Import packages
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Comparator;
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

    BST<String, FileData> nameTree;
    BST<String, ArrayList<FileData>> dateTree;
    
    /* 
     * Constructor that creates a new FileSystem object and
     * initializes its instance variable.
     */
    
    public FileSystem() {
    	nameTree = new BST<String, FileData>();
    	dateTree = new BST<String, ArrayList<FileData>>();
    }

    /* 
     * Constructor that creates a new FileSystem object with
     * the given inputFile that contains the file system info.
     */
    
    public FileSystem(String inputFile) {
    	nameTree = new BST<String, FileData>();
    	dateTree = new BST<String, ArrayList<FileData>>();
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
     * name, dir, date
     */
    
    public void add(String name, String dir, String date) {
    	// Checks if anything is null.
    	if (name == null || dir == null || date == null) { return; }
    	// Stores replaced file data.
    	FileData replaced = new FileData(name, dir, date);
    	// Loops through if nameTree contains name and date
    	if (this.nameTree.containsKey(name) &&
    		this.nameTree.get(name).lastModifiedDate.compareTo(date) < 0) {
    		// Stores old date.
    		String exDate = this.nameTree.get(name).lastModifiedDate;
    		this.nameTree.set(name, replaced);
    		for (int i = 0; i < this.dateTree.get(exDate).size(); i++) {
    			if (this.dateTree.get(exDate).get(i).name.equals(name)) {
    				this.dateTree.get(exDate).remove(i);
    				i--;
    			}
    		}
    		if (this.dateTree.containsKey(date)) {
        		this.dateTree.get(date).add(replaced);
        	} else {
        		ArrayList<FileData> fileData = new ArrayList<FileData>();
        		fileData.add(replaced);
        		this.dateTree.set(date, fileData);
        	}
    	// Loops through if nameTree does not already contain the file name
    	} else if (!this.nameTree.containsKey(name)) {
    		this.nameTree.set(name, replaced);
    		if (!this.dateTree.containsKey(date)) {
    			ArrayList<FileData> fileData = new ArrayList<FileData>();
    			fileData.add(replaced);
        		this.dateTree.set(date, fileData);
    		} else {
    			ArrayList<FileData> fileData = this.dateTree.get(date);
    			fileData.add(replaced);
        		this.dateTree.set(date, fileData);
    		}
    	}
    }

    /* 
     * findFileNamesByDate method returns a string array list 
     * representing all unique file names given a certain date.
     */
    
    public ArrayList<String> findFileNamesByDate(String date) {
    	if (date == null) { return null; }
    	// file names
    	ArrayList<String> names = new ArrayList<String>();
    	// loops through for all file names with given date
    	for (FileData data : this.dateTree.get(date)) {
    		names.add(data.name);
    	} return names;
    }

    /* 
     * filter method returns a file system of all the items
     * within the filter of startDate and endDate.
     */
    
    public FileSystem filter(String startDate, String endDate) {
    	// filter
    	FileSystem filter = new FileSystem();
    	// loops through searching for items in between start and end dates
    	for (String dates : this.dateTree.keys()) {
    		if (dates.compareTo(startDate) >= 0 && dates.compareTo(endDate) < 0) {
    			for (FileData fileData : this.dateTree.get(dates)) {
    				filter.add(fileData.name, fileData.dir, fileData.lastModifiedDate);
    			}
    		}
    	} return filter;
    }
    
    /* 
     * filter method returns a file system of all the items
     * within the filter of wildCard.
     */
    
    public FileSystem filter(String wildCard) {
    	// filter
    	FileSystem filter = new FileSystem();
    	// loops through searching for wildCard in nameTree
    	for (String name : this.nameTree.keys()) {
    		if (name.contains(wildCard)) {
    			FileData data = this.nameTree.get(name);
    			filter.add(data.name, data.dir, data.lastModifiedDate);
    		}
    	}
    	// loops through searching for wildCard in dateTree
    	for (String date : this.dateTree.keys()) {
    		if (date.contains(wildCard)) {
    			for (FileData data : this.dateTree.get(date)) {
    				filter.add(data.name, data.dir, data.lastModifiedDate);
    			}
    		}
    	} return filter;
    }
    
    /* 
     * outputNameTree method returns a string array list 
     * for all items in nameTree.
     */
    
    public List<String> outputNameTree(){
    	// output
    	ArrayList<String> output = new ArrayList<String>();
    	// loops through for all outputs
    	for (String name : this.nameTree.keys()) {
    		output.add(name + ": " + this.nameTree.get(name).toString());
    	} return output;
    }
    
    /* 
     * outputDateTree method returns a string array list 
     * for all items in dateTree.
     */
    
    public List<String> outputDateTree(){
    	ArrayList<String> output = new ArrayList<String>();
    	// keys
    	List<String> keys = this.dateTree.keys();
    	// loops through for all keys
    	for (int i = keys.size() - 1; i >= 0; i--) {
    		ArrayList<FileData> fileData = this.dateTree.get(keys.get(i));
    		for (int j = fileData.size() - 1; j >= 0; j--) {
    			output.add(keys.get(i) + ": " + fileData.get(j).toString());
    		}
    	} return output;
    }
}

