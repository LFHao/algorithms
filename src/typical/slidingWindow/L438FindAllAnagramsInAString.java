package typical.slidingWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class L438FindAllAnagramsInAString {
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<>();

        if (s == null || s.length() == 0 || s.length() < p.length()) return res;

        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < p.length(); i++) {
            char c = p.charAt(i);
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        int count = map.size();

        int start = 0, end = 0;
        while (end < s.length()) {
            char endC = s.charAt(end);
            if (map.containsKey(endC)) {
                map.put(endC, map.get(endC) - 1);
                if (map.get(endC) == 0) count--;
            }
            end++;

            while (count == 0) {
                char startC = s.charAt(start);

                if (map.containsKey(startC)) {
                    map.put(startC, map.get(startC) + 1);
                    if (map.get(startC) > 0) count++;
                }

                if (end - start == p.length()) {
                    res.add(start);
                }

                start++;
            }
        }

        return res;
    }

    public static void main(String[] args) {
        L438FindAllAnagramsInAString s = new L438FindAllAnagramsInAString();
        System.out.println(s.findAnagrams("aa", "bb"));
    }
}
