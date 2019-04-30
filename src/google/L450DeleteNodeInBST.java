package google;

import utils.TreeNode;

/**
 * https://leetcode.com/problems/delete-node-in-a-bst/discuss/93296/Recursive-Easy-to-Understand-Java-Solution
 */
public class L450DeleteNodeInBST {
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) return null;

        if (key < root.val) {
            root.left = deleteNode(root.left, key);
        } else if (key > root.val) {
            root.right = deleteNode(root.right, key);
        } else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            } else {
                int findMinInRight = findMinRight(root.right);
                root.val = findMinInRight;
                root.right = deleteNode(root.right, findMinInRight);
            }
        }
        return root;
    }

    private int findMinRight(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node.val;
    }
}
