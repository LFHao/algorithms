package google;


public class L410SplitArrayLargestSum {
    /**
     * Time complexity : O(n^2 * m). The total number of states is O(n * m). To compute each state f[i][j], we need to go through the
     *  whole array to find the optimum k. This requires another O(n) loop. So the total time complexity is O(n ^ 2 * m)
     *
     * Space complexity : O(n * m). The space complexity is equivalent to the number of states, which is O(n * m)
     * @param nums
     * @param m
     * @return
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

    public static void main(String[] args) {
        int[] nums = {7,2,5,10,8};
        System.out.println(new L410SplitArrayLargestSum().splitArray(nums, 2));

    }
}
