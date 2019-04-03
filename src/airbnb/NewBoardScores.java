package airbnb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewBoardScores {
    int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    public int boardScores(List<String> input) {
        String[][] board = parseBoard(input);

        List<Area> areas = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                char c = board[i][j].charAt(0);
                if (Character.isLetter(c)) {
                    List<Integer> info = new ArrayList<>();
                    info.add(0);
                    info.add(0);
                    dfs(board, c, info,  i , j);
                    areas.add(new Area(c, info.get(0), info.get(1)));
                }
            }
        }

        int sum = 0;
        for (Area a : areas) {
            sum += a.crown * a.area;
        }

        return sum;
    }

    private void dfs(String[][] board, char c, List<Integer> info, int i, int j) {
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || board[i][j].charAt(0) != c) {
            return;
        }

        info.set(0, info.get(0) + 1);

        info.set(1, info.get(1) + Integer.valueOf(board[i][j].substring(1)));
        board[i][j] = "#";
        for(int[] dir : dirs) {
            dfs(board, c, info,i + dir[0], j + dir[1]);
        }
    }

    private String[][] parseBoard(List<String> input) {
        String[][] board = new String[input.size()][input.get(0).length()];

        for (int i = 0; i < input.size(); i++) {
            board[i] = input.get(i).split(" ");
        }

        return board;
    }

    public static void main(String[] args) {
        List<String> input = new ArrayList<>(Arrays.asList(new String[]{
                "S0 W1 W1 W0 L2",
                "W0 W0 T0 T0 T0",
                "W0 W1 T0 M2 M1",
                "S0 L0 S1 S0 S0",
                "M0 R2 R0 R1 T0"}));
        System.out.println(new NewBoardScores().boardScores(input));

        input = new ArrayList<>(Arrays.asList(new String[]{
                "W2 W0 L1",
                "W1 L2 L100"}));
        System.out.println(new NewBoardScores().boardScores(input));
    }
}

class Area {
    char type;
    int area;
    int crown;

    Area(char type, int area, int crown) {
        this.type = type;
        this.area = area;
        this.crown = crown;
    }
}
