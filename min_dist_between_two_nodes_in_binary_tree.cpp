/*

https://www.geeksforgeeks.org/find-distance-between-two-nodes-of-a-binary-tree/

https://practice.geeksforgeeks.org/problems/min-distance-between-two-given-nodes-of-a-binary-tree/1

Min distance between two given nodes of a Binary Tree

*/


/*Please note that it's Function problem i.e.
you need to write your solution in the form of Function(s) only.
Driver Code to call/invoke your function is mentioned above.*/

/* A binary tree node
struct Node
{
    int data;
    Node* left, * right;
}; */
/* Should return minimum distance between a and b 
   in a tree with given root*/
   

int distToParent(Node* parent, vector<Node*> &path) {
    int pos = 0;
    for (int i=0; i<path.size(); ++i) {
        if (path[i] == parent) {
            pos = i;
            break;
        }
    }
    return path.size()-pos-1;
}

Node* findLowestParent(vector<Node*> &pathA, vector<Node*> &pathB) {
    int lenA = pathA.size();
    int lenB = pathB.size();
    int n = min(lenA, lenB);
    Node *parent = NULL;
    for(int i=0; i<n; ++i) {
        if (pathA[i] == pathB[i]) {
            parent = pathA[i];
        } else {
            break;
        }
    }
    return parent;
}

bool findPath(Node* root,int v, vector<Node*> &path) {
    if (root == NULL) return false;
    
    if (root->data == v) {
        path.push_back(root);
        return true;
    }
    path.push_back(root);
    
    bool found = findPath(root->left, v, path);
    if (!found) {
        found = findPath(root->right, v, path);
    }
    if (!found) {
        path.pop_back();
    }
    return found;
}
   

int findDist(Node* root, int a, int b)
{
    if (root == NULL) return 0;
    vector<Node*> pathA;
    vector<Node*> pathB;
    bool foundA = findPath(root, a, pathA);
    bool foundB = findPath(root, b, pathB);
    if (foundA && foundB) {
        Node* parent = findLowestParent(pathA, pathB);
        return distToParent(parent, pathA) + distToParent(parent, pathB);
    }
    else {
        return 0;
    }
}

