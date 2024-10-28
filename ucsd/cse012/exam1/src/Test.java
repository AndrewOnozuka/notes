import java.util.*;
import java.util.Arrays;
public class Test{
    public static void main(String[] args) {
        int[] test = {4, 2, 5, 7, 2, 4};
        
        System.out.println(Arrays.toString(flipAround(test, 7)));
    }
    // this.arr
    // arr -> elements
    // int - >E
    public static int[] flipAround(int[] arr, int element) {
        boolean eExists = false;
        int position = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == element) {
                eExists = true;
                position = i;
            }
        }
        
        if (eExists == false) {
            throw new NoSuchElementException();
        }
        int[] clone = arr.clone();
        int j = 0;
        for(int i = position + 1; i < clone.length; i++) {
            arr[j] = clone[i];
            j++;
        }
        for(int i = 0; i < position; i++) {
            arr[clone.length - position + i] = clone[i];
        }
        arr[clone.length - position - 1] = clone[position];
        return arr;
    }
    /*
    public void flipAround(E element) {
        boolean eExists = false;
        Node curr = this.front;
        while(curr.next != null) {

        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == element) {
                eExists = true;
                position = i;
            }
        }
        Node wElememt;
        
        if (eExists == false) {
            throw new NoSuchElementException();
        }
    }
    */
}