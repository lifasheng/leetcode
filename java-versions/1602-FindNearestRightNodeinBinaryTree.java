/*
Given the root of a binary tree and a node u in the tree, return the nearest node on the same level that is to the right of u, or return null if u is the rightmost node in its level.

Example 1:
Input: root = [1,2,3,null,4,5,6], u = 4
Output: 5
Explanation: The nearest node on the same level to the right of node 4 is node 5.

Example 2:
Input: root = [3,null,4,2], u = 2
Output: null
Explanation: There are no nodes to the right of 2.
 
Constraints:
The number of nodes in the tree is in the range [1, 105].
1 <= Node.val <= 105
All values in the tree are distinct.
u is a node in the binary tree rooted at root.
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
    public TreeNode findNearestRightNode(TreeNode root, TreeNode u) {
        return findNearestRightNode_dfs(root, u);
    }
    
    private TreeNode findNearestRightNode_bfs(TreeNode root, TreeNode u) {
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; ++i) {
                TreeNode node = queue.poll();
                if (node == u) {
                    if (i == size - 1) return null;
                    return queue.poll();
                }
                if (node.left != null) {
                    queue.offer(node.left);
                }
                
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        return null;
    }
    
    private int depthOfU;
    private TreeNode result;
    private TreeNode findNearestRightNode_dfs(TreeNode root, TreeNode u) {
        this.depthOfU = -1;
        this.result = null;
        dfs(root, u, 0);
        return result;
    }
    
    private void dfs(TreeNode root, TreeNode u, int depth) {
        if (root == null  || result != null) return;
        
        if (root == u) {
            depthOfU = depth;
            return;
        }
        
        if (depth == depthOfU) {
            result = root;
            return;
        }
        
        dfs(root.left, u, depth + 1);
        dfs(root.right, u, depth + 1);
    }
}

