/*
10. Regular Expression Matching
Hard
Given an input string (s) and a pattern (p), implement regular expression matching with support for '.' and '*' where: 

'.' Matches any single character.
'*' Matches zero or more of the preceding element.
The matching should cover the entire input string (not partial).

Example 1:
Input: s = "aa", p = "a"
Output: false
Explanation: "a" does not match the entire string "aa".

Example 2:
Input: s = "aa", p = "a*"
Output: true
Explanation: '*' means zero or more of the preceding element, 'a'. Therefore, by repeating 'a' once, it becomes "aa".

Example 3:
Input: s = "ab", p = ".*"
Output: true
Explanation: ".*" means "zero or more (*) of any character (.)".

Example 4:
Input: s = "aab", p = "c*a*b"
Output: true
Explanation: c can be repeated 0 times, a can be repeated 1 time. Therefore, it matches "aab".
Example 5:

Input: s = "mississippi", p = "mis*is*p*."
Output: false
 

Constraints:

0 <= s.length <= 20
0 <= p.length <= 30
s contains only lowercase English letters.
p contains only lowercase English letters, '.', and '*'.
It is guaranteed for each appearance of the character '*', there will be a previous valid character to match.
*/


class Solution {
    // 递归法,思路比较清晰。
    private boolean isMatch1(String s, String p) {
        if (s.isEmpty() && p.isEmpty()) return true;
        if (!s.isEmpty() && p.isEmpty()) return false;
        if (s.isEmpty() && !p.isEmpty()) {
            if (p.length() >= 2 && p.charAt(1) == '*') {
                return isMatch(s, p.substring(2));
            } else {
                return false;
            }
        }
        
        if (s.charAt(0) == p.charAt(0) || p.charAt(0) == '.') {
            if (p.length() > 1 && p.charAt(1) == '*') {
                return isMatch(s.substring(1), p) 
                    || isMatch(s, p.substring(2));
            } else {
                return isMatch(s.substring(1), p.substring(1));
            }
        } else {
            if (p.length() >=2 && p.charAt(1) == '*') {
                return isMatch(s, p.substring(2));
            } else {
                return false;
            }
        }
        
    }

    // TODO: 动态规划
    private boolean isMatch2(String s, String p) {
        return false;
    }

    public boolean isMatch(String s, String p) {
        return isMatch1(s, p);
    }
}

