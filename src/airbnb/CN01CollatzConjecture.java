package airbnb;

import java.util.HashMap;
import java.util.Map;

public class CN01CollatzConjecture {
    Map<Integer, Integer> map = new HashMap<>();

    public int findLongestSteps(int num) {
        int max = 0;

        for (int i = 1; i <= num; i++) {
            int steps = findSteps(i);
            map.put(i, steps);
            max = Math.max(max, steps);
        }

        return max;
    }

    private int findSteps(int num) {
        if (num <= 1) return 1;
        if (map.containsKey(num)) return map.get(num);

        int step;
        if (num % 2 == 0){
            step = 1 + findSteps(num / 2);
        } else {
            step = 1 + findSteps(3 * num + 1);
        }
       //map.put(num, step);

        return step;
    }

    public static void main(String[] args) {
        CN01CollatzConjecture cc = new CN01CollatzConjecture();

        System.out.println(String.format("max steps for num %s is ", 10) + cc.findLongestSteps(10));

        // using for-each loop for iteration over Map.entrySet()
        for (Map.Entry<Integer,Integer> entry : cc.map.entrySet()) {
            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue());
        }
    }

}
