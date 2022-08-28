/*
Meduim
Write an efficient algorithm that searches for a value target in an m x n integer matrix matrix. This matrix has the following properties:

Integers in each row are sorted in ascending from left to right.
Integers in each column are sorted in ascending from top to bottom.

Example 1:
Input: matrix = [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]], target = 5
Output: true

Example 2:
Input: matrix = [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]], target = 20
Output: false

Constraints:
m == matrix.length
n == matrix[i].length
1 <= n, m <= 300
-109 <= matrix[i][j] <= 109
All the integers in each row are sorted in ascending order.
All the integers in each column are sorted in ascending order.
-109 <= target <= 109
*/

class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        return searchMatrix_searchFromRightTopCorner(matrix, target);
    }
    
    // solution 1: O(mlogn)
    private boolean searchMatrix_searchFromLeftTopCorner(int[][] matrix, int target) {
        int m = matrix.length;
        if (m == 0) return false;
        int n = matrix[0].length;
        if (n == 0) return false;

        // find the lower bound of target in first col
        int maxRow = findPosInFirstCol(matrix, target);
        
        if (maxRow < m && matrix[maxRow][0] == target) return true;
        
        for (int row = 0; row < maxRow; ++row) {
            if (binarySearch(matrix, target, row)) return true;
        }
        return false;
    }
    
    private int findPosInFirstCol(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;
        
        int low = 0, high = m - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (matrix[mid][0] == target) return mid;
            else if (matrix[mid][0] < target) low = mid + 1;
            else high = mid - 1;
        }
        return low;
    }
    
    private boolean binarySearch(int[][] matrix, int target, int row) {
        int m = matrix.length;
        int n = matrix[0].length;
        int low = 0, high = n - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (matrix[row][mid] == target) return true;
            else if (matrix[row][mid] < target) low = mid + 1;
            else high = mid - 1;
        }
        return false;
    }
    
    // solution: start from left bottom or right top: O(m + n)
    /*
    The key to the time complexity analysis is noticing that, on every iteration 
    (during which we do not return true) either row or col is is decremented/incremented exactly once. 
    Because row can only be decremented mm times and col can only be incremented nn times before 
    causing the while loop to terminate, the loop cannot run for more than n+mn+m iterations. 
    Because all other work is constant, the overall time complexity is linear in the sum of the 
    dimensions of the matrix.
    */
    private boolean searchMatrix_searchFromRightTopCorner(int[][] matrix, int target) {
        int m = matrix.length;
        if (m == 0) return false;
        int n = matrix[0].length;
        if (n == 0) return false;
        
        int i = 0, j = n - 1;
        while (i < m && j >= 0) {
            if (matrix[i][j] == target) return true;
            else if (matrix[i][j] < target) ++i;
            else --j;
        }
        return false;
    }
}

