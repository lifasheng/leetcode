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
#define M3
#ifdef M1  //递归 O(n) time, 同populating-next-right-pointers-in-each-node。
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
#ifdef M2 // 迭代版, 也是一层一层地处理， 同populating-next-right-pointers-in-each-node。
// 和M1中的递版本很类似，只是用next取代了递归而已。
    void connect(TreeLinkNode *root) {
        if (!root) return;
        
        while (root) {
            TreeLinkNode dummy(-1);
            TreeLinkNode *prev = &dummy;
            TreeLinkNode *next = NULL;
            for(TreeLinkNode *cur = root; cur; cur = cur->next) {
                if (cur->left) {
                    prev->next = cur->left;
                    prev = prev->next;
                    if (!next) next = cur->left;
                }
                 
                if (cur->right) {
                    prev->next = cur->right;
                    prev = prev->next;
                    if (!next) next = cur->right;
                }
            }
            root = next;
        }
    }
#endif
#ifdef M3  //超时了：-（
// morris 中序遍历的方法， O(n) time, O(1) space  
// test case： {1,2,3,4,#,#,5} {1,2,3,4,5,#，7,6,10,#，#，#，#，8,9}
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
                // 例外，放在这会导致这段代码执行两遍，加上一个if判断。
                //--------------------------------------
                if(cur->left->next == NULL) {
                    if (cur->right != NULL) {
                        cur->left->next = cur->right; // 左孩子指向右孩子
                        if (cur->next) { // 为右孩子找到next结点
                            TreeLinkNode *p = cur->next;
                            while (p && p->left == NULL && p->right == NULL) p = p->next;
                            if (p != NULL)
                                cur->right->next = p->left ? p->left : p->right;
                        }
                    }
                    else { // 右孩子为空
                        if (cur->next) { // 为左孩子找到next结点
                            TreeLinkNode *p = cur->next;
                            while (p && p->left == NULL && p->right == NULL) p = p->next;
                            if (p != NULL)
                                cur->left->next = p->left ? p->left : p->right;
                        }
                    }
                }
                //--------------------------------------
                
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
                    // 此处省略访问当前结点的代码
                    cur = cur->right;
                }
            }
        }
    }
#endif
};
