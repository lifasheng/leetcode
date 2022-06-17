```
Given the root of a binary tree, flatten the tree into a "linked list":

The "linked list" should use the same TreeNode class where the right child pointer points to the next node in the list and the left child pointer is always null.
The "linked list" should be in the same order as a pre-order traversal of the binary tree.
 

Example 1:
Input: root = [1,2,5,3,4,null,6]
Output: [1,null,2,null,3,null,4,null,5,null,6]


Example 2:
Input: root = []
Output: []

Example 3:
Input: root = [0]
Output: [0]
```


```
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
    public void flatten(TreeNode root) {        
        recursiveFlatten(root);
    }
    
    private TreeNode recursiveFlatten(TreeNode root) {
        if (root == null) return root;
        if (root.left == null && root.right == null) return root;
        
        TreeNode originalLeft = root.left;
        TreeNode originalRight = root.right;
        TreeNode leftTail = recursiveFlatten(root.left);
        TreeNode rightTail = recursiveFlatten(root.right);

        if (originalLeft != null) {
            root.right = root.left;
            root.left = null;
            leftTail.right = originalRight;
        }
        
        return rightTail != null ? rightTail : leftTail;
    }
}
```
