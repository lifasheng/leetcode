/*
Given a binary search tree with non-negative values, find the minimum absolute difference between values of any two nodes.

Example:

Input:

   1
    \
     3
    /
   2

Output:
1

Explanation:
The minimum absolute difference is 1, which is the difference between 2 and 1 (or between 2 and 3).
 

Note: There are at least two nodes in this BST.

*/

/*
idea:
basically, the minimum difference exists in two adjacent nodes when the tree is inorder traversed. For the above given example it is: (1, 2, 3)
In CPP, we can use reference to record the prevNode and minVal.
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
public:
    int getMinimumDifference(TreeNode* root) {
        if (root == NULL) return 0;
        int minVal = INT_MAX;
        TreeNode *prevNode = NULL;
        traveralTree(root, minVal, prevNode);
        return minVal;
    }

    void traveralTree(TreeNode *root, int &minVal, TreeNode *&prevNode) {
        if (root != NULL) {
            traveralTree(root->left, minVal, prevNode);
            if (prevNode != NULL) {
                minVal = min(minVal, abs(root->val - prevNode->val));
            }
            prevNode = root;
            traveralTree(root->right, minVal, prevNode);
        }
    }
};
