import java.util.Random;

public class QuickSorter {
    private long comparisons = 0;
    private int currentDepth = 0;
    private int maxDepth = 0;
    private final Random random = new Random();

    public void sort(int[] arr){
        if(arr == null || arr.length <= 1) return;

        comparisons = 0;
        currentDepth = 0;
        maxDepth = 0;

        quickSort(arr, 0, arr.length-1);
    }

    private void quickSort(int[] arr,int low, int high){
        while (low < high){
            int p = randomizedPartition(arr, low ,high);
            if(p - low < high - p){
                currentDepth++;
                if(currentDepth > maxDepth){
                    maxDepth = currentDepth;
                }
                quickSort(arr, low, p-1);
                currentDepth--;

                low = p + 1;
            }

            else{
                currentDepth++;
                if(currentDepth > maxDepth){
                    maxDepth = currentDepth;
                }
                quickSort(arr, p+1, high);
                currentDepth--;

                high = p - 1;
            }
        }
    }


    private int randomizedPartition(int[] arr, int low, int high){
        int pivotIdx = low + random.nextInt(high-low+1);
        swap(arr, pivotIdx, high);
        return partition(arr, low, high);
    }

    private int partition(int[] arr, int low, int high){
        int pivot = arr[high];
        int i = low - 1;

        for(int j = low; j < high; j++){
            comparisons++;
            if(arr[j] <= pivot){
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i+1, high);
        return i + 1;
    }

    private void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public long getComparisons(){ return comparisons;}
    public int getMaxDepth(){ return maxDepth;}

    @Override
    public String toString(){
        return "QuickSort Metrics:\nComparisons = "+comparisons+"\nMaxDepth = "+maxDepth;
    }
}
