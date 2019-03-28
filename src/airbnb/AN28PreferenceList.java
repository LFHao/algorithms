package airbnb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Topological sort
 */
public class AN28PreferenceList {
    public List<Integer> getPreference(List<List<Integer>> preferences) {
        List<Integer> res = new ArrayList<>();
        if (preferences == null || preferences.size() == 0) return res;

        Map<Integer, Set<Integer>> graph = new HashMap<>();
        Map<Integer, Integer> indegrees = new HashMap<>();

        buildGraph(preferences, graph, indegrees);
        topoSort(graph, indegrees, res);

        return res;
    }

    // build graph
    private void buildGraph(List<List<Integer>> preferences, Map<Integer, Set<Integer>> graph, Map<Integer, Integer> indegrees) {
        for (List<Integer> list : preferences) {
            for (int num : list) {
                graph.put(num, new HashSet<>());
                indegrees.put(num, 0);
            }
        }

        for (List<Integer> list : preferences) {
            for (int i = 0; i < list.size() - 1; i++) {
                if (!graph.get(list.get(i)).contains(graph.get(list.get(i + 1)))) {
                    graph.get(list.get(i)).add(list.get(i + 1));
                    indegrees.put(list.get(i + 1), indegrees.get(list.get(i + 1)) + 1);
                }
            }
        }
    }

    private void topoSort(Map<Integer, Set<Integer>> graph, Map<Integer, Integer> indegrees, List<Integer> res) {
        Queue<Integer> queue = new LinkedList<>();
        for (int i : indegrees.keySet()) {
            if (indegrees.get(i) == 0) {
                queue.offer(i);
            }
        }

        while(!queue.isEmpty()) {
            int preference = queue.poll();
            res.add(preference);

            for (int next : graph.get(preference)) {
                indegrees.put(next, indegrees.get(next) - 1);
                if (indegrees.get(next) == 0) {
                    queue.offer(next);
                }
            }
        }
    }

    public List<Integer> getPreference2(List<List<Integer>> preferences) {
        Map<Integer, Integer> inDegree = new HashMap<>();
        Map<Integer, Set<Integer>> nodes = new HashMap<>();
        for (List<Integer> l : preferences) {
            for (int i = 0; i < l.size() - 1; i++) {
                int from = l.get(i);
                int to = l.get(i + 1);
                if (!nodes.containsKey(from)) {
                    inDegree.put(from, 0);
                    nodes.put(from, new HashSet<>());
                }
                if (!nodes.containsKey(to)) {
                    inDegree.put(to, 0);
                    nodes.put(to, new HashSet<>());
                }
                if (!nodes.get(from).contains(to)) {
                    Set<Integer> s = nodes.get(from);
                    s.add(to);
                    nodes.put(from, s);
                }
                inDegree.put(to, inDegree.getOrDefault(to, 0) + 1);
            }
        }
        Queue<Integer> q = new LinkedList<>();
        for (int k : inDegree.keySet()) {
            if (inDegree.get(k) == 0) {
                q.offer(k);
            }
        }
        List<Integer> res = new ArrayList<>();
        while (!q.isEmpty()) {
            int id = q.poll();
            res.add(id);
            Set<Integer> neighbors = nodes.get(id);
            for (int next : neighbors) {
                int degree = inDegree.get(next) - 1;
                inDegree.put(next, degree);
                if (degree == 0) q.offer(next);
            }
        }
        return res;
    }


    public static void main(String[] args) {
        AN28PreferenceList pl = new AN28PreferenceList();

        List<List<Integer>> preferences = new ArrayList<>();
        List<Integer> p1 = new ArrayList<Integer>() {{
            add(2);
            add(3);
            add(5);
        }};
        List<Integer> p2 = new ArrayList<Integer>() {{
            add(4);
            add(2);
            add(1);
        }};
        List<Integer> p3 = new ArrayList<Integer>() {{
            add(4);
            add(1);
            add(5);
            add(6);
        }};
        List<Integer> p4 = new ArrayList<Integer>() {{
            add(4);
            add(7);
        }};
        preferences.add(p1);
        preferences.add(p2);
        preferences.add(p3);
        preferences.add(p4);
        System.out.println(pl.getPreference(preferences));
        System.out.println(pl.getPreference2(preferences));

        p1 = new ArrayList<Integer>() {{
            add(3);
            add(5);
            add(7);
            add(9);
        }};
        p2 = new ArrayList<Integer>() {{
            add(2);
            add(3);
            add(8);
        }};
        p3 = new ArrayList<Integer>() {{
            add(5);
            add(8);
        }};
        preferences = new ArrayList<>();
        preferences.add(p1);
        preferences.add(p2);
        preferences.add(p3);

        System.out.println(pl.getPreference(preferences));
        System.out.println(pl.getPreference2(preferences));

    }

}
