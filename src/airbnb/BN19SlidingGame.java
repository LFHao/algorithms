package airbnb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Question:
 * You're given a 3x3 board of a tile puzzleState, with 8 tiles numbered 1 to 8, and an empty spot. You can move any tile
 * adjacent to the empty spot, to the empty spot, creating an empty spot where the tile originally was. The goal is
 * to find a series of moves that will solve the board, i.e. get [ [1, 2, 3], [4, 5, 6], [7, 8, - ]…
 *
 * A* Search Algorithm: https://blog.csdn.net/hitwhylz/article/details/23089415
 * Solution: https://jogchat.com/leetcode_companies/airbnb/The%208-Puzzle%20Game%EF%BC%88Airbnb%20Hard%EF%BC%89.html
 * https://blog.goodaudience.com/solving-8-puzzle-using-a-algorithm-7b509c331288
 */
public class BN19SlidingGame {
    private int[][] dirs = {{1, 0}, {0, -1}, {-1, 0}, {0, 1}};
    int m;
    int n;
    private PuzzleState puzzleState;
    private PuzzleState goalPuzzleState;
    private Map<Integer, String> stepMap;

    BN19SlidingGame(int m, int n, int[][] originalPuzzle, int[][] goalPuzzle) {
        this.m = m;
        this.n = n;
        this.puzzleState = new PuzzleState(originalPuzzle);
        this.goalPuzzleState = new PuzzleState(goalPuzzle);
        this.stepMap = new HashMap<>();
        this.stepMap.put(3, "Up");
        this.stepMap.put(-3, "Down");
        this.stepMap.put(-1, "Right");
        this.stepMap.put(1, "Left");
    }

    /**
     * Use BFS to check if the puzzleState is solvable step by step (step by step/ level by level is BFS)
     * @return
     */
    public boolean canSolve() {
        String goalString = puzzleToString(this.goalPuzzleState.matrix);

        Queue<int[]> positions = new LinkedList<>();
        Queue<String> puzzleStrings = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        positions.offer(this.puzzleState.zeroPosition);
        puzzleStrings.offer(puzzleToString(this.puzzleState.matrix));

        while (!positions.isEmpty()) {
            int size = positions.size();
            for (int i = 0; i < size; i++) {
                String puzzleString = puzzleStrings.poll();

                if (puzzleString.equals(goalString)) {
                    return true;
                }

                int[] position = positions.poll();
                int x = position[0];
                int y = position[1];
                for (int k =0; k < dirs.length; k++) {
                    int nextX = x + dirs[k][0];
                    int nextY = y + dirs[k][1];

                    if (isValidPosition(nextX, nextY)) {
                        int[] nextPosition = {nextX, nextY};
                        int[][] nextPuzzle = stringToPuzzle(puzzleString);
                        swap(nextPuzzle, position, nextPosition);
                        String nextPuzzleString = puzzleToString(nextPuzzle);
                        if (visited.contains(nextPuzzleString)) {
                            continue;
                        }
                        positions.offer(nextPosition);
                        puzzleStrings.offer(nextPuzzleString);
                        visited.add(nextPuzzleString);
                    }
                }
            }
        }

        return false;
    }

    /**
     * Use A* to get it solved
     */
    public void solvePuzzle() {
        String goalString = puzzleToString(this.goalPuzzleState.matrix);
        String currString = puzzleToString(this.puzzleState.matrix);

        Queue<PuzzleState> queue = new PriorityQueue<>();
        // this records visited puzzleToString
        Map<String, PuzzleState> visited = new HashMap<>();

        int step = 0;
        this.puzzleState.minCost = 0 + calculateHammingCost(this.puzzleState.matrix, this.goalPuzzleState.matrix);
        queue.offer(this.puzzleState);


        while (!queue.isEmpty()) {
            PuzzleState current = queue.poll();
            String puzzleString = puzzleToString(current.matrix);
            visited.put(puzzleString, current);

            if (current.minCost == 0) {
                break;
            }

            step++;
            for (int[] dir : dirs) {
                int nextX = current.zeroPosition[0] + dir[0];
                int nextY = current.zeroPosition[1] + dir[1];

                if (isValidPosition(nextX, nextY)) {
                    int[] nextPosition = {nextX, nextY};
                    int[][] nextPuzzleMatrix = stringToPuzzle(puzzleString);
                    swap(nextPuzzleMatrix, current.zeroPosition, nextPosition);
                    String nextPuzzleString = puzzleToString(nextPuzzleMatrix);
                    PuzzleState nextPuzzleState = new PuzzleState(nextPuzzleMatrix);

                    if (!visited.containsKey(nextPuzzleString) && step + calculateHammingCost(nextPuzzleMatrix, this.goalPuzzleState.matrix) < nextPuzzleState.minCost) {
                        nextPuzzleState.minCost = step + calculateHammingCost(nextPuzzleMatrix, this.goalPuzzleState.matrix);
                        nextPuzzleState.prevPuzzle = puzzleString;
                        queue.offer(nextPuzzleState);
                        visited.put(nextPuzzleString, nextPuzzleState);
                    }
                }
            }
        }

        List<String> res = new ArrayList<>();
        String temp = goalString;
        while (visited.containsKey(temp) && !temp.equals(currString)) {
            String prev = visited.get(temp).prevPuzzle;
            int diff = prev.indexOf('0') - temp.indexOf('0');
//            System.out.println(temp + " , prev = " + visited.get(temp).prevPuzzle + " , diff = " + diff);
            res.add(stepMap.get(diff));
            temp = prev;
        }

        Collections.reverse(res);

        System.out.println(res.size());
        System.out.println(res);

    }


    private boolean isValidPosition(int x, int y) {
        return x >= 0 && y >= 0 && x < this.m && y < this.n;
    }

    private String puzzleToString(int[][] puzzle) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[0].length; j++) {
                sb.append(puzzle[i][j]);
            }
        }

        return sb.toString();
    }

    private int[][] stringToPuzzle(String string) {
        int k = 0;
        int[][] newPuzzle = new int[this.m][this.n];
        for (int i = 0; i < newPuzzle.length; i++) {
            for (int j = 0; j < newPuzzle[0].length; j++) {
                newPuzzle[i][j] = Character.getNumericValue(string.charAt(k++));
            }
        }
        return newPuzzle;
    }

    private void swap(int[][] newPuzzle, int[] oldPosition, int[] newPosition) {
        int temp = newPuzzle[newPosition[0]][newPosition[1]];
        newPuzzle[newPosition[0]][newPosition[1]] = 0;
        newPuzzle[oldPosition[0]][oldPosition[1]] = temp;
    }

    /**
     * we can calculate the h-score by comparing the initial(current) state and goal state and counting the number of misplaced tiles.
     * @param currentState
     * @param finalState
     * @return
     */
    public int calculateHammingCost(int[][] currentState, int[][] finalState) {
        int cost = 0;
        for(int i=0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(currentState[i][j]!= 0 && currentState[i][j] != finalState[i][j]) {
                    cost++;
                }
            }
        }
        return cost;
    }//calculate the cost using Hamming distance

    public static void main(String[] args) {
        int[][] matrix = {
                {3, 1, 4},
                {6, 2, 0},
                {7, 5, 8}
        };

        int[][] last = {
                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8}
        };

        BN19SlidingGame game = new BN19SlidingGame(3, 3, matrix, last);
        System.out.println(game.canSolve());
        game.solvePuzzle();
    }


    class PuzzleState implements Comparable<PuzzleState> {
        int[][] matrix;
        int[] zeroPosition;
        // min cost from this puzzleState to the goal puzzleState, we don't need min cost from original to this one because
        // we assume every step costs the same
        int minCost;
        String prevPuzzle;

        PuzzleState(int[][] originalPuzzle) {
            this.matrix = originalPuzzle;
            zeroPosition = new int[2];
            this.minCost = Integer.MAX_VALUE;
            this.prevPuzzle = puzzleToString(originalPuzzle);

            for (int i = 0; i < originalPuzzle.length; i++) {
                for (int j = 0; j < originalPuzzle[0].length; j++) {
                    if (originalPuzzle[i][j] == 0) {
                        this.zeroPosition[0] = i;
                        this.zeroPosition[1] = j;
                    }
                }
            }
        }

        @Override
        public int compareTo(PuzzleState p2) {
            return this.minCost - p2.minCost;
        }
    }
}

