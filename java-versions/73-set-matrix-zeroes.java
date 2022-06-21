/*
73. Set Matrix Zeroes
Medium
Given an m x n matrix. If an element is 0, set its entire row and column to 0. Do it in-place.

Follow up:

A straight forward solution using O(mn) space is probably a bad idea.
A simple improvement uses O(m + n) space, but still not the best solution.
Could you devise a constant space solution?
*/

class Solution {
    // Time: O(m*n), Space: O(m+n)
    public void setZeroes1(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        
        boolean[] arrX = new boolean[m];
        boolean[] arrY = new boolean[n];
        
        for(int i=0; i<m; ++i) {
            for(int j=0; j<n; ++j) {
                if (matrix[i][j] == 0) {
                    arrX[i] = true;
                    arrY[j] = true;
                }
            }
        }
        
        for(int i=0; i<m; ++i) {
            for(int j=0; j<n; ++j) {
                if (arrX[i] || arrY[j]) {
                    matrix[i][j] = 0;
                }
            }
        }
    }
    
    // Time: O(m*n), Space: O(1)
    public void setZeroes2(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        
        boolean firstRowHasZero = false;
        boolean firstColumnHasZero = false;
        for(int j=0; j<n; ++j) {
            if(matrix[0][j] == 0) {
                firstRowHasZero = true;
            }
        }
        for(int i=0; i<m; ++i) {
            if(matrix[i][0] == 0) {
                firstColumnHasZero = true;
            }
        }
        
        // please note start from 1 here!!!
        for(int i=1; i<m; ++i) {
            for(int j=1; j<n; ++j) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }
        
        for(int i=1; i<m; ++i) {
            for(int j=1; j<n; ++j) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }
        
        if (firstRowHasZero) {
            for(int j=0; j<n; ++j) {
                matrix[0][j] = 0;
            }
        }
        if (firstColumnHasZero) {
            for(int i=0; i<m; ++i) {
                matrix[i][0] = 0;
            }
        }
    }
    
    public void setZeroes(int[][] matrix) {
        setZeroes2(matrix);
    }
}


