package uber;

import java.util.HashSet;
import java.util.Set;

public class L202HappyNumber {
    public boolean isHappy(int n) {
        Set<Integer> set = new HashSet<>();

        while (n != 1) {
            if (set.contains(n)) return false;

            set.add(n);
            int sum = 0;
            while (n > 0) {
                sum += Math.pow(n % 10, 2);
                n = n / 10;
            }
            n = sum;
        }
        return true;
    }

    public static void main(String[] args) {
        L202HappyNumber s = new L202HappyNumber();
        System.out.println(s.isHappy(3));
    }
}
