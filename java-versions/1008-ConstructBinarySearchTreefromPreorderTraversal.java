/*
Given an array of integers preorder, which represents the preorder traversal of a BST (i.e., binary search tree), construct the tree and return its root.

It is guaranteed that there is always possible to find a binary search tree with the given requirements for the given test cases.

A binary search tree is a binary tree where for every node, any descendant of Node.left has a value strictly less than Node.val, and any descendant of Node.right has a value strictly greater than Node.val.

A preorder traversal of a binary tree displays the value of the node first, then traverses Node.left, then traverses Node.right.

Example 1:
Input: preorder = [8,5,1,7,10,12]
Output: [8,5,10,1,7,null,12]
Example 2:

Input: preorder = [1,3]
Output: [1,null,3]
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
    public TreeNode bstFromPreorder(int[] preorder) {
        if (preorder.length == 0) return null;
        return build(preorder, 0, preorder.length - 1);
    }
    
    private TreeNode build(int[] preorder, int start, int end) {
        if (start > end) return null;
        if (start == end) {
            return new TreeNode(preorder[start]);
        }
        
        int rootVal = preorder[start];
        TreeNode root = new TreeNode(rootVal);
        
        // find right child
        int right = start + 1;
        while (right <= end && preorder[right] < rootVal) {
            ++ right;
        }
        
        root.left = build(preorder, start + 1, right - 1);
        // if no right child, then right == end + 1, which means null node.
        root.right = build(preorder, right, end);
        
        return root;
        
    }
}


