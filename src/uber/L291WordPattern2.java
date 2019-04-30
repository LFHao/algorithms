package uber;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Input: pattern = "abab", str = "redblueredblue"
 * Output: true
 *
 * Input: pattern = pattern = "aaaa", str = "asdasdasdasd"
 * Output: true
 *
 */
public class L291WordPattern2 {
    public boolean wordPatternMatch(String pattern, String str) {
        Map<Character, String> patternToStr = new HashMap<>();
        // can use only a set to store the mapping from str to patter, if duplicate exists, then it means not match
        Set<String> strToPattern = new HashSet<>();

        return isMatch(pattern, 0, str, 0, patternToStr, strToPattern);

    }

    private boolean isMatch(String pattern, int pIndex, String str, int sIndex, Map<Character, String> patternToStr, Set<String> strToPattern) {
        if (pIndex == pattern.length() && sIndex == str.length()) return true;
        if (pIndex == pattern.length() || sIndex == str.length()) return false;

        char pChar = pattern.charAt(pIndex);
        if (patternToStr.containsKey(pChar)) {
            String strMatch = patternToStr.get(pChar);

            // if this str substring is not starts with strMatch, we return
            if (!str.startsWith(strMatch, sIndex)) return false;

            // if it matches, continue with next char in pattern and remaining substring in str
            return isMatch(pattern, pIndex + 1, str, sIndex + strMatch.length(), patternToStr, strToPattern);

        } else {
            for (int i = sIndex + 1; i <= str.length(); i++) {
                String s = str.substring(sIndex, i);

                // if other patterns have matched to this s previously, it means not match
                // continue with search
                if (strToPattern.contains(s)) {
                    continue;
                }

                patternToStr.put(pChar, s);
                strToPattern.add(s);

                // to see if the remaining matches
                if (isMatch(pattern, pIndex + 1, str, i, patternToStr, strToPattern)) return true;

                // backtrack we remove the previous (no use) results
                patternToStr.remove(pChar);
                strToPattern.remove(s);
            }

        }

        // if until here we do not have any true result, return false;
        return false;
    }

    public static void main(String[] args) {
        L291WordPattern2 s = new L291WordPattern2();
        System.out.println(s.wordPatternMatch("abab", "redblueredblue"));
    }

}
