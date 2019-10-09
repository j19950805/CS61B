import edu.princeton.cs.algs4.Queue;

import org.junit.Test;
import org.junit.Assert;

import static org.junit.Assert.assertTrue;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {

    }

    @Test
    public void testMergeSort() {
        Queue<String> q1 = new Queue<>();
        q1.enqueue("Papaya");
        q1.enqueue("Banana");
        q1.enqueue("Apple");
        q1.enqueue("Tangerine");
        q1.enqueue("Grape");
        q1.enqueue("Mango");
        q1.enqueue("Guava");
        q1.enqueue("Apple");
        q1.enqueue("Kiwifruit");
        q1.enqueue("Mango");
        q1.enqueue("Peach");
        assertTrue(isSorted(MergeSort.mergeSort(q1)));
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
