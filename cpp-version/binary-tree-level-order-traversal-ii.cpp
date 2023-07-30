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
    vector<vector<int> > levelOrderBottom(TreeNode *root) {
        vector<vector<int> > result;
        if (!root) return result;
        
        queue<TreeNode*> q;
        vector<int> level;
        
        q.push(root);
        q.push(NULL);
        while(!q.empty()) {
            TreeNode *p = q.front();
            q.pop();
            
            if (!p) { // NULL
                result.push_back(level);
                level.clear();
                if (!q.empty()) { // 这个判断很重要，如果队列中没有元素了，就不能再加NULL，否则就死循环了。
                    q.push(NULL);
                }
                continue;
            }
            
            level.push_back(p->val); // 访问当前层上的结点
            
            if (p->left) {
                q.push(p->left);
            }
            if (p->right) {
                q.push(p->right);
            }
        }
        
        reverse(result.begin(), result.end());
        return result;
    }
};
