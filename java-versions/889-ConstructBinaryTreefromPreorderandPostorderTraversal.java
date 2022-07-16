/*
Given two integer arrays, preorder and postorder where preorder is the preorder traversal of a binary tree of distinct values and postorder is the postorder traversal of the same tree, reconstruct and return the binary tree.

If there exist multiple answers, you can return any of them.

Example 1:
Input: preorder = [1,2,4,5,3,6,7], postorder = [4,5,2,6,7,3,1]
Output: [1,2,3,4,5,6,7]

Example 2:
Input: preorder = [1], postorder = [1]
Output: [1]
 
Constraints:
1 <= preorder.length <= 30
1 <= preorder[i] <= preorder.length
All the values of preorder are unique.
postorder.length == preorder.length
1 <= postorder[i] <= postorder.length
All the values of postorder are unique.
It is guaranteed that preorder and postorder are the preorder traversal and postorder traversal of the same binary tree.
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
    public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {
        valToIndexMap = new HashMap<>();
        for (int i = 0; i < preorder.length; ++i) {
            valToIndexMap.put(preorder[i], i);
        }
        return buildTree(preorder, 0, preorder.length - 1, postorder, 0, postorder.length - 1);
    }
    
    private TreeNode buildTree(int[] preorder, int preStart, int preEnd, int[] postorder, int postStart, int postEnd) {
        if (preStart > preEnd) return null;
        if (preStart == preEnd) return new TreeNode(preorder[preStart]);
        
        int rootVal = preorder[preStart];
        int rightChildVal = postorder[postEnd - 1];
        int index = valToIndexMap.get(rightChildVal); // rightChildVal in preorder array, we can also use leftChildVal to search in postorder.
        int leftSize = index - preStart - 1;
        int rightSize = preEnd - index + 1;
        
        TreeNode root = new TreeNode(rootVal);
        root.left = buildTree(preorder, preStart + 1, index - 1, postorder, postStart, postStart + leftSize - 1);
        root.right = buildTree(preorder, index, preEnd, postorder, postEnd - rightSize, postEnd - 1);
        return root;
    }
}

