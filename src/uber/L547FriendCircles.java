package uber;

public class L547FriendCircles {
    public int findCircleNum(int[][] M) {
        UnionFind uf = new UnionFind(M.length);

        for (int i = 0; i < M.length; i++) {
            // it can skip itself and previous visited relationship
            for (int j = i + 1; j < M[0].length; j++) {
                if (M[i][j] == 1) {
                    uf.union(i, j);
                }
            }
        }

        return uf.count;
    }

    class UnionFind {
        int[] friends;
        int[] ranks;
        int count;

        UnionFind(int len) {
            friends = new int[len];
            for (int i = 0; i < friends.length; i++) {
                friends[i] = i;
            }

            ranks = new int[len];
            count = len;
        }

        int find(int i) {
            if (i != friends[i]) {
                friends[i] = find(friends[i]);
            }
            return friends[i];
        }

        void union(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);

            if (rootI != rootJ) {
                if (ranks[rootI] > ranks[rootJ]) {
                    friends[rootJ] = friends[rootI];
                } else if (ranks[rootI] < ranks[rootJ]) {
                    friends[rootI] = friends[rootJ];
                } else {
                    friends[rootJ] = friends[rootI];
                    ranks[rootI]++;
                }
                count--;
            }
        }

        int getCount() {
            return count;
        }
    }

    public static void main(String[] args) {
        int[][] M = {{1,1,0},
                {1,1,0},
                {0,0,1}};

        System.out.println(new L547FriendCircles().findCircleNum(M));
    }
}
