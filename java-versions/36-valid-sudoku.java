/*
36. Valid Sudoku
Medium

2087

502

Add to List

Share
Determine if a 9 x 9 Sudoku board is valid. Only the filled cells need to be validated according to the following rules:

Each row must contain the digits 1-9 without repetition.
Each column must contain the digits 1-9 without repetition.
Each of the nine 3 x 3 sub-boxes of the grid must contain the digits 1-9 without repetition.
Note:

A Sudoku board (partially filled) could be valid but is not necessarily solvable.
Only the filled cells need to be validated according to the mentioned rules.

*/

// Time: O(N^2), Space: O(1)
class Solution {
    private boolean isValid(char c, boolean[] used) {
        if (c == '.') {
            return true;
        }
        
        if (used[c-'1']) {
            return false;
        }
        
        used[c-'1'] = true;
        return true;
    }
        
    public boolean isValidSudoku(char[][] board) {
        for(int i=0; i<9; ++i) {
            boolean[] used = new boolean[9];
            for(int j=0; j<9; ++j) {
                if (!isValid(board[i][j], used)) {
                    return false;
                }
            }
        }
        
        for(int j=0; j<9; ++j) {
            boolean[] used = new boolean[9];
            for(int i=0; i<9; ++i) {
                if (!isValid(board[i][j], used)) {
                    return false;
                }
            }
        }
        
        for(int r=0; r<3; ++r) {
            for(int c=0; c<3; ++c) {
                boolean[] used = new boolean[9];
                for(int i=r*3; i<r*3+3; ++i) {
                    for(int j=c*3; j<c*3+3; ++j) {
                        if (!isValid(board[i][j], used)) {
                            return false;
                        }
                    }
                }
            }
        }
        
        return true;
    }
}



