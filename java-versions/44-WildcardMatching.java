/*
Hard

Given an input string (s) and a pattern (p), implement wildcard pattern matching with support for '?' and '*' where:
'?' Matches any single character.
'*' Matches any sequence of characters (including the empty sequence).
The matching should cover the entire input string (not partial).

Example 1:
Input: s = "aa", p = "a"
Output: false
Explanation: "a" does not match the entire string "aa".

Example 2:
Input: s = "aa", p = "*"
Output: true
Explanation: '*' matches any sequence.

Example 3:
Input: s = "cb", p = "?a"
Output: false
Explanation: '?' matches 'c', but the second letter is 'a', which does not match 'b'.

Constraints:
0 <= s.length, p.length <= 2000
s contains only lowercase English letters.
p contains only lowercase English letters, '?' or '*'.
*/

class Solution {
    public boolean isMatch(String s, String p) {
        return isMatch_dp(s, removeDuplicateStar(p));
    }

    private boolean isMatch_recursive(String s, String p) {
        if (p.isEmpty()) return s.isEmpty();
        if (s.isEmpty()) {
            return p.isEmpty() || (p.charAt(0) == '*' && isMatch_recursive(s, p.substring(1)));
        }

        if (p.charAt(0) == '*') {
            return isMatch_recursive(s, p.substring(1))
                || isMatch_recursive(s.substring(1), p);
        }

        return (s.charAt(0) == p.charAt(0) || p.charAt(0) == '?') && isMatch_recursive(s.substring(1), p.substring(1));
    }

    // translate above recurisve way to dp with memo
    private Boolean[][] memo;
    private boolean isMatch_dp(String s, String p) {
        p = removeDuplicateStar(p);
        memo = new Boolean[s.length() + 1][p.length() + 1];
        return isMatch(0, 0, s, p);
    }

    private boolean isMatch(int i, int j, String s, String p) {
        if (memo[i][j] != null) return memo[i][j];

        if (j == p.length()) {
            memo[i][j] = (i == s.length());
        } else if (i == s.length()) {
            memo[i][j] = (j == p.length() || (p.charAt(j) == '*' && isMatch(i, j + 1, s, p)));
            return memo[i][j];
        } else if (p.charAt(j) == '*') {
            memo[i][j] = isMatch(i, j + 1, s, p) || isMatch(i + 1, j, s, p);
            return memo[i][j];
        } else {
            memo[i][j] = (s.charAt(i) == p.charAt(j) || p.charAt(j) == '?') && isMatch(i + 1, j + 1, s, p);
        }

        return memo[i][j];
    }

    private String removeDuplicateStar(String p) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < p.length();) {
            char ci = p.charAt(i);
            sb.append(ci);
            if (ci != '*') {
                ++ i;
            } else {
                while (i < p.length() && p.charAt(i) == ci) ++ i;
            }
        }
        return sb.toString();
    }
}

