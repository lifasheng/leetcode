/*
Given the root of a binary tree with unique values and the values of two different nodes of the tree x and y, return true if the nodes corresponding to the values x and y in the tree are cousins, or false otherwise.

Two nodes of a binary tree are cousins if they have the same depth with different parents.
Note that in a binary tree, the root node is at the depth 0, and children of each depth k node are at the depth k + 1.

Example 1:
Input: root = [1,2,3,4], x = 4, y = 3
Output: false

Example 2:
Input: root = [1,2,3,null,4,null,5], x = 5, y = 4
Output: true

Example 3:
Input: root = [1,2,3,null,4], x = 2, y = 3
Output: false

Constraints:
The number of nodes in the tree is in the range [2, 100].
1 <= Node.val <= 100
Each node has a unique value.
x != y
x and y are exist in the tree.
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
    
    public boolean isCousins(TreeNode root, int x, int y) {
        return isCousins_bfs(root, x, y);
    }
    
    // Solution 1: dfs
    private TreeNode parentX, parentY;
    private int depthX, depthY;
    private boolean isCousins;
    private boolean isCousins_dfs(TreeNode root, int x, int y) {
        parentX = null;
        parentY = null;
        depthX = -1;
        depthY = -1;
        isCousins = false;
        dfs(root, x, y, null, 0);
        return isCousins;
    }
    
    private void dfs(TreeNode node, int x, int y, TreeNode parent, int depth) {
        if (node == null) return;
        if (depthX != -1 && depth > depthX) return; // no need to go deeper than depthX
        if (depthY != -1 && depth > depthY) return; // no need to go deeper than depthY
        
        if (node.val == x) {
            depthX = depth;
            parentX = parent;
            if (parentY != null) {
                isCousins = parentX != parentY && depthX == depthY;
            }
            return; // no need to go deeper than depthX
        } else if (node.val == y) {
            depthY = depth;
            parentY = parent;
            if (parentX != null) {
                isCousins = parentX != parentY && depthX == depthY;
            }
            return; // no need to go deeper than depthY
        }
        
        dfs(node.left, x, y, node, depth + 1);
        dfs(node.right, x, y, node, depth + 1);
    }
    
    // Solution 2: bfs
    private boolean isCousins_bfs(TreeNode root, int x, int y) {
        if (root == null) return false;
        
        // local class
        class Pair {
            TreeNode node;
            TreeNode parent;
            Pair(TreeNode node, TreeNode parent) {
                this.node = node;
                this.parent = parent;
            }
        }
        
        int depthX = -1, depthY = -1;
        TreeNode parentX = null, parentY = null;
        
        Deque<Pair> queue = new ArrayDeque<>();
        queue.offer(new Pair(root, null));
        int depth = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; ++i) {
                Pair pair = queue.poll();
                TreeNode node = pair.node;
                TreeNode parent = pair.parent;
                
                if (node.val == x) {
                    depthX = depth;
                    parentX = parent;
                    if (parentY != null) {
                        return parentX != parentY && depthX == depthY;
                    }
                }
                if (node.val == y) {
                    depthY = depth;
                    parentY = parent;
                    if (parentX != null) {
                        return parentX != parentY && depthX == depthY;
                    }
                }
                
                if (node.left != null) {
                    queue.offer(new Pair(node.left, node));
                }
                if (node.right != null) {
                    queue.offer(new Pair(node.right, node));
                }
            }
            
            ++ depth;
        }
        return false;
    }
}

