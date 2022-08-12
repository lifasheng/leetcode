/*
Easy

Given a string s which consists of lowercase or uppercase letters, return the length of the longest palindrome that can be built with those letters.

Letters are case sensitive, for example, "Aa" is not considered a palindrome here.

Example 1:
Input: s = "abccccdd"
Output: 7
Explanation: One longest palindrome that can be built is "dccaccd", whose length is 7.

Example 2:
Input: s = "a"
Output: 1
Explanation: The longest palindrome that can be built is "a", whose length is 1.

Constraints:
1 <= s.length <= 2000
s consists of lowercase and/or uppercase English letters only.
*/

class Solution {
    public int longestPalindrome(String s) {
        if (s == null || s.isEmpty()) return 0;
        
        Map<Character, Integer> countMap = new HashMap<>();
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (countMap.containsKey(c)) {
                countMap.put(c, countMap.get(c) + 1);
            } else {
                countMap.put(c, 1);
            }
        }
        
        boolean hasOdd = false;
        int n = 0;
        for (Integer count : countMap.values()) {
            if (count % 2 == 0) {
                n += count;
            } else {
                hasOdd = true;
                n += (count - 1);
            }
        }
        return hasOdd ? (n + 1) : n;
    }
}

