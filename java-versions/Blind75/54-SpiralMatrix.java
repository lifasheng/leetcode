/*
Given an m x n matrix, return all elements of the matrix in spiral order.

Example 1:
Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
Output: [1,2,3,6,9,8,7,4,5]

Example 2:
Input: matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
Output: [1,2,3,4,8,12,11,10,9,5,6,7]
 

Constraints:

m == matrix.length
n == matrix[i].length
1 <= m, n <= 10
-100 <= matrix[i][j] <= 100
*/



class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        int upper_bound = 0, lower_bound = m - 1;
        int left_bound = 0, right_bound = n - 1;
        List<Integer> result = new ArrayList<>();
        while (result.size() < m * n) {
            // 上面：从左往右
            for (int j = left_bound; j <= right_bound; ++j) {
                result.add(matrix[upper_bound][j]);
            }
            ++upper_bound;
            if (result.size() == m*n) break;

            // 右边：从上往下
            for (int i = upper_bound; i <= lower_bound; ++i) {
                result.add(matrix[i][right_bound]);
            }
            --right_bound;
            if (result.size() == m*n) break;

            // 下面： 从右往左
            for (int j = right_bound; j >= left_bound; --j) {
                result.add(matrix[lower_bound][j]);
            }
            --lower_bound;
            if (result.size() == m*n) break;

            // 左边： 从下往上
            for (int i = lower_bound; i >= upper_bound; --i) {
                result.add(matrix[i][left_bound]);
            }
            ++left_bound;
        }

        return result;
    }
}


