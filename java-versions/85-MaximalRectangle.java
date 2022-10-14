/*
Hard
Given a rows x cols binary matrix filled with 0's and 1's, find the largest rectangle containing only 1's and return its area.

Example 1:
Input: matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
Output: 6
Explanation: The maximal rectangle is shown in the above picture.

Example 2:
Input: matrix = [["0"]]
Output: 0

Example 3:
Input: matrix = [["1"]]
Output: 1

Constraints:
rows == matrix.length
cols == matrix[i].length
1 <= row, cols <= 200
matrix[i][j] is '0' or '1'.
*/

class Solution {
    public int maximalRectangle(char[][] matrix) {
        return maximalRectangle_histogram(matrix);
    }
    
    // solution 1: brute force  O(N^3M^3)
    private int maximalRectangle_bruteForce(char[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        
        int maxArea = 0;
        
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (matrix[i][j] == '0') continue;
                
                for (int i2 = i; i2 < m; ++i2) {
                    for (int j2 = j; j2 < n; ++j2) {
                        if (matrix[i2][j2] == '0') continue;
                        
                        boolean allOnes = true;
                        for (int r = i; r <= i2; ++r) {
                            for (int c = j; c <= j2; ++c) {
                                if (matrix[r][c] == '0') {
                                    allOnes = false;
                                    break;
                                }
                            }
                        }
                        if (allOnes) {
                            maxArea = Math.max(maxArea, (i2 - i + 1) * (j2 - j + 1));
                        }
                    }
                }
                
            }
        }
        
        return maxArea;
    }
    
    // 利用 84-LargestRectangleinHistogram 的idea
    // O(M*N)
    private int maximalRectangle_histogram(char[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        
        int[] heights = new int[n];
        for (int j = 0; j < n; ++j) {
            heights[j] = matrix[0][j] - '0';
        }
        
        int maxArea = maxRectangle(heights);
        
        for (int i = 1; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (matrix[i][j] == '0') {
                    heights[j] = 0;
                } else {
                    heights[j] += 1;
                }
            }
            maxArea = Math.max(maxArea, maxRectangle(heights));
        }
        return maxArea;
    }
    
    private int maxRectangle(int[] heights) {
        int n = heights.length;
        int maxArea = 0;
        
        Deque<Integer> stack = new ArrayDeque<>();
        int i = 0;
        while (i < n) {
            if (stack.isEmpty() || heights[i] > heights[stack.peek()]) {
                stack.push(i);
                ++ i;
            } else {
                int index = stack.pop();
                int width = stack.isEmpty() ? i : (i - stack.peek() - 1);
                maxArea = Math.max(maxArea, heights[index] * width);
            }
        }
        while (!stack.isEmpty()) {
            int index = stack.pop();
            int width = stack.isEmpty() ? i : (i - stack.peek() - 1);
            maxArea = Math.max(maxArea, heights[index] * width);
        }
        return maxArea;
    }
}

