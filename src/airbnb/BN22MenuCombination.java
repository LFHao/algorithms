package airbnb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BN22MenuCombination {
    public List<List<Double>> getCombos(double[] prices, double target) {
        List<List<Double>> res = new ArrayList<>();
        if (prices == null || target <= 0 || prices.length == 0) return res;

        int centTarget = (int) Math.round(target * 100);
        int[] centPrices = new int[prices.length];
        Arrays.sort(prices);

        for (int i = 0; i < prices.length; i++) {
            centPrices[i] = (int) Math.round(prices[i] * 100);
        }

        search(res, new ArrayList<>(), centPrices, prices, centTarget, 0);
        return res;

    }

    private void search(List<List<Double>> res, List<Double> curComb, int[] centPrices, double[] prices, int centTarget, int start) {
        if (centTarget == 0) {
            res.add(new ArrayList<>(curComb));
            return;
        }

        if (centTarget < 0) return;

        for (int i = start; i < centPrices.length; i++) {
            if (i > start && centPrices[i] == centPrices[i - 1]) {
                continue;
            }

//            if (centPrices[i] > centTarget) {
//                break;
//            }

            curComb.add(prices[i]);
            search(res, curComb, centPrices, prices, centTarget - centPrices[i], i + 1);
            curComb.remove(curComb.size() - 1);
        }
    }

    public static void main(String[] args) {
        BN22MenuCombination mc = new BN22MenuCombination();
        double[] prices = {10.02, 1.11, 2.22, 3.01, 4.02, 2.00, 5.03};
        List<List<Double>> combos = mc.getCombos(prices, 7.03);
        System.out.println(combos);
        System.out.println(combos.size());
    }
}
