/*
Given the root of a binary tree, return the length of the longest consecutive sequence path.

The path refers to any sequence of nodes from some starting node to any node in the tree along the parent-child connections. The longest consecutive path needs to be from parent to child (cannot be the reverse).

Example 1:
Input: root = [1,null,3,2,4,null,null,null,5]
Output: 3
Explanation: Longest consecutive sequence path is 3-4-5, so return 3.

Example 2:
Input: root = [2,null,3,2,null,1]
Output: 2
Explanation: Longest consecutive sequence path is 2-3, not 3-2-1, so return 2.
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
    
    private int maxLen;
    public int longestConsecutive(TreeNode root) {
        if (root == null) return 0;
        this.maxLen = 1;
        preorder(root.left, root.val, 1);
        preorder(root.right, root.val, 1);
        return maxLen;
    }
    
    private void preorder(TreeNode node, int parentVal, int len) {
        if (node == null) return;
        
        if (node.val == parentVal + 1) {
            len ++;
        } else {
            len = 1;
        }
        
        maxLen = Math.max(len, maxLen);
        preorder(node.left, node.val, len);
        preorder(node.right, node.val, len);
       
    }
}
