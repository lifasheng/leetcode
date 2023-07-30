#include <iostream>
#include <cassert>
#include <string>
#include <sstream>
#include <vector>
#include <unordered_map>
using namespace std;

/*
 Given a binary tree, return all root-to-leaf paths.

For example, given the following binary tree:

   1
 /   \
2     3
 \
  5

All root-to-leaf paths are:

["1->2->5", "1->3"]
*/


struct TreeNode {
    int val;
    TreeNode *left;
    TreeNode *right;
    TreeNode(int x) : val(x), left(NULL), right(NULL) {}
};
 
class Solution {
public:
    vector<string> binaryTreePaths(TreeNode* root) {
        vector<string> paths;
        string path;
        bfs(root, paths, path);
        return paths;
    }
    void bfs(TreeNode *root, vector<string> &paths, string path) {
        if (root == NULL) return;

        // note that string does not support += int
        char buf[32];
        snprintf(buf,32, "%d", root->val);
        path += buf;
        if (root->left == NULL && root->right == NULL) {
            paths.push_back(path);
            return;
        }

        path += "->";
        bfs(root->left, paths, path);
        bfs(root->right, paths, path);
    }
};

// each string is formatted like "1->2", "2->5"
TreeNode* buildTree(vector<string> treeStr) {
    TreeNode *root = NULL;
    unordered_map<int, TreeNode*> m;
    for(auto & s : treeStr) {
        stringstream ss(s);
        int parent, child;
        string sep;
        ss >> parent;
        if (ss.peek() == ' ') ss.ignore(10, ' '); // skip space before ->
        ss.ignore(2);       // skip ->
        if (ss.peek() == ' ') ss.ignore(10, ' '); // skip space after ->
        ss >> child;
        cout << parent << " - " << child << endl;

        TreeNode *parentNode = m[parent] != NULL ? m[parent] : (m[parent] = new TreeNode(parent));
        TreeNode *childNode  = m[child]  != NULL ? m[child]  : (m[child]  = new TreeNode(child));
        if (parentNode->left == NULL) {
            parentNode->left = childNode;
        }
        else {
            parentNode->right = childNode;
        }
        if (root == NULL) {
            root = parentNode;
        }
    }

    return root;
}

void preOrder(TreeNode *root) {
    if (root == NULL) return;
    cout << root->val << " ";
    preOrder(root->left);
    preOrder(root->right);
}

int main() {
    Solution solution;

    vector<string> treeStr = {"1 -> 2", "1 -> 3", "2->5", "5->4", "3->6", "3->7"};
    TreeNode *root = buildTree(treeStr);
    //preOrder(root); cout << endl;
    vector<string> paths = solution.binaryTreePaths(root);
    for(auto & s : paths) { cout << s << endl; }

    assert(1);

    return 0;
}
