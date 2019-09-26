package bearmaps;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {
    @Test
    public void TestNaivePointSet() {
        Point p1 = new Point(1.1, 2.2);
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);

        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point ret = nn.nearest(3.0, 4.0);
        assertEquals(3.3, ret.getX(), 0.1);
        assertEquals(4.4, ret.getY(), 0.1);
    }

    @Test
    public void TestKDTree() {
        List<Point> samplePoints = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            samplePoints.add(new Point(StdRandom.uniform(-1000,1000), StdRandom.uniform(-1000, 1000)));
        }


        long start = System.currentTimeMillis();
        KDTree kdt = new KDTree(samplePoints);
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed(KDTreeInstantiation): " + (end - start)/1000.0 +  " seconds.");

        start = System.currentTimeMillis();
        NaivePointSet nn = new NaivePointSet(samplePoints);
        end = System.currentTimeMillis();
        System.out.println("Total time elapsed(NaivePointSetInstantiation): " + (end - start)/1000.0 +  " seconds.");

        double x = StdRandom.uniform(-1000.0, 1000.0);
        double y = StdRandom.uniform(-1000.0, 1000.0);
        Point ret1 = kdt.nearest(x, y);
        Point ret2 = nn.nearest(x, y);
        assertEquals(ret1, ret2);

        start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            x = StdRandom.uniform(-1000.0, 1000.0);
            y = StdRandom.uniform(-1000.0, 1000.0);
            ret1 = kdt.nearest(x, y);
        }
        end = System.currentTimeMillis();
        System.out.println("Total time elapsed(KDTree.nearest): " + (end - start)/1000.0 +  " seconds.");

        start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            x = StdRandom.uniform(-1000.0, 1000.0);
            y = StdRandom.uniform(-1000.0, 1000.0);
            ret2 = nn.nearest(x, y);
        }
        end = System.currentTimeMillis();
        System.out.println("Total time elapsed(NaivePointSet.nearest): " + (end - start)/1000.0 +  " seconds.");

    }

    public static void main(String[] args) {
    }
}
