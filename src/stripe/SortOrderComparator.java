package stripe;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Step 4: multiple order precedence in a comparator, find first_by_order
 */
public class SortOrderComparator implements Comparator<Map<String, Integer>> {
    LinkedHashMap<String, String> sortOrder;

    SortOrderComparator(LinkedHashMap<String, String> sortOrder) {
        this.sortOrder = sortOrder;
    }

    public int compare(Map<String, Integer> left, Map<String, Integer> right) {
        Iterator<Map.Entry<String, String>> it = sortOrder.entrySet().iterator();
        int res = 0;
        while (it.hasNext()) {
            Map.Entry<String, String> order = it.next();
            RecordComparator rc = new RecordComparator(order.getKey(), order.getValue().equals("asc"));
            res = rc.compare(left, right);
            if (res != 0) break;
        }
        return res;
    }

}
