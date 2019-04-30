package airbnb;

/**
 * Question:
 * 往一个int array 代表海拔的格子里倒水，打印出倒水后的图， input: int[] 海拔， int 水数量， int 倒得位置。
 *
 * Assumption:
 * - handle the water drop by drop。
 * - 水先向左走，走到不能走为止。call it leftMost
 * - 如果leftmost的水比开始点低，leftMost水+1，done
 * - 如果leftmost的水不比开始点低，水向右走，走到不能走为止。call it rightMost
 * - 如果rightmost的水比开始点低，rightMost水+1，done
 * - 如果rightmost的水不比开始点低，leftMost水+1，done
 * - **?? there are no infinitely high walls on the left and right
 *
 */
public class AN14WaterDrop {
    public void dropWater(int[] heights, int index, int water) {
        int[] waters = new int[heights.length];

        while (water > 0) {
            int minHeight = heights[index] + waters[index];
            int minIndex = index;
            int l = index - 1;
            int r = index + 1;

            // to the left
            if (l >=0 && heights[l] + waters[l] < heights[index] + waters[index]) {
                // if heights[l] + waters[l] <= minHeight, it can continue to go left
                while (l >= 0 && heights[l] + waters[l] <= minHeight) {
                    minHeight = Math.min(minHeight, heights[l] + waters[l]);
                    minIndex = l;
                    l--;
                }
                // to the right
            } else if (r < heights.length && heights[r] + waters[r] < heights[index] + waters[index]) {
                // if heights[r] + waters[r] <= minHeight, it can continue to go right
                while (r < heights.length && heights[r] + waters[r] <= minHeight) {
                    minHeight = Math.min(minHeight, heights[r] + waters[r]);
                    minIndex = r;
                    r++;
                }
            }

            water--;
            waters[minIndex]++;
        }

        print(heights, waters);
    }

    private void print(int[] heights, int[] waters) {
        int maxH = 0;
        for (int i = 0; i < heights.length; i++) {
            maxH = Math.max(maxH, heights[i] + waters[i]);
        }

        char[][] res = new char[maxH][heights.length];
        for (int j = 0; j < heights.length; j++) {
            int h = heights[j];
            int i = maxH - 1;
            while(h > 0) {
                res[i][j] = 'X';
                h--;
                i--;
            }

            int w = waters[j];
            while (w > 0) {
                res[i][j] = 'W';
                w--;
                i--;
            }
        }

        for (int i = 0; i < res.length; i++) {
            System.out.println(new String(res[i]));
        }

    }

    public void pourWater(int[] heights, int location, int water) {
        int[] waters = new int[heights.length];
        int pourLocation;

        while (water > 0) {
            int left = location - 1;
            while (left >= 0) {
                if (heights[left] + waters[left] > heights[left + 1] + waters[left + 1]) {
                    break;
                }
                left--;
            }
            if (heights[left + 1] + waters[left + 1] < heights[location] + waters[location]) {
                pourLocation = left + 1;
                waters[pourLocation]++;
                water--;
                continue;
            }

            int right = location + 1;
            while (right < heights.length) {
                if (heights[right] + waters[right] > heights[right - 1] + waters[right - 1]) {
                    break;
                }
                right++;
            }
            if (heights[right - 1] + waters[right - 1] < heights[location] + waters[location]) {
                pourLocation = right - 1;
                waters[pourLocation]++;
                water--;
                continue;
            }

            pourLocation = location;
            waters[pourLocation]++;
            water--;
        }

        print(heights, waters);
    }

    public static void main(String[] args) {
        AN14WaterDrop wd = new AN14WaterDrop();
        int[] heights = {5, 4, 2, 1, 3, 2, 2, 1, 0, 1, 4, 3};

        wd.dropWater(heights, 4, 1);
        System.out.println();
        wd.pourWater(heights, 4, 1);
        System.out.println();

        wd.dropWater(heights, 4, 5);
        System.out.println();
        wd.pourWater(heights, 4, 5);
        System.out.println();

        int[] heights2 = {0, 1, 1};
        wd.dropWater(heights2, 0, 3);
        System.out.println();
        wd.pourWater(heights2, 0, 3);
        System.out.println();


        int[] waterLand = new int[]{5, 4, 2, 1, 2, 3, 2, 1, 0, 1, 2, 4};
        wd.dropWater(waterLand, 5, 20);
        System.out.println();
        wd.pourWater(waterLand, 5, 20);
        System.out.println();

        int[] waterLand2 = new int[]{2, 1, 1, 1, 1, 1, 2};
        wd.dropWater(waterLand2, 3, 2);
        System.out.println();
        wd.pourWater(waterLand2, 3, 2);
        System.out.println();

    }

}
