package google;

public class L62UniquePaths {
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (i == 1 && j == 1) {
                    dp[i][j] = 1;
                    continue;
                }
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }

        return dp[m][n];
    }

    public int uniquePaths1D(int m, int n) {
        int[] dp = new int[n + 1];
        dp[1] = 1;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (i == 1 && j == 1) {
                    dp[j] = 1;
                    continue;
                }
                dp[j] += dp[j - 1];
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        L62UniquePaths s = new L62UniquePaths();
        System.out.println(s.uniquePaths(3, 2));
        System.out.println(s.uniquePaths(7, 3));

        System.out.println(s.uniquePaths1D(3, 2));
        System.out.println(s.uniquePaths1D(7, 3));
    }
}
