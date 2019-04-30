package typical.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class L127WordLadder {
    // Single Direction BSF
    // Time: O(n * 26^l), n = wordList.length, l = word.length()
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Queue<String> queue = new LinkedList<>();
        Set<String> set = new HashSet<>(wordList);
        queue.offer(beginWord);

        int level = 1;

        while(!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                String curWord = queue.poll();

                if (curWord.equals(endWord)) {
                    return level;
                }

                for (int j = 0; j < curWord.length(); j++) {
                    char[] charArr = curWord.toCharArray();
                    for (char c = 'a'; c <= 'z'; c++) {
                        charArr[j] = c;

                        String newWord = new String(charArr);
                        if (!newWord.equals(beginWord) && set.contains(newWord)) {
                            set.remove(newWord);
                            queue.offer(newWord);
                        }
                    }
                }
            }
            level++;
        }

        return 0;
    }

    // Bi-Direction BSF
    // Time: O(n * 26^1/2), Space: O(n)
    public int ladderLength2(String beginWord, String endWord, List<String> wordList) {
        Set<String> beginSet = new HashSet<>();
        Set<String> endSet = new HashSet<>();
        Set<String> wordSet = new HashSet<>(wordList);

        if (!wordSet.contains(endWord)) return 0;

        beginSet.add(beginWord);
        endSet.add(endWord);

        int level = 1;
        while (!beginSet.isEmpty() && !endSet.isEmpty()) {
            if (beginSet.size() > endSet.size()) {
                Set<String> tmp = beginSet;
                beginSet = endSet;
                endSet = tmp;
            }

            // this tmpSet only stores words in 1 level
            Set<String> tmp = new HashSet<>();
            for (String s : beginSet) {
                char[] sChars = s.toCharArray();
                for (int i = 0; i < s.length(); i++) {
                    char origin = sChars[i];

                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == origin) continue;

                        sChars[i] = c;
                        String transformString = String.valueOf(sChars);

                        // return level + 1, because level haven't been incremented in this level
                        if (endSet.contains(transformString)) return level + 1;

                        if (wordSet.contains(transformString)) {
                            wordSet.remove(transformString);
                            tmp.add(transformString);
                        }
                    }
                    sChars[i] = origin;

                }
            }
            // update beginSet to this level
            beginSet = tmp;
            level++;
        }

        return 0;

    }

    public static void main(String[] args) {
        L127WordLadder wl = new L127WordLadder();
        List<String> wordList = new ArrayList<>(Arrays.asList("hot","dot","dog","lot","log", "cog"));
//        System.out.println(wl.ladderLength("hit", "cog", wordList));
        System.out.println(wl.ladderLength2("hit", "cog", wordList));

    }

}
