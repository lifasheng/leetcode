/*
You are given the root of a binary tree with n nodes, where each node is uniquely assigned a value from 1 to n. You are also given a sequence of n values voyage, which is the desired pre-order traversal of the binary tree.

Any node in the binary tree can be flipped by swapping its left and right subtrees. For example, flipping node 1 will have the following effect:

Flip the smallest number of nodes so that the pre-order traversal of the tree matches voyage.

Return a list of the values of all flipped nodes. You may return the answer in any order. If it is impossible to flip the nodes in the tree to make the pre-order traversal match voyage, return the list [-1].

 

Example 1:
Input: root = [1,2], voyage = [2,1]
Output: [-1]
Explanation: It is impossible to flip the nodes such that the pre-order traversal matches voyage.

Input: root = [1,2,3], voyage = [1,3,2]
Output: [1]
Explanation: Flipping node 1 swaps nodes 2 and 3, so the pre-order traversal matches voyage.

Input: root = [1,2,3], voyage = [1,2,3]
Output: []
Explanation: The tree's pre-order traversal already matches voyage, so no nodes need to be flipped.
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
    private List<Integer> list;
    private int index;
    private int[] voyage;
    
    // preorder遍历树，如果不match，就尝试翻转
    // 只需要判断左子树是不是match，左子树不match，翻转后右子树必然需要match，否则就和voyage不match。
    // 如果左子树为空，那右子树必须match，否则也和voyage不match
    public List<Integer> flipMatchVoyage(TreeNode root, int[] voyage) {
        this.voyage = voyage;
        this.index = 0;
        this.list = new ArrayList<>();
        
        boolean isValid = traverse(root);
        if (isValid) {
            return list;
        } else {
            return Arrays.asList(-1);
        }
    }
    
    private boolean traverse(TreeNode node) {
        if (node == null) return true;
        
        if (node.val != voyage[index]) {
            return false;
        }
        
        ++ index;
        
        if (node.left != null && node.left.val != voyage[index]) {
            list.add(node.val);
            swap(node);
        }
        
        return traverse(node.left) && traverse(node.right);
    }
    
    private void swap(TreeNode node) {
        if (node == null) return;
        TreeNode left = node.left;
        node.left = node.right;
        node.right = left;
    }
}



