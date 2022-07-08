/*
Given the root of a binary tree, return the average value of the nodes on each level in the form of an array. Answers within 10-5 of the actual answer will be accepted.
 
Example 1:
Input: root = [3,9,20,null,null,15,7]
Output: [3.00000,14.50000,11.00000]
Explanation: The average value of nodes on level 0 is 3, on level 1 is 14.5, and on level 2 is 11.
Hence return [3, 14.5, 11].

Example 2:
Input: root = [3,9,20,15,7]
Output: [3.00000,14.50000,11.00000]
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
    
    public List<Double> averageOfLevels(TreeNode root) {
        return averageOfLevels_bfs(root);
    }

    private List<Double> averageOfLevels_dfs(TreeNode node) {
        List<Double> sum = new ArrayList<>();
        List<Integer> count = new ArrayList<>();
        dfs(node, 0, sum, count);
        for (int i = 0; i < sum.size(); ++i) {
            sum.set(i, sum.get(i) / count.get(i));
        }
        return sum;
    }
    
    private void dfs(TreeNode node, int depth, List<Double> sum, List<Integer> count) {
        if (node == null) return;
        if (sum.size() == depth) {
            sum.add(1.0 * node.val);
            count.add(1);
        } else {
            sum.set(depth, sum.get(depth) + node.val);
            count.set(depth, count.get(depth) + 1);
        }
        
        dfs(node.left, depth + 1, sum, count);
        dfs(node.right, depth + 1, sum, count);
    }
    
    private List<Double> averageOfLevels_bfs(TreeNode root) {
        List<Double> sums = new ArrayList<>();
        if (root == null) return sums;
        
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            double sum = 0;
            for (int i = 0; i < size; ++i) {
                TreeNode node = queue.poll();
                sum += node.val;
                
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            sums.add(sum / size);
        }
        
        return sums;
    }
}

