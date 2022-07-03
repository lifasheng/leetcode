```
Given the head of a linked list, we repeatedly delete consecutive sequences of nodes that sum to 0 until there are no such sequences.

After doing so, return the head of the final linked list.  You may return any such answer.

 

(Note that in the examples below, all sequences are serializations of ListNode objects.)

Example 1:

Input: head = [1,2,-3,3,1]
Output: [3,1]
Note: The answer [1,2,1] would also be accepted.
Example 2:

Input: head = [1,2,3,-3,4]
Output: [1,2,4]
Example 3:

Input: head = [1,2,3,-3,-2]
Output: [1]

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
    public ListNode removeZeroSumSublists(ListNode head) {
        return removeZeroSumSubListIterative(head);
    }
    
    // O(N): prefix sum, TODO: get familiar with this!
    private ListNode removeZeroSumSubListIterative(ListNode head) {
        if (head == null) return null;
        
        ListNode dummy = new ListNode(0, head);
        Map<Integer, ListNode> m = new HashMap<>();
        
        m.put(0, dummy);
        
        ListNode p = head;
        int presum = 0;
        while (p != null) {
            presum += p.val;
            
            if (!m.containsKey(presum)) {
                m.put(presum, p);
            } else {
                int sum = presum;
                // 1   2    3  -3   4 5
                //   start      p
                ListNode start = m.get(presum);
                ListNode end = p;
                while (start != end) {
                    start = start.next;
                    sum += start.val;
                    if (start != end) {
                        m.remove(sum);
                    }
                }
                m.get(presum).next = p.next;
            }
            p = p.next;
        }
        return dummy.next;
    }
    
    // O(N^2)
    private ListNode removeZeroSumSublistsRecursive(ListNode head) {
        if(head == null) return null;
        // Current Case: to find zero sum consecutive nodes from HEAD
        ListNode cur = head;
        int sum = 0;
        while(cur != null) {
            sum += cur.val;
            // found
            if(sum == 0) break;
            cur = cur.next;
        }
        // found such consecutive nodes, delete them by recursing to cur.next
        if(cur != null) return removeZeroSumSublistsRecursive(cur.next);
        // such consecutive nodes don't exist, move on to head.next;
        head.next = removeZeroSumSublistsRecursive(head.next);
        return head;
        
    }
}
```
