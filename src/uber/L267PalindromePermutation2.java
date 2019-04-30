package uber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class L267PalindromePermutation2 {
    public List<String> generatePalindromes(String s) {
        if (s.length() == 1) return new ArrayList<>(Arrays.asList(s));

        Map<Character, Integer> map = new HashMap<>();
        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        if (map.size() == 1) return new ArrayList<>(Arrays.asList(s));

        List<String> res = new ArrayList<>();
        int count = 0; String x = "";
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            char k = entry.getKey();
            int n = entry.getValue();
            if (n % 2 != 0 && count > 0) {
                return res;
            } else if (n % 2 != 0 && count == 0) {
                count++;
                x += entry.getKey();
            }
            for (int j = 0; j < n / 2; j++) {
                sb.append(k);
            }

        }

        backtrack(sb.toString(), new boolean[sb.length()], new StringBuilder(), res, x);
        return res;
    }

    private void backtrack(String str, boolean[] used, StringBuilder sb, List<String> res, String x) {
        if (sb.length() == str.length()) {
            res.add(sb.toString() + x + sb.reverse().toString());
            sb.reverse();
            return;
        }

        for (int i = 0; i < str.length(); i++) {
            if (used[i]) continue;

            if (i > 0 && str.charAt(i) == str.charAt(i - 1) && used[i - 1]) continue;

            sb.append(str.charAt(i));
            used[i] = true;
            backtrack(str, used, sb, res, x);
            used[i] = false;
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    private String getPalindrome(String str) {
        StringBuilder sb = new StringBuilder();
        int i = str.length() - 1;
        while (i >= 0) {
            sb.append(str.charAt(i--));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        L267PalindromePermutation2 s = new L267PalindromePermutation2();
        System.out.println(s.generatePalindromes("aac"));


    }
}
