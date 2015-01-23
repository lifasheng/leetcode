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
#define M3
#ifdef M1 // 递归版本, O(n) time
    vector<int> preorderTraversal(TreeNode *root) {
        vector<int> result;
        preorderTraversal(root, result);
        return result;
    }
    void preorderTraversal(TreeNode *root, vector<int> &result) {
        if (!root) return;
        result.push_back(root->val);
        preorderTraversal(root->left, result);
        preorderTraversal(root->right, result);
    }
#endif
#ifdef M2 // 迭代版本1
    vector<int> preorderTraversal(TreeNode *root) {
        vector<int> result;
        if (!root) return result;
        
        stack<TreeNode*> s;
        s.push(root);
        while(!s.empty()) {
            TreeNode *p = s.top();
            s.pop();
            result.push_back(p->val); // 访问当前结点
            
            if (p->right) {
                s.push(p->right); //右结点入栈
            }
            if (p->left) {
                s.push(p->left); //左结点入栈
            }
        }
        
        return result;
    }
#endif
#ifdef M3 // 迭代版本2
    vector<int> preorderTraversal(TreeNode *root) {
        vector<int> result;
        
        stack<TreeNode*> s;
        TreeNode *p = root;
        while(p || !s.empty()) {
            if (p) {
                result.push_back(p->val); // 访问当前结点
                s.push(p); // 当前结点入栈，以备以后访问其右孩
                p = p->left; // 访问其左孩子
            }
            else {
                p = s.top(); //该结点以及其左孩子都已访问，现在访问其右孩子
                s.pop();
                p = p->right;
            }
        }
        
        return result;
    }
#endif
};
