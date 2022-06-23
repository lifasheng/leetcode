/*
Given the root of a binary tree, return the same tree where every subtree (of the given tree) not containing a 1 has been removed.

A subtree of a node node is node plus every node that is a descendant of node.

Input: root = [1,null,0,0,1]
Output: [1,null,0,null,1]
Explanation: 
Only the red nodes satisfy the property "every subtree not containing a 1".
The diagram on the right represents the answer.


Input: root = [1,0,1,0,0,0,1]
Output: [1,null,1,null,1]

Input: root = [1,1,0,1,1,0,1,0]
Output: [1,1,0,1,1,null,1]
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
    public TreeNode pruneTree(TreeNode root) {
        boolean treeContainOne = containOne(root);
        return treeContainOne ? root : null;
    }
    
    private boolean containOne(TreeNode root) {
        if (root == null) return false;
        boolean leftContainOne = containOne(root.left);
        boolean rightContainOne = containOne(root.right);
        if (!leftContainOne) {
            root.left = null;
        }
        if (!rightContainOne) {
            root.right = null;
        }
        
        return leftContainOne || rightContainOne || (root.val == 1);
    }
}



