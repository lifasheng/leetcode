/*
You are given an m x n binary matrix grid, where 0 represents a sea cell and 1 represents a land cell.

A move consists of walking from one land cell to another adjacent (4-directionally) land cell or walking off the boundary of the grid.

Return the number of land cells in grid for which we cannot walk off the boundary of the grid in any number of moves.


Example 1:
Input: grid = [[0,0,0,0],[1,0,1,0],[0,1,1,0],[0,0,0,0]]
Output: 3
Explanation: There are three 1s that are enclosed by 0s, and one 1 that is not enclosed because its on the boundary.

Example 2:
Input: grid = [[0,1,1,0],[0,0,1,0],[0,0,1,0],[0,0,0,0]]
Output: 0
Explanation: All 1s are either on the boundary or can reach the boundary.

Constraints:
m == grid.length
n == grid[i].length
1 <= m, n <= 500
grid[i][j] is either 0 or 1.
*/

// similar with 1254-NumberofClosedIslands.java 
class Solution {
    private boolean isClosed;
    private int count;
    public int numEnclaves(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        boolean[][] visited = new boolean[m][n];
        int result = 0;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    isClosed = true;
                    count = 0;
                    visited[i][j] = true;
                    dfs(grid, visited, i, j);
                    if (isClosed) {
                        result += count;
                    }
                }
            }
        }
        return result;
    }

    private static final int[][] directions = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private void dfs(int[][] grid, boolean[][] visited, int i, int j) {
        int m = grid.length;
        int n = grid[0].length;
        ++ count;

        for (int[] dir : directions) {
            int newI = i + dir[0];
            int newJ = j + dir[1];

            // 如果到达边界，就不是closed
            if (newI < 0 || newI >= m || newJ < 0 || newJ >= n) {
                isClosed = false;
                continue;
            }

            if (grid[newI][newJ] == 0 || visited[newI][newJ]) {
                continue;
            }

            visited[newI][newJ] = true;
            dfs(grid, visited, newI, newJ);
        }
    }
}


