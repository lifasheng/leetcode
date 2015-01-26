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
    bool isBalanced(TreeNode *root) {
        return height(root) >=0;
    }
    
    int height(TreeNode *root) {
        if (!root) return 0;
        int lh = height(root->left);
        if (lh == -1) return -1;
        int rh = height(root->right);
        if (rh == -1) return -1;
        if (abs(lh-rh) > 1) return -1;
        return max(lh, rh) + 1;
    }
};
