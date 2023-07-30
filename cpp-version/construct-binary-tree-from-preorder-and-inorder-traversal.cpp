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
    typedef vector<int>::iterator Iterator;
    TreeNode *buildTree(vector<int> &preorder, vector<int> &inorder) {
        return buildTree(preorder.begin(), preorder.end(), inorder.begin(), inorder.end());
    }
    TreeNode *buildTree(Iterator first1, Iterator last1, Iterator first2, Iterator last2) {
        if (first1 == last1 || first2 == last2) return NULL;
        auto iter = find(first2, last2, *first1);
        auto d = distance(first2, iter);
        TreeNode *root = new TreeNode(*first1);
        root->left = buildTree(next(first1), next(first1, d+1), first2, iter);
        root->right = buildTree(next(first1, d+1), last1, next(iter), last2);
        return root;
    }
};
