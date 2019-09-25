package bearmaps;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ArrayHeapMinPQTest {
    @Test
    public void TestArrayHeapMinPQ() {
        ArrayHeapMinPQ<Character> ahmPQ = new ArrayHeapMinPQ<>();
        ahmPQ.add('e', 0.1);
        ahmPQ.add('d', 0.2);
        ahmPQ.add('c', 0.3);
        ahmPQ.add('b', 0.4);
        ahmPQ.add('a', 0.5);
        assertEquals(ahmPQ.getSmallest(), (Character) 'e');
        ahmPQ.changePriority('a', 0.05);
        assertEquals(ahmPQ.removeSmallest(), (Character) 'a');
        assertEquals(ahmPQ.size(), 4);
        ahmPQ.changePriority('b', 0.12);
        assertEquals(ahmPQ.removeSmallest(), (Character) 'e');
        assertEquals(ahmPQ.removeSmallest(), (Character) 'b');

    }

    public static void main(String[] args) {
        ArrayHeapMinPQ<Integer> ahmPQ = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> nmPQ = new NaiveMinPQ<>();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i += 1) {
            ahmPQ.add(i, StdRandom.uniform());
        }
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed(ArrayHeapMinPQ.add): " + (end - start)/1000.0 +  " seconds.");

        start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i += 1) {
            nmPQ.add(i, StdRandom.uniform());
        }
        end = System.currentTimeMillis();
        System.out.println("Total time elapsed(NaiveMinPQ.add): " + (end - start)/1000.0 +  " seconds.");

        start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i += 1) {
            ahmPQ.getSmallest();
        }
        end = System.currentTimeMillis();
        System.out.println("Total time elapsed(ArrayHeapMinPQ.getSmallest): " + (end - start)/1000.0 +  " seconds.");

        start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i += 1) {
            nmPQ.getSmallest();
        }
        end = System.currentTimeMillis();
        System.out.println("Total time elapsed(NaiveMinPQ.getSmallest): " + (end - start)/1000.0 +  " seconds.");

        start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i += 1) {
            ahmPQ.contains(i);
        }
        end = System.currentTimeMillis();
        System.out.println("Total time elapsed(ArrayHeapMinPQ.contains): " + (end - start)/1000.0 +  " seconds.");

        start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i += 1) {
            nmPQ.contains(i);
        }
        end = System.currentTimeMillis();
        System.out.println("Total time elapsed(NaiveMinPQ.contains): " + (end - start)/1000.0 +  " seconds.");

        start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i += 1) {
            ahmPQ.changePriority(i, StdRandom.uniform());
        }
        end = System.currentTimeMillis();
        System.out.println("Total time elapsed(ArrayHeapMinPQ.changePriority): " + (end - start)/1000.0 +  " seconds.");

        start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i += 1) {
            nmPQ.changePriority(i, StdRandom.uniform());
        }
        end = System.currentTimeMillis();
        System.out.println("Total time elapsed(NaiveMinPQ.changePriority): " + (end - start)/1000.0 +  " seconds.");

        int size;
        start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i += 1) {
            ahmPQ.removeSmallest();
        }
        end = System.currentTimeMillis();
        System.out.println("Total time elapsed(ArrayHeapMinPQ.removeSmallest): " + (end - start)/1000.0 +  " seconds.");

        start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i += 1) {
            nmPQ.removeSmallest();
        }
        end = System.currentTimeMillis();
        System.out.println("Total time elapsed(NaiveMinPQ.removeSmallest): " + (end - start)/1000.0 +  " seconds.");
    }
}
