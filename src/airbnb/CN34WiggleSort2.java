package airbnb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Virtual Indexing:
 * https://leetcode.com/problems/wiggle-sort-ii/discuss/77682/Step-by-step-explanation-of-index-mapping-in-Java
 *
 * Quick Select to find KthLargest:
 * https://leetcode.com/problems/kth-largest-element-in-an-array/discuss/60312/AC-Clean-QuickSelect-Java-solution-avg.-O(n)-time
 *
 * Time: O(n), Space: O(n)
 */
public class CN34WiggleSort2 {
    public void wiggleSort(int[] nums) {
        int[] copy = nums.clone();
        int median = findKthLargest(copy, (copy.length + 1) / 2);

        int n = nums.length;
        // l points from 0, means what position/index can insert number smaller than median
        // r points from n - 1, means what position/index can insert number larger than median
        // p traverses the array from 0 to r
        for (int l = 0, r = n - 1, p = l; p <= r;) {
            if (copy[p] < median) {
                // p++ here because we know the number swapped from num[l] was traversed already either smaller or equal to median
                swap(copy, l++, p++);
            } else if (copy[p] > median) {
                // we don't increment p here because we haven't traversed num[r] swapped here
                swap(copy, p, r--);
            } else {
                p++;
            }
        }

        int m =(n + 1) / 2;
        for (int i = m - 1, j = 0; i >= 0; i--, j += 2) nums[j] = copy[i];
        for (int i = n - 1, j = 1; i >= m; i--, j += 2) nums[j] = copy[i];
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

    public static void main(String[] args) {
//        int[] array = {5,3,1,2,6,7,8,5,5};
//        System.out.println(new CN34WiggleSort2().findKthLargest(array, (array.length + 1)/2));
//        for(int i : array) {
//            System.out.print(i + ", ");
//        }

        List<Item> list = new ArrayList<>(Collections.nCopies(4, null));
        list.set(0, new Item(0));
        list.set(2, new Item(1));
        list.set(3, null);
        List<Item> res = list.stream().filter(Objects::nonNull).collect(Collectors.toList());
        System.out.println(res);

    }


}

class Item {
    int id;
    Item(int id) {
        this.id = id;
    }
}
