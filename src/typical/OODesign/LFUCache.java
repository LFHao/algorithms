package typical.OODesign;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class LFUCache {
    private int capacity;
    private int minFreq;
    private Map<Integer, Integer> keyToFreqMap;
    private Map<Integer, LinkedHashMap<Integer, Integer>> freqToEntryMap;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.minFreq = 0;
        keyToFreqMap = new HashMap<>();
        freqToEntryMap = new HashMap<>();
    }

    public int get(int key) {
        if (capacity == 0 || !keyToFreqMap.containsKey(key)) {
            return -1;
        }

        // remove it from last freq list
        int oldFreq = keyToFreqMap.get(key);
        int value = freqToEntryMap.get(oldFreq).get(key);
        freqToEntryMap.get(oldFreq).remove(key);
        freqToEntryMap.computeIfAbsent(oldFreq + 1, m ->
                new LinkedHashMap<>(capacity, 0.75f, true)).put(key, value);

        // update its freq
        keyToFreqMap.put(key, oldFreq + 1);

        if (oldFreq == minFreq && freqToEntryMap.get(oldFreq).size() == 0) minFreq += 1;

        return value;
    }

    public void put(int key, int value) {
        if (capacity == 0) return;
        // we first call get() to see if it exists
        // if it exists, we just need to update its value, get() already takes care of its position
        if (get(key) != -1) {
            freqToEntryMap.get(keyToFreqMap.get(key)).put(key, value);
            return;
        }

        // if it does not exist, we should add into maps
        if (keyToFreqMap.size() == capacity) removeLeastFreq();
        keyToFreqMap.put(key, 1);
        freqToEntryMap.computeIfAbsent(1, m -> new LinkedHashMap<>()).put(key, value);
        minFreq = 1;

    }

    private void removeLeastFreq() {
        // least recent accessed one is in the head
        int keyToRemove = freqToEntryMap.get(minFreq).keySet().iterator().next();
        freqToEntryMap.get(minFreq).remove(keyToRemove);
        keyToFreqMap.remove(keyToRemove);
    }
}
