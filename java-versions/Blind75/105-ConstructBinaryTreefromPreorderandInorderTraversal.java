/*
very very good

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
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int n = preorder.length;
        Map<Integer, Integer> posMap = new HashMap<>();
        for (int i = 0; i < n; ++i) {
            posMap.put(inorder[i], i);
        }
        return buildTree(preorder, inorder, 0, n - 1, 0, n -1, posMap);
    }

    private TreeNode buildTree(int[] preorder, int[] inorder, 
                               int preStart, int preEnd,  int inStart, int inEnd, 
                               Map<Integer, Integer> posMap) {
        if (preStart > preEnd || inStart > inEnd) {
            return null;
        }

        // 优化: 用map提前计算好每个数在inorder中的位置
        // int rootPos = inStart;
        // for (int i = inStart; i <= inEnd; ++i) {
        //     if (inorder[i] == preorder[preStart]) {
        //         rootPos = i;
        //         break;
        //     }
        // }
        int rootPos = posMap.get(preorder[preStart]);

        TreeNode root = new TreeNode(preorder[preStart]);

        int leftSize = rootPos - inStart;
        root.left = buildTree(preorder, inorder, preStart + 1, preStart + leftSize, inStart, rootPos - 1, posMap);
        root.right = buildTree(preorder, inorder, preStart + leftSize + 1, preEnd, rootPos + 1, inEnd, posMap);

        return root;
    }
}


