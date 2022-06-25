/*
You need to construct a binary tree from a string consisting of parenthesis and integers.

The whole input represents a binary tree. It contains an integer followed by zero, one or two pairs of parenthesis. The integer represents the root's value and a pair of parenthesis contains a child binary tree with the same structure.

You always start to construct the left child node of the parent first if it exists.

Example 1:
Input: s = "4(2(3)(1))(6(5))"
Output: [4,2,6,3,1,5]
Example 2:

Input: s = "4(2(3)(1))(6(5)(7))"
Output: [4,2,6,3,1,5,7]
Example 3:

Input: s = "-4(2(3)(1))(6(5)(7))"
Output: [-4,2,6,3,1,5,7]
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
    public TreeNode str2tree(String s) {
        return str2tree_recursive(s);
    }
    
    private TreeNode str2tree_usingstack(String s) {
        // TODO
        return null;
    }
    
    private TreeNode str2tree_recursive(String s) {
        if (s == null || s.isEmpty()) return null;
        
        List<String> parts = splitString(s);
        
        TreeNode root = new TreeNode(Integer.valueOf(parts.get(0)));
        root.left = str2tree(parts.get(1));
        root.right = str2tree(parts.get(2));
        return root;
    }
    
    private List<String> splitString(String str) {
        List<String> list = new ArrayList<>();
        if (str.isEmpty()) {
            return list;
        }
        if (!str.contains("(")) { // single node
            return Arrays.asList(str, null, null);
        }
        
        int leftStartIndex = str.indexOf('(');
        
        // root of current string
        list.add(str.substring(0, leftStartIndex));
        
        int leftEndIndex = leftStartIndex + 1;
        int count = 1; // used to match ( and )
        for (int i = leftStartIndex+1; i < str.length(); ++i) {
            char c = str.charAt(i);
            if (c == '(') {
                ++ count;
            }
            if (c == ')') {
                -- count;
                if (count == 0) { // find matching right brace, get root's left part
                    leftEndIndex = i;
                    list.add(str.substring(leftStartIndex + 1, leftEndIndex));
                    
                    if (i + 1 != str.length()) { // right child part
                        list.add(str.substring(leftEndIndex + 2, str.length() - 1));
                    } else { // no right child
                        list.add(null);
                    }
                    break;
                }
            }
        }
        return list;
        
    }
}






