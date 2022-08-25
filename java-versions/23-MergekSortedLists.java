/*
Hard

You are given an array of k linked-lists lists, each linked-list is sorted in ascending order.

Merge all the linked-lists into one sorted linked-list and return it.

Example 1:
Input: lists = [[1,4,5],[1,3,4],[2,6]]
Output: [1,1,2,3,4,4,5,6]
Explanation: The linked-lists are:
[
  1->4->5,
  1->3->4,
  2->6
]
merging them into one sorted list:
1->1->2->3->4->4->5->6

Example 2:
Input: lists = []
Output: []

Example 3:
Input: lists = [[]]
Output: []

Constraints:
k == lists.length
0 <= k <= 104
0 <= lists[i].length <= 500
-104 <= lists[i][j] <= 104
lists[i] is sorted in ascending order.
The sum of lists[i].length will not exceed 104.
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
    public ListNode mergeKLists(ListNode[] lists) {
        return mergeKLists_heap(lists);
    }
    
    // solution 1: merge every two lists
    private ListNode mergeKLists_mergeSort(ListNode[] lists) {
        if (lists.length == 0) return null;
        ListNode list = lists[0];
        for (int i = 1; i < lists.length; ++i) {
            list = mergeTwoLists(list, lists[i]);
        }
        return list;
    }
    
    private ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null) return list2;
        if (list2 == null) return list1;
        
        ListNode p1 = list1;
        ListNode p2 = list2;
        ListNode dummy = new ListNode(-1);
        ListNode p = dummy;
        while (p1 != null || p2 != null) {
            int v1 = (p1 == null) ? Integer.MAX_VALUE : p1.val;
            int v2 = (p2 == null) ? Integer.MAX_VALUE : p2.val;
            if (v1 < v2) {
                p.next = p1;
                p1 = p1.next;
            } else {
                p.next = p2;
                p2 = p2.next;
            }
            p = p.next;
        }
        return dummy.next;
    }
    
    // solution 2: use heap to merge
    private ListNode mergeKLists_heap(ListNode[] lists) {
        if (lists.length == 0) return null;
        
        PriorityQueue<ListNode> pq = new PriorityQueue<>((n1, n2) -> n1.val - n2.val);
        for (ListNode node : lists) {
            if (node != null) {
                pq.offer(node);
            }
        }
        
        ListNode dummy = new ListNode(-1);
        ListNode p = dummy;
        while (!pq.isEmpty()) {
            ListNode node = pq.poll();
            p.next = node;
            p = p.next;
            if (node.next != null) {
                pq.offer(node.next);
            }
        }
        return dummy.next;
    }

    // solution 3: divide and conceque
}

