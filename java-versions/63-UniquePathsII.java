/*
Medium

You are given an m x n integer array grid. There is a robot initially located at the top-left corner (i.e., grid[0][0]). The robot tries to move to the bottom-right corner (i.e., grid[m-1][n-1]). The robot can only move either down or right at any point in time.

An obstacle and space are marked as 1 or 0 respectively in grid. A path that the robot takes cannot include any square that is an obstacle.

Return the number of possible unique paths that the robot can take to reach the bottom-right corner.

The testcases are generated so that the answer will be less than or equal to 2 * 109.

Example 1:
Input: obstacleGrid = [[0,0,0],[0,1,0],[0,0,0]]
Output: 2
Explanation: There is one obstacle in the middle of the 3x3 grid above.
There are two ways to reach the bottom-right corner:
1. Right -> Right -> Down -> Down
2. Down -> Down -> Right -> Right

Example 2:
Input: obstacleGrid = [[0,1],[0,0]]
Output: 1

Constraints:
m == obstacleGrid.length
n == obstacleGrid[i].length
1 <= m, n <= 100
obstacleGrid[i][j] is 0 or 1.
*/

class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        return uniquePathsWithObstacles_dp(obstacleGrid);
    }
    
    // solution 1: 递归 + 备忘录
    private int uniquePathsWithObstacles_memo(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        Integer[][] memo = new Integer[m+1][n+1];
        return helper(obstacleGrid, memo, m, n);
    }
    
    private int helper(int[][] obstacleGrid, Integer[][] memo, int i, int j) {
        if (i == 0 || j == 0) return 0;
        if (obstacleGrid[i-1][j-1] == 1) return 0;
        if (i == 1 && j == 1) return 1;
        if (memo[i][j] == null) {
            memo[i][j] = helper(obstacleGrid, memo, i - 1, j) + helper(obstacleGrid, memo, i, j - 1);
        }
        return memo[i][j];
    }
    
    // solution 2: dp
    // test case: [[1,0]], [[0]], [[1]]
    private int uniquePathsWithObstacles_dp(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        
        if (obstacleGrid[0][0] == 1) return 0;
        
        //这里要稍微特殊处理一下第一行和第一列
        Integer[][] dp = new Integer[m][n];
        dp[0][0] = 1;
        for (int i = 1; i < m; ++i) {
            dp[i][0] = (obstacleGrid[i][0] == 0 && dp[i - 1][0] == 1) ? 1 : 0;
        }
        
        for (int j = 1; j < n; ++j) {
            dp[0][j] = (obstacleGrid[0][j] == 0 && dp[0][j - 1] == 1) ? 1 : 0;
        }
        
        for (int i = 1; i < m; ++i) {
            for (int j = 1; j < n; ++j) {
                if (obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                } else {
                    dp[i][j] = dp[i-1][j] + dp[i][j-1];
                }
            }
        }
        return dp[m-1][n-1];
    }
}

