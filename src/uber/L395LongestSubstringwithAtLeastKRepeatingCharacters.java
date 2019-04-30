package uber;

import java.util.HashMap;
import java.util.Map;

public class L395LongestSubstringwithAtLeastKRepeatingCharacters {
    Map<String, Integer> map = new HashMap<>();
    public int longestSubstring(String s, int k) {

        return divideAndConquer(s, 0, s.length(), k);
    }

    private int divideAndConquer(String s, int start, int end, int k) {
        if (end - start < k) return 0;//substring length shorter than k.

        String subStr = s.substring(start, end);
        if (map.containsKey(subStr)) {
            return map.get(subStr);
        }

        int[] countArr = new int[26];
        for (char c : subStr.toCharArray()) {
            countArr[c - 'a']++;
        }

        for (int i = 0; i < subStr.length(); i++) {
            int count = countArr[subStr.charAt(i) - 'a'];
            if (count < k && count > 0) {
                int left = divideAndConquer(s, start, i, k);
                int right = divideAndConquer(s, i + 1, end, k);
                return Math.max(left, right);
            }
        }

        int maxLen = end - start;
        map.put(subStr, maxLen);
        return maxLen;
    }

    public static void main(String[] args) {
        L395LongestSubstringwithAtLeastKRepeatingCharacters s = new L395LongestSubstringwithAtLeastKRepeatingCharacters();

        System.out.println(s.longestSubstring("weitong", 2));
    }
}
