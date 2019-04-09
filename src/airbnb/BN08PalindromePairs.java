package airbnb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Question:
 * Given a list of unique words, find all pairs of distinct indices (i, j) in the given list,
 * so that the concatenation of the two words, i.e. words[i] + words[j] is a palindrome.
 *
 * https://leetcode.com/problems/palindrome-pairs/discuss/79199/150-ms-45-lines-JAVA-solution
 *
 */
public class BN08PalindromePairs {
    /**
     * Time Complexity: O(n * L^2): number of words * L (traverse word) * L (check if there is palindrome)
     * @param words
     * @return
     */
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
                    // str2.length() != 0 is to remove the duplicate
                    // if in previous round str1 = "" and str2 = word, already finds the pair A
                    // when str1 = word and str2 = "", it would be the same result as the pair A
                    // so we should remove the duplicate
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

        word = word.toLowerCase();
        while (start < end) {
            if (!Character.isLetterOrDigit(word.charAt(start))) {
                start++;
            } else if(!Character.isLetterOrDigit(word.charAt(end))) {
                end--;
            } else if (word.charAt(start++) != word.charAt(end--)) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        String[] words = {"abcd","dcba","lls","s","sssll"};
        System.out.println(new BN08PalindromePairs().palindromePairs(words));
    }
}
