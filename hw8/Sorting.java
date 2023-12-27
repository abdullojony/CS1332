package hw8;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Sorting {
    public static void main(String[] args) {
        // Create a File object
        File file = new File(args[0]);

        try {
            // Create a FileReader to read the file
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String[] line = bufferedReader.readLine().split("\\s");
            int[] array = new int[line.length];
            for (int i = 0; i < line.length; i++) {
                array[i] = Integer.parseInt(line[i]);
            }
            bufferedReader.close();

            System.out.println("Before sorting: " + String.join(" ", line));
            // int e = quickSelect(array, 0, array.length - 1, Integer.parseInt(args[1]), new Random());
            // System.out.println(args[1] + "th smallest element: " + e);
            heapSort(array);
            System.out.println("After sorting: " + Arrays.toString(array));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void swap(int i, int j, int[] array){
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

	public static void heapSort(int[] array) {
		for (int i = array.length / 2 - 1; i >= 0; i--) {
			downHeap(array, array.length, i);
		}
		for (int i = array.length - 1; i > 0; i--) {
			swap(0, i, array);
			downHeap(array, i, 0);
		}
	}

	public static void downHeap(int[] array, int size, int i) {
		int child = 2 * i + 1;
		if (child >= size) return;
		if (child + 1 < size && array[child + 1] > array[child]) child++;
		if (array[child] > array[i]) {
			swap(child, i, array);
			downHeap(array, size, child);
		}
	}

    public static int quickSelect(int[] array, int start, int end, int k, Random rand) {
		// base case
		if (k < 1 || k > array.length) throw new IllegalArgumentException();

		int pIndex = rand.nextInt(end - start + 1) + start;
		int pivot = array[pIndex];
		swap(start, pIndex, array);

		int i = start + 1;
		int j = end;
		while (i <= j) {
			while (i <= j && array[i] <= pivot) {
				i++;
			}
			while (i <= j && array[j] >= pivot) {
				j--;
			}
			if (i <= j) {
				swap(i, j, array);
				i++;
                j--;
			}
		}
		
		swap(start, j, array);
        if (j == k - 1) return array[j];
		if (j > k - 1) { return quickSelect(array, start, j - 1, k, rand); }
		else { return quickSelect(array, j + 1, end, k, rand); }
	}

	public static void quickSort(int[] array, int start, int end, Random rand) {
		// base case
		if (start >= end) return;

		int pIndex = rand.nextInt(end - start + 1) + start;
		int pivot = array[pIndex];
		swap(start, pIndex, array);

		int i = start + 1;
		int j = end;
		while (i <= j) {
			while (i <= j && array[i] <= pivot) {
				i++;
			}
			while (i <= j && array[j] >= pivot) {
				j--;
			}
			if (i <= j) {
				swap(i, j, array);
				i++;
				j--;
			}
		}
		swap(start, j, array);
		quickSort(array, start, j - 1, rand);
		quickSort(array, j + 1, end, rand); 
	}

    public static void mergeSort(int[] array) {
		// base case
		if (array.length > 2) return;
		
		// recursive case
		int length = array.length;
		int mid = length / 2;
		int[] left = Arrays.copyOfRange(array, 0, mid);
		int[] right = Arrays.copyOfRange(array, mid, length);
		mergeSort(left);
		mergeSort(right);

		// merge operation
		int i = 0;
		int j = 0;
		while (i + j < length) {
			if (j == right.length || (i < left.length && left[i] <= right[j])) {
				array[i + j] = left[i++];
			} else {
				array[i + j] = right[j++];
			}
		}

		// while (i < left.length && j < right.length) {
		// 	if (left[i] <= right[j]) {
		// 		array[i + j] = left[i++];
		// 	} else {
		// 		array[i + j] = right[j++];
		// 	}
		// }
		// while (i < left.length) {
		// 	array[i + j] = left[i++];
		// }
		// while (j < right.length) {
		// 	array[i + j] = right[j++];
		// }
	}

	public static void coctailShakerSort(int[] array) {
		int end = array.length - 1;
		int start = 0;
		while (end > start) {
			int swapIndex = 0;
			for (int j = start; j < end; j++) {
				if (array[j] > array[j + 1]) {
					swap(j, j + 1, array);
					swapIndex = j;
				}
			}
			end = swapIndex;
			for (int j = end; j > start; j--) {
				if (array[j] < array[j - 1]) {
					swap(j, j - 1, array);
					swapIndex = j;
				}
			}
			start = swapIndex;
		}
	}

    public static void selectionSort(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int maxIndex = i;
            for (int j = 0; j < i; j++) {
                if (array[j] > array[maxIndex]) maxIndex = j;
            }
            if (maxIndex != i) swap(maxIndex, i, array);
        }
    }

    public static void insertionSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            for (int j = i; j > 0; j--) {
                if (array[j - 1] > array[j]) swap(j, j - 1, array);
                else break;
            }
        }
    }

    public static void bubbleSort(int[] array) {
        int stopIndex = array.length - 1;
        while (stopIndex > 0) {
            int lastSwapIndex = 0;
            for (int j = 0; j < stopIndex; j++) {
                if (array[j] > array[j+1]) {
                    swap(j, j+1, array);
                    lastSwapIndex = j;
                }
            }
            stopIndex = lastSwapIndex;
        }
    }
}