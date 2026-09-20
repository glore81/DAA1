import java.util.Arrays;
import java.util.Random;

public class SelectTester {
    private static final int NUM_TESTS = 100;
    private static final int MAX_ARRAY_SIZE = 1000;
    private static final Random random = new Random();

    public static void main(String[] args){
        boolean allPassed = true;

        for(int i = 1; i <= NUM_TESTS;i++){
            int size = random.nextInt(MAX_ARRAY_SIZE) + 1;
            int[] originalArray = generateRandomArray(size);

            int k = random.nextInt(size);
            int[] testArray = Arrays.copyOf(originalArray, originalArray.length);
            int[] expectedArray = Arrays.copyOf(originalArray, originalArray.length);
            Arrays.sort(expectedArray);
            int expectedK = expectedArray[k];

            DeterministicSelector selector = new DeterministicSelector();
            int actualK = selector.select(originalArray,k);

            if(expectedK != actualK){
                System.out.println("Test #"+i+" Failed");
                System.out.println("Array size:"+ size +", k: "+k);
                System.out.println("Expected value: "+ expectedK);
                System.out.println("Got value: "+ actualK);
                allPassed = false;
                break;
            }
        }
        if(allPassed){
            System.out.println("All " + NUM_TESTS+" test passed successfully");
        }
    }

    private static int[] generateRandomArray(int  size){
        int[] arr = new int[size];
        for(int i = 0; i < size; i++){
            arr[i] = random.nextInt(100000);
        }
        return arr;
    }
}
