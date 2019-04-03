package airbnb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BN24BoggleGame {
    public List<String> findWords(char[][] board, String[] words) {
        TrieNode root = buildTrie(words);

        Map<Integer, List<String>> map = new HashMap<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                dfs(board, i, j, root, root, new ArrayList<>(), 0, map);
            }
        }

        int maxCount = Integer.MIN_VALUE;
        for (int c : map.keySet()) {
            maxCount = Math.max(maxCount, c);
        }

        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }

        return maxCount != Integer.MIN_VALUE ? new ArrayList(map.get(maxCount)) : new ArrayList<>();
    }

    private void dfs(char[][] board, int i, int j, TrieNode root, TrieNode node, List<String> res, int count,
                     Map<Integer, List<String>> map) {
        if (i < 0 || j < 0 || i > board.length - 1 || j > board[0].length - 1) {
            return;
        }

        char c = board[i][j];
        if (c == '#' || node.next[c - 'a'] == null) {
            return;
        }

        node = node.next[c - 'a'];
        TrieNode temp = new TrieNode();
        board[i][j] = '#';
        // already to the leaf
        // Clarify if the res can contain duplicates
        if (node.word != null) {
            res.add(node.word);
            count++;
            // temp node is for next time when res should be added into map
            temp = node;
            // if find 1 word, continue to search but TrieNode should be root
            node = root;
        }

        dfs(board, i - 1, j, root, node, new ArrayList<>(res), count, map);
        dfs(board, i + 1, j, root, node, new ArrayList<>(res), count, map);
        dfs(board, i, j - 1, root, node, new ArrayList<>(res), count, map);
        dfs(board, i, j + 1, root, node, new ArrayList<>(res), count, map);

        // TO here, this path is done to the end
        if (count > 0 && temp.word != null) {
            map.put(count, res);
        }

        board[i][j] = c;
    }

    private TrieNode buildTrie(String[] words) {

        TrieNode root = new TrieNode();

        for (String word : words) {
            // build from root for every word
            TrieNode p = root;
            for (char c : word.toCharArray()) {
                int i = c - 'a';
                if (p.next[i] == null) {
                    p.next[i] = new TrieNode();
                }
                p = p.next[i];
            }
            // to the leaf
            p.word = word;
        }
        return root;
    }

    class TrieNode {
        TrieNode[] next = new TrieNode[26];
        String word;
    }

    public static void main(String[] args) {
        char[][] board = {
                {'o', 'a', 't', 'h'},
                {'p', 't', 'a', 'e'},
                {'e', 'h', 'k', 'r'},
                {'a', 'f', 'l', 'v'}
        };
        String[] words = {"oath", "erv", "fl", "vr"};
        System.out.println(new BN24BoggleGame().findWords(board, words));
    }
}
