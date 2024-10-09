// You can (and should) add "implements Partitioner" below once you have the implementation ready

/* 
 * class for partitioner using a first element pivot.
 * 
 * @author Andrew Onozuka
 */

public class FirstElePivotPartitioner implements Partitioner {

	/* 
	 * partition method so that the values less than
	 * the pivot are before the pivot point and the
	 * values larger are after the pivot. final pivot
	 * index should be returned.
	 */
	
	@Override
	public int partition(String[] strs, int low, int high) {
		// pivotIdx starts at the low idx, hence the name of file.
		int pivotIdx = low;
		String pivot = strs[pivotIdx];
		int smallerBefore = low + 1;
        int largerAfter = high - 1;
        // Loops through to sort in relation to pivot value
        while (smallerBefore <= largerAfter) {
        	if (strs[smallerBefore].compareTo(pivot) < 0) {
        		smallerBefore++;
        	} else {
        		swap(strs, smallerBefore, largerAfter);
        		largerAfter--;
        	}
        }
        swap(strs, largerAfter, pivotIdx);
        return largerAfter;
	}

	/*
	 * swap method that stores one value, swaps two indices,
	 * then finishes the swap using the stored value.
	 */
	
	public void swap(String[] strs, int low, int high) {
		String temp = strs[low];
		strs[low] = strs[high];
		strs[high] = temp;
	}
}
