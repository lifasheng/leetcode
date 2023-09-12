/*
good!
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

class Solution {
    public int countSubstrings(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];

        int count = 0;
        for (int i = n-1; i >=0; --i) {
            for (int j = i; j < n; ++j) {
                if (i == j) {
                    dp[i][j] = true;
                } else if (i + 1 == j) {
                    dp[i][j] = (s.charAt(i) == s.charAt(j));
                } else {
                    dp[i][j] = (dp[i+1][j-1] && (s.charAt(i) == s.charAt(j)));
                }

                if (dp[i][j]) {
                    ++ count;
                }
            }
        }
        return count;
    }
}

/*
f[i, j] = true if i == j
f[i, j] = (si == sj) if i + 1 == j
f[i, j] = f[i+1, j-1] && si == sj


abba

   0 1 2 3
0. o x x x
1    o o x
2      o x 
3.       o

*/


