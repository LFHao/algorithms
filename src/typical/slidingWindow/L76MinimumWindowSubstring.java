package typical.slidingWindow;

import java.util.HashMap;
import java.util.Map;

public class L76MinimumWindowSubstring {
    public String minWindow(String s, String t) {
        Map<Character, Integer> map = new HashMap<>();

        char[] tChars = t.toCharArray();
        for (char c : tChars) {
            // need to use map because there are duplicates
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        int start = 0, end = 0, resStart = 0;
        int count = map.size();
        int min = Integer.MAX_VALUE;

        while (end < s.length()) {
            char endC = s.charAt(end);
            if (map.containsKey(endC)) {
                map.put(endC, map.get(endC) - 1);
                // it means all the characters are found, and numbers found are equal or more than required
                if (map.get(endC) == 0) count--;
            }

            end++;

            while (count == 0) {
                if (end - start < min) {
                    min = end - start;
                    resStart = start;
                }

                char startC = s.charAt(start);
                if (map.containsKey(startC)) {
                    map.put(startC, map.get(startC) + 1);
                    // map.get(c) == 0 means found, > 0 means in the new range this character is missing
                    // map.get(c) < 0 means found is more than required
                    if (map.get(startC) > 0) count++;
                }

                start++;
            }
        }

        return min == Integer.MAX_VALUE? "" : s.substring(resStart, resStart + min);
    }

    public static void main(String[] args) {
        L76MinimumWindowSubstring s = new L76MinimumWindowSubstring();
        System.out.println(s.minWindow("ADOBECODEBANC", "ABC"));
    }
}
