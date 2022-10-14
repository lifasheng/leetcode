/*
Medium
Given strings s1, s2, and s3, find whether s3 is formed by an interleaving of s1 and s2.
An interleaving of two strings s and t is a configuration where s and t are divided into n and m non-empty substrings respectively, such that:
s = s1 + s2 + ... + sn
t = t1 + t2 + ... + tm
|n - m| <= 1
The interleaving is s1 + t1 + s2 + t2 + s3 + t3 + ... or t1 + s1 + t2 + s2 + t3 + s3 + ...
Note: a + b is the concatenation of strings a and b.

Example 1:
Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"
Output: true
Explanation: One way to obtain s3 is:
Split s1 into s1 = "aa" + "bc" + "c", and s2 into s2 = "dbbc" + "a".
Interleaving the two splits, we get "aa" + "dbbc" + "bc" + "a" + "c" = "aadbbcbcac".
Since s3 can be obtained by interleaving s1 and s2, we return true.

Example 2:
Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc"
Output: false
Explanation: Notice how it is impossible to interleave s2 with any other string to obtain s3.

Example 3:
Input: s1 = "", s2 = "", s3 = ""
Output: true

Constraints:
0 <= s1.length, s2.length <= 100
0 <= s3.length <= 200
s1, s2, and s3 consist of lowercase English letters.
*/

class Solution {
    
    public boolean isInterleave(String s1, String s2, String s3) {
        return isInterleave_dp(s1, s2, s3);
    }
    
    // solution 1: recusive + memo
    /*
    Time complexity: O(m⋅n), where m is the length of s1 and n is the length of s2. 
    That's a consequence of the fact that each (i, j) combination is computed only once.

    Space complexity: O(m⋅n) to keep double array memo.
    */
    public boolean isInterleave_memo(String s1, String s2, String s3) {
        if ((s1.length() + s2.length()) != s3.length()) return false;
        
        // -1 表示还没有计算过，1表示s1[i..] 和s2[j..] 能组合出s3[i+j..]，0表示不能
        int[][] memo = new int[s1.length()][s2.length()];
        for (int[] arr: memo) {
            Arrays.fill(arr, -1);
        }
        return dp(s1, 0, s2, 0, s3, 0, memo);
    }
    
    public boolean dp(String s1, int i, String s2, int j, String s3, int k, int[][]memo) {
        if (i == s1.length()) {
            return s2.substring(j).equals(s3.substring(k));
        }
        if (j == s2.length()) {
            return s1.substring(i).equals(s3.substring(k));
        }
        
        if (memo[i][j] != -1) {
            return memo[i][j] == 1 ? true : false;
        }
        
        char c1 = s1.charAt(i);
        char c2 = s2.charAt(j);
        char c3 = s3.charAt(k);
        boolean res  = (c1 == c3 && dp(s1, i + 1, s2, j, s3, k + 1, memo))
            || (c2 == c3 && dp(s1, i, s2, j + 1, s3, k + 1, memo));
        
        memo[i][j] = res ? 1 : 0;
        return res;
    }
    
    // solution 2: dp
    // dp[i][j] = (s1[i] == s3[i+j] && dp[i-1][j]) || (s2[j] == s3[i + j] && dp[i][j-1])
    public boolean isInterleave_dp(String s1, String s2, String s3) {
        int n1 = s1.length();
        int n2 = s2.length();
        int n3 = s3.length();
        if (n1 + n2 != n3) {
            return false;
        }
        char[] arr1 = s1.toCharArray();
        char[] arr2 = s2.toCharArray();
        char[] arr3 = s3.toCharArray();
        
        boolean[][] dp = new boolean[n1 + 1][n2 + 1];
        // 这里的i和j可以理解为长度，而不是下标
        for (int i = 0; i <= n1; ++i) {
            for (int j = 0; j <= n2; ++j) {
                if (i == 0 && j == 0) {
                    dp[i][j] = true;
                } else if (i == 0) {
                    dp[i][j] = dp[i][j - 1] && arr2[j - 1] == arr3[i + j - 1];
                } else if (j == 0) {
                    dp[i][j] = dp[i - 1][j] && arr1[i - 1] == arr3[i + j - 1];
                } else {
                    dp[i][j] = (dp[i - 1][j] && arr1[i - 1] == arr3[i + j - 1]) || (dp[i][j - 1] && arr2[j - 1] == arr3[i + j - 1]);
                }
            }
        }
        return dp[n1][n2];
    }
}

