package org.example;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


// Main class should be named 'Solution' and should not be public.


/*
Write a function that pretty prints binary tree in the format provided in the example. Values in the tree nodes are single characters.
Example of pretty printed tree:
        a. -> level 0
    b       c.  level 1
   d  e   f  g.       2
 h i j k l m n o.     3

 height = 4
 2^0  = 1. 1 + 14. 7 + 1 + 7
 2^1 = 2.  2 + 13. 7 + 1 + 7.    3 + 1 + 3
 2^2 = 4.  4 + 11  7 + 1 + 7.    3 + 1 + 3.  1+1+1
 2^3 = 8.  8 + 7 = 15

-------a------- 7.   2^3 - 1
---b---.---c---      2^2-1
-d-.-e-.-f-.-g-      2^1-1
h.i.j.k.l.m.n.o.     2^0-1
*/

class TreeNode {
    char c;
    TreeNode left;
    TreeNode right;
    public TreeNode(char c, TreeNode left, TreeNode right) {
        this.c = c;
        this.left = left;
        this.right = right;
    }

    public TreeNode(char c) {
        this.c = c;
    }
}

public class App {

    public static void prettyPrintBinaryTree(TreeNode root) {
        List<List<Character>> chars = traverseTree(root);
        int levelNumber = chars.size();
        int maxWidth = (int)Math.pow(2, (levelNumber - 1));
        System.out.println("maxWidth:" + maxWidth);
        //int totalWidth =
        for (List<Character> list : chars) {
            for (char c : list) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }

    private static List<List<Character>> traverseTree(TreeNode root) {
        List<List<Character>> res = new ArrayList<>();
        if (root == null) return res;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Character> level = new ArrayList<>();
            for (int i = 0; i < size; ++i) {
                TreeNode node = queue.poll();
                if (node == null) {
                    level.add('@');
                    continue;
                } else {
                    level.add(node.c);
                }
                if (node.left != null) {
                    queue.offer(node.left);
                } else {
                    queue.offer(null);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                } else {
                    queue.offer(null);
                }
            }
            res.add(level);
        }
        res.remove(res.size() - 1);
        return res;
    }

    public static void main(String[] args) {
        TreeNode bNode = new TreeNode('b');
        TreeNode gNode = new TreeNode('g');
        TreeNode cNode = new TreeNode('c', null, gNode);
        TreeNode aNode = new TreeNode('a', bNode, cNode);

        prettyPrintBinaryTree(aNode);
    }
}


