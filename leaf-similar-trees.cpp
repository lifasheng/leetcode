/*
Consider all the leaves of a binary tree.  From left to right order, the values of those leaves form a leaf value sequence.



For example, in the given tree above, the leaf value sequence is (6, 7, 4, 9, 8).

Two binary trees are considered leaf-similar if their leaf value sequence is the same.

Return true if and only if the two given trees with head nodes root1 and root2 are leaf-similar.

 

Note:

Both of the given trees will have between 1 and 100 nodes.
*/

/**
 * Definition for a binary tree node.
 * struct TreeNode {
 *     int val;
 *     TreeNode *left;
 *     TreeNode *right;
 *     TreeNode(int x) : val(x), left(NULL), right(NULL) {}
 * };
 */
class Solution {
private:
    void getLeavesOfTree(TreeNode *root, vector<int> &leaves) {
        if (root == NULL) return;
        if (root->left == NULL && root->right == NULL) {
            leaves.push_back(root->val);
            return;
        }
        getLeavesOfTree(root->left, leaves);
        getLeavesOfTree(root->right, leaves);
    }
    
    bool isSameSequence(vector<int> &v1, vector<int> &v2) {
        if (v1.size() != v2.size()) return false;
        for(int i=0; i<v1.size(); ++i) {
            if (v1[i] != v2[i]) return false;
        }
        return true;
    }
public:
    bool leafSimilar(TreeNode* root1, TreeNode* root2) {
        vector<int> v1, v2;
        getLeavesOfTree(root1, v1);
        getLeavesOfTree(root2, v2);
        return isSameSequence(v1, v2);
    }
};
