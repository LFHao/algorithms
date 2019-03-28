package airbnb;

import java.util.ArrayList;
import java.util.List;

/**
 * https://www.lintcode.com/problem/k-edit-distance/description
 * It will TimeOut and memory if use the dp[m+1][n+1] matrix
 * Replace with roll-over matrix to 1D array and 1 var to record
 * https://leetcode.com/problems/edit-distance/discuss/25969/My-clean-java-solution-with-O(n)-space-in-17-lines
 *
 */
public class N23KEditDistance {
    public List<String> kDistance(String[] words, String target, int k) {
        List<String> res = new ArrayList<>();
        if (target == null || words == null || words.length == 0 || k < 0) return res;
        for(String word : words) {
            if (getEditDistance(word, target) <= k) {
                res.add(word);
            }
        }
        return res;
    }

    private int getEditDistance(String word, String target) {
        int m = word.length();
        int n = target.length();

        int[] dp = new int[n + 1];

        // set base condition
        // for word1 without length but word2 with, it takes i steps so add
        for (int j = 0; j <= n; j++) {
            dp[j] = j;
        }

        // pre is dp[i][j-1]
        // dp[j-1] is dp[i-1][j-1]
        // dp[j] is dp[i-1][j]
        for (int i = 1; i <= m; i++) {
            // for every row, pre is dp[i][0] which is i
            int pre = i;
            for (int j = 1; j <= n; j++) {
                int cur;
                if (word.charAt(i - 1) == target.charAt(j - 1)) {
                    // no operations needed on top of (i-1, j-1)
                    cur = dp[j - 1];
                } else {
                    cur = Math.min(Math.min(
                            // insert j(based 0 on word)th char of word2 to word1 to match
                            pre,
                            // delete i(based 0 on word)th char of word1 to match
                            dp[j]),
                            // replace i(based 0)th char of word1 with jth char of word2
                            dp[j - 1]) + 1;
                }
                // update dp[j-1] to be dp[i][j-1] since it will never be used in dp[i][j+1]
                // and it will be used in dp[i+1][j]
                dp[j - 1] = pre;
                // update pre to be current for dp[i][j+1] use
                pre = cur;
            }
            dp[n] = pre;
        }

        return dp[n];
    }

    public static void main(String[] args) {
        String[] words = {"abc","abd","abcd","adc"};
        String target = "ac";
        N23KEditDistance solution = new N23KEditDistance();
        System.out.println(solution.kDistance(words, target, 1));
    }
}
