/******************************************************************
 *
 *   Jenine Irshaid / 002
 *
 *   This java file contains the problem solutions for the methods selectionSort,
 *   mergeSortDivisibleByKFirst, asteroidsDestroyed, and numRescueCanoes methods.
 *
 ********************************************************************/

import java.util.Arrays;

public class ProblemSolutions {

    /**
     * Method SelectionSort
     *
     * This method performs a selection sort. This file will be spot checked,
     * so ENSURE you are performing a Selection Sort!
     *
     * This is an in-place sorting operation that has two function signatures. This
     * allows the second parameter to be optional, and if not provided, defaults to an
     * ascending sort. If the second parameter is provided and is false, a descending
     * sort is performed.
     *
     * @param values        - int[] array to be sorted.
     * @param ascending     - if true, method performs an ascending sort, else descending.
     */

    public void selectionSort(int[] values) {
        selectionSort(values, true);
    }

    public static void selectionSort(int[] values, boolean ascending ) {

        int n = values.length;
        if (n <= 1) return;

        // Classic selection sort
        for (int i = 0; i < n - 1; i++) {
            int selectedIdx = i;

            // Find min (for ascending) or max (for descending) in the unsorted part
            for (int j = i + 1; j < n; j++) {
                if (ascending) {
                    if (values[j] < values[selectedIdx]) {
                        selectedIdx = j;
                    }
                } else {
                    if (values[j] > values[selectedIdx]) {
                        selectedIdx = j;
                    }
                }
            }

            // Swap current position with selected index
            if (selectedIdx != i) {
                int temp = values[i];
                values[i] = values[selectedIdx];
                values[selectedIdx] = temp;
            }
        }

    } // End method selectionSort


    /**
     *  Method mergeSortDivisibleByKFirst
     *
     *  Performs a modified merge sort where all values divisible by k appear first
     *  (in their original relative order), followed by the remaining values in
     *  ascending order.
     */
    public void mergeSortDivisibleByKFirst(int[] values, int k) {

        // Protect against bad input values
        if (k == 0)  return;
        if (values.length <= 1)  return;

        mergeSortDivisibleByKFirst(values, k, 0, values.length-1);
    }

    private void mergeSortDivisibleByKFirst(int[] values, int k, int left, int right) {

        if (left >= right)
            return;

        int mid = left + (right - left) / 2;
        mergeSortDivisibleByKFirst(values, k, left, mid);
        mergeSortDivisibleByKFirst(values, k, mid + 1, right);
        mergeDivisbleByKFirst(values, k, left, mid, right);
    }

    /*
     * The merging portion of the merge sort, divisible by k first.
     *
     * Rule for ordering:
     *  - All numbers divisible by k come first, in their original relative order.
     *  - All other numbers come after, sorted in ascending order.
     */
    private void mergeDivisbleByKFirst(int arr[], int k, int left, int mid, int right)
    {
        int n = right - left + 1;
        int[] temp = new int[n];

        int i = left;
        int j = mid + 1;
        int idx = 0;

        while (i <= mid && j <= right) {
            boolean leftDiv  = (arr[i] % k == 0);
            boolean rightDiv = (arr[j] % k == 0);

            if (leftDiv && !rightDiv) {
                // Left is divisible, right is not → left first
                temp[idx++] = arr[i++];
            } else if (!leftDiv && rightDiv) {
                // Right is divisible, left is not → right first
                temp[idx++] = arr[j++];
            } else if (leftDiv && rightDiv) {
                // Both divisible → preserve original order (take from left)
                temp[idx++] = arr[i++];
            } else {
                // Neither divisible → sort ascending by value
                if (arr[i] <= arr[j]) {
                    temp[idx++] = arr[i++];
                } else {
                    temp[idx++] = arr[j++];
                }
            }
        }

        // Copy remaining elements from left half (if any)
        while (i <= mid) {
            temp[idx++] = arr[i++];
        }

        // Copy remaining elements from right half (if any)
        while (j <= right) {
            temp[idx++] = arr[j++];
        }

        // Copy merged segment back into original array
        for (int t = 0; t < n; t++) {
            arr[left + t] = temp[t];
        }
    }


    /**
     * Method asteroidsDestroyed
     *
     * Return true if it's possible to destroy all asteroids; otherwise false.
     * Strategy: sort asteroids in ascending order and always collide with the
     * smallest remaining asteroid first (greedy).
     */
    public static boolean asteroidsDestroyed(int mass, int[] asteroids) {

        // If there are no asteroids, planet survives trivially
        if (asteroids == null || asteroids.length == 0) {
            return true;
        }

        long planetMass = mass;  // use long to avoid overflow

        Arrays.sort(asteroids);  // smallest asteroid first

        for (int a : asteroids) {
            if (planetMass < a) {
                return false;    // planet destroyed
            }
            planetMass += a;     // asteroid destroyed, planet gains mass
        }

        return true;
    }


    /**
     * Method numRescueSleds
     *
     * Greedy strategy:
     *  - Sort people weights.
     *  - Use two pointers: lightest (i) and heaviest (j).
     *  - If they can share a sled (people[i] + people[j] <= limit), put them together.
     *    Move both pointers.
     *  - Otherwise, put the heaviest alone on a sled (j--).
     *  - Each step uses one sled.
     */
    public static int numRescueSleds(int[] people, int limit) {

        if (people == null || people.length == 0) {
            return 0;
        }

        Arrays.sort(people);
        int i = 0;
        int j = people.length - 1;
        int sleds = 0;

        while (i <= j) {
            if (i == j) {
                // Only one person left
                sleds++;
                break;
            }

            // Try to pair the lightest and heaviest
            if (people[i] + people[j] <= limit) {
                i++;
                j--;
            } else {
                // Heaviest must go alone
                j--;
            }
            sleds++;
        }

        return sleds;
    }

} // End Class ProblemSolutions
