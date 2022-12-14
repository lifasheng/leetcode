/*
Medium

Given an integer n, return the least number of perfect square numbers that sum to n.
A perfect square is an integer that is the square of an integer; in other words, it is the product of some integer with itself. For example, 1, 4, 9, and 16 are perfect squares while 3 and 11 are not.
 
Example 1:
Input: n = 12
Output: 3
Explanation: 12 = 4 + 4 + 4.

Example 2:
Input: n = 13
Output: 2
Explanation: 13 = 4 + 9.

Constraints:
1 <= n <= 104
*/

// very very good!
class Solution {

    public int numSquares(int n) {
        return numSquares_dp(n);
    }

    List<Integer> squarList = new ArrayList<>();
    public int numSquares_memo(int n) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 1; i < 100 && i * i <= n; ++i) {
            squarList.add(i * i);
            map.put(i * i, 1);
        }
       
        return memo(n, map);
    }

    int memo(int n, Map<Integer, Integer> map) {
        if (n == 0) {
            return 0;
        }
        if (map.containsKey(n)) {
            return map.get(n);
        }

        int minStep = Integer.MAX_VALUE;
        for (int i = 0; i < squarList.size(); ++i) {
            int squareI = squarList.get(i);
            if (n - squareI < 0) {
                break;
            }

            // 这个思路和dp应该是类似的
            // int step = 1 + memo(n - squareI, map);
            // minStep = Math.min(minStep, step);
            
            // 重点在这一步，它比上面的方法要快很多，至于为什么能想到这个，我也不清楚, 应该是和数学有关
            minStep = Math.min(minStep, n / squareI + memo(n % squareI, map));
        }
        map.put(n, minStep);
        return minStep;
    }

    
    ///////////////// 参考别人的写法  ////////////////
    int[] mem;
    public int numSquares_backtrack_2(int n) {
        mem = new int[n];
        return helpSquares(n);
    }
    private int helpSquares(int n) {
        //System.out.println(n);
        if (n==0) return 0;
        if (n==1) return 1;
        if (mem[n-1] != 0) return mem[n-1];
        int min = Integer.MAX_VALUE;
        for (int j = 1; j*j <= n; j++) {
            min = Math.min(min, n/(j*j)+helpSquares(n%(j*j)));
        }
        mem[n-1] = min;
        return min;
    }


    // 题目问和为 n 的平方数的最小数量，那么我们可以根据和为 n-1x1, n-2*2, n-3x3... 的平方数的最小数量推导出来。
    // 这个思路就是状态转移方程，具体看代码，复杂度 O(N*sqrt(N))，也是不错的。
    // dp[0] = 0
    // dp[1] = 1
    // dp[2] = dp[1] + 1 = 2
    // dp[3] = dp[2] + 1 = 3
    // dp[4] = min(dp[3] + 1, dp[0] + 1) = min(4, 1) = 1
    // dp[5] = min(dp[4] + 1, dp[1] + 1) = min(2, 2) = 2
    // dp[6] = min(dp[5] + 1, dp[2] + 1) = min(3, 3) = 3
    public int numSquares_dp(int n) {
        // 定义：和为 i 的平方数的最小数量是 dp[i]
        int[] dp = new int[n + 1];
        Arrays.fill(dp, n + 1);

        // base case
        dp[0] = 0;
        // 状态转移方程
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j * j <= i; j++) {
                // i - j * j 只要再加一个平方数 j * j 即可凑出 i
                dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
            }
        }
        return dp[n];
    }
}

