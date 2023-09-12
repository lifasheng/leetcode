/*
very good !

Given a positive integer n, generate an n x n matrix filled with elements from 1 to n2 in spiral order.

Example 1:
Input: n = 3
Output: [[1,2,3],[8,9,4],[7,6,5]]

Example 2:
Input: n = 1
Output: [[1]]

Constraints:
1 <= n <= 20
*/

class Solution {
    public int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];

        int upper_bound = 0, lower_bound = n-1;
        int left_bound = 0, right_bound = n-1;

        int size = n * n;
        int k = 0;
        while (k < size) {
            for (int j = left_bound; j <= right_bound; ++j) {
                matrix[upper_bound][j] = ++k;
            }
            ++upper_bound;
            if (k == size) break;

            for (int i = upper_bound; i <= lower_bound; ++i) {
                matrix[i][right_bound] = ++k;
            }
            --right_bound;
            if (k == size) break;

            for (int j = right_bound; j >= left_bound; --j) {
                matrix[lower_bound][j] = ++k;
            }
            --lower_bound;
            if (k == size) break;

            for (int i = lower_bound; i >= upper_bound; --i) {
                matrix[i][left_bound] = ++k;
            }
            ++left_bound;

        }
        return matrix;
    }
}



