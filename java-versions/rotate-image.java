/*
48. Rotate Image
Medium
You are given an n x n 2D matrix representing an image, rotate the image by 90 degrees (clockwise).

You have to rotate the image in-place, which means you have to modify the input 2D matrix directly. DO NOT allocate another 2D matrix and do the rotation.
*/

class Solution {
    private void swap(int[][] matrix, int i1, int j1, int i2, int j2) {
        int temp = matrix[i1][j1];
        matrix[i1][j1] = matrix[i2][j2];
        matrix[i2][j2] = temp;
    }
    
    public void rotate1(int[][] matrix) {
        if (matrix == null) {
            return;
        }
        
        int n = matrix.length;
        for(int i=0; i<n; ++i) { // 沿着副对角线反转
            for(int j=0; j<n-1-i; ++j) {
                swap(matrix, i, j, n-1-j, n-1-i);
            }
        }
        
        for(int i=0; i<n/2; ++i) { // 沿着水平中线反转
            for(int j=0; j<n; ++j) {
                swap(matrix, i, j, n-1-i, j);
            }
        }
    }
    
    public void rotate2(int[][] matrix) {
        if (matrix == null) {
            return;
        }
        
        int n = matrix.length;
        for(int i=0; i<n; ++i) { // 沿着主对角线反转
            for(int j=i+1; j<n; ++j) {
                swap(matrix, i, j, j, i);
            }
        }
        
        for(int i=0; i<n; ++i) { // 沿着垂直中线反转
            for(int j=0; j<n/2; ++j) {
                swap(matrix, i, j, i, n-1-j);
            }
        }
    }
    
    public void rotate(int[][] matrix) {
        rotate2(matrix);
    }
}


