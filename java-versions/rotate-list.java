/*
61. Rotate List
Medium
Given the head of a linked list, rotate the list to the right by k places.
Example 1:

Input: head = [1,2,3,4,5], k = 2
Output: [4,5,1,2,3]
Example 2:


Input: head = [0,1,2], k = 4
Output: [2,0,1]
 

Constraints:

The number of nodes in the list is in the range [0, 500].
-100 <= Node.val <= 100
0 <= k <= 2 * 109

*/

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    
    private int getLength(ListNode head) {
        int len = 0;
        while(head != null) {
            ++len;
            head = head.next;
        }
        return len;
    }
    
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null) return head;
        
        int len = getLength(head);
        k %= len;
        if (k==0) return head;
        
        // 让p1先走K步，然后p1和p2同时走，p1指向尾节点，p2指向要rotate节点的前一个节点，
        // 先首尾相连，再在p2处断开就可以
        ListNode p1 = head;
        ListNode p2 = head;
        for(int i=1; i<=k; ++i) {
            p1 = p1.next;
        }
        while(p1.next != null) {
            p1 = p1.next;
            p2 = p2.next;
        }
        
        ListNode newHead = p2.next;
        p1.next = head;
        p2.next = null;
            
        return newHead;
    }
}



