/*
Given an integer n, return the number of structurally unique BST's (binary search trees) which has exactly n nodes of unique values from 1 to n.

Example 1:
Input: n = 3
Output: 5

Example 2:
Input: n = 1
Output: 1
*/

class Solution {
    private int[][] memo;
    
    public int numTrees(int n) {
        return numTrees_2(n);
    }
    
    // solution 1: use memo, two dimension array
    private int numTrees_1(int n) {
        this.memo = new int[n+1][n+1];
        return dp(1, n);
    }
    
    private int dp(int low, int high) {
        if (low >= high) return 1;
        if (memo[low][high] != 0) return memo[low][high];
        
        int count = 0;
        for (int mid = low; mid <= high; ++mid) {
            count += dp(low, mid -1 ) * dp(mid + 1, high);
        }
        memo[low][high] = count;
        return count;
    }
    
    // G(n) = sum (i: 1->n) (G(i-1) * G(n-i));
    // For G(n), it does not matter the content of the sequence, but the length of the sequence. Therefore, F(3,7)=G(2)⋅G(4).
    private int numTrees_2(int n) {
        int[] g = new int[n+1];
        
        g[0] = 1;
        g[1] = 1;
        
        int count = 0;
        for (int i = 2; i <= n; ++i) {
            for (int j = 1; j <= i; ++j) {
                g[i] += g[j-1] * g[i-j];
            }
        }
        return g[n];
    }
}


