/*
5. Longest Palindromic Substring
Medium
Given a string s, return the longest palindromic substring in s.

Example 1:
Input: s = "babad"
Output: "bab"
Note: "aba" is also a valid answer.

Example 2:
Input: s = "cbbd"
Output: "bb"

Example 3:
Input: s = "a"
Output: "a"

Example 4:
Input: s = "ac"
Output: "a"
 

Constraints:

1 <= s.length <= 1000
s consist of only digits and English letters (lower-case and/or upper-case)
*/

class Solution {
    
    // O(N^2)  暴力法，对每个字符检查它的最长回文
    private String longestPalindrome1(String s) {
        if (s == null || s.isEmpty()) return "";
        
        char[] arr = s.toCharArray();
        int n = arr.length;
        
        int startIdx = 0;
        int maxLen = 0;
        for(int i=0; i<n; ++i) {
            
            // 以i为中心，按照bab结构往两边查找
            int curLen = 1;
            int j=i-1;
            int k=i+1;
            while(j>=0 && k<n) {
                if (arr[j] == arr[k]) {
                    curLen += 2;
                    --j;
                    ++k;
                } else {
                    break;
                }
            }
            if (curLen > maxLen) {
                maxLen = curLen;
                startIdx = j+1;
            }
            
            // 以i为起点，按照aa结构往两边查找
            curLen = 0;
            j=i;
            k=i+1;
            while(j>=0 && k<n) {
                if (arr[j] == arr[k]) {
                    curLen += 2;
                    --j;
                    ++k;
                } else {
                    break;
                }
            }
            if (curLen > maxLen) {
                maxLen = curLen;
                startIdx = j+1;
            }
        }
        
        return s.substring(startIdx, startIdx+maxLen);
    }

    // same as above, just simplify it.
    private String longestPalindrome2(String s) {
        if (s == null || s.isEmpty()) return "";
        int maxLen = 1;
        int startIndex = 0;
        for (int i = 0; i < s.length(); ++i) {
            int len1 = expandAroundCenter(s, i, i);
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);
            if (len > maxLen) {
                maxLen = len;
                startIndex = i - (len - 1) / 2; // important
            }
        }
        return s.substring(startIndex, maxLen + startIndex);
    }
    
    private int expandAroundCenter(String s, int i, int j) {
        while (i >= 0 && j < s.length() && s.charAt(i) == s.charAt(j)) {
            -- i;
            ++ j;
        }
        return j - i - 1; // important
    }
    
    /*
      动态规划法  O(N^2)
      f[i][j] = i==j => true
                i+1 == j => s[i]==s[j]
                s[i]==s[j] && f[i+1][j-1]
                                : 
    */
    private String longestPalindrome2(String s) {
        if (s == null || s.isEmpty()) return "";
        
        char[] arr = s.toCharArray();
        int n = arr.length;
        
        boolean[][] f = new boolean[n][n];
        
        // 填充二维数组右上角部分，行是从底下往上遍历，列是从左往右遍历
        int maxLen = 0;
        int startIdx = 0;
        for(int i=n-1; i>=0; --i) {
            for(int j=i; j<n; ++j) {
                f[i][j] = (arr[i] == arr[j]) && (i == j || i+1==j || f[i+1][j-1]);
                if (f[i][j] && (j-i+1) > maxLen) {
                    maxLen = j-i+1;
                    startIdx = i;
                }
            }
        }
        
        return s.substring(startIdx, startIdx + maxLen);
    }

    public String longestPalindrome(String s) {
        return longestPalindrome2(s);
    }
}


