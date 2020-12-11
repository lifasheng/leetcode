/*
83. Remove Duplicates from Sorted List
Easy

2032

135

Add to List

Share
Given a sorted linked list, delete all duplicates such that each element appear only once.

Example 1:

Input: 1->1->2
Output: 1->2
Example 2:

Input: 1->1->2->3->3
Output: 1->2->3
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
    // iterative way
    public ListNode deleteDuplicates1(ListNode head) {
        if (head == null || head.next == null) return head;
        
        ListNode prev = head;
        ListNode curr = prev.next;
        
        while(curr != null) {
            if (curr.val != prev.val) {
                prev.next = curr;
                prev = curr;
            }
            curr = curr.next;
        }
        prev.next = null;
        return head;
    }
    
    private ListNode deleteDuplicateRecursively(int prevVal, ListNode curr) {
        if (curr == null) return null;
        if (curr.val == prevVal) {
            return deleteDuplicateRecursively(prevVal, curr.next);
        } else {
            curr.next = deleteDuplicateRecursively(curr.val, curr.next);
            return curr;
        }
    }
    
    public ListNode deleteDuplicates2(ListNode head) {
        if (head == null || head.next == null) return head;
        
        head.next = deleteDuplicateRecursively(head.val, head.next);
        return head;
    }
    
    public ListNode deleteDuplicates(ListNode head) {
        return deleteDuplicates2(head);
    }
}

