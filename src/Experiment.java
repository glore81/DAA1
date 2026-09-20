import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

public class Experiment {
    private static final String CSV = "results/results.csv";
    private static final Random random = new Random();

    private static final int[] SIZES = {100, 500, 1000, 5000, 10000, 50000, 100000};
    private static final int RUNS_PER_SIZE = 5;

    public static void main(String[] args){
        System.out.println("Starting performance experiments...");

        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV))){
            writer.println("Algorithm, InputType, Size, ExecutionTimeNs, ExecutionTimeMs, Comparisons, MaxDepth");

            runSortingExperiments(writer);
            runSelectExperiments(writer);
            runClosestPairExperiments(writer);

            System.out.println("Experiments finished! Results saved to "+CSV);
        } catch (IOException e) {
            System.out.println("Error writing to CSV: " + e.getMessage());
        }
    }

    private static void runSortingExperiments(PrintWriter writer){
        String[] inputTypes =  {"Random", "Sorted", "ReverseSorted", "Duplicates"};
        for(int size : SIZES){
            for(String inputType : inputTypes){
                long totalMergeTime = 0, totalMergeComp = 0, totalMergeDepth = 0;
                long totalQuickTime = 0, totalQuickComp = 0, totalQuickDepth = 0;

                for(int i = 0; i < RUNS_PER_SIZE; i++){
                    int[] originalArray = generateArray(size,inputType);

                    int[] mergeArray = Arrays.copyOf(originalArray, originalArray.length);
                    MergeSorter mergeSorter = new MergeSorter();

                    long start = System.nanoTime();
                    mergeSorter.sort(mergeArray);
                    long end = System.nanoTime();

                    totalMergeTime += (end-start);
                    totalMergeComp += mergeSorter.getComparisons();
                    totalMergeDepth += mergeSorter.getMaxDepth();

                    int[] quickArray = Arrays.copyOf(originalArray, originalArray.length);
                    QuickSorter quickSorter = new QuickSorter();

                    start = System.nanoTime();
                    quickSorter.sort(quickArray);
                    end = System.nanoTime();
                    totalQuickTime += (end-start);
                    totalQuickComp += quickSorter.getComparisons();
                    totalQuickDepth += quickSorter.getMaxDepth();
                }
                long avgMergeTime = totalMergeTime / RUNS_PER_SIZE;
                long avgMergeComp = totalMergeComp /  RUNS_PER_SIZE;
                long avgMergeDepth = totalMergeDepth / RUNS_PER_SIZE;
                long avgQuickTime = totalQuickTime / RUNS_PER_SIZE;
                long avgQuickComp = totalQuickComp /  RUNS_PER_SIZE;
                long avgQuickDepth = totalQuickDepth / RUNS_PER_SIZE;

                writer.printf(Locale.US, "MergeSort, %s, %d, %d, %.4f, %d, %d%n",
                        inputType,size,avgMergeTime, avgMergeTime/1e6,avgMergeComp, avgMergeDepth);
                writer.printf(Locale.US, "QuickSort, %s, %d, %d, %.4f, %d, %d%n",
                        inputType,size,avgQuickTime, avgQuickTime/1e6,avgQuickComp, avgQuickDepth);
            }
        }
    }

    private static void runSelectExperiments(PrintWriter writer){
        for(int size: SIZES){
            long totalTime = 0;
            for(int i = 0; i < RUNS_PER_SIZE; i++){
                int[] arr = generateArray(size, "Random");
                int k = random.nextInt(size);

                DeterministicSelector selector = new DeterministicSelector();
                long start = System.nanoTime();
                selector.select(arr, k);
                long end = System.nanoTime();

                totalTime += (end-start);
            }
            long avgTime = totalTime / RUNS_PER_SIZE;
            writer.printf(Locale.US, "DeterministicSelector, Random, %d, %d, %.4f, 0, 0%n", size, avgTime, avgTime / 1e6);
        }
    }

    private static void runClosestPairExperiments(PrintWriter writer){
        int[] pointSizes = {100, 500, 1000, 5000, 10000};

        for(int size: pointSizes){
            long totalTime = 0;
            for(int i = 0; i < RUNS_PER_SIZE; i++){
                Point[] points = generatePoints(size);

                ClosestPairSolver solver = new ClosestPairSolver();
                long start = System.nanoTime();
                solver.findClosestPair(points);
                long end = System.nanoTime();
                totalTime += (end-start);
        }
            long avgTime = totalTime / RUNS_PER_SIZE;
            writer.printf(Locale.US, "ClosestPair, RandomPoints, %d, %d, %.4f, 0, 0%n", size, avgTime, avgTime / 1e6);
        }
    }

    private static int[] generateArray(int size, String type){
        int[] arr = new int[size];

        switch(type){
            case "Sorted":
                for(int i = 0; i < size; i++) arr[i] = i;
                break;
            case "ReverseSorted":
                for(int i = 0; i < size; i++) arr[i] = size - i;
                break;
            case "Duplicates":
                for(int i = 0; i < size; i++) arr[i] = random.nextInt(10);
                break;
            case "Random":
            default:
                for (int i = 0; i < size; i++) arr[i] = random.nextInt(100000);
                break;
        }
        return arr;
    }

    private static Point[] generatePoints(int n){
        Point[] points = new Point[n];
        for(int i = 0; i < n; i++){
            double x = -10000 + random.nextDouble() * 20000;
            double y = -10000 + random.nextDouble() * 20000;
            points[i] = new Point(x,y);
        }
        return points;
    }
}
