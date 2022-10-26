/*
Medium

Given the root of a binary tree and an integer targetSum, return the number of paths where the sum of the values along the path equals targetSum.
The path does not need to start or end at the root or a leaf, but it must go downwards (i.e., traveling only from parent nodes to child nodes).

Example 1:
Input: root = [10,5,-3,3,2,null,11,3,-2,null,1], targetSum = 8
Output: 3
Explanation: The paths that sum to 8 are shown.

Example 2:
Input: root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
Output: 3

Constraints:
The number of nodes in the tree is in the range [0, 1000].
-109 <= Node.val <= 109
-1000 <= targetSum <= 1000
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
    
    /*
    very very very good!
    思路：
    前缀和，并且要用map，因为有可能多个序列的和为同一个数。
    
    用一维数组为例：
    arr:       [3, 4, 1, 6, -6, 4, 3] target = 7
    prefixSum: {3:1, 7:1, 8:1, 14:1, 8:2, 12:1, 15:1}，结果为4次
    
    有两种情况：
    1. 从头到当前节点和为target
    2. 从中间某节点到当前节点和为target
    
    先根遍历即可！
    */
    
    private int count = 0;
    public int pathSum(TreeNode root, int targetSum) {
        Map<Long, Integer> map = new HashMap<>();
        dfs(root, targetSum, 0L, map);
        return count;
    }
    
    private void dfs(TreeNode root, int targetSum, long currSum, Map<Long, Integer> map) {
        if (root == null) {
            return;
        }
        
        currSum += root.val;
        
        // 第一种情况，从根节点到当前节点之和
        if (currSum == targetSum) {
            ++ count;
        }
        
        // 第二种情况，从中间某节点到当前节点之和，可能有多次
        count += map.getOrDefault(currSum - targetSum, 0);
        
        map.put(currSum, map.getOrDefault(currSum, 0) + 1);
            
        dfs(root.left, targetSum, currSum, map);
        dfs(root.right, targetSum, currSum, map);

        map.put(currSum, map.get(currSum) - 1);
    }
    
}

