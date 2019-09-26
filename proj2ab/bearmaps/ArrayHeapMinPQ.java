package bearmaps;

import java.util.*;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private ArrayList<PriorityNode> minPQ;
    private HashMap<T, Integer> keyIndex;

    public  ArrayHeapMinPQ() {
        minPQ = new ArrayList<>();
        minPQ.add(null);
        keyIndex = new HashMap<>();
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("Item already exist");
        }
        minPQ.add(new PriorityNode(item, priority));
        keyIndex.put(item, size());
        swim(size());
    }

    @Override
    public boolean contains(T item) {
        return keyIndex.containsKey(item);
    }

    @Override
    public T getSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException("PQ is empty");
        }
        return minPQ.get(1).getItem();
    }

    @Override
    public T removeSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException("PQ is empty");
        }
        T smallest = minPQ.get(1).getItem();
        swap(1, size());
        minPQ.remove(size());
        keyIndex.remove(smallest);
        sink(1);
        return smallest;
    }

    @Override
    public int size() {
        return minPQ.size() - 1;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        int originalIndex = keyIndex.get(item);
        PriorityNode p = minPQ.get(originalIndex);
        p.setPriority(priority);
        swim(originalIndex);
        sink(originalIndex);
    }

    private class PriorityNode implements Comparable<PriorityNode> {
        private T item;
        private double priority;

        PriorityNode(T e, double p) {
            this.item = e;
            this.priority = p;
        }

        T getItem() {
            return item;
        }

        double getPriority() {
            return priority;
        }

        void setPriority(double priority) {
            this.priority = priority;
        }


        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            } else {
                return ((PriorityNode) o).getItem().equals(getItem());
            }
        }

        @Override
        public int hashCode() {
            return item.hashCode();
        }

        @Override
        public int compareTo(PriorityNode o) {
            if (o == null) {
                return -1;
            }
            return Double.compare(this.getPriority(), o.getPriority());
        }
    }

    private void swim(int k) {
        while (k / 2 > 0 && greater(k / 2, k)) {
            swap(k, k / 2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (k * 2 <= size()) {
            int j = k * 2;
            if (j < size() && greater(j, j + 1)) {
                j++;
            }
            if (!greater(k, j)) {
                break;
            }
            swap(j, k);
            k = j;
        }
    }

    private void swap(int i, int j) {
        Collections.swap(minPQ, i, j);
        keyIndex.replace(minPQ.get(i).getItem(), i);
        keyIndex.replace(minPQ.get(j).getItem(), j);
    }

    private boolean greater(int i, int j) {
        return minPQ.get(i).compareTo(minPQ.get(j)) > 0;
    }

}