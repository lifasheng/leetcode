/*
Given the root of a binary tree, return its maximum depth.

A binary tree's maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.

Example 1:
Input: root = [3,9,20,null,null,15,7]
Output: 3

Example 2:
Input: root = [1,null,2]
Output: 2
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
    public int maxDepth(TreeNode root) {
        return maxDepth_dfs(root);
    }
    
    // Solution 1: fasest
    private int maxDepth_dfs(TreeNode node) {
        if (node == null) return 0;
        int left = maxDepth_dfs(node.left);
        int right = maxDepth_dfs(node.right);
        return Math.max(left, right) + 1;
    }
    
    // Solution 2: bfs
    private int maxDepth_bfs(TreeNode root) {
        if (root == null) return 0;
        
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int depth = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            ++depth;
            for (int i = 0; i < size; ++i) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        return depth;
    }
    
    // Solution 3: inorder traverse using stack
    private int maxDepth_inorder_usingstack(TreeNode root) {
        if (root == null) return 0;
        
        class Pair {
            TreeNode node;
            int depth;
            Pair(TreeNode node, int depth) {
                this.node = node;
                this.depth = depth;
            }
        }
        
        Deque<Pair> stack = new ArrayDeque<>();
        
        int maxDepth = 0;
        int depth = 1;
        TreeNode p = root;
        while (p != null || !stack.isEmpty()) {
            while (p != null) {
                stack.push(new Pair(p, depth));
                p = p.left;
                depth ++;
            }
            
            Pair pair = stack.pop();
            p = pair.node;
            depth = pair.depth;
            
            maxDepth = Math.max(maxDepth, depth);
            
            p = p.right;
            depth ++;
        }
        return maxDepth;
    }
}

