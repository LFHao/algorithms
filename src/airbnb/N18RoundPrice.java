package airbnb;

import java.util.Arrays;
import java.util.Comparator;


/**
 * 做法是：比如[1.2, 2.5, 3.6, 4.0]
 * 建个新数列是原来数字的int, arr = [1, 2, 3, 4]
 * 然后算需要补多少个数字才能到需要的sum. round(1.2+2.5+3.6+4.0) - (1+2+3+4) = 1， 补1 个数字
 * 就好
 * 现在把原数组arr的差排列一下。 差是[0.2, 0.5, 0.6, 0]. 要从差最大的
 * index 排列了一下就是[2, 1, 0, 3]，按这个顺序补数字就好。
 * 我们只需要补一个数字，先从index = 2, 所以最后的结果是 [1, 2, 4, 4]。
 */
public class N18RoundPrice {
    public int[] roundPrice(float[] prices) {
        int[] floorArr = new int[prices.length];
        PriceFloorDiff[] floorDiff = new PriceFloorDiff[prices.length];

        float sum = 0f;
        int floorSum = 0;
        for (int i = 0; i < prices.length; i++) {
            floorArr[i] = (int) Math.floor(prices[i]);
            floorDiff[i] = new PriceFloorDiff(prices[i] - floorArr[i], i);
            sum += prices[i];
            floorSum += floorArr[i];

        }

        int roundDiff = Math.round(sum) - floorSum;
        Arrays.sort(floorDiff, Comparator.comparing((PriceFloorDiff pfd) -> -pfd.floorDiff));

        int[] res = new int[prices.length];
        for (PriceFloorDiff pfd : floorDiff) {
            if (roundDiff > 0) {
                res[pfd.index] = floorArr[pfd.index] + 1;
                roundDiff--;
            } else {
                res[pfd.index] = floorArr[pfd.index];
            }
        }
        return res;
    }

    public static void main(String[] args) {
        N18RoundPrice rp = new N18RoundPrice();
        float[] prices = {1.2f, 2.5f, 3.6f, 4.0f};
        int[] res = rp.roundPrice(prices);
        for (int r : res) {
            System.out.println(r);
        }
    }

}

class PriceFloorDiff {
    float floorDiff;
    int index;
    PriceFloorDiff(float floorDiff, int index) {
        this.floorDiff = floorDiff;
        this.index = index;
    }
}
