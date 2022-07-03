/*
Given the root of a binary tree where every node has a unique value and a target integer k, return the value of the nearest leaf node to the target k in the tree.

Nearest to a leaf means the least number of edges traveled on the binary tree to reach any leaf of the tree. Also, a node is called a leaf if it has no children.

Example 1:
Input: root = [1,3,2], k = 1
Output: 2
Explanation: Either 2 or 3 is the nearest leaf node to the target of 1.

Example 2:
Input: root = [1], k = 1
Output: 1
Explanation: The nearest leaf node is the root node itself.

Example 3:
Input: root = [1,2,3,4,null,null,null,5,null,6], k = 2
Output: 3
Explanation: The leaf node with value 3 (and not the leaf node with value 6) is nearest to the node with value 2.
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
    
    private TreeNode nodeK;
    private Map<TreeNode, TreeNode> parentMap;
    public int findClosestLeaf(TreeNode root, int k) {
        if (root == null) return -1;
        
        this.nodeK = null;
        this.parentMap = new HashMap<>();
        buildParentMap(root, null, k);
        
        // we can record the nodeK when build parent map, so save one time scan.
        // TreeNode nodeK = findNode(root, k);
        
        return bfs(nodeK);
    }
    
    private int bfs(TreeNode nodeK) {
        Set<TreeNode> visited = new HashSet<>();
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(nodeK);
        visited.add(nodeK);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            
            if (isLeaf(node)) {
                return node.val;
            }
            
            TreeNode left = node.left;
            TreeNode right = node.right;
            TreeNode parent = parentMap.get(node);
            
            if (left != null && !visited.contains(left)) {
                queue.add(left);
                visited.add(left);
            }
            if (right != null && !visited.contains(right)) {
                queue.add(right);
                visited.add(right);
            }
            if (parent != null && !visited.contains(parent)) {
                queue.add(parent);
                visited.add(parent);
            }
        }
        return -1;
    }
    
    private boolean isLeaf(TreeNode node) {
        return node.left == null && node.right == null;
    }
    
    private TreeNode findNode(TreeNode node, int k) {
        if (node == null) return null;
        if (node.val == k) return node;
        
        TreeNode left = findNode(node.left, k);
        if (left != null) return left;
        return findNode(node.right, k);
    }
    
    private void buildParentMap(TreeNode node, TreeNode parent, int k) {
        if (node == null) return;
        if (node.val == k) nodeK = node;
        parentMap.put(node, parent);
        buildParentMap(node.left, node, k);
        buildParentMap(node.right, node, k);
    }
}

