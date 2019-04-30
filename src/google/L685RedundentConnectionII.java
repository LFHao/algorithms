package google;

/**
 * A tree's definition 1) no circle in the graph, 2) each node only has 1 parent
 */
public class L685RedundentConnectionII {
    /**
     * There are two cases for the tree structure to be invalid.
     * 1) A node having two parents;
     *    including corner case: e.g. [[4,2],[1,5],[5,2],[5,3],[2,4]]
     * 2) A circle exists
     *
     * https://leetcode.com/problems/redundant-connection-ii/discuss/182655/Union-Find-with-a-few-of-explanations-on-key-points...
     * @param edges
     * @return
     */
    public int[] findRedundantDirectedConnection(int[][] edges) {
        // case 1: check if any node has 2 parents - edge 1 and edge 2

        // case 2: check if there is circle in the graph
        // - No: return edge 2
        // - Yes: remove edge 1 to check if there is still circle:
        //    - Yes, edge 2.
        //    - No, edge 1.

        int[] cand1 = new int[2];
        int[] cand2 = new int[2];
        int[] roots = new int[edges.length + 1];
        for (int[] edge : edges) {
            if (roots[edge[1]] != 0) {
                cand1 = new int[] {roots[edge[1]], edge[1]};
                cand2 = edge;
                break;
            }
            roots[edge[1]] = edge[0];
        }

        UnionFind uf = new UnionFind(edges.length);
        for(int[] edge : edges) {
            if (edge[0] == cand1[0] && edge[1] == cand1[1] || edge[0] == cand2[0] && edge[1] == cand2[1]) {
                continue;
            }
            if (!uf.union(edge[0], edge[1])) return edge;
        }

        if (!uf.union(cand1[0], cand1[1])) return cand1;
        return cand2;
    }
}
