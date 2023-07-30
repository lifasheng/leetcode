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
#ifdef M3 //morris
/*
http://www.cnblogs.com/AnnieKim/archive/2013/06/15/morristraversal.html
后续遍历稍显复杂，需要建立一个临时节点dump，令其左孩子是root。
并且还需要一个子过程，就是倒序输出某两个节点之间路径上的各个节点。
步骤：
当前节点设置为临时节点dump。
1. 如果当前节点的左孩子为空，则将其右孩子作为当前节点。
2. 如果当前节点的左孩子不为空，在当前节点的左子树中找到当前节点在中序遍历下的前驱节点。
   a) 如果前驱节点的右孩子为空，将它的右孩子设置为当前节点。当前节点更新为当前节点的左孩子。
   b) 如果前驱节点的右孩子为当前节点，将它的右孩子重新设为空。倒序输出从当前节点的左孩子到该前驱节点这条路径上的所有节点。当前节点更新为当前节点的右孩子。
3. 重复以上1、2直到当前节点为空。
*/
    void reverseTraversal(TreeNode *from, TreeNode *to, vector<int> &result) {
        stack<TreeNode*> s;
        s.push(from);
        if (from != to) {
            while(from != to) {
                from = from->right;
                s.push(from);
            }
        }
        
        while(!s.empty()) {
            result.push_back(s.top()->val);
            s.pop();
        }
    }
    vector<int> postorderTraversal(TreeNode *root) {
        vector<int> result;
        if (!root) return result;
        TreeNode dummy(-1);
        dummy.left = root;
        TreeNode *cur = &dummy, *prev = NULL;
        while(cur) {
            if (cur->left == NULL) {
                cur = cur->right;
            }
            else {
                prev = cur->left;
                while(prev->right != NULL && prev->right != cur) {
                    prev = prev->right;
                }
                if (prev->right == NULL) {
                    prev->right = cur;
                    cur = cur->left;
                }
                else {
                    prev->right = NULL;
                    reverseTraversal(cur->left, prev, result); // here!!!
                    cur = cur->right;
                }
            }
        }
        
        return result;
    }
#endif
};
