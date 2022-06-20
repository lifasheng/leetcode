/*
You are given a doubly linked list, which contains nodes that have a next pointer, a previous pointer, and an additional child pointer. This child pointer may or may not point to a separate doubly linked list, also containing these special nodes. These child lists may have one or more children of their own, and so on, to produce a multilevel data structure as shown in the example below.

Given the head of the first level of the list, flatten the list so that all the nodes appear in a single-level, doubly linked list. Let curr be a node with a child list. The nodes in the child list should appear after curr and before curr.next in the flattened list.

Return the head of the flattened list. The nodes in the list must have all of their child pointers set to null.

 

Example 1:
Input: head = [1,2,3,4,5,6,null,null,null,7,8,9,10,null,null,11,12]
Output: [1,2,3,7,8,11,12,9,10,4,5,6]
Explanation: The multilevel linked list in the input is shown.
After flattening the multilevel linked list it becomes:

Example 2:
Input: head = [1,2,null,3]
Output: [1,3,2]
Explanation: The multilevel linked list in the input is shown.
After flattening the multilevel linked list it becomes:

Example 3:
Input: head = []
Output: []
Explanation: There could be empty list in the input.
*/


/*
// Definition for a Node.
class Node {
    public int val;
    public Node prev;
    public Node next;
    public Node child;
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
    public Node flatten(Node head) {
        return flattenIteratively(head);
    }
    
    // Solution 1: find tail by loop the flattened child
    private Node flattenRecursivelyNoOptimization(Node head) {
        Node p = head;
        while (p != null) {
            if (p.child != null) {
                Node flattenedChild = flattenRecursivelyNoOptimization(p.child);
                Node tail = flattenedChild;
                while (tail.next != null) {
                    tail = tail.next;
                }
                
                tail.next = p.next;
                if (p.next != null) {
                    p.next.prev = tail;
                }
                
                p.next = flattenedChild;
                flattenedChild.prev = p;
                p.child = null;
            }
            
            p = p.next;
        }
        
        return head;
    }
    
    // Solution 2: return tail in recursive method, O(N)
    private Node flattenRecursivelyWithOptimization(Node head) {
        Pair pair = flattenRecursively(head);
        return pair.head;
    }
    
    private Pair flattenRecursively(Node head) {        
        Node p = head;
        Node tail = p;
        while (p != null) {
            if (p.child != null) {
                Pair pair = flattenRecursively(p.child);
                pair.tail.next = p.next;
                if (p.next != null) {
                    p.next.prev = pair.tail;
                }

                p.next = pair.head;
                pair.head.prev = p;
                p.child = null;
            }
            
            tail = p;
            p = p.next;
        }
        
        return new Pair(head, tail);
    }
    
    // Solution 3: use stack, like preorder traverse binary tree,  O(N)
    private Node flattenIteratively(Node head) {
        if (head == null) return head;
        
        Node dummy = new Node(0, null, head, null);
        Node prev = dummy;
        
        Stack<Node> stack = new Stack<>();
        stack.push(head);
        while(!stack.isEmpty()) {
            Node curr = stack.pop();
            
            curr.prev = prev;
            prev.next = curr;
            prev = curr;
            
            if (curr.next != null) {
                stack.push(curr.next);
            }
            
            if (curr.child != null) {
                stack.push(curr.child);
                // don't forget to remove all child pointers.
                curr.child = null;
            }
        }
        
        head.prev = null;
        return dummy.next;
    }
}


