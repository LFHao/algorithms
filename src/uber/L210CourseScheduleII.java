package uber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class L210CourseScheduleII {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] res = new int[numCourses];
        if (prerequisites.length == 0) {
            int index = 0;
            while (index < numCourses) {
                res[index] = index;
                index++;
            }
            return res;
        }

        Arrays.fill(res, -1);

        Map<Integer, List<Integer>> courseMap = new HashMap<>();
        Map<Integer, Integer> indegrees = new HashMap<>();
        for (int[] pre : prerequisites) {
            courseMap.putIfAbsent(pre[0], new ArrayList<>());
            courseMap.putIfAbsent(pre[1], new ArrayList<>());
            indegrees.putIfAbsent(pre[0], 0);
            indegrees.putIfAbsent(pre[1], 0);
        }

        for (int[] pre : prerequisites) {
            List<Integer> nextCourses = courseMap.get(pre[1]);
            nextCourses.add(pre[0]);
            courseMap.put(pre[1], nextCourses);
            indegrees.put(pre[0], indegrees.get(pre[0]) + 1);
        }

        Queue<Integer> queue = new LinkedList<>();
        indegrees.forEach((k, v) -> {
            if (v == 0)
                queue.offer(k);
        });

        Set<Integer> inOrder = new HashSet<>();
        int i = 0;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            res[i] = cur;
            inOrder.add(cur);
            for (int next : courseMap.get(cur)) {
                if (indegrees.get(next) == 0) continue;

                indegrees.put(next, indegrees.get(next) - 1);
                if (indegrees.get(next) == 0) {
                    queue.offer(next);
                }
            }
            i++;
        }

        indegrees.entrySet().removeIf(e -> e.getValue() == 0);

        // have circle
        if (!indegrees.isEmpty()) {
            return new int[0];
        }

        for (int n = 0; n < numCourses; n++) {
            if (!inOrder.contains(n)) {
                res[i++] = n;
            }
        }

        return res;
    }

    public static void main(String[] args) {
        L210CourseScheduleII s = new L210CourseScheduleII();
        int[][] p = {{1, 0}};
        System.out.println(Arrays.toString(s.findOrder(3, p)));
    }
}
