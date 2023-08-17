/*
Given the head of a linked list, remove the nth node from the end of the list and return its head.

Example 1:
Input: head = [1,2,3,4,5], n = 2
Output: [1,2,3,5]

Example 2:
Input: head = [1], n = 1
Output: []

Example 3:
Input: head = [1,2], n = 1
Output: [1]

Constraints:
The number of nodes in the list is sz.
1 <= sz <= 30
0 <= Node.val <= 100
1 <= n <= sz

Follow up: Could you do this in one pass?
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
    public ListNode removeNthFromEnd(ListNode head, int n) {
        return solution2(head, n);
    }

    // 先计算长度，再删除， 遍历两遍
    private ListNode solution1(ListNode head, int n) {
        if (head == null) {
            return head;
        }

        int len = 0;
        ListNode p = head;
        while (p != null) {
            ++ len;
            p = p.next;
        }

        ListNode dummy = new ListNode(-1, head);
        p = dummy;
        for (int i = 0; i < len - n; ++i) {
            p = p.next;
        }
        p.next = p.next.next;
        return dummy.next;
    }


    // 双指针方法
    private ListNode solution2(ListNode head, int n) {
        if (head == null) {
            return head;
        }

        ListNode dummy = new ListNode(-1, head);
        ListNode fast = dummy;
        ListNode slow = dummy;

        // 先往前走n+1步
        for (int i = 0; i <= n; ++i) {
            fast = fast.next;
        }

        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        slow.next = slow.next.next;

        return dummy.next;
    }
}

