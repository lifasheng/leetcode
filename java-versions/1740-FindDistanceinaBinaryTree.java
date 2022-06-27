/*
Given the root of a binary tree and two integers p and q, return the distance between the nodes of value p and value q in the tree.

The distance between two nodes is the number of edges on the path from one to the other.

 

Example 1:
Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 0
Output: 3
Explanation: There are 3 edges between 5 and 0: 5-3-1-0.

Example 2:
Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 7
Output: 2
Explanation: There are 2 edges between 5 and 7: 5-2-7.

Example 3:
Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 5
Output: 0
Explanation: The distance between a node and itself is 0.
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
    public int findDistance(TreeNode root, int p, int q) {
        return findDistance_usingLCA(root, p, q);
    }
    
    // Solution 1, need to travese whole tree to build parent map
    private Map<TreeNode, TreeNode> parentMap;
    private TreeNode nodeP;
    private TreeNode nodeQ;
    private int findDistance_usingParent(TreeNode root, int p, int q) {
        if (p == q) return 0;
        parentMap = new HashMap<>();
        preorderTraverse(root, null, p, q);
        
        LinkedList<Integer> pathP = new LinkedList<>();
        LinkedList<Integer> pathQ = new LinkedList<>();
        TreeNode t = nodeP;
        while (t != null) {
            pathP.addFirst(t.val);
            t = parentMap.get(t);
        }
        
        t = nodeQ;
        while (t != null) {
            pathQ.addFirst(t.val);
            t = parentMap.get(t);
        }
        
        int index = 0;
        while (index < pathP.size() && index < pathQ.size()) {
            if (pathP.get(index).intValue() == pathQ.get(index).intValue()) {
                ++index;
            } else {
                break;
            }
        }
        return pathP.size() - index + pathQ.size() - index;
    }
    
    private void preorderTraverse(TreeNode node, TreeNode parent, int p, int q) {
        if (node == null) return;
        
        if (node.val == p) {
            nodeP = node;
        }
        if (node.val == q) {
            nodeQ = node;
        }
        
        parentMap.put(node, parent);
        preorderTraverse(node.left, node, p, q);
        preorderTraverse(node.right, node, p, q);
    }
    
    // Solution 2, need to call findNode method twice
    private int findDistance_findingPath(TreeNode root, int p, int q) {
        if (p == q) return 0;
        
        LinkedList<Integer> pathP = new LinkedList<>();
        LinkedList<Integer> pathQ = new LinkedList<>();
        
        findNode(root, p, pathP);
        findNode(root, q, pathQ);
        
        int index = 0;
        while (index < pathP.size() && index < pathQ.size()) {
            if (pathP.get(index).intValue() == pathQ.get(index).intValue()) {
                ++index;
            } else {
                break;
            }
        }
        return pathP.size() - index + pathQ.size() - index;
    }
    
    private boolean findNode(TreeNode root, int p, LinkedList<Integer> path) {
        if (root == null) return false;
        
        if (root.val == p) {
            path.add(root.val);
            return true;
        }
        
        path.add(root.val);
        boolean found = findNode(root.left, p, path);
        if (found) return found;
        
        found = findNode(root.right, p, path);
        if (found) return found;
        
        path.removeLast();
        return false;
    }
    
    
    // Solution 3, faster than Solution 1/2, it just traverse the tree once, not full tree
    // once it find p and q, it will return immediately.
    private int findDistance_usingLCA(TreeNode root, int p, int q) {
        if (root == null || p == q) return 0;
        TreeNode ancestor = lca(root, p, q);
        return distance(ancestor, p, 0) + distance(ancestor, q, 0);
    }
    
    private TreeNode lca(TreeNode node, int p, int q) {
        if (node == null) return null;
        
        if (node.val == p || node.val == q) return node;
        
        TreeNode left = lca(node.left, p, q);
        TreeNode right = lca(node.right, p, q);
        if (left != null && right != null) {
            return node;
        } else {
            return left != null ? left : right;
        }
    }
    
    private int distance(TreeNode node, int p, int dist) {
        if (node == null) return 0;
        if (node.val == p) return dist;
        
        int leftd = distance(node.left, p, dist + 1);
        int rightd = distance(node.right, p, dist + 1);
        return leftd != 0 ? leftd : rightd;
    }
    
    
}


