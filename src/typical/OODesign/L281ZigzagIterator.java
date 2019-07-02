package typical.OODesign;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class L281ZigzagIterator {
    // similar to merge k lists
    private Queue<Iterator> queue;

    public L281ZigzagIterator(List<List<Integer>> lists) {
        queue = new LinkedList<>();
        for (List<Integer> list : lists) {
            if (!list.isEmpty()) {
                queue.offer(list.iterator());
            }
        }
    }

    public int next() {
        Iterator cur = queue.poll();
        int res = (int) cur.next();

        if (cur.hasNext()) queue.offer(cur);

        return res;
    }


    public boolean hasNext() {
        return !queue.isEmpty();
    }

    public static void main(String[] args) {
        List<Integer> v1 = new ArrayList<>(Arrays.asList(new Integer[] {1,2,3}));
        List<Integer> v2 = new ArrayList<>(Arrays.asList(new Integer[] {4,5,6,7}));
        List<Integer> v3 = new ArrayList<>(Arrays.asList(new Integer[] {8,9}));
        List<List<Integer>> lists = new ArrayList<>();
        lists.add(v1);
        lists.add(v2);
        lists.add(v3);

        L281ZigzagIterator s = new L281ZigzagIterator(lists);
        while (s.hasNext()) {
            System.out.println(s.next());
        }

    }
}
