### Given the head of a linked list, return the list after sorting it in ascending order.

```
Input: head = [4,2,1,3]
Output: [1,2,3,4]

Input: head = [-1,5,3,4,0]
Output: [-1,0,3,4,5]

Input: head = []
Output: []
```


```
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
    public ListNode sortList(ListNode head) {
        return mergeSort(head);
    }
    
    private ListNode heapSort(ListNode head) {
        PriorityQueue<ListNode> pq = new PriorityQueue<>((n1, n2)-> n1.val - n2.val);
        while(head != null) {
            pq.add(head);
            head = head.next;
        }
        
        ListNode dummy = new ListNode();
        ListNode p = dummy;
        while(!pq.isEmpty()) {
            p.next = pq.poll();
            p = p.next;
        }
        p.next = null;
        return dummy.next;
    }
    
    private ListNode mergeSort(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        
        ListNode secondHead = split(head);
        ListNode sortedFirst = mergeSort(head);
        ListNode sortedSecond = mergeSort(secondHead);
        return merge(sortedFirst, sortedSecond);
    }
    
    private ListNode split(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        
        ListNode dummy = new ListNode(-1, head);
        ListNode fast = dummy;
        ListNode slow = dummy;
        while(fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        ListNode result = slow.next;
        slow.next = null;
        return result;
    }
    
    private ListNode merge(ListNode h1, ListNode h2) {
        ListNode dummy = new ListNode();
        ListNode p = dummy;
        while(h1 != null && h2 != null) {
            if (h1.val < h2.val) {
                p.next = h1;
                p = p.next;
                h1 = h1.next;
            } else {
                p.next = h2;
                p = p.next;
                h2 = h2.next;
            }
        }
        if (h1 != null) {
            p.next = h1;
        }
        if (h2 != null) {
            p.next = h2;
        }
        return dummy.next;
    }
    
    
}
```