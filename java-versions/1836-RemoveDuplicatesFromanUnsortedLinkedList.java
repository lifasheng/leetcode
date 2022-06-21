/*
Given the head of a linked list, find all the values that appear more than once in the list and delete the nodes that have any of those values.

Return the linked list after the deletions.


Input: head = [1,2,3,2]
Output: [1,3]
Explanation: 2 appears twice in the linked list, so all 2's should be deleted. After deleting all 2's, we are left with [1,3].


Input: head = [2,1,1,2]
Output: []
Explanation: 2 and 1 both appear twice. All the elements should be deleted.

Input: head = [3,2,2,1,3,2,4]
Output: [1,4]
Explanation: 3 appears twice and 2 appears three times. After deleting all 3's and 2's, we are left with [1,4].
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
    public ListNode deleteDuplicatesUnsorted(ListNode head) {
        if (head == null || head.next == null) return head;
        
        // count appearance times
        Map<Integer, Integer> m = new HashMap<>();
        ListNode p = head;
        while (p != null) {
            if (!m.containsKey(p.val)) {
                m.put(p.val, 1);
            } else {
                m.put(p.val, m.get(p.val) + 1);
            }
            
            p = p.next;
        }
        
        // find first node that appear once
        ListNode curr = head;
        while (curr != null && m.get(curr.val) > 1) {
            curr = curr.next;
        }
        if (curr == null) return null;
        
        head = curr;
        ListNode prev = curr;
        while (curr != null) {
            if (m.get(curr.val) == 1) {
                prev = curr;
                curr = curr.next;
            } else {
                prev.next = curr.next;
                curr = curr.next;
            }
        }
        
        return head;
    }
}




