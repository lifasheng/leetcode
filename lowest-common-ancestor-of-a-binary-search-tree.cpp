#include <iostream>
#include <cassert>
#include <string>
#include <sstream>
#include <vector>
#include <unordered_map>
using namespace std;

/*
 Given a binary search tree (BST), find the lowest common ancestor (LCA) of two given nodes in the BST.

According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes v and w as the lowest node in T that has both v and w as descendants (where we allow a node to be a descendant of itself).”

        _______6______
       /              \
    ___2__          ___8__
   /      \        /      \
   0      _4       7       9
         /  \
         3   5

For example, the lowest common ancestor (LCA) of nodes 2 and 8 is 6. Another example is LCA of nodes 2 and 4 is 2, since a node can be a descendant of itself according to the LCA definition.
*/


struct TreeNode {
    int val;
    TreeNode *left;
    TreeNode *right;
    TreeNode(int x) : val(x), left(NULL), right(NULL) {}
};

#if 0

// recursive version
class Solution {
public:
    TreeNode* lowestCommonAncestor(TreeNode* root, TreeNode* p, TreeNode* q) {
        if (root == NULL) return NULL;
        if (p == NULL) return q;
        if (q == NULL) return p;

        if (p->val > q->val) {
            swap(p, q);
        }

        if (p->val < root->val && q->val < root->val) {
            return lowestCommonAncestor(root->left, p, q);
        }
        else if (p->val > root->val && q->val > root->val) {
            return lowestCommonAncestor(root->right, p, q);
        }
        else if (p->val < root->val && root->val < q->val) {
            return root;
        }
        else if (p->val == root->val || q->val == root->val) {
            return root;
        }
        else {
            assert(0);
            return NULL;
        }

    }
};

#else

class Solution {
public:
    TreeNode* lowestCommonAncestor(TreeNode* root, TreeNode* p, TreeNode* q) {
        if (root == NULL) return NULL;
        if (p == NULL) return q;
        if (q == NULL) return p;

        if (p->val > q->val) {
            swap(p, q);
        }
        vector<TreeNode*> pPath = findPath(root, p->val);
        vector<TreeNode*> qPath = findPath(root, q->val);
        int i=0, j=0, k=0;
        while(i<pPath.size() && j<qPath.size()) {
            if (pPath[i] == qPath[j]) {
                k = i;
                ++i;
                ++j;
            }
            else {
                break;
            }
        }

        return pPath[k];
    }
    vector<TreeNode*> findPath(TreeNode *root, int val) {
        vector<TreeNode*> path;
        while(root != NULL) {
            path.push_back(root);
            if (root->val > val) {
                root = root->left;
            }
            else if (root->val < val) {
                root = root->right;
            }
            else {
                break;
            }
        }
        return path;
    }
};

#endif
 

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

bool isBST(TreeNode *root, int minVal, int maxVal) {
    if (root == NULL) return true;

    if (root->val > minVal && root->val < maxVal) {
        return isBST(root->left, minVal, root->val) && isBST(root->right, root->val, maxVal);
    }
    return false;
}

bool isBST(TreeNode *root) {
    return isBST(root, INT_MIN, INT_MAX);
}

TreeNode* searchBST(TreeNode *root, int val) {
    if (root == NULL) return NULL;
    if (root->val == val) return root;
    else if (root->val > val) return searchBST(root->left, val);
    else return searchBST(root->right, val);
}

void preOrder(TreeNode *root) {
    if (root == NULL) return;
    cout << root->val << " ";
    preOrder(root->left);
    preOrder(root->right);
}

int main() {
    Solution solution;

    vector<string> treeStr = {
        "6 -> 2", "6 -> 8", 
        "2->0", "2->4", 
        "4->3", "4->5",
        "8->7", "8->9"
    };
    TreeNode *root = buildTree(treeStr);
    preOrder(root); cout << endl;
    assert(isBST(root));

    assert(solution.lowestCommonAncestor(root, searchBST(root, 0), searchBST(root, 3)) == searchBST(root, 2));
    assert(solution.lowestCommonAncestor(root, searchBST(root, 4), searchBST(root, 3)) == searchBST(root, 4));
    assert(solution.lowestCommonAncestor(root, searchBST(root, 5), searchBST(root, 3)) == searchBST(root, 4));

    return 0;
}
