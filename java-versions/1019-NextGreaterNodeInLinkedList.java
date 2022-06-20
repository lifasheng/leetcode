```
You are given the head of a linked list with n nodes.

For each node in the list, find the value of the next greater node. That is, for each node, find the value of the first node that is next to it and has a strictly larger value than it.

Return an integer array answer where answer[i] is the value of the next greater node of the ith node (1-indexed). If the ith node does not have a next greater node, set answer[i] = 0.


Example 1:
Input: head = [2,1,5]
Output: [5,5,0]

Example 2:
Input: head = [2,7,4,3,5]
Output: [7,0,5,5,0]
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
    public int[] nextLargerNodes(ListNode head) {
        return findNextLargerNodeUsingMonotonicStackFromBackToFront(head);
    }
    
    
    // Solution 1: recursively find next larger node
    private int[] findNextLargerNodeRecursively(ListNode head) {
        Map<ListNode, Integer> m = new HashMap<>();
        findNextLargerNodeRecursively(head, m);
        int[] result = new int[m.size()];
        ListNode p = head;
        int index = 0;
        while(p != null) {
            result[index] = m.get(p);
            ++index;
            p = p.next;
        }
        return result;
    }
    
    private void findNextLargerNodeRecursively(ListNode head, Map<ListNode, Integer> m) {
        if (head == null) return;
        if (head.next == null) {
            m.put(head, 0);
            return;
        }
        
        if (head.next.val > head.val) {
            m.put(head, head.next.val);
            findNextLargerNodeRecursively(head.next, m);
            return;
        }
        
        findNextLargerNodeRecursively(head.next, m);
        
        // optimize here, avoid O(N^2),  i.e   6->5->4->3->2->1
        // otherwise, it will time limit
        // but it is still not efficient for case: 6->5->4->3->2->7,  or 6->5->4->1->2->7
        if (head.next.val <= head.val && m.get(head.next) == 0) {
            m.put(head, 0);
            return;
        }
        
        ListNode p = head.next;
        m.put(head, 0); // set default, if next larger node found, will override it. 
        while(p != null) {
            if (m.get(p) > head.val) {
                m.put(head, m.get(p));
                break;
            }
            p = p.next;
        }
    }
    
    // Solution 2: use two pointer, O(N^2), slower than Solution 1 because its worst case is O(N^2)
    private int[] findNextLargerNodeUsingTwoPointer(ListNode head) {
        if (head == null) return new int[0];
        if (head.next == null) {
            return new int[]{0};
        }
        
        ArrayList<Integer> list = new ArrayList<>();
        ListNode p = head;
        while(p != null) {
            ListNode q = p.next;
            boolean found = false;
            while(q != null) {
                if (q.val > p.val) {
                    list.add(q.val);
                    found = true;
                    break;
                }
                q = q.next;
            }
            if (!found) {
                list.add(0);
            }
            p = p.next;
        }
        
        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            result[i] = list.get(i);
        }
        return result;
    }
    
    private ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) return head;
        
        ListNode newHead = null;
        ListNode p = head;
        while (p != null) {
            ListNode next = p.next;
            p.next = newHead;
            newHead = p;
            p = next;
        }
        return newHead;
    }
    
    
    // Solution 3: using monotonic stack, reverse list, O(N)
    // https://www.youtube.com/watch?v=74uDE9e_ess
    // example head = [2,7,4,3,5]
    // reverse list: 5 3 4 7 2
    private int[] findNextLargerNodeUsingMonotonicStackAndReverseList(ListNode head) {
        if (head == null) return new int[0];
        if (head.next == null) return new int[]{0};
        
        ListNode reversedHead = reverseList(head);
        // save val
        Stack<Integer> stack = new Stack<>();
        
        ArrayList<Integer> list = new ArrayList<>();
        ListNode p = reversedHead;
        while(p != null) {
            if (stack.isEmpty()) {
                list.add(0);
                stack.push(p.val);
                p = p.next;
            } else { 
                if (p.val < stack.peek()) {
                    list.add(stack.peek());
                    stack.push(p.val);
                    p = p.next;
                } else {
                    stack.pop();
                }
            }
        }
        Collections.reverse(list);
        
        return listToArray(list);
    }
    
    // similar with solution 3, scan from back to front
    // example head = [2,7,4,3,5]
    // 从后往前扫描时，stack里面存的是之前遇到的数
    private int[] findNextLargerNodeUsingMonotonicStackFromBackToFront(ListNode head) {
        List<Integer> list = new ArrayList<>();
        for (ListNode p = head; p != null; p = p.next) {
            list.add(p.val);
        }
        
        int[] result = new int[list.size()];
        
        // save val
        Stack<Integer> stack = new Stack<>();
        
        for(int i = list.size()-1; i  >= 0; --i) {
            while(!stack.isEmpty() && list.get(i) >= stack.peek()) {
                stack.pop();
            }
            result[i] = stack.isEmpty() ? 0 : stack.peek();
            stack.push(list.get(i));
        }
        
        return result;
    }
    
    private int[] listToArray(List<Integer> list) {
        int[] result = new int[list.size()];
        for(int i=0; i<list.size(); ++i) {
            result[i] = list.get(i);
        }
        return result;
    }
    
    class Pair {
        int index;
        int val;

        public Pair(int index, int val) {
            this.index = index;
            this.val = val;
        }
    }
    
    private int listLength(ListNode head) {        
        int len = 0;
        ListNode p = head;
        while(p != null) {
            ++len;
            p = p.next;
        }
        return len;
    }
    
    // Solution 4: using monotonic stack, no reverse, O(N)
    // https://www.cnblogs.com/cnoodle/p/12490559.html
    // example head = [2,7,4,3,5]
    // scan from front to back
    // 从前往后扫描时，stack里面存的是之前的下标
    private int[] findNextLargerNodeUsingMonotonicStackWithoutReverseList(ListNode head) {
        if (head == null) return new int[0];
        if (head.next == null) return new int[]{0};
        
        int listLen = listLength(head);
        int[] result = new int[listLen];
        
        Stack<Pair> stack = new Stack<>();

        ListNode p = head;
        int index = 0;
        while(p != null) {
            while (!stack.isEmpty() && p.val > stack.peek().val) {
                result[stack.peek().index] = p.val;
                stack.pop();
            }
            stack.push(new Pair(index, p.val));
            p = p.next;
            ++index;
        }
        return result;
    }
    
    // same as Solution 4, but use an ArrayList to help
    // example head = [2,7,4,3,5]
    private int[] findNextLargerNodeUsingMonotonicStackWithoutReverseList2(ListNode head) {
        List<Integer> list = new ArrayList<>();
        for(ListNode p = head; p != null; p = p.next) {
            list.add(p.val);
        }
        
        // default set to 0
        int[] result = new int[list.size()];
        
        // save the index of each item in ArrayList
        Stack<Integer> stack = new Stack<>();

        for(int i=0; i<list.size(); ++i) {
            while (!stack.isEmpty() && list.get(i) > list.get(stack.peek())) {
                result[stack.peek()] = list.get(i);
                stack.pop();
            }
            stack.push(i);
        }
        return result;
    }
}


