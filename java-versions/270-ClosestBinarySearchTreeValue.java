/*
Given the root of a binary search tree and a target value, return the value in the BST that is closest to the target.

Example 1:
Input: root = [4,2,5,1,3], target = 3.714286
Output: 4

Example 2:
Input: root = [1], target = 4.428571
Output: 1
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
    private double minDiff;
    private int result;
    public int closestValue(TreeNode root, double target) {
        minDiff = Integer.MAX_VALUE;
        result = root.val;
        binarySearch(root, target);
        return result;
    }
    private void binarySearch(TreeNode node, double target) {
        if (node == null) return;
        
        double diff = Math.abs(node.val - target);
        if (diff < minDiff) {
            minDiff = diff;
            result = node.val;
        }
        
        if (node.val < target) {
            binarySearch(node.right, target);
        } else {
            binarySearch(node.left, target);
        }
    }
}
