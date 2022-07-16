/*
You are given the root of a binary tree with n nodes. Each node is uniquely assigned a value from 1 to n. You are also given an integer startValue representing the value of the start node s, and a different integer destValue representing the value of the destination node t.

Find the shortest path starting from node s and ending at node t. Generate step-by-step directions of such path as a string consisting of only the uppercase letters 'L', 'R', and 'U'. Each letter indicates a specific direction:

'L' means to go from a node to its left child node.
'R' means to go from a node to its right child node.
'U' means to go from a node to its parent node.
Return the step-by-step directions of the shortest path from node s to node t.

Example 1:
Input: root = [5,1,2,3,null,6,4], startValue = 3, destValue = 6
Output: "UURL"
Explanation: The shortest path is: 3 → 1 → 5 → 2 → 6.

Example 2:
Input: root = [2,1], startValue = 2, destValue = 1
Output: "L"
Explanation: The shortest path is: 2 → 1.
 
Constraints:
The number of nodes in the tree is n.
2 <= n <= 105
1 <= Node.val <= n
All the values in the tree are unique.
1 <= startValue, destValue <= n
startValue != destValue
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

// TODO: better understand the time complexity of each solution, very good!
class Solution {
    private Map<TreeNode, TreeNode> parentMap;
    private TreeNode startNode, destNode;
    
    public String getDirections(TreeNode root, int startValue, int destValue) {
        return getDirections_solution4(root, startValue, destValue);
    }
    

    /**
     * Solution 1: Time limit exceeded
     * step 1: build parent map, and get both startNode and destNode;
     * step 2: bfs starting from startNode, and reach destNode;
     */
    private String getDirections_bfs_usingmap(TreeNode root, int startValue, int destValue) {
        parentMap = new HashMap<>();
        buildParentMap(root, null, startValue, destValue);
        
        Set<TreeNode> visited = new HashSet<>();
        Deque<Pair<TreeNode, String>> queue = new ArrayDeque<>();
        queue.offer(new Pair(startNode, ""));
        visited.add(startNode);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; ++i) {
                Pair<TreeNode, String> pair = queue.poll();
                TreeNode node = pair.getKey();
                String step = pair.getValue();
                if (node == destNode) {
                    return step;
                }
                
                TreeNode left = node.left;
                TreeNode right = node.right;
                TreeNode parent = parentMap.get(node);
                
                if (left != null && !visited.contains(left)) {
                    queue.offer(new Pair(left, step + "L"));
                    visited.add(left);
                }
                
                if (right != null && !visited.contains(right)) {
                    queue.offer(new Pair(right, step + "R"));
                    visited.add(right);
                }
                
                if (parent != null && !visited.contains(parent)) {
                    queue.offer(new Pair(parent, step + "U"));
                    visited.add(parent);
                }
            }
        }
        return "";
    }
    
    private void buildParentMap(TreeNode node, TreeNode parent, int startValue, int destValue) {
        if (node == null) return;
        
        if (node.val == startValue) {
            startNode = node;
        } else if (node.val == destValue) {
            destNode = node;
        }
        parentMap.put(node, parent);
        
        buildParentMap(node.left, node, startValue, destValue);
        buildParentMap(node.right, node, startValue, destValue);
    }
    
    /**
     * Solution2: pass
     * step 1: build parent map, and get both startNode and destNode;
     * step 2: find ancestor
     * step 3: find both path from startNode/destNode to ancestor
     * step 4: merge both path
     */
    private String getDirections_usingmap(TreeNode root, int startValue, int destValue) {
        parentMap = new HashMap<>();
        buildParentMap(root, null, startValue, destValue);
        
        TreeNode ancestor = findAncestor(root);
        
        StringBuilder s1 = new StringBuilder();
        TreeNode p = startNode;
        while (p != ancestor) {
            p = parentMap.get(p);
            s1.append('U');
        }
        
        StringBuilder s2 = new StringBuilder();
        p = destNode;
        while (p != ancestor) {
            TreeNode parent = parentMap.get(p);
            if (parent.left == p) {
                s2.append('L');
            } else {
                s2.append('R');
            }
            p = parent;
        }
        
        s1.append(s2.reverse());
        return s1.toString();
    }
    
    private TreeNode findAncestor(TreeNode root) {
        TreeNode p = startNode;
        Set<TreeNode> set = new HashSet<>();
        while (p != null) {
            set.add(p);
            p = parentMap.get(p);
        }
        
        p = destNode;
        while (p != null) {
            if (set.contains(p)) return p;
            p = parentMap.get(p);
        }
        return null;
    }
    
    
        
    /**
     * Solution 3: faster than solution 2
     * step 1: find ancestor
     * step 2: find path from ancestor to each node using dfs
     * step 4: merge both path
     */
    private String getDirections_dfs(TreeNode root, int startValue, int destValue) {
        TreeNode ancestor = findAncestor(root, startValue, destValue);
        
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();
        dfs(ancestor, startValue, s1);
        dfs(ancestor, destValue, s2);
        
        // startNode goes up to ancestor, so all steps are 'U'
        StringBuilder s3 = new StringBuilder();
        for (int i = 0; i < s1.length(); ++i) {
            s3.append('U');
        }
        return s3.append(s2).toString();
    }
    
    private boolean dfs(TreeNode root, int val, StringBuilder sb) {
        if (root == null) return false;
        if (root.val == val) {
            return true;
        }
        
        sb.append('L');
        boolean found = dfs(root.left, val, sb);
        if (found) return found;
        sb.deleteCharAt(sb.length() - 1);
        
        sb.append('R');
        found = dfs(root.right, val, sb);
        if (found) return found;
        sb.deleteCharAt(sb.length() - 1);
        
        return false;
    }
    
    private TreeNode findAncestor(TreeNode root, int startValue, int destValue) {
        if (root == null) return null;
        
        if (root.val == startValue || root.val == destValue) return root;
        
        TreeNode left = findAncestor(root.left, startValue, destValue);
        TreeNode right = findAncestor(root.right, startValue, destValue);
        if (left != null && right != null) return root;
        
        return left != null ? left : right;
    }
    
    /**
     * Solution 4:  good, 很多string的技巧
     * 这题的思路比较巧妙，主要分三步：
     * 1、分别记录从根节点到 startValue 和 destValue 的路径 startPath 和 destPath。
     * 2、然后去除 startPath 和 destPath 的公共前缀。
     * 3、最后将 startPath 全部变成 U，把 startPath 和 destPath 接在一起，就是题目要求的路径了。
     */
    private String getDirections_solution4(TreeNode root, int startValue, int destValue) {
        this.startValue = startValue;
        this.destValue = destValue;
        // 寻找走到 startValue 和 destValue 的方向路径
        traverse(root);
        // 去除两个方向路径的公共前缀
        int p = 0, m = startPath.length(), n = destPath.length();
        while (p < m && p < n
                && startPath.charAt(p) == destPath.charAt(p)) {
            p++;
        }
        startPath = startPath.substring(p);
        destPath = destPath.substring(p);
        // 将走向 startValue 的方向路径全部变成 U
        startPath = "U".repeat(startPath.length());
        // 组合 startPath 和 destPath 就得到了答案
        return startPath + destPath;
    }

    StringBuilder path = new StringBuilder();
    String startPath, destPath;
    int startValue, destValue;

    // 二叉树遍历函数
    void traverse(TreeNode root) {
        if (root == null) {
            return;
        }
        if (root.val == startValue) {
            startPath = path.toString();
        } else if (root.val == destValue) {
            destPath = path.toString();
        }

        // 二叉树遍历框架
        path.append('L');
        traverse(root.left);
        path.deleteCharAt(path.length() - 1);

        path.append('R');
        traverse(root.right);
        path.deleteCharAt(path.length() - 1);
    }
}


