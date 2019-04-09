package uber;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/evaluate-division/
 * Using Union Find
 * Time: 0(e + q) where e is the number of equations and q is the number of queries since Union and find
 * operations could be considered constant time if using rank and path compression.
 */
public class L399EvaluateDivision {
    public double[] calcEquation(String[][] equations, double[] values, String[][] queries) {
        UnionFind uf = new UnionFind(equations);

        for (int i = 0; i < equations.length; i++) {
            uf.union(equations[i][0], equations[i][1], values[i]);
        }

        double[] res = new double[queries.length];
        for (int j = 0; j < queries.length; j++) {
            String q1 = queries[j][0];
            String q2 = queries[j][1];
            if (!uf.nodeMap.containsKey(q1) || !uf.nodeMap.containsKey(q2)) {
                res[j] = -1.0d;
                continue;
            }

            if (uf.nodeMap.get(q1).root.equals(q2) || uf.nodeMap.get(q2).root.equals(q1)) {
                res[j] = uf.nodeMap.get(q1).root.equals(q2)? uf.nodeMap.get(q1).weight : 1/uf.nodeMap.get(q2).weight;
                continue;
            }

            String q1Root = uf.find(q1);
            String q2Root = uf.find(q2);
            if (!q1Root.equals(q2Root)) {
                res[j] = -1.0d;
                continue;
            }
            res[j] = uf.nodeMap.get(q1).weight / uf.nodeMap.get(q2).weight;
        }

        return res;
    }

    public static void main(String[] args) {
        String[][] equations = {{"a","b"},{"e","f"},{"b","e"}};
        double[] values = {3.4, 1.4, 2.3};
        String[][] queries = {{"b","a"}, {"a","f"}, {"f","f"}, {"e","e"}, {"c","c"}, {"a","c"}, {"f", "e"}};

        L399EvaluateDivision s = new L399EvaluateDivision();
        System.out.println(Arrays.toString(s.calcEquation(equations, values, queries)));

    }

    class UnionFind {
        Map<String, Node> nodeMap;

        UnionFind(String[][] equations) {
            this.nodeMap = new HashMap<>();
            initialize(equations);
        }

        void initialize(String[][] equations) {
            for (String[] equation : equations) {
                nodeMap.putIfAbsent(equation[0], new Node(equation[0], 1));
                nodeMap.putIfAbsent(equation[1], new Node(equation[1], 1));
            }
        }

        String find(String str) {
            String curRoot = nodeMap.get(str).root;
            if (!str.equals(curRoot)) {
                nodeMap.get(str).root = find(curRoot);
                nodeMap.get(str).weight *= nodeMap.get(curRoot).weight;
            }
            return nodeMap.get(str).root;
        }

        void union(String str1, String str2, double value) {
            String root1 = find(str1);
            String root2 = find(str2);

            if (!root1.equals(root2)) {
                if (nodeMap.get(root1).rank < nodeMap.get(root2).rank) {
                    nodeMap.get(root1).root = root2;
                    nodeMap.get(root1).weight = nodeMap.get(str2).weight * value;
                } else if (nodeMap.get(root1).rank > nodeMap.get(root2).rank){
                    nodeMap.get(root2).root = root1;
                    nodeMap.get(root2).weight = nodeMap.get(str1).weight / value;
                } else {
                    nodeMap.get(root1).root = root2;
                    nodeMap.get(root1).weight = nodeMap.get(str2).weight * value;
                    // why only rank is equal, we increase the rank
                    nodeMap.get(root2).rank++;
                }
            }

        }

    }

    class Node {
        String root;
        double weight;
        int rank;

        Node(String root, double weight) {
            this.root = root;
            this.weight = weight;
            this.rank = 0;
        }
    }
}
