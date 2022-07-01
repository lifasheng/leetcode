/*
Two players play a turn based game on a binary tree. We are given the root of this binary tree, and the number of nodes n in the tree. n is odd, and each node has a distinct value from 1 to n.

Initially, the first player names a value x with 1 <= x <= n, and the second player names a value y with 1 <= y <= n and y != x. The first player colors the node with value x red, and the second player colors the node with value y blue.

Then, the players take turns starting with the first player. In each turn, that player chooses a node of their color (red if player 1, blue if player 2) and colors an uncolored neighbor of the chosen node (either the left child, right child, or parent of the chosen node.)

If (and only if) a player cannot choose such a node in this way, they must pass their turn. If both players pass their turn, the game ends, and the winner is the player that colored more nodes.

You are the second player. If it is possible to choose such a y to ensure you win the game, return true. If it is not possible, return false.

Example 1:
Input: root = [1,2,3,4,5,6,7,8,9,10,11], n = 11, x = 3
Output: true
Explanation: The second player can choose the node with value 2.

Example 2:
Input: root = [1,2,3], n = 3, x = 1
Output: false
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

/*
[8,6,7,3,4,null,9,null,2,5,null,null,null,null,null,null,1]
9
4


            8 
       6         7
   3       4           9
     2   5            
            1



[1,2,3,4,5,6,7,8,9,10,11]
11
3

        1
   2           3
 4    5      6     7
8 9 10 11  
*/
class Solution {
    private Map<TreeNode, TreeNode> parentMap = new HashMap<>();
    public boolean btreeGameWinningMove(TreeNode root, int n, int x) {
        buildParentMap(root, null);
        
        TreeNode nodeX = findNode(root, x);
        
        Set<TreeNode> visited = new HashSet<>();
        visited.add(nodeX);
        int leftSize = calculateSize(nodeX.left, visited);
        int rightSize = calculateSize(nodeX.right, visited);
        int parentSize = calculateSize(parentMap.get(nodeX), visited);
        
        int[] sizeArr = new int[]{leftSize, rightSize, parentSize};
        Arrays.sort(sizeArr);
        
        // we should always start from x's neighbor and choose the max size branch of node x.
        return sizeArr[2] > (sizeArr[0] + sizeArr[1] + 1);
    }
    
    private void buildParentMap(TreeNode node, TreeNode parent) {
        if (node == null) return;
        parentMap.put(node, parent);
        buildParentMap(node.left, node);
        buildParentMap(node.right, node);
    }
    
    private TreeNode findNode(TreeNode root, int x) {
        if (root == null) return null;
        if (root.val == x) return root;
        TreeNode left = findNode(root.left, x);
        if (left != null) return left;
        return findNode(root.right, x);
    }
    
    private int calculateSize(TreeNode node, Set<TreeNode> visited) {
        if (node == null || visited.contains(node)) return 0;
        
        visited.add(node);
        int leftSize = calculateSize(node.left, visited);
        int rightSize = calculateSize(node.right, visited);
        int parentSize = calculateSize(parentMap.get(node), visited);
        return 1 + leftSize + rightSize + parentSize;
    }
    
}

