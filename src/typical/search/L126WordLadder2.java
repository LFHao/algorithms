package typical.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class L126WordLadder2 {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> res = new ArrayList<>();
        if (wordList == null || wordList.size() == 0) return res;

        Map<String, List<String>> nodeMap = new HashMap<>();
        boolean canFind = bfs(wordList, beginWord, endWord, nodeMap);

        if (canFind) {
            backtrack(beginWord, endWord, nodeMap, res, new ArrayList<>());
        }

        return res;
    }

    private boolean bfs(List<String> wordList, String beginWord, String endWord, Map<String, List<String>> map) {
        Set<String> wordSet = new HashSet<>(wordList);
        boolean isFound = false;

        if (!wordSet.contains(endWord)) return isFound;
        Set<String> beginSet = new HashSet<>();
        Set<String> endSet = new HashSet<>();
        beginSet.add(beginWord);
        endSet.add(endWord);
        boolean forward = true;

        while (!beginSet.isEmpty() && !endSet.isEmpty() && !isFound) {
            if (beginSet.size() > endSet.size()) {
                Set<String> tmp = beginSet;
                beginSet = endSet;
                endSet = tmp;
                forward = !forward;
            }

            for (String word : beginSet) {
                wordSet.remove(word);
            }
            // endWord is not in the endSet
            for (String word : endSet) {
                wordSet.remove(word);
            }

            Set<String> temp = new HashSet<>();
            for (String s : beginSet) {
                char[] sChars = s.toCharArray();
                for (int i = 0; i < sChars.length; i++) {
                    char origin = sChars[i];
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == origin) continue;
                        sChars[i] = c;
                        String transformString = String.valueOf(sChars);

                        String parent = forward? s : transformString;
                        String child = forward? transformString : s;

                        if (endSet.contains(transformString)) {
                            isFound = true;
                            map.putIfAbsent(parent, new ArrayList<>());
                            map.get(parent).add(child);
                        } else if (wordSet.contains(transformString) && !isFound) {
                            temp.add(transformString);
                            map.putIfAbsent(parent, new ArrayList<>());
                            map.get(parent).add(child);
                        }
                    }
                    sChars[i] = origin;

                }
            }
            beginSet = temp;
        }

        return isFound;

    }

    private void backtrack(String beginWord, String endWord, Map<String, List<String>> nodeMap, List<List<String>> res, List<String> list) {
        list.add(beginWord);
        if (beginWord.equals(endWord)) {
            res.add(new ArrayList<>(list));
            return;
        }

        if (!nodeMap.containsKey(beginWord)) return;

        for (String next : nodeMap.get(beginWord)) {
            backtrack(next, endWord, nodeMap, res, list);
            list.remove(list.size() - 1);
        }
    }

    public static void main(String[] args) {
        L126WordLadder2 s = new L126WordLadder2();
        List<String> wordList = new ArrayList<>(Arrays.asList(new String[]{"hot","dot","dog","lot","log","cog"}));
        System.out.println(s.findLadders("hit", "cog", wordList));
    }
}
