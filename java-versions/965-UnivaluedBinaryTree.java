/*
A binary tree is uni-valued if every node in the tree has the same value.

Given the root of a binary tree, return true if the given tree is uni-valued, or false otherwise.

Input: root = [1,1,1,1,1,null,1]
Output: true

Input: root = [2,2,2,5,2]
Output: false
*/


/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public boolean isUnivalTree(TreeNode root) {
        return isUnivalTree(root, root.val);
    }
    private boolean isUnivalTree(TreeNode root, int val) {
        if (root == null) return true;
        if (root.val != val) return false;
        return isUnivalTree(root.left, val) && isUnivalTree(root.right, val);
    }
}


