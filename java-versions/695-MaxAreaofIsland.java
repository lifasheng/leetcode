/*
You are given an m x n binary matrix grid. An island is a group of 1's (representing land) connected 4-directionally (horizontal or vertical.) You may assume all four edges of the grid are surrounded by water.

The area of an island is the number of cells with a value 1 in the island.

Return the maximum area of an island in grid. If there is no island, return 0.

Example 1:
Input: grid = [[0,0,1,0,0,0,0,1,0,0,0,0,0],[0,0,0,0,0,0,0,1,1,1,0,0,0],[0,1,1,0,1,0,0,0,0,0,0,0,0],[0,1,0,0,1,1,0,0,1,0,1,0,0],[0,1,0,0,1,1,0,0,1,1,1,0,0],[0,0,0,0,0,0,0,0,0,0,1,0,0],[0,0,0,0,0,0,0,1,1,1,0,0,0],[0,0,0,0,0,0,0,1,1,0,0,0,0]]
Output: 6
Explanation: The answer is not 11, because the island must be connected 4-directionally.

Example 2:
Input: grid = [[0,0,0,0,0,0,0,0]]
Output: 0
 
Constraints:
m == grid.length
n == grid[i].length
1 <= m, n <= 50
grid[i][j] is either 0 or 1.
*/

class Solution {
    private int count;
    public int maxAreaOfIsland(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        boolean[][] visited = new boolean[m][n];
        int result = 0;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    count = 0;
                    visited[i][j] = true;
                    dfs(grid, visited, i, j);
                    result = Math.max(result, count);
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

            if (newI < 0 || newI >= m || newJ < 0 || newJ >= n) {
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





//// without using global count var

class Solution {
    public int maxAreaOfIsland(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        boolean[][] visited = new boolean[m][n];
        int result = 0;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    visited[i][j] = true;
                    int count = dfs(grid, visited, i, j);
                    result = Math.max(result, count);
                }
            }
        }
        return result;
    }

    private static final int[][] directions = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private int dfs(int[][] grid, boolean[][] visited, int i, int j) {
        int m = grid.length;
        int n = grid[0].length;

        int count = 1;
        for (int[] dir : directions) {
            int newI = i + dir[0];
            int newJ = j + dir[1];

            if (newI < 0 || newI >= m || newJ < 0 || newJ >= n) {
                continue;
            }

            if (grid[newI][newJ] == 0 || visited[newI][newJ]) {
                continue;
            }

            visited[newI][newJ] = true;
            count += dfs(grid, visited, newI, newJ);
        }
        return count;
    }
}



