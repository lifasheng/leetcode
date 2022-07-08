/*
Given the root of a binary tree, determine if it is a complete binary tree.

In a complete binary tree, every level, except possibly the last, is completely filled, and all nodes in the last level are as far left as possible. It can have between 1 and 2h nodes inclusive at the last level h.

Example 1:
Input: root = [1,2,3,4,5,6]
Output: true
Explanation: Every level before the last is full (ie. levels with node-values {1} and {2, 3}), and all nodes in the last level ({4, 5, 6}) are as far left as possible.

Example 2:
Input: root = [1,2,3,4,5,null,7]
Output: false
Explanation: The node with value 7 isn't as far left as possible.

Constraints:
The number of nodes in the tree is in the range [1, 100].
1 <= Node.val <= 1000
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

// 1. check each level is complete, like [1 null 2]
// 2. check adjacent level is complete, like [1, 2, null, 3]
class Solution {
    public boolean isCompleteTree(TreeNode root) {
        return isCompleteTree_bfs(root);
    }
    
    private boolean isComplete;
    private boolean isCompleteTree_dfs(TreeNode root) {
        if (root == null) return true;
        this.isComplete = true;
        List<Integer> visitedIndexInEachLevel = new ArrayList<>();
        int depth = 0;
        int indexInCurrentLevel = 0;
        dfs(root, visitedIndexInEachLevel, depth, indexInCurrentLevel);
        
        // This is checking all levels are complete except the last level
        for (int i = 0; i < visitedIndexInEachLevel.size() - 1; ++i) {
            if (visitedIndexInEachLevel.get(i) != (Math.pow(2, i) - 1)) {
                isComplete = false;
                break;
            }
        }
        return isComplete;
    }
    
    private void dfs(TreeNode node, List<Integer> visitedIndexInEachLevel, 
                     int depth, int indexInCurrentLevel) {
        
        if (node == null || !isComplete) return;
        
        if (visitedIndexInEachLevel.size() == depth) { // the first visited node in current level
            if (indexInCurrentLevel != 0) {
                isComplete = false;
                return;
            } else {
                visitedIndexInEachLevel.add(indexInCurrentLevel);
            }
        } else {
            int visitedIndexInCurrentLevel = visitedIndexInEachLevel.get(depth);
            if (indexInCurrentLevel != visitedIndexInCurrentLevel + 1) {
                isComplete = false;
                return;
            } else {
                visitedIndexInEachLevel.set(depth, visitedIndexInCurrentLevel + 1);
            }
        }
        
        dfs(node.left, visitedIndexInEachLevel, depth + 1, 2 * indexInCurrentLevel);
        dfs(node.right, visitedIndexInEachLevel, depth + 1, 2 * indexInCurrentLevel + 1);
    }
    
    // test case: [1,2,3,5,null,7,8]
    private boolean isCompleteTree_bfs(TreeNode root) {
        if (root == null) return true;
        
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        
        int level = 0;
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            
            // ensure there is no empty node in the middle of each level, especially the last level
            boolean hasEmptyNode = false;
            for (int i = 0; i < size; ++i) {
                TreeNode node = queue.poll();
                
                if (node.left != null) {
                    queue.offer(node.left);
                    
                    // if there is empty node before this node in current level, the tree is incomplete
                    if (hasEmptyNode) return false;
                } else {
                    hasEmptyNode = true;
                }
                if (node.right != null) {
                    queue.offer(node.right);
                    
                    // if there is empty node before this node in current level, the tree is incomplete
                    if (hasEmptyNode) return false;
                } else {
                    hasEmptyNode = true;
                }
            }
            
            
            boolean isCurrentLevelComplete = (size == Math.pow(2, level));
            boolean hasNextLevel = !queue.isEmpty();
            if (hasNextLevel && !isCurrentLevelComplete) {
                return false;
            }
            
            level ++;
        }
        
        return true;
    }
}


