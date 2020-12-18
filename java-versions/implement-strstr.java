/*
28. Implement strStr()
Easy

Return the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.

Clarification:

What should we return when needle is an empty string? This is a great question to ask during an interview.

For the purpose of this problem, we will return 0 when needle is an empty string. This is consistent to C's strstr() and Java's indexOf().

 

Example 1:

Input: haystack = "hello", needle = "ll"
Output: 2
Example 2:

Input: haystack = "aaaaa", needle = "bba"
Output: -1
Example 3:

Input: haystack = "", needle = ""
Output: 0
*/

class Solution {
    private boolean match(String haystack, String needle, int index) {
        int n = needle.length();
        for(int i=0; i<n; ++i) {
            if (haystack.charAt(i+index) != needle.charAt(i)) return false;
        }
        return true;
    }
    // 暴力法 Time: O((m-n)*n), Space: O(1)
    public int strStr(String haystack, String needle) {
        if (needle.isEmpty()) return 0;
        
        for(int i=0; i<=haystack.length()-needle.length(); ++i) {
            if (match(haystack, needle, i)) {
                return i;
            }
        }
        return -1;
    }
}

