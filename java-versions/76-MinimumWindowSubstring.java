/*
Hard

Given two strings s and t of lengths m and n respectively, return the minimum window substring of s such that every character in t (including duplicates) is included in the window. If there is no such substring, return the empty string "".

The testcases will be generated such that the answer is unique.

A substring is a contiguous sequence of characters within the string.

Example 1:
Input: s = "ADOBECODEBANC", t = "ABC"
Output: "BANC"
Explanation: The minimum window substring "BANC" includes 'A', 'B', and 'C' from string t.

Example 2:
Input: s = "a", t = "a"
Output: "a"
Explanation: The entire string s is the minimum window.

Example 3:
Input: s = "a", t = "aa"
Output: ""
Explanation: Both 'a's from t must be included in the window.
Since the largest window of s only has one 'a', return empty string.
 
Constraints:
m == s.length
n == t.length
1 <= m, n <= 105
s and t consist of uppercase and lowercase English letters.
*/


class Solution {
    public String minWindow(String s, String t) {
        int m = s.length();
        int n = t.length();
        if (m < n) return "";
        
        Map<Character, Integer> requiredCountMap = new HashMap<>();
        for (int i = 0; i < t.length(); ++i) {
            char c = t.charAt(i);
            requiredCountMap.put(c, requiredCountMap.getOrDefault(c, 0) + 1);
        }
        
        Map<Character, Integer> countMap = new HashMap<>();
        int minLen = Integer.MAX_VALUE;
        int start = 0;
        int left = 0, right = 0;
        int meetCount = 0;
        while (right < s.length()) {
            // expand right
            char r = s.charAt(right);
            ++ right;
            countMap.put(r, countMap.getOrDefault(r, 0) + 1);
            
            // when the value > 128, they will not be equal because they are object event the number is same.
            // so intValue is very important.
            if (countMap.get(r).intValue() == requiredCountMap.getOrDefault(r, -1).intValue()) {
                ++ meetCount;
            }
            
            // shrink left
            while (meetCount == requiredCountMap.size()) {
                if (right - left < minLen) {
                    minLen = right - left;
                    start = left;
                }
                
                char l = s.charAt(left);
                ++ left;
                countMap.put(l, countMap.get(l) - 1);
                
                if (countMap.get(l) < requiredCountMap.getOrDefault(l, -1)) {
                    -- meetCount;
                }
            }
        }
        
        return minLen == Integer.MAX_VALUE ? "" : s.substring(start, start + minLen);
    }
}


//////////////////////////////////////////////////////////////////////
class Solution {
    public String minWindow(String s, String t) {
        int m = s.length();
        int n = t.length();
        if (m < n) {
            return "";
        }
        
        Map<Character, Integer> requiredCount = new HashMap<>();
        for (int i = 0; i < n; ++i) {
            char c = t.charAt(i);
            requiredCount.put(c, requiredCount.getOrDefault(c, 0) + 1);
        }
        
        Map<Character, Integer> count = new HashMap<>();
        int left = 0, right = 0;
        int meet = 0;
        int minLen = Integer.MAX_VALUE;  // 重点一，直接设成整数最大值
        int start = -1;
        
        while (right < m) {
            // expand right
            char cr = s.charAt(right);
            ++ right;
            
            if (requiredCount.containsKey(cr)) {
                count.put(cr, count.getOrDefault(cr, 0) + 1);
                if (count.get(cr).equals(requiredCount.get(cr))) {  // 重点二，不能用==，因为大于128，就不相等了，要用equals
                    ++ meet;
                }
            }
            
            // shrink left
            while (meet == requiredCount.size()) {
                // update result
                if (right - left < minLen) {
                    minLen = right - left;
                    start = left;
                }
                
                char cl = s.charAt(left);
                ++ left;
                
                if (requiredCount.containsKey(cl)) {
                    count.put(cl, count.get(cl) - 1);
                    if (count.get(cl) < requiredCount.get(cl)) {
                        -- meet;
                    }
                }
            }
        }
        
        return (start > -1) ? s.substring(start, start + minLen) : "";
    }
}

