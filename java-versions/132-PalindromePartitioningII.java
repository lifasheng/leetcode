/*
Hard
Given a string s, partition s such that every substring of the partition is a palindrome.

Return the minimum cuts needed for a palindrome partitioning of s.

Example 1:
Input: s = "aab"
Output: 1
Explanation: The palindrome partitioning ["aa","b"] could be produced using 1 cut.

Example 2:
Input: s = "a"
Output: 0

Example 3:
Input: s = "ab"
Output: 1
 
Constraints:
1 <= s.length <= 2000
s consists of lowercase English letters only.
*/

class Solution {
    
    // solution 0: 和131. Palindrome Partitioning类似的方法，会超时
    private int minCut;
    private int curCut;
    public int minCut_0(String s) {
        int n = s.length();
        minCut = n;
        curCut = 0;
        boolean[][] dp = new boolean[n][n];
        for (int i = n - 1; i >= 0; --i) {
            for (int j = i; j < n; ++j) {
                dp[i][j] = (s.charAt(i) == s.charAt(j)) && ((j - i < 2) || dp[i + 1][j - 1]);
            }
        }
        
        dfs(s, 0, dp);
        return minCut - 1;
    }
    
    // Time: O(2^N):  T(N)=T(N−1)+T(N−2)+...+T(1).
    private void dfs(String s, int start, boolean[][] dp) {
        if (start == s.length()) {
            minCut = Math.min(minCut, curCut);
            return;
        }
        
        for (int end = start; end < s.length(); ++end) {
            if (dp[start][end]) {
                 ++ curCut;
                dfs(s, end + 1, dp);
                -- curCut;
            }
        }
    }
    
    public int minCut(String s) {
        return minCut_2(s);
    }
    
    // solution 1: 递归 + 备忘录法, 注意和131. Palindrome Partitioning里面的dfs的细微区别
    // 因为这里只需要计算划分数，不需要返回所有可能的划分很适合递归求解，同时用上备忘录进行优化。
    // 这个方法要掌握，也比较好理解.
    // memoCuts 表示从i到n-1的cut数
    // very very good!!!
    public int minCut_1(String s) {
        int n = s.length();
        
        Integer[] memoCuts = new Integer[n];
        boolean[][] dp = new boolean[n][n];
        for (int i = n - 1; i >= 0; --i) {
            for (int j = i; j < n; ++j) {
                dp[i][j] = (s.charAt(i) == s.charAt(j)) && ((j - i < 2) || dp[i + 1][j - 1]);
            }
        }
        
        return findMinimumCut(s, 0, dp, memoCuts);
    }
    
    /*
    In the recursive method findMinimumCut, we are calculating the results for any substring only once. 
    We know that a string size N has N^2 possible substrings. 
    Thus, the worst-case time complexity of the recursive method findMinimumCut is O(N^2)
    */
    private int findMinimumCut(String s, int start, boolean[][] dp, Integer[] memoCuts) {
        int end = s.length() - 1;
        if (start == end || dp[start][end]) {
            memoCuts[start] = 0;
            return 0;
        }
        
        if (memoCuts[start] != null) return memoCuts[start];
        
        int minCut = end - start;
        for (int i = start; i <= end; ++i) {
            if (dp[start][i]) {
                minCut = Math.min(minCut, 1 + findMinimumCut(s, i + 1, dp, memoCuts));
            }
        }
        memoCuts[start] = minCut;
        return minCut;
    }
    
    
    // solution 2: dp bottom up, 
    // f(i, j) = min {f(i, k) + f(k + 1, j)} , i ≤ k ≤ j, 0 ≤ i ≤ j < n
    // 这是一个二维函数，实际写代码比较麻烦。
    // 所以要转换成一维 DP。如果每次，从 i 往右扫描，每找到一个回文就算一次 DP 的话，就可以
    // 转换为 f(i)= 区间 [i, n-1] 之间最小的 cut 数，n 为字符串长度，则状态转移方程为
    // f(i) = min {f(j + 1) + 1} , i ≤ j < n
    // 计算时需要从右往左计算
    public int minCut_2(String s) {
        int n = s.length();
        
        boolean[][] dp = new boolean[n][n];
        for (int i = n - 1; i >= 0; --i) {
            for (int j = i; j < n; ++j) {
                dp[i][j] = (s.charAt(i) == s.charAt(j)) && ((j - i < 2) || dp[i + 1][j - 1]);
            }
        }
        
        int[] f = new int[n + 1];
        for (int i = 0; i <= n; ++i) {
            f[i] = n - 1 - i;
        }
        for (int i = n - 1; i >= 0; --i) {
            for (int j = i; j < n; ++j) {
                if (dp[i][j]) {
                    f[i] = Math.min(f[i], f[j + 1] + 1);
                }
             }
        }
        return f[0];
    }
}

