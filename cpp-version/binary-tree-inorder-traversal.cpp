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
// O(n) time, O(n) space
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
#ifdef M3
// Morris traversal, O(n) time, O(1) space
// please refer: http://www.cnblogs.com/AnnieKim/archive/2013/06/15/morristraversal.html
    vector<int> inorderTraversal(TreeNode *root) {
        vector<int> result;

        TreeNode *cur = root, *prev = NULL;
        while(cur) {
            if (cur->left == NULL) {
                result.push_back(cur->val);
                cur = cur->right;
            }
            else {
                // 在当前结点的左子树中找到它在中序遍历中的前驱结点
                prev = cur->left;
                while(prev->right != NULL && prev->right != cur) {
                    prev = prev->right;
                }

                if (prev->right == NULL) {  // 建立联系，将前驱结点的右孩子指向当前结点
                    prev->right = cur;
                    cur = cur->left;
                }
                else {
                    prev->right = NULL; // 当前结点的左子树已经遍历过了，断开其前驱结点的右结点（恢复二叉树的形状）
                    result.push_back(cur->val);
                    cur = cur->right;
                }
            }
        }

        return result;
    }
#endif
};
