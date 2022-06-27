/*
Given the root of a binary tree, collect a tree's nodes as if you were doing this:

Collect all the leaf nodes.
Remove all the leaf nodes.
Repeat until the tree is empty.
 

Example 1:

Input: root = [1,2,3,4,5]
Output: [[4,5,3],[2],[1]]
Explanation:
[[3,5,4],[2],[1]] and [[3,4,5],[2],[1]] are also considered correct answers since per each level it does not matter the order on which elements are returned.
Example 2:

Input: root = [1]
Output: [[1]]
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
    // 在先根遍历的时候，如果是leaf节点，我们reutnr null，这样就可以更新父节点。
    // 还有一种方法是用map先保存父子关系，这样也可以更新父节点。
    private List<List<Integer>> result;
    public List<List<Integer>> findLeaves(TreeNode root) {
        result = new ArrayList<>();
        if (root == null) return result;
        
        while(root != null) {
            List<Integer> list = new ArrayList<>();
            root = preorderTraverse(root, list);
            result.add(list);
        }
        return result;
    }
    
    private TreeNode preorderTraverse(TreeNode node, List<Integer> list) {
        if (node == null) return null;
        
        if (isLeaf(node)) {
            list.add(node.val);
            return null;
        }
        
        node.left = preorderTraverse(node.left, list);
        node.right = preorderTraverse(node.right, list);
        return node;
    }
    
    private boolean isLeaf(TreeNode node) {
        return node.left == null && node.right == null;
    }
}


