/*
Hard

very very good!

You are given an m x n integer matrix grid where each cell is either 0 (empty) or 1 (obstacle). You can move up, down, left, or right from and to an empty cell in one step.

Return the minimum number of steps to walk from the upper left corner (0, 0) to the lower right corner (m - 1, n - 1) given that you can eliminate at most k obstacles. If it is not possible to find such walk return -1.

 

Example 1:


Input: grid = [[0,0,0],[1,1,0],[0,0,0],[0,1,1],[0,0,0]], k = 1
Output: 6
Explanation: 
The shortest path without eliminating any obstacle is 10.
The shortest path with one obstacle elimination at position (3,2) is 6. Such path is (0,0) -> (0,1) -> (0,2) -> (1,2) -> (2,2) -> (3,2) -> (4,2).
Example 2:


Input: grid = [[0,1,1],[1,1,1],[1,0,0]], k = 1
Output: -1
Explanation: We need to eliminate at least two obstacles to find such a walk.
 

Constraints:

m == grid.length
n == grid[i].length
1 <= m, n <= 40
1 <= k <= m * n
grid[i][j] is either 0 or 1.
grid[0][0] == grid[m - 1][n - 1] == 0
*/

class Solution {
    // https://ithelp.ithome.com.tw/m/articles/10272083
    // 求最短路径用bfs
    // 最短路径相关问题：https://zhuanlan.zhihu.com/p/136183284
    public int shortestPath(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;

        if (k >= m+n-2) return m+n-2;

        int[][] directions = new int[][]{ {-1, 0}, {1, 0}, {0, -1}, {0, 1} };
        
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{0, 0, 0, k}); // 坐标, step, remaink


        // 这里不能用int[]，因为array没有equals方法，达不到去重效果。
        Set<List<Integer>> visited = new HashSet<>();
        visited.add(List.of(0, 0, k));

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; ++i) {
                int[] stat = queue.poll();
                int curR = stat[0];
                int curC = stat[1];
                int step = stat[2];
                int remainK = stat[3];
                // reach the right bottom corner
                if (curR == m - 1 && curC == n - 1) {
                    return step;
                }

                for (int[] dir : directions) {
                    int r = curR + dir[0];
                    int c = curC + dir[1];

                    List<Integer> list = List.of(r, c, remainK);
                    if (r < 0 || r >= m || c < 0 || c >= n || visited.contains(list)) {
                        continue;
                    }

                    if (grid[r][c] == 0) {
                        visited.add(list);
                        queue.offer(new int[]{r, c, step + 1, remainK});
                    } else {
                        if (remainK <= 0) continue;
                        visited.add(list);
                        queue.offer(new int[]{r, c, step + 1, remainK - 1});
                    }
                }
            }
        }

        return -1;
    }
}

/*
// dfs 超时
class Solution {
    int minSteps = Integer.MAX_VALUE;
    boolean skip = false;
    public int shortestPath(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        boolean[] visited = new boolean[m*n];
        List<Integer> path = new ArrayList<>();
        visited[0] = true;
        dfs(grid, k, visited, path, 0, 0);
        return minSteps == Integer.MAX_VALUE ? -1 : minSteps;
    }

    private static final int[][] DIRECTIONS = new int[][] {
        {-1, 0},
        {1, 0},
        {0, -1},
        {0, 1}
    };

    void dfs(int[][] grid, int k, boolean[] visited, List<Integer> path, int i, int j) {
        int m = grid.length;
        int n = grid[0].length;
        if (i == m - 1 && j == n - 1) {
            minSteps = Math.min(minSteps, path.size());
            if (minSteps == (m+n-2)) {
                skip = true;
            }
            return;
        }

        if (skip) return;

        for (int[] dir : DIRECTIONS) {
            int newI = i + dir[0];
            int newJ = j + dir[1];
            int index = newI * n + newJ;

            if (newI < 0 || newI >= m || newJ < 0 || newJ >= n 
            || visited[index]) {
                continue;
            }

            if (grid[newI][newJ] == 0) {
                visited[index] = true;
                path.add(index);
                dfs(grid, k, visited, path, newI, newJ);
                visited[index] = false;
                path.remove(path.size() - 1);
            } else {
                if (k > 0) {
                    visited[index] = true;
                    path.add(index);
                    dfs(grid, k - 1, visited, path, newI, newJ);
                    visited[index] = false;
                    path.remove(path.size() - 1);
                } else {
                    continue;
                }
            }
        }
    }
}
*/


