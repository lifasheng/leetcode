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
        vector<TreeNode*> list;
        vector<int > vals;
        InOrderTraversal(root, list, vals);
        
        sort(vals.begin(), vals.end());
        for(int i =0; i< list.size(); i++) {
            list[i]->val = vals[i];
        }
    }
    void InOrderTraversal(TreeNode* node, vector<TreeNode*>& list, vector<int>& vals) {
        if(node == NULL) return;
        InOrderTraversal(node->left, list, vals);
        list.push_back(node);
        vals.push_back(node->val);
        InOrderTraversal(node->right, list, vals); 
    }
#endif
};
