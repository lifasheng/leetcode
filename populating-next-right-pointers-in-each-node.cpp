/**
 * Definition for binary tree with next pointer.
 * struct TreeLinkNode {
 *  int val;
 *  TreeLinkNode *left, *right, *next;
 *  TreeLinkNode(int x) : val(x), left(NULL), right(NULL), next(NULL) {}
 * };
 */
class Solution {
public:
#define M4
#ifdef M1
/*
思路：采用先序遍历的迭代版本，用一个栈辅助 （不知道这算不算不符合题意呢，因为这是O(n) space）
在遍历每个结点p的时候，让p的左孩子指向p的右孩子，而p的右孩子则指向p的next的左孩子(如果p的next不为空)。
*/
    void connect(TreeLinkNode *root) {
        if (!root) return;
        
        stack<TreeLinkNode*> s;
        TreeLinkNode *p;
        
        s.push(root);
        while(!s.empty()) {
            p = s.top();
            s.pop();
            
            if (p->left && p->right) {
                p->left->next = p->right; // left child points to right child
                if (p->next) {
                    p->right->next = p->next->left; // right child points to its parent's next's left
                }
                
                s.push(p->right);
                s.push(p->left);
            }
        }
    }
#endif
#ifdef M2
// morris 中序遍历的方法， O(n) time, O(1) space
    void connect(TreeLinkNode *root) {
        TreeLinkNode *cur = root, *prev = NULL;
        
        while (cur) {
            if (cur->left == NULL) {
                // 此处省略访问当前结点的代码
                cur = cur->right; // 注意在morris方法中，叶子结点的right是用来指向中序遍历的后续结点的，所以此处cur->right肯定不为空。
            }
            else {
                //下面这段代码不能放在访问当前结点的位置，跟一遍题目中的测试用例即明白了。
                // 放在这能保证每个结点的左孩子尽早指向它的右孩子，这样左孩子的子树就能利用这个信息了。
                // 例外，放在这会导致这段代码执行两遍，虽然效率上不会右太大损失，但还是加上一个if判断。
                //--------------------------------------
                if(cur->left->next == NULL) {
                    cur->left->next = cur->right;
                    if (cur->next) {
                        cur->right->next = cur->next->left;
                    }
                }
                //--------------------------------------
                
                prev = cur->left;
                while(prev->right && prev->right != cur) {
                    prev = prev->right;
                }
                
                if (prev->right == NULL) {
                    prev->right = cur;
                    cur = cur->left;
                }
                else {
                    prev->right = NULL;
                    // 此处省略访问当前结点的代码
                    cur = cur->right;
                }
                
                
            }
        }
    }
#endif
#ifdef M3  //递归 O(n) time
// 对每一层，用一个链表将其子结点串起来，形成了下一层。
// 这种方法充分利用了next指针。
    void connect(TreeLinkNode *root) {
        if (!root) return;
        TreeLinkNode dummy(-1);
        TreeLinkNode *prev = &dummy;
        for(TreeLinkNode *cur = root; cur; cur = cur->next) {
            if (cur->left) {
                prev->next = cur->left;
                prev = prev->next;
            }
            if (cur->right) {
                prev->next = cur->right;
                prev = prev->next;
            }
        }
        connect(dummy.next);
    }
#endif
#ifdef M4 // 迭代版, 也是一层一层地处理
// 和M3中的递版本很类似
    void connect(TreeLinkNode *root) {
        if (!root) return;
        
        while(root) {
            TreeLinkNode dummy(-1);
            TreeLinkNode *prev = &dummy;
            for(TreeLinkNode *cur = root; cur; cur=cur->next) {
                if (cur->left) {
                    prev->next = cur->left;
                    prev = prev->next;
                }
                if (cur->right) {
                    prev->next = cur->right;
                    prev = prev->next;
                }
            }
            root = dummy.next;
        }
    }
#endif
};
