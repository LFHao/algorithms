package airbnb;


/**
 * Question:
 * Find the median from a large file of integers. You can not access the numbers by index, can only access it
 * sequentially. And the numbers cannot fit in memory.
 *
 * Similar to https://leetcode.com/problems/find-median-from-data-stream/
 */
public class BN09FindMedianInLargeFile {
    public double findMedian(int[] nums) {
        int len = nums.length;
        if (len % 2 == 1) {
            // if it is an odd number, find the median one in the nums
            return search(nums, len/2 + 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
        } else {
            // if it is an even number, find the len/2-th and the (len/2+1)-th to get the median
            return (search(nums, len/2, Integer.MIN_VALUE, Integer.MAX_VALUE) +
                    search(nums, len/2 + 1, Integer.MIN_VALUE, Integer.MAX_VALUE)) / 2;
        }
    }

    private long search(int[] nums, int n, long low, long high) {
        if (low >= high) return low;

        long res = low;
        // use long to avoid overflow (larger than Integer.MAX_VALUE)
        long guess = low + (high - low) / 2;
        // get how many numbers are smaller than guess
        int count = 0;
        for (int num : nums) {
            if (num <= guess) {
                count++;
                // result should be the largest one that is smaller than guess
                // it must be in the array
                res = Math.max(res, num);
            }
        }

        if (count == n) {
            return res;
        } else if (count < n) {
            return search(nums, n, Math.max(res + 1, guess), high);
        } else {
            return search(nums, n, low, res);
        }
    }

    public static void main(String[] args) {
        BN09FindMedianInLargeFile s = new BN09FindMedianInLargeFile();
        System.out.println(s.findMedian(new int[]{3, -2, 7}));
        System.out.println(s.findMedian(new int[]{-100, 99, 3, 0, 5, 7, 11, 66}));
        System.out.println(s.findMedian(new int[]{4, -100, 99, 3, 0, 5, 7, 11, 66, -33}));
    }

}
