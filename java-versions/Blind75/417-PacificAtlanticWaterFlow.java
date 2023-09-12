/*
very very good!

There is an m x n rectangular island that borders both the Pacific Ocean and Atlantic Ocean. The Pacific Ocean touches the island's left and top edges, and the Atlantic Ocean touches the island's right and bottom edges.

The island is partitioned into a grid of square cells. You are given an m x n integer matrix heights where heights[r][c] represents the height above sea level of the cell at coordinate (r, c).

The island receives a lot of rain, and the rain water can flow to neighboring cells directly north, south, east, and west if the neighboring cell's height is less than or equal to the current cell's height. Water can flow from any cell adjacent to an ocean into the ocean.

Return a 2D list of grid coordinates result where result[i] = [ri, ci] denotes that rain water can flow from cell (ri, ci) to both the Pacific and Atlantic oceans.

Example 1:
Input: heights = [[1,2,2,3,5],[3,2,3,4,4],[2,4,5,3,1],[6,7,1,4,5],[5,1,1,2,4]]
Output: [[0,4],[1,3],[1,4],[2,2],[3,0],[3,1],[4,0]]
Explanation: The following cells can flow to the Pacific and Atlantic oceans, as shown below:
[0,4]: [0,4] -> Pacific Ocean 
       [0,4] -> Atlantic Ocean
[1,3]: [1,3] -> [0,3] -> Pacific Ocean 
       [1,3] -> [1,4] -> Atlantic Ocean
[1,4]: [1,4] -> [1,3] -> [0,3] -> Pacific Ocean 
       [1,4] -> Atlantic Ocean
[2,2]: [2,2] -> [1,2] -> [0,2] -> Pacific Ocean 
       [2,2] -> [2,3] -> [2,4] -> Atlantic Ocean
[3,0]: [3,0] -> Pacific Ocean 
       [3,0] -> [4,0] -> Atlantic Ocean
[3,1]: [3,1] -> [3,0] -> Pacific Ocean 
       [3,1] -> [4,1] -> Atlantic Ocean
[4,0]: [4,0] -> Pacific Ocean 
       [4,0] -> Atlantic Ocean
Note that there are other possible paths for these cells to flow to the Pacific and Atlantic oceans.

Example 2:
Input: heights = [[1]]
Output: [[0,0]]
Explanation: The water can flow from the only cell to the Pacific and Atlantic oceans.
 
Constraints:
m == heights.length
n == heights[r].length
1 <= m, n <= 200
0 <= heights[r][c] <= 105
*/

class Solution {

    private static final int[][] DIRECTIONS = new int[][] {
        {-1, 0},
        {1, 0},
        {0, -1},
        {0, 1}
    };

    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        return pacificAtlantic_dfs(heights);
    }


    /*
     思路： 从边上往中间search，看哪些点能够到达。
    */
    public List<List<Integer>> pacificAtlantic_bfs(int[][] heights) {
        int m = heights.length;
        int n = heights[0].length;

        boolean[][] pacific = new boolean[m][n];
        boolean[][] atlantic = new boolean[m][n];

        Queue<int[]> pacificQueue = new LinkedList<>();
        Queue<int[]> atlanticQueue = new LinkedList<>();

        // 上下两行
        for (int j = 0; j < n; ++j) {
            pacificQueue.offer(new int[]{0, j});
            pacific[0][j] = true;

            atlanticQueue.offer(new int[]{m-1, j});
            atlantic[m-1][j] = true;
        }

        // 左右两列
        for (int i = 0; i < m; ++i) {
            pacificQueue.offer(new int[]{i, 0});
            pacific[i][0] = true;

            atlanticQueue.offer(new int[]{i, n -1});
            atlantic[i][n-1] = true;
        }

        bfs(heights, pacific, pacificQueue);
        bfs(heights, atlantic, atlanticQueue);

        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (pacific[i][j] && atlantic[i][j]) {
                    result.add(List.of(i, j));
                }
            }
        }
        return result;

    }

    private void bfs(int[][] heights, boolean[][] visited, Queue<int[]> queue) {
        int m = heights.length;
        int n = heights[0].length;

        while(!queue.isEmpty()) {
            int[] pos = queue.poll();
            int i = pos[0];
            int j = pos[1];

            for (int[] dir : DIRECTIONS) {
                int newI = i + dir[0];
                int newJ = j + dir[1];
                if (newI < 0 || newI >= m || newJ < 0 || newJ >= n 
                || visited[newI][newJ] 
                || heights[newI][newJ] < heights[i][j]) {
                    continue;
                }

                visited[newI][newJ] = true;
                queue.offer(new int[]{newI, newJ});
            }
        }
    }


    // 思路和bfs类似，从四边向中间递归搜索
    // Time: O((m+n) * mn)
    public List<List<Integer>> pacificAtlantic_dfs(int[][] heights) {
        int m = heights.length;
        int n = heights[0].length;

        boolean[][] pacific = new boolean[m][n];
        boolean[][] atlantic = new boolean[m][n];

        for (int i = 0; i < m; ++i) {
            pacific[i][0] = true;
            atlantic[i][n-1] = true;
            dfs(heights, pacific, i, 0);
            dfs(heights, atlantic, i, n-1);
        }

        for (int j = 0; j < n; ++j) {
            pacific[0][j] = true;
            atlantic[m-1][j] = true;
            dfs(heights, pacific, 0, j);
            dfs(heights, atlantic, m-1, j);
        }

        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (pacific[i][j] && atlantic[i][j]) {
                    result.add(List.of(i, j));
                }
            }
        }
        return result;
    }

    public void dfs(int[][] heights, boolean[][] visited, int i, int j) {
        int m = heights.length;
        int n = heights[0].length;

        for (int[] dir : DIRECTIONS) {
            int newI = i + dir[0];
            int newJ = j + dir[1];
            if (newI < 0 || newI >= m || newJ < 0 || newJ >= n 
            || visited[newI][newJ] 
            || heights[newI][newJ] < heights[i][j]) {
                continue;
            }

            visited[newI][newJ] = true;
            dfs(heights, visited, newI, newJ);
        }
    }
}


