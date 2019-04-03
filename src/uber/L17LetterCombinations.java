package uber;

import java.util.ArrayList;
import java.util.List;

public class L17LetterCombinations {
    public List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        if (digits == null || digits.length() == 0) return res;

        String[] dict = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        backtrack(digits, dict, res, new StringBuilder(), 0);
        return res;
    }

    private void backtrack(String digits, String[] dict, List<String> res, StringBuilder sb, int index) {
        if (index == digits.length()) {
            res.add(sb.toString());
            return;
        }

        String letters = dict[digits.charAt(index) - '0'];
        for (int i = 0; i < letters.length(); i++) {
            sb.append(letters.charAt(i));
            backtrack(digits, dict, res, sb, index + 1);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}
