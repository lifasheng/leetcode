/*
138. Copy List with Random Pointer
Medium
A linked list is given such that each node contains an additional random pointer which could point to any node in the list or null.

Return a deep copy of the list.

The Linked List is represented in the input/output as a list of n nodes. Each node is represented as a pair of [val, random_index] where:

val: an integer representing Node.val
random_index: the index of the node (range from 0 to n-1) where random pointer points to, or null if it does not point to any node.
 

Example 1:
Input: head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
Output: [[7,null],[13,0],[11,4],[10,2],[1,0]]

Example 2:
Input: head = [[1,1],[2,1]]
Output: [[1,1],[2,1]]

Example 3:
Input: head = [[3,null],[3,0],[3,null]]
Output: [[3,null],[3,0],[3,null]]

Example 4:
Input: head = []
Output: []

Explanation: Given linked list is empty (null pointer), so return null.
 
Constraints:

-10000 <= Node.val <= 10000
Node.random is null or pointing to a node in the linked list.
The number of nodes will not exceed 1000.
*/

/*
// Definition for a Node.
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
*/

class Solution {
    
    // 用HashMap辅助，用于连接random指针。 Time: O(N), Space: O(N)
    // 将链表还是当成链表，先不管random指针，按照next关系，把链表clone，并用map记录新旧对应关系
    public Node copyRandomList1(Node head) {
        Map<Node, Node> m = new HashMap<>();
        Node p = head;
        Node dummyHead = new Node(-1);
        Node q = dummyHead;
        while(p != null) {
            q.next = new Node(p.val);
            q = q.next;
            m.put(p, q);
            p = p.next;
        }
        
        for(Map.Entry<Node, Node> entry : m.entrySet()) {
            Node k = entry.getKey();
            Node v = entry.getValue();
            
            v.random = m.get(k.random);
        }
        return dummyHead.next;
    }
    
    private Node copyRandomListRecursively(Node head, Map<Node, Node> m) {
        if (head == null) return head;
        if (m.containsKey(head)) return m.get(head);
        
        Node p = new Node(head.val);
        m.put(head, p); // 这行代码不能放在下面两行之后，考虑自己指向自己的情况。
        p.next = copyRandomListRecursively(head.next, m);
        p.random = copyRandomListRecursively(head.random, m);
        return p;
    }
    
    // 递归 + 备忘录, Time: O(N), Space: O(N)
    // 将链表看成一个graph，使用clone graph的方法来clone
    public Node copyRandomList2(Node head) {
        return copyRandomListRecursively(head, new HashMap<>());
    }
    
    // 迭代， Time: O(N), Space: O(1)
    // 先利用next指针插入clone的节点，再更新random指针，最后split新旧两个链表。
    public Node copyRandomList3(Node head) {
        if (head == null) return head;
        
        // copy 节点，插入到自己的后面，先不管random指针，这一步只关注next指针
        Node p = head;
        Node q;
        while(p != null) {
            q = new Node(p.val);
            q.next = p.next;
            p.next = q;
            p = q.next;
        }
        
        // 为每个copy的节点设置random指针
        p = head;
        q = p.next;
        while(q != null) {
            if (p.random != null) { // 注意 random可以为null
                q.random = p.random.next;
            }
            if (q.next != null) {
                p = q.next;
                q = p.next;
            } else {
                break; 
            }
        }
        
        // 断开新旧节点，主要处理next指针
        Node newHead = head.next;
        p = head;
        q = p.next;
        while(q != null) {
            if (q.next != null) {
                p.next = p.next.next;
                q.next = q.next.next;
                p = p.next;
                q = q.next;
            } else {
                p.next = null;
                break;
            }
        }
        
        return newHead;
        
    }
    
    public Node copyRandomList(Node head) {
        return copyRandomList3(head);
    }
}



