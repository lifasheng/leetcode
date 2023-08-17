/*
very very good!!

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


/*
https://labuladong.github.io/algo/di-yi-zhan-da78c/shou-ba-sh-66994/dong-ge-da-d14d3/
总结下结论，当二叉树中节点的值不存在重复时：
如果你的序列化结果中不包含空指针的信息，且你只给出一种遍历顺序，那么你无法还原出唯一的一棵二叉树。
如果你的序列化结果中不包含空指针的信息，且你会给出两种遍历顺序，那么按照前文 东哥手把手带你刷二叉树（构造篇） 所说，分两种情况：
2.1. 如果你给出的是前序和中序，或者后序和中序，那么你可以还原出唯一的一棵二叉树。
2.2. 如果你给出前序和后序，那么你无法还原出唯一的一棵二叉树。
如果你的序列化结果中包含空指针的信息，且你只给出一种遍历顺序，也要分两种情况：
3.1. 如果你给出的是前序或者后序，那么你可以还原出唯一的一棵二叉树。
3.2. 如果你给出的是中序，那么你无法还原出唯一的一棵二叉树。
*/
// pre-order
public class Codec {

    static String NULL = "#";
    static String SEP = ",";

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serialize(root, sb);
        return sb.substring(0, sb.length() - 1);
    }

    private void serialize(TreeNode root, StringBuilder sb) {
        if (root == null) {
            sb.append(NULL).append(SEP);
            return;
        }

        sb.append(root.val).append(SEP);

        serialize(root.left, sb);
        serialize(root.right, sb);
    }



    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        LinkedList<String> list = new LinkedList<>();
        String[] arr = data.split(SEP);
        for (String s : arr) {
            list.add(s);
        }

        return deserialize(list);
    }

    private TreeNode deserialize(LinkedList<String> list) {
        String s = list.removeFirst();

        if (NULL.equals(s)) {
            return null;
        }

        TreeNode node = new TreeNode(Integer.valueOf(s));

        node.left = deserialize(list);
        node.right = deserialize(list);
        return node;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// TreeNode ans = deser.deserialize(ser.serialize(root));     








// post-order
public class Codec {

    static String NULL = "#";
    static String SEP = ",";

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serialize(root, sb);
        return sb.substring(0, sb.length() - 1);
    }

    private void serialize(TreeNode root, StringBuilder sb) {
        if (root == null) {
            sb.append(NULL).append(SEP);
            return;
        }

        serialize(root.left, sb);
        serialize(root.right, sb);
        sb.append(root.val).append(SEP);
    }



    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        LinkedList<String> list = new LinkedList<>();
        String[] arr = data.split(SEP);
        for (String s : arr) {
            list.add(s);
        }

        return deserialize(list);
    }

    private TreeNode deserialize(LinkedList<String> list) {
        String s = list.removeLast();

        if (NULL.equals(s)) {
            return null;
        }

        TreeNode node = new TreeNode(Integer.valueOf(s));

        node.right = deserialize(list);
        node.left = deserialize(list);
        return node;
    }
}


