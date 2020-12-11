/*
206. Reverse Linked List
Easy
Reverse a singly linked list.

Example:

Input: 1->2->3->4->5->NULL
Output: 5->4->3->2->1->NULL
Follow up:

A linked list can be reversed either iteratively or recursively. Could you implement both?
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
    public ListNode reverseList1(ListNode head) {
        ListNode head2 = null;
        while(head != null) {
            ListNode next = head.next;
            head.next = head2;
            head2= head;
            head = next;
        }
        return head2;
    }
    
    // 常规的递归思路，关键点在于反转后的尾节点处理
    public ListNode reverseList2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode p = reverseList2(head.next);
        head.next.next = head;
        head.next = null;
        return p;
    }
    
    private ListNode reverseRecursively(ListNode prev, ListNode cur) {
        if (cur == null) return prev;
        ListNode next = cur.next;
        cur.next = prev;
        return reverseRecursively(cur, next);
    }
    
    // 类似于迭代，两两反转
    public ListNode reverseList3(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode prev = head;
        ListNode cur = head.next;
        head.next = null;
        return reverseRecursively(prev, cur);
    }
    
    public ListNode reverseList(ListNode head) {
        return reverseList3(head);
    }
}


