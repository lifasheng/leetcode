/*
You have n binary tree nodes numbered from 0 to n - 1 where node i has two children leftChild[i] and rightChild[i], return true if and only if all the given nodes form exactly one valid binary tree.

If node i has no left child then leftChild[i] will equal -1, similarly for the right child.
Note that the nodes have no values and that we only use the node numbers in this problem.

Example 1:
Input: n = 4, leftChild = [1,-1,3,-1], rightChild = [2,-1,-1,-1]
Output: true

Example 2:
Input: n = 4, leftChild = [1,-1,3,-1], rightChild = [2,3,-1,-1]
Output: false

Example 3:
Input: n = 2, leftChild = [1,0], rightChild = [-1,-1]
Output: false
*/


// TODO: understand more
class Solution {
  class UF {
    int[] parents;
    int size;
    UF(int n) {
      parents = new int[n];
      size = n;
      Arrays.fill(parents, -1);
    }
    
    int find(int x) {
      if (parents[x] == -1) {
        return x;
      }
      return parents[x] = find(parents[x]);
    }

    boolean union(int a, int b) {
      int pA = find(a), pB = find(b);
      if (pA == pB) {
        return false;
      }
      parents[pA] = pB;
      size--;
      return true;
    }

    boolean connected() {
      return size == 1;
    }
  }

  public boolean validateBinaryTreeNodes(int n, int[] leftChild, int[] rightChild) {
    UF uf = new UF(n);
    int[] indeg = new int[n];
    for (int i = 0; i < n; i++) {
      int l = leftChild[i], r = rightChild[i];
      if (l != -1) {
        /**
         * i: parent node
         * l: left child node
         * if i and l are already connected or the in degree of l is already 1
         */
        if (!uf.union(i, l) || ++indeg[l] > 1) {
          return false;
        }
      }
      if (r != -1) {
        // Same thing for parent node and the right child node
        if (!uf.union(i, r) || ++indeg[r] > 1) {
          return false;
        }
      }
    }
    return uf.connected();
  }
}


