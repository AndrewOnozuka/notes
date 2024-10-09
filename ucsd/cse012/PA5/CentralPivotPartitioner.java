// You can (and should) add "implements Partitioner" below once you have the implementation ready

/* 
 * class for partitioner using a central pivot.
 * 
 * @author Andrew Onozuka
 */

public class CentralPivotPartitioner implements Partitioner {

	/* 
	 * partition method so that the values less than
	 * the pivot are before the pivot point and the
	 * values larger are after the pivot. final pivot
	 * index should be returned.
	 */
	
	@Override
	public int partition(String[] strs, int low, int high) {
		// pivots using center, hence the name
		int centerIdx = (high+low) / 2;
		int pivotIdx = high - 1;
		swap(strs, centerIdx, pivotIdx);
		String pivot = strs[pivotIdx];
		int smallerBefore = low;
		int largerAfter = high - 2;
		// Loops both from the front and back as needed
		while (smallerBefore < largerAfter) {
			if (strs[smallerBefore].compareTo(pivot) < 0) {
				smallerBefore ++;
			} else {
				swap(strs, smallerBefore, largerAfter);
				largerAfter --;
			}
		}
		if (strs[smallerBefore].compareTo(pivot) < 0) {
			swap(strs, smallerBefore + 1, pivotIdx);
			return smallerBefore + 1;
		} else {
			swap(strs, smallerBefore, pivotIdx);
			return smallerBefore;
		}
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
