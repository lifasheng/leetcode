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
#ifdef M1 // 递归版，简单粗暴的递归，思路比较清晰
    void flatten(TreeNode *root) {
        if (!root) return;
        
        flatten(root->left);
        flatten(root->right);
        
        if (root->left == NULL) return;
        
        // 将左子树形成的链表插入到root和root->right之间
        TreeNode *p = root->left;
        while(p->right != NULL) {
            p = p->right;
        }
        p->right = root->right;
        
        // 更新root的左右指针
        root->right = root->left;
        root->left = NULL;
    }
#endif
#ifdef M2 // 递归版2
/*
思路看似很清晰，但用一个用例跟一下，发现很容易绕晕了。
但是想明白两点就不会晕了：
(1) 对于NULL结点，flatten之后的结果是tail。
(2) 对于叶子结点，flatten之后的结果：该结点的右孩子为tail，左孩子为NULL。
*/
    void flatten(TreeNode *root) {
        flatten(root, NULL);
    }
    // 把root这棵树变成链表后，将tail接在它后面。
    TreeNode* flatten(TreeNode *root, TreeNode *tail) {
        if (!root) return tail;
        
        TreeNode *flattenedRight = flatten(root->right, tail);
        TreeNode *flattenedLeft = flatten(root->left, flattenedRight);
        
        root->right = flattenedLeft;
        root->left = NULL;
        return root;
    }
#endif
#ifdef M3 // 迭代版1, 采用先序遍历的方法
    void flatten(TreeNode *root) {
        if (!root) return;
        
        stack<TreeNode*> s;
        s.push(root);
        while(!s.empty()) {
            TreeNode *p = s.top();
            s.pop();
            
            if (p->right) s.push(p->right);
            if (p->left) s.push(p->left);
            
            if (!s.empty()) { // 如果栈为空，说明p的左右孩子都为NULL， 所以不需要更新它的左右孩子了。
                p->right = s.top();
                p->left = NULL;
            }
        }
    }
#endif 
#ifdef M4 // 迭代版2
/*
http://yucoding.blogspot.com/2013/01/leetcode-question-30-flatten-binary.html
in-place operation.
flatten的过程就是将右孩子接到左孩子的后面，并把左孩子设为右孩子。
我们可以先找到左孩子的最右子结点，然后把右孩子接到该结点后面。
具体步骤：
(1) 找到当前结点的左子树中的最右子节点;
(2) 将当前结点的右孩子接到该结点之后;
(3) 将当前结点的右孩子指向当前结点的左孩子;
(4) 将当前结点的左孩子设为NULL;
(5) 将当前结点指向其右孩子;
(6) 重复1-5,直到当前结点为空;
*/
    void flatten(TreeNode *root) {
        while (root){
            if (root->left){
                TreeNode* pre=root->left;
                while (pre->right){pre = pre->right;}
                pre->right = root->right;
                root->right = root->left;
                root->left = NULL;
            }
            root=root->right;
        }
    }
#endif
};
