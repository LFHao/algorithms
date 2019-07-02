package google;


public class L410SplitArrayLargestSum {
    /**
     * DP
     * Time complexity : O(n^2 * m). The total number of states is O(n * m). To compute each state f[i][j], we need to go through the
     *  whole array to find the optimum k. This requires another O(n) loop. So the total time complexity is O(n ^ 2 * m)
     *
     * Space complexity : O(n * m). The space complexity is equivalent to the number of states, which is O(n * m)
     */
    public int splitArray(int[] nums, int m) {
        int n = nums.length;
        // dp[i][j] means for sub array [0~i] the min of largest sum of j parts
        int[][] dp = new int[n + 1][m + 1];
        int[] subSum = new int[n + 1];

        // fill dp[i][j] with Integer.MAX_VALUE because for any non-calculate, we need to find the minimum
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                dp[i][j] = Integer.MAX_VALUE;
            }
        }

        for (int i = 0; i < n; i++) {
            subSum[i + 1] = subSum[i] + nums[i];
        }

        // Let's define f[i][j] to be the minimum largest subarray sum for splitting nums[0..i] into j parts.

        // Consider the jth subarray. We can split the array from a smaller index k to i to form it.
        // Thus f[i][j] can be derived from max(f[k][j - 1], nums[k + 1] + ... + nums[i]). For all valid index k,
        // f[i][j] should choose the minimum value of the above formula.

        dp[0][0] = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                for (int k = 0; k < i; k++) {
                    dp[i][j] = Math.min(dp[i][j], Math.max(dp[k][j - 1], subSum[i] - subSum[k]));
                }
            }
        }

        return dp[n][m];
    }

    /**
     * Binary Search
     * Time Complexity:
     * Space:
     *
     */
    public int splitArray2(int[] nums, int m) {
        int n = nums.length;
        int max = nums[0];
        int[] subSum = new int[n];
        subSum[0] = nums[0];
        for (int i = 1; i < n; i++) {
            max = Math.max(max, nums[i]);
            subSum[i] = subSum[i - 1] + nums[i];
        }
        int sum = subSum[n - 1];

        if (m == 1) return sum;

        int start = max;
        int end = sum;
        while (start <= end) {
            int mid = start + (end - start) / 2;

            // might cut into less than m parts, then mid is larger than target
            if (isValid(nums, m, mid, subSum)) {
                end = mid - 1;
            } else {
                // mid is smaller than target
                start = mid + 1;
            }
        }

        return start;
    }

    private boolean isValid(int[] nums, int m, int target, int[] subSum) {
        int count = 1;
        int i = -1;
        int total = 0;
        for (int j = 0; j < subSum.length; j++) {
            total = i == -1? subSum[j] : subSum[j] - subSum[i];
            if (total > target) {
                i = j - 1;
                count++;
                if (count > m) return false;
            }
        }

        return true;
    }


    public static void main(String[] args) {
        int[] nums = {7,2,5,10,8};
        System.out.println(new L410SplitArrayLargestSum().splitArray2(nums, 2));

    }
}
