package google;

import java.util.Stack;
import utils.TreeNode;

public class L98ValidateBinarySearchTree {
    /**
     * Recursive Solution
     * @param root
     * @return
     */
    public boolean isValidBST(TreeNode root) {
        return helper(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean helper(TreeNode node, int min, int max) {
        if (node == null) return true;
        if (node.val <= min || node.val >= max) return false;
        return helper(node.left, min, node.val) && helper(node.right, node.val, max);
    }

    /**
     * Iterative Solution
     * @param root
     * @return
     */
    public boolean isValidBST2(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();

        TreeNode pre = null;
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            // go to the left until the leftmost
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            // popup the leftmost node unvisited
            cur = stack.pop();
            // pre is either left of cur or cur is right of pre or pre is the root and cur is the leftmost in root's right
            if (pre != null && cur.val <= pre.val) return false;
            pre = cur;
            cur = cur.right;
        }

        return true;
    }
}
