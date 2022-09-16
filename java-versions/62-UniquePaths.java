/*
Medium
There is a robot on an m x n grid. The robot is initially located at the top-left corner (i.e., grid[0][0]). The robot tries to move to the bottom-right corner (i.e., grid[m - 1][n - 1]). The robot can only move either down or right at any point in time.

Given the two integers m and n, return the number of possible unique paths that the robot can take to reach the bottom-right corner.

The test cases are generated so that the answer will be less than or equal to 2 * 109.

Example 1:
Input: m = 3, n = 7
Output: 28

Example 2:
Input: m = 3, n = 2
Output: 3
Explanation: From the top-left corner, there are a total of 3 ways to reach the bottom-right corner:
1. Right -> Down -> Down
2. Down -> Down -> Right
3. Down -> Right -> Down
 
Constraints:
1 <= m, n <= 100
*/

class Solution {
    public int uniquePaths(int m, int n) {
        return uniquePaths3(m, n);
    }
    
    // 递归 + 备忘录法 O(M * N)
    private Integer[][] memo;
    private int uniquePaths1(int m, int n) {
        memo = new Integer[m+1][n+1];
        return helper(m, n);
    }
    
    private int helper(int m, int n) {
        if (m < 0 || n < 0) return 0;
        if (m == 1 && n == 1) return 1;
        
        if (memo[m][n] == null) {
            memo[m][n] = helper(m - 1, n) + helper(m, n - 1);
        }
        
        return memo[m][n];
    }
    
    // dp: O(M * N), space: O(M * N)
    private int uniquePaths2(int m, int n) {
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                dp[i][j] = (i == 0 || j == 0) ? 1 : dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m-1][n-1];
    }
    
    // dp: O(M * N), space: O(N)
    private int uniquePaths3(int m, int n) {
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                // dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                // 左边的dp[j] 表示更新后的dp[j]，与公式中的dp[i][j]对应
                // 右边边的dp[j] 表示老的dp[j]，与公式中的dp[i-1][j]对应
                dp[j] = dp[j] + dp[j - 1];
            }
        }
        return dp[n - 1];
    }
}

