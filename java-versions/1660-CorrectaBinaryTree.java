/*
You have a binary tree with a small defect. There is exactly one invalid node where its right child incorrectly points to another node at the same depth but to the invalid node's right.

Given the root of the binary tree with this defect, root, return the root of the binary tree after removing this invalid node and every node underneath it (minus the node it incorrectly points to).

Custom testing:

The test input is read as 3 lines:

TreeNode root
int fromNode (not available to correctBinaryTree)
int toNode (not available to correctBinaryTree)
After the binary tree rooted at root is parsed, the TreeNode with value of fromNode will have its right child pointer pointing to the TreeNode with a value of toNode. Then, root is passed to correctBinaryTree.

Example 1:
Input: root = [1,2,3], fromNode = 2, toNode = 3
Output: [1,null,3]
Explanation: The node with value 2 is invalid, so remove it.

Example 2:
Input: root = [8,3,1,7,null,9,4,2,null,null,null,5,6], fromNode = 7, toNode = 4
Output: [8,3,1,null,null,9,4,null,null,5,6]
Explanation: The node with value 7 is invalid, so remove it and the node underneath it, node 2.
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
    private Map<TreeNode, TreeNode> parentMap;
    private TreeNode invalidNode;
    
    public TreeNode correctBinaryTree(TreeNode root) {
        if (root == null || root.right == root) return null;
        
        this.parentMap = new HashMap<>();
        this.invalidNode = null;
        
        buildParentMap(root, null);
        
        TreeNode parent = parentMap.get(invalidNode);
        if (parent.left == invalidNode) {
            parent.left = null;
        } else {
            parent.right = null;
        }
        return root;
    }
    
    // preorder, but first right child, then left child because invalid node is on the left.
    private void buildParentMap(TreeNode node, TreeNode parent) {
        if (node == null) return;
        if (parentMap.containsKey(node)) {
            invalidNode = parent;
            return; // once we find the invalid node, stop traversing the tree.
        } else {
            parentMap.put(node, parent);
        }
        buildParentMap(node.right, node);
        buildParentMap(node.left, node);
    }
}

