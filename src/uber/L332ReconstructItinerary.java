package uber;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Leetcode #332 https://leetcode.com/problems/reconstruct-itinerary/
 * Hierholzer's algorithm to find a Eulerian path in the graph
 * Time Complexity: O(E) - edge
 */
public class L332ReconstructItinerary {
    Map<String, PriorityQueue<String>> map;
    LinkedList<String> res;

    public List<String> findItinerary(String[][] tickets) {
        map = new HashMap<>();
        res = new LinkedList<>();

        if (tickets == null || tickets.length == 0) return res;

        for (String[] iti : tickets) {
            map.putIfAbsent(iti[0], new PriorityQueue<>());
            map.get(iti[0]).add(iti[1]);
        }

        String cur = "JFK";
        dfs(cur);
        return res;
    }

    private void dfs(String port) {
        PriorityQueue<String> next = map.get(port);
        while (next != null && !next.isEmpty()) {
            dfs(next.poll());
        }

        res.addFirst(port);
    }

    public static void main(String[] args) {
        L332ReconstructItinerary r = new L332ReconstructItinerary();
        String[][] tickets = {{"MUC", "LHR"}, {"JFK", "MUC"}, {"SFO", "SJC"}, {"LHR", "SFO"}};
        System.out.println(r.findItinerary(tickets));
        String[][] tickets2 = {{"JFK","SFO"},{"JFK","ATL"},{"SFO","ATL"},{"ATL","JFK"},{"ATL","SFO"}};
        System.out.println(r.findItinerary(tickets2));

        String[][] tickets3 = {{"JFK","KUL"},{"JFK","NRT"},{"NRT","JFK"}};
        System.out.println(r.findItinerary(tickets3));

    }
}
