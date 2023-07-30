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
#define M5
#ifdef M1 // 递归版
// 类似于二叉树的先根遍历， 巧妙之处在于二维数组的处理上。
    vector<vector<int> > levelOrder(TreeNode *root) {
        vector<vector<int> > result;
        levelOrder(root, 1, result);
        return result;
    }
    void levelOrder(TreeNode *root, int level, vector<vector<int> > &result) {
        if (!root) return;
        if (level > result.size()) {  // 为level这层建立vector
            result.push_back(vector<int>());
        }
        
        result[level-1].push_back(root->val);
        levelOrder(root->left, level+1, result);
        levelOrder(root->right, level+1, result);
    }
#endif
#ifdef M2 // 迭代版
// 用两个queue的方法, 这里对二维数组的处理方法和递归版本类似。
// 这种技巧值得学习。
    vector<vector<int> > levelOrder(TreeNode *root) {
        vector<vector<int> > result;
        if (!root) return result;
        
        queue<TreeNode*> current, next;
        int level = 0;
        
        current.push(root);
        while(!current.empty()) {
            ++level;
            while(!current.empty()) {
                if (level > result.size()) { // 为当前层建立vecotr
                    result.push_back(vector<int>());
                }
                
                TreeNode *p = current.front();
                current.pop();
                result[level-1].push_back(p->val); // 访问当前层上的结点
                
                if (p->left) {
                    next.push(p->left);
                }
                if (p->right) {
                    next.push(p->right);
                }
            }
            // 此时current为空，next可能不为空
            swap(current, next);
        }
        
        return result;
    }
#endif
#ifdef M3 // 迭代版
// 用两个queue的方法，这里对二维数组处理方法很传统。
    vector<vector<int> > levelOrder(TreeNode *root) {
        vector<vector<int> > result;
        if (!root) return result;
        
        queue<TreeNode*> current, next;
        vector<int> level;
        
        current.push(root);
        while(!current.empty()) {
            while(!current.empty()) {
                TreeNode *p = current.front();
                current.pop();
                level.push_back(p->val); // 访问当前层上的结点
                
                if (p->left) {
                    next.push(p->left);
                }
                if (p->right) {
                    next.push(p->right);
                }
            }
            result.push_back(level);
            level.clear();
            swap(current, next);
        }
        
        return result;
    }
#endif
#ifdef M4 // 迭代版
// 用一个queue的方法，这里对二维数组处理方法同样可以有两种选择，这里只用传统方法（类似于M3）。
// 这里用了一个技巧，就是用NULL 来标识一层的结束。
// 如果不想把NULL加到队列的话，可以用一个指针来指向每层的最后一个元素， 见M5
    vector<vector<int> > levelOrder(TreeNode *root) {
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
        
        return result;
    }
#endif
#ifdef M5 // 迭代版
// 用一个queue的方法，这里对二维数组处理方法同样可以有两种选择，这里只用传统方法（类似于M3）。
// 这里用了一个技巧，就是用一个指针来指向每层的最后一个元素
    vector<vector<int> > levelOrder(TreeNode *root) {
        vector<vector<int> > result;
        if (!root) return result;
        
        queue<TreeNode*> q;
        vector<int> level;
        
        TreeNode *last = root;
        q.push(root);
        while(!q.empty()) {
            TreeNode *p = q.front();
            q.pop();
            
            level.push_back(p->val); // 访问当前层上的结点
            
            if (p->left) {
                q.push(p->left);
            }
            if (p->right) {
                q.push(p->right);
            }
            
            if (p == last) { // 当前层最后一个元素
                result.push_back(level);
                level.clear();
                if (!q.empty()) { // 更新last指针
                    last = q.back();
                }
            }
        }
        
        return result;
    }
#endif
};
