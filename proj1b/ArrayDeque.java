import java.util.Objects;

public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        size = 0;
        items = (T[]) new Object[8];
        nextFirst = 3;
        nextLast = 4;
    }

    public ArrayDeque(ArrayDeque other) {
        size = other.size;
        items = (T[]) new Object[other.items.length];
        System.arraycopy(other.items, 0, items, 0, items.length);
        nextFirst = other.nextFirst;
        nextLast = other.nextLast;
    }

    private void resize(int capacity) {
        T[] resized = (T[]) new Object[capacity];
        int initFirst = arrayAdd(nextFirst, 1);
        if (nextLast <= nextFirst) {
            int tailLength = items.length - nextFirst - 1;
            int newFirst = (capacity - tailLength) % capacity;
            System.arraycopy(items, 0, resized, 0, nextLast);
            System.arraycopy(items, initFirst, resized, newFirst, tailLength);
            items = resized;
            nextFirst = arrayAdd(newFirst, -1);
        } else {
            System.arraycopy(items, initFirst, resized, 4, size);
            items = resized;
            nextFirst = 3;
            nextLast = size + 4;
        }
    }

    private int arrayAdd(int index, int addend) {
        int res = (index + addend) % items.length;
        if (res >= 0) {
            return res;
        } else {
            return items.length + res;
        }
    }

    public void addFirst(T item) {
        size += 1;
        items[nextFirst] = item;
        nextFirst = arrayAdd(nextFirst, -1);
        if (nextFirst == nextLast) {
            resize(items.length * 2);
        }
    }

    public void addLast(T item) {
        size += 1;
        items[nextLast] = item;
        nextLast = arrayAdd(nextLast, 1);
        if (nextFirst == nextLast) {
            resize(items.length * 2);
        }
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int p = arrayAdd(nextFirst, 1);
        while (p != nextLast) {
            System.out.printf("%s ", items[p]);
            p = arrayAdd(p, 1);
        }
        System.out.println();
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        size -= 1;
        nextFirst = arrayAdd(nextFirst, 1);
        T first = items[nextFirst];
        items[nextFirst] = null;

        if (((float) size / items.length) < 0.25 && items.length >= 16) {
            resize(items.length / 2);
        }
        return first;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        size -= 1;
        nextLast = arrayAdd(nextLast, -1);
        T last = items[nextLast];
        items[nextLast] = null;

        if (((float) size / items.length) < 0.25 && items.length >= 16) {
            resize(items.length / 2);
        }
        return last;
    }

    public T get(int index) {
        if (index > size) {
            return null;
        }
        return items[arrayAdd(nextFirst, index + 1)];
    }
}
