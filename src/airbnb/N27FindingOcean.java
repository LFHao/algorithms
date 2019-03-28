package airbnb;

import java.util.ArrayList;
import java.util.List;

public class N27FindingOcean {
    int[][] dir = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    public void floodFill(char[][] board, int i, int j, char oldColor, char newColor) {
        dfsFill(board, i, j, oldColor, newColor);
    }

    private void dfsFill(char[][] board, int i, int j, char oldColor, char newColor) {
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || board[i][j] != oldColor || board[i][j] == newColor) {
            return;
        }

        board[i][j] = newColor;
        for (int d = 0; d < dir.length; d++) {
            dfsFill(board, i + dir[d][0], j + dir[d][1], oldColor, newColor);
        }
    }

    public static void main(String[] args) {
        List<String> testData = new ArrayList<String>() {{
            add("WWWLLLW");
            add("WWLLLWW");
            add("WLLLLWW");
        }};
        char[][] board = new char[testData.size()][testData.get(0).length()];
        enrichBoard(board, testData);

        System.out.println("Before");
        printBoard(board);

        N27FindingOcean fo = new N27FindingOcean();
        fo.floodFill(board, 0, 0, 'W', 'O');

        System.out.println("After");
        printBoard(board);

        testData = new ArrayList<String>() {{
            add("LLLLLLLLLLLLLLLLLLLL");
            add("LLLLLLLLLLLLLLLLLLLL");
            add("LLLLLLLLLLLLLLWLLLLL");
            add("LLWWLLLLLLLLLLLLLLLL");
            add("LLWWLLLLLLLLLLLLLLLL");
            add("LLLLLLLLLLLLLLLLLLLL");
            add("LLLLLLLWWLLLLLLLLLLL");
            add("LLLLLLLLWWLLLLLLLLLL");
            add("LLLLLLLLLWWWLLLLLLLL");
            add("LLLLLLLLLLWWWWWWLLLL");
            add("LLLLLLLLLLWWWWWWLLLL");
            add("LLLLLLLLLLWWWWWWLLLL");
            add("LLLLWWLLLLWWWWWWLLLL");
            add("LLLLWWWLLLWWWWWWWWWW");
            add("LLLLLWWWWWWWWWWWLLLL");
            add("LLLLLLLLLLLLLLWWWWLL");
            add("LLLLLLLLLLLLLLWLLLLL");
            add("LLLLWLLLLLLLLLLLLWLL");
            add("LLLLLLLLLLLLLLLLLLWL");
        }};

        char[][] board2 = new char[testData.size()][testData.get(0).length()];
        enrichBoard(board2, testData);

        System.out.println("Before");
        printBoard(board2);

        fo.floodFill(board2, 9, 12, 'W', 'O');
        System.out.println("After");
        printBoard(board2);
    }

    private static void enrichBoard(char[][] board, List<String> testData) {
        for (int i = 0; i < testData.size(); i++) {
            for (int j = 0; j < testData.get(i).length(); j++) {
                board[i][j] = testData.get(i).charAt(j);
            }
        }
    }

    private static void printBoard(char[][] board) {
        for(char[] lines : board) {
            System.out.println(new String(lines));
        }
    }

}
