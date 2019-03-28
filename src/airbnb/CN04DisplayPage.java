package airbnb;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CN04DisplayPage {
    public List<String> displayPages(List<String> input, int pageSize) {
        List<String> res = new ArrayList<>();

        // 1 set is to 1 page, to track if there is duplicate host id in the page
        Set<String> set = new HashSet<>();
        // reachEnd is if each round has reached the end of the input
        boolean reachEnd = false;
        Iterator<String> iterator = input.iterator();
        int count = 0;

        while (iterator.hasNext()) {
            String curListing = iterator.next();
            String hostId = curListing.split(",")[0];

            // if it reaches to the end of the iterator, then add the following with no mind
            if (!set.contains(hostId) || reachEnd) {
                set.add(hostId);
                res.add(curListing);
                iterator.remove();
                count++;
            }

            if (count == pageSize) {
                // only when there still more listing, we add a page divider
                if (!input.isEmpty()) {
                    res.add(" ");
                }
                // we reset everything after one page is done
                set.clear();
                count = 0;
                reachEnd = false;
                iterator = input.iterator();
            }

            // if after this operation, the next has no value, then restart the iterator to traverse another round
            if (!iterator.hasNext()) {
                reachEnd = true;
                iterator = input.iterator();
            }
        }

        return res;
    }

    public static void main(String[] args) {
        List<String> input = new LinkedList<>();
        input.add("1,10,SF");
        input.add("2,9,SF");
        input.add("1,8,SF");
        input.add("1,7,SJ");
        input.add("2,6,OK");
        input.add("1,5,WA");

        List<String> res = new CN04DisplayPage().displayPages(input,4);
        System.out.println(res);
    }
}
