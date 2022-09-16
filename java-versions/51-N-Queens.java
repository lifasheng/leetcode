/*
Hard

The n-queens puzzle is the problem of placing n queens on an n x n chessboard such that no two queens attack each other.
Given an integer n, return all distinct solutions to the n-queens puzzle. You may return the answer in any order.
Each solution contains a distinct board configuration of the n-queens' placement, where 'Q' and '.' both indicate a queen and an empty space, respectively.

Example 1:
Input: n = 4
Output: [[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
Explanation: There exist two distinct solutions to the 4-queens puzzle as shown above

Example 2:
Input: n = 1
Output: [["Q"]]

Constraints:
1 <= n <= 9
*/

class Solution0 {
    private char[] DOTS;
    public List<List<String>> solveNQueens(int n) {
        DOTS = new char[n];
        Arrays.fill(DOTS, '.');
        
        List<List<String>> result = new ArrayList<>();
        int[] path = new int[n];
        backtrack(n, result, path, 0);
        return result;
    }
    
    private void backtrack(int n, List<List<String>> result, int[] path, int currentRow) {
        if (currentRow == n) {
            List<String> oneSolution = new ArrayList<>();
            for (int i = 0; i < n; ++i) {
                DOTS[path[i]] = 'Q';
                oneSolution.add(new String(DOTS));
                DOTS[path[i]] = '.';
            }
            result.add(oneSolution);
            return;
        }
        
        for (int col = 0; col < n; ++col) {
            path[currentRow] = col;
            if (isValid(path, currentRow)) {
                backtrack(n, result, path, currentRow + 1);
            } 
        }
    }
    
    // O(N)， 每次都循环判断
    private boolean isValid(int[] path, int currentRow) {
        int n = path.length;
        for (int row = 0; row < currentRow; ++row) {
            // same column
            if (path[row] == path[currentRow]) return false;
            
            // diagonal and anti-diagonal
            int rowDiff = Math.abs(row - currentRow);
            int colDiff = Math.abs(path[row] - path[currentRow]);
            if (rowDiff == colDiff) return false;
        }
        return true;
    }
}

// 用3个set来辅助判断列， 两个对角线
// Time complexity: O(N!)
class Solution {
    private char[] DOTS;
    public List<List<String>> solveNQueens(int n) {
        DOTS = new char[n];
        Arrays.fill(DOTS, '.');
        
        List<List<String>> result = new ArrayList<>();
        int[] path = new int[n];
        Set<Integer> cols = new HashSet<>();
        Set<Integer> diagonals = new HashSet<>();
        Set<Integer> antiDiagonals = new HashSet<>();
        backtrack(n, result, path, 0, cols, diagonals, antiDiagonals);
        return result;
    }
    
    private void backtrack(int n, List<List<String>> result, int[] path, int currentRow,
                          Set<Integer> cols, Set<Integer> diagonals, Set<Integer> antiDiagonals) {
        if (currentRow == n) {
            List<String> oneSolution = new ArrayList<>();
            for (int i = 0; i < n; ++i) {
                DOTS[path[i]] = 'Q';
                oneSolution.add(new String(DOTS));
                DOTS[path[i]] = '.';
            }
            result.add(oneSolution);
            return;
        }
        
        for (int col = 0; col < n; ++col) {
            path[currentRow] = col;
            int currDiagonal = currentRow - col;  // 很好的规律
            int currAntiDiagonal = currentRow + col; // 很好的规律
            boolean isInvalid = cols.contains(col) || diagonals.contains(currDiagonal) || antiDiagonals.contains(currAntiDiagonal);
            if (isInvalid) continue;
            
            cols.add(col);
            diagonals.add(currDiagonal);
            antiDiagonals.add(currAntiDiagonal);
            
            backtrack(n, result, path, currentRow + 1, cols, diagonals, antiDiagonals);
            
            cols.remove(col);
            diagonals.remove(currDiagonal);
            antiDiagonals.remove(currAntiDiagonal);
        }
    }
}

