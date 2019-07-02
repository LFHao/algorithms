package typical.twoPointers;

import java.util.Arrays;

public class L16ThreeSumClosest {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);

        int minDiff = Integer.MAX_VALUE;
        int minSum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            int left = i + 1, right = nums.length - 1;
            int sum = target - nums[i];
            while (left < right) {
                int diff = Math.abs(target - nums[i] - nums[left] - nums[right]);

                if (diff < minDiff) {
                    minSum = nums[i] + nums[left] + nums[right];
                    minDiff = diff;
                }
                if (nums[left] + nums[right] > sum) {
                    right--;
                } else if (nums[left] + nums[right] < sum) {
                    left++;
                } else if (nums[left] + nums[right] == sum) {
                    return target;
                }
            }

        }

        return minSum;
    }

    public static void main(String[] args) {
        int[] nums = {-1, 2, 1, -4};
        L16ThreeSumClosest s = new L16ThreeSumClosest();
        System.out.println(s.threeSumClosest(nums, 1));
    }
}
