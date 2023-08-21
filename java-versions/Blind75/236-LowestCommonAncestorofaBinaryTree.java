/*
very very good!


Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.

According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes p and q as the lowest node in T that has both p and q as descendants (where we allow a node to be a descendant of itself).”

Example 1:
Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
Output: 3
Explanation: The LCA of nodes 5 and 1 is 3.

Example 2:
Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
Output: 5
Explanation: The LCA of nodes 5 and 4 is 5, since a node can be a descendant of itself according to the LCA definition.

Example 3:
Input: root = [1,2], p = 1, q = 2
Output: 1
 
Constraints:
The number of nodes in the tree is in the range [2, 105].
-109 <= Node.val <= 109
All Node.val are unique.
p != q
p and q will exist in the tree.
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

    // solution 1: using dfs to find path
    private TreeNode lowestCommonAncestor_dfs(TreeNode root, TreeNode p, TreeNode q) {
        LinkedList<TreeNode> pathP = new LinkedList<>();
        LinkedList<TreeNode> pathQ = new LinkedList<>();
        findPath(root, p, pathP);
        findPath(root, q, pathQ);
        return findLowestCommonAncestor(pathP, pathQ);
    }

    private boolean findPath(TreeNode root, TreeNode p, LinkedList<TreeNode> path) {
        if (root == null) {
            return false;
        }

        if (root == p) {
            path.add(root);
            return true;
        }

        path.addLast(root);
        boolean found = findPath(root.left, p, path);
        if (found) {
            return true;
        }

        found = findPath(root.right, p, path);
        if (found) {
            return true;
        }

        path.removeLast();
        return false;
    }

    private TreeNode findLowestCommonAncestor(List<TreeNode> pathP, List<TreeNode> pathQ) {
        TreeNode commonAncestor = pathP.get(0);
        int n = Math.min(pathP.size(), pathQ.size());
        for (int i = 0; i < n; ++i) {
            if (pathP.get(i) != pathQ.get(i)) {
                break;
            } else {
                commonAncestor = pathP.get(i);
            }
        }
        return commonAncestor;
    }

    // solution 2: use recrusive
    // 说到递归，肯定是有边界条件的，这里的边界条件除了递归到叶子节点外，
    // 还有就是到达p或q，因为你p或者q的子孙节点不可能是p和q的LCA。
    private TreeNode lowestCommonAncestor_recursive(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;

        if (root == p || root == q) return root;

        TreeNode left = lowestCommonAncestor_recursive(root.left, p, q);
        TreeNode right = lowestCommonAncestor_recursive(root.right, p, q);
        if (left != null && right != null) {
            return root;
        }
        return left != null ? left : right;
    }
}


