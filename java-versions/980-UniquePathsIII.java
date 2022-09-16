/*
Hard
You are given an m x n integer array grid where grid[i][j] could be:

1 representing the starting square. There is exactly one starting square.
2 representing the ending square. There is exactly one ending square.
0 representing empty squares we can walk over.
-1 representing obstacles that we cannot walk over.
Return the number of 4-directional walks from the starting square to the ending square, that walk over every non-obstacle square exactly once.

Example 1:
Input: grid = [[1,0,0,0],[0,0,0,0],[0,0,2,-1]]
Output: 2
Explanation: We have the following two paths: 
1. (0,0),(0,1),(0,2),(0,3),(1,3),(1,2),(1,1),(1,0),(2,0),(2,1),(2,2)
2. (0,0),(1,0),(2,0),(2,1),(1,1),(0,1),(0,2),(0,3),(1,3),(1,2),(2,2)

Example 2:
Input: grid = [[1,0,0,0],[0,0,0,0],[0,0,0,2]]
Output: 4
Explanation: We have the following four paths: 
1. (0,0),(0,1),(0,2),(0,3),(1,3),(1,2),(1,1),(1,0),(2,0),(2,1),(2,2),(2,3)
2. (0,0),(0,1),(1,1),(1,0),(2,0),(2,1),(2,2),(1,2),(0,2),(0,3),(1,3),(2,3)
3. (0,0),(1,0),(2,0),(2,1),(2,2),(1,2),(1,1),(0,1),(0,2),(0,3),(1,3),(2,3)
4. (0,0),(1,0),(2,0),(2,1),(1,1),(0,1),(0,2),(0,3),(1,3),(1,2),(2,2),(2,3)

Example 3:
Input: grid = [[0,1],[2,0]]
Output: 0
Explanation: There is no path that walks over every empty square exactly once.
Note that the starting and ending square can be anywhere in the grid.

Constraints:
m == grid.length
n == grid[i].length
1 <= m, n <= 20
1 <= m * n <= 20
-1 <= grid[i][j] <= 2
There is exactly one starting cell and one ending cell.
*/

class Solution {
    
    private static final int[][] DIRECTIONS = {
        {-1, 0},
        {1, 0},
        {0, -1},
        {0, 1}
    };
    
    // Time: O(3^N), Space: O(N), very good!
    public int uniquePathsIII(int[][] grid) {
        return uniquePathsIII_backtrack(grid);
    }
    
    int pathCount = 0;
    Set<Pair<Integer, Integer>> visited;
    Pair<Integer, Integer> end;
    int emptySquareCount = 0;
    private int uniquePathsIII_backtrack(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        visited = new HashSet<>();
        
        Pair<Integer, Integer> start = null;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == 1) {
                    start = new Pair(i, j);
                } else if (grid[i][j] == 2) {
                    end = new Pair(i, j);
                } else if (grid[i][j] == 0) {
                    ++ emptySquareCount;
                }
            }
        }
        
        backtrack(grid, start);
        return pathCount;
    }
    
    private void backtrack(int[][] grid, Pair<Integer, Integer> start) {
        if (start.equals(end)) { // here, use equals, not ==
            if (visited.size() == emptySquareCount + 1) { // + 1 is for end position
                ++ pathCount;
            }
            return;
        }
        
        for (int[] direction : DIRECTIONS) {
            int newRow = start.getKey() + direction[0];
            int newCol = start.getValue() + direction[1];
            
            Pair<Integer, Integer> pair = new Pair(newRow, newCol);
            if (newRow < 0 || newCol < 0 || newRow >= grid.length || newCol >= grid[0].length
               || grid[newRow][newCol] == -1 || grid[newRow][newCol] == 1 || visited.contains(pair)) {
                continue;
            }
            
            visited.add(pair);
            backtrack(grid, pair);
            visited.remove(pair);
        }
    }
}

