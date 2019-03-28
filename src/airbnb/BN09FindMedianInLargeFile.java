package airbnb;

import java.util.Collections;
import java.util.PriorityQueue;

// https://leetcode.com/problems/find-median-from-data-stream/
public class BN09FindMedianInLargeFile {
    /** initialize your data structure here. */
    /** Use 2 heaps: maxSmall heap to store smaller half, and minLarge heap to store larger half
     And the maxSmall is 1 bigger or equal to minLarge, so when findMedian, you can get maxSmall if it's odd
     **/
    private PriorityQueue<Integer> maxSmall;
    private PriorityQueue<Integer> minLarge;

    public BN09FindMedianInLargeFile() {
        maxSmall = new PriorityQueue<>(Collections.reverseOrder()); // reverse order and no size limit
        minLarge = new PriorityQueue<>();
    }

    public void addNum(int num) {
        maxSmall.add(num);
        // because maxSmall has 1 more number added, then it should move 1 number to minLarge
        minLarge.add(maxSmall.poll());
        if (minLarge.size() > maxSmall.size()) {
            maxSmall.add(minLarge.poll());
        }
    }

    public double findMedian() {
        if (maxSmall.size() == minLarge.size()) return (double) (maxSmall.peek() + minLarge.peek())/2;
        return maxSmall.peek();
    }
}
