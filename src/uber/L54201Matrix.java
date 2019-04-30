package uber;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class L54201Matrix {
    /**
     * BFS
     * Time Complexity: O(n), Space: O(1)
     */
    int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    public int[][] updateMatrix(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;

        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    queue.offer(new int[]{i, j});
                } else {
                    matrix[i][j] = Integer.MAX_VALUE;
                }
            }
        }

        while (!queue.isEmpty()) {
            int[] unit = queue.poll();
            int x = unit[0];
            int y = unit[1];

            for (int[] dir : dirs) {
                int nx = x + dir[0];
                int ny = y + dir[1];

                if (!isValid(nx, ny, m, n)) continue;

                // it means 1) this neighbor is updated; 2) this neighbor is the smallest value
                if (matrix[nx][ny] <= matrix[x][y] + 1) continue;

                // get the min
                matrix[nx][ny] = matrix[x][y] + 1;
                queue.offer(new int[]{nx, ny});
            }

        }

        return matrix;
    }

    private boolean isValid(int i, int j, int m, int n) {
        return i >= 0 && j >=0 && i < m && j < n;
    }

    public static void main(String[] args) {
        L54201Matrix s = new L54201Matrix();
        int[][] matrix = {{0,0,0}, {0,1,0}, {1,1,1}};
        s.updateMatrix(matrix);
        for (int[] m : matrix) {
            System.out.println(Arrays.toString(m));
        }

    }
}
