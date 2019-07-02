package typical.OODesign;

public class L900RLEIterator {
    private int index;
    private int[] A;

    public L900RLEIterator(int[] A) {
        this.index = 0;
        this.A = A;
    }

    public int next(int n) {
        while (index + 1 < A.length && A[index] < n) {
            n -= A[index];
            index += 2;
        }

        int res = -1;
        if (index + 1 < A.length && n >= 0) {
            A[index] -= n;
            res = A[index + 1];
            while (index + 1 < A.length && A[index] == 0) {
                index += 2;
            }
        }

        return res;
    }

    public static void main(String[] args) {
        int[] arr = {3,8,0,9,2,5};
        L900RLEIterator s = new L900RLEIterator(arr);
        System.out.println(s.next(2));
        System.out.println(s.next(1));
        System.out.println(s.next(1));
        System.out.println(s.next(2));
    }
}
