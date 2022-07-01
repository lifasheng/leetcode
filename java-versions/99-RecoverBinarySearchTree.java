/*
You are given the root of a binary search tree (BST), where the values of exactly two nodes of the tree were swapped by mistake. Recover the tree without changing its structure.

Example 1:
Input: root = [1,3,null,null,2]
Output: [3,1,null,null,2]
Explanation: 3 cannot be a left child of 1 because 3 > 1. Swapping 1 and 3 makes the BST valid.

Example 2:
Input: root = [3,1,4,null,null,2]
Output: [2,1,4,null,null,3]
Explanation: 2 cannot be in the right subtree of 3 because 2 < 3. Swapping 2 and 3 makes the BST valid.
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
    public void recoverTree(TreeNode root) {
        recoverTree_recursive(root);
    }
    
    // Solution 1: O(N) time, O(N) space, 3 pass
    private void recoverTree_usingInOrder(TreeNode root) {
        if (root == null) return;
        
        List<Integer> list = new ArrayList<>();
        inorder(root, list);
        int[] swappedNumbers = findTwoSwapped(list);
        recover(root, 2, swappedNumbers[0], swappedNumbers[1]);
    }
    
    // get almost sorted values
    private void inorder(TreeNode node, List<Integer> list) {
        if (node == null) return;
        
        inorder(node.left, list);
        list.add(node.val);
        inorder(node.right, list);
    }
    
    // 1 2 3 4 5 => 3 2 1 4 5
    // 1 2 3 => 2 1 3
    private int[] findTwoSwapped(List<Integer> list) {
        int n = list.size();
        int x = -1;
        int y = -1;
        boolean isXFound = false;
        for (int i = 1; i < n; ++i) {
            if (list.get(i) < list.get(i-1)) {
                y = list.get(i);
                if (!isXFound) {
                    x = list.get(i-1);
                    isXFound = true;
                } else {
                    break;
                }
            }
        }
        return new int[]{x, y};
    }
    
    private void recover(TreeNode node, int count, int x, int y) {
        if (node == null) return;
        
        if (node.val == x || node.val == y) {
            node.val = (node.val == x) ? y : x;
            -- count;
            if (count == 0) return;
        }
        
        recover(node.left, count, x, y);
        recover(node.right, count, x, y);
        
    }
    
    // Solution 2: O(N) time, O(N) space, One pass
    private void recoverTree_Iterative(TreeNode root) {
        if (root == null) return;
        
        TreeNode x = null, y = null, prev = null;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode p = root;
        
        while (p != null || !stack.isEmpty()) {
            while (p != null) {
                stack.push(p);
                p = p.left;
            }
            
            p = stack.pop();
            
            if (prev != null && p.val < prev.val) {
                y = p;
                if (x == null) {
                    x = prev;
                } else {
                    break;
                }
            }
            
            prev = p;
            p = p.right;
        }
        
        swap(x, y);
    }
    
    private void swap(TreeNode x, TreeNode y) {
        int vx = x.val;
        x.val = y.val;
        y.val = vx;
    }
    
    
    private TreeNode x = null, y = null, prev = null;
    // Solution 3: O(N) time, O(N) space, One pass
    private void recoverTree_recursive(TreeNode root) {
        if (root == null) return;
        
        findTwoSwapped(root);
        swap(x, y);
    }
    
    private void findTwoSwapped(TreeNode node) {
        if (node == null) return;
        
        findTwoSwapped(node.left);
        if (prev != null && node.val < prev.val) {
            y = node;
            if (x == null) {
                x = prev;
            } else {
                return;
            }
        }
        prev = node;
        findTwoSwapped(node.right);
    }
    
}


