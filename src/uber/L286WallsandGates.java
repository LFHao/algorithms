package uber;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class L286WallsandGates {
    int[][] dirs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    public void wallsAndGates(int[][] rooms) {
        if (rooms == null || rooms.length == 0 || rooms[0].length == 0) return;

        int m = rooms.length;
        int n = rooms[0].length;
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (rooms[i][j] == 0) {
                    queue.offer(new int[]{i, j});
                }
            }
        }

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            for (int[] dir : dirs) {
                int x = cur[0] + dir[0];
                int y = cur[1] + dir[1];
                // only the INF needs to be updated as the nearest 0 already update
                // its nearest neighbors
                if (isValid(x, y, m, n) && rooms[x][y] == Integer.MAX_VALUE) {
                    rooms[x][y] = rooms[cur[0]][cur[1]] + 1;
                    queue.offer(new int[]{x, y});
                }
            }
        }
    }

    private boolean isValid(int i, int j, int m, int n) {
        return i>=0 && j>=0 && i<m && j<n;
    }

    public static void main(String[] args) {
        int[][] grid = {{Integer.MAX_VALUE, -1, 0, Integer.MAX_VALUE},
                {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, -1},
                {Integer.MAX_VALUE, -1, Integer.MAX_VALUE, -1},
                {0, -1, Integer.MAX_VALUE, Integer.MAX_VALUE}};
        L286WallsandGates s = new L286WallsandGates();
        s.wallsAndGates(grid);

        for (int[] g: grid) {
            System.out.println(Arrays.toString(g));
        }
    }
}
