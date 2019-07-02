package typical.graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class L743NeworkDelayTime {
    // Dijkstra. Time: O(NlogN + E). Space: O(N + E)
    public int networkDelayTime(int[][] times, int N, int K) {
        Map<Integer, List<int[]>> timesMap = new HashMap<>();
        Map<Integer, Node> nodeMap = new HashMap<>();
        for (int[] time : times) {
            timesMap.putIfAbsent(time[0], new ArrayList<>());
            timesMap.get(time[0]).add(new int[] {time[1], time[2]});
            nodeMap.putIfAbsent(time[0], new Node(time[0]));
            nodeMap.putIfAbsent(time[1], new Node(time[1]));
        }
        if (nodeMap.size() != N) return -1;

        int[] prev = new int[N + 1];
        for (int i = 1; i <= N; i++) prev[i] = i;
        boolean[] visited = new boolean[N + 1];

        Queue<Node> queue = new PriorityQueue<>(Comparator.comparing(Node::getTime));
        Node kNode = nodeMap.get(K);
        kNode.time = 0;
        queue.offer(kNode);

        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            visited[cur.id] = true;
            if (timesMap.containsKey(cur.id)) {
                List<int[]> dest = timesMap.get(cur.id);
                for (int[] e : dest) {
                    if (!visited[e[0]]) {
                        Node next = nodeMap.get(e[0]);
                        if (cur.time + e[1] < next.time) {
                            prev[next.id] = cur.id;
                            queue.remove(next);
                            next.time = cur.time + e[1];
                            queue.offer(next);
                        }
                    }
                }
            }
        }

        // it start to reach all nodes at the same time
        int res = 0;
        for (Map.Entry<Integer, Node> entry : nodeMap.entrySet()) {
            if (entry.getValue().time == Integer.MAX_VALUE) return -1;
            res = Math.max(res, entry.getValue().time);
        }

        return res;

    }

    class Node {
        int id;
        int time;
        Node(int id) {
            this.id = id;
            this.time = Integer.MAX_VALUE;
        }

        int getTime() {return time;}
    }
}
