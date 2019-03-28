package airbnb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class N26StringPyramids {
    // to store the leaves and root relationship
    private Map<String, Set<Character>> rootMap;
    // the cache to store if the string/substring can/cannot lead to root in leaf
    private Map<String, Boolean> cache;
    private static final String SEPARATOR = "#";

    N26StringPyramids(String[] lines) {
        rootMap = new HashMap<>();
        cache = new HashMap<>();
        storeMap(lines);
    }

    private void storeMap(String[] lines) {
        for (String line : lines) {
            String[] nodes = line.split(",");
            String key = nodes[0] + SEPARATOR + nodes[1];
            Set<Character> parentSet = new HashSet<>();
            for (char c : nodes[2].toCharArray()) {
                parentSet.add(c);
            }
            rootMap.put(key, parentSet);
        }

    }

    public boolean canBePyramid(String input) {
        cache.clear();
        return search(input, input);
    }

    /**
     * to search if there is valid pyramid can be built from input to the current (might be next level string or root)
     */
    private boolean search(String from, String current) {
        // directly return the value from cache if it exists there
        if (cache.containsKey(from)) return cache.get(from);

        // track to leaf
        if (current.length() == 1) {
            // update cache
            cache.put(current, from.contains(current));
            return cache.get(current);
        }

        // continue to track to the root
        // get the substring of next level
        List<String> nextList = new ArrayList<>();

        // typical backtrack
        getNextLevelList(nextList, current, 0, new StringBuilder());
        // continue with search for next level list
        for (String next : nextList) {
            if (search(from, next)) {
                cache.put(next, true);
                return true;
            }
        }

        return false;

    }

    /**
     * Typical backtrack recursion.
     * @param list
     * @param current
     * @param start
     * @param sb
     */
    private void getNextLevelList(List<String> list, String current, int start, StringBuilder sb) {
        // it ends in current.length() - 1 because in the previous round (start == current.length() - 2)
        // it checks current.length() - 1 already
        if (start == current.length() - 1) {
            // NO Need to new String since sb.toString() returns a new String already
            list.add(sb.toString());
        }

        // start from start everytime, not from the beginning
        for (int i = start; i < current.length() - 1; i++) {
            String key = current.charAt(i) + SEPARATOR + current.charAt(i + 1);
            if (!rootMap.containsKey(key)) {
                continue;
            }
            for (char c : rootMap.get(key)) {
                sb.append(c);
                // continue with next char
                getNextLevelList(list, current, start + 1, sb);

                // exit from next level, remove the last result
                sb.deleteCharAt(sb.length() - 1);
            }
        }
    }

    public static void main(String[] args) {
        String[] lines = {
                "A,A,AC", "A,B,CD", "A,C,D", "A,D,B",
                "B,A,B", "B,B,C", "B,C,A", "B,D,CD",
                "C,A,A", "C,B,C", "C,C,D", "C,D,B",
                "D,A,BC", "D,B,D", "D,C,A", "D,D,C"
        };

        N26StringPyramids sp = new N26StringPyramids(lines);
        System.out.println(sp.canBePyramid("ABCD"));
        System.out.println(sp.canBePyramid("AACC"));
        System.out.println(sp.canBePyramid("AAAA"));
        System.out.println(sp.canBePyramid("CCCC"));
        System.out.println(sp.canBePyramid("DDDD"));

    }


}
