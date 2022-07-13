/*
Given an n x n binary matrix grid, return the length of the shortest clear path in the matrix. If there is no clear path, return -1.

A clear path in a binary matrix is a path from the top-left cell (i.e., (0, 0)) to the bottom-right cell (i.e., (n - 1, n - 1)) such that:

All the visited cells of the path are 0.
All the adjacent cells of the path are 8-directionally connected (i.e., they are different and they share an edge or a corner).
The length of a clear path is the number of visited cells of this path.

Example 1:
Input: grid = [[0,1],[1,0]]
Output: 2

Example 2:
Input: grid = [[0,0,0],[1,1,0],[1,1,0]]
Output: 4

Example 3:
Input: grid = [[1,0,0],[1,1,0],[1,1,0]]
Output: -1

Constraints:
n == grid.length
n == grid[i].length
1 <= n <= 100
grid[i][j] is 0 or 1
*/

class Solution {
    public int shortestPathBinaryMatrix(int[][] grid) {
        return shortestPathBinaryMatrix_bfs(grid);
    }
    
    // Solution 1: DFS will timeout
    private int minPath;
    private Set<Pair<Integer, Integer>> visited;
    private boolean found;
    private int shortestPathBinaryMatrix_dfs(int[][] grid) {
        if (grid[0][0] == 1) return -1;
        
        this.minPath = Integer.MAX_VALUE;
        this.visited = new HashSet<>();
        this.found = false;
        visited.add(new Pair(0, 0));
        dfs(grid, 0, 0, 1);
        return found ? minPath : -1;
    }
    
    private void dfs(int[][] grid, int row, int col, int path) {
        int n = grid.length;
        
        if (row == n - 1 && col == n - 1) {
            minPath = Math.min(minPath, path);
            found = true;
            return;
        }
        
        for (int i = -1; i <= 1; ++i) {
            if (row + i >= n || row + i < 0) continue;
            
            for (int j = -1; j <= 1; ++j) {
                int newRow = row + i, newCol = col + j;
                if ((i == 0 && j == 0)
                    || newCol >= n || newCol < 0 
                    || grid[newRow][newCol] == 1) {
                    continue;
                }

                Pair pair = new Pair(newRow, newCol);
                if (!visited.contains(pair)) {
                    visited.add(pair);
                    dfs(grid, newRow, newCol, path + 1);
                    visited.remove(pair);
                }
            }
        }
    }
    
    
    // Solution 2: bfs
    private int shortestPathBinaryMatrix_bfs(int[][] grid) {
        if (grid[0][0] == 1) return -1;
        
        int n = grid.length;
        
        Deque<Pair<Integer, Integer>> queue = new ArrayDeque<>();
        Set<Pair<Integer, Integer>> visited = new HashSet<>();
        
        queue.offer(new Pair(0, 0));
        int minPath = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            ++minPath;
            for (int m = 0; m < size; ++m) {
                Pair<Integer, Integer> pair = queue.poll();
                int row = pair.getKey();
                int col = pair.getValue();
                if (row == n-1 && col == n-1) return minPath;
                for (int i = -1; i <= 1; ++i) {
                    for (int j = -1; j <= 1; ++j) {
                        int newRow = row + i, newCol = col + j;
                        if ((i == 0 && j == 0)
                            || newRow >= n || newRow < 0
                            || newCol >= n || newCol < 0 
                            || grid[newRow][newCol] == 1) {
                            continue;
                        }
                        Pair newPair = new Pair(newRow, newCol);
                        if (!visited.contains(newPair)) {
                            visited.add(newPair);
                            queue.offer(newPair);
                        }
                    }
                }
            }
        }
        
        return -1;
    }
}


