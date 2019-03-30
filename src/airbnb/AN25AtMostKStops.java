package airbnb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Question:
 * Given a flight itinerary consisting of starting city, destination city, and ticket price (2d list) - find the
 * optimal price flight path to get from start to destination. (A variation of Dynamic Programming Shortest
 * Path)
 *
 * BFS: Dijkstra is not suitable for this question because it needs to match the number of STOP,
 * but not only the minCost. You cannot use PriorityQueue because it will mess up the stop level.
 * So BFS is the most proper solution.
 */
public class AN25AtMostKStops {
    public int minCost(List<String> lines, String source, String target, int k) {
        if (lines == null || lines.size() == 0 || k < 0) {
            return -1;
        }

        Map<String, Flight> map = new HashMap<>();
        getFlightInfo(lines, map);
        Queue<String> queue = new LinkedList<>();
        Queue<Integer> costQueue = new LinkedList<>();
        boolean find = false;
        // set up base - source
        queue.offer(source);
        costQueue.offer(0);
        map.get(source).minCost = 0;
        int stops = 0;

        while (!queue.isEmpty()) {
            // if stops are more than k, we break the loop
            if (stops > k + 1) {
                break;
            }

            int size = queue.size();
            for (int i = 0; i < size; i++) {
                // currCost and curSource is correlated, means in this level of stop, how much does it cost from
                // source
                String curSource = queue.poll();
                Flight curStop = map.get(curSource);
                int currCost = costQueue.poll();
                // update the minimum here to get the accurate number
                curStop.minCost = Math.min(curStop.minCost, currCost);

                // because the variable stops including the destination, so subtract by 1
                if (curSource.equals(target) && stops == k + 1) {
                    find = true;
                    // you should continue instead of break to explore other possibilities
                    continue;
                }

                for (String next : curStop.nextStops.keySet()) {
                    // trim
                    int nextCost = currCost + curStop.nextStops.get(next);
                    if (nextCost < map.get(next).minCost &&
                            (stops < k || (stops == k && next.equals(target)))) {
                        queue.offer(next);
                        costQueue.offer(nextCost);
                    }
                }
            }
            stops++;
        }

        return find ? map.get(target).minCost : -1;
    }

    private void getFlightInfo(List<String> lines, Map<String, Flight> map) {
        for (String line : lines) {
            String[] route = line.split(",");
            String from = route[0].split("->")[0];
            String to = route[0].split("->")[1];
            int price = Integer.valueOf(route[1].trim());
            if (!map.containsKey(from)) map.put(from, new Flight(from));
            if (!map.containsKey(to)) map.put(to, new Flight(to));
            map.get(from).nextStops.put(to, price);
        }
    }

    public static void main(String[] args) {
        AN25AtMostKStops aks = new AN25AtMostKStops();
        List<String> lines = new ArrayList<>();
        lines.add("A->B,250");
        lines.add("A->C,400");
        lines.add("A->D,200");
        lines.add("D->C,100");
        lines.add("B->C,100");
        lines.add("C->D,100");
        lines.add("C->A,10");

        System.out.println(aks.minCost(lines, "A", "D", 0));
        System.out.println(aks.minCost(lines, "A", "D", 1));
        System.out.println(aks.minCost(lines, "A", "D", 2));
        System.out.println(aks.minCost(lines, "A", "C", 1));

        List<String> lines2 = new ArrayList<>();
        lines2.add("A->B,100");
        lines2.add("B->C,100");
        lines2.add("A->C,500");
        System.out.println(aks.minCost(lines2, "A", "C", 1));
    }
}

class Flight {
    String from;
    int minCost;
    // mapping: next stop and price
    Map<String, Integer> nextStops;

    Flight(String from) {
        this.from = from;
        this.minCost = Integer.MAX_VALUE;
        this.nextStops = new HashMap<>();
    }
}
