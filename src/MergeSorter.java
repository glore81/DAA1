public class MergeSorter {
    private long comparisons = 0;
    private int maxDepth = 0;
    private int currentDepth = 0;


    public void sort(int[] arr) {

        if (arr == null || arr.length <= 1) return;

        comparisons = 0;
        maxDepth = 0;
        currentDepth = 0;

        int[] aux = new int[arr.length];
        mergeSort(arr,aux, 0, arr.length-1);
    }

    private void mergeSort(int[] arr, int[] aux, int left, int right) {

        currentDepth++;
        if(currentDepth > maxDepth) {
            maxDepth = currentDepth;
        }

        if(right - left + 1 <= 16){
            insertionSort(arr,left,right);
            currentDepth--;
            return;
        }

        int mid = left + (right - left) / 2;

        mergeSort(arr,aux, left, mid);
        mergeSort(arr, aux, mid+1, right);

        merge(arr, aux, left, mid, right);

        currentDepth--;
    }


    private void merge(int[] arr, int[] aux, int left, int mid, int right) {
        int i = left;
        int j = mid+1;
        int k = left;

        while(i <= mid && j <= right){
            comparisons++;
            if(arr[i] <= arr[j]){
                aux[k++] = arr[i++];
            } else {
                aux[k++] = arr[j++];
            }
        }

        while(i <= mid){
            aux[k++] = arr[i++];
        }

        while(j <= right){
            aux[k++] = arr[j++];
        }

        for(int idx = left; idx <= right; idx++){
            arr[idx] = aux[idx];
        }
    }

    private void insertionSort(int[] arr, int left, int right){
        for(int i = left + 1;i <= right; i++){
            int key =  arr[i];
            int j = i-1;
            while(j >= left){
                comparisons++;
                if(arr[j] > key) {
                    arr[j + 1] = arr[j];
                    j--;
                } else {
                    break;
                }
            }

            arr[j+1] = key;
        }
    }

    public long getComparisons(){return comparisons;}
    public int getMaxDepth() {return maxDepth;}

    @Override
    public String toString(){
        return "MergeSort Metrics:\nComparisons = "+comparisons+"\nMaxDepth = "+maxDepth;
    }
}
