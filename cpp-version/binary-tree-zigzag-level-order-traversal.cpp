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
    vector<vector<int> > zigzagLevelOrder(TreeNode *root) {
        vector<vector<int> > result;
        if (!root) return result;
        
        queue<TreeNode*> current, next;
        vector<int> level;
        bool bShouldReverse = false;
        
        current.push(root);
        while(!current.empty()) {
            while(!current.empty()) {
                TreeNode *p = current.front();
                current.pop();
                
                level.push_back(p->val);
                if(p->left) next.push(p->left);
                if(p->right) next.push(p->right);
            }
            
            if (bShouldReverse) {
                reverse(level.begin(), level.end());
            }
            result.push_back(level);
            level.clear();
            swap(current, next);
            
            bShouldReverse = !bShouldReverse;
        }
        
        return result;
    }
};
