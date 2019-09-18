import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    StringBuilder message = new StringBuilder();
    StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
    ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();

    public void AddRandomInt() {
        double numberBetweenZeroAndOne = StdRandom.uniform();
        Integer item = StdRandom.uniform(100);

        if (numberBetweenZeroAndOne < 0.5) {
            sad.addFirst(item);
            ads.addFirst(item);
            message.append("\naddFirst(" + item + ")");
        } else {
            sad.addLast(item);
            ads.addLast(item);
            message.append("\naddLast(" + item + ")");
        }
    }

    public void removeRandomInt() {
        double numberBetweenZeroAndOne = StdRandom.uniform();
        if (numberBetweenZeroAndOne < 0.5) {
            message.append("\nremoveFirst()");
            Integer expect = ads.removeFirst();
            Integer actual = sad.removeFirst();
            assertEquals(message.toString(), expect, actual);
        } else {
            message.append("\nremoveLast()");
            Integer expect = ads.removeLast();
            Integer actual = sad.removeLast();
            assertEquals(message.toString(), expect, actual);
        }
    }

    public void TestSize() {
        message.append("\nsize()");
        assertEquals(message.toString(), ads.size(), sad.size());
    }

    @Test
    public void TestAddAndRemove() {
        for (int i = 0; i < 1000; i++) {
            if (sad.isEmpty()) {
                AddRandomInt();
                TestSize();
            } else {
                double random = StdRandom.uniform();
                if (random < 0.5) {
                    AddRandomInt();
                    TestSize();
                } else {
                    removeRandomInt();
                    TestSize();
                }
            }
        }
    }

    @Test
    public void TestGet() {
        for (int i = 0; i < 1000; i++) {
            AddRandomInt();
            int testIndex = StdRandom.uniform(sad.size());
            message.append("\nget(" + testIndex + ")");
            Integer expect = ads.get(testIndex);
            Integer actual = sad.get(testIndex);
            assertEquals(message.toString(), expect, actual);
        }
    }
}
