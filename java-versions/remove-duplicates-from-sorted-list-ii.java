/*
82. Remove Duplicates from Sorted List II
Medium
Given a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the original list.

Return the linked list sorted as well.

Example 1:

Input: 1->2->3->3->4->4->5
Output: 1->2->5
Example 2:

Input: 1->1->1->2->3
Output: 2->3


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
    /* 迭代版
    判断当前节点和前驱节点是否相等，
    如果不等，prev可以加入新的链表；
    如果相等，则curr需要跳过所有和prev相同值的节点，
        如果curr到达末尾，则prev设为null，这样后面p.next = prev可以正确
        如果curr未到末尾，则prev设为curr，curr往后，继续外层的逻辑。
    curr==null，循环结束，此时需要设置新链表的尾部为prev。
    
    跑几个例子就清楚了。
    */
    public ListNode deleteDuplicates1(ListNode head) {
        if (head == null || head.next == null) return head;
        
        ListNode dummyHead = new ListNode(-1);
        ListNode p = dummyHead;
        ListNode prev = head;
        ListNode curr = prev.next;
        
        while(curr != null) {
            if (curr.val != prev.val) {
                p.next = prev;
                p = prev;
                prev = curr;
                curr = curr.next;
            } else {
                while(curr != null && curr.val == prev.val) {
                    curr = curr.next;
                }
                if (curr != null) {
                    prev = curr;
                    curr = curr.next;
                } else {
                    prev = null;
                }
            }
        }
        
        p.next = prev;
        
        return dummyHead.next;
    }
    
    // 递归版
    // 如果首节点和下一个节点值不同，则直接递归，很简单
    // 如果他们相同，则循环遍历，直到找到一个不同的值，再递归
    // 注意思路上和remove-duplicates-from-sorted-list的区别！
    public ListNode deleteDuplicates2(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode next = head.next;
        if (head.val == next.val) {
            while(next != null && head.val == next.val) {
                next = next.next;
            }
            // when it comes here, next is either null or a new value.
            // test using [1,1,1] or [1,1,2] or [2, 1, 1]
            return deleteDuplicates2(next);
            
        } else {
            head.next = deleteDuplicates2(next);
            return head;
        }
    }
    
    public ListNode deleteDuplicates(ListNode head) {
        return deleteDuplicates2(head);
    }
}

