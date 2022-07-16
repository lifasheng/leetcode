/*
Given a binary search tree (BST), find the lowest common ancestor (LCA) node of two given nodes in the BST.

According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes p and q as the lowest node in T that has both p and q as descendants (where we allow a node to be a descendant of itself).”

Example 1:
Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
Output: 6
Explanation: The LCA of nodes 2 and 8 is 6.

Example 2:
Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 4
Output: 2
Explanation: The LCA of nodes 2 and 4 is 2, since a node can be a descendant of itself according to the LCA definition.

Example 3:
Input: root = [2,1], p = 2, q = 1
Output: 2
 
Constraints:
The number of nodes in the tree is in the range [2, 105].
-109 <= Node.val <= 109
All Node.val are unique.
p != q
p and q will exist in the BST.
*/

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        return lowestCommonAncestor_recursive(root, p, q);
    }
    
    private TreeNode lowestCommonAncestor_recursive(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;
        
        if (root.val > p.val && root.val > q.val) {
            return lowestCommonAncestor_recursive(root.left, p, q);
        }
        
        if (root.val < p.val && root.val < q.val) {
            return lowestCommonAncestor_recursive(root.right, p, q);
        }
        
        return root;
    }
    
    // solution for generate Binary tree 
    private TreeNode lowestCommonAncestor_dfs(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;
        if (root == p) return p;
        if (root == q) return q;
        
        TreeNode left = lowestCommonAncestor_dfs(root.left, p, q);
        TreeNode right = lowestCommonAncestor_dfs(root.right, p, q);
        if (left != null && right != null) return root;
        return left != null ? left : right;
    }
    
    // solution for generate Binary tree
    private TreeNode lowestCommonAncestor_iterative(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;
        
        List<TreeNode> pathP = new ArrayList<>();
        List<TreeNode> pathQ = new ArrayList<>();;
        preorder(root, p, pathP);
        preorder(root, q, pathQ);
        
        int i = 0;
        for (; i < pathP.size() && i < pathQ.size(); ++i) {
            if (pathP.get(i).val != pathQ.get(i).val) break;
        }
        return pathP.get(i - 1);
    }
    
    private boolean preorder(TreeNode root, TreeNode p, List<TreeNode> path) {
        if (root == null) return false;
        
        path.add(root);
        if (root == p) {
            return true;
        }
        
        boolean found = preorder(root.left, p, path);
        if (found) return found;
        
        found = preorder(root.right, p, path);
        if (found) return found;
        
        path.remove(path.size() - 1);
        return false;
    }
}

