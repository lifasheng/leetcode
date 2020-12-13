/*
25. Reverse Nodes in k-Group
Hard
Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.

k is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not a multiple of k then left-out nodes, in the end, should remain as it is.

Follow up:

Could you solve the problem in O(1) extra memory space?
You may not alter the values in the list's nodes, only nodes itself may be changed.
 

Example 1:


Input: head = [1,2,3,4,5], k = 2
Output: [2,1,4,3,5]
Example 2:


Input: head = [1,2,3,4,5], k = 3
Output: [3,2,1,4,5]
Example 3:

Input: head = [1,2,3,4,5], k = 1
Output: [1,2,3,4,5]
Example 4:

Input: head = [1], k = 1
Output: [1]
 

Constraints:

The number of nodes in the list is in the range sz.
1 <= sz <= 5000
0 <= Node.val <= 1000
1 <= k <= sz
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
    private int getLength(ListNode head) {
        int len = 0;
        while(head != null) {
            ++len;
            head = head.next;
        }
        return len;
    }
    
    // 用stack辅助，先算出list长度，就知道需要reverse几个group。
    // Time: O(N), Space: O(K)
    public ListNode reverseKGroup1(ListNode head, int k) {
        if (head == null || head.next == null || k<=1) return head;

        int len = getLength(head);
        
        ListNode dummyHead = new ListNode(-1);
        ListNode q = dummyHead;
        
        Stack<ListNode> s = new Stack<>();
        ListNode p = head;
        for(int n=1; n<=len/k; ++n) { // n代表几个group
            for(int i=1; i<=k; ++i) {
                s.push(p);
                p = p.next;
            }
            for(int i=1; i<=k; ++i) {
                q.next = s.pop();
                q = q.next;
            }
        }
        if (p != null) {
            q.next = p;
        } else {
            q.next = null;
        }
        
        return dummyHead.next;
    }
    
    private ListNode reverseOneGroup(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode head2 = null;
        while(head != null) {
            ListNode next = head.next;
            head.next = head2;
            head2 = head;
            head = next;
        }
        return head2;
    }
    
    // 递归版， Time: O(N), Space: O(N/k), 所以它的空间不一定比使用stack辅助好。
    public ListNode reverseKGroup2(ListNode head, int k) {
        if (head == null || head.next == null || k<=1) return head;
        
        // 找到前面的k个元素，p指向group的最后一个元素
        ListNode p = head;
        int n = k-1;
        while(p != null && n > 0) {
            --n;
            p = p.next;
        }
        if (p != null) { // 有k个元素，对这个group翻转，并递归
            ListNode remain = reverseKGroup2(p.next, k);
            p.next = null; // 断开，这样我们就可以使用普通的翻转链表的方法来翻转前面的group
            ListNode newHead = reverseOneGroup(head);
            head.next = remain; // head成了翻转后的尾节点，和后面递归翻转得到的链表连接起来。
            return newHead;
        } else { // 不足k个元素，不用翻转
            return head;
        }
    }
    
    // 迭代版， Time: O(N), Space: O(1)， 思路类似于递归版
    public ListNode reverseKGroup3(ListNode head, int k) {
        if (head == null || head.next == null || k<=1) return head;
        
        ListNode newHead = null;
        ListNode firstOfGroup = head;
        ListNode lastOfRerversedGroup = null;
        ListNode p = head;
        while (true) {
            // 找到k个元素，p指向group的最后一个元素
            int n = k-1;
            while(p != null && n > 0) {
                --n;
                p = p.next;
            }
            if (p != null) { // 有k个元素，对这个group翻转
                ListNode next = p.next;
                p.next = null; // 断开，这样我们就可以使用普通的翻转链表的方法来翻转前面的group
                ListNode reversedHead = reverseOneGroup(firstOfGroup);
                if (newHead == null) {
                    newHead = reversedHead;
                }
                // 连接前后两个group
                if (lastOfRerversedGroup != null) {
                    lastOfRerversedGroup.next = reversedHead;
                } 
                lastOfRerversedGroup = firstOfGroup;
                
                // 进入下一个group
                p = next;
                firstOfGroup = next;
            } else { // 不足k个元素，不用翻转，结束了
                lastOfRerversedGroup.next = firstOfGroup;
                break;
            }
        }
        return newHead;
    }
    
    public ListNode reverseKGroup(ListNode head, int k) {
        return reverseKGroup3(head, k);
    }
}


