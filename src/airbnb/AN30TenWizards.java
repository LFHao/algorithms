package airbnb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Dijkstra
 *
 * Question:
 * There are 10 wizards, 0-9, you are given a list that each entry is a list of wizards known by wizard. Define
 * the cost between wizards and wizard as square of different of i and j. To find the min cost between 0
 * and 9.
 * wizard[0] list: 1, 4, 5 wizard[4] list: 9 wizard 0 to 9 min distance is (0-4)^2+(4-9)^2=41 (wizard[0]
 * ->wizard[4]->wizard[9])
 *
 */
public class AN30TenWizards {
    public List<Integer> getShortestPath(List<List<Integer>> wizards, int source, int target) {
        List<Integer> res = new ArrayList<>();

        int[] prev = new int[wizards.size()];
        boolean[] visited = new boolean[wizards.size()];

        Map<Integer, Wizard> map = new HashMap<>();
        // initialize every wizard in the map
        for (int i = 0; i < wizards.size(); i++) {
            map.put(i, new Wizard(i));
            // initially every node's prev is itself
            prev[i] = i;
        }

        // the source point is 0
        map.get(source).distance = 0;
        // use PriorityQueue to get the smallest weight on the top
        Queue<Wizard> queue = new PriorityQueue<>();
        queue.offer(map.get(source));

        while (!queue.isEmpty()) {
            Wizard current = queue.poll();
            if (current.id == target) break;

            // update to visited
            visited[current.id] = true;

            for (int nextId : wizards.get(current.id)) {
                if (!visited[nextId]) {
                    Wizard next = map.get(nextId);

                    int weight = (int) Math.pow(nextId - current.id, 2);
                    if (current.distance + weight < next.distance) {
                        prev[nextId] = current.id;
                        // this is for de-dupe to avoid it already exists in the queue
                        queue.remove(next);
                        next.distance = current.distance + weight;
                        queue.offer(next);
                    }
                }

            }
        }

        res.add(target);
        while (target != source) {
            target = prev[target];
            res.add(target);
        }

        Collections.reverse(res);
        return res;
    }

    public static void main(String[] args) {
        AN30TenWizards tw = new AN30TenWizards();
        int[][] ids = {{1, 5, 9}, {2, 3, 9}, {4}, {}, {}, {9}, {}, {}, {}, {}};
        List<List<Integer>> wizards = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            List<Integer> wizard = new ArrayList<>();
            for (int j = 0; j < ids[i].length; j++) {
                wizard.add(ids[i][j]);
            }
            wizards.add(wizard);
        }

        System.out.println(tw.getShortestPath(wizards, 0, 3));
    }
}

class Wizard implements Comparable<Wizard> {
    int id;
    // distance means the distance between the source and this wizard
    int distance;

    Wizard(int id) {
        this.id = id;
        this.distance = Integer.MAX_VALUE;
    }

    @Override
    public int compareTo(Wizard other) {
        return this.distance - other.distance;
    }
}