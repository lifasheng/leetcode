/*
Given the root of a binary tree, the value of a target node target, and an integer k, return an array of the values of all nodes that have a distance k from the target node.

You can return the answer in any order.

Input: root = [3,5,1,6,2,0,8,null,null,7,4], target = 5, k = 2
Output: [7,4,1]
Explanation: The nodes that are a distance 2 from the target node (with value 5) have values 7, 4, and 1.

Input: root = [1], target = 1, k = 3
Output: []
*/


/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        return bfs(root, target, k);
    }
    
    private Map<TreeNode, TreeNode> parentMap = new HashMap<>();
    
    private List<Integer> bfs(TreeNode root, TreeNode target, int k) {
        preOrderTraverse(root, null);
        Set<TreeNode> visited = new HashSet<>();
        List<Integer> result = new ArrayList<>();
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(target);
        visited.add(target);
        int distance = 0;
        boolean found = false;
        while (!queue.isEmpty() && !found) {
            int size = queue.size();
            for (int i = 0; i < size; ++i) {
                TreeNode node = queue.poll();
                if (distance == k) {
                    result.add(node.val);
                    found= true;
                }
                
                if (node.left != null && !visited.contains(node.left)) {
                    queue.add(node.left);
                    visited.add(node.left);
                }
                if (node.right != null && !visited.contains(node.right)) {
                    queue.add(node.right);
                    visited.add(node.right);
                }
                if (parentMap.get(node) != null && !visited.contains(parentMap.get(node))) {
                    queue.add(parentMap.get(node));
                    visited.add(parentMap.get(node));
                }
            }
            ++ distance;
        }
        
        return result;
    }
    

    private List<Integer> dfs(TreeNode root, TreeNode target, int k) {
        preOrderTraverse(root, null);
        
        Set<TreeNode> visited = new HashSet<>();
        List<Integer> result = new ArrayList<>();
        dfs(target, 0, k, visited, result);
        return result;
    }
    
    private void dfs(TreeNode node, int distance, int k, Set<TreeNode> visited, List<Integer> result) {
        if (node == null || visited.contains(node)) return;
        
        visited.add(node);
        if (distance == k) {
            result.add(node.val);
        }
        dfs(node.left, distance + 1, k, visited, result);
        dfs(node.right, distance + 1, k , visited, result);
        dfs(parentMap.get(node), distance + 1, k, visited, result);
    }
    
    // build parent map
    private void preOrderTraverse(TreeNode node, TreeNode parent) {
        if (node == null) return;
        parentMap.put(node, parent);
        preOrderTraverse(node.left, node);
        preOrderTraverse(node.right, node);
    }
    
}




