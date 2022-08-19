/*
Medium

Given a string s, return the number of palindromic substrings in it.
A string is a palindrome when it reads the same backward as forward.
A substring is a contiguous sequence of characters within the string.

Example 1:
Input: s = "abc"
Output: 3
Explanation: Three palindromic strings: "a", "b", "c".

Example 2:
Input: s = "aaa"
Output: 6
Explanation: Six palindromic strings: "a", "a", "a", "aa", "aa", "aaa".
 
Constraints:
1 <= s.length <= 1000
s consists of lowercase English letters.
*/

// dp, very good!
class Solution {
    public int countSubstrings(String s) {
        int n = s.length();
        
        int count = 0;
        
        boolean[][] dp = new boolean[n][n];
        
        for (int i = 0; i < n; ++i) {
            dp[i][i] = true;
            ++ count;
        }
        
        for (int i = n - 1; i >= 0; --i) {
            for (int j = i + 1; j < n; ++j) {
                if (i + 1 == j) {
                    dp[i][j] = (s.charAt(i) == s.charAt(j));
                } else {
                    dp[i][j] = (s.charAt(i) == s.charAt(j)) ? dp[i + 1][j - 1] : false;
                }
                if (dp[i][j]) count ++;
            }
        }
        return count;
    }
}

