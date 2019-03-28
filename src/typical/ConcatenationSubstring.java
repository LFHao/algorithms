package typical;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConcatenationSubstring {
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> res = new ArrayList<>();
        // edge case
        if (words == null || words.length == 0) return res;

        int len = words[0].length();
        int index = 0;
        Map<String, Integer> map = new HashMap<>();
        int totalCount = words.length * len;
        for (String word : words) {
            if (map.containsKey(word)) {
                map.put(word, map.get(word) + 1);
            } else {
                map.put(word, 1);
            }
        }

        while (index < s.length() - totalCount + 1) {
            Map<String, Integer> operateMap = new HashMap<>();

            int wordCount = 0;
            while (wordCount < words.length) {
                String curWord = s.substring(index + wordCount * len, index + (wordCount + 1) * len);
                if (map.containsKey(curWord)) {
                    operateMap.put(curWord, operateMap.getOrDefault(curWord, 0) - 1);
                    if (operateMap.get(curWord) > map.getOrDefault(curWord, 0)) {
                        break;
                    }
                } else {
                    break;
                }
                wordCount++;
            }

            if (wordCount == words.length) {
                res.add(index);
            }
            index++;

        }

        return res;

    }

    public static void main(String[] args) {
        ConcatenationSubstring cs = new ConcatenationSubstring();
        String[] words = {"foo","bar"};
        System.out.println(cs.findSubstring("barfoothefoobarman", words));
    }
}
