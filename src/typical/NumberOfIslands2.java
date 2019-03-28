package typical;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NumberOfIslands2 {

    /**
     * @param n: An integer
     * @param m: An integer
     * @param operators: an array of point
     * @return: an integer array
     */
    public List<Integer> numIslands2(int n, int m, Point[] operators) {
        // this array to record the root of each typical.Point
        int[] roots = new int[n * m];
        Arrays.fill(roots, -1);
        List<Integer> res = new ArrayList<>();
        int count = 0;

        int[][] dir = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int i = 0; i < operators.length; i++) {
            count++;

            int index = operators[i].x * m + operators[i].y;
            roots[index] = index;

            // find neighbor's roots and union if necessary
            for (int j = 0; j < dir.length; j++) {
                int x = operators[i].x + dir[j][0];
                int y = operators[i].y + dir[j][1];
                int neighbor = x * m + y;
                if (x >= 0 && y >=0 && x < n && y < m && roots[neighbor] != -1) {
                    int root = findRoot(roots, neighbor);

                    // union if different
                    if (root != index) {
                        count = union(roots, index, neighbor, count);
                    }
                }
            }

            res.add(count);
        }

        return res;
    }

    private int findRoot(int[] roots, int index) {
        while (index != roots[index]) {
            roots[index] = roots[roots[index]];
            index = roots[index];
        }
        return index;
    }

    private int union(int[] roots, int i1, int i2, int count) {
        int id1 = roots[i1], id2 = roots[i2];
        if (id1 != id2) {
            for (int i = 0; i < roots.length; i++) {
                if (roots[i] == id2) {
                    roots[i] = id1;
                }
            }
            count--;
            return count;
        }
        return count;
    }

    public static void main(String[] args) {
        NumberOfIslands2 noi = new NumberOfIslands2();
        Point[] points = {new Point(0,0), new Point(1,1), new Point(1,0), new Point(0,1)};
        System.out.println(noi.numIslands2(2, 2, points));
    }
}

/**
 * Definition for a point.
 */
class Point {
    int x;
    int y;
    Point() { x = 0; y = 0; }
    Point(int a, int b) { x = a; y = b; }
}

