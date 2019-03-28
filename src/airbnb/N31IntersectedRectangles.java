package airbnb;

import java.util.HashSet;
import java.util.Set;

/**
 * https://leetcode.com/problems/rectangle-overlap/discuss/132340/C%2B%2BJavaPython-1-line-Solution-1D-to-2D
 * and Union Find
 */
public class N31IntersectedRectangles {

    private boolean isIntersected(int[][] r1, int[][] r2) {
        // either r1 is on left of r2 or r2 is on the left of r1
        return (r1[0][0] < r2[1][0] && r2[0][0] < r1[1][0] && r1[0][1] < r2[1][1] && r2[0][1] < r1[1][1]) ||
                (r2[0][0] < r1[1][0] && r1[0][0] < r2[1][0] && r2[0][1] < r1[1][1] && r1[0][1] < r2[1][1]);
    }

    public int countIntersection(int[][][] rectangles) {
        UnionFind uf = new UnionFind(rectangles.length);

        for (int i = 0; i < rectangles.length; i++) {
            for (int j = i + 1; j < rectangles.length; j++) {
                int[][] cur = rectangles[i];
                int[][] next = rectangles[j];
                if (isIntersected(cur, next)) {
                    uf.union(i, j);
                }
            }
        }

        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < rectangles.length; i++) {
            set.add(uf.find(i));
        }

        return set.size();
    }

    public static void main(String[] args) {
        int[][][] rectangles = {
                {{-3, -2}, {2, 1}},
                {{10, 8}, {15, 10}},
                {{1, 0}, {7, 4}},
                {{12, 9}, {16, 12}},
                {{-2, -1}, {5, 3}}
        };

        System.out.println(new N31IntersectedRectangles().countIntersection(rectangles));

    }

    class UnionFind {
        Group[] groups;

        UnionFind(int length) {
            // to store the group root of each rectangle and initialize it to its own
            groups = new Group[length];
            for (int i = 0; i < length; i++) {
                groups[i] = new Group(i, 0);
            }
        }

        int find(int index) {
            if (index != groups[index].root) {
                groups[index].root = find(groups[index].root);
            }
            return groups[index].root;
        }

        void union(int i, int j) {
            int iRoot = find(i);
            int jRoot = find(j);

            if (iRoot == jRoot) {
                return;
            }

            if (groups[i].rank <= groups[j].rank) {
                groups[i].root = jRoot;
                groups[j].rank++;
            } else {
                groups[j].rank = iRoot;
                groups[i].rank++;
            }
        }
    }

    class Group {
        int root;
        int rank;

        Group(int root, int rank) {
            this.root = root;
            this.rank = rank;
        }
    }
}
