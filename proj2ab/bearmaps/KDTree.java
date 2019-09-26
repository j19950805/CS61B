package bearmaps;

import java.util.Comparator;
import java.util.List;

public class KDTree {
    private Node root;

    private class Node {
        private Point point;
        private Node left, right;

        private Node(Point p) {
            point = p;
        }
    }

    private void add(Point p) {
        root = add(p, root, xComparator);
    }

    private Node add(Point p, Node x, Comparator<Point> c) {
        if (x == null) {
            return new Node(p);
        }
        if (c.compare(p, x.point) < 0) {
            x.left = add(p, x.left, changeComparator(c));
        } else {
            x.right = add(p, x.right, changeComparator(c));
        }
        return x;
    }

    public KDTree(List<Point> points) {
        for (Point p: points) {
            add(p);
        }
    }

    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        Point res = root.point;
        double minDist = Point.distance(goal, res);
        return nearest(goal, res, minDist, root, xComparator);
    }

    private Point nearest(Point goal, Point res, double minDist, Node x, Comparator<Point> c) {
        if (x == null) {
            return res;
        }

        double distance = Point.distance(goal, x.point);
        if (distance < minDist) {
            minDist = distance;
            res = x.point;
        }

        if (c.compare(goal, x.point) < 0) {
            res = nearest(goal, res, minDist, x.left, changeComparator(c));
            minDist = Point.distance(goal, res);
            if (directionMinDist(goal, x.point, c) < minDist) {
                res = nearest(goal, res, minDist, x.right, changeComparator(c));
            }
        } else {
            res = nearest(goal, res, minDist, x.right, changeComparator(c));
            minDist = Point.distance(goal, res);
            if (directionMinDist(goal, x.point, c) < minDist) {
                res = nearest(goal, res, minDist, x.right, changeComparator(c));
            }
        }
        return res;
    }

    Comparator<Point> xComparator = (p1, p2) -> {
        Double cmp = p1.getX() - p2.getX();
        if (cmp > 0) {
            return 1;
        } else if (cmp < 0) {
            return -1;
        } else {
            return 0;
        }
    };

    Comparator<Point> yComparator = (p1, p2) -> {
        Double cmp = p1.getY() - p2.getY();
        if (cmp > 0) {
            return 1;
        } else if (cmp < 0) {
            return -1;
        } else {
            return 0;
        }
    };
    private double directionMinDist(Point goal, Point nodePoint,Comparator<Point> c) {
        if (c == xComparator) {
            return Math.pow(goal.getX() - nodePoint.getX(), 2);
        } else {
            return Math.pow(goal.getY() - nodePoint.getY(), 2);
        }
    }

    private Comparator<Point> changeComparator(Comparator<Point> c) {
        if (c == xComparator) {
            return yComparator;
        } else {
            return xComparator;
        }
    }
}
