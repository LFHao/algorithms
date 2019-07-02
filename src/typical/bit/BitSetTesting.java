package typical.bit;

import java.util.BitSet;

public class BitSetTesting {
    public static void main(String[] args) {
        int i = (((1023-1) >> 6)+1);
        System.out.println(i);
        BitSet bitSet = new BitSet();
        bitSet.set(3);
        System.out.println(bitSet.get(3));
    }
}
