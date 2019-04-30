package typical.slidingWindow;

import java.util.HashMap;
import java.util.Map;

public class L424LongestRepeatingCharacterReplacement {

    public int characterReplacement(String s, int k) {
        if (s == null || s.length() == 0) return 0;

        int start = 0, end = 0;
        int max = 0, dominantCount = 0;
        int totalCount = 0;
        Map<Character, Integer> map = new HashMap<>();

        while (end < s.length()) {
            char endC = s.charAt(end);
            map.put(endC, map.getOrDefault(endC, 0) + 1);
            totalCount++;
            dominantCount = Math.max(dominantCount, map.get(endC));
            end++;

            while (totalCount - dominantCount > k) {
                char startC = s.charAt(start);
                map.put(startC, map.getOrDefault(startC, 0) - 1);
                totalCount--;
                dominantCount = Math.max(dominantCount, map.get(startC));
                start++;
            }
            max = Math.max(max, end - start);
        }

        return max;
    }

    public static void main(String[] args) {
        L424LongestRepeatingCharacterReplacement s = new L424LongestRepeatingCharacterReplacement();
        System.out.println(s.characterReplacement("AAAB", 0));
        System.out.println(s.characterReplacement("AABABBA", 1));
    }
}
