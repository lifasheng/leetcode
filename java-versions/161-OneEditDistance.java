/*
Medium
Given two strings s and t, return true if they are both one edit distance apart, otherwise return false.

A string s is said to be one distance apart from a string t if you can:

Insert exactly one character into s to get t.
Delete exactly one character from s to get t.
Replace exactly one character of s with a different character to get t.

Example 1:
Input: s = "ab", t = "acb"
Output: true
Explanation: We can insert 'c' into s to get t.

Example 2:
Input: s = "", t = ""
Output: false
Explanation: We cannot get t from s by only one step.
 
Constraints:
0 <= s.length, t.length <= 104
s and t consist of lowercase letters, uppercase letters, and digits.
*/

class Solution {
    public boolean isOneEditDistance(String s, String t) {
        return isOneEditDistance_recursive(s, t);
    }
    
    // 这个相当于O(N^2), 最坏的情况出现在两个字符串相等时.
    public boolean isOneEditDistance_recursive(String s, String t) {
        int ns = s.length();
        int nt = t.length();
        if (ns > nt) {
            return isOneEditDistance_recursive(t, s);
        }
        
        if (nt - ns > 1) {
            return false;
        }
        
        // 排除相等，或者长度差为2及以上的情况
        if (s.isEmpty() || t.isEmpty()) {
            return Math.max(s.length(), t.length()) == 1;
        }
        
        if (s.charAt(0) == t.charAt(0)) {
            return isOneEditDistance(s.substring(1), t.substring(1));
        }

        return s.equals(t.substring(1)) || s.substring(1).equals(t)  || s.substring(1).equals(t.substring(1));
    }
    
    // O(N)
    private boolean isOneEditDistance_iterative(String s, String t) {
        int ns = s.length();
        int nt = t.length();
        if (ns > nt) {
            return isOneEditDistance_iterative(t, s);
        }
        
        if (nt - ns > 1) {
            return false;
        }
        
        for (int i = 0; i < ns; ++i) {
            if (s.charAt(i) != t.charAt(i)) {
                if (ns == nt) {
                    // 相当于replace的情况
                    return s.substring(i + 1).equals(t.substring(i + 1));
                } else {
                    // 相当于insert的情况
                    return s.substring(i).equals(t.substring(i + 1));
                }
            }
        }
        // 相当于delete的情况
        // 如果前面ns个字符都相等，则两者的长度之差必须是1，不能为0，或者1以上。
        return ns + 1 == nt;
    }
}

