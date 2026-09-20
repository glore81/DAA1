import java.util.Arrays;
import java.util.Random;


public class SortingTester {
    private static final int ARR_SIZE = 2000;
    private static final Random random = new Random();

    public static void main(String[] args){
        System.out.println("\nMerge sort:");
        runAllTests("MergeSort");
        System.out.println("\nQuick sort:");
        runAllTests("QuickSort");
    }
    private static void runAllTests(String algorithm){
        test(algorithm, "Random Array",generateRandomArray(ARR_SIZE));
        test(algorithm, "Sorted Array",generateSortedArray( ARR_SIZE));
        test(algorithm, "Reverse Sorted Array",generateReverseArray(ARR_SIZE));
        test(algorithm, "Duplicate Array",generateDuplicateArray(ARR_SIZE));
        test(algorithm, "Empty Array", new int[]{});
        test(algorithm, "Single Element Array", new int[]{4});
    }

    private static void test(String algorithm, String testName, int[] originalArray){
        int[] testArray = Arrays.copyOf(originalArray, originalArray.length);
        int[] expectedArray = Arrays.copyOf(originalArray, originalArray.length);

        Arrays.sort(expectedArray);

        if(algorithm.equals("MergeSort")){
            MergeSorter mergeSorter = new MergeSorter();
            mergeSorter.sort(testArray);
        } else if (algorithm.equals("QuickSort")) {
            QuickSorter quickSorter = new QuickSorter();
            quickSorter.sort(testArray);
        }

        if (Arrays.equals(testArray, expectedArray)){
            System.out.println(testName + ": Completed");
        } else {
            System.out.println(testName + ": Failed, array sorted incorrectly");
        }

    }

    private static int[] generateRandomArray(int size){
        int[] arr = new int[size];
        for(int i = 0; i < size; i++){
            arr[i] = random.nextInt(10000000);
        }
        return arr;
    }

    private static int[] generateSortedArray(int size){
        int[] arr = new int[size];
        for(int i = 0; i < size; i++){
            arr[i] = i;
        }
        return arr;
    }

    private static int[] generateReverseArray(int size){
        int[] arr = new int[size];
        for(int i = 0; i < size; i++){
            arr[i] = size-i;
        }
        return arr;
    }

    private static int[] generateDuplicateArray(int size){
        int[] arr = new int[size];
        for(int i = 0; i < size; i++){
            arr[i] = random.nextInt(10);
        }
        return arr;
    }

}
