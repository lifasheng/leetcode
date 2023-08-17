/*
Given the root of a binary tree, return its maximum depth.

A binary tree's maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.


Example 1:
Input: root = [3,9,20,null,null,15,7]
Output: 3

Example 2:
Input: root = [1,null,2]
Output: 2
 
Constraints:
The number of nodes in the tree is in the range [0, 104].
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
    public int maxDepth(TreeNode root) {
        return solution4(root);
    }

    /*
    递归 + 先根遍历
    */
    int maxDepth = 0;
    private int solution1(TreeNode root) {
        preOrder(root, 1);
        return maxDepth;
    }

    private void preOrder(TreeNode root, int depth) {
        if (root == null) {
            return;
        }

        maxDepth = Math.max(maxDepth, depth);

        preOrder(root.left, depth + 1);
        preOrder(root.right, depth + 1);
    }

    /*
    迭代 + 中根遍历
    */
    private int solution2(TreeNode root) {
        int maxDepth = 0;

        if (root == null) {
            return maxDepth;
        }

        Deque<Pair<TreeNode, Integer>> stack = new ArrayDeque<>();

        TreeNode p = root;
        int depth = 1;
        while (p != null || !stack.isEmpty()) {
            if (p != null) { // 访问左子树
                stack.push(new Pair<>(p, depth));
                p = p.left;
                ++ depth;
            } else {
                // 访问根节点
                Pair<TreeNode, Integer> pair = stack.pop();
                p = pair.getKey();
                depth = pair.getValue();
                maxDepth = Math.max(maxDepth, depth);

                // 访问右子树
                p = p.right;
                ++ depth;
            }
        }
        return maxDepth;
    }

    /*
    后根遍历 + 递归
    */
    private int solution3(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftMax = solution3(root.left);
        int rightMax = solution3(root.right);
        return Math.max(leftMax, rightMax) + 1;
    }

    private int solution4(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int depth = 0;

        Queue<TreeNode> q = new LinkedList<>(); // ArrayDeque<>();
        q.offer(root);
        while(!q.isEmpty()) {
            int size = q.size();
            ++ depth;
            for (int i = 0; i < size; ++i) {
                TreeNode node = q.poll();
                if (node.left != null) {
                    q.offer(node.left);
                }
                if (node.right != null) {
                    q.offer(node.right);
                }
            }
        }
        return depth;
    }
}


