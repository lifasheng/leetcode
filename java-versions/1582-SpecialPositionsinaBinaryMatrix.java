/*
Given an m x n binary matrix mat, return the number of special positions in mat.

A position (i, j) is called special if mat[i][j] == 1 and all other elements in row i and column j are 0 (rows and columns are 0-indexed).

Input: mat = [[1,0,0],[0,0,1],[1,0,0]]
Output: 1
Explanation: (1, 2) is a special position because mat[1][2] == 1 and all other elements in row 1 and column 2 are 0.

Input: mat = [[1,0,0],[0,1,0],[0,0,1]]
Output: 3
Explanation: (0, 0), (1, 1) and (2, 2) are special positions.
*/


class Solution {
    private int[][] mat;
    private int row;
    private int col;
    public int numSpecial(int[][] mat) {
        this.mat = mat;
        this.row = mat.length;
        this.col = mat[0].length;
        return countNumSpecial(mat);
    }
    
    private int countNumSpecial(int[][] mat) {
        int count = 0;
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
                if (mat[i][j] == 1 && isSpecial(i, j)) {
                    ++ count;
                }
            }
        }
        return count;
    }
    
    private boolean isSpecial(int m, int n) {
        for (int i = 0; i < row; ++i) {
            if (i == m) continue;
            if (mat[i][n] == 1) return false;
        }
        
        for (int j = 0; j < col; ++j) {
            if (j == n) continue;
            if (mat[m][j] == 1) return false;
        }
        
        return true;
    }
}

