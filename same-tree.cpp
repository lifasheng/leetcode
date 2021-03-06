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
    bool isSameTree(TreeNode *p, TreeNode *q) {
        if (!p && !q) return true;
        if (!p || !q) return false;
        
        return p->val == q->val && isSameTree(p->left, q->left) && isSameTree(p->right, q->right);
    }
#endif
#ifdef M2 // 迭代版, 先根遍历1，采用根遍历特有的模式。
    bool isSameTree(TreeNode *p, TreeNode *q) {
        stack<TreeNode*> s;
        s.push(p);
        s.push(q);
        while(!s.empty()) {
            p = s.top();
            s.pop();
            q = s.top();
            s.pop();
            if (!p && !q) continue;
            if (!p || !q) return false;
            if (p->val != q->val) {
                return false;
            }

            s.push(p->right);
            s.push(q->right);

            s.push(p->left);
            s.push(q->left);
        }
        return true;
    }
#endif
#ifdef M3 // 迭代版， 先根遍历2, 采用先，中，后所使用的统一框架。
    bool isSameTree(TreeNode *p, TreeNode *q) {
        stack<TreeNode*> s;
        while( p || q || !s.empty()) {
            if (p && q) { // p,q 都不为空
                if (p->val != q->val) return false;
                s.push(p);
                s.push(q);
                p = p->left;
                q = q->left;
            }
            else if (p || q) { //p, q有一个为空
                return false;
            }
            else { // p, q 都为空，
                   // 这里最好也保持出栈顺序和入栈顺序相反,这样能保证p总是指向左子树，q总是指向右子树。
                   // 不过不相反也没关系，因为比较的两棵树是对的，先左子树，再右子树。
                   // 这里都是比较右子树，至于p，q指向哪个右子树不重要。
                q = s.top(); //p = s.top();
                s.pop();
                p = s.top(); //q = s.top();
                s.pop();

                p = p->right;
                q = q->right;
            }
        }

        return true;
    }
#endif
};
