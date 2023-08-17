/*
very very good!

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
        return solution2(lists);
    }

    /*
    list number is m
    each list average length is n

    1 2 3 4 5... m 个 list
    1 merge 2: 2n
    12 merge 3: 3n
    123 merge 4: 4n
    1234 merge 5: 5n
    .... ...  m: m*n

    2n + 3n + 4n + 5n +...+ m*n = n* (2+m)*(m-1) /2 = O(n * m^2)
    */
    private ListNode solution1(ListNode[] lists) {
        ListNode res = null;
        for (ListNode list : lists) {
            res = merge2Lists(res, list);
        }
        return res;
    }

    /*
    上面的方法是从头到尾的合并，时间复杂度是O(n * m^2)

    如果我们用归并排序的方法来做呢？
    假设有m个list， 1 2 3 4 5 ... m
    1 2 ...m/2      m/2 + 1 ... m
    f(m) = 2*f(m/2) + mn
    f(m/2) = 2*f(m/4) + (m/2)*n
    f(2) = 2*f(1) + 2*n

    f(m) = 2* (2*f(m/4) + m*n/2) + mn
         = 4f(m/4) + 2mn
         = 8f(m/8) + 3mn
         = 16f(m/16) + 4mn
         = ... ...
         m/(2^x) = 2
         x = log(m/2)

    time complexity = O(mn*log(m/2))

    这个试了一下，速度是最快的。
    */
    private ListNode solution2(ListNode[] lists) {
        if (lists.length == 0) {
            return null;
        }
        return recursiveMerge(lists, 0, lists.length - 1);
    }

    private ListNode recursiveMerge(ListNode[] lists, int low, int high) {
        if (low == high) {
            return lists[low];
        }

        if (low + 1 == high) {
            return merge2Lists(lists[low], lists[high]);
        }

        int mid = (low + high) / 2;
        ListNode left = recursiveMerge(lists, low, mid);
        ListNode right = recursiveMerge(lists, mid+1, high);

        return merge2Lists(left, right);
    }

    private ListNode merge2Lists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode();
        ListNode p = dummy;

        while(list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                p.next = list1;
                list1 = list1.next;
            } else {
                p.next = list2;
                list2 = list2.next;
            }
            p = p.next;
        }

        if (list1 != null) {
            p.next = list1;
        }

        if (list2 != null) {
            p.next = list2;
        }

        return dummy.next;
    }


    /*
    m 个list，每个list average length is n

    O(logm * m*n), 和归并合并一样的时间复杂度
    */
    private ListNode solution3(ListNode[] lists) {
        if (lists.length == 0) { // 注意这个判断很重要
            return null;
        }
        ListNode dummy = new ListNode();
        ListNode p = dummy;

        Queue<ListNode> pq = new PriorityQueue<>((l1, l2) -> l1.val - l2.val);
        for (ListNode list : lists) {
            if (list != null) { // 注意这个判断很重要
                pq.offer(list);
            }
        }

        while (!pq.isEmpty()) {
            ListNode top = pq.poll();
            p.next = top;
            p = p.next;
            if (top.next != null) {
                pq.offer(top.next);
            }
        }
        p.next = null;

        return dummy.next;

    }
}


