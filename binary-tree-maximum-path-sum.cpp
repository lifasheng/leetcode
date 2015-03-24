/**
 * Definition for binary tree
 * struct TreeNode {
 *     int val;
 *     TreeNode *left;
 *     TreeNode *right;
 *     TreeNode(int x) : val(x), left(NULL), right(NULL) {}
 * };
 */
class Solution {
public:
/*
可以利用“最大连续子序列和”问题的思路， 
先算出左右子树的结果 L 和 R,如果 L 大于 0,那么对后续结果是有利的,我们
加上 L,如果 R 大于 0,对后续结果也是有利的,继续加上 R。
这里一个当前最大和cur，还一个全局最大和maxSum。
注意,最后 return 的时候,只返回一个方向上的值,为什么?这是因为在递归中,只能向父节
点返回,不可能存在 L->root->R 的路径,只可能是 L->root 或 R->root。
*/
    int maxPathSum(TreeNode *root) {
        int maxSum = INT_MIN;
        dfs(root, maxSum);
        return maxSum;
    }
    int dfs(TreeNode *root, int &maxSum) {
        if(!root) return 0;
        int l = dfs(root->left, maxSum);
        int r = dfs(root->right, maxSum);
        int cur = root->val;
        cur += (l>0)?l:0;
        cur += (r>0)?r:0;
        maxSum = max(maxSum, cur);
        return max(l,r)>0?max(l,r)+root->val : root->val;
    }
};
