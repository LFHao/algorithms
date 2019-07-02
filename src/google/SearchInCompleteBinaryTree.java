package google;

import java.util.Stack;
import utils.TreeNode;

public class SearchInCompleteBinaryTree {
    public boolean exist(TreeNode root, int target) {
        Stack<Integer> stack = new Stack<>();

        while (target != 1) {
            stack.push(target & 1);
            target >>= 1;
        }

        TreeNode cur = root;
        while (!stack.isEmpty()) {
            int mod = stack.pop();
            if (cur == null) return false;
            if (mod == 0) cur = cur.left;
            else cur = cur.right;
        }

        return true;
    }
}
