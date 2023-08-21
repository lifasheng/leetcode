/*
very very good!
Easy

Given the roots of two binary trees root and subRoot, return true if there is a subtree of root with the same structure and node values of subRoot and false otherwise.
A subtree of a binary tree tree is a tree that consists of a node in tree and all of this node's descendants. The tree tree could also be considered as a subtree of itself.

Example 1:
Input: root = [3,4,5,1,2], subRoot = [4,1,2]
Output: true

Example 2:
Input: root = [3,4,5,1,2,null,null,null,null,0], subRoot = [4,1,2]
Output: false
 
Constraints:
The number of nodes in the root tree is in the range [1, 2000].
The number of nodes in the subRoot tree is in the range [1, 1000].
-104 <= root.val <= 104
-104 <= subRoot.val <= 104
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
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        return solution2(root, subRoot);
    }

    // O(m*n) m 是root的节点数，n是subroot的节点数
    /*
     注意这里不是
        return isSameTree(root, subRoot) 
        || isSameTree(root.left, subRoot) 
        || isSameTree(root.right, subRoot);
     因为这样的的话其实是没有往下递归的，在root以及它的左右节点比较完就结束了，但是很有可能在很下面的子树能匹配上。
    */
    private boolean solution1(TreeNode root, TreeNode subRoot) {
        if (root == null && subRoot == null) return true;
        if (root == null || subRoot == null) return false;

        return isSameTree(root, subRoot) 
        || solution1(root.left, subRoot) 
        || solution1(root.right, subRoot);
    }

    private boolean isSameTree(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) {
            return true;
        }
        if (root1 == null || root2 == null) {
            return false;
        }

        return root1.val == root2.val
        && isSameTree(root1.left, root2.left)
        && isSameTree(root1.right, root2.right);
    }

    /*
     这里用到了serialization的技巧，但是每个数字前面需要加一个字符，因为[14]和[4] 也是有包含关系，但却不是相同的树。

      // example1: 3 4 1 # # 2 # # 5 ##
      // example2: 3 4 1 # # 2 0 # # # 5 # # 
      //  subRoot:   4 1 # # 2 # #

      用到了之前那道Serialize and Deserialize Binary Tree的解法，思路是对s和t两棵树分别进行序列化，
      各生成一个字符串，如果t的字符串是s的子串的话，就说明t是s的子树，
      但是需要注意的是，为了避免出现[12], [2], 这种情况，虽然2也是12的子串，但是[2]却不是[12]的子树，
      所以我们再序列化的时候要特殊处理一下，就是在每个结点值前面都加上一个字符，比如','，来分隔开，
      那么[12]序列化后就是",12,#"，而[2]序列化之后就是",2,#"，这样就可以完美的解决之前的问题了，
    
    字符串比较的时间复杂度最差情况也是O(m*n)
    */
    private boolean solution2(TreeNode root, TreeNode subRoot) {
        String s1 = serialize(root);
        String s2 = serialize(subRoot);
        return s1.contains(s2);
    }

    private static final String NULL = "#";
    private static final String SEP = ",";
    private String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serialize(root, sb);
        return sb.substring(0, sb.length() - 1);
    }

    private void serialize(TreeNode root, StringBuilder sb) {
        if (root == null) {
            sb.append(NULL).append(SEP);
            return;
        }

        sb.append(',').append(root.val).append(SEP);
        serialize(root.left, sb);
        serialize(root.right, sb);
    }

}





