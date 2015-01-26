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
#ifdef M1 // 递归版
// O(n) time
    bool isSymmetric(TreeNode *left, TreeNode *right) {
        if (!left && !right) return true;
        if (!left || !right) return false;
        return left->val == right->val && isSymmetric(left->left, right->right) && isSymmetric(left->right, right->left);
    }
    bool isSymmetric(TreeNode *root) {
        if (!root) return true;
        return isSymmetric(root->left, root->right);
    }
#endif
#ifdef M2 // 迭代版1, 类似于same tree的迭代版1, 用这个版本相对不容易出错
    bool isSymmetric(TreeNode *root) {
        if (!root) return true;
        
        stack<TreeNode*> s;
        s.push(root->left);
        s.push(root->right);
        
        TreeNode *p, *q;
        while(!s.empty()) {
            p = s.top();
            s.pop();
            q = s.top();
            s.pop();
            
            if (!p && !q) continue;
            if (!p || !q) return false;
            
            if (p->val != q->val) return false;
            
            s.push(p->left);
            s.push(q->right);
            
            s.push(p->right);
            s.push(q->left);
        }
        
        return true;
    }
#endif
#ifdef M3 // 迭代版2, 类似于same tree的迭代版2
// 这个版本唯一需要注意的地方是入栈和出栈顺序得相反，不然就会出错！！！
// 而same tree的迭代版本1,2 以及symmetric tree的版本1就没有这个问题。
// 可以用测试用例：[[2][3 3][4 5 NULL 4]] 来测试一下。 
// 相当于比较两棵树：[[31] [41, 51]], [[32] [NULL, 42]], 其中1和2用于标识是哪棵树
// 版本1肯定是没问题的，因为每次都是把正确的两对入栈了，出栈时给哪个指针不影响结果。
// 而版本2对于same tree也没问题，因为p和q都是指向右子树。
// 但版本2对symmetric tree就要求p，q的入栈和出栈顺序相反，这样保证p和q始终分别指向左子树和右子树。
    bool isSymmetric(TreeNode *root) {
        if (!root) return true;
        
        stack<TreeNode*> s;
        TreeNode *p = root->left, *q = root->right;
        
        while(p || q || !s.empty()) {
            if (p && q) {  // p， q都不为空
                if (p->val != q->val) return false;
                s.push(p);
                s.push(q);
                p = p->left;
                q = q->right;
            }
            else if (p || q) { // p，q有一个为空
                return false;
            }
            else { // p，q都为空, 注意这里的出栈顺序要和上面的入栈顺序相反！！！
                q = s.top();
                s.pop();
                p = s.top();
                s.pop();
                
                p = p->right;
                q = q->left;
            }
        }
        
        return true;
    }
#endif
};
