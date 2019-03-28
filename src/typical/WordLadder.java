package typical;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class WordLadder {
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

    public static void main(String[] args) {
        WordLadder wl = new WordLadder();
        List<String> wordList = new ArrayList<>(Arrays.asList("hot","dot","dog","lot","log","cog"));
        System.out.println(wl.ladderLength("hit", "cog", wordList));
    }

}
