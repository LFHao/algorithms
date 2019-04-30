package uber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import utils.Interval;

public class L986IntervalListIntersections {
    public int[][] intervalIntersection(int[][] A, int[][] B) {
        if (A == null || A.length == 0 || B == null || B.length == 0) {
            return new int[0][0];
        }

        List<int[]> list = new ArrayList<>();

        int i = 0, j = 0;
        while (i < A.length && j < B.length) {
            int[] a = A[i];
            int[] b = B[j];

            int startMax = Math.max(a[0], b[0]);
            int endMin = Math.min(a[1], b[1]);

            if (startMax <= endMin) {
                list.add(new int[] {startMax, endMin});
            }

            // update pointer based on endMin
            if (a[1] == endMin) i++;
            if (b[1] == endMin) j++;
        }

        // User new Interval[0] instead of new Interval[list.size()] https://shipilev.net/blog/2016/arrays-wisdom-ancients/#_conclusion
        return list.toArray(new int[0][0]);
    }

    public static void main(String[] args) {
        int[][] A = {{0,2},{5,10}, {13,23}, {24,25}};
        int[][] B = {{1,5}, {8,12}, {15,24}, {25,26}};

        L986IntervalListIntersections s = new L986IntervalListIntersections();
        int[][] res = s.intervalIntersection(A, B);

    }

}
