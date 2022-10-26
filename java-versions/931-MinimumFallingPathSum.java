/*
Medium

Given an n x n array of integers matrix, return the minimum sum of any falling path through matrix.
A falling path starts at any element in the first row and chooses the element in the next row that is either directly below or diagonally left/right. Specifically, the next element from position (row, col) will be (row + 1, col - 1), (row + 1, col), or (row + 1, col + 1).

Example 1:
Input: matrix = [[2,1,3],[6,5,4],[7,8,9]]
Output: 13
Explanation: There are two falling paths with a minimum sum as shown.

Example 2:
Input: matrix = [[-19,57],[-40,-5]]
Output: -59
Explanation: The falling path with a minimum sum is shown.

Constraints:
n == matrix.length == matrix[i].length
1 <= n <= 100
-100 <= matrix[i][j] <= 100
*/

class Solution {
    
    public int minFallingPathSum(int[][] matrix) {
        return minFallingPathSum_dp(matrix);
    }
    
    // dp[i][j] = min(dp[i+1][j-1], dp[i+1][j], dp[i+1][j+1]) + matrix[i][j]
    public int minFallingPathSum_dp(int[][] matrix) {
        int n = matrix.length;
        int[][] dp = new int[n][n];
        for (int j = 0; j < n; ++j) {
            dp[n - 1][j] = matrix[n - 1][j];
        }
        for (int i = n - 2; i >= 0; --i) {
            for (int j = 0; j < n; ++j) {
                if (j == 0) {
                    dp[i][j] = Math.min(dp[i + 1][j], dp[i + 1][j + 1]) + matrix[i][j];
                } else if (j == n - 1) {
                    dp[i][j] = Math.min(dp[i + 1][j - 1], dp[i + 1][j]) + matrix[i][j];
                } else {
                    dp[i][j] = Math.min(Math.min(dp[i + 1][j - 1], dp[i + 1][j]), dp[i + 1][j + 1]) + matrix[i][j];
                }
            }
        }
        
        int res = Integer.MAX_VALUE;
        for (int j = 0; j < n; ++j) {
            res = Math.min(res, dp[0][j]);
        }
        return res;
    }
    
    public int minFallingPathSum_memo(int[][] matrix) {
        int n = matrix.length;
        int[][] memo = new int[n][n];
        
        for (int[] arr: memo) {
            Arrays.fill(arr, Integer.MAX_VALUE);
        }
        
        int minSum = Integer.MAX_VALUE;
        for (int j = 0; j < n; ++j) {
            minSum = Math.min(minSum, helper(matrix, 0, j, memo));
        }
        return minSum;
    }
    
    private int helper(int[][] matrix, int i, int j, int[][] memo) {
        int n = matrix.length;
        if (i < 0 || j < 0 || i >= n || j >= n) {
            return Integer.MAX_VALUE;
        }
        
        if (memo[i][j] != Integer.MAX_VALUE) {
            return memo[i][j];
        }
        
        int leftBottom = helper(matrix, i + 1, j - 1, memo);
        int bottom = helper(matrix, i + 1, j, memo);
        int rightBottom = helper(matrix, i + 1, j + 1, memo);
        int min = Math.min(leftBottom, Math.min(bottom, rightBottom));
        memo[i][j] = matrix[i][j] + ((min == Integer.MAX_VALUE) ? 0 : min);
        return memo[i][j];
    }
}

