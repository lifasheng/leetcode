/*
Hard
Given two strings word1 and word2, return the minimum number of operations required to convert word1 to word2.

You have the following three operations permitted on a word:

Insert a character
Delete a character
Replace a character
 
Example 1:
Input: word1 = "horse", word2 = "ros"
Output: 3
Explanation: 
horse -> rorse (replace 'h' with 'r')
rorse -> rose (remove 'r')
rose -> ros (remove 'e')

Example 2:
Input: word1 = "intention", word2 = "execution"
Output: 5
Explanation: 
intention -> inention (remove 't')
inention -> enention (replace 'i' with 'e')
enention -> exention (replace 'n' with 'x')
exention -> exection (replace 'n' with 'c')
exection -> execution (insert 'u')
 
Constraints:
0 <= word1.length, word2.length <= 500
word1 and word2 consist of lowercase English letters.
*/

class Solution {
    /*
    设状态为 f[i][j]，表示 A[0,i] 和 B[0,j] 之间的最小编辑距离。设 A[0,i] 的形式是
    str1c，B[0,j] 的形式是 str2d，
    1. 如果 c==d，则 f[i][j]=f[i-1][j-1]；
    2. 如果 c!=d，
    (a) 如果将 c 替换成 d，则 f[i][j]=f[i-1][j-1]+1；
    (b) 如果在 c 后面添加一个 d，则 f[i][j]=f[i][j-1]+1；
    (c) 如果将 c 删除，则 f[i][j]=f[i-1][j]+1；
    */
    public int minDistance(String word1, String word2) {
        return minDistance_dp(word1, word2);
    }
    
    private int minDistance_dp(String word1, String word2) {
        int n1 = word1.length();
        int n2 = word2.length();
        int[][] dp = new int[n1 + 1][n2 + 1];
        for (int i = 0; i <= n1; ++i) {
            dp[i][0] = i;
        }
        for (int j = 0 ; j <= n2; ++j) {
            dp[0][j] = j;
        }
        for (int i = 1; i <= n1; ++i) {
            for (int j = 1; j <= n2; ++j) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(Math.min(dp[i - 1][j - 1], dp[i][j - 1]), dp[i - 1][j]);
                }
            }
        }
        
        return dp[n1][n2];
    }
    
    private int minDistance_memo(String word1, String word2) {
        if (word1.isEmpty()) {
            return word2.length();
        }
        
        if (word2.isEmpty()) {
            return word1.length();
        }
        
        int n1 = word1.length();
        int n2 = word2.length();
        int[][] memo = new int[n1][n2];
        for (int[] arr : memo) {
            Arrays.fill(arr, Integer.MAX_VALUE);
        }
        return helper(word1, n1,  word2, n2, memo);
    }
    
    private int helper(String word1, int i, String word2, int j, int[][] memo) {
        if (i == 0 || j == 0) {
            return Math.max(i, j);
        }
        
        if (memo[i - 1][j - 1] != Integer.MAX_VALUE) {
            return memo[i - 1][j - 1];
        }
        
        char c1 = word1.charAt(i - 1);
        char c2 = word2.charAt(j - 1);
        if (c1 == c2) {
            memo[i - 1][j - 1] = helper(word1, i - 1, word2, j - 1, memo);
            return memo[i - 1][j - 1];
        }
        
        int d1 = helper(word1, i, word2, j - 1, memo) + 1;
        int d2 = helper(word1, i - 1, word2, j, memo) + 1;
        int d3 = helper(word1, i - 1, word2, j - 1, memo) + 1;
        int d = Math.min(d1, Math.min(d2, d3));
        memo[i - 1][j - 1] = d;
        return memo[i - 1][j - 1];
    }
}

