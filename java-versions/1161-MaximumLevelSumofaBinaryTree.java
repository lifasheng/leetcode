/*
Given the root of a binary tree, the level of its root is 1, the level of its children is 2, and so on.

Return the smallest level x such that the sum of all the values of nodes at level x is maximal.

Example 1:
Input: root = [1,7,0,7,-8,null,null]
Output: 2
Explanation: 
Level 1 sum = 1.
Level 2 sum = 7 + 0 = 7.
Level 3 sum = 7 + -8 = -1.
So we return the level with the maximum sum which is level 2.


Example 2:
Input: root = [989,null,10250,98693,-89388,null,null,null,-32127]
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
    
    private Map<Integer, Integer> levelToSumMap = new HashMap<>();
    public int maxLevelSum(TreeNode root) {
        return bfs(root);
    }
    
    private int bfs(TreeNode root) {
        if (root == null) return 0;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        
        int maxSum = Integer.MIN_VALUE;
        int smallestLevel = 0;
        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            int sum = 0;
            ++ level;
            for (int i = 0; i < size; ++i) {
                TreeNode node = queue.poll();
                sum += node.val;
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            if (sum > maxSum) {
                maxSum = sum;
                smallestLevel = level;
            }
        }
        return smallestLevel;
    }
    
    private int dfs(TreeNode root) {
        dfs(root, 1);
        
        int maxSum = Integer.MIN_VALUE;
        int smallestLevel = 0;
        for (Map.Entry<Integer, Integer> entry : levelToSumMap.entrySet()) {
            if (entry.getValue() > maxSum) {
                maxSum = entry.getValue();
                smallestLevel = entry.getKey();
            }
        }
        return smallestLevel;
    }
    
    private void dfs(TreeNode root, int level) {
        if (root == null) return;
        
        int sum = root.val;
        if (levelToSumMap.containsKey(level)) {
            sum += levelToSumMap.get(level);
        }
        levelToSumMap.put(level, sum);
        
        
        dfs(root.left, level + 1);
        dfs(root.right, level + 1);

    }
}

