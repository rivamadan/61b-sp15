/* Radix.java */

package radix;

/**
 * Sorts is a class that contains an implementation of radix sort.
 * @author
 */
public class Sorts {


    /**
     *  Sorts an array of int keys according to the values of <b>one</b>
     *  of the base-16 digits of each key. Returns a <b>NEW</b> array and
     *  does not modify the input array.
     *  
     *  @param key is an array of ints.  Assume no key is negative.
     *  @param whichDigit is a number in 0...7 specifying which base-16 digit
     *    is the sort key. 0 indicates the least significant digit which
     *    7 indicates the most significant digit
     *  @return an array of type int, having the same length as "keys"
     *    and containing the same keys sorted according to the chosen digit.
     **/
    public static int[] countingSort(int[] keys, int whichDigit) {
        int[] digitSort = new int[keys.length];
       	int[] counts = new int[15];
       	counts[0] = 0;
        for (int i = 0; i < keys.length; i++) {
            int digit = keys[i] & (15 << whichDigit * 4);
            counts[digit+1]++;
        }

        for (int i = 0; i < counts.length; i++) {
        	counts[i+1] += counts[i]; 
        }

        for (int i = 0; i < keys.length; i++) {
            int digit = keys[i] & (15 << whichDigit * 4);
            digitSort[counts[digit]] = keys[i];
            counts[digit]++;
        }


        return digitSort;
    }

    /**
     *  radixSort() sorts an array of int keys (using all 32 bits
     *  of each key to determine the ordering). Returns a <b>NEW</b> array
     *  and does not modify the input array
     *  @param key is an array of ints.  Assume no key is negative.
     *  @return an array of type int, having the same length as "keys"
     *    and containing the same keys in sorted order.
     **/
    public static int[] radixSort(int[] keys) {
        int[] sorted = keys;
        for (int i = 7; i >= 0; i--) {
            sorted = countingSort(sorted, i);
        }
        return sorted;
    }

}
