/*
Given an n x n binary grid, in one step you can choose two adjacent rows of the grid and swap them.

A grid is said to be valid if all the cells above the main diagonal are zeros.

Return the minimum number of steps needed to make the grid valid, or -1 if the grid cannot be valid.

The main diagonal of a grid is the diagonal that starts at cell (1, 1) and ends at cell (n, n).

Example 1:
Input: grid = [[0,0,1],[1,1,0],[1,0,0]]
Output: 3

Example 2:
Input: grid = [[0,1,1,0],[0,1,1,0],[0,1,1,0],[0,1,1,0]]
Output: -1
Explanation: All rows are similar, swaps have no effect on the grid.

Example 3:
Input: grid = [[1,0,0],[1,1,0],[1,1,1]]
Output: 0

Constraints:
n == grid.length == grid[i].length
1 <= n <= 200
grid[i][j] is either 0 or 1
*/


class Solution {
    // 贪心算法  greedy algorithm, very good!
    // 1. 计算每一行的右边0的个数
    // 2. 从上到下，对每一行 i，它右边的0的个数 如果小于该行右边实际0的个数，则往下找一个可以满足的行 j。
    //    这里就是贪心算法了，我们只需要找到第一个j就行，因为即使后面还有也不用了，而且上面的行 i需要的0的个数多，都被满足了，就不用担心下面的行 因为我们把j这一行用掉了而导致找不到最终结果了。 我的意思是假如j行下面还有一个行k也可以和行i交换，则我们不用担心如果不用k会有什么问题，因为行k可以用于帮助行i下面的其他行。
    // 3. 既然找到了行j，那我们就模拟交换的过程，把行j往上交换到行i，并计算交换次数。
    public int minSwaps(int[][] grid) {
        int n = grid.length;
        
        int[] rightZeros = new int[n];
        for (int i = 0; i < n; ++i) {
            rightZeros[i] = countRightZeros(grid[i]);
        }
        
        int count = 0;
        for (int i = 0; i < n; ++i) {
            int requiredZeros = n - 1 - i;
            
            int j = i;
            while (j < n && rightZeros[j] < requiredZeros) {
                ++ j;
            }
            
            if (j == n) return -1;
            
            while (j != i) {
                ++ count;
                swap(rightZeros, j, j - 1);
                -- j;
            }
        }
        return count;
    }
    
    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
    
    
    private int countRightZeros(int[] row) {
        int count = 0;
        for (int i = row.length - 1; i >= 0; --i) {
            if (row[i] == 1) break;
            ++count;
        }
        return count;
    }
}

