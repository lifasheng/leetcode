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
    vector<TreeNode *> generateTrees(int n) {
        if (n<=0) return generateTrees(1, 0);
        return generateTrees(1, n);
    }
    vector<TreeNode*> generateTrees(int start, int end) {
        vector<TreeNode*> result;
        if (start > end) {
            result.push_back(NULL);
            return result;
        }
        
        for(int i=start; i<=end; ++i) {
            vector<TreeNode*> left_trees = generateTrees(start, i-1);
            vector<TreeNode*> right_trees = generateTrees(i+1, end);
            for(auto j: left_trees) {
                for(auto k:right_trees) {
                    TreeNode *root = new TreeNode(i);
                    root->left = j;
                    root->right = k;
                    result.push_back(root);
                }
            }
        }
        
        return result;
    }
};
