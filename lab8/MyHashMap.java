import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MyHashMap<K, V> implements Map61B<K, V> {
    private double loadFactor;
    private int n;
    private int m;
    private ULLMap<K, V>[] buckets;
    private HashSet<K> keySet;


    public MyHashMap() {
        this(16);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, 0.75);
    }

    public MyHashMap(int initialSize, double loadFactor) {
        n = 0;
        m = initialSize;
        this.loadFactor = loadFactor;
        buckets = (ULLMap<K, V>[]) new ULLMap[m];
        for (int i = 0; i < m; i++) {
            buckets[i] = new ULLMap<>();
        }
        keySet = new HashSet<>();
    }

    private void resize(int bucketSize) {
        m = bucketSize;
        ULLMap<K, V>[] newBuckets = (ULLMap<K, V>[]) new ULLMap[m];
        for (int i = 0; i < m; i++) {
            newBuckets[i] = new ULLMap<>();
        }
        for (ULLMap<K, V> lst: buckets) {
            for (K key : lst) {
                V value = lst.get(key);
                newBuckets[hash(key)].put(key, value);
            }
        }
        buckets = newBuckets;
    }

    @Override
    public void clear() {
        MyHashMap<K, V> clearMap = new MyHashMap<>();
        this.m = clearMap.m;
        this.n = clearMap.n;
        this.keySet = clearMap.keySet;
        this.buckets = clearMap.buckets;
    }

    @Override
    public boolean containsKey(K key) {
        return buckets[hash(key)].containsKey(key);
    }

    @Override
    public V get(K key) {
        return buckets[hash(key)].get(key);
    }

    @Override
    public int size() {
        return n;
    }

    @Override
    public void put(K key, V value) {
        buckets[hash(key)].put(key, value);
        keySet.add(key);
        n = keySet.size();
        if ((double)n / m > loadFactor) {
            resize(m * 2);
        }
    }

    @Override
    public Set<K> keySet() {
        return keySet;
    }

    @Override
    public V remove(K key) {
        keySet.remove(key);
        n = keySet.size();
        if ((double) n / m > loadFactor && m > 16) {
            resize(m / 2);
        }
        return buckets[hash(key)].remove(key);
    }

    @Override
    public V remove(K key, V value) {
        V val = buckets[hash(key)].remove(key, value);
        if(val == null) {
            return null;
        }
        keySet.remove(key);
        n = keySet.size();
        if ((double) n / m > loadFactor && m > 16) {
            resize(m / 2);
        }
        return val;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet.iterator();
    }

    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }
}
