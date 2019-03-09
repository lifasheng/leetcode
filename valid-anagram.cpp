/*

242. Valid Anagram
Easy

Given two strings s and t , write a function to determine if t is an anagram of s.

Example 1:

Input: s = "anagram", t = "nagaram"
Output: true
Example 2:

Input: s = "rat", t = "car"
Output: false
Note:
You may assume the string contains only lowercase alphabets.

Follow up:
What if the inputs contain unicode characters? How would you adapt your solution to such case?

*/

class Solution {
public:
    #if 0
    bool isAnagram(string s, string t) {
        if (s.size() != t.size()) return false;
        
        std::sort(s.begin(), s.end());
        std::sort(t.begin(), t.end());
        
        return s == t;
    }
    #endif
    
    #if 1
    bool isAnagram(string s, string t) {
        if (s.size() != t.size()) return false;
        
        unordered_map<char, int> m;
        for(auto c : s) {
            m[c]++;
        }
        for (auto c: t) {
            m[c] --;
        }
        
        for (auto e : m) {
            if (e.second != 0) return false;
        }
        
        return true;
    }
    #endif
};
