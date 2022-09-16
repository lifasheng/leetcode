/*
Hard
The n-queens puzzle is the problem of placing n queens on an n x n chessboard such that no two queens attack each other.
Given an integer n, return the number of distinct solutions to the n-queens puzzle.

Example 1:
Input: n = 4
Output: 2
Explanation: There are two distinct solutions to the 4-queens puzzle as shown.

Example 2:
Input: n = 1
Output: 1

Constraints:
1 <= n <= 9
*/

class Solution {
    int totalCount = 0;
    public int totalNQueens(int n) {
        int[] path = new int[n];
        backtrack(n, path, 0);
        return totalCount;
    }
    
    private void backtrack(int n, int[] path, int currentRow) {
        if (currentRow == n) {
            ++ totalCount;
            return;
        }
        
        for (int col = 0; col < n; ++col) {
            path[currentRow] = col;
            if (!isValid(path, currentRow)) continue;
            backtrack(n, path, currentRow + 1);
        }
    }
    
    private boolean isValid(int[] path, int currentRow) {
        int n = path.length;
        for (int row = 0; row < currentRow; ++row) {
            if (path[row] == path[currentRow]) return false;
            if (Math.abs(row - currentRow) == Math.abs(path[row] - path[currentRow])) return false;
        }
        return true;
    }
}

