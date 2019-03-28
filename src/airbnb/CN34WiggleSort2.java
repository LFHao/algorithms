package airbnb;

/**
 * Virtual Indexing:
 * https://leetcode.com/problems/wiggle-sort-ii/discuss/77682/Step-by-step-explanation-of-index-mapping-in-Java
 *
 * Quick Select to find KthLargest:
 * https://leetcode.com/problems/kth-largest-element-in-an-array/discuss/60312/AC-Clean-QuickSelect-Java-solution-avg.-O(n)-time
 */
public class CN34WiggleSort2 {
    public void wiggleSort(int[] nums) {
        int median = findKthLargest(nums, (nums.length + 1) / 2);

        int n = nums.length;

        // Mapped_idx[Left] denotes the position where the next smaller-than median element  will be inserted.
        // Mapped_idx[Right] denotes the position where the next larger-than median element  will be inserted.
        int left = 0, i = 0, right = n - 1;

        while (i <= right) {
            if (nums[newIndex(i,n)] > median) {
                swap(nums, newIndex(left++,n), newIndex(i++,n));
            }
            else if (nums[newIndex(i,n)] < median) {
                swap(nums, newIndex(right--,n), newIndex(i++,n));
            }
            else {
                i++;
            }
        }

    }

    private int findKthLargest(int[] a, int k) {
        int n = a.length;
        int p = quickSelect(a, 0, n - 1, n - k + 1);
        return a[p];
    }

    // return the index of the kth smallest number
    int quickSelect(int[] a, int lo, int hi, int k) {
        // use quick sort's idea
        // put nums that are <= pivot to the left
        // put nums that are  > pivot to the right
        int i = lo, j = hi, pivot = a[hi];
        while (i < j) {
            if (a[i++] > pivot) swap(a, --i, --j);
        }
        swap(a, i, hi);

        // count the nums that are <= pivot from lo
        int m = i - lo + 1;

        // pivot is the one!
        if (m == k)     return i;
            // pivot is too big, so it must be on the left
        else if (m > k) return quickSelect(a, lo, i - 1, k);
            // pivot is too small, so it must be on the right
        else            return quickSelect(a, i + 1, hi, k - m);
    }

    void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private int newIndex(int index, int n) {
        int newIndex = (1 + 2*index) % (n | 1);
        return newIndex;
    }

    public static void main(String[] args) {
        int[] array = {6,13,5,4,5,2};
        new CN34WiggleSort2().wiggleSort(array);
        for(int i : array) {
            System.out.print(i + ", ");
        }
    }
}
