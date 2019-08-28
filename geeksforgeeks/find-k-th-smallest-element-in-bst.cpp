/*

Given root of binary search tree and K as input, find K-th smallest element in BST. Your task is to return the K-th smallest element in BST from the function k_smallest_element().

Note: The Time Complexity will be O(h) where h is the height of the Tree.

*/

// https://practice.geeksforgeeks.org/problems/find-k-th-smallest-element-in-bst/1


/*This is a function problem.You only need to complete the function given below*/
/*Complete the function below
Node is as follows:
struct Node
{
    int data;
    int lCount;
 
    Node* left;
    Node* right;
};*/
int KthSmallestElement(Node *root, int k)
{
    if (root == NULL || k <= 0) return -1;
    
    if (k == root->lCount + 1) return root->data;
    if (k <= root->lCount) return KthSmallestElement(root->left, k);
    return KthSmallestElement(root->right, k-1-root->lCount);
}
