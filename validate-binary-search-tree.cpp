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
    bool isValidBST(TreeNode *root) {
        return isValidBST(root, (long long)INT_MIN-1, (long long)INT_MAX+1);
    }
    bool isValidBST(TreeNode *root, long long lowest, long long highest) {
        if (!root) return true;
        return lowest < root->val && root->val < highest 
        && isValidBST(root->left, lowest, root->val)
        && isValidBST(root->right, root->val, highest);
    }
};
