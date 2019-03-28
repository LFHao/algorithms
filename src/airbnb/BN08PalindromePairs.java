package airbnb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://leetcode.com/problems/palindrome-pairs/discuss/79199/150-ms-45-lines-JAVA-solution
 */
public class BN08PalindromePairs {
    public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> res = new ArrayList<>();
        if (words == null || words.length < 2) return res;

        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            map.put(words[i], i);
        }

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            int len = word.length();
            // to make sure even words[i].length() == 0 it can go into the for loop
            for (int j = 0; j <= words[i].length(); j++) {
                String str1 = word.substring(0, j);
                String str2 = word.substring(j, len);

                if (isPalindrome(str1)) {
                    String str2Rev = new StringBuffer(str2).reverse().toString();
                    if (map.containsKey(str2Rev) && map.get(str2Rev) != i) {
                        List<Integer> list = new ArrayList<>();
                        list.add(map.get(str2Rev));
                        list.add(i);
                        res.add(list);
                    }
                }

                if (isPalindrome(str2)) {
                    String str1Rev = new StringBuilder(str1).reverse().toString();
                    // to remove the duplicate
                    if (map.containsKey(str1Rev) && map.get(str1Rev) != i && str2.length()!=0) {
                        List<Integer> list = new ArrayList<>();
                        list.add(map.get(str1Rev));
                        list.add(i);
                        res.add(list);
                    }
                }
            }
        }

        return res;
    }

    private boolean isPalindrome(String word) {
        int start = 0;
        int end = word.length() - 1;

        while (start <= end) {
            if (word.charAt(start) != word.charAt(end)) {
                return false;
            }
            start++;
            end--;
        }

        return true;
    }

    public static void main(String[] args) {
        String[] words = {"a", ""};
        System.out.println(new BN08PalindromePairs().palindromePairs(words));
    }
}
