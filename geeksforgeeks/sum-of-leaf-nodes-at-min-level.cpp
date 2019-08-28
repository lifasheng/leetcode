/*
Given a Binary Tree of size N, your task is to complete the function minLeafSum(), that should return the sum of all the leaf nodes that are at minimum level of the given binary tree.
Example:

Input : 
         1
        /  \
       2    3
     /  \     \
    4    5     8 
  /  \ 
 7    2      
Output :
sum = 5 + 8 = 13

*/

// https://practice.geeksforgeeks.org/problems/sum-of-leaf-nodes-at-min-level/1


/*
Structure of the Node of the tree is 
struct Node
{
    int data;
    struct Node* left;
    struct Node* right;
};*/
// Your task is to complete this function
// function shoudl return the required sum
int minLeafSum(Node* root)
{
    if (root == NULL) return 0;
    
    queue<Node*> q;
    q.push(root);
    q.push(NULL); // mark of end of this level
    
    bool isLeafFound = false;
    int sum = 0; // sum of leaf node at minimum level
    while (!q.empty()) {
        Node *p = q.front();
        q.pop();
        
        if (p != NULL) {
            if (p->left == NULL && p->right == NULL) {
                isLeafFound = true;
                sum += p->data;
            } else {
                if (p->left != NULL) {
                    q.push(p->left);
                }
                
                if (p->right != NULL) {
                    q.push(p->right);
                }
            }
        } else {
            if (isLeafFound) { // we find the minimum leaf level
                break;
            } else {
                if (!q.empty()) {
                    q.push(NULL);
                    sum = 0; // reset sum
                }
            }
        }
    }
    return sum;
}

