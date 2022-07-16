/*
Given the root of a binary tree, return the lowest common ancestor (LCA) of two given nodes, p and q. If either node p or q does not exist in the tree, return null. All values of the nodes in the tree are unique.

According to the definition of LCA on Wikipedia: "The lowest common ancestor of two nodes p and q in a binary tree T is the lowest node that has both p and q as descendants (where we allow a node to be a descendant of itself)". A descendant of a node x is a node y that is on the path from node x to some leaf node.

Example 1:
Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
Output: 3

Explanation: The LCA of nodes 5 and 1 is 3.
Example 2:
Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
Output: 5
Explanation: The LCA of nodes 5 and 4 is 5. A node can be a descendant of itself according to the definition of LCA.

Example 3:
Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 10
Output: null
Explanation: Node 10 does not exist in the tree, so return null.
 
Constraints:
The number of nodes in the tree is in the range [1, 104].
-109 <= Node.val <= 109
All Node.val are unique.
p != q
 
Follow up: Can you find the LCA traversing the tree, without checking nodes existence?
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
        return lowestCommonAncestor_dfs(root, p, q);
    }
    
    // solution for generate Binary tree 
    private boolean pFound, qFound;
    private TreeNode lowestCommonAncestor_dfs(TreeNode root, TreeNode p, TreeNode q) {
        pFound = false;
        qFound = false;
        TreeNode node = dfs(root, p, q);
        if (pFound == true && qFound == true) return node;
        return null;
    }
    private TreeNode dfs(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;
        if (root == p) {
            pFound = true;
            if (contains(root, q)) {
                qFound = true;
            }
            return root;
        }
        if (root == q) {
            qFound = true;
            if (contains(root, p)) {
                pFound = true;
            }
            return root;
        }
        
        TreeNode left = dfs(root.left, p, q);
        TreeNode right = dfs(root.right, p, q);
        if (left != null && right != null) return root;
        
        return left != null ? left : right;
    }
    
    private boolean contains(TreeNode node, TreeNode t) {
        if (node == null) return false;
        if (node == t) return true;
        return contains(node.left, t) || contains(node.right, t);
    }
    
    // solution for generate Binary tree
    private TreeNode lowestCommonAncestor_iterative(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;
        
        List<TreeNode> pathP = new ArrayList<>();
        List<TreeNode> pathQ = new ArrayList<>();;
        preorder(root, p, pathP);
        preorder(root, q, pathQ);
        
        // check if any node does not exist !!!
        if (pathP.isEmpty() || pathQ.isEmpty()) return null;
        
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

