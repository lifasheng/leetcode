/*
143. Reorder List
Medium

2671

133

Add to List

Share
Given a singly linked list L: L0→L1→…→Ln-1→Ln,
reorder it to: L0→Ln→L1→Ln-1→L2→Ln-2→…

You may not modify the values in the list's nodes, only nodes itself may be changed.

Example 1:

Given 1->2->3->4, reorder it to 1->4->2->3.
Example 2:

Given 1->2->3->4->5, reorder it to 1->5->2->4->3.
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
    private ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) return head;
        
        ListNode head2 = null;
        ListNode next;
        while(head != null) {
            next = head.next;
            head.next = head2;
            head2 = head;
            head = next;
        }
        return head2;
    }
    
    private ListNode findMiddleNode(ListNode head) {
        ListNode dummyHead = new ListNode(-1, head);
        ListNode fast = dummyHead;
        ListNode slow = dummyHead;
        while(fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }
    
    private ListNode mergeLists(ListNode left, ListNode right) {
        ListNode dummyHead = new ListNode(-1);
        ListNode t = dummyHead;
        while(right != null) {
            ListNode leftNext = left.next;
            ListNode rightNext = right.next;
            t.next = left;
            t.next.next = right;
            t = right;
            left = leftNext;
            right = rightNext;
        }
        if (left != null) {
            t.next = left;
        }
        return dummyHead.next;
    }
    
    public void reorderList(ListNode head) {
        if (head == null || head.next == null) return;
        
        ListNode middleNode = findMiddleNode(head);
        ListNode right = reverseList(middleNode.next);
        middleNode.next = null; // 断开左右两个链表
        
        head = mergeLists(head, right);
    }
}

