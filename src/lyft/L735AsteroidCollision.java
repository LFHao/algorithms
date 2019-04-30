package lyft;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Objects;

public class L735AsteroidCollision {
    /**
     * Time Complexity: O(n), Space Complexity: O(n)
     * @param asteroids
     * @return
     */
    public int[] asteroidCollision(int[] asteroids) {
        List<Integer> list = new ArrayList<>();

        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < asteroids.length; i++) {
            int a = asteroids[i];
            if (a >= 0) {
                deque.offerLast(a);
            } else {
                if (!deque.isEmpty()) {
                    boolean add = false;
                    while (!deque.isEmpty()) {
                        int cur = deque.pollLast();
                        if (!add & (cur + a == 0)) {
                            add = true;
                            break;
                        }
                        if (!add && (cur + a > 0)) {
                            add = true;
                            deque.offerLast(cur);
                            break;
                        }
                    }
                    if (!add) list.add(a);
                } else {
                    list.add(a);
                }
            }
        }

        while (!deque.isEmpty()) {
            list.add(deque.pollFirst());
        }

        return list.stream().filter(Objects::nonNull).mapToInt(i -> i).toArray();
    }

    public static void main(String[] args) {
        int[] arr = {5, 10, -5};
        int[] arr2 = {8, -8};
        int[] arr3 = {10, 2, -5};
        int[] arr4 = {-2, -1, 1, 2};
        L735AsteroidCollision s = new L735AsteroidCollision();
        System.out.println(Arrays.toString(s.asteroidCollision(arr)));
        System.out.println(Arrays.toString(s.asteroidCollision(arr2)));
        System.out.println(Arrays.toString(s.asteroidCollision(arr3)));
        System.out.println(Arrays.toString(s.asteroidCollision(arr4)));
        int[] arr5 = {-2, 2, -1, -2};
        System.out.println(Arrays.toString(s.asteroidCollision(arr5)));


    }
}
