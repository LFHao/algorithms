package uber;

public class L37SudokuSolver {
    public void solveSudoku(char[][] board) {
        if (board ==  null || board.length == 0) return;

        solveHelper(board);

    }

    private boolean solveHelper(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '.') {
                    for (char c = '1'; c <= '9'; c++) {
                        if (isValid(board, i, j, c)) {
                            board[i][j] = c;
                            // if this c can be filled in board[i][j], we use dfs to see if it can solve
                            if (solveHelper(board)) return true;
                            board[i][j] = '.';
                        }
                    }
                    // if no number can be filled, return false
                    return false;
                }
            }
        }
        return true;
    }


    private boolean isValid(char[][] board, int row, int col, char c) {
        // only need to check the row, the column and the square
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == c) return false;
            if (board[i][col] == c) return false;
            if (board[(row / 3) * 3 + i / 3][(col / 3) * 3 + i % 3] == c) return false;
        }
        return true;
    }
}
