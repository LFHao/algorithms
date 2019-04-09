package airbnb;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NewLCA {
    /**
     * Solution 1
     * @param relations
     * @param p1
     * @param p2
     * @return
     */
    public String lcaPlace(String[][] relations, String p1, String p2) {
        PlaceNode root = buildTree(relations, new HashMap<>());

        return findLCA(root, p1, p2).name;
    }

    private PlaceNode findLCA(PlaceNode root, String p1, String p2) {
        if (root == null || root.name.equals(p1) || root.name.equals(p2)) return root;

        PlaceNode left = findLCA(root.left, p1, p2);
        PlaceNode right = findLCA(root.right, p1, p2);

        if (left != null && right != null) return root;
        return left != null ? left : right;
    }

    private PlaceNode buildTree(String[][] relations, Map<String, PlaceNode> map) {
        for (String[] relation : relations) {
            map.putIfAbsent(relation[0], new PlaceNode(relation[0]));
            map.putIfAbsent(relation[1], new PlaceNode(relation[1]));
            map.putIfAbsent(relation[2], new PlaceNode(relation[2]));
            map.get(relation[0]).left = map.get(relation[1]);
            map.get(relation[0]).right = map.get(relation[2]);
        }
        // is the 1st one the root?
        return map.get(relations[0][0]);
    }

    class PlaceNode {
        String name;
        PlaceNode left;
        PlaceNode right;

        PlaceNode(String name) {
            this.name = name;
        }
    }

    /**
     * Solution 2
     * @param relations
     * @param p1
     * @param p2
     * @return
     */
    public String lcaPlace2(String[][] relations, String p1, String p2) {
        Map<String, String> map = new HashMap<>();
        buildMap(relations, map);

        Set<String> p1Parent = new HashSet<>();
        // find p1's parent path
        while (map.get(p1) != null) {
            p1Parent.add(p1);
            p1 = map.get(p1);
        }
        p1Parent.add(p1);

        // traverse p2 parent path to see if it contains in p1's path
        // the 1st one to find is the lowest common ancestor
        while (!p1Parent.contains(p2)) {
            p2 = map.get(p2);
        }
        return p2;
    }

    private void buildMap(String[][] relations, Map<String, String> map) {
        for (String[] relation : relations) {
            map.putIfAbsent(relation[1], relation[0]);
            map.putIfAbsent(relation[2], relation[0]);
        }
    }


    public static void main(String[] args) {
        NewLCA s = new NewLCA();
        String[][] relations = {{"earth", "NA", "SA"}, {"SA", "brazil", "peru"}, {"NA", "USA", "CANADA"}, {"USA", "CA", "NYC"}, {"CA", "SF", "Oakland"}};
        System.out.println(s.lcaPlace(relations, "NYC", "Oakland"));
        System.out.println(s.lcaPlace2(relations, "NYC", "Oakland"));

    }
}
