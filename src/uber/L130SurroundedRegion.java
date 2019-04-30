package uber;

import java.util.Arrays;

public class L130SurroundedRegion {
    int[][] dirs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    public void solve(char[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) return;

        int m = board.length;
        int n = board[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if ((i == 0 || i == m - 1|| j == 0 || j == n - 1) && board[i][j] == 'O') {
                    solveHelper(board, i, j);
                }
            }
        }

        // mark * back to O, and remaining can be marked as X
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == '*') {
                    board[i][j] = 'O';
                } else {
                    board[i][j] = 'X';
                }
            }
        }
    }

    // from border to inside, mark all 'O' to '*' until it meets 'X'
    private void solveHelper(char[][] board, int i, int j) {
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || board[i][j] != 'O') return;
        board[i][j] = '*';

        for (int[] dir : dirs) {
            solveHelper(board, i + dir[0], j + dir[1]);
        }
    }

    public static void main(String[] args) {
        char[][] board = {"XXXX".toCharArray(), "XOOX".toCharArray(), "XXOX".toCharArray(), "XOXX".toCharArray()};
        L130SurroundedRegion s = new L130SurroundedRegion();
        s.solve(board);
        for (int i = 0; i < board.length; i++) {
            System.out.println(Arrays.toString(board[i]));
        }
    }

}
