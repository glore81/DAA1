import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ClosestPairSolver {
    public double bruteForce(Point[] points, int low, int high){
        double minDist = Double.MAX_VALUE;

        for(int i = low; i < high; i++){
            for(int j = i + 1; j <= high; j++){
                double d = Point.distance(points[i],points[j]);
                if(d < minDist){
                    minDist = d;
                }
            }
        }
        return minDist;
    }

    private double searchInHalves(Point[] points, int low, int high){
        int n = high - low + 1;
        if(n <= 3){
            return bruteForce(points, low, high);
        }

        int mid = low + (high - low) / 2;
        Point midPoint = points[mid];

        double distL = searchInHalves(points, low, mid);
        double distR = searchInHalves(points, mid + 1, high);

        double distMin = Math.min(distL,distR);

        ArrayList<Point> bound = new ArrayList<>();
        for(int i = low; i <= high; i++){
            if(Math.abs(points[i].x - midPoint.x) < distMin){
                bound.add(points[i]);
            }
        }

        return Math.min(distMin, boundClosest(bound,distMin));
    }

    private double boundClosest(ArrayList<Point> bound, double distMin){
        double min = distMin;

        bound.sort(Comparator.comparingDouble(point -> point.y));

        for(int i = 0; i < bound.size(); i++){
            for(int j = i+1; j < bound.size() && (bound.get(j).y - bound.get(i).y) < min; j++){
                double currentDist = Point.distance(bound.get(i), bound.get(j));
                if (currentDist < min){
                    min = currentDist;
                }
            }
        }
        return min;
    }

    public double findClosestPair(Point[] points){
        if(points == null || points.length < 2){
            throw new IllegalArgumentException("At least 2 points are required to find a pair!");
        }

        Arrays.sort(points, Comparator.comparingDouble(point -> point.x));
        return searchInHalves(points, 0, points.length-1);
    }
}
