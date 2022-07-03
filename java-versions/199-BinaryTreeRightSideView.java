/*
Given the root of a binary tree, imagine yourself standing on the right side of it, return the values of the nodes you can see ordered from top to bottom.

Example 1:
Input: root = [1,2,3,null,5,null,4]
Output: [1,3,4]

Example 2:
Input: root = [1,null,3]
Output: [1,3]

Example 3:
Input: root = []
Output: []
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
    
    public List<Integer> rightSideView(TreeNode root) {
        return rightSideView_bfs(root);
    }
    
    // Solution 1: using map
    private Map<Integer, Integer> rightSideViewMap;
    private List<Integer> rightSideView_usingMap(TreeNode root) {
        if (root == null) return new ArrayList<>();
        
        this.rightSideViewMap = new HashMap<>();
        
        preorder(root, 0);
        
        return rightSideViewMap
                .keySet()
                .stream()
                .sorted()
                .map(k -> rightSideViewMap.get(k))
                .collect(Collectors.toList());
    }
    
    // first right, then left
    private void preorder(TreeNode node, int depth) {
        if (node == null) return;
        
        if (!rightSideViewMap.containsKey(depth)) {
            rightSideViewMap.put(depth, node.val);
        }
        
        preorder(node.right, depth + 1);
        preorder(node.left, depth + 1);
    }
    
    
    // Solution 2: using list, much faster than using map
    private List<Integer> rightSideViewList;
    private List<Integer> rightSideView_usingList(TreeNode root) {
        this.rightSideViewList = new ArrayList<>();
        
        preorder2(root, 0);
        
        return rightSideViewList;
    }
    
    // first right, then left
    private void preorder2(TreeNode node, int depth) {
        if (node == null) return;
        
        if (rightSideViewList.size() == depth) {
            rightSideViewList.add(node.val);
        }
        
        preorder2(node.right, depth + 1);
        preorder2(node.left, depth + 1);
    }
    
    // Solution 3: BFS, level by level
    private List<Integer> rightSideView_bfs(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; ++i) {
                TreeNode node = queue.poll();
                if (node.right != null) {
                    queue.add(node.right);
                }
                if (node.left != null) {
                    queue.add(node.left);
                }
                
                // we are scaning from right to left in each level, 
                // so first node in each level is right most
                if (i == 0) { 
                    result.add(node.val);
                }
            }
        }
        
        return result;
    }
}


