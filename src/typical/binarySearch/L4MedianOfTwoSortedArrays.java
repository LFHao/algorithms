package typical.binarySearch;

public class L4MedianOfTwoSortedArrays {
    // it is to find a number in the arrays that split A+B into 2 parts with equal length
    public double findMedianSortedArrays(int[] A, int[] B) {
        int n = A.length + B.length;
        // if len is an even, it is the half of (n/2)th and (n/2+1)th : k is 1-based
        if (n % 2 == 0) {
           return (findKth(A, B, n/2) + findKth(A, B, n/2+1))/2.0;
        }
        return findKth(A, B, n/2+1);
    }

    // use long to avoid overflow
    private long findKth(int[] A, int[] B, int k) {
        if (A.length == 0) {
            return B[k - 1];
        }
        if (B.length == 0) {
            return A[k - 1];
        }

        // to find kth, we use binary search, we assume that it starts from finding the (min+max)/2
        int left = Math.min(A[0], B[0]);
        int right = Math.max(A[A.length - 1], B[B.length - 1]);

        while (left + 1< right) {
            int mid = left + (right - left)/2;
            if (findEqualOrSmaller(A, mid) + findEqualOrSmaller(B, mid) < k) {
                left = mid;
            } else {
                right = mid;
            }
        }

        if (findEqualOrSmaller(A, left) + findEqualOrSmaller(B, left) >= k) {
            return left;
        }
        return right;
    }

    // it is similar to find the last position of target
    private int findEqualOrSmaller(int[] nums, int target) {
        int left = 0; int right = nums.length - 1;
        while (left + 1 < right) {
            int mid = left + (right - left)/2;
            if (nums[mid] <= target) {
                left = mid;
            } else {
                right = mid;
            }
        }

        if (nums[left] > target) return left;
        if (nums[right] > target) return right;
        return nums.length;
    }

    public static void main(String[] args) {
        int[] A = {1, 3};
        int[] B = {2};
        System.out.println(new L4MedianOfTwoSortedArrays().findMedianSortedArrays(A, B));

    }
}
