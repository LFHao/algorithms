package google;

import java.util.Arrays;

public class L684RedundentConnection {
    /**
     * Union Find: The Union by Rank and Path Compression can optimize the time complexity
     * from O(n) to O(logn) (even smaller).
     * @param edges
     * @return
     */
    public int[] findRedundantConnection(int[][] edges) {
        UnionFind uf = new UnionFind(edges.length);

        int[] res = new int[2];
        for (int[] edge : edges) {
            if (!uf.union(edge[0], edge[1])) res = edge;
        }
        return res;
    }


    public static void main(String[] args) {
        int[][] edges = {{1,2}, {1,3}, {2,3}};
        System.out.println(Arrays.toString(new L684RedundentConnection().findRedundantConnection(edges)));
    }
}

class UnionFind {
    int[] roots;
    int[] rank;

    UnionFind(int n) {
        roots = new int[n];
        for(int i = 1; i <= n; i++) {
            roots[i - 1] = i;
        }
        rank = new int[n];
    }

    int find(int i) {
        if (roots[i - 1] != i) {
            roots[i - 1] = find(roots[i - 1]);
        }
        return roots[i - 1];
    }

    boolean union(int i, int j) {
        int rootI = find(i);
        int rootJ = find(j);

        if (rootI == rootJ) return false;

        if (rank[rootI - 1] < rank[rootJ - 1]) {
            roots[rootI - 1] = rootJ;
        } else if (rank[rootI - 1] > rank[rootJ - 1]) {
            roots[rootJ - 1] = rootI;
        } else {
            roots[rootJ - 1] = rootI;
            rank[rootI - 1]++;
        }

        return true;
    }
}