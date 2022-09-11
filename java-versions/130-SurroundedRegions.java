/*
Medium

Given an m x n matrix board containing 'X' and 'O', capture all regions that are 4-directionally surrounded by 'X'.

A region is captured by flipping all 'O's into 'X's in that surrounded region.

Example 1:
Input: board = [["X","X","X","X"],["X","O","O","X"],["X","X","O","X"],["X","O","X","X"]]
Output: [["X","X","X","X"],["X","X","X","X"],["X","X","X","X"],["X","O","X","X"]]
Explanation: Notice that an 'O' should not be flipped if:
- It is on the border, or
- It is adjacent to an 'O' that should not be flipped.
The bottom 'O' is on the border, so it is not flipped.
The other three 'O' form a surrounded region, so they are flipped.

Example 2:
Input: board = [["X"]]
Output: [["X"]]
 
Constraints:
m == board.length
n == board[i].length
1 <= m, n <= 200
board[i][j] is 'X' or 'O'.
*/

class Solution {
    private static final char MARKER = '@';
    
    // very good!
    public void solve(char[][] board) {
        solve_bfs(board);
    }
    
    // solution 1: 思路： 从四条边上的O出发往里面找，能遇到的O都是要保留的，此时我们可以用一个特殊符号来代替，这样方便区分。
    public void solve_dfs(char[][] board) {
        int m = board.length;
        int n = board[0].length;
        
        for (int i = 0; i < m; ++i) {
            dfs(board, i, 0);
            dfs(board, i, n - 1);
        }
        
        for (int j = 0; j < n; ++j) {
            dfs(board, 0, j);
            dfs(board, m - 1, j);
        }
        
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (board[i][j] == MARKER) {
                    board[i][j] = 'O';
                } else {
                    board[i][j] = 'X';
                }
            }
        }
    }
    
    private boolean isValid(char[][] board, int i, int j) {
        int m = board.length;
        int n = board[0].length;
        if (i < 0 || i >= m || j < 0 || j >= n || board[i][j] != 'O') return false;
        return true;
    }
    
    private void dfs(char[][] board, int i, int j) {
        if (!isValid(board, i, j)) return;
        
        board[i][j] = MARKER;
    
        dfs(board, i - 1, j);
        dfs(board, i + 1, j);
        dfs(board, i, j - 1);
        dfs(board, i, j + 1);
    }
    
    // solution 2: bfs
    public void solve_bfs(char[][] board) {
        int m = board.length;
        int n = board[0].length;
        
        Deque<int[]> queue = new ArrayDeque<>();
        
        for (int i = 0; i < m; ++i) {
            queue.offer(new int[]{i, 0});
            queue.offer(new int[]{i, n - 1});
        }
        
        for (int j = 0; j < n; ++j) {
            queue.offer(new int[]{0, j});
            queue.offer(new int[]{m - 1, j});
        }
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; ++i) {
                int[] pair = queue.poll();
                if (isValid(board, pair[0], pair[1])) {
                    board[pair[0]][pair[1]] = MARKER;
                    
                    int[] up = new int[]{pair[0] - 1, pair[1]};
                    int[] down = new int[]{pair[0] + 1, pair[1]};
                    int[] left = new int[]{pair[0], pair[1] - 1};
                    int[] right = new int[]{pair[0], pair[1] + 1};
                    queue.offer(up);
                    queue.offer(down);
                    queue.offer(left);
                    queue.offer(right);
                }
            }
        }
        
        
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (board[i][j] == MARKER) {
                    board[i][j] = 'O';
                } else {
                    board[i][j] = 'X';
                }
            }
        }
    }
}

