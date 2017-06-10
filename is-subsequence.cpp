/*
Given a string s and a string t, check if s is subsequence of t.

You may assume that there is only lower case English letters in both s and t. t is potentially a very long (length ~= 500,000) string, and s is a short string (<=100).

A subsequence of a string is a new string which is formed from the original string by deleting some (can be none) of the characters without disturbing the relative positions of the remaining characters. (ie, "ace" is a subsequence of "abcde" while "aec" is not).

Example 1:
s = "abc", t = "ahbgdc"

Return true.

Example 2:
s = "axc", t = "ahbgdc"

Return false.

Follow up:
If there are lots of incoming S, say S1, S2, ... , Sk where k >= 1B, and you want to check one by one to see if T has its subsequence. In this scenario, how would you change your code?
*/


#if 0
// my solution: 递归, 注意测试用例就OK.
class Solution {
public:
    bool isSubsequence(string s, string t) {
        return isSubsequence(s.begin(), s.end(), t.begin(), t.end());
    }
    bool isSubsequence(string::iterator sbeg, string::iterator send, string::iterator tbeg, string::iterator tend) {
        if ( (send-sbeg) > (tend-tbeg) ) return false;
        if (sbeg == send) return true;
        
        auto pt = find(tbeg, tend, *sbeg);
        if (pt == tend) return false;
        return isSubsequence(next(sbeg), send, next(pt), tend);
    }
    
};
#endif
