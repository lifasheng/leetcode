/*
In an infinite binary tree where every node has two children, the nodes are labelled in row order.

In the odd numbered rows (ie., the first, third, fifth,...), the labelling is left to right, while in the even numbered rows (second, fourth, sixth,...), the labelling is right to left.

Given the label of a node in this tree, return the labels in the path from the root of the tree to the node with that label.

Example 1:
Input: label = 14
Output: [1,3,4,14]

Example 2:
Input: label = 26
Output: [1,2,6,10,26]

Constraints:
1 <= label <= 10^6
*/


class Solution {
    public List<Integer> pathInZigZagTree(int label) {
        return pathInZigZagTree_2(label);
    }
    
    // Solution 1: naive, but still fast
    private List<Integer> pathInZigZagTree_1(int label) {
        LinkedList<Integer> list = new LinkedList<>();
        
        int level = findLevel(label);
        int index = indexOfCurrentLevel(label);
        for (int i = level; i >= 0; i--) {
            int number = findNumberByLevelAndIndex(i, index);
            list.addFirst(number);
            index = index / 2; // parent's index in upper level
        }
        return list;
    }
    
    // please note this index is alwasy from left to right in each level
    private int findNumberByLevelAndIndex(int level, int index) {
        int startNumber = startNumberOfCurrentLevel(level);
        if (isEven(level)) {
            int size = sizeOfCurrentLevel(level);
            return size - 1 + startNumber - index;
        } else {
            return startNumber + index;
        }
    }
    
    private int findLevel(int label) {
        return (int)(Math.log(label)/Math.log(2));
    }
    
    private int sizeOfCurrentLevel(int level) {
        return (int)(Math.pow(2, level));
    }
    
    // level start from 0
    private boolean isEven(int level) {
        return (level + 1) % 2 == 0;
    }
    
    private int startNumberOfCurrentLevel(int level) {
        return sizeOfCurrentLevel(level);
    }
    
    // index in each level, always from left to right
    private int indexOfCurrentLevel(int label) {
        int level = findLevel(label);
        int size = sizeOfCurrentLevel(level);
        int startNumber = startNumberOfCurrentLevel(level);
        int index = label - startNumber;
        if (isEven(level)) {
            index = size - 1 - index;
        }
        return index;
    }
    
    // Solution 2:
    /*
    如果我想求到达某一个 label 节点的路径，那么我一直对 label 除以 2 就行了（忽略余数）。

    你比如我想求到达 13 的路径，就是 13, 6, 3, 1，然后反转一下就行了。
    
    现在虽然是之字形排列，但稍加修改就可以适应这个变化：
    label = range[1] - (label - range[0]);
    */
    private List<Integer> pathInZigZagTree_2(int label) {
        LinkedList<Integer> list = new LinkedList<>();
        
        while (label >= 1) {
            list.addFirst(label);
            label = label / 2;

            int level = findLevel(label);
            int[] range = getLevelRange(level);
            // 由于之字形分布，根据上层的节点取值范围，修正父节点
            label = range[1] - (label - range[0]);
        }
        return list;
    }
    // 获取第 n 层节点的取值范围
    private int[] getLevelRange(int n) {
        int p = (int) Math.pow(2, n);
        return new int[]{p, 2 * p - 1};
    }
}


