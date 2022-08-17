/*
Serialization is the process of converting a data structure or object into a sequence of bits so that it can be stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in the same or another computer environment.

Design an algorithm to serialize and deserialize a binary tree. There is no restriction on how your serialization/deserialization algorithm should work. You just need to ensure that a binary tree can be serialized to a string and this string can be deserialized to the original tree structure.

Clarification: The input/output format is the same as how LeetCode serializes a binary tree. You do not necessarily need to follow this format, so please be creative and come up with different approaches yourself.

Example 1:
Input: root = [1,2,3,null,null,4,5]
Output: [1,2,3,null,null,4,5]

Example 2:
Input: root = []
Output: []
 
Constraints:
The number of nodes in the tree is in the range [0, 104].
-1000 <= Node.val <= 1000
*/


/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Codec {

    // binary tree codec, very good!!!
    private static final String NULL = "#";
    private static final String SEP = ",";
    
    // Encodes a tree to a single string.
    // preorder, with null terminator
    public String serialize(TreeNode root) {
        if (root == null) return NULL;
        String left = serialize(root.left);
        String right = serialize(root.right);
        return root.val + SEP + left + SEP + right;
    }
    
    public String serialize2(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serialize(root, sb);
        String result = sb.toString();
        return result.substring(0, result.length() - 1);
    }
    
    private void serialize(TreeNode root, StringBuilder sb) {
        if (root == null)  {
            sb.append(NULL).append(SEP);
            return;
        }
        
        sb.append(root.val).append(SEP);
        serialize(root.left, sb);
        serialize(root.right, sb);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        List<String> strings = new ArrayList<>();
        for (String s : data.split(SEP)) {
            strings.add(s);
        }
        return deserialize(strings);
    }
    
    // preorder
    private TreeNode deserialize(List<String> strings) {
        if (strings.isEmpty()) return null;
        
        String s = strings.get(0);
        strings.remove(0);
        
        if (s.equals(NULL)) return null;
        
        TreeNode node = new TreeNode(Integer.valueOf(s));
        node.left = deserialize(strings);
        node.right = deserialize(strings);
        return node;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// TreeNode ans = deser.deserialize(ser.serialize(root));

