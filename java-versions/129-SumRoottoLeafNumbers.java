/*
Medium

You are given the root of a binary tree containing digits from 0 to 9 only.

Each root-to-leaf path in the tree represents a number.

For example, the root-to-leaf path 1 -> 2 -> 3 represents the number 123.
Return the total sum of all root-to-leaf numbers. Test cases are generated so that the answer will fit in a 32-bit integer.

A leaf node is a node with no children.

Example 1:
Input: root = [1,2,3]
Output: 25
Explanation:
The root-to-leaf path 1->2 represents the number 12.
The root-to-leaf path 1->3 represents the number 13.
Therefore, sum = 12 + 13 = 25.

Example 2:
Input: root = [4,9,0,5,1]
Output: 1026
Explanation:
The root-to-leaf path 4->9->5 represents the number 495.
The root-to-leaf path 4->9->1 represents the number 491.
The root-to-leaf path 4->0 represents the number 40.
Therefore, sum = 495 + 491 + 40 = 1026.
 
Constraints:
The number of nodes in the tree is in the range [1, 1000].
0 <= Node.val <= 9
The depth of the tree will not exceed 10.
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
    public int sumNumbers(TreeNode root) {
        return sumNumbers_preorder_2(root);
    }
    
    // solution 1: recursive
    public int sumNumbers_recursive(TreeNode root) {
        if (root == null) return 0;
        return sumNumbers(root, 0);
    }
    
    private int sumNumbers(TreeNode node, int sum) {
        if (node == null) return 0;
        
        int newSum = 10 * sum + node.val;
        if (node.left == null && node.right == null) {
            return newSum;
        }
        
        return sumNumbers(node.left, newSum)
            + sumNumbers(node.right, newSum);
    }
    
    // solution 2: preorder iterative
    private int sumNumbers_preorder(TreeNode root) {
        if (root == null) return 0;
        
        Deque<Pair<TreeNode, Integer>> stack = new ArrayDeque<>();
        TreeNode p = root;
        int sum = 0;
        int curSum = 0;
        while (p != null || !stack.isEmpty()) {
            while (p != null) {
                curSum = curSum * 10 + p.val;
                if (p.left == null && p.right == null) {
                    sum += curSum;
                }
                
                stack.push(new Pair(p, curSum));
                p = p.left;
            }
            Pair<TreeNode, Integer> pair = stack.pop();
            p = pair.getKey();
            curSum = pair.getValue();
            p = p.right;
        }
        return sum;
    }
    
    // another way to implement preorder
    private int sumNumbers_preorder_2(TreeNode root) {
        if (root == null) return 0;
        
        Deque<Pair<TreeNode, Integer>> stack = new ArrayDeque<>();
        stack.push(new Pair(root, 0));
        int sum = 0;
        int curSum = 0;
        while (!stack.isEmpty()) {
            Pair<TreeNode, Integer> pair = stack.pop();
            root = pair.getKey();
            curSum = pair.getValue();
            
            if (root != null) {
                curSum = 10 * curSum + root.val;
                if (root.left == null && root.right == null) {
                    sum += curSum;
                }
                
                stack.push(new Pair(root.left, curSum));
                stack.push(new Pair(root.right, curSum));
            }
        }
        return sum;
    }
    
}

