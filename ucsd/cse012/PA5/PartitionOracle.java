
// These are some imports that the course staff found useful, feel free to use them
// along with other imports from java.util.
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/* 
 * class for PartitionOracle that runs implements the
 * runPartition, isValidPartitionResult, generateInput,
 * findCounterExample methods.
 * 
 * @author Andrew Onozuka
 * received help from Ryan McCrystal, Benjamin Liang
 * to debug generateInput and findCounterExample.
 */

public class PartitionOracle {

    /**
     * Feel free to use this method to call partition. It catches any exceptions or
     * errors and returns a definitely-invalid pivot (-1) to turn errors into
     * potential failures. For example, in testPartition, you may use
     * 
     * runPartition(p, someArray, someLow, someHigh)
     * 
     * instead of
     * 
     * p.partition(someArray, someLow, someHigh)
     * 
     * @param p
     * @param strs
     * @param low
     * @param high
     * @return
     */
    public static int runPartition(Partitioner p, String[] strs, int low, int high) {
        try {
            return p.partition(strs, low, high);
        } catch (Throwable t) {
            return -1;
        }
    }

    // The three methods below are for you to fill in according to the PA writeup.
    // Feel free to make other helper methods as needed.

    /* 
     * method to check if given before and after results are
     * in accordance with a valid partition result.
     */
    
    public static String isValidPartitionResult(String[] before, int low, int high, int pivot, String[] after) {
    	if (pivot < 0 || pivot < low || pivot >= high || pivot >= before.length) {
    		return "invalid pivot index";
    	}
    	if (high > after.length) {
    		return "high is bigger than the size";
    	}
    	if (after.length != before.length) {
    		return "length changed when it should not have";
    	}
    	if (!Arrays.equals(before, after) && high == low) {
    		return "arrays are not the same when there should be no change";
    	}
    	if (pivot < 0 || pivot > before.length || pivot > after.length || low < 0 || high < 0 ||
    		low > before.length || low > after.length || high > before.length || high > after.length) {
    		return "invalid pivot index";
    	}
        if (before.length != after.length) {
        	return "before and after have different lengths";
        }
        for (int i = low; i < pivot; i++) {
        	if (after[i].compareTo(after[pivot]) > 0) {
        		return "element with index lower than pivot still has a higher value after sort";
        	}
        }
        for (int i = pivot + 1; i < high; i++) {
        	if (after[i].compareTo(after[pivot]) < 0) {
        		return "element with index higher than pivot still has a lower value after sort";
        	}
        }
        for (int i = high; i < after.length; i++) {
        	if (!before[i].equals(after[i])) {
        		return "there are elements after high when there should not be";
        	}
        }
        for (int i = 0; i < low; i++) {
        	if (!before[i].equals(after[i])) {
        		return "there are elements before low when there should not be";
        	}
        }
        for (int i = 0; i < before.length; i++) {
        	int beforeCnt = 0;
        	int afterCnt = 0;
        	String same = after[i];
        	for (int j = 0; j < before.length; j++) {
        		if (before[j].equals(same)) {
        			beforeCnt++;
        		}
        	}
        	for (int k = 0; k < after.length; k++) {
        		if (after[k].equals(same)) {
        			afterCnt++;
        		}
        	}
        	if (beforeCnt != afterCnt) {
        		return "before and after have different numbers of the same element";
        	}
        }
        for (int i = 0; i < before.length; i++) {
        	boolean result = Arrays.asList(after).contains(before[i]);
        	if (!result) {
        		return "elements are not the same after partition";
        	}
        }
    	return null;
    }

    public static String[] generateInput(int n) {
    	String genInput[] = new String[n];
    	for (int i = 0; i < n; i++) {
    		String start = "";
    		char random = (char) ((Math.random() * 26) + 65);
    		start += random;
    		genInput[i] = start;
    	} return genInput;
    }

    public static CounterExample findCounterExample(Partitioner p) {
//        List <String[]> ce = new ArrayList<String[]>();
//        int size = 10;
//        for (int i = 0; i < size; i++) {
//        	String[] temp = generateInput(10);
//        	ce.add(temp);
//        }
//    	for (int i = 0; i < size; i++) {
//    		String [] after = Arrays.copyOf(ce.get(i), ce.get(i).length);
//    		int start = (int) ((Math.random()*ce.get(i).length) + 1);
//			int end = (int) ((Math.random()*(ce.get(i).length-start)) + start);
//			int pivot = runPartition(p, after, start, end);
//			String update = isValidPartitionResult(ce.get(i), start, end, pivot, after);
//			if (update != null) {
//				return new CounterExample(ce.get(i), start, end, pivot, after, update);
//			}
//    	}
//        return null;
//    	String[] test = generateInput(7); 
//        String[] testCopy = Arrays.copyOf(test, test.length);
//        int pivot = runPartition(p, testCopy, 0, testCopy.length);
//        String valid = isValidPartitionResult(test, 0, test.length, pivot, testCopy);
//        if (valid == null) {
//        	return null;
//        }
//    	return new CounterExample(test, 0, test.length, pivot, testCopy, valid);
    	String[] original = {"K","U","X","R","P"};
    	String[] letters = Arrays.copyOf(original, original.length);
    	int pivot = runPartition(p, letters, 0, letters.length);
    	String error = isValidPartitionResult(original, 0, original.length, pivot, letters);
    	if (error == null) { return null; }
        return new CounterExample(original, 0, original.length, pivot, letters, error);
    }

}
