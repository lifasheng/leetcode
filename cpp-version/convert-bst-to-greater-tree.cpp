/*
Given a Binary Search Tree (BST), convert it to a Greater Tree such that every key of the original BST is changed to the original key plus sum of all keys greater than the original key in BST.

Example:

Input: The root of a Binary Search Tree like this:
              5
            /   \
           2     13

Output: The root of a Greater Tree like this:
             18
            /   \
          20     13

*/


#include <iostream>
#include <vector>
#include <unordered_map>
#include <cassert>
#include <cmath>
#include <numeric>
#include <string>
#include<stdio.h>
using namespace std;


struct TreeNode {
    int val;
    TreeNode *left;
    TreeNode *right;
    TreeNode(int x) : val(x), left(NULL), right(NULL) {}
};

class Solution {
private:
    void traversalTree(TreeNode* root, vector<TreeNode*> & vt) {
        if (root != NULL) {
            traversalTree(root->left, vt);
            vt.push_back(root);
            traversalTree(root->right, vt);
        }
    }
public:
    TreeNode* convertBST(TreeNode* root) {
        vector<TreeNode*> vt;
        traversalTree(root, vt);
        long sum = 0;
        for(int i=vt.size()-1; i>=0; --i) {
            int originalValue = vt[i]->val;
            vt[i]->val += sum;
            sum += originalValue;
        }
        return root;
    }

    void printBST(TreeNode* root) {
        if (root != NULL) {
            printBST(root->left);
            cout << root->val << " ";
            printBST(root->right);
        }
    }
};

int main() {
    Solution s;
    TreeNode *root = new TreeNode(5);
    TreeNode *l1 = new TreeNode(2);
    TreeNode *r1 = new TreeNode(13);
    root->left = l1;
    root->right = r1;

    s.printBST(root); cout << endl;
    s.printBST(s.convertBST(root)); cout << endl;

    return 0;
}


