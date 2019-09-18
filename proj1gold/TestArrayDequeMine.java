import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestArrayDequeMine {
    String message = "";

    public void AddRandomInt(Deque<Integer> A) {
        double numberBetweenZeroAndOne = StdRandom.uniform();
        Integer item = StdRandom.uniform(1000);

        if (numberBetweenZeroAndOne < 0.5) {
            A.addFirst(item);
            message += "\naddFirst(" + item + ")";
        } else {
            A.addLast(item);
            message += "\naddLast(" + item + ")";
        }
    }

    public void removeRandomInt(Deque<Integer> A, Deque<Integer> B) {
        double numberBetweenZeroAndOne = StdRandom.uniform();
        if (numberBetweenZeroAndOne < 0.5) {
            message += "\nremoveFirst()";
            Integer expect = B.removeFirst();
            Integer actual = A.removeFirst();
            assertEquals(message, expect, actual);
        } else {
            message += "\nremoveLast()";
            Integer expect = B.removeLast();
            Integer actual = A.removeLast();
            assertEquals(message, expect, actual);
        }
    }

    public void TestSize(Deque<Integer> A, Deque<Integer> B) {
        message += "\nsize()";
        assertEquals(message, B.size(), A.size());
    }

    @Test
    public void TestAddAndRemove() {
        ArrayDeque<Integer> A = new ArrayDeque<Integer>();
        for (int i = 0; i < 1000; i++) {
            AddRandomInt(A);
        }
        ArrayDeque<Integer> B = new ArrayDeque<Integer>(A);
        for (int i = 0; i < 1000; i++) {
            removeRandomInt(A, B);
        }
    }

    @Test
    public void TestGet() {
        LinkedListDeque<Integer> A = new LinkedListDeque<>();
        for (int i = 0; i < 1000; i++) {
            AddRandomInt(A);
            LinkedListDeque<Integer> B = new LinkedListDeque<>(A);
            int testIndex = StdRandom.uniform(A.size());
            message += "\nget(" + testIndex + ")";
            Integer expect = B.get(testIndex);
            Integer actual = A.get(testIndex);
            assertEquals(message, expect, actual);
        }
    }

    public static void main(String[] args) {
        ArrayDeque<Integer> A = new ArrayDeque<>();
        for (int i = 0; i < 20; i++) {
            A.addLast(i);
        }
        A.printDeque();
    }
}
