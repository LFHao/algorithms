package stripe;

import java.util.Comparator;
import java.util.Map;

/**
 * Step 3.a: implement a comparator class
 */
public class RecordComparator implements Comparator<Map<String, Integer>> {
    String key;
    boolean isAsc;

    RecordComparator(String key, boolean isAsc) {
        this.key = key;
        this.isAsc = isAsc;
    }
    public int compare(Map<String, Integer> left, Map<String, Integer> right) {
        int leftValue = left.getOrDefault(key, 0);
        int rightValue = right.getOrDefault(key, 0);
        int v1 = isAsc? leftValue : rightValue;
        int v2 = isAsc? rightValue : leftValue;
        return (v1 < v2) ? -1 : ((v1 == v2) ? 0 : 1);
    }
}
