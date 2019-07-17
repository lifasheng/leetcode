/*

Print all nodes at distance k from a given node
Given a binary tree, a target node in the binary tree, and an integer value k, print all the nodes that are at distance k from the given target node. No parent pointers are available.
BinaryTree

Consider the tree shown in diagram

Input: target = pointer to node with data 8.
root = pointer to node with data 20.
k = 2.
Output : 10 14 22

If target is 14 and k is 3, then output
should be “4 20”

Recommended: Please solve it on “PRACTICE” first, before moving on to the solution.
There are two types of nodes to be considered.
1) Nodes in the subtree rooted with target node. For example if the target node is 8 and k is 2, then such nodes are 10 and 14.
2) Other nodes, may be an ancestor of target, or a node in some other subtree. For target node 8 and k is 2, the node 22 comes in this category.

Finding the first type of nodes is easy to implement. Just traverse subtrees rooted with the target node and decrement k in recursive call. When the k becomes 0, print the node currently being traversed (See this for more details). Here we call the function as printkdistanceNodeDown().

How to find nodes of second type? For the output nodes not lying in the subtree with the target node as the root, we must go through all ancestors. For every ancestor, we find its distance from target node, let the distance be d, now we go to other subtree (if target was found in left subtree, then we go to right subtree and vice versa) of the ancestor and find all nodes at k-d distance from the ancestor.


https://www.geeksforgeeks.org/print-nodes-distance-k-given-node-binary-tree/
https://practice.geeksforgeeks.org/problems/nodes-at-given-distance-in-binary-tree/1
*/

/////////////////////////////////////////////////////////////////////////////////////////////
/*
idea:
1. find the target node in tree.
2. print the nodes that is k distance in subtree of target node.
3. travsel back from target node to root, print the nodes that is k-i distance in subtree that does not include the path.
*/

void printkdistanceNodeDown(node *root, int k)
{
    if (root == NULL) return;
    if (k == 0) {
        cout << root->data << " ";
    } else {
        printkdistanceNodeDown(root->left, k-1);
        printkdistanceNodeDown(root->right, k-1);
    }
}

bool findNode(node* root, node* target, vector<node*> &parents) {
    if (root == NULL || target == NULL) return false;
    parents.push_back(root);
    if (root->data == target->data) {
        return true;
    }
    
    bool inLeft = findNode(root->left, target, parents);
    if (inLeft) {
        return true;
    }

    bool inRight = findNode(root->right, target, parents);
    if (inRight) {
        return true;
    }
    
    parents.pop_back();
    return false;
}

void printkdistanceNodeDownExcludeItself(node* root , int k, node* self) {
    if (root == NULL) return;
    if (k == 0) {
        cout << root->data << " ";
    } else {
        if (root->left != self) printkdistanceNodeDownExcludeItself(root->left, k-1, self);
        if (root->right != self) printkdistanceNodeDownExcludeItself(root->right, k-1, self);
    }
}

/* Prints all nodes at distance k from a given target node.
 The k distant nodes may be upward or downward.  This function
 Returns distance of root from target node, it returns -1 if target
 node is not present in tree rooted with root. */
int printkdistanceNode(node* root, node* target , int k)
{
    if (root == NULL || target == NULL) return -1;
    vector<node*> parents;
    bool inTree = findNode(root, target, parents);
    if (!inTree) return -1;
    
    printkdistanceNodeDown(target, k);
    
    reverse(parents.begin(), parents.end());
    for(int i=1; i<parents.size(); ++i) {
        printkdistanceNodeDownExcludeItself(parents[i], k-i, parents[i-1]);
    }
    return parents.size()-1;
}


/////////////////////////////////////////////////////////////////////////////////////////////

#include <iostream> 
using namespace std; 

// A binary Tree node 
struct node 
{ 
	int data; 
	struct node *left, *right; 
}; 

/* Recursive function to print all the nodes at distance k in the 
tree (or subtree) rooted with given root. See */
void printkdistanceNodeDown(node *root, int k) 
{ 
	// Base Case 
	if (root == NULL || k < 0) return; 

	// If we reach a k distant node, print it 
	if (k==0) 
	{ 
		cout << root->data << endl; 
		return; 
	} 

	// Recur for left and right subtrees 
	printkdistanceNodeDown(root->left, k-1); 
	printkdistanceNodeDown(root->right, k-1); 
} 

// Prints all nodes at distance k from a given target node. 
// The k distant nodes may be upward or downward. This function 
// Returns distance of root from target node, it returns -1 if target 
// node is not present in tree rooted with root. 
int printkdistanceNode(node* root, node* target , int k) 
{ 
	// Base Case 1: If tree is empty, return -1 
	if (root == NULL) return -1; 

	// If target is same as root. Use the downward function 
	// to print all nodes at distance k in subtree rooted with 
	// target or root 
	if (root == target) 
	{ 
		printkdistanceNodeDown(root, k); 
		return 0; 
	} 

	// Recur for left subtree 
	int dl = printkdistanceNode(root->left, target, k); 

	// Check if target node was found in left subtree 
	if (dl != -1) 
	{ 
		// If root is at distance k from target, print root 
		// Note that dl is Distance of root's left child from target 
		if (dl + 1 == k) 
			cout << root->data << endl; 

		// Else go to right subtree and print all k-dl-2 distant nodes 
		// Note that the right child is 2 edges away from left child 
		else
			printkdistanceNodeDown(root->right, k-dl-2); 

		// Add 1 to the distance and return value for parent calls 
		return 1 + dl; 
	} 

	// MIRROR OF ABOVE CODE FOR RIGHT SUBTREE 
	// Note that we reach here only when node was not found in left subtree 
	int dr = printkdistanceNode(root->right, target, k); 
	if (dr != -1) 
	{ 
		if (dr + 1 == k) 
			cout << root->data << endl; 
		else
			printkdistanceNodeDown(root->left, k-dr-2); 
		return 1 + dr; 
	} 

	// If target was neither present in left nor in right subtree 
	return -1; 
} 

// A utility function to create a new binary tree node 
node *newnode(int data) 
{ 
	node *temp = new node; 
	temp->data = data; 
	temp->left = temp->right = NULL; 
	return temp; 
} 

// Driver program to test above functions 
int main() 
{ 
	/* Let us construct the tree shown in above diagram */
	node * root = newnode(20); 
	root->left = newnode(8); 
	root->right = newnode(22); 
	root->left->left = newnode(4); 
	root->left->right = newnode(12); 
	root->left->right->left = newnode(10); 
	root->left->right->right = newnode(14); 
	node * target = root->left->right; 
	printkdistanceNode(root, target, 2); 
	return 0; 
}




