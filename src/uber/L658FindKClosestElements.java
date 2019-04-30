package uber;

import java.util.ArrayList;
import java.util.List;

public class L658FindKClosestElements {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        List<Integer> res = new ArrayList<>();

        if (x < arr[0]) {
            for (int i = 0; i < k; i++) {
                res.add(arr[i]);
            }
            return res;
        }

        if (x > arr[arr.length - 1]) {
            for (int i = arr.length - 1; i >= 0; i--) {
                res.add(arr[i]);
            }
            return res;
        }

        int l = 0, r = arr.length - 1, mid;
        while (l < r) {
            mid = l + (r - l) / 2;
            if (arr[mid] > x) {
                r = mid - 1;
            } else if (arr[mid] < x) {
                l = mid + 1;
            } else {
                l = mid;
                break;
            }
        }

        r = l + 1;
        while (k > 0) {
            if (r >= arr.length || (l >= 0 &&  x - arr[l] <= arr[r] - x)) {
                res.add(0, arr[l--]);
            } else {
                res.add(arr[r++]);
            }
            k--;
        }

        return res;
    }

    public static void main(String[] args) {
        L658FindKClosestElements s = new L658FindKClosestElements();
        int[] arr = {1,2,3,4,5};
        System.out.println(s.findClosestElements(arr, 4, 3));
        int[] arr2 = {1};
        System.out.println(s.findClosestElements(arr2, 1, 1));

        int[] arr3 = {0,1,1,1,2,3,6,7,8,9};
        System.out.println(s.findClosestElements(arr3, 9, 4));
    }
}
