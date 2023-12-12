/*
Given a string s, return the longest palindromic substring in s.

Example 1:
Input: s = "babad"
Output: "bab"
Explanation: "aba" is also a valid answer.

Example 2:
Input: s = "cbbd"
Output: "bb"

Constraints:
1 <= s.length <= 1000
s consist of only digits and English letters.
*/

class Solution {
    public String longestPalindrome(String s) {
        return longestPalindrome_dp(s);
    }

    public String longestPalindrome_dp(String s) {
        int n = s.length();

        if (n <= 1) return s;

        boolean[][] dp = new boolean[n][n];
        int maxLen = 0;
        int startIndex = 0;
        for (int i = n-1; i >= 0; --i) {
            for (int j = i; j < n; ++j) {
                if (i == j) {
                    dp[i][j] = true;
                } else if (i + 1 == j) {
                    dp[i][j] = (s.charAt(i) == s.charAt(j));
                } else {
                    dp[i][j] = dp[i+1][j-1] && (s.charAt(i) == s.charAt(j));
                }

                if (dp[i][j]) {
                    if (maxLen < (j-i+1)) {
                        maxLen = j-i+1;
                        startIndex = i;
                    }
                }
            }
        }
        return s.substring(startIndex, startIndex + maxLen);
    }
}


