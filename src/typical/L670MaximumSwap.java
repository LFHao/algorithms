package typical;

public class L670MaximumSwap {
    public int maximumSwap(int num) {
        String numString = String.valueOf(num);
        int i = 0;
        int a = -1; int b = -1; int max = -1;
        int min = numString.length();

        while (i < numString.length() - 1) {
            char c = numString.charAt(i);
            char next = numString.charAt(i + 1);
            if (c == next && a == -1) {
                min = Math.min(i, min);
            }
            if ((next == '0' || c < next) && a == -1) {
                a = Math.min(i, min);
                max = next == '0'? c - '0' : next - '0';
            }
            if (a > -1 && (next - '0') >= max) {
                b = i + 1;
                max = next - '0';
            }
            i++;
        }

        if (a == -1 || b == -1) return num;
        return swap(numString, a, b);

    }

    private int swap(String num, int a, int b) {
        char[] numArr = num.toCharArray();
        char temp = numArr[a];
        numArr[a] = numArr[b];
        numArr[b] = temp;
        return Integer.parseInt(new String(numArr));

    }

    public static void main(String[] args) {
        L670MaximumSwap s = new L670MaximumSwap();
//        System.out.println(s.maximumSwap(9973));
        System.out.println(s.maximumSwap(99901));

    }
}
