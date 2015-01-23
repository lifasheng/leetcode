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
    vector<int> postorderTraversal(TreeNode *root) {
        vector<int> result;
        postorderTraversal(root, result);
        return result;
    }
    
    void postorderTraversal(TreeNode *root, vector<int> &result) {
        if (!root) return;
        postorderTraversal(root->left, result);
        postorderTraversal(root->right, result);
        result.push_back(root->val);
    }
#endif
#ifdef M2 // 迭代版本
    vector<int> postorderTraversal(TreeNode *root) {
        vector<int> result;
        stack<std::pair<TreeNode*,bool> > s;
        
        TreeNode *p = root;
        while(p || !s.empty()) {
            if (p) {
                s.push(make_pair(p, false)); // 当前结点入栈
                p = p->left; // 先访问其左孩
            }
            else {
                if (!s.top().second) { // 当前结点的右孩子还没访问过
                    s.top().second = true;
                    p = s.top().first->right;
                }
                else {
                    p = s.top().first;
                    s.pop();
                    result.push_back(p->val); // 访问当前结点
                    p = NULL;  // 这个很重要，当前结点访问完后，以该结点为根的子树都访问过了，所以要回退到其父结点了。
                }
            }
        }
        
        return result;
    }
#endif
};
