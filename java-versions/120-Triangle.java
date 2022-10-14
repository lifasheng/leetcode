/*
Medium

Given a triangle array, return the minimum path sum from top to bottom.

For each step, you may move to an adjacent number of the row below. More formally, if you are on index i on the current row, you may move to either index i or index i + 1 on the next row.

Example 1:
Input: triangle = [[2],[3,4],[6,5,7],[4,1,8,3]]
Output: 11
Explanation: The triangle looks like:
   2
  3 4
 6 5 7
4 1 8 3
The minimum path sum from top to bottom is 2 + 3 + 5 + 1 = 11 (underlined above).

Example 2:
Input: triangle = [[-10]]
Output: -10

Constraints:
1 <= triangle.length <= 200
triangle[0].length == 1
triangle[i].length == triangle[i - 1].length + 1
-104 <= triangle[i][j] <= 104
*/

class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        return minimumTotal_memo(triangle);
    }
    
    // dp[i][j] = triangle[i][j] + min(dp[i + 1][j], dp[i + 1][j + 1])
    private int minimumTotal_dp_bottomup(List<List<Integer>> triangle) {
        int m = triangle.size();
        int n = triangle.get(m - 1).size();
        
        ArrayList<Integer> dp = new ArrayList(triangle.get(m - 1));
        
        for (int i = m - 2; i >= 0; --i) {
            List<Integer> list = triangle.get(i);
            -- n;
            for (int j = 0; j < n; ++j) {
                dp.set(j, list.get(j) + Math.min(dp.get(j), dp.get(j + 1)));
            }
        }
        return dp.get(0);
    }
    
    // dp[i][j] = triangle[i][j] + min(dp[i - 1][j], dp[i - 1][j - 1])
    private int minimumTotal_dp_topdown(List<List<Integer>> triangle) {
        int m = triangle.size();
        int n = triangle.get(m - 1).size();
        
        int[] dp = new int[n];
        dp[0] = triangle.get(0).get(0);
        
        for (int i = 1; i < m; ++i) {
            for (int j = i; j >= 0; --j) { // 注意这里要从右往左，不然dp[j-1]的值不对
                int vij = triangle.get(i).get(j);
                if (j == 0) {
                    dp[j] = vij + dp[j];
                } else if (j == i) {
                    dp[j] = vij + dp[j - 1];
                } else {
                    dp[j] = vij + Math.min(dp[j - 1], dp[j]);
                }
            }
        }
        int res = Integer.MAX_VALUE;
        for (int i : dp) {
            res = Math.min(res, i);
        }
        return res;
    }
    
    
    // solution 3: memo
    private int minimumTotal_memo(List<List<Integer>> triangle) {
        Map<String, Integer> memo = new HashMap<>();
        return dfs(triangle, 0, 0, memo);
    }
    
    private int dfs(List<List<Integer>> triangle, int row, int col, Map<String, Integer> memo) {
        String s = row + ":" + col;
        if (memo.containsKey(s)) {
            return memo.get(s);
        }
        
        int minPath = triangle.get(row).get(col);
        if (row < triangle.size() - 1) {
            minPath += Math.min(dfs(triangle, row + 1, col, memo), dfs(triangle, row + 1, col + 1, memo));
        }
        
        memo.put(s, minPath);
        return minPath;
    }
}

