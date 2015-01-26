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
#ifdef M1
// O(n) time, O(n) space
    void recoverTree(TreeNode *root) {
        if (!root) return;
        stack<TreeNode*> s;
        vector<TreeNode*> v;
        
        // 先中根遍历, 得到按val升序的指针数组
        TreeNode *p = root;
        while(p || !s.empty()) {
            if (p) {
                s.push(p);
                p = p->left;
            }
            else {
                p = s.top();
                s.pop();
                
                v.push_back(p);
                p = p->right;
            }
        }
        
        // 分别从前往后， 从后往前找到不符合升序的两个下标
        int i, j;
        int size = v.size();
        for(i=1; i<size; ++i) {
            if (v[i]->val < v[i-1]->val) break;
        }
        
        for(j=size-2; j>=0; --j) {
            if (v[j]->val > v[j+1]->val) break;
        }
        
        swap(v[i-1]->val, v[j+1]->val);
    }
#endif
#ifdef M2
// O(n)空间的解法比较直观，中序遍历一遍以后，重新赋值一遍即可，这个解法可以面向n个元素错位的情况。
// 这种方法用到了两个数组，但是能处理n个元素错位的情况。
    void recoverTree(TreeNode *root) {
        vector<TreeNode*> nodes;
        vector<int > vals;
        InOrderTraversal(root, nodes, vals);
        
        sort(vals.begin(), vals.end());
        for(int i =0; i< nodes.size(); i++) {
            nodes[i]->val = vals[i];
        }
    }
    void InOrderTraversal(TreeNode* node, vector<TreeNode*>& nodes, vector<int>& vals) {
        if(node == NULL) return;
        InOrderTraversal(node->left, nodes, vals);
        nodes.push_back(node);
        vals.push_back(node->val);
        InOrderTraversal(node->right, nodes, vals);
    }
#endif
#ifdef M3
/*
Morris O(n) space, O(1) space
please refer to: http://www.cnblogs.com/TenosDoIt/p/3445682.html
这个问题可以通过遍历一遍有序序列分下面2步完成：
(1) 首先找到第一个错误的数 first，即第一个比它后缀要大的数;
(2) 然后要找到第一个错误的数应该放置的位置（这就是第二个错误的数），
即要找到第一个比first大的数的前驱，这个前驱就是第一个错误的数应该放的位置，也就是第二个错误的数。
（注意一个特殊情况{0,1}，first为1，没有找到比first大的数，这时second就是最后一个数0）
// test case: binary tree: 层次遍历为[ [7] [5 9] [4 6 8 10] ], 5 和 8 被错误地交换了。
*/
    void recoverTree(TreeNode *root) {
        TreeNode *cur = root, *prev = NULL;
        // first,second 分别指向两个错误的节点，parent保存中序访问中当前节点的前驱
        // 需要注意的是，这里prev仅仅是用来找到当前结点的左子树的最大结点，那个结点就是中序遍历中当前结点的前驱结点;
        // 例如，[[5][4 6]]这个二叉树，根为5。 prev在这里用于找到5的前驱结点，即4。但是并没有用来找6的前驱。
        // 而parent则可以找到每个结点的前驱，因为它在每次访问一个结点的时侯都会更新。
        TreeNode *first = NULL, *second = NULL, *parent = NULL;

        while(cur) {
            if (cur->left == NULL) {
                // 在morris中序遍历中，这里是访问当前结点的代码
                //--------------------------------------------------
                if (parent != NULL)
                {
                    if (first == NULL && cur->val < parent->val) {
                        first = parent;
                    }
                    else if (first != NULL && second == NULL & cur->val > first->val) {
                        second = parent;
                    }
                }
                parent = cur;
                //--------------------------------------------------
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
                else { // 前驱结点的右孩子为当前结点
                    // 在morris中序遍历中，这里是访问当前结点的代码
                    // 其实此时parent肯定不为空， 这里加上只是为了和上面的代码保持一致。
                    // 因为此时访问的当前结点，其左子树不为空，所以它的parent肯定不为空。
                    //--------------------------------------------------
                    //if (parent != NULL)
                    {
                        if (first == NULL && cur->val < parent->val) {
                            first = parent;
                        }
                        else if (first != NULL && second == NULL & cur->val > first->val) {
                            second = parent;
                        }
                    }
                    parent = cur;
                    //--------------------------------------------------
                    prev->right = NULL;  // 断开前驱结点和当前结点之间的指针
                    cur = cur->right;
                }
            }
        }

        if (first != NULL) {
            if (second == NULL) second = parent;
            std::swap(first->val, second->val);
        }
    }
};
