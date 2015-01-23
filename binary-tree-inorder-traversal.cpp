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
#define M2
#ifdef M1 // 递归版本
    vector<int> inorderTraversal(TreeNode *root) {
        vector<int> result;
        inorderTraversal(root, result);
        return result;
    }
    void inorderTraversal(TreeNode *root, vector<int> &result) {
        if (!root) return;
        inorderTraversal(root->left, result);
        result.push_back(root->val);
        inorderTraversal(root->right, result);
    }
#endif
#ifdef M2 // 迭代版本
    vector<int> inorderTraversal(TreeNode *root) {
        vector<int> result;
        if (!root) return result;
        
        stack<TreeNode*> s;
        TreeNode *p = root;
        while(p || !s.empty()) {
            if (p) {
                s.push(p); // 当前结点入栈
                p = p->left; //先访问当前结点的左孩子
            }
            else {
                p = s.top();
                s.pop();
                result.push_back(p->val); // 访问当前结点
                p = p->right;  //访问当前结点的右孩子
            }
        }
        
        return result;
    }
#endif
};
