/*
Construct a tree from string, which is usually leetcode style.
*/
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    public TreeNode() {
    }

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public static TreeNode buildTree(Integer[] integers) {
        if (integers.length == 0) return null;
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode root = new TreeNode(integers[0]);
        int index = 1;
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (index < integers.length) {
                if (integers[index] != null) {
                    node.left = new TreeNode(integers[index]);
                    queue.add(node.left);
                }
                index ++;
            }
            if (index < integers.length) {
                if (integers[index] != null) {
                    node.right = new TreeNode(integers[index]);
                    queue.add(node.right);
                }
                index ++;
            }
            if (index >= integers.length) {
                break;
            }
        }
        return root;
    }

    public static void printTree(TreeNode root) {
        if (root != null) {
            System.out.print(root.val +  " ");
            printTree(root.left);
            printTree(root.right);
        }
    }

    public static void main(String[] args) {
        // TreeNode root = TreeNode.buildTree(new Integer[]{1,1,1,1,1,1,1,null,null,null,1,null,null,null,null,2,2,2,2,2,2,2,null,2,null,null,2,null,2});
        TreeNode root = TreeNode.buildTree(new Integer[]{1,2,3,4,5});

        TreeNode.printTree(root);
        System.out.println();

        Solution2 s = new Solution2();
        System.out.println(s.widthOfBinaryTree(root));
    }
}

