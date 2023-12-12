public class QuickSelect {

    private static int quickSelectKthElement(int[] array, int k) {
        int left = 0;
        int right = array.length - 1;

        for(;;) {
            int pivotIndex = partition(array, left, right);

            if(k == pivotIndex) {
                return array[k];
            } else if(k < pivotIndex) {
                right = pivotIndex - 1;
            } else {
                left = pivotIndex + 1;
            }
        }
    }

    // partition the array using array[right] as a pivot
    // returns the pivot's final index
    // [1 3 4 2] -> [1 3 4 2]
    // [4, 2 1 3] -> [1 2 4 3]
    private static int partition(int[] array, int left, int right) {
        int pivot = array[right];
        int swapIndex = left;
        for (int i = left; i < right; ++i) {
            if (array[i] < pivot) {
                swap(array, i, swapIndex++);
            }
        }
        swap(array, right, swapIndex);
        return swapIndex;
    }

    private static void swap(int[] array, int a, int b) {
        int tmp = array[a];
        array[a] = array[b];
        array[b] = tmp;
    }

    public static void sort(int[] arr) {
        int low = 0, high = arr.length - 1;
        sort(arr, low, high);
    }

    private static void sort(int[] arr, int low, int high) {
        if (low < high) {
            int pivot = partition(arr, low, high);
            sort(arr, low, pivot - 1);
            sort(arr, pivot + 1, high);
        }
    }

    public static void main(String[] args) {
        int[] array = {4,4, 5,-1, 2,3,1};
        //System.out.println(quickSelectKthElement(array, 1));
        for (int i = 0; i < array.length; ++i) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
        sort(array);
        for (int i = 0; i < array.length; ++i) {
            System.out.print(array[i] + " ");
        }
        System.out.println();

    }
}


