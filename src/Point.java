public class Point {
    double x, y;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public static double distance(Point p1, Point p2) {
        double dy = p1.y - p2.y;
        double dx = p1.x - p2.x;
        return Math.sqrt(dx * dx + dy * dy);
    }

}
