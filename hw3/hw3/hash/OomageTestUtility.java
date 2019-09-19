package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        int N = oomages.size();
        int[] buckets = new int[M];
        for (Oomage o: oomages) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            buckets[bucketNum] += 1;
        }
        for (int bucketSize : buckets) {
            if (bucketSize < N / 50 || bucketSize> N / 2.5) {
                return false;
            }
        }
        return true;
    }
}
