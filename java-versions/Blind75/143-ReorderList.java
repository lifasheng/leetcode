/*
You are given the head of a singly linked-list. The list can be represented as:

L0 → L1 → … → Ln - 1 → Ln
Reorder the list to be on the following form:

L0 → Ln → L1 → Ln - 1 → L2 → Ln - 2 → …
You may not modify the values in the list's nodes. Only nodes themselves may be changed.

Example 1:
Input: head = [1,2,3,4]
Output: [1,4,2,3]

Example 2:
Input: head = [1,2,3,4,5]
Output: [1,5,2,4,3]
 
Constraints:
The number of nodes in the list is in the range [1, 5 * 104].
1 <= Node.val <= 1000
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
    public void reorderList(ListNode head) {
        solution1(head);
    }

    // 找到中间节点的前一个节点，将链表分成两部分，反转后面半部分，再合并。
    private void solution1(ListNode head) {
        if (head == null || head.next == null) {
            return;
        }

        ListNode middleNode = findMiddleNode(head);
        ListNode head2 = middleNode.next;
        middleNode.next = null; // 断开前后两部分
        ListNode reversedHead2 = reverseList(head2);

        merge2Lists(head, reversedHead2);
    }

    private ListNode merge2Lists(ListNode head, ListNode head2) {
        ListNode dummy = new ListNode();
        ListNode p = dummy;

        while (head != null && head2 != null) {
            p.next = head;
            head = head.next;
            p = p.next;

            p.next = head2;
            head2 = head2.next;
            p = p.next;
        }
        if (head != null) {
            p.next = head;
        }
        if (head2 != null) {
            p.next = head2;
        }
        return dummy.next;
    }

    private ListNode findMiddleNode(ListNode head) {
        ListNode dummy = new ListNode(-1, head);
        ListNode fast = dummy;
        ListNode slow = dummy;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        return slow;
    }

    // dummy -> 1 -> 2 -> 3 -> null
    private ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode dummy = new ListNode(-1, head);
        ListNode prev = dummy.next;
        ListNode cur = prev.next;

        while (cur != null) {
            prev.next = cur.next;
            cur.next = dummy.next;
            dummy.next = cur;
            cur = prev.next;
        }

        return dummy.next;
    }
}

