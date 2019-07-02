package typical.binarySearch;

public class BinarySearchTemplate {
    // also works for finding the last position of target
    public int findEqualOrSmaller(int[] nums, int start, int end, int target) {
        int left = start;
        int right = end;
        while (left <= right) {
            int mid = left + (right - left)/2;
            if (nums[mid] > target) {
                right = mid - 1;
            } else {
                // left is equal or smaller than target
                left = mid + 1;
            }
        }
        return right;
    }

    // also works for finding the first position of target
    public int findEqualOrLarger(int[] nums, int start, int end, int target) {
        int left = start;
        int right = end;
        while (left <= right) {
            int mid = left + (right - left)/2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                // left is equal or smaller than target
                right = mid - 1;
            }
        }
        return left;
    }

    // ??? WORKS? merge findEqualOrSmaller and findEqualOrLarger
    public int findEqualOr(int[] nums, int start, int end, int target, boolean smaller) {
        int res = start;
        int left = start;
        int right = end;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                res = mid;
                if (smaller) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                // left is equal or smaller than target
                right = mid - 1;
            }
        }
        return res;
    }
}
