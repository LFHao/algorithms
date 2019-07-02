package typical.sweepLine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class L218SkylineProblem {
    // Sweep Line
    // http://zxi.mytechroad.com/blog/tree/leetcode-218-the-skyline-problem/
    public List<List<Integer>> getSkyline(int[][] buildings) {
        List<int[]> list = new ArrayList<>();
        for(int[] building : buildings) {
            // enter point
            list.add(new int[]{building[0], building[2]});
            // leaving point
            list.add(new int[]{building[1], -building[2]});
        }

        list.sort((e1, e2) -> e1[0] == e2[0] ? e2[1] - e1[1] : e1[0] - e2[0]);

        List<List<Integer>>  res = new ArrayList<>();
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        for (int[] e : list) {
            int x = e[0];
            int h = e[1];

            boolean entering = h > 0;
            h = Math.abs(h);

            if (entering) {
                int maxH = maxHeap.isEmpty() ? 0 : maxHeap.peek();
                if (h > maxH) {
                    List<Integer> l = new ArrayList<>();
                    l.add(x);
                    l.add(h);
                    res.add(l);
                }
                maxHeap.add(h);
            } else {
                // if it is leaving, h was added when entering
                // remove in PriorityQueue is O(n) to find + O(logn) to remove
                maxHeap.remove(h);
                int maxH = maxHeap.isEmpty() ? 0 : maxHeap.peek();
                if (h > maxH) {
                    List<Integer> l = new ArrayList<>();
                    l.add(x);
                    l.add(maxH);
                    res.add(l);
                }
            }
        }

        return res;
    }
}
