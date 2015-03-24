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
#if 0
    int sumNumbers(TreeNode *root) {
        int result = 0;
        vector<TreeNode*> path;
        sumNumbers(root, path, result);
        return result;
    }
    void sumNumbers(TreeNode *root, vector<TreeNode*> &path, int &result) {
        if (!root) return;
        path.push_back(root);
        if (root->left == NULL && root->right == NULL) {
            result += sumPath(path);
        }
        sumNumbers(root->left, path, result);
        sumNumbers(root->right, path ,result);
        path.pop_back();
    }
    int sumPath(vector<TreeNode*> &path) {
        int result = 0;
        for(auto i:path) {
            result = result * 10 + i->val;
        }
        return result;
    }
#else
    int sumNumbers(TreeNode *root) {
        return dfs(root, 0);
    }
    int dfs(TreeNode *root, int sum) {
        if (!root) return 0; // 这里必须是返回0,不能返回sum
                             // 因为一个孩子为空的结点不能算叶子结点，考虑{1, 0}这棵树， 1的右孩子为空，但1不是叶子结点。
        
        if (root->left == NULL && root->right == NULL) {
            return sum*10 + root->val;
        }
        return dfs(root->left, sum*10+root->val) + dfs(root->right, sum*10+root->val);
        
    }
#endif
};
