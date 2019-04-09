package uber;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class L767ReorganizeString {

    /**
     * Key point: priority queue to store the character and count
     * @param S
     * @return
     */
    public String reorganizeString(String S) {
        int len = S.length();
        Map<Character, Integer> map = new HashMap<>();
        for (char c : S.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
            // this means cannot be reorganized
            if (map.get(c) > (len + 1)/2 ) {
                return "";
            }
        }

        PriorityQueue<Map.Entry<Character, Integer>> queue = new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());
        for (Map.Entry<Character, Integer> entry: map.entrySet()) {
            queue.offer(entry);
        }

        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            Map.Entry<Character, Integer> cur = queue.poll();
            if (sb.length() == 0 || cur.getKey() != sb.charAt(sb.length() - 1)) {
                sb.append(cur.getKey());
                cur.setValue(cur.getValue() - 1);
                if (cur.getValue() > 0) queue.offer(cur);

            } else {
                // because this is a priority queue, if we put it back directly, it will be at the top again
                // so we need to pop the next char
                Map.Entry<Character, Integer> next = queue.poll();
                // corner case: if no more elements in queue, the input string should be invalid
                // because we do not have any other characters that different with current string tail
                if (next == null) {
                    return "";
                }
                sb.append(next.getKey());
                next.setValue(next.getValue() - 1);
                if (next.getValue() > 0) queue.offer(next);

                // push it back to queue
                queue.offer(cur);
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        L767ReorganizeString s = new L767ReorganizeString();
        System.out.println(s.reorganizeString("vvvlo"));
    }
}
