package uber;

public class L361BombEnemy {
    public int maxKilledEnemies(char[][] grid) {
        int[] col = new int[grid[0].length];
        int row = 0;
        int max = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 'W') continue;

                // enemy in this row
                if (i == 0 || grid[i][j - 1] == 'W') {
                    row = calculateRowEnemy(grid, i, j);
                }

                if (j == 0 || grid[i - 1][j] == 'W') {
                    col[j] = calculateColEnemy(grid, i, j);
                }

                if (grid[i][j] == '0') {
                    max = Math.max(row + col[j], max);
                }
            }
        }

        return max;
    }

    private int calculateRowEnemy(char[][] grid, int i, int j) {
        int count = 0;
        while (j < grid[0].length && grid[i][j] != 'W') {
            if (grid[i][j] == 'E') {
                count++;
            }
            j++;
        }
        return count;
    }

    private int calculateColEnemy(char[][] grid, int i, int j) {
        int count = 0;
        while (i < grid.length && grid[i][j] != 'W') {
            if (grid[i][j] == 'E') {
                count++;
            }
            i++;
        }
        return count;
    }
}
