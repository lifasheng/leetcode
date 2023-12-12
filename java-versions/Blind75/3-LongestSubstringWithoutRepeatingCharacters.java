/*
very good!


Given a string s, find the length of the longest 
substring
 without repeating characters.

Example 1:
Input: s = "abcabcbb"
Output: 3
Explanation: The answer is "abc", with the length of 3.

Example 2:
Input: s = "bbbbb"
Output: 1
Explanation: The answer is "b", with the length of 1.

Example 3:
Input: s = "pwwkew"
Output: 3
Explanation: The answer is "wke", with the length of 3.
Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
 
Constraints:
0 <= s.length <= 5 * 104
s consists of English letters, digits, symbols and spaces.
*/


class Solution {
    public int lengthOfLongestSubstring(String s) {
        int n = s.length();
        if (n <= 1) return n;

        Map<Character, Integer> window = new HashMap<>();

        int left = 0, right = 0;
        int maxLen = 0;
        while (right < n) {
            char cr = s.charAt(right);
            ++ right;

            window.put(cr, window.getOrDefault(cr, 0) + 1);

            while (window.get(cr) > 1) {
                char cl = s.charAt(left);
                ++ left;
                
                window.put(cl, window.get(cl) - 1);
            }

            maxLen = Math.max(maxLen, right - left);
        }
        return maxLen;
    }
}



