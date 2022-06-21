/*
Given the head of a singly linked list that is sorted in non-decreasing order using the absolute values of its nodes, return the list sorted in non-decreasing order using the actual values of its nodes.

Example 1:
Input: head = [0,2,-5,5,10,-10]
Output: [-10,-5,0,2,5,10]
Explanation:
The list sorted in non-descending order using the absolute values of the nodes is [0,2,-5,5,10,-10].
The list sorted in non-descending order using the actual values is [-10,-5,0,2,5,10].

Example 2:

Input: head = [0,1,2]
Output: [0,1,2]
Explanation:
The linked list is already sorted in non-decreasing order.
Example 3:

Input: head = [1]
Output: [1]
Explanation:
The linked list is already sorted in non-decreasing order.
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
    public ListNode sortLinkedList(ListNode head) {
        if (head == null || head.next == null) return head;
        
        ListNode head2 = null;
        ListNode tail2 = null;
        ListNode curr = head;
        ListNode prev = null;
        while (curr != null) {
            if (curr.val < 0) {
                ListNode next = curr.next;
                // bypass curr node in original list, 
                // however, we don't need to update prev pointer
                if (prev != null) {
                    prev.next = next;
                }
                
                // link current negative node to new list, also update tail
                curr.next = head2;
                head2 = curr;
                if (tail2 == null) {
                    tail2 = curr;
                }
                
                // if original's head < 0, should update it to next node
                if (head == curr) {
                    head = next;
                }
                
                // move to next node in original list
                curr = next;
            } else {
                prev = curr;
                curr = curr.next;
            }
        }
        if (tail2 == null) return head;
        
        tail2.next = head;
        return head2;
        
    }
}



