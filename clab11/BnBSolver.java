import java.util.Collections;
import java.util.List;

/**
 * BnBSolver for the Bears and Beds problem. Each Bear can only be compared to Bed objects and each Bed
 * can only be compared to Bear objects. There is a one-to-one mapping between Bears and Beds, i.e.
 * each Bear has a unique size and has exactly one corresponding Bed with the same size.
 * Given a list of Bears and a list of Beds, create lists of the same Bears and Beds where the ith Bear is the same
 * size as the ith Bed.
 */
public class BnBSolver {
    private List<Bear> bears;
    private List<Bed> beds;

    public BnBSolver(List<Bear> bears, List<Bed> beds) {
        this.bears = bears;
        this.beds = beds;
        sortBedsWithRandomBear(0, bears.size() - 1);
    }

    private void sortBedsWithRandomBear(int start, int end) {
        if (start >= end) {
            return;
        }
        Bear pivot = bears.get(StdRandom.uniform(start, end + 1));
        int i = start;
        int j = end;
        while (i < j) {
            int cmp1 = pivot.compareTo(beds.get(i));
            int cmp2 = pivot.compareTo(beds.get(j));
            if (cmp1 > 0) {
                i++;
            }
            if (cmp2 < 0) {
                j--;
            }
            if (cmp1 <= 0 && cmp2 >= 0) {
                Collections.swap(beds, i, j);
            }
        }
        sortBearsWithBed(beds.get(i), start, end);
    }

    private void sortBearsWithBed(Bed pivot, int start, int end) {
        int i = start;
        int j = end;
        while (i < j) {
            int cmp1 = pivot.compareTo(bears.get(i));
            int cmp2 = pivot.compareTo(bears.get(j));
            if (cmp1 > 0) {
                i++;
            }
            if (cmp2 < 0) {
                j--;
            }
            if (cmp1 <= 0 && cmp2 >= 0) {
                Collections.swap(bears, i, j);
            }
        }
        sortBedsWithRandomBear(start, i - 1);
        sortBedsWithRandomBear(i + 1, end);
    }

    /**
     * Returns List of Bears such that the ith Bear is the same size as the ith Bed of solvedBeds().
     */
    public List<Bear> solvedBears() {
        return bears;
    }

    /**
     * Returns List of Beds such that the ith Bear is the same size as the ith Bear of solvedBears().
     */
    public List<Bed> solvedBeds() {
        return beds;
    }
}
