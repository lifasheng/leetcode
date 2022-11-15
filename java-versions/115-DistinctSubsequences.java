/*
Hard
Given two strings s and t, return the number of distinct subsequences of s which equals t.
The test cases are generated so that the answer fits on a 32-bit signed integer.

Example 1:
Input: s = "rabbbit", t = "rabbit"
Output: 3
Explanation:
As shown below, there are 3 ways you can generate "rabbit" from s.
rabbbit
rabbbit
rabbbit

Example 2:
Input: s = "babgbag", t = "bag"
Output: 5
Explanation:
As shown below, there are 5 ways you can generate "bag" from s.
babgbag
babgbag
babgbag
babgbag
babgbag

Constraints:
1 <= s.length, t.length <= 1000
s and t consist of English letters.
*/

class Solution {
    public int numDistinct(String s, String t) {
        return numDistinct_dp(s, t);
    }
    
    // very very good!
    // Time: O(M*N), space: O(M*N)
    /*
    Given two indices i and j, our function would return the number of distinct subsequences 
    in the substring s[i⋯M] that equal the substring t[j⋯N] where M and N represent the lengths of the two string respectively.
    func(i, j) = func(i + 1, j) + func(i + 1, j + 1)  if s[i] == t[j]
    func(i, j) = func(i + 1, j)  if s[i] != t[j]
    */
    private int numDistinct_memo(String s, String t) {
        if (s.length() < t.length()) {
            return 0;
        }
        int[][] memo = new int[s.length()][t.length()];
        for (int[] arr : memo) {
            Arrays.fill(arr, -1);
        }
        return dfs(s, t, 0, 0, memo);
    }
    
    private int dfs(String s, String t, int i, int j, int[][] memo) {
        // 剪枝 s.length() - i < t.length() - j
        if (i == s.length() || j == t.length() || s.length() - i < t.length() - j) {
            return (j == t.length()) ? 1 : 0;
        }
        
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        
        if (s.charAt(i) != t.charAt(j)) {
            memo[i][j] = dfs(s, t, i + 1, j, memo); 
        } else {
            memo[i][j] = dfs(s, t, i + 1, j + 1, memo) + dfs(s, t, i + 1, j, memo);
        }
        return memo[i][j];
    }
    
    public int numDistinct_dp(String s, String t) {
        int m = s.length();
        int n = t.length();
        if (m < n) {
            return 0;
        }
        
        // 字符串一般都会len + 1，以包含empty string的情况
        int[][] dp = new int[m + 1][n + 1];

        // dp[m][j] means substring of s is empty, so dp[m][j] = 0.
        for (int j = 0; j < n; ++j) {
            dp[m][j] = 0;
        }
        // dp[i][n] means substring of t is empty, every string has an empty subsequence, so it is always 1.
        // note dp[m][n] = 1 because an empty string is a subsequence of an empty string.
        for (int i = 0; i <= m; ++i) {
            dp[i][n] = 1;
        }
        
        for (int i = m - 1; i >= 0; --i) {
            for (int j = n - 1; j >= 0; --j) {
                if (s.charAt(i) == t.charAt(j)) {
                    dp[i][j] = dp[i + 1][j] + dp[i + 1][j + 1];
                } else {
                    dp[i][j] = dp[i + 1][j];
                }
            }
        }
        return dp[0][0];
    }
}

