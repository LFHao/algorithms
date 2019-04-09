package uber;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * Use Deque
 */
public class L239SlidingWindowMaximum {
    /**
     * https://leetcode.com/problems/sliding-window-maximum/discuss/65884/Java-O(n)-solution-using-deque-with-explanation
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return new int[0];
        }

        int[] res = new int[nums.length - k + 1];

        // head to remove  old nums out of range, tail to add new nums
        Deque<Integer> deque = new ArrayDeque<>();
        // index i to go through the array
        int i = 0, resI = 0;
        while (i < nums.length) {
            // remove invalid old index that are out of range
            while (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
                deque.pollFirst();
            }

            // remove numbers smaller than newly added a[i] as it cannot be used in the future
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }

            // add nums[i]
            deque.offer(i);

            // if there is a range, add the nums[peek] as nums[peek] is the max
            if (i - k + 1 >= 0) {
                res[resI++] = nums[deque.peekFirst()];
            }
            i++;
        }

        return res;
    }

    public static void main(String[] args) {
        int[] nums = {1,3,-1,-3,5,3,6,7};
        int[] res = new L239SlidingWindowMaximum().maxSlidingWindow(nums, 3);
        System.out.println(Arrays.toString(res));
    }
}
