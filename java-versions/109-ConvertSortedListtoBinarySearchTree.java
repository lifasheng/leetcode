/*
Given the head of a singly linked list where elements are sorted in ascending order, convert it to a height balanced BST.

For this problem, a height-balanced binary tree is defined as a binary tree in which the depth of the two subtrees of every node never differ by more than 1.

Example 1:
Input: head = [-10,-3,0,5,9]
Output: [0,-3,9,-10,null,5]
Explanation: One possible answer is [0,-3,9,-10,null,5], which represents the shown height balanced BST.

Example 2:
Input: head = []
Output: []
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
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
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
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public TreeNode sortedListToBST(ListNode head) {
        return buildBST_inorder(head);
    }

    // Solution 1: split list in the middle, Time complexity: O(NlogN)
    private TreeNode buildBST_usingList(ListNode head) {
        if (head == null) return null;
        if (head.next == null) return new TreeNode(head.val);

        ListNode middleNode = findMiddleNode(head);
        TreeNode root = new TreeNode(middleNode.val);
        root.left = buildBST_usingList(head);
        root.right = buildBST_usingList(middleNode.next);
        return root;
    }

    // dummy->1->2
    // dummy->1->2->3
    private ListNode findMiddleNode(ListNode head) {
        if (head == null || head.next == null) return head;


        ListNode dummy = new ListNode(-1, head);
        ListNode fast = dummy;
        ListNode slow = dummy;
        while (fast != null && fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        ListNode middleNode = slow.next;
        slow.next = null;
        return middleNode;
    }

     // Solution 2: use ArrayList to help, Time complexity: O(N)
    private TreeNode buildBST_usingArray(ListNode head) {
        if (head == null) return null;
        if (head.next == null) return new TreeNode(head.val);

        List<Integer> list = new ArrayList<>();
        for (ListNode p = head; p != null; p = p.next) {
            list.add(p.val);
        }

        return buildBST(list, 0, list.size() - 1);
    }

    private TreeNode buildBST(List<Integer> list, int start, int end) {
        if (start > end) return null;
        if (start == end) return new TreeNode(list.get(start));

        int mid = start + (end - start) / 2;

        TreeNode root = new TreeNode(list.get(mid));
        root.left = buildBST(list, start, mid - 1);
        root.right = buildBST(list, mid + 1, end);
        return root;
    }

    // Solution 3: inorder, Time complexity: O(N), the best!
    private ListNode cur;
    private TreeNode buildBST_inorder(ListNode head) {
        if (head == null) return null;
        if (head.next == null) return new TreeNode(head.val);

        cur = head;
        int len = getListLength(head);
        return buildBST_inorder(0, len - 1);
    }

    private TreeNode buildBST_inorder(int start, int end) {
        if (start > end) return null;

        int mid = start + (end - start) / 2;

        TreeNode left = buildBST_inorder(start, mid - 1);

        TreeNode root = new TreeNode(cur.val);
        cur = cur.next;

        TreeNode right = buildBST_inorder(mid + 1, end);

        root.left = left;
        root.right = right;
        return root;
    }

    private int getListLength(ListNode head) {
        int len = 0;
        while (head != null) {
            ++ len;
            head = head.next;
        }
        return len;
    }
}

