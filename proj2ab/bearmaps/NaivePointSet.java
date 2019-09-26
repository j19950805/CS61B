package bearmaps;

import java.util.ArrayList;
import java.util.List;

public class NaivePointSet implements PointSet {
    private List<Point> points;

    public NaivePointSet(List<Point> points) {
        this.points = new ArrayList<>();
        this.points.addAll(points);
    }

    @Override
    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        Point result = points.get(0);
        double minDistance = Point.distance(goal, points.get(0));
        for (int i = 1; i < points.size(); i++) {
            double distance = Point.distance(goal, points.get(i));
            if (minDistance > distance) {
                minDistance = distance;
                result = points.get(i);
            }
        }
        return result;
    }
}
