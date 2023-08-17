/*
Given the head of a singly linked list, reverse the list, and return the reversed list.

Example 1:
Input: head = [1,2,3,4,5]
Output: [5,4,3,2,1]

Example 2:
Input: head = [1,2]
Output: [2,1]

Example 3:
Input: head = []
Output: []
 
Constraints:
The number of nodes in the list is the range [0, 5000].
-5000 <= Node.val <= 5000
 
Follow up: A linked list can be reversed either iteratively or recursively. Could you implement both?
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
    public ListNode reverseList(ListNode head) {
        return solution3(head);
    }

    private ListNode solution1(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode p = null;
        ListNode q = head;

        while (q != null) {
            ListNode next = q.next;
            q.next = p;
            p = q;
            q = next;
        }
        return p;
    }

    private ListNode recursive(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode p = recursive(head.next);
        head.next.next = head;
        head.next = null;
        return p;
    }

    // 头插法
    /*
    以包含四个元素、带有dummy头结点的链表为例:
    dummy->n1->n2->n3->n4->null
    保持头结点不变，头插法实现链表反转步骤为：
    1： 将n2插在11前面，该链表变为：
    dummy->n2->n1->n3->n4；
    2： 将n3插在n2前面，该链表变为：
    dummy->n3->n2->n1->n4；
    3： 将a4插在a3前面，该链表变为：
    dummy->n4->n3->n2->n1；
    可以发现，此时已经完成了链表的反转。
    */

    // prev 永远指向原来链表中的第一个节点，它的next就是下一个要反转的节点。
    private ListNode solution3(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode dummy = new ListNode(-1, head);
        ListNode prev = head;
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



