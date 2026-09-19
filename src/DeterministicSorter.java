public class DeterministicSorter {
    public int select(int[] arr, int k){
        if (arr == null || k < 0 || k >= arr.length){
            throw new IllegalArgumentException("Invalid arrguments");
        }
        int[] copy = arr.clone();
        return select(copy, 0, copy.length-1, k);
    }

    private int select(int[] arr, int low, int high, int k){
        if(low == high){
            return arr[low];
        }

        int pivotValue = medianOfMedians(arr, low, high);
        int pivotIdx = partition(arr, low, high, pivotValue);

        if(k == pivotIdx){
            return arr[k];
        }
        else if(k < pivotIdx){
            return select(arr, low, pivotIdx - 1, k);
        }
        else {
            return select(arr, pivotIdx + 1, high, k);
        }

    }

    private void insertionSort(int[] arr, int left, int right){
        for(int i = left+1; i <= right; i++){
            int key = arr[i];
            int j = i-1;
            while (j >= left){
                if(arr[j] > key){
                    arr[j+1] = arr[j];
                    j--;
                }
                else {
                    break;
                }
            }
            arr[j+1] = key;
        }
    }

    private int medianOfMedians(int[] arr, int low, int high){
        int n = high - low + 1;

        if (n <= 5){
            insertionSort(arr, low, high);
            return arr[low + n / 2];
        }
        int numGroups = (n+4) / 5;
        int[] medians = new int[numGroups];
        for(int i = 0; i < numGroups; i++){
            int groupLow = low + i * 5;
            int groupHigh = Math.min(groupLow + 4, high);
            insertionSort(arr, groupLow, groupHigh);

            medians[i] = arr[groupLow + (groupHigh - groupLow) / 2];
        }
        return select(medians, 0, medians.length - 1, medians.length / 2);
    }

    private int partition(int[] arr, int low, int high, int pivotValue){
        for(int i = low; i <= high; i++){
            if(arr[i] == pivotValue){
                int temp = arr[i];
                arr[i] = arr[high];
                arr[high] = temp;
                break;
            }
        }

        int pivot = arr[high];
        int storeIdx = low;
        for (int j = low;j < high; j++){
            if(arr[j] < pivot){
                swap(arr, storeIdx, j);
                storeIdx++;
            }
        }
        swap(arr, storeIdx, high);
        return storeIdx;
    }

    private void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
