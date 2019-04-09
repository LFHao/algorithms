package uber;

import java.util.Arrays;

/**
 * The SortColors 1-Pass solution is used in WiggleSort2
 */
public class L75SortColors {
    public void sortColors(int[] nums) {
        // define value ranges
        int s = 0, m = 1, l = 2;
        // define 3 pointers:
        // left - to represent this position needs to put s (0), all the index before left is already sorted
        // right - to represent this position needs to put l (1), all the index after right is already sorted
        // i - pass through the array and sort the array from start to end
        int left = 0, right = nums.length - 1, i = 0;
        while (i <= right) {
            if (nums[i] == s) {
                swap(nums, i, left);
                // left is sorted, move left forward
                // the num swapped from left must be <= m, so move i forward too
                left++;
                i++;
            } else if (nums[i] == l) {
                swap(nums, i, right);
                // right is sorted, move right backward
                // but here we cannot guarantee the num swapped from right is < or > or == m, so we don't move i
                right--;
            } else {
                i++;
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args) {
        int[] nums = {2,0,2,1,1,0};
        new L75SortColors().sortColors(nums);
        System.out.println(Arrays.toString(nums));
    }
}
