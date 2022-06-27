/*
One way to serialize a binary tree is to use preorder traversal. When we encounter a non-null node, we record the node's value. If it is a null node, we record using a sentinel value such as '#'.

For example, the above binary tree can be serialized to the string "9,3,4,#,#,1,#,#,2,#,6,#,#", where '#' represents a null node.

Given a string of comma-separated values preorder, return true if it is a correct preorder traversal serialization of a binary tree.

It is guaranteed that each comma-separated value in the string must be either an integer or a character '#' representing null pointer.

You may assume that the input format is always valid.

For example, it could never contain two consecutive commas, such as "1,,3".
Note: You are not allowed to reconstruct the tree.
*/

class Solution {
    public boolean isValidSerialization(String preorder) {
        return isValudPreorder_deserialize(preorder);
    }
    
    private boolean isValidPreorder_usingSlots(String preorder) {
        if (preorder.isEmpty()) return true;
        
        String[] values = preorder.split(",");
        
        int slots = 1;
        
        for (String value : values) {
            --slots;
            if (slots < 0) return false;
            
            if (!value.equals("#")) {
                slots += 2;
            }
        }
        
        return slots == 0;
    }
    
    private boolean isValudPreorder_deserialize(String preorder) {
        LinkedList<String> nodes = new LinkedList<>();
        for (String s : preorder.split(",")) {
            nodes.add(s);
        }
        return deserialize(nodes) && nodes.isEmpty();
    }
    
    private boolean deserialize(LinkedList<String> nodes) {
        if (nodes.isEmpty()) return false;
        
        // construct root node
        String s = nodes.removeFirst();
        if (s.equals("#")) {
            return true;
        }
        
        // construct left child and right child.
        return deserialize(nodes) && deserialize(nodes);
    }
}



