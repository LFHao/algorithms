package google;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class L890FindAndReplacePattern {
    public List<String> findAndReplacePattern(String[] words, String pattern) {
        List<String> res = new ArrayList<>();
        if (pattern == null || words == null || words.length == 0) return res;
        Map<Character, Character> patternMap = new HashMap<>();
        Map<Character, Character> wordMap = new HashMap<>();

        for (String word : words) {
            if (word.length() != pattern.length()) continue;
            for (int i = 0; i < word.length(); i++) {
                if (patternMap.containsKey(pattern.charAt(i))) {
                    if (patternMap.get(pattern.charAt(i)) != word.charAt(i)) break;
                } else {
                    if (wordMap.containsKey(word.charAt(i))) break;
                    patternMap.putIfAbsent(pattern.charAt(i), word.charAt(i));
                    wordMap.putIfAbsent(word.charAt(i), pattern.charAt(i));
                }
                if (i == word.length() - 1) res.add(word);
            }
            patternMap.clear();
            wordMap.clear();
        }

        return res;
    }

    public static void main(String[] args) {
        String[] words = {"abc","deq","mee","aqq","dkd","ccc"};
        System.out.println(new L890FindAndReplacePattern().findAndReplacePattern(words, "abb"));
    }
}
