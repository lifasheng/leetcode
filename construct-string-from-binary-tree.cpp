/*
You need to construct a string consists of parenthesis and integers from a binary tree with the preorder traversing way.

The null node needs to be represented by empty parenthesis pair "()". And you need to omit all the empty parenthesis pairs that don't affect the one-to-one mapping relationship between the string and the original binary tree.

Example 1:
Input: Binary tree: [1,2,3,4]
       1
     /   \
    2     3
   /    
  4     

Output: "1(2(4))(3)"

Explanation: Originallay it needs to be "1(2(4)())(3()())", 
but you need to omit all the unnecessary empty parenthesis pairs. 
And it will be "1(2(4))(3)".
Example 2:
Input: Binary tree: [1,2,3,null,4]
       1
     /   \
    2     3
     \  
      4 

Output: "1(2()(4))(3)"

Explanation: Almost the same as the first example, 
except we can't omit the first parenthesis pair to break the one-to-one mapping relationship between the input and the output.
*/

#include <iostream>
#include <vector>
#include <unordered_map>
#include <cassert>
#include <cmath>
#include <numeric>
#include <string>
#include <stdio.h>
#include <sstream>
using namespace std;


struct TreeNode {
    int val;
    TreeNode *left;
    TreeNode *right;
    TreeNode(int x) : val(x), left(NULL), right(NULL) {}
 };

class Solution {
public:
    string tree2str(TreeNode* root) {
        if (root == NULL) return "";
        string left = tree2str(root->left);
        string right = tree2str(root->right);
        stringstream ss;
        ss << root->val;
        if (left.size() > 0 || right.size() > 0) {
            if (right.size() > 0) {
                ss << "(" << left << ")(" << right << ")";
            } else {
                ss << "(" << left << ")";
            }
        }
        return ss.str();
    }
};

int main() {
    Solution s;
    {
        TreeNode *root = new TreeNode(1);
        TreeNode *l11 = new TreeNode(2);
        TreeNode *r11 = new TreeNode(3);
        TreeNode *l21 = new TreeNode(4);

        root->left = l11;
        root->right = r11;
        l11->left = l21;

        cout << s.tree2str(root) << endl;
    }

    {
        TreeNode *root = new TreeNode(1);
        TreeNode *l11 = new TreeNode(2);
        TreeNode *r11 = new TreeNode(3);
        TreeNode *l21 = new TreeNode(4);

        root->left = l11;
        root->right = r11;
        l11->right = l21;

        cout << s.tree2str(root) << endl;
    }

    return 0;
}


