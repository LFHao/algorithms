package uber;

import java.util.ArrayList;
import java.util.List;

public class IntersectionOfKLists {
    public List<Integer> getIntersectionOfKLists(List<int[]> lists) {
        List<Integer> res = new ArrayList<>();
        int[] indices = new int[lists.size()];
        boolean toEnd = false;

        while (!toEnd) {
            // find max of num (at specific index) in each list
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < lists.size(); i++) {
                max = Math.max(max, lists.get(i)[indices[i]]);
            }

            // find if there are intersected numbers among n lists
            int count = 0;
            for (int i = 0; i < lists.size(); i++) {
                if (lists.get(i)[indices[i]] == max) {
                    count++;
                    // we can say that next round, the index of this array should be moved to the next
                    indices[i]++;
                } else {
                    int nextIndex = findEqualOrLarger(lists.get(i), indices[i], max);
                    if (checkEnd(nextIndex,lists.get(i).length)) {
                        toEnd = true;
                        break;
                    }

                    indices[i] = nextIndex;
                    if (lists.get(i)[indices[i]] == max) {
                        count++;
                        indices[i]++;
                        if (checkEnd(indices[i], lists.get(i).length)) toEnd = true;
                    }
                }
            }

            // count if intersected is same with lists.size()
            if (count == lists.size()) res.add(max);
        }

        return res;

    }

    private boolean checkEnd(int index, int size) {
        return index == size;
    }

    private int findEqualOrLarger(int[] nums, int i, int target) {
        int left = i, right = nums.length - 1;

        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] >= target) {
                right = mid;
            } else {
                left = mid;
            }
        }
        if (nums[left] >= target) return left;
        if (nums[right] >= target) return right;

        // 1 case is that till the length, all numbers are smaller than target
        return nums.length;
    }

    public static void main(String[] args) {
        IntersectionOfKLists s = new IntersectionOfKLists();
        List<int[]> lists = new ArrayList<>();
//        int[] a1 = {1,1,1,2};
//        int[] a2 = {3};
//        int[] a3 = {4};
        int[] a1 = {1,2,3,4,5,6,8,9,10,13};
        int[] a2 = {3,5,9,13,14,15};
        int[] a3 = {3,9,10,13};
        lists.add(a1);
        lists.add(a2);
        lists.add(a3);

        System.out.println(s.getIntersectionOfKLists(lists));
    }
}
