package airbnb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * BFS
 */
public class AN25AtMostKStops {
    public int minCost(List<String> lines, String source, String target, int k) {
        if (lines.size() == 0 || k < 0) {
            return -1;
        }

        Map<String, List<Flight>> map = new HashMap<>();
        getFlightInfo(lines, map);
        Queue<String> queue = new LinkedList<>();
        Queue<Integer> costQueue = new LinkedList<>();
        queue.offer(source);
        costQueue.offer(0);
        int minCost = Integer.MAX_VALUE;
        int stops = 0;

        while (!queue.isEmpty()) {
            // if stops are more than k, we break the loop
            if (stops > k + 1) {
                break;
            }

            int size = queue.size();

            for (int i = 0; i < size; i++) {
                // currCost and curSource is correlated
                String curSource = queue.poll();
                int currCost = costQueue.poll();

                // because the variable stops including the destination, so subtract by 1
                if (curSource.equals(target) && stops == k + 1) {
                    minCost = Math.min(minCost, currCost);
                    continue;
                }

                if (!map.containsKey(curSource)) {
                    continue;
                }

                for (Flight f : map.get(curSource)) {
                    queue.offer(f.to);
                    costQueue.offer(currCost + f.price);
                }
            }
            stops++;
        }

        return minCost == Integer.MAX_VALUE? -1 : minCost;
    }

    private void getFlightInfo(List<String> lines, Map<String, List<Flight>> map) {
        for (String line : lines) {
            String[] route = line.split(",");
            String from = route[0].split("->")[0];
            String to = route[0].split("->")[1];
            int price = Integer.valueOf(route[1]);
            List<Flight> list = map.getOrDefault(from, new ArrayList<>());
            list.add(new Flight(from, to, price));
            map.put(from, list);
        }
    }

    public static void main(String[] args) {
        AN25AtMostKStops aks = new AN25AtMostKStops();
        List<String> lines = new ArrayList<>();
        lines.add("A->B,100");
        lines.add("A->C,400");
        lines.add("B->C,100");
        lines.add("C->D,100");
        lines.add("C->A,10");

        System.out.println(aks.minCost(lines, "A", "D", 0));
        System.out.println(aks.minCost(lines, "A", "D", 1));
        System.out.println(aks.minCost(lines, "A", "D", 2));
    }
}

class Flight {
    String from;
    String to;
    int price;

    Flight(String from, String to, int price) {
        this.from = from;
        this.to = to;
        this.price = price;
    }
}
