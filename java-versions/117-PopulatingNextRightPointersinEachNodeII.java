/*
Given a binary tree

struct Node {
  int val;
  Node *left;
  Node *right;
  Node *next;
}
Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set to NULL.

Initially, all next pointers are set to NULL.

Example 1:
Input: root = [1,2,3,4,5,null,7]
Output: [1,#,2,3,#,4,5,7,#]
Explanation: Given the above binary tree (Figure A), your function should populate each next pointer to point to its next right node, just like in Figure B. The serialized output is in level order as connected by the next pointers, with '#' signifying the end of each level.

Example 2:
Input: root = []
Output: []
 
Constraints:
The number of nodes in the tree is in the range [0, 6000].
-100 <= Node.val <= 100
 
Follow-up:
You may only use constant extra space.
The recursive approach is fine. You may assume implicit stack space does not count as extra space for this problem.
*/

/*
// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {}
    
    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
};
*/

class Solution {
    // very good!
    public Node connect(Node root) {
        return connectBFS_NoQueue(root);
    }
    
    //O(N) space
    private Node connectBFS(Node root) {
        if (root == null) return null;
        
        Deque<Node> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            Node prev = null;
            for (int i = 0; i < size; ++i) {
                Node node = queue.poll();
                if (prev != null) {
                    prev.next = node;
                }
                prev = node;
                
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        return root;
    }
    
    // O(1) space, using leftMost node and above level's list
    private Node connectBFS_NoQueue(Node root) {
        if (root == null) return null;
        
        Node headCurrentLevel = root;
        while (headCurrentLevel != null) {
            Node pCurrentLevel = headCurrentLevel;
            Node headNextLevel = new Node(-1);
            Node pNextLevel = headNextLevel;
            while (pCurrentLevel != null) {
                if (pCurrentLevel.left != null) {
                    pNextLevel.next = pCurrentLevel.left;
                    pNextLevel = pCurrentLevel.left;
                }
                if (pCurrentLevel.right != null) {
                    pNextLevel.next = pCurrentLevel.right;
                    pNextLevel = pCurrentLevel.right;
                }
                
                pCurrentLevel = pCurrentLevel.next;
            }
            headCurrentLevel = headNextLevel.next;
        }
        return root;
    }

}

