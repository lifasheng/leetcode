/*
Given two binary search trees root1 and root2, return a list containing all the integers from both trees sorted in ascending order.

Input: root1 = [2,1,4], root2 = [1,0,3]
Output: [0,1,1,2,3,4]

Input: root1 = [1,null,8], root2 = [8,1]
Output: [1,1,8,8]
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
    public List<Integer> getAllElements(TreeNode root1, TreeNode root2) {
        return getAllElementsOnePassWithStack(root1, root2);
    }
    
    // O(N)
    private List<Integer> getAllElementsOnePassWithStack(TreeNode root1, TreeNode root2) {
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        
        TreeNode p1 = root1;
        TreeNode p2 = root2;
        while (p1 != null || !stack1.isEmpty() || p2 != null || !stack2.isEmpty()) {
            while (p1 != null) {
                stack1.push(p1);
                p1 = p1.left;
            }
            
            while(p2 != null) {
                stack2.push(p2);
                p2 = p2.left;
            }
            
            if (!stack1.isEmpty() && !stack2.isEmpty()) {
                if (stack1.peek().val < stack2.peek().val) {
                    p1 = stack1.pop();
                    list.add(p1.val);
                    p1 = p1.right;
                } else {
                    p2 = stack2.pop();
                    list.add(p2.val);
                    p2 = p2.right;
                }
            } else if (!stack1.isEmpty()) {
                p1 = stack1.pop();
                list.add(p1.val);
                p1 = p1.right;
            } else {
                p2 = stack2.pop();
                list.add(p2.val);
                p2 = p2.right;
            }
        }
        return list;
    }
    
    // O(N)
    private List<Integer> getAllElementsTwoPass(TreeNode root1, TreeNode root2) {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        inOrderTraversal(root1, list1);
        inOrderTraversal(root2, list2);
        return merge(list1, list2);
    }
    
    private List<Integer> merge(List<Integer> list1, List<Integer> list2) {
        List<Integer> list = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (i < list1.size() && j < list2.size()) {
            if (list1.get(i) < list2.get(j)) {
                list.add(list1.get(i));
                ++ i;
            } else {
                list.add(list2.get(j));
                ++ j;
            }
        }
        while (i < list1.size()) {
            list.add(list1.get(i));
            ++ i;
        }
        while (j < list2.size()) {
            list.add(list2.get(j));
            ++ j;
        }
        return list;
    }
    
    private void inOrderTraversal(TreeNode root, List<Integer> list) {
        if (root == null) return;
        
        inOrderTraversal(root.left, list);
        list.add(root.val);
        inOrderTraversal(root.right, list);
    }
}


