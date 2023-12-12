/*
Medium

very good!

Given two strings s1 and s2, return true if s2 contains a permutation of s1, or false otherwise.

In other words, return true if one of s1's permutations is the substring of s2.

Example 1:
Input: s1 = "ab", s2 = "eidbaooo"
Output: true
Explanation: s2 contains one permutation of s1 ("ba").

Example 2:
Input: s1 = "ab", s2 = "eidboaoo"
Output: false
 
Constraints:
1 <= s1.length, s2.length <= 104
s1 and s2 consist of lowercase English letters.
*/

class Solution {
    public boolean checkInclusion(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        if (m > n) return false;

        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();

        for (int i = 0; i < m; ++i) {
            char c = s1.charAt(i);
            need.put(c, need.getOrDefault(c, 0) + 1);
        }

        int meet = 0;
        int left = 0, right = 0;
        while (right < n) {
            char cr = s2.charAt(right);
            ++ right;

            window.put(cr, window.getOrDefault(cr, 0) + 1);
            if (need.containsKey(cr) && window.get(cr).equals(need.get(cr))) {
                ++ meet;
            }

            while (meet == need.size()) {
                // 如果子串长度和s1相等，并且meet == need.size(), 说明肯定没有多余的其它字符。
                if ((right - left) == m) {
                    return true;
                }

                char cl = s2.charAt(left);
                ++ left;

                window.put(cl, window.get(cl) - 1);

                if (window.get(cl) < need.getOrDefault(cl, 0)) {
                    -- meet;
                }
            }
        }

        return false;
    }
}



