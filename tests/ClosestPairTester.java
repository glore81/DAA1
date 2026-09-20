import java.util.Random;

public class ClosestPairTester {
    private static final int NUM_TESTS = 50;
    private static final int MAX_POINTS = 2000;
    private static final Random random = new Random();
    private static final double EPSILON = 1e-9;


    public static void main(String[] args){
        boolean allPassed = true;

        for(int i = 0; i < NUM_TESTS; i++){
            int n = random.nextInt(MAX_POINTS - 2) + 2;
            Point[] points = generateRandomPoints(n);

            ClosestPairSolver solver = new ClosestPairSolver();
            double fastResult = solver.findClosestPair(points);

            double bruteResult = solver.bruteForce(points, 0, points.length-1);
            if(Math.abs(fastResult-bruteResult) > EPSILON){
                System.out.println("Test #"+ i + " Failed");
                System.out.println("Points count: "+ n);
                System.out.println("Fast result: "+ fastResult);
                System.out.println("Brute result: "+ bruteResult);
                allPassed = false;
                break;
            }
        }
        if(allPassed){
            System.out.println("All " + NUM_TESTS + " tests completed successfully");
        }
    }

    private static Point[] generateRandomPoints(int n){
        Point[] points = new Point[n];
        for(int i = 0; i < n; i++){
            double x = -10000 + random.nextDouble() * 20000;
            double y = -10000 + random.nextDouble() * 20000;

            points[i] = new Point(x,y);
        }
        return points;
    }
}
