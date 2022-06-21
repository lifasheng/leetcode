/*
You are given two linked lists: list1 and list2 of sizes n and m respectively.

Remove list1's nodes from the ath node to the bth node, and put list2 in their place.

The blue edges and nodes in the following figure indicate the result:

Input: list1 = [0,1,2,3,4,5], a = 3, b = 4, list2 = [1000000,1000001,1000002]
Output: [0,1,2,1000000,1000001,1000002,5]
Explanation: We remove the nodes 3 and 4 and put the entire list2 in their place. The blue edges and nodes in the above figure indicate the result.


Input: list1 = [0,1,2,3,4,5,6], a = 2, b = 5, list2 = [1000000,1000001,1000002,1000003,1000004]
Output: [0,1,1000000,1000001,1000002,1000003,1000004,6]
Explanation: The blue edges and nodes in the above figure indicate the result.
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
    public ListNode mergeInBetween(ListNode list1, int a, int b, ListNode list2) {
        ListNode beforeA = findNodeBeforeNthNode(list1, a);
        ListNode beforeB = findNodeBeforeNthNode(list1, b);
        ListNode afterB = beforeB.next.next;
        
        ListNode p = list2;
        while (p != null && p.next != null) {
            p = p.next;
        }
        beforeA.next = list2;
        p.next = afterB;
        return list1;
        
    }
    
    private ListNode findNodeBeforeNthNode(ListNode head, int n) {
        ListNode dummy = new ListNode(-1, head);
        ListNode p = dummy;
        while (p != null && n > 0) {
            p = p.next;
            --n;
        }
        return p;
    }
}




