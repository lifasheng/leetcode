/*
very good!

Given two strings s and p, return an array of all the start indices of p's anagrams in s. You may return the answer in any order.

An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.

Example 1:
Input: s = "cbaebabacd", p = "abc"
Output: [0,6]
Explanation:
The substring with start index = 0 is "cba", which is an anagram of "abc".
The substring with start index = 6 is "bac", which is an anagram of "abc".

Example 2:
Input: s = "abab", p = "ab"
Output: [0,1,2]
Explanation:
The substring with start index = 0 is "ab", which is an anagram of "ab".
The substring with start index = 1 is "ba", which is an anagram of "ab".
The substring with start index = 2 is "ab", which is an anagram of "ab".

Constraints:
1 <= s.length, p.length <= 3 * 104
s and p consist of lowercase English letters.
*/



// very similar as #567 question
class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        int m = s.length();
        int n = p.length();
        List<Integer> res = new ArrayList<>();

        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();
        for (int i = 0; i < n; ++i) {
            char c = p.charAt(i);
            need.put(c, need.getOrDefault(c, 0) + 1);
        }

        int meet = 0;
        int left = 0, right = 0;
        while (right < m) {
            char cr = s.charAt(right);
            ++ right;

            window.put(cr, window.getOrDefault(cr, 0) + 1);

            if (need.containsKey(cr) && window.get(cr).equals(need.get(cr))) {
                ++ meet;
            }

            while (meet == need.size()) {
                if (right - left == n) {
                    res.add(left);
                }

                char cl = s.charAt(left);
                ++ left;

                window.put(cl, window.get(cl) - 1);

                if (window.get(cl) < need.getOrDefault(cl, 0)) {
                    -- meet;
                }
            }
        }

        return res;
    }
}



