package twitter;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class FrogJump {
    public boolean canCross(int[] stones) {
        if (stones == null || stones.length == 0) return true;
        // the 1st step must be 1
        // if (stones[1] != 1) return false;

        int lastStone = stones[stones.length - 1];

        Map<Integer, HashSet<Integer>> map = new HashMap<>();
        map.computeIfAbsent(0, k -> new HashSet<>()).add(0);

        for (int i = 1; i < stones.length; i++) {
            int curIndex = stones[i];
            for (int k = i - 1; k >= 0; k--) {
                int prevIndex = stones[k];
                int kStep = curIndex - prevIndex;
                if (map.containsKey(prevIndex) &&
                        (map.get(prevIndex).contains(kStep) ||
                                map.get(prevIndex).contains(kStep - 1) ||
                                map.get(prevIndex).contains(kStep + 1))) {
                    map.computeIfAbsent(curIndex, key -> new HashSet<>()).add(kStep);
                }
            }
        }

        return map.containsKey(lastStone);
    }

    public static void main(String[] args) {
        FrogJump s = new FrogJump();
        int[] stones = {0,1,3,4,5,7,9,10,12};

        System.out.println(s.canCross(stones));
    }
}
