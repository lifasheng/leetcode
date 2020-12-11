/*
92. Reverse Linked List II
Medium
Reverse a linked list from position m to n. Do it in one-pass.

Note: 1 ≤ m ≤ n ≤ length of list.

Example:

Input: 1->2->3->4->5->NULL, m = 2, n = 4
Output: 1->4->3->2->5->NULL
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
/*
头插法的思路还是比较清晰的。
首先用dummyHead辅助找到m的前一个位置，这个位置将是头插法中的head。
prev指向head.next， cur=prev.next， 开始头插法即可。
用几个例子验证一下。
*/
class Solution {
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if (m==n || head==null || head.next==null) return head;
        
        ListNode dummyHead = new ListNode(-1, head);
        ListNode subHead = dummyHead;
        for(int i=1; i<m; ++i) {
            subHead = subHead.next;
        }
        
        // 头插法
        ListNode prev = subHead.next; // position m
        ListNode cur = prev.next;
        for(int i=m; i<n; ++i) {
            prev.next = cur.next;
            cur.next = subHead.next;
            subHead.next = cur;
            cur = prev.next;
        }
        
        return dummyHead.next;
    }
}

