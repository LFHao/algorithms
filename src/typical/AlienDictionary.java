package typical;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Topological sort
 */
public class AlienDictionary {

    /**
     * @param words: a list of words
     * @return: a string which is correct order
     */
    public String alienOrder(String[] words) {
        if (words == null || words.length == 0) return "";

        // build directed graph and indegrees using 2 maps
        Map<Character, Set<Character>> graph = new HashMap<>();
        Map<Character, Integer> indegrees = new HashMap<>();
        buildGraph(words, graph, indegrees);

        // topological sort
        StringBuilder sb = new StringBuilder();
        return topoSort(graph, indegrees, sb) ? sb.toString() : "";
    }

    private void buildGraph(String[] words, Map<Character, Set<Character>> graph, Map<Character, Integer> indegrees) {
        for (String word : words) {
            for (char c : word.toCharArray()) {
                graph.put(c, new HashSet<>());
                indegrees.put(c, 0);
            }
        }

        for (int i = 1; i < words.length; i++) {
            String pre = words[i - 1];
            String cur = words[i];
            for (int j = 0; j < Math.min(pre.length(), cur.length()); j++) {
                char preChar = pre.charAt(j);
                char curChar = cur.charAt(j);
                if (preChar != curChar) {
                    if (!graph.get(preChar).contains(curChar)) {
                        graph.get(preChar).add(curChar);
                        indegrees.put(curChar, indegrees.getOrDefault(curChar, 0) + 1);
                    }
                    // this means if 1 diff found, we don't and can't compare the following chars
                    break;
                }
            }
        }
    }

    private boolean topoSort(Map<Character, Set<Character>> graph, Map<Character, Integer> indegrees, StringBuilder sb) {
        Queue<Character> queue = new LinkedList<>();
        for (Character c : indegrees.keySet()) {
            if (indegrees.get(c) == 0) {
                queue.offer(c);
            }
        }

        while (!queue.isEmpty()) {
            char c = queue.poll();
            sb.append(c);

            Set<Character> nextSet = graph.get(c);
            for (Character next : nextSet) {
                indegrees.put(next, indegrees.get(next) - 1);
                if (indegrees.get(next) == 0) {
                    queue.offer(next);
                }
            }

        }

        return indegrees.entrySet().stream().filter(e -> e.getValue() > 0).collect(Collectors.toList()).isEmpty();

    }

    public static void main(String[] args) {
        String[] words = {  "z", "z"};
        System.out.println(new AlienDictionary().alienOrder(words));
    }
}
