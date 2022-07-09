/*
Given the root of a binary tree, return the vertical order traversal of its nodes' values. (i.e., from top to bottom, column by column).

If two nodes are in the same row and column, the order should be from left to right.

Example 1:
Input: root = [3,9,20,null,null,15,7]
Output: [[9],[3,15],[20],[7]]

Example 2:
Input: root = [3,9,8,4,0,1,7]
Output: [[4],[9],[3,0,1],[8],[7]]

Example 3:
Input: root = [3,9,8,4,0,1,7,null,null,null,2,5]
Output: [[4],[9,5],[3,0,1],[8,2],[7]]
 
Constraints:
The number of nodes in the tree is in the range [0, 100].
-100 <= Node.val <= 100
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
    
    public List<List<Integer>> verticalOrder(TreeNode root) {
        return verticalOrder_bfs_withouotsorting(root);
    }
    
    // Solution 1: Time: O(N) for BFS, and O(NlogN) for sort
    private List<List<Integer>> verticalOrder_bfs(TreeNode root) {
        if (root == null) return new ArrayList<>();
        
        Map<Integer, List<Integer>> colToNodesMap = new HashMap<>();
        
        Deque<Pair<TreeNode, Integer>> queue = new ArrayDeque<>();
        queue.offer(new Pair(root, 0));
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; ++i) {
                Pair<TreeNode, Integer> pair = queue.poll();
                TreeNode node = pair.getKey();
                int col = pair.getValue();

                List<Integer> list = colToNodesMap.computeIfAbsent(col, k -> new ArrayList<>());
                list.add(node.val);
                
                if (node.left != null) {
                    queue.offer(new Pair(node.left, col - 1));
                }
                if (node.right != null) {
                    queue.offer(new Pair(node.right, col + 1));
                }
            }
        }
        
        return colToNodesMap.keySet()
            .stream()
            .sorted()
            .map(k -> colToNodesMap.get(k))
            .collect(Collectors.toList());
    }
    
    // Solution 2: Time: O(N) for BFS, much faster than Solution 1
    private List<List<Integer>> verticalOrder_bfs_withouotsorting(TreeNode root) {
        if (root == null) return new ArrayList<>();
        
        Map<Integer, List<Integer>> colToNodesMap = new HashMap<>();
        
        int minCol = 0, maxCol = 0;
        
        Deque<Pair<TreeNode, Integer>> queue = new ArrayDeque<>();
        queue.offer(new Pair(root, 0));
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; ++i) {
                Pair<TreeNode, Integer> pair = queue.poll();
                TreeNode node = pair.getKey();
                int col = pair.getValue();

                List<Integer> list = colToNodesMap.computeIfAbsent(col, k -> new ArrayList<>());
                list.add(node.val);
                
                minCol = Math.min(minCol, col);
                maxCol = Math.max(maxCol, col);
                
                if (node.left != null) {
                    queue.offer(new Pair(node.left, col - 1));
                }
                if (node.right != null) {
                    queue.offer(new Pair(node.right, col + 1));
                }
            }
        }
        
        // return colToNodesMap.keySet()
        //     .stream()
        //     .sorted()
        //     .map(k -> colToNodesMap.get(k))
        //     .collect(Collectors.toList());
        
        // Good idea!!!
        List<List<Integer>> result = new ArrayList<>();
        for (int col = minCol; col <= maxCol; ++col) {
            result.add(colToNodesMap.get(col));
        }
        return result;
    }
    
    // Solution 3: dfs, Time Complexity: O(W⋅HlogH)) where WW is the width of the binary tree (i.e. the number of columns in the result) and HH is the height of the tree.
    private Map<Integer, List<Pair<Integer, Integer>>> colToNodesMap;
    private int minCol, maxCol;
    private List<List<Integer>> verticalOrder_dfs(TreeNode root) {
        if (root == null) return new ArrayList<>();
        // we need to save the row number as well, because dfs can not guarantee the vertical order
        this.colToNodesMap = new HashMap<>();
        this.minCol = 0;
        this.maxCol = 0;

        preorder(root, 0, 0);
        
        List<List<Integer>> result = new ArrayList<>();
        for (int col = minCol; col <= maxCol; ++col) {
            List<Pair<Integer, Integer>> list = colToNodesMap.get(col);
            List<Integer> sortedList = list.stream()
                .sorted((Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) -> p1.getKey() - p2.getKey())
                .map(p -> p.getValue())
                .collect(Collectors.toList());
            result.add(sortedList);
        }
        return result;
    }
    
    private void preorder(TreeNode node, int row, int col) {
        if (node == null) return;
        List<Pair<Integer, Integer>> list = colToNodesMap.computeIfAbsent(col, k -> new ArrayList<>());
        list.add(new Pair(row, node.val));
        
        minCol = Math.min(minCol, col);
        maxCol = Math.max(maxCol, col);
        
        preorder(node.left, row + 1, col - 1);
        preorder(node.right, row + 1, col + 1);
    }

}



