/*
Given an m x n 2D binary grid grid which represents a map of '1's (land) and '0's (water), return the number of islands.

An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.

Example 1:
Input: grid = [
  ["1","1","1","1","0"],
  ["1","1","0","1","0"],
  ["1","1","0","0","0"],
  ["0","0","0","0","0"]
]
Output: 1

Example 2:
Input: grid = [
  ["1","1","0","0","0"],
  ["1","1","0","0","0"],
  ["0","0","1","0","0"],
  ["0","0","0","1","1"]
]
Output: 3
 
Constraints:
m == grid.length
n == grid[i].length
1 <= m, n <= 300
grid[i][j] is '0' or '1'.
*/

class Solution {
    public int numIslands(char[][] grid) {
        return numIslands_dfs(grid);
    }

    private static final int[][] DIRECTIONS = new int[][] {
        {-1, 0},
        {1, 0},
        {0, -1},
        {0, 1}
    };

    private int numIslands_dfs(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        boolean[] visited = new boolean[m*n];
        int count = 0;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == '1' && !visited[i*n+j]) {
                    ++ count;
                    dfs(grid, visited, i, j);
                }
            }
        }
        return count;
    }

    private void dfs(char[][] grid, boolean[] visited, int i, int j) {
        int m = grid.length;
        int n = grid[0].length;

        visited[i*n+j] = true;

        for (int[] dir : DIRECTIONS) {
            int newI = i + dir[0];
            int newJ = j + dir[1];

            if (newI < 0 || newI >= m || newJ < 0 || newJ >= n
            || grid[newI][newJ] != '1'
            || visited[newI * n + newJ]) {
                continue;
            }

            dfs(grid, visited, newI, newJ);
        }
    }


    private int numIslands_bfs(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        boolean[] visited = new boolean[m*n];
        int count = 0;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == '1' && !visited[i*n+j]) {
                    ++ count;
                    bfs(grid, visited, i, j);
                }
            }
        }
        return count;
    }

    private void bfs(char[][] grid, boolean[] visited, int i, int j) {
        int m = grid.length;
        int n = grid[0].length;

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{i, j});
        visited[i*n+j] = true;
        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            i = pos[0];
            j = pos[1];

            for (int[] dir : DIRECTIONS) {
                int newI = i + dir[0];
                int newJ = j + dir[1];

                if (newI < 0 || newI >= m || newJ < 0 || newJ >= n
                || grid[newI][newJ] != '1'
                || visited[newI * n + newJ]) {
                    continue;
                }

                visited[newI*n+newJ] = true;
                queue.offer(new int[]{newI, newJ});
            }
        }
    }
}



