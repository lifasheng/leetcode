/*
You are given two m x n binary matrices grid1 and grid2 containing only 0's (representing water) and 1's (representing land). An island is a group of 1's connected 4-directionally (horizontal or vertical). Any cells outside of the grid are considered water cells.

An island in grid2 is considered a sub-island if there is an island in grid1 that contains all the cells that make up this island in grid2.

Return the number of islands in grid2 that are considered sub-islands.


Example 1:
Input: grid1 = [[1,1,1,0,0],[0,1,1,1,1],[0,0,0,0,0],[1,0,0,0,0],[1,1,0,1,1]], grid2 = [[1,1,1,0,0],[0,0,1,1,1],[0,1,0,0,0],[1,0,1,1,0],[0,1,0,1,0]]
Output: 3
Explanation: In the picture above, the grid on the left is grid1 and the grid on the right is grid2.
The 1s colored red in grid2 are those considered to be part of a sub-island. There are three sub-islands.

Example 2:
Input: grid1 = [[1,0,1,0,1],[1,1,1,1,1],[0,0,0,0,0],[1,1,1,1,1],[1,0,1,0,1]], grid2 = [[0,0,0,0,0],[1,1,1,1,1],[0,1,0,1,0],[0,1,0,1,0],[1,0,0,0,1]]
Output: 2 
Explanation: In the picture above, the grid on the left is grid1 and the grid on the right is grid2.
The 1s colored red in grid2 are those considered to be part of a sub-island. There are two sub-islands.

Constraints:
m == grid1.length == grid2.length
n == grid1[i].length == grid2[i].length
1 <= m, n <= 500
grid1[i][j] and grid2[i][j] are either 0 or 1.
*/

// https://labuladong.github.io/algo/di-san-zha-24031/bao-li-sou-96f79/yi-wen-mia-4f482/
class Solution {
    /*
    什么情况下 grid2 中的一个岛屿 B 是 grid1 中的一个岛屿 A 的子岛？
    当岛屿 B 中所有陆地在岛屿 A 中也是陆地的时候，岛屿 B 是岛屿 A 的子岛。
    反过来说，如果岛屿 B 中存在一片陆地，在岛屿 A 的对应位置是海水，那么岛屿 B 就不是岛屿 A 的子岛。
    那么，我们只要遍历 grid2 中的所有岛屿，把那些不可能是子岛的岛屿排除掉，剩下的就是子岛。
    */
    private boolean isSubIsland;
    public int countSubIslands(int[][] grid1, int[][] grid2) {
        int m = grid2.length;
        int n = grid2[0].length;

        boolean[][] visited = new boolean[m][n];
        int count = 0;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid2[i][j] == 1 && !visited[i][j]) {
                    visited[i][j] = true;
                    isSubIsland = true;
                    dfs(grid1, grid2, visited, i, j);
                    if (isSubIsland) {
                        ++ count;
                    }
                }
            }
        }
        return count;
    }

    private static final int[][] directions = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private void dfs(int[][] grid1, int[][] grid2, boolean[][] visited, int i, int j) {
        int m = grid2.length;
        int n = grid2[0].length;

        if (grid1[i][j] == 0) {
            isSubIsland = false;

            // 这里不能直接返回，而是要访问完当前整个island，不然就不能处理下面这种情况。
            // 1 1 0                 1  1  1
            //   1 1 1                  1  1  1  
            // return;
        }

        for (int[] dir : directions) {
            int newI = i + dir[0];
            int newJ = j + dir[1];

            if (newI < 0 || newI >= m || newJ < 0 || newJ >= n) {
                continue;
            }

            if (grid2[newI][newJ] == 0 || visited[newI][newJ]) {
                continue;
            }

            visited[newI][newJ] = true;
            dfs(grid1, grid2, visited, newI, newJ);
        }
    }
}


/*
// Time Limit Exceeded
class Solution {
    public int countSubIslands(int[][] grid1, int[][] grid2) {
        List<Set<Integer>> islandCells1 = getIslands(grid1);
        List<Set<Integer>> islandCells2 = getIslands(grid2);
        int count = 0;
        for (int i = 0; i < islandCells2.size(); ++i) {
            Set<Integer> set2 = islandCells2.get(i);
            for (int j = 0; j < islandCells1.size(); ++j) {
                Set<Integer> set1 = islandCells1.get(j);
                if (set1.containsAll(set2)) {
                    ++ count;
                }
            }
        }
        return count;
    }

    private List<Set<Integer>> getIslands(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        boolean[][] visited = new boolean[m][n];
        List<Set<Integer>> res = new ArrayList<>();
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    visited[i][j] = true;
                    Set<Integer> islandCells = new HashSet<>();
                    dfs(grid, visited, islandCells, i, j);
                    res.add(islandCells);
                }
            }
        }
        return res;
    }

    private static final int[][] directions = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private void dfs(int[][] grid, boolean[][] visited, Set<Integer> islandCells, int i, int j) {
        int m = grid.length;
        int n = grid[0].length;

        islandCells.add(i*n+j);

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
            dfs(grid, visited, islandCells, newI, newJ);
        }
    }
}
*/

