package uber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class L347TopKFrequentElements {
    /**
     * Bucket Sort, which is O(N)
     * @param nums
     * @param k
     * @return
     */
    public List<Integer> topKFrequent(int[] nums, int k) {
        List<Integer>[] freqBucket = new List[nums.length + 1];

        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int n : nums) {
            freqMap.put(n, freqMap.getOrDefault(n, 0) + 1);
        }

        for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            int freq = entry.getValue();
            if (freqBucket[freq] == null) freqBucket[freq] = new ArrayList<>();
            freqBucket[freq].add(entry.getKey());
        }

        List<Integer> res = new ArrayList<>();
        for (int i = freqBucket.length - 1; i >= 0 && res.size() < k; i--) {
            if (freqBucket[i] != null) {
                res.addAll(freqBucket[i]);
            }
        }

        return res;
    }
}
