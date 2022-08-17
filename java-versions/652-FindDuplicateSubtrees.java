/*
Given the root of a binary tree, return all duplicate subtrees.

For each kind of duplicate subtrees, you only need to return the root node of any one of them.

Two trees are duplicate if they have the same structure with the same node values.

Example 1:
Input: root = [1,2,3,4,null,2,4,null,null,4]
Output: [[2,4],[4]]

Example 2:
Input: root = [2,1,1]
Output: [[1]]

Example 3:
Input: root = [2,2,2,3,null,3,null]
Output: [[2,3],[3]]

Constraints:
The number of the nodes in the tree will be in the range [1, 10^4]
-200 <= Node.val <= 200
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
    
    // 转换思路，就是后续遍历一下整棵树，遍历时序列化每棵子树，并用hash保持子树的根结点
    // binary tree codec, very good!!! 
    // 之前的codec都用了StringBuilder, 这里就不用，可以改改之前的也不用StringBuilder 试试。
    
    
    private static final String NULL = "#";
    private static final String SEP = ",";
 
    // we can also optimize the value to save how many times this serializedstring occurs.
    private Map<String, List<TreeNode>> serializedStringToNodesMap = new HashMap<>();
    
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        serialize(root);
        
        List<TreeNode> result = new ArrayList<>();
        for (List<TreeNode> list : serializedStringToNodesMap.values()) {
            if (list.size() > 1) {
                result.add(list.get(0));
            }
        }
        return result;
    }
    
    private String serialize(TreeNode node) {
        if (node == null)  {
            return NULL;
        }
        
        String left = serialize(node.left);
        String right = serialize(node.right);
        String serializedString = left + SEP + right + SEP + String.valueOf(node.val);
        serializedStringToNodesMap.putIfAbsent(serializedString, new ArrayList<>());
        serializedStringToNodesMap.get(serializedString).add(node);
        return serializedString;
    }

}

