package es.datastructur.synthesizer;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer<Integer>(10);
        assertTrue(arb.isEmpty());
        for (int i = 1; i <= 10; i++) {
            arb.enqueue(i);
        }
        assertEquals(10, arb.fillCount());
        assertTrue(arb.isFull());
        for (int i = 1; i <= 10; i++) {
            assertEquals(i, arb.peek());
            assertEquals(i, arb.dequeue());
            assertEquals(10 - i, arb.fillCount());
        }
        assertTrue(arb.isEmpty());
    }

    @Test
    public void TestIteration() {
        ArrayRingBuffer arb = new ArrayRingBuffer<Integer>(10);
        for (int i = 1; i <= 10; i++) {
            arb.enqueue(i);
        }
        Iterator<Integer> arbIterator = arb.iterator();
        for (int i = 1; i <= 10; i++) {
            assertTrue(arbIterator.hasNext());
            assertEquals((Integer) i, arbIterator.next());
        }
        assertFalse(arbIterator.hasNext());
        assertTrue(arb.isFull());
    }

    @Test
    public void TestEquals() {
        ArrayRingBuffer arb1 = new ArrayRingBuffer<Integer>(10);
        ArrayRingBuffer arb2 = new ArrayRingBuffer<Integer>(10);
        ArrayRingBuffer arb3 = new ArrayRingBuffer<Integer>(10);
        for (int i = 1; i <= 10; i++) {
            arb1.enqueue(i);
            arb2.enqueue(i);
        }
        assertTrue(arb1.equals(arb1));
        assertTrue(arb1.equals(arb2));
        assertFalse(arb1.equals(arb3));
        assertFalse(arb1.equals(null));
        assertFalse(arb1.equals(1));
    }
}
