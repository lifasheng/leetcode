/*
Given the root of a binary tree and a leaf node, reroot the tree so that the leaf is the new root.

You can reroot the tree with the following steps for each node cur on the path starting from the leaf up to the root​​​ excluding the root:

If cur has a left child, then that child becomes cur's right child.
cur's original parent becomes cur's left child. Note that in this process the original parent's pointer to cur becomes null, making it have at most one child.
Return the new root of the rerooted tree.

Note: Ensure that your solution sets the Node.parent pointers correctly after rerooting or you will receive "Wrong Answer".

Example 1:
Input: root = [3,5,1,6,2,0,8,null,null,7,4], leaf = 7
Output: [7,2,null,5,4,3,6,null,null,null,1,null,null,0,8]

Example 2:
Input: root = [3,5,1,6,2,0,8,null,null,7,4], leaf = 0
Output: [0,1,null,3,8,5,null,null,null,6,2,null,null,7,4]

Constraints:
The number of nodes in the tree is in the range [2, 100].
-109 <= Node.val <= 109
All Node.val are unique.
leaf exist in the tree.
*/

/*
// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;
    public Node parent;
};
*/

class Solution {
    public Node flipBinaryTree(Node root, Node leaf) {
        return flipBinaryTree_recursive(root, leaf);
    }
    
    private Node flipBinaryTree_iterative(Node root, Node leaf) {
        List<Node> path = new ArrayList<>();
        
        Node cur = leaf;
        while (cur != root) {
            path.add(cur);
            Node parent = cur.parent;
            if (parent.left == cur) {
                parent.left = null;
            } else {
                parent.right = null;
            }
            
            if (cur.left != null) {
                cur.right = cur.left;
            }
            cur.left = parent;
            cur.parent = null;
            cur = parent;
        }
        path.add(root);
        
        // update parent pointer
        for (int i = 1; i < path.size(); ++i) {
            path.get(i).parent = path.get(i-1);
        }
        return leaf;
    }
    
    private Node flipBinaryTree_recursive(Node root, Node leaf) {
        if (root == leaf) return leaf;
        
        Node parent = leaf.parent;
        // disconnect with parent
        if (parent.left == leaf) {
            parent.left = null;
        } else {
            parent.right = null;
        }
        
        if (leaf.left != null) {
            leaf.right = leaf.left;
        }
        // recursive for parent
        Node parentRoot = flipBinaryTree_recursive(root, parent);
        
        leaf.left = parentRoot;
        leaf.parent = null; // important
        parentRoot.parent = leaf; // important
        
        return leaf;
    }
}

