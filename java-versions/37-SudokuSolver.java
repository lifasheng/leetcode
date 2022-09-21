/*
Hard
Write a program to solve a Sudoku puzzle by filling the empty cells.

A sudoku solution must satisfy all of the following rules:

Each of the digits 1-9 must occur exactly once in each row.
Each of the digits 1-9 must occur exactly once in each column.
Each of the digits 1-9 must occur exactly once in each of the 9 3x3 sub-boxes of the grid.
The '.' character indicates empty cells.

Example 1:
Input: board = [["5","3",".",".","7",".",".",".","."],["6",".",".","1","9","5",".",".","."],[".","9","8",".",".",".",".","6","."],["8",".",".",".","6",".",".",".","3"],["4",".",".","8",".","3",".",".","1"],["7",".",".",".","2",".",".",".","6"],[".","6",".",".",".",".","2","8","."],[".",".",".","4","1","9",".",".","5"],[".",".",".",".","8",".",".","7","9"]]
Output: [["5","3","4","6","7","8","9","1","2"],["6","7","2","1","9","5","3","4","8"],["1","9","8","3","4","2","5","6","7"],["8","5","9","7","6","1","4","2","3"],["4","2","6","8","5","3","7","9","1"],["7","1","3","9","2","4","8","5","6"],["9","6","1","5","3","7","2","8","4"],["2","8","7","4","1","9","6","3","5"],["3","4","5","2","8","6","1","7","9"]]
Explanation: The input board is shown above and the only valid solution is shown below:

Constraints:
board.length == 9
board[i].length == 9
board[i][j] is a digit or '.'.
It is guaranteed that the input board has only one solution.
*/

class Solution {
    private boolean isSolved;
    char[][] board;
    boolean[][] rowUsed;
    boolean[][] colUsed;
    boolean[][] boxUsed;
    
    /*
    思路： backtrack
    
    用三种数组来辅助标识某行，某列，某子块是否包含某个数字
    先遍历一遍，找到所有空位，用回溯法来填充这些空位。
    
    1. 注意box里面的下标计算
    2. 注意全局变了isSolved的使用很重要，尤其是找到solution之后就不要再回溯了。
    */
    public void solveSudoku(char[][] board) {
        if (board.length != 9 || board[0].length != 9) return;
        
        isSolved = false;
        
        this.board = board;
        rowUsed = new boolean[9][10];
        colUsed = new boolean[9][10];
        boxUsed = new boolean[9][10];
        
        List<Pair<Integer, Integer>> emptyCells = new ArrayList<>();
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                if (board[i][j] == '.') {
                    emptyCells.add(new Pair(i, j));
                } else {
                    int num = board[i][j] - '0';
                    rowUsed[i][num] = true;
                    colUsed[j][num] = true;
                    int t = (i / 3) * 3 + j / 3;
                    boxUsed[t][num] = true;
                }
            }
        }
        
        dfs(emptyCells, 0);
    }
    
    private void dfs(List<Pair<Integer, Integer>> emptyCells, int start) {
        if (isSolved) return;
        if (start == emptyCells.size()) {
            isSolved = true;
            return;
        }
        
        Pair<Integer, Integer> pair = emptyCells.get(start);
        int i = pair.getKey();
        int j = pair.getValue();
        for (int d = 1; d <= 9; ++d) {
            if (!canPlace(i, j, d)) continue;
            
            placeNumber(i, j, d);
            
            dfs(emptyCells, start + 1);

            if (isSolved) return; // if solution found,  don't backtrack, just return
            removeNumber(i, j, d);
        }
    }
    
    private boolean canPlace(int i, int j, int d) {
        if (rowUsed[i][d]) return false;
        if (colUsed[j][d]) return false;
        int t = (i / 3) * 3 + j / 3;
        if (boxUsed[t][d]) return false;
        return true;
    }
    
    private void placeNumber(int i, int j, int d) {
        rowUsed[i][d] = true;
        colUsed[j][d] = true;
        int t = (i / 3) * 3 + j / 3;
        boxUsed[t][d] = true;
        board[i][j] = (char)(d + '0');
    }
    
    private void removeNumber(int i, int j, int d) {
        board[i][j] = '.';
        rowUsed[i][d] = false;
        colUsed[j][d] = false;
        int t = (i / 3) * 3 + j / 3;
        boxUsed[t][d] = false;
    }
    
}

