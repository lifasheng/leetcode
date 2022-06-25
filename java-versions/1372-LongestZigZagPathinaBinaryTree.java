/*
You are given the root of a binary tree.

A ZigZag path for a binary tree is defined as follow:

Choose any node in the binary tree and a direction (right or left).
If the current direction is right, move to the right child of the current node; otherwise, move to the left child.
Change the direction from right to left or from left to right.
Repeat the second and third steps until you can't move in the tree.
Zigzag length is defined as the number of nodes visited - 1. (A single node has a length of 0).

Return the longest ZigZag path contained in that tree.

Input: root = [1,null,1,1,1,null,null,1,1,null,1,null,null,null,1,null,1]
Output: 3
Explanation: Longest ZigZag path in blue nodes (right -> left -> right).


Input: root = [1,1,1,null,1,null,null,1,1,null,1]
Output: 4
Explanation: Longest ZigZag path in blue nodes (left -> right -> left -> right).

Example 3:
Input: root = [1]
Output: 0

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
    private int longestLength = 0;
    
    // starting from root
    // use a boolean to mark if current node is from parent's left or right
    // so when we go do to current node's child, we can know if it is a zigzag and add length
    public int longestZigZag(TreeNode root) {
        if (root == null) return 0;
        preOrder(root.left, true, 1);
        preOrder(root.right, false, 1);
        return longestLength;
    }
    
    private void preOrder(TreeNode node, boolean fromLeft, int length) {
        if (node == null) return;
        
        longestLength = Math.max(longestLength, length);
        
        if (fromLeft) {
            preOrder(node.left, true, 1); // reset to 1
            preOrder(node.right, false, length + 1); // zigzag
        } else {
            preOrder(node.left, true, length + 1); // zigzag
            preOrder(node.right, false, 1); // reset to 1
        }
    }
}


