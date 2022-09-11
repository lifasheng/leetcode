/*
Medium
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
        return numIslands_uf(grid);
    }
    
    // very very good!
    // solution 1: dfs
    private int numIslands_dfs(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        
        // 很多解法是将grid[i][j] 设置成0，这样也可以，只是会改变输入，可以讨论。
        boolean[] visited = new boolean[m * n]; // 这里就不用map了，一个一维数组就可以。
        
        int numOfIslands = 0;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == '1' && !visited[i * n + j]) {
                    ++ numOfIslands;
                    dfs(grid, i, j, visited);
                }
            }
        }
        return numOfIslands;
    }
    
    private void dfs(char[][] grid, int i, int j, boolean[] visited) {
        int m = grid.length;
        int n = grid[0].length;
        
        // 剪枝很重要
        if (!isValid(grid, i, j) || visited[i * n + j]) return;
        visited[i * n + j] = true;
        dfs(grid, i - 1, j, visited);
        dfs(grid, i + 1, j, visited);
        dfs(grid, i, j - 1, visited);
        dfs(grid, i, j + 1, visited);
    }
    
    private boolean isValid(char[][] grid, int i, int j) {
        int m = grid.length;
        int n = grid[0].length;
        if (i < 0 || i >= m || j < 0 || j >= n || grid[i][j] == '0') return false;
        return true;
    }
    
    // solution 2: bfs
    private int numIslands_bfs(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        boolean[] visited = new boolean[m * n]; // 这里就不用map了，一个一维数组就可以。
        
        int numOfIslands = 0;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == '1' && !visited[i * n + j]) {
                    ++ numOfIslands;
                    bfs(grid, i, j, visited);
                }
            }
        }
        return numOfIslands;
    }
    
    private void bfs(char[][] grid, int i, int j, boolean[] visited) {
        int m = grid.length;
        int n = grid[0].length;
        
        Deque<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{i, j});

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int k = 0; k < size; ++k) {
                int[] pair = queue.poll();
                i = pair[0];
                j = pair[1];
                if (!isValid(grid, i, j) || visited[i * n + j]) continue;
                
                visited[i * n + j] = true;
                queue.offer(new int[]{i - 1, j});
                queue.offer(new int[]{i + 1, j});
                queue.offer(new int[]{i, j - 1});
                queue.offer(new int[]{i, j + 1});
            }
        }
    }
    
    // solution 3: union find， very good!
    private int numIslands_uf(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        UnionFind uf = new UnionFind(grid);
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == '0') continue;
                
                // 和周围四个方向上的1进行合并
                if (i - 1 >= 0 && grid[i - 1][j] == '1') {
                    uf.union(i * n + j, (i - 1) * n + j);
                }
                if (i + 1 < m && grid[i + 1][j] == '1') {
                    uf.union(i * n + j, (i + 1) * n + j);
                }
                if (j - 1 >= 0 && grid[i][j - 1] == '1') {
                    uf.union(i * n + j, i * n + j - 1);
                }
                if (j + 1 < n && grid[i][j + 1] == '1') {
                    uf.union(i * n + j, i * n + j + 1);
                }
            }
        }
        return uf.count();
    }
    
    class UnionFind {
        private int count;  // 连通分量个数
        private int[] parent;
        
        // 这里的技巧在于如何初始化这个uf对象
        // 我们只关注‘1’的字符，所以需要传入grid，parent数组还是用一维来表示。
        public UnionFind(char[][] grid) {
            int row = grid.length;
            int col = grid[0].length;
            int n = row * col;
            this.parent = new int[n];
            for (int i = 0; i < row; ++i) {
                for (int j = 0; j < col; ++j) {
                    if (grid[i][j] == '1') {
                        parent[i * col + j] = i * col + j;
                        ++ count;
                    }
                }
            }
        }
        
        public void union(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);
            if (rootI == rootJ) return;
            
            parent[rootI] = rootJ;
            -- count;
        }
        
        public boolean isConnected(int i, int j) {
            return find(i) == find(j);
        }
        
        // path compression
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }
        
        public int count() {
            return count;
        }
    }
}

