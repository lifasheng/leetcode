/*
Given the root of a binary tree, return the maximum width of the given tree.

The maximum width of a tree is the maximum width among all levels.

The width of one level is defined as the length between the end-nodes (the leftmost and rightmost non-null nodes), where the null nodes between the end-nodes that would be present in a complete binary tree extending down to that level are also counted into the length calculation.

It is guaranteed that the answer will in the range of a 32-bit signed integer.

Example 1:
Input: root = [1,3,2,5,3,null,9]
Output: 4
Explanation: The maximum width exists in the third level with length 4 (5,3,null,9).

Input: root = [1,3,2,5,null,null,9,6,null,7]
Output: 7
Explanation: The maximum width exists in the fourth level with length 7 (6,null,null,null,null,null,7).

Input: root = [1,3,2,5]
Output: 2
Explanation: The maximum width exists in the second level with length 2 (3,2).
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
                      0                               0 
            00                  01                   0  1
       000       001       010       011            0 1 2 3
    0000 0001 0010 0011 0100 0101 0110 0111      0 1 2 3 4 5 6 7
    */
    public int widthOfBinaryTree(TreeNode root) {
        return DFS(root);
    }
    
    class Pair {
        TreeNode node;
        Integer index;
        public Pair(TreeNode node, Integer index) {
            this.node = node;
            this.index = index;
        }
    }
    private int BFS(TreeNode root) {
        if (root == null) return 0;
        
        Queue<Pair> queue = new LinkedList<>();
        queue.add(new Pair(root, 0));
        
        int maxWidth = 1;
        while(!queue.isEmpty()) {
            int size = queue.size();
            int startIndex = -1;
            for (int i = 0; i < size; ++i) {
                Pair pair = queue.poll();
                if (startIndex == -1) {
                    startIndex = pair.index;
                } else {
                    maxWidth = Math.max(maxWidth, pair.index - startIndex + 1);
                }
                
                if (pair.node.left != null) {
                    queue.add(new Pair(pair.node.left, pair.index * 2));
                }
                if (pair.node.right != null) {
                    queue.add(new Pair(pair.node.right, pair.index * 2 + 1));
                }
            }
        }
        return maxWidth;
    }
    
    private int maxWith_dfs = 1;
    private Map<Integer, Integer> levelToFirstIndexMap = new HashMap<>();
    private int DFS(TreeNode root) {
        if (root == null) return 0;
        
        DFS(root, 0, 0);
        return maxWith_dfs;
    }
    
    private void DFS(TreeNode root, int level, int index) {
        if (root == null) return;
        
        if (!levelToFirstIndexMap.containsKey(level)) {
            levelToFirstIndexMap.put(level, index);
        } else {
            maxWith_dfs = Math.max(maxWith_dfs, index - levelToFirstIndexMap.get(level) + 1);
        }
        
        DFS(root.left, level + 1, 2 * index);
        DFS(root.right, level + 1, 2 * index + 1);
    }
}



