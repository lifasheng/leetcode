/*
Given the heads of two singly linked-lists headA and headB, return the node at which the two lists intersect. If the two linked lists have no intersection at all, return null.

For example, the following two linked lists begin to intersect at node c1:


The test cases are generated such that there are no cycles anywhere in the entire linked structure.

Note that the linked lists must retain their original structure after the function returns.
*/

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;
        
        
        ListNode pA = headA;
        ListNode pB = headB;
        int lenA = listLength(headA);
        int lenB = listLength(headB);
        if (lenA > lenB) {
            for (int i = 0; i < lenA - lenB; ++i) {
                pA = pA.next;
            }
        } else {
            for (int i = 0; i < lenB - lenA; ++i) {
                pB = pB.next;
            }
        }
        
        while (pA != null && pB != null) {
            if (pA == pB) {
                return pA;
            }
            
            pA = pA.next;
            pB = pB.next;
        }
        return null;
    }
    
    private int listLength(ListNode head) {
        int len = 0;
        ListNode p = head;
        while (p != null) {
            ++ len;
            p = p.next;
        }
        return len;
    }
}




