/*
Medium
Given an m x n binary matrix filled with 0's and 1's, find the largest square containing only 1's and return its area.

Example 1:
Input: matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
Output: 4

Example 2:
Input: matrix = [["0","1"],["1","0"]]
Output: 1

Example 3:
Input: matrix = [["0"]]
Output: 0
 
Constraints:
m == matrix.length
n == matrix[i].length
1 <= m, n <= 300
matrix[i][j] is '0' or '1'.
*/

class Solution {
    public int maximalSquare(char[][] matrix) {
        return maximalSquare_dp_bottomUp(matrix);
    }
    
    // O((mn)^2), 超时
    public int maximalSquare_bruteForce(char[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int w = Math.min(m, n);
        
        int maxArea = 0;
        
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (matrix[i][j] == '0') continue;
                
                maxArea = Math.max(maxArea, 1);
                
                for (int t = 1; t < w; ++t) {
                    boolean allOnes = true;
                    if (i + t >= m || j + t >= n) break;
                    
                    for (int i2 = i; i2 <= i + t; ++i2) {
                        if (matrix[i2][j + t] == '0') {
                            allOnes = false;
                            break;
                        }
                    }
                    
                    if (!allOnes) break;
                    
                    for (int j2 = j; j2 <= j + t; ++j2) {
                        if (matrix[i + t][j2] == '0') {
                            allOnes = false;
                            break;
                        }
                    }
                    
                    if (!allOnes) break;
                    
                    maxArea = Math.max(maxArea, (1 + t) * (1 + t));
                }
            }
        }
        return maxArea;
    }
    
    // solution 2: dp, O(M*N)
    // 定义：以 matrix[i][j] 为右下角元素的全为 1 正方形矩阵的最大边长为 dp[i][j]。
    // dp[i][j] = min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1]) + 1
    public int maximalSquare_dp_bottomUp(char[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        
        int maxWidth = 0;
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; ++i) {
            dp[i][0] = matrix[i][0] - '0';
            if (dp[i][0] == 1) maxWidth = 1;
        }
        
        for (int j = 0; j < n; ++j) {
            dp[0][j] = matrix[0][j] - '0';
            if (dp[0][j] == 1) maxWidth = 1;
        }
        
        for (int i = 1; i < m; ++i) {
            for (int j = 1; j < n; ++j) {
                if (matrix[i][j] == '0') {
                    dp[i][j] = 0;
                } else {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                    maxWidth = Math.max(maxWidth, dp[i][j]);
                }
            }
        }
        return maxWidth * maxWidth;
    }
    
    // dp, top down, 这是一个很好的启发， 从这个启发可以得到上面的bottom up的dp
    /*
    you'd go to the first 1 and ask it, "Hey, what's the largest square of 1s that begins with you?". 
    To calculate that it needs to know the largest squares its adjacent cells can begin. 
    So, it'll ask the same question to its adjacent cells which will in turn will ask their adjacent cells and so on... 
    The cell that began the question will deduce that the largest square that begins with it is 
    1 + the minimum of all the values its adjacent cells returned.
    */
    public int maximalSquare_dp_topdown_memo(char[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        Map<Pair<Integer, Integer>, Integer> posToLen = new HashMap<>();
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (matrix[i][j] == '1') {
                    dfs(matrix, i, j, posToLen);
                }
            }
        }
        
        int maxWidth = 0;
        for (int v : posToLen.values()) {
            maxWidth = Math.max(maxWidth, v);
        }
        return maxWidth * maxWidth;
    }
    
    private int dfs(char[][] matrix, int i, int j, Map<Pair<Integer, Integer>, Integer> posToLen) {
        int m = matrix.length;
        int n = matrix[0].length;
        if (i >= m || j >= n) return 0;
        
        Pair<Integer, Integer> pos = new Pair(i, j);
        if (posToLen.containsKey(pos)) {
            return posToLen.get(pos);
        }
        
        if (matrix[i][j] == '0') {
            posToLen.put(pos, 0);
            return 0;
        }
        
        int right = dfs(matrix, i, j + 1, posToLen);
        int down = dfs(matrix, i + 1, j, posToLen);
        int rightDown = dfs(matrix, i + 1, j + 1, posToLen);
        
        int width = Math.min(Math.min(right, down), rightDown) + 1;
        posToLen.put(pos, width);
        return width;
    }
    
}

