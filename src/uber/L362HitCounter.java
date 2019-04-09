package uber;

import java.util.LinkedList;
import java.util.Queue;

public class L362HitCounter {
    /**
     * Solution 1: queue
     */
    Queue<Integer> queue;
    /** Initialize your data structure here. */
    public L362HitCounter() {
        queue = new LinkedList<>();
    }

    public void hit(int timestamp) {
        refreshQueue(timestamp);
        queue.offer(timestamp);
    }

    public int getHits(int timestamp) {
        refreshQueue(timestamp);
        return queue.size();
    }

    private void refreshQueue(int timestamp) {
        while (! this.queue.isEmpty() && queue.peek() + 300 <= timestamp) {
            queue.poll();
        }
    }

    /**
     * Solution 2: 2 arrays time[] to record timestamp of every valid hit (within 300 seconds)
     * hit[] to record corresponding hits
     */
    int[] times;
    int[] hits;
    int seconds;
    public L362HitCounter(int seconds) {
        this.seconds = seconds;
        times = new int[seconds];
        hits = new int[seconds];
    }

    public void hit2(int timestamp) {
        if (times[timestamp % seconds] != timestamp) {
            times[timestamp % seconds] = timestamp;
            hits[timestamp % seconds] = 1;
        } else {
            hits[timestamp % seconds]++;
        }
    }

    public int getHits2(int timestamp) {
        int sum = 0;
        for (int i = 0; i < seconds; i++) {
            if (times[i] + 300 > timestamp) {
                sum += hits[i];
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        L362HitCounter c1 = new L362HitCounter();
        L362HitCounter c2 = new L362HitCounter(300);
        c1.hit(1);
        c2.hit2(1);
        c1.hit(2);
        c2.hit2(2);
        c1.hit(3);
        c2.hit2(3);
        System.out.println(c1.getHits(4));
        System.out.println(c2.getHits2(4));
        c1.hit(300);
        c2.hit2(300);
        System.out.println(c1.getHits(300));
        System.out.println(c2.getHits2(300));
        System.out.println(c1.getHits(301));
        System.out.println(c2.getHits2(301));
    }
}
