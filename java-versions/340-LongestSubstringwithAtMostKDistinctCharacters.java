/*
Given a string s and an integer k, return the length of the longest substring of s that contains at most k distinct characters.

Example 1:
Input: s = "eceba", k = 2
Output: 3
Explanation: The substring is "ece" with length 3.

Example 2:
Input: s = "aa", k = 1
Output: 2
Explanation: The substring is "aa" with length 2.
 
Constraints:
1 <= s.length <= 5 * 104
0 <= k <= 50
*/

class Solution {
    // sliding window
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        Map<Character, Integer> m = new HashMap<>();
        
        int maxLen = 0;
        int left = 0, right = 0;
        while (right < s.length()) {
            // slide right
            char r = s.charAt(right);
            ++ right;
            m.put(r, m.getOrDefault(r, 0) + 1);
            
            // shrink left
            while (m.size() > k) {
                char l = s.charAt(left);
                ++ left;
                
                m.put(l, m.get(l) - 1);
                if (m.get(l) == 0) {
                    m.remove(l);
                }
            }
            
            // update result
            maxLen = Math.max(maxLen, right - left);
        }
        return maxLen;
    }
}

