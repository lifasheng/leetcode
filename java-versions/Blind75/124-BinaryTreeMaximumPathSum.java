/*
A path in a binary tree is a sequence of nodes where each pair of adjacent nodes in the sequence has an edge connecting them. A node can only appear in the sequence at most once. Note that the path does not need to pass through the root.

The path sum of a path is the sum of the node's values in the path.

Given the root of a binary tree, return the maximum path sum of any non-empty path.

Example 1:
Input: root = [1,2,3]
Output: 6
Explanation: The optimal path is 2 -> 1 -> 3 with a path sum of 2 + 1 + 3 = 6.

Example 2:
Input: root = [-10,9,20,null,null,15,7]
Output: 42
Explanation: The optimal path is 15 -> 20 -> 7 with a path sum of 15 + 20 + 7 = 42.
 
Constraints:
The number of nodes in the tree is in the range [1, 3 * 104].
-1000 <= Node.val <= 1000
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
    int maxSum = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        helper(root);
        return maxSum;
    }

    /*
    用一个全局变量来保存最终的结果。

    递归函数每次返回的是包含根节点以及它的一个孩子（如果孩子节点的sum>0)的路径。
    我们不能包含根节点以及两个孩子，这样往上返回的话就不是一个valid的路径了。
    比如，对于左边example 2中根节点为20时，只能返回15-20这个path，而不能同时返回15-20-7这个path

    对于每个节点，我们会递归其左右子节点，并判断以下几种情况：
    1. 如果左子节点返回的值大于0，那么在当前节点可以考虑加上左子节点；
    2. 如果右子节点返回的值大于0，那么在当前节点可以考虑加上右子节点；
    3. 如果左右子节点都不大于0, 则只考虑当前根节点；
    这样就可以在当前根节点得到局部最大值，并更新全局最大值。

    对于左边example 2，根节点为-10时，左节点返回了9，右节点返回的是35 （15-20 这个path），
    所以在-10这个根节点看来，局部最大值是9+ （-10）+35 = 34

    当递归到根节点为20时，左节点返回了15，右节点返回了7，局部最大值为15+20+7 = 42
    同时往上返回的时候只能返回一条边，也就是15-20

    */
    private int helper(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int left = Math.max(0, helper(root.left));
        int right = Math.max(0, helper(root.right));

        maxSum = Math.max(maxSum, root.val + left + right);

        return root.val + Math.max(left, right);
    }
}

