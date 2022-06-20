/*
Given the head of a singly linked list and an integer k, split the linked list into k consecutive linked list parts.

The length of each part should be as equal as possible: no two parts should have a size differing by more than one. This may lead to some parts being null.

The parts should be in the order of occurrence in the input list, and parts occurring earlier should always have a size greater than or equal to parts occurring later.

Return an array of the k parts.

 

Example 1:
Input: head = [1,2,3], k = 5
Output: [[1],[2],[3],[],[]]
Explanation:
The first element output[0] has output[0].val = 1, output[0].next = null.
The last element output[4] is null, but its string representation as a ListNode is [].

Example 2:
Input: head = [1,2,3,4,5,6,7,8,9,10], k = 3
Output: [[1,2,3,4],[5,6,7],[8,9,10]]
Explanation:
The input has been split into consecutive parts with size difference at most 1, and earlier parts are a larger size than the later parts.
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
    private int listLength(ListNode head) {
        int len = 0;
        for (ListNode p = head; p != null; p = p.next) {
            ++len;
        }
        return len;
    }
    
    public ListNode[] splitListToParts(ListNode head, int k) {        
        ListNode[] result = new ListNode[k];
        int len = listLength(head);
        if (len <= k) {
            for (int i = 0; i < k; ++i) {
                head = splitList(head, 1, result, i);
            }
        } else {
            int n = len / k;
            int m = len % k;
            for (int i = 0; i < m; ++i) {
                head = splitList(head, n + 1, result, i);
            }
            for (int i = m; i < k; ++i) {
                head = splitList(head, n, result, i);
            }
        }
        
        return result;
    }
    
    // split len nodes from list starting with head, assign it to result[index],
    // and return new head, which is head + len if there is still head.
    private ListNode splitList(ListNode head, int len, ListNode[] result, int index) {
        if (head == null) {
            result[index] = null;
            return null;
        }
        
        ListNode p = head;
        ListNode prev = p;
        int n = 0;
        while(p != null && n < len) {
            prev = p;
            p = p.next;
            ++n;
        }
        
        if (n < len) {
            result[index] = head;
            return null;
        } else {
            result[index] = head;
            prev.next = null;
            return p;
        }
    }
}




