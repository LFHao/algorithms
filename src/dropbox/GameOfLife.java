package dropbox;

public class GameOfLife {
    // extra space
    public void gameOfLife1(int[][] board) {
        if (board == null || board.length == 0) return;

        int m = board.length, n = board[0].length;
        int[][] copy = new int[m][n];
        for (int i = 0; i < m; i++) {
            copy[i] = board[i].clone();
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                int lives = getLiveNeighbors(copy, i, j, m, n);

                if (copy[i][j] == 1) {
                    if (lives < 2 || lives > 3) {
                        board[i][j] = 0;
                    }
                    // board is dead:
                } else if (copy[i][j] == 0 && lives == 3) {
                    board[i][j] = 1;
                }
            }
        }
    }

    private int getLiveNeighbors(int[][] board, int i, int j, int m, int n) {
        int lives = 0;

        for (int a = Math.max(0, i - 1); a <= Math.min(i + 1, m - 1); a++) {
            for (int b = Math.max(0, j - 1); b <= Math.min(j + 1, n - 1); b++) {

                lives += board[a][b];

            }
        }

        // deduct itself
        lives -= board[i][j] & 1;
        return lives;
    }

    // bit
    public void gameOfLife2(int[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) return;

        int m = board.length;
        int n = board[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int lives = getLives(board, i, j, m, n);

                // In the beginning, every 1st bit is 0; (new state)
                // So we only need to care about when will the 1st bit become 1. (new state)
                // board is live
                // 01 -> 11 (live) or 01 -> 01 (dead)
                if (board[i][j] == 1) {
                    if (lives == 2 || lives == 3) {
                        board[i][j] = 3;
                    }
                    // board is dead
                    // 00 -> 10 (live) or 00 -> 00 (dead)
                } else if (board[i][j] == 0 && lives == 3) {
                    board[i][j] = 2;
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                board[i][j] >>= 1;
            }
        }

    }

    private int getLives(int[][] board, int i, int j, int m, int n) {
        int lives = 0;

        for (int a = Math.max(0, i-1); a <= Math.min(i+1, m-1); a++) {
            for (int b = Math.max(0, j-1); b <= Math.min(j+1, n-1); b++) {
                // need to get the 2nd bit
                lives += board[a][b] & 1;
            }
        }

        lives -= board[i][j] & 1;
        return lives;
    }

}


/**
 Can we still use the same solution that we saw earlier or is there something else we will have to do different?
 If the board becomes infinitely large, there are multiple problems our current solution would run into:

 It would be computationally impossible to iterate a matrix that large.
 It would not be possible to store that big a matrix entirely in memory. We have huge memory capacities these days
 i.e. of the order of hundreds of GBs. However, it still wouldn't be enough to store such a large matrix in memory.
 We would be wasting a lot of space if such a huge board only has a few live cells and the rest of them are all dead.
 In such a case, we have an extremely sparse matrix and it wouldn't make sense to save the board as a "matrix".

 In order for us to update a particular cell, we only have to look at its 8 neighbors which essentially lie in the row
 above and below it. So, for updating the cells of a row, we just need the row above and the row below. Thus, we read
 one row at a time from the file and at max we will have 3 rows in memory. We will keep discarding rows that are
 processed and then we will keep reading new rows from the file, one at a time.
 */
