package uber;

public class L34FirstAndLastElementSortedArray {
    /**
     * There must be two indices in the array. Which means, we can just simply apply to binary search twice to
     * find first and last index of the target element.
     * @param nums
     * @param target
     * @return
     */
    public int[] searchRange(int[] nums, int target) {
        int[] res = new int[2];
        res[0] = findFirst(nums, target);
        res[1] = findLast(nums, target);
        return res;
    }

    private int findFirst(int[] nums, int target) {
        int start = 0, end = nums.length - 1;
        int index = -1;
        int mid;
        while (start <= end) {
            mid = start + (end - start)/2;
            if (nums[mid] >= target) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }

            if (nums[mid] == target) index = mid;
        }

        return index;
    }

    private int findLast(int[] nums, int target) {
        int start = 0, end = nums.length - 1;
        int index = -1;
        int mid;
        while (start <= end) {
            mid = start + (end - start)/2;
            if (nums[mid] <= target) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }

            if (nums[mid] == target) index = mid;
        }

        return index;
    }
}
