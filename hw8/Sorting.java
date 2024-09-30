import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Abdullojon Yusupov
 * @userid abdullojony
 * @version 1.0
 */
public class Sorting {

    /**
     * Swap two elements in an array
     *
     * @param arr the array that contains items to swap
     * @param a first item
     * @param b second item
     * @param <T>  data type to sort
     */
    private static <T> void swap(T[] arr, int a, int b) {
        T tmp = arr[b];
        arr[b] = arr[a];
        arr[a] = tmp;
    }

    /**
     * Implement insertion sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) throw new IllegalArgumentException();
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (comparator.compare(arr[j - 1], arr[j]) > 0) swap(arr, j, j - 1);
                else break;
            }
        }
    }

    /**
     * Implement selection sort.
     *
     * It should be:
     *  in-place
     *  unstable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n^2)
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) throw new IllegalArgumentException();
        for (int i = arr.length - 1; i > 0; i--) {
            int maxIndex = i;
            for (int j = 0; j < i; j++) {
                if (comparator.compare(arr[j], arr[maxIndex]) > 0) maxIndex = j;
            }
            if (maxIndex != i) swap(arr, maxIndex, i);
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     *  out-of-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n log n)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You can create more arrays to run mergesort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) throw new IllegalArgumentException();

        // base case
        if (arr.length < 2) return;

        // dividing
        int l = arr.length;
        int mid = l / 2;
        T[] leftArr = (T[]) new Object[mid];
        T[] rightArr = (T[]) new Object[l - mid];
        for (int i = 0; i < l; i++) {
            if (i < mid) leftArr[i] = arr[i];
            else rightArr[i - mid] = arr[i];
        }
        mergeSort(leftArr, comparator);
        mergeSort(rightArr, comparator);

        // merging
        merge(arr, comparator, leftArr, rightArr);
    }

    /**
     * Merges separated arrays
     *
     * @param arr the array to be sorted
     * @param comparator comparator used to compare values
     * @param leftArr left array
     * @param rightArr right Array
     * @param <T> data type to sort
     */
    private static <T> void merge(T[] arr, Comparator<T> comparator,
                                  T[] leftArr, T[] rightArr) {
        int i = 0;
        int j = 0;
        while (i + j < arr.length) {
            if (j == rightArr.length || 
            (i < leftArr.length && comparator.compare(leftArr[i], rightArr[j]) <= 0)) {
                arr[i + j] = leftArr[i++];
            } else {
                arr[i + j] = rightArr[j++];
            }
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     *  in-place
     *  unstable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @throws IllegalArgumentException if the array or comparator or rand is
     * null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException();
        }
        
        qsHelper(arr, 0, arr.length - 1, rand, comparator);
    }

    /**
     * Helper method for quick sort
     *
     * @param arr the array to be sorted
     * @param first starting index
     * @param last ending index
     * @param rand the Random object used to select pivots
     * @param comparator the Comparator used to compare the data in arr
     * @param <T> data type to sort
     */
    private static <T> void qsHelper(T[] arr, int first, int last,
                                     Random rand, Comparator<T> comparator) {
        // base case
        if (first >= last) return;

        int pIndex = rand.nextInt(last - first + 1) + first;
        T pivot = arr[pIndex];
        swap(arr, first, pIndex);

        int i = first + 1;
        int j = last;
        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], pivot) <= 0) {
                i++;
            }
            while (i <= j && comparator.compare(pivot, arr[j]) <= 0) {
                j--;
            }
            if (i <= j) {
                swap(arr, i++, j--);
            }
        }
        swap(arr, first, j);
        qsHelper(arr, first, j - 1, rand, comparator);
        qsHelper(arr, j + 1, last, rand, comparator);
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     *  out-of-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) throw new IllegalArgumentException();
        if (arr.length < 2) return;

        // create array of 20 (10 for positive, 10 for negatives) buckets (queues)
        Queue<Integer>[] buckets = new Queue[20];
        int l = arr.length;

        // find the longest number
        int max = Math.abs(arr[0]);
        for (int i = 1; i < l; i++) {
            if (Math.abs(arr[i]) > max) max = Math.abs(arr[i]);
        }

        // calculate number of iterations to make
        int iterations = 1;
        while (max / 10 != 0) {
            iterations++;
            max /= 10;
        }

        // number for dividing
        int div = 1;

        // start iterating
        for (int i = 1; i <= iterations; i++) {
            // loop over the array
            for (int j = 0; j < l; j++) {
                // find the i'th significant digit
                int digit = (arr[j] / div) % 10;

                // add 10 to handle negatives
                digit = digit + 10;

                // put numbers in buckets
                if (buckets[digit] == null) {
                    buckets[digit] = new LinkedList<>();
                }
                buckets[digit].add(arr[j]);
            }

            int j = 0;
            // sort numbers according by removing from buckets
            for (Queue<Integer> q : buckets) {
                if (q == null || q.isEmpty()) continue;
                while(!q.isEmpty()) {
                    arr[j++] = q.remove();
                }
            }
            
            div *= 10;
        }
    }
}