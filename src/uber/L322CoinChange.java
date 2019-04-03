package uber;

import java.util.Arrays;

/**
 * Time: O(mn) Space: O(m)
 */
public class L322CoinChange {
    /**
     * Coin Change
     * @param coins
     * @param amount
     * @return
     */
    public int coinChange(int[] coins, int amount) {
        if (coins == null || coins.length == 0 || amount < 0) return -1;
        if (amount == 0) return 0;

        int[] dp = new int[amount + 1];
        // since we'll use Math.min to compare, we must fill this array with amount + 1 which larger than amount
        // Do not use Integer.MAX_VALUE because below we will need to +1, that will lead to overflow
        Arrays.fill(dp, amount + 1);
        // set base
        dp[0] = 0;
        for (int i = 1; i <= amount; i++) {
            for (int j = 0; j < coins.length; j++) {
                if (i >= coins[j]) {
                    dp[i] = Math.min(dp[i], dp[i - coins[j]]) + 1;
                }
            }
        }

        return dp[amount] > amount ? -1 : dp[amount];
    }

    /**
     * Coin Change 2
     * @param amount
     * @param coins
     * @return
     */
    public int change(int amount, int[] coins) {
        if (amount < 0 || coins == null) return -1;

        int[] dp = new int[amount + 1];
        dp[0] = 1;

        //    Please note that the outer loop should be about coins, while the inner loop should be about amount. Or else, there may be duplicates in the result, e.g. for input: amount = 5, coins = [1, 2, 5], it counts [2, 2, 1] and [2, 1, 2] as two different combinations, so it will return 9 rather than 5. All in all, the order of coins doesn't matterin this case, so we set it as the outer loop.
        // we go through the array coin by coin to avoid duplicates
        for (int i = 0; i < coins.length; i++) {
            for (int j = 1; j <= amount; j++) {
                if (j >= coins[i]) {
                    dp[j] += dp[j - coins[i]];
                }
            }
        }

        return dp[amount];
    }

    public static void main(String[] args) {
        L322CoinChange cc = new L322CoinChange();
        int[] coins = {1, 2, 5};
        System.out.println(cc.coinChange(coins, 11));
    }
}
