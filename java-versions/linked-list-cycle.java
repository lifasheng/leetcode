/*
141. Linked List Cycle
Easy
Given head, the head of a linked list, determine if the linked list has a cycle in it.

There is a cycle in a linked list if there is some node in the list that can be reached again by continuously following the next pointer. Internally, pos is used to denote the index of the node that tail's next pointer is connected to. Note that pos is not passed as a parameter.

Return true if there is a cycle in the linked list. Otherwise, return false.

 

Example 1:
Input: head = [3,2,0,-4], pos = 1
Output: true
Explanation: There is a cycle in the linked list, where the tail connects to the 1st node (0-indexed).

Example 2:
Input: head = [1,2], pos = 0
Output: true
Explanation: There is a cycle in the linked list, where the tail connects to the 0th node.

Example 3:
Input: head = [1], pos = -1
Output: false
Explanation: There is no cycle in the linked list.
*/

/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    // Time: O(N), Space: O(N)
    public boolean hasCycle1(ListNode head) {
        Set<ListNode> s = new HashSet<>();
        while(head != null) {
            if (s.contains(head)) {
                return true;
            } else {
                s.add(head);
            }
            head = head.next;
        }
        return false;
    }
    
    /*
    Now consider a cyclic list and imagine the slow and fast pointers are two runners racing around a circle track. The fast runner will eventually meet the slow runner. Why? Consider this case (we name it case A) - The fast runner is just one step behind the slow runner. In the next iteration, they both increment one and two steps respectively and meet each other.

    How about other cases? For example, we have not considered cases where the fast runner is two or three steps behind the slow runner yet. This is simple, because in the next or next's next iteration, this case will be reduced to case A mentioned above.
    对于faster比slower慢一步的情况，他们下一步就相遇。
    对于faster比slower慢两步的情况，他们走一步之后，就变成差一步。
    同理，对于faster比slower慢三步的情况，他们走一步之后，就变成差两步，所以最后总会退化成差一步。
    */
    // Time: O(N), Space: O(1)
    public boolean hasCycle2(ListNode head) {        
        ListNode fast = head;
        ListNode slow = head;
        while(fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) return true;
        }
        return false;
    }
    
    public boolean hasCycle(ListNode head) {
        return hasCycle2(head);
    }
}

