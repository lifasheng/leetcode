/*
Serialization is converting a data structure or object into a sequence of bits so that it can be stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in the same or another computer environment.

Design an algorithm to serialize and deserialize a binary search tree. There is no restriction on how your serialization/deserialization algorithm should work. You need to ensure that a binary search tree can be serialized to a string, and this string can be deserialized to the original tree structure.

The encoded string should be as compact as possible.

Example 1:
Input: root = [2,1,3]
Output: [2,1,3]

Example 2:
Input: root = []
Output: []
 
Constraints:
The number of nodes in the tree is in the range [0, 104].
0 <= Node.val <= 104
The input tree is guaranteed to be a binary search tree.
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

    // BST Codec, very good!!!
    private static final String SEP = ",";
    
    // Encodes a tree to a single string.
    // preorder
    public String serialize(TreeNode root) {
        if (root == null) return "";
        
        StringBuilder sb = new StringBuilder();
        serialize(root, sb);
        String result = sb.toString();
        return result.substring(0, result.length() - 1);
    }
    
    private void serialize(TreeNode root, StringBuilder sb) {
        if (root == null) return;
        sb.append(root.val);
        sb.append(SEP);
        serialize(root.left, sb);
        serialize(root.right, sb);
    }

    // Decodes your encoded data to tree.
    // preorder + value range
    public TreeNode deserialize(String data) {
        if(data == null || data == "") return null;
        
        List<Integer> dataList = new ArrayList<>();
        for (String s : data.split(SEP)) {
            dataList.add(Integer.valueOf(s));
        }
        
        return deserialize(dataList, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    private TreeNode deserialize(List<Integer> dataList, int minVal, int maxVal) {
        if (dataList.isEmpty()) return null;
        
        int val = dataList.get(0);
        if (val < minVal || val > maxVal) return null;
        
        TreeNode node = new TreeNode(val);
        dataList.remove(0);
        
        node.left = deserialize(dataList, minVal, val);
        node.right = deserialize(dataList, val, maxVal);
        return node;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// String tree = ser.serialize(root);
// TreeNode ans = deser.deserialize(tree);
// return ans;

