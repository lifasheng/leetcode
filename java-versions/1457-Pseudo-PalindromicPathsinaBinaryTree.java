/*
Given a binary tree where node values are digits from 1 to 9. A path in the binary tree is said to be pseudo-palindromic if at least one permutation of the node values in the path is a palindrome.

Return the number of pseudo-palindromic paths going from the root node to leaf nodes.

Example 1:
Input: root = [2,3,1,3,1,null,1]
Output: 2 
Explanation: The figure above represents the given binary tree. There are three paths going from the root node to leaf nodes: the red path [2,3,3], the green path [2,1,1], and the path [2,3,1]. Among these paths only red path and green path are pseudo-palindromic paths since the red path [2,3,3] can be rearranged in [3,2,3] (palindrome) and the green path [2,1,1] can be rearranged in [1,2,1] (palindrome).

Example 2:
Input: root = [2,1,1,1,3,null,null,null,null,null,1]
Output: 1 
Explanation: The figure above represents the given binary tree. There are three paths going from the root node to leaf nodes: the green path [2,1,1], the path [2,1,3,1], and the path [2,1]. Among these paths only the green path is pseudo-palindromic since [2,1,1] can be rearranged in [1,2,1] (palindrome).

Example 3:
Input: root = [9]
Output: 1

Constraints:

The number of nodes in the tree is in the range [1, 105].
1 <= Node.val <= 9
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
    
    private int count;
    public int pseudoPalindromicPaths (TreeNode root) {
        return pseudoPalindromicPaths_usingBitOperation(root);
    }
    
    // use map to calculate how many times each value appear, we can also use set, which is similar.
    private int pseudoPalindromicPaths_usingMap (TreeNode root) {
        this.count = 0;
        Map<Integer, Integer> valToTimesMap = new HashMap<>();
        dfs(root, valToTimesMap);
        return count;
    }
    
    private void dfs(TreeNode node, Map<Integer, Integer> valToTimesMap) {
        if (node == null) return;
        
        if (isLeaf(node)) {
            addToMap(node.val, valToTimesMap);
            if (isPseudoPalindromicPath(valToTimesMap)) {
                ++ count;
            }
            removeFromMap(node.val, valToTimesMap);
            
            return;
        }
        
        addToMap(node.val, valToTimesMap);
        dfs(node.left, valToTimesMap);
        dfs(node.right, valToTimesMap);
        removeFromMap(node.val, valToTimesMap);
    }
    
    private void addToMap(int val, Map<Integer, Integer> valToTimesMap) {
        if (valToTimesMap.containsKey(val)) {
            valToTimesMap.put(val, valToTimesMap.get(val) + 1);
        } else {
            valToTimesMap.put(val, 1);
        }
    }
    
    private void removeFromMap(int val, Map<Integer, Integer> valToTimesMap) {
        if (valToTimesMap.containsKey(val)) {
            valToTimesMap.put(val, valToTimesMap.get(val) - 1);
        }
    }
    
    private boolean isLeaf(TreeNode node) {
        return node.left == null && node.right == null;
    }
    
    private boolean isPseudoPalindromicPath(Map<Integer, Integer> valToTimesMap) {
        int n = 0;
        for (Map.Entry<Integer, Integer> entry : valToTimesMap.entrySet()) {
            if (entry.getValue() % 2 == 1) {
                ++ n;
                if (n > 1) return false;
            }
        }
        return n < 2;
    }
    
    
    // Solution 2: use bit operation to calculate how many times a number appear.
    // Since the node values are digits from 1 to 9, so we can use a int[] instead of map to calculate the times.
    // However, we can even improve further, using bit operation.
    // We only need 9 bits to represent the apperance times of each digit from 1 to 9.
    // And we only allow at most one bit is 1 in final path.
    private int pseudoPalindromicPaths_usingBitOperation (TreeNode root) {
        preorder(root, 0);
        return count;
    }
    private void preorder (TreeNode node, int path) {
        if (node == null) return;
        
        if (isLeaf(node)) {
            path ^= (1 << node.val);
            
            if ((path & (path - 1)) == 0) {
                ++ count;
            }
            
            path ^= (1 << node.val);
            return;
        }
        
        path ^= (1 << node.val);
        preorder(node.left, path);
        preorder(node.right, path);
        path ^= (1 << node.val);
    }
}

