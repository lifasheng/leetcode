/*
Given two integer arrays inorder and postorder where inorder is the inorder traversal of a binary tree and postorder is the postorder traversal of the same tree, construct and return the binary tree.

Example 1:
Input: inorder = [9,3,15,20,7], postorder = [9,15,7,20,3]
Output: [3,9,20,null,null,15,7]

Example 2:
Input: inorder = [-1], postorder = [-1]
Output: [-1]

Constraints:
1 <= inorder.length <= 3000
postorder.length == inorder.length
-3000 <= inorder[i], postorder[i] <= 3000
inorder and postorder consist of unique values.
Each value of postorder also appears in inorder.
inorder is guaranteed to be the inorder traversal of the tree.
postorder is guaranteed to be the postorder traversal of the tree.
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
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        valToIndexMap = new HashMap<>();
        for (int i = 0; i < inorder.length; ++i) {
            valToIndexMap.put(inorder[i], i);
        }
        return dfs(inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1);
    }
    
    private TreeNode dfs(int[] inorder, int inStart, int inEnd, int[] postorder, int postStart, int postEnd) {
        if (inStart > inEnd || postStart > postEnd) return null;
        
        // Optimize using a map.
        int index = valToIndexMap.get(postorder[postEnd]);

        int leftSize = index - inStart;
        int rightSize = inEnd - index;
        TreeNode root = new TreeNode(postorder[postEnd]);
        root.left = dfs(inorder, inStart, index - 1, postorder, postStart, postStart + leftSize - 1);
        root.right = dfs(inorder, index + 1, inEnd, postorder, postEnd - rightSize, postEnd - 1);
        return root;
    }
}

