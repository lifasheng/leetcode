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
#ifdef M1 // 递归
/*
一开始错的方法：
public int minDepth(TreeNode root) {
  if (!root) return 0;
  int lh = minDepth(root->left);
  int rh = minDepth(root->right);
  return min(lh, rh) + 1;
}
问题在于，某个子树是NULL的时候，它不是叶子节点啊！
比如a#b, b才是叶子节点。
*/
    int minDepth(TreeNode *root) {
        if (!root) return 0;
        if (root->left == NULL && root->right == NULL) return 1;
        
        int lh = root->left  ? minDepth(root->left)  : INT_MAX;
        int rh = root->right ? minDepth(root->right) : INT_MAX;
        return min(lh, rh)+1;
    }
#endif
#ifdef M2 //迭代版 Since the aim is to find the shortest path, the BFS is better. 
    int minDepth(TreeNode *root) {
        if (!root) return 0;
        queue<pair<TreeNode*, int> > s;
        s.push({root, 1});
        while(!s.empty()) {
            pair<TreeNode*, int> p = s.front();
            s.pop();
            if (p.first->left == NULL && p.first->right == NULL) return p.second;
            if (p.first->left)  s.push({p.first->left, p.second+1});
            if (p.first->right) s.push({p.first->right, p.second+1});
        }
        return 0;
    }
#endif
};
