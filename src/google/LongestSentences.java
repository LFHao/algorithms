package google;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LongestSentences {
    int max = 0;
    public List<String> longestSentences(List<String> sentences) {
        Map<String, List<Integer>> startMap = new HashMap<>();
        Map<String, List<Integer>> endMap = new HashMap<>();
        for (int i = 0; i < sentences.size(); i++) {
            String s = sentences.get(i);
            String[] words = s.split(" ");
            List<Integer> startList = startMap.getOrDefault(words[0], new ArrayList<>());
            startList.add(i);
            startMap.put(words[0], startList);
            List<Integer> endList = endMap.getOrDefault(words[words.length - 1], new ArrayList<>());
            endList.add(i);
            endMap.put(words[words.length - 1], endList);
        }

        Set<String> startSet = new HashSet<>();
        for (String startWord : startMap.keySet()) {
            if (!endMap.keySet().contains(startWord)) {
                startSet.add(startWord);
            }
        }

        List<String> res = new ArrayList<>();
        for (String word : startSet) {
            for (int index : startMap.get(word)) {
                dfs(sentences, startMap, index, new ArrayList<>(), res);
            }
        }

        return res;
    }

    private void dfs(List<String> sentences, Map<String, List<Integer>> startMap, int i, List<String> list, List<String> res) {
        String s = sentences.get(i);
        String[] words = s.split(" ");
        String lastWord = words[words.length - 1];
        list.add(s);
        if (!startMap.containsKey(lastWord)) {
            if (list.size() > max) {
                max = list.size();
                res.clear();
                res.addAll(list);
            }

            return;
        }

        if (startMap.containsKey(lastWord)) {
            for (int next : startMap.get(lastWord)) {
                dfs(sentences, startMap, next, list, res);
            }
            list.remove(list.size() - 1);
        }

    }

    public static void main(String[] args) {
        List<String> sentences = new ArrayList<>();
        sentences.add("I like dog");
        sentences.add("who am I");
        sentences.add("dog is cute");
        sentences.add("dog is better than cat");
        sentences.add("cat meow");
        sentences.add("where should be I");
        sentences.add("which dog");
        sentences.add("where am I");
        sentences.add("cute animal");
        System.out.println(new LongestSentences().longestSentences(sentences));
    }
}

