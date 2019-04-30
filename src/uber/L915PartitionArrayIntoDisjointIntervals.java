package uber;

public class L915PartitionArrayIntoDisjointIntervals {
    public int partitionDisjoint(int[] A) {
        int maxL = A[0];
        int right = 1, partition = right;

        while (right < A.length) {
            if (maxL <= A[right]) {
                int temp = A[right];
                while (right < A.length && maxL <= A[right]) {
                    temp = Math.max(temp, A[right]);
                    right++;
                }
                if (right < A.length) {
                    maxL = temp;
                    right++;
                    partition = right;
                } else {
                    break;
                }
            } else {
                if (right == A.length - 1) break;
                right++;
                partition = right;
            }
        }

        return partition;
    }

    public static void main(String[] args) {
        L915PartitionArrayIntoDisjointIntervals s = new L915PartitionArrayIntoDisjointIntervals();
        int[] arr = {5,0,3,8,6};
        System.out.println(s.partitionDisjoint(arr));
        int[] arr2 = {1,1,1,0,6,12};
        System.out.println(s.partitionDisjoint(arr2));
        int[] arr3 = {6,0,8,30,37,6,75,98,39,90,63,74,52,92,64};
        System.out.println(s.partitionDisjoint(arr3));

    }
}
