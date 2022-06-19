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
    // 迭代，两两反转
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
    
    // 头插法，迭代反转
    /*
    以包含四个元素、带有头结点的链表为例:
    head->n1->n2->n3->n4->null
    保持头结点不变，头插法实现链表反转步骤为：
    1： 将n2插在11前面，该链表变为：
    head->n2->n1->n3->n4；
    2： 将n3插在n2前面，该链表变为：
    head->n3->n2->n1->n4；
    3： 将a4插在a3前面，该链表变为：
    head->n4->n3->n2->n1；
    可以发现，此时已经完成了链表的反转。
    */
    public ListNode reverseList4(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode dummyHead = new ListNode(-1, head);
        ListNode prev = dummyHead.next;
        ListNode cur = prev.next;

        // prev 指向的是第一个节点，并且它不会变，一直指向该节点。
        // 将cur节点插入到头部去，即插入到dummyHead之后。
        while(cur != null) {
            prev.next = cur.next;
            cur.next = dummyHead.next;
            dummyHead.next = cur;
            cur = prev.next;
        }

        return dummyHead.next;
    }

    public ListNode reverseList(ListNode head) {
        return reverseList4(head);
    }
}

