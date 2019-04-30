package google;

import java.util.BitSet;

public class L379PhoneDirectory {
    /**
     * User Bitset
     */
    class PhoneDirectory {
        final BitSet bitSet;
        final int maxNumbers;

        /** Initialize your data structure here
         @param maxNumbers - The maximum numbers that can be stored in the phone directory. */
        public PhoneDirectory(int maxNumbers) {
            this.maxNumbers = maxNumbers;
            bitSet = new BitSet(this.maxNumbers);
        }

        /** Provide a number which is not assigned to anyone.
         @return - Return an available number. Return -1 if none is available. */
        public int get() {
            int n = bitSet.nextClearBit(0);
            if (n == maxNumbers) return -1;

            // set 0 to 1 or 1 to 0
            bitSet.flip(n);
            return n;
        }

        /** Check if a number is available or not. */
        public boolean check(int number) {
            // if it is set, then this number is not available
            return !bitSet.get(number);
        }

        /** Recycle or release a number. */
        public void release(int number) {
            //Sets the bit specified by the index to false
            bitSet.clear(number);
        }
    }

/**
 * Your PhoneDirectory object will be instantiated and called as such:
 * PhoneDirectory obj = new PhoneDirectory(maxNumbers);
 * int param_1 = obj.get();
 * boolean param_2 = obj.check(number);
 * obj.release(number);
 */
}
