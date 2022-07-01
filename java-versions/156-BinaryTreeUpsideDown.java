/*
Given the root of a binary tree, turn the tree upside down and return the new root.

You can turn a binary tree upside down with the following steps:

The original left child becomes the new root.
The original root becomes the new right child.
The original right child becomes the new left child.

The mentioned steps are done level by level. It is guaranteed that every right node has a sibling (a left node with the same parent) and has no children.

Example 1:
Input: root = [1,2,3,4,5]
Output: [4,5,2,null,null,3,1]

Example 2:
Input: root = []
Output: []

Example 3:
Input: root = [1]
Output: [1]
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
    public TreeNode upsideDownBinaryTree(TreeNode root) {
        if (root == null) return null;
        if (root.left == null && root.right == null) return root;
        
        TreeNode left = root.left;
        TreeNode right = root.right;
        TreeNode leftRoot = upsideDownBinaryTree(root.left);
        TreeNode rightRoot = upsideDownBinaryTree(root.right);
        left.right = root;
        left.left = rightRoot;
        root.left = null;
        root.right = null;
        return leftRoot;
    }
}



