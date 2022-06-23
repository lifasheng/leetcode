/*
Given the root of a binary tree, construct a 0-indexed m x n string matrix res that represents a formatted layout of the tree. The formatted layout matrix should be constructed using the following rules:

The height of the tree is height and the number of rows m should be equal to height + 1.
The number of columns n should be equal to 2height+1 - 1.
Place the root node in the middle of the top row (more formally, at location res[0][(n-1)/2]).
For each node that has been placed in the matrix at position res[r][c], place its left child at res[r+1][c-2height-r-1] and its right child at res[r+1][c+2height-r-1].
Continue this process until all the nodes in the tree have been placed.
Any empty cells should contain the empty string "".
Return the constructed matrix res.

Input: root = [1,2]
Output: 
[["","1",""],
 ["2","",""]]

Input: root = [1,2,3,null,4]
Output: 
[["","","","1","","",""],
 ["","2","","","","3",""],
 ["","","4","","","",""]]
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
    public List<List<String>> printTree(TreeNode root) {
        if (root == null) return new ArrayList<>();
        
        int height = findTreeHeight(root) - 1;
        int row = height + 1;
        int col = (int)Math.pow(2, height+1) - 1;
        
        List<List<String>> matrix = constructMatrix(row, col);
        
        int low = 0;
        int high = col-1;
        setTreeNodeToMatrix(root, 0, low, high, matrix);
        return matrix;
    }
    
    private List<List<String>> constructMatrix(int row, int col) {
        List<List<String>> matrix = new ArrayList<>();
        for (int i = 0; i < row; ++i) {
            String[] strArray = new String[col];
            Arrays.fill(strArray, ""); // initialize with empty string
            matrix.add(Arrays.asList(strArray));
        }
        return matrix;
    }
    
    /**
     * We can fill the matrix while we preorder traversal the tree.
     * row: which row in the matrix
     * low and high is the range of current sub tree in the matrix
     * We always put the root of current sub tree at the middle of the given range
     * and ajudst the range when we go down to left and right children.
     */
    private void setTreeNodeToMatrix(TreeNode root, int row, int low, int high, List<List<String>> matrix) {
        if (root == null) return;
        List<String> list = matrix.get(row);
        int mid = (low + high) / 2;
        list.set(mid, String.valueOf(root.val));
        setTreeNodeToMatrix(root.left, row + 1, low, mid - 1, matrix);
        setTreeNodeToMatrix(root.right, row + 1, mid + 1, high, matrix);
    }
    
    private int findTreeHeight(TreeNode root) {
        if (root == null) return 0;
        int leftHeight = findTreeHeight(root.left);
        int rightHeight = findTreeHeight(root.right);
        return 1 + Math.max(leftHeight, rightHeight);
    }
}




