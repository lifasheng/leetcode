/*
Given an array of unique integers preorder, return true if it is the correct preorder traversal sequence of a binary search tree.

Example 1:
Input: preorder = [5,2,1,3,6]
Output: true

Example 2:
Input: preorder = [5,2,6,1,3]
Output: false
*/


class Solution {
    // [1, 2]       // true
    // [2, 1]       // ture
    // [3, 1, 2]    // true
    // [2, 1, 3]    // true
    // [5,2,1,3,6]  // true
    // [5,2,6,1,3]  //false
    public boolean verifyPreorder(int[] preorder) {
        int size = preorder.length;
        if (size <= 1) return true;
        return isValidBST(preorder, 0, size - 1);
    }
    
    private boolean isValidBST(int[] preorder, int start, int end) {
        if (start >= end) return true;
        
        int root = preorder[start];
        
        // find first number that is greater than root, which is right child of root.
        // this loop will ensure all node in left is less than root.
        int right = -1;
        for (int i = start + 1; i <= end; ++i) {
            if (preorder[i] > root) {
                right = i;
                break;
            }
        }
        
        if (right == -1) { // no right child
            return isValidBST(preorder, start + 1, end);
        } else {
            // all right node should greater than root
            for (int i = right; i <= end; ++i) {
                if (preorder[i] <= root) {
                    return false;
                }
            }

            // recursive to verify left and right sub tree.            
            return isValidBST(preorder, start + 1, right - 1) 
                && isValidBST(preorder, right, end); 
        }
        
    }
}

