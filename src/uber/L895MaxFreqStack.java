package uber;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * https://leetcode.com/problems/maximum-frequency-stack/discuss/163410/C%2B%2BJavaPython-O(1)
 * stackMap is to store for each frequency, what numbers are still in stack
 * freqMap is to store for each number, how many frequency is it
 *
 * 1. push(5), the stackMap is {1, {5}}, freqMap is {5, 1}
 * 2. push(5), the stackMap is {{1, {5}}, {2, {5}}}, freqMap is {5, 2}
 * 3. push(7), the stackMap is {{1, {7, 5}}, {2, {5}}} freqMap is {{5, 2}, {7, 1}}
 * 4. push(5), the stackMap is {{1, {7, 5}}, {2, {5}}, {3, {5}}} freqMap is {{5, 3}, {7, 1}}
 */
public class L895MaxFreqStack {
    static class FreqStack {
        Map<Integer, Stack<Integer>> stackMap;
        Map<Integer, Integer> freqMap;
        int maxFreq;

        FreqStack() {
            stackMap = new HashMap<>();
            freqMap = new HashMap<>();
            maxFreq = 0;
        }

        public void push(int x) {
            // update x freq in freqMap
            int freq = freqMap.getOrDefault(x, 0) + 1;
            freqMap.put(x, freq);
            maxFreq = Math.max(maxFreq, freq);

            // update stackMap
            Stack<Integer> fStack = stackMap.getOrDefault(freq, new Stack<>());
            fStack.push(x);
            stackMap.put(freq, fStack);
        }

        public int pop() {
            // we want to retrieve the one with maxFreq
            int x = stackMap.get(maxFreq).pop();
            // update freqMap
            freqMap.put(x, freqMap.get(x) - 1);
            // if there is no other number has the maxFreq, we are going to look for the next freq (maxFreq - 1)
            if (stackMap.get(maxFreq).size() == 0) maxFreq--;
            return x;
        }
    }

    public static void main(String[] args) {
        FreqStack freqStack = new FreqStack();
        freqStack.push(5);
        freqStack.push(5);
        freqStack.push(7);
        freqStack.push(5);
        System.out.println(freqStack.pop());
        System.out.println(freqStack.pop());
        System.out.println(freqStack.pop());
        System.out.println(freqStack.pop());
    }

}
