package typical.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class L39CombinationSum {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        if (candidates == null || candidates.length == 0) return res;

        Arrays.sort(candidates);
        backtrack(candidates, target, res, new ArrayList<>(), 0);
        return res;
    }

    private void backtrack(int[] candidates, int target, List<List<Integer>> res, List<Integer> list, int start) {
        if (target == 0) {
            res.add(new ArrayList<>(list));
            return;
        }

        if (start >= candidates.length) return;

        int k = target / candidates[start];
        for (int i = start; i < candidates.length; i++) {
            for (int j = 1; j < k; j++) {
                list.add(candidates[i]);
                backtrack(candidates, target - candidates[i] * j, res, list, i + 1);
                list.remove(list.size() - 1);
            }
        }
    }

    public static void main(String[] args) {

    }
}
