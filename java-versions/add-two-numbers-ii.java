/*
445. Add Two Numbers II
Medium
You are given two non-empty linked lists representing two non-negative integers. The most significant digit comes first and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.

You may assume the two numbers do not contain any leading zero, except the number 0 itself.

Follow up:
What if you cannot modify the input lists? In other words, reversing the lists is not allowed.

Example:

Input: (7 -> 2 -> 4 -> 3) + (5 -> 6 -> 4)
Output: 7 -> 8 -> 0 -> 7
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
 
 
     public static ListNode createList(int[] arr) {
        ListNode[] nodes = new ListNode[arr.length];
        ListNode next = null;
        for(int i=arr.length-1; i>=0; --i) {
            nodes[i] = new ListNode(arr[i], next);
            next = nodes[i];
        }
        return nodes[0];
    }

    public static void printList(ListNode h) {
        while(h!=null) {
            System.out.print(h.val + " ");
            h = h.next;
        }
        System.out.println();
    }
    
 
 */

// We need to pass by reference, Integer is immutable, so not work.
class IntValue {
    int val;
    IntValue(int v) {
        this.val = v;
    }
}

class Solution {

    private ListNode reverse(ListNode head) {
        ListNode head2 = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = head2;
            head2 = head;
            head = next;
        }
        return head2;
    }
    
    private ListNode addTwoList(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(-1);
        ListNode p = dummyHead;
        int carry = 0;
        while(l1 != null || l2 != null) {
            int a = (l1 == null ? 0 : l1.val);
            int b = (l2 == null ? 0 : l2.val);
            int sum = a+b+carry;
            p.next = new ListNode(sum%10);
            p = p.next;
            carry = sum/10;
            l1 = (l1 == null ? null : l1.next);
            l2 = (l2 == null ? null : l2.next);
        }
        if (carry > 0) {
            p.next = new ListNode(carry);
        }
        return dummyHead.next;
    }
    
    // reverse two list, add them, and reverse result list
    public ListNode addTwoNumbers1(ListNode l1, ListNode l2) {
        return reverse(addTwoList(reverse(l1), reverse(l2)));
    }
    
    //-----------------------------------------
    private int getLength(ListNode h) {
        int len = 0;
        while(h != null) {
            ++len;
            h = h.next;
        }
        return len;
    }
    
    // we assume len(h1) > len(h2)
    // let's say h1: n11->n12->n13->n14, 
    //           h2:          n21->n22
    // then we will find the pivot n12, so that the left part is same long as h2.
    private ListNode findPivot(ListNode h1, ListNode h2, int lenDiff) {
        ListNode p = h1;
        for(int i=1; i<lenDiff; ++i) {
            p = p.next;
        }
        return p;
    }
    
    // add two list recursively without reverse list.
    // We use IntValue as reference so that the carry can be returned.
    private ListNode recursiveAddTwoList(ListNode h1, ListNode h2, IntValue carry) {
        if (h1 == null && h2 == null) return null;
        
        if (h1.next == null) {
            int sum = h1.val + h2.val + carry.val;
            carry.val = sum/10;
            return new ListNode(sum%10);
        }
        
        ListNode next = recursiveAddTwoList(h1.next, h2.next, carry);
        int sum = h1.val + h2.val + carry.val;
        carry.val = sum/10;
        return new ListNode(sum%10, next);
    }
    
    // add the carry to the list from right to left recursively.
    private ListNode recursiveAddOneList(ListNode h1, IntValue carry) {
        if (h1 == null) return null;
        
        if (h1.next == null) {
            int sum = h1.val + carry.val;
            carry.val = sum/10;
            return new ListNode(sum%10);
        }
        
        ListNode next = recursiveAddOneList(h1.next, carry);
        int sum = h1.val + carry.val;
        carry.val = sum/10;
        return new ListNode(sum%10, next);
    }
    
    // use recursive method to add two list
    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        int len1 = getLength(l1);
        int len2 = getLength(l2);
        if (len1 == len2) {
            IntValue carry = new IntValue(0);
            ListNode result = recursiveAddTwoList(l1, l2, carry);
            if (carry.val > 0) {
                return new ListNode(carry.val, result);
            } else {
                return result;
            }
        }
        
        // make sure h1 is longer than h2
        ListNode h1 = l1;
        ListNode h2 = l2;
        int lenDiff = len1 - len2;
        if (len1 < len2) {
            h1 = l2;
            h2 = l1;
            lenDiff = len2 - len1;
        }
        
        ListNode pivot = findPivot(h1, h2, lenDiff);
        ListNode pivotNext = pivot.next;
        pivot.next = null; // disconnect the longer list
        
        // the list starting with pivotNext is same long as h2
        IntValue carry = new IntValue(0);
        ListNode part2 = recursiveAddTwoList(pivotNext, h2, carry);
        ListNode part1 = recursiveAddOneList(h1, carry);
        if (carry.val > 0) {
            part1 = new ListNode(carry.val, part1);
        }
        pivot.next = pivotNext; // reconnect the longer list
        
        // link part1 to part2.
        ListNode p = part1;
        while(p.next!=null) {
            p = p.next;
        }
        p.next = part2;
        
        return part1;
    }
    
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        return addTwoNumbers2(l1, l2);
    }
}

