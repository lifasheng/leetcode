/*
Given a 2D grid consists of 0s (land) and 1s (water).  An island is a maximal 4-directionally connected group of 0s and a closed island is an island totally (all left, top, right, bottom) surrounded by 1s.

Return the number of closed islands.

Example 1:
Input: grid = [[1,1,1,1,1,1,1,0],[1,0,0,0,0,1,1,0],[1,0,1,0,1,1,1,0],[1,0,0,0,0,1,0,1],[1,1,1,1,1,1,1,0]]
Output: 2
Explanation: 
Islands in gray are closed because they are completely surrounded by water (group of 1s).

Example 2:
Input: grid = [[0,0,1,0,0],[0,1,0,1,0],[0,1,1,1,0]]
Output: 1

Example 3:
Input: grid = [[1,1,1,1,1,1,1],
               [1,0,0,0,0,0,1],
               [1,0,1,1,1,0,1],
               [1,0,1,0,1,0,1],
               [1,0,1,1,1,0,1],
               [1,0,0,0,0,0,1],
               [1,1,1,1,1,1,1]]
Output: 2
 
Constraints:
1 <= grid.length, grid[0].length <= 100
0 <= grid[i][j] <=1
*/

class Solution {
    private boolean isClosed;
    public int closedIsland(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        boolean[][] visited = new boolean[m][n];
        int count = 0;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == 0 && !visited[i][j]) {
                    isClosed = true;
                    visited[i][j] = true;
                    dfs(grid, visited, i, j);
                    if (isClosed) {
                        ++ count;
                    }
                }
            }
        }
        return count;
    }

    private static final int[][] directions = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private void dfs(int[][] grid, boolean[][] visited, int i, int j) {
        int m = grid.length;
        int n = grid[0].length;

        for (int[] dir : directions) {
            int newI = i + dir[0];
            int newJ = j + dir[1];

            // 如果到达边界，就不是closed
            if (newI < 0 || newI >= m || newJ < 0 || newJ >= n) {
                isClosed = false;
                continue;
            }

            if (grid[newI][newJ] == 1 || visited[newI][newJ]) {
                continue;
            }

            visited[newI][newJ] = true;
            dfs(grid, visited, newI, newJ);
        }
    }
}



