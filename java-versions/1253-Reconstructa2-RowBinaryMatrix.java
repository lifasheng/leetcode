/*
Given the following details of a matrix with n columns and 2 rows :

The matrix is a binary matrix, which means each element in the matrix can be 0 or 1.
The sum of elements of the 0-th(upper) row is given as upper.
The sum of elements of the 1-st(lower) row is given as lower.
The sum of elements in the i-th column(0-indexed) is colsum[i], where colsum is given as an integer array with length n.
Your task is to reconstruct the matrix with upper, lower and colsum.

Return it as a 2-D integer array.

If there are more than one valid solution, any of them will be accepted.

If no valid solution exists, return an empty 2-D array.

Example 1:
Input: upper = 2, lower = 1, colsum = [1,1,1]
Output: [[1,1,0],[0,0,1]]
Explanation: [[1,0,1],[0,1,0]], and [[0,1,1],[1,0,0]] are also correct answers.

Example 2:
Input: upper = 2, lower = 3, colsum = [2,2,1,1]
Output: []

Example 3:
Input: upper = 5, lower = 5, colsum = [2,1,2,0,1,0,1,2,0,1]
Output: [[1,1,1,0,1,0,0,1,0,0],[1,0,1,0,0,0,1,1,0,1]]

Constraints:
1 <= colsum.length <= 10^5
0 <= upper, lower <= colsum.length
0 <= colsum[i] <= 2
*/

class Solution {
    public List<List<Integer>> reconstructMatrix(int upper, int lower, int[] colsum) {
        return reconstructMatrix_iterative(upper, lower, colsum);
    }
    // Solution 1: using dfs
    int[] row0;
    int[] row1;
    private List<List<Integer>> reconstructMatrix_dfs(int upper, int lower, int[] colsum) {
        int n = colsum.length;
        this.row0 = new int[n];
        this.row1 = new int[n];
        
        List<List<Integer>> result = new ArrayList<>();
        int sum = 0;
        for (int i : colsum) {
            sum += i;
        }
        if (sum != (upper + lower)) return result;
        
        boolean isValid = dfs(0, upper, lower, colsum);
        
        if (!isValid) return result;
        
        result.add(arrToList(row0));
        result.add(arrToList(row1));
        return result;
    }
    
    private List<Integer> arrToList(int[] arr) {
        List<Integer> res = new ArrayList<>();
        for (int i : arr) {
            res.add(i);
        }
        return res;
    }
    
    private boolean dfs(int idx, int upper, int lower, int[] colsum) {
        int n = colsum.length;
        if (idx >= n) return (upper == 0 && lower == 0);
        
        if (colsum[idx] == 2) {
            if (upper < 1) return false;
            row0[idx] = 1;
            -- upper;
            if (lower < 1) return false;
            row1[idx] = 1;
            -- lower;
            return dfs(idx + 1, upper, lower, colsum);
        }
        
        if (colsum[idx] == 0) {
            row0[idx] = 0;
            row1[idx] = 0;
            return dfs(idx + 1, upper, lower, colsum);
        }
        
        if (upper < 1 && lower < 1) return false;
        
        if (upper >= lower) { // this greedy strategy is important, otherwise, if we try row0[i] = 1 first, it will timeout.
            row0[idx] = 1;
            -- upper;
            return upper >= 0 && dfs(idx + 1, upper, lower, colsum);
        } else {
            row1[idx] = 1;
            -- lower;
            return lower >= 0 && dfs(idx + 1, upper, lower, colsum);
        }
    }
    
    private List<List<Integer>> reconstructMatrix_iterative(int upper, int lower, int[] colsum) {
        int n = colsum.length;
        int[][] arr = new int[2][n];
               
        List<List<Integer>> result = new ArrayList<>();
        boolean isValid = true;
        for (int i = 0; i < n ; ++i) {
            if (colsum[i] == 2) {
                arr[0][i] = 1;
                arr[1][i] = 1;
                -- upper;
                -- lower;
            } else if (colsum[i] == 1) {
                if (upper >= lower) { // greedy strategy
                    arr[0][i] = 1;
                    -- upper;
                } else {
                    arr[1][i] = 1;
                    -- lower;
                }
            }
            
            if (upper < 0 || lower < 0) {
                isValid = false;
                break;
            }
        }
        
        isValid = isValid && (upper == 0 && lower == 0);
        if (!isValid) {
            return result;
        }
        
        result.add(arrToList(arr[0]));
        result.add(arrToList(arr[1]));
        return result;
    }
}


