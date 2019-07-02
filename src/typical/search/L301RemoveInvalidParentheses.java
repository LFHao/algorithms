package typical.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class L301RemoveInvalidParentheses {
    public List<String> removeInvalidParentheses(String s) {
        List<String> res = new ArrayList<>();
        if (s == null || s.length() == 0) return res;

        // count how many left and how many right should be removed
        int left = 0, right = 0;
        // like interval.start and end ->
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') left++;
            else if (c == ')') {
                if (left == 0) {
                    right++;
                } else {
                    left--;
                }
            }
        }

        // dfs to remove
        dfs(left, right, 0, s, res);

        return res;
    }

    private void dfs(int left, int right, int start, String s, List<String> res) {
        if (left == 0 && right == 0 ) {
            if (isValid(s))
                res.add(s);
            return;
        }

        for (int i = start; i < s.length(); i++) {
            char c = s.charAt(i);
            if (i != start && c == s.charAt(i - 1)) continue;

            if (c == '(' || c == ')') {
                StringBuilder sb = new StringBuilder(s);
                if (right > 0 && c == ')') {
                    sb.deleteCharAt(i);
                    dfs(left, right - 1, i, sb.toString(), res);
                } else if (left > 0 && c == '(') {
                    sb.deleteCharAt(i);
                    dfs(left - 1, right, i, sb.toString(), res);
                }

            }

        }

    }

    // isValid parentheses
    private boolean isValid(String s) {
        int count = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') count++;
            if (c == ')') count--;
            if (count < 0) return false;
        }

        return count == 0;
    }

    public static void main(String[] args) {
        String s = "(a)())()";
        L301RemoveInvalidParentheses a = new L301RemoveInvalidParentheses();
//        System.out.println(a.removeInvalidParentheses(s));

        Map<String, String> map = Collections.emptyMap();


        System.out.println(map.get("account"));



    }
}
