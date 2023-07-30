/*
Invert a binary tree.

Example:

Input:

     4
   /   \
  2     7
 / \   / \
1   3 6   9
Output:

     4
   /   \
  7     2
 / \   / \
9   6 3   1
Trivia:
This problem was inspired by this original tweet by Max Howell:

Google: 90% of our engineers use the software you wrote (Homebrew), but you can’t invert a binary tree on a whiteboard so f*** off.

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
public:
    TreeNode* invertTree(TreeNode* root) {
        if (root == NULL) return root;
        std::swap(root->left, root->right);
        invertTree(root->left);
        invertTree(root->right);
        return root;
    }

    void printTree(TreeNode *root) {
        if (root != NULL) {
            printTree(root->left);
            cout << root->val << " ";
            printTree(root->right);
        }
    }
};

int main() {
    Solution s;
    TreeNode *root = new TreeNode(4);
    TreeNode *l11 = new TreeNode(2);
    TreeNode *r11 = new TreeNode(7);
    TreeNode *l21 = new TreeNode(1);
    TreeNode *l22 = new TreeNode(3);
    TreeNode *r21 = new TreeNode(6);
    TreeNode *r22 = new TreeNode(9);

    root->left = l11;
    root->right = r11;
    l11->left = l21;
    l11->right = l22;
    r11->left = r21;
    r11->right = r22;

    s.printTree(root); cout << endl;
    TreeNode *root2 = s.invertTree(root);
    s.printTree(root2); cout << endl;

    return 0;
}


