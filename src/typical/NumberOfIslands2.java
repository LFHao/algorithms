package typical;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NumberOfIslands2 {

    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        List<Integer> res = new ArrayList<>();
        if (positions == null || positions.length == 0) return res;

        UnionFind uf = new UnionFind(m*n);
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        int count = 0;
        for (int[] position : positions) {
            count++;

            int x = position[0];
            int y = position[1];
            int index = x*n + y;
            // set to index
            uf.roots[index] = index;

            for (int[] dir : dirs) {
                int neighborX = x + dir[0];
                int neighborY = y + dir[1];
                int neighborIndex = neighborX*n + neighborY;

                if (validIndex(neighborX, neighborY, m, n) && uf.roots[neighborIndex] != -1) {
                    // only when roots are different, we union and we decrement count if union
                    if (uf.union(index, neighborIndex)) {
                        count--;
                    }
                }
            }
            res.add(count);
        }

        return res;
    }

    private boolean validIndex(int x, int y, int m, int n) {
        return x >=0 && y >= 0 && x < m && y < n;
    }

    class UnionFind {
        int[] roots;
        int[] ranks;

        UnionFind(int n) {
            roots = new int[n];
            ranks = new int[n];
            Arrays.fill(roots, -1);
        }

        // path compression
        int find(int i) {
            if (i != roots[i]) {
                roots[i] = find(roots[i]);
            }
            return roots[i];
        }

        // union by rank
        boolean union(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);

            // do not need to union
            if (rootI == rootJ) return false;

            if (ranks[rootI] > ranks[rootJ]) {
                roots[rootJ] = roots[rootI];
            } else if (ranks[rootI] < ranks[rootJ]) {
                roots[rootI] = roots[rootJ];
            } else {
                roots[rootJ] = roots[rootI];
                ranks[rootI]++;
            }

            return true;
        }
    }

    public static void main(String[] args) {
        NumberOfIslands2 noi = new NumberOfIslands2();
        int[][] points = {{0,0},{0,1},{1,2},{2,1}};
        System.out.println(noi.numIslands2(3, 3, points));

    }
}

