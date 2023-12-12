/*
Hard

very very good!

Given two strings s and t of lengths m and n respectively, return the minimum window 
substring of s such that every character in t (including duplicates) is included in the window. If there is no such substring, return the empty string "".

The testcases will be generated such that the answer is unique.


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

        Map<Character, Integer> needs = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();

        for (int i = 0; i < n; ++i) {
            char c = t.charAt(i);
            needs.put(c, needs.getOrDefault(c, 0) + 1);
        }

        int meet = 0;
        int left = 0, right = 0;
        int minLen = m + 1;
        int startIndex = -1;
        while (right < m) {
            // 扩大窗口
            char cr = s.charAt(right);
            ++ right;

            // 进行窗口内数据的一系列更新
            if (needs.containsKey(cr)) {
                window.put(cr, window.getOrDefault(cr, 0) + 1);
                if (window.get(cr).equals(needs.get(cr))) {
                    ++ meet; // 只有当 window[c] 和 need[c] 对应的出现次数一致时，才能满足条件，才能 +1
                }
            }

            // 判断左侧窗口是否要收缩
            while (left < right && meet == needs.size()) {
                // 更新最小覆盖子串
                int len = right - left;
                if (len < minLen) {
                    minLen = len;
                    startIndex = left;
                }

                // 缩小窗口
                char cl = s.charAt(left);
                ++ left;

                // 进行窗口内数据的一系列更新
                if (needs.containsKey(cl)) {
                    if (window.get(cl).equals(needs.get(cl))) {
                        -- meet;  // 只有当 window[c] 和 need[c] 对应的出现次数一致时，才能满足条件，才能 -1
                    }
                    window.put(cl, window.get(cl) - 1);
                }
            }
        }
        return startIndex == -1 ? "" : s.substring(startIndex, startIndex + minLen);
    }
}




