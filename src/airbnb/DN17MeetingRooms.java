package airbnb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class DN17MeetingRooms {
    /**
     * Solution 1
     * @param intervals: an array of meeting time intervals
     * @return: the minimum number of conference rooms required
     */
    public int minMeetingRooms(List<Interval> intervals) {
        if (intervals == null || intervals.size() == 0) return 0;

        Collections.sort(intervals, Comparator.comparing((Interval i) -> i.start));

        List<List<Interval>> rooms = new ArrayList<>();
        rooms.add(new ArrayList<>(Arrays.asList(intervals.get(0))));

        for (int i = 1; i < intervals.size(); i++) {
            Interval interval = intervals.get(i);
            boolean hasConflict = false;
            for (int j = 0; j < rooms.size(); j++) {
                int meetingSize = rooms.get(j).size();
                if (interval.start < rooms.get(j).get(meetingSize - 1).end) {
                    hasConflict = true;
                } else {
                    rooms.get(j).add(interval);
                    hasConflict = false;
                    break;
                }
            }
            if (hasConflict) {
                rooms.add(new ArrayList<>(Arrays.asList(interval)));
            }

        }

        return rooms.size();
    }

    public int minMeetingRooms2(List<Interval> intervals) {
        if (intervals == null || intervals.size() == 0) return 0;

        Collections.sort(intervals, Comparator.comparing((Interval i) -> i.start));

        PriorityQueue<Integer> queue = new PriorityQueue<>();
        queue.offer(intervals.get(0).end);
        int count = 1;

        for (int i = 1; i < intervals.size(); i++) {
            Interval interval = intervals.get(i);
            if (interval.start < queue.peek()) {
                count++;
            } else {
                queue.poll();
            }
            queue.offer(interval.end);
        }

        return count;
    }

    public static void main(String[] args) {
        List<Interval> intervals = new ArrayList<>(Arrays.asList(new Interval(0, 30), new Interval(5, 10), new Interval(15, 20)));
        DN17MeetingRooms mr = new DN17MeetingRooms();
        System.out.println(mr.minMeetingRooms2(intervals));
    }
}


/**
 * Definition of airbnb.Interval:
 */

class Interval {
    int start, end;
    Interval(int start, int end) {
        this.start = start;
        this.end = end;
    }
}

