/*
Convert a Binary Search Tree to a sorted Circular Doubly-Linked List in place.

You can think of the left and right pointers as synonymous to the predecessor and successor pointers in a doubly-linked list. For a circular doubly linked list, the predecessor of the first element is the last element, and the successor of the last element is the first element.

We want to do the transformation in place. After the transformation, the left pointer of the tree node should point to its predecessor, and the right pointer should point to its successor. You should return the pointer to the smallest element of the linked list.

Example 1:
Input: root = [4,2,5,1,3]
Output: [1,2,3,4,5]

Example 2:
Input: root = [2,1,3]
Output: [1,2,3]
*/


/*
// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val,Node _left,Node _right) {
        val = _val;
        left = _left;
        right = _right;
    }
};
*/

class Solution {
    class Pair {
        Node head;
        Node tail;
        public Pair(Node head, Node tail) {
            this.head = head;
            this.tail = tail;
        }
    }
    public Node treeToDoublyList(Node root) {
        if (root == null) return root;
        if (root.left == null && root.right == null) {
            root.left = root;
            root.right = root;
            return root;
        }
        Pair pair = convertTreeToList(root);
        pair.head.left = pair.tail;
        pair.tail.right = pair.head;
        return pair.head;
    }
    
    // convert BST to Doubly-Linked List without Circular 
    private Pair convertTreeToList(Node root) {
        if (root.left == null && root.right == null) {
            return new Pair(root, root);
        }
        if (root.left == null) {
            Pair rightPair = convertTreeToList(root.right);
            root.right = rightPair.head;
            rightPair.head.left = root;
            return new Pair(root, rightPair.tail);
        } else if (root.right == null) {
            Pair leftPair = convertTreeToList(root.left);
            leftPair.tail.right = root;
            root.left = leftPair.tail;
            return new Pair(leftPair.head, root);
        } else {
            Pair leftPair = convertTreeToList(root.left);
            Pair rightPair = convertTreeToList(root.right);
            leftPair.tail.right = root;
            root.left = leftPair.tail;
            root.right = rightPair.head;
            rightPair.head.left = root;
            return new Pair(leftPair.head, rightPair.tail);
        }
    }
}
