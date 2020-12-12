/*
24. Swap Nodes in Pairs
Medium
Given a linked list, swap every two adjacent nodes and return its head.

You may not modify the values in the list's nodes. Only nodes itself may be changed.

Example 1:

Input: head = [1,2,3,4]
Output: [2,1,4,3]
Example 2:

Input: head = []
Output: []
Example 3:

Input: head = [1]
Output: [1]

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
思路：
用prev指针指向cur的前一个结点，cur和next指向当前结点和下一个结点。
每次循环，可以翻转cur和next，然后更新prev指针和cur指针
O(n) time, O(1) space
Test case: 1->2,  1->2->3, 1->2->3->4, 1->2->3->4->5
*/
class Solution {
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) return head;
        
        ListNode dummyHead = new ListNode(-1, head);
        ListNode prev = dummyHead;
        ListNode curr = head;
        ListNode next = null;
        while(curr != null && curr.next != null) { // 保证有两个节点
            next = curr.next;
            
            curr.next = next.next;
            next.next = curr;
            prev.next = next;
            prev = curr;
            curr = curr.next;
        }
        return dummyHead.next;
    }
}


