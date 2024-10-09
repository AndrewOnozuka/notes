/*
 * @author https://www.geeksforgeeks.org/quick-sort/
 * Although I could not find a license, https://www.geeksforgeeks.org/copyright-information/ says:
 * "You are free to:
 * Share — copy and redistribute the material in any medium or format
 * Adapt — remix, transform, and build upon the material for any purpose."
 * I changed it from int to str.
 * It passes tests.
 * The worst case runtime has a tight big-O bound of n since it goes through the for-loop n times.
 * This is the most dominant term so it is the big-O bound.
 */

// You can (and should) add "implements Partitioner" below once you have the implementation ready
public class WebPartitioner implements Partitioner {

	@Override
	public int partition(String[] strs, int low, int high) {
	    // pivot
	    String pivot = strs[high];
	     
	    // Index of smaller element and
	    // indicates the right position
	    // of pivot found so far
	    int i = (low - 1);
	 
	    for(int j = low; j <= high - 1; j++)
	    {
	         
	        // If current element is smaller
	        // than the pivot
	        if (strs[j].compareTo(pivot) < 0)
	        {
	             
	            // Increment index of
	            // smaller element
	            i++;
	            swap(strs, i, j);
	        }
	    }
	    swap(strs, i + 1, high);
	    return (i + 1);
	}

	// swap helper method (also implemented from geeks for geeks)
	private void swap(String[] strs, int i, int j) {
		// TODO Auto-generated method stub
		String temp = strs[i];
	    strs[i] = strs[j];
	    strs[j] = temp;
	}	
}
