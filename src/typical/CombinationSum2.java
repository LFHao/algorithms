package typical;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Time: O(2^n), Space: O(n)
 */
public class CombinationSum2 {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        if (candidates == null || candidates.length == 0) return res;

        Arrays.sort(candidates);
        backtrack(candidates, target, 0, new ArrayList<>(), res);
        return res;
    }

    private void backtrack(int[] candidates, int target, int index, List<Integer> list, List<List<Integer>> res) {
        if (index > candidates.length || target < 0) return;

        if (target == 0) {
            res.add(new ArrayList<>(list));
            return;
        }

        for (int i = index; i < candidates.length; i++) {
            if (target < candidates[i]) continue;
            // it means this 2 combination has been account already
            if (i > index && candidates[i] == candidates[i - 1]) continue;
            list.add(candidates[i]);
            backtrack(candidates, target - candidates[i], i + 1, list, res);
            list.remove(list.size() - 1);
        }
    }

    public static void main(String[] args) {
        CombinationSum2 s = new CombinationSum2();
        int[] c = {2,5,2,1,2};
        System.out.println(s.combinationSum2(c, 5));
    }
 }
