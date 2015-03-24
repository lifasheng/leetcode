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
    vector<vector<int> > pathSum(TreeNode *root, int sum) {
        vector<vector<int> > result;
        vector<int> path;
        pathSum(root, sum, path, result);
        return result;
    }
    void pathSum(TreeNode *root, int gap, vector<int> &path, vector<vector<int> > &result) {
        if (!root) return;
        
        path.push_back(root->val);
        if (root->left == NULL && root->right == NULL) {
            if (gap == root->val) {
                result.push_back(path);
            }
        }
        pathSum(root->left, gap-root->val, path, result);
        pathSum(root->right, gap-root->val, path, result);
        path.pop_back();
    }
};
