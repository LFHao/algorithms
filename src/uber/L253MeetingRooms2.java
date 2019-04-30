package uber;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class L253MeetingRooms2 {

    public int minMeetingRooms(int[][] intervals) {
        if (intervals == null || intervals.length == 0) return 0;

        Arrays.sort(intervals, Comparator.comparing((int[] interval) -> interval[0]));

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(intervals[0][1]);

        for (int i = 1; i < intervals.length; i++) {
            int[] interval = intervals[i];
            if (interval[0] >= pq.peek()) {
                pq.poll();
            }
            pq.offer(interval[1]);
        }
        return pq.size();
    }
}
