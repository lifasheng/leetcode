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
    TreeNode *sortedArrayToBST(vector<int> &num) {
        return sortedArrayToBST(num, 0, num.size()-1);
    }
    TreeNode *sortedArrayToBST(vector<int> &num, int low, int high) {
        if (low > high) return NULL;
        
        int mid = (low+high)/2;
        TreeNode *root = new TreeNode(num[mid]);
        root->left = sortedArrayToBST(num, low, mid-1);
        root->right = sortedArrayToBST(num, mid+1, high);
        return root;
    }
};
