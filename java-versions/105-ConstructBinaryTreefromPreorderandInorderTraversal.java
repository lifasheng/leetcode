/*
Given two integer arrays preorder and inorder where preorder is the preorder traversal of a binary tree and inorder is the inorder traversal of the same tree, construct and return the binary tree.

Example 1:
Input: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
Output: [3,9,20,null,null,15,7]

Example 2:
Input: preorder = [-1], inorder = [-1]
Output: [-1]

Constraints:
1 <= preorder.length <= 3000
inorder.length == preorder.length
-3000 <= preorder[i], inorder[i] <= 3000
preorder and inorder consist of unique values.
Each value of inorder also appears in preorder.
preorder is guaranteed to be the preorder traversal of the tree.
inorder is guaranteed to be the inorder traversal of the tree.
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
    private Map<Integer, Integer> valToIndexMap;
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        valToIndexMap = new HashMap<>();
        for (int i = 0; i < inorder.length; ++i) {
            valToIndexMap.put(inorder[i], i);
        }
        return dfs(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }
    
    private TreeNode dfs(int[] preorder, int preStart, int preEnd, int[] inorder, int inStart, int inEnd) {
        if (preStart > preEnd || inStart > inEnd) return null;
        
        // find index of root value in inorder array.
        // int index = inStart;
        // for (; index <= inEnd; ++index) {
        //     if (inorder[index] == preorder[preStart]) break;
        // }
        
        // Optimize using a map.
        int index = valToIndexMap.get(preorder[preStart]);

        int leftSize = index - inStart;
        int rightSize = inEnd - index;
        TreeNode root = new TreeNode(preorder[preStart]);
        root.left = dfs(preorder, preStart + 1, preStart + leftSize, inorder, inStart, index - 1);
        root.right = dfs(preorder, preStart + leftSize + 1, preEnd, inorder, index + 1, inEnd);
        return root;
    }
}

