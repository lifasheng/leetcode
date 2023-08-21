/*
Given the root of a binary tree, determine if it is a valid binary search tree (BST).
A valid BST is defined as follows:

The left subtree of a node contains only nodes with keys less than the node's key.
The right subtree of a node contains only nodes with keys greater than the node's key.
Both the left and right subtrees must also be binary search trees.
 

Example 1:
Input: root = [2,1,3]
Output: true

Example 2:
Input: root = [5,1,4,null,null,3,6]
Output: false
Explanation: The root node's value is 5 but its right child's value is 4.

Constraints:
The number of nodes in the tree is in the range [1, 104].
-231 <= Node.val <= 231 - 1
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
    // [-2147483648,null,2147483647]
    public boolean isValidBST(TreeNode root) {
        //return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);

        return isValidBST(root, null, null);
    }

    // solution 1: use treeNode to record min and max
    private boolean isValidBST(TreeNode root, TreeNode min, TreeNode max) {
        if (root == null) return true;

        if (min != null && root.val <= min.val) return false;
        if (max != null && root.val >= max.val) return false;

        return isValidBST(root.left, min, root)
        && isValidBST(root.right, root, max);
    }

    // solution 1: use long to record min and max
    private boolean isValidBST(TreeNode root, long minValue, long maxValue) {
        if (root == null) return true;

        return (minValue < root.val && root.val < maxValue)
        && isValidBST(root.left, minValue, root.val)
        && isValidBST(root.right, root.val, maxValue);
    }

}



