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



/*
思路：
统计t中每个字符出现的位置。
对于s中的字符，判断它是否出现在t中，以及它的位置是否在t对应字符的位置范围中，在的话，记录最小的位置。
显然，这里判断位置是否在一个范围中很容易，但要找到最小的合适位置就要用到二分查找。
*/
#if 1
class Solution {
public:
    bool isSubsequence(string s, string t) {
        unordered_map<char, vector<int> > m;
        int pos = 0;
        for (auto c : t) {
            m[c].push_back(pos++);
        }

        pos = -1;
        for (auto c : s) {
            if (m.find(c) == m.end()) return false;
            vector<int> vi = m[c];
            if (vi.empty()) return false;
            if (vi[vi.size()-1] <= pos) {
                return false;
            } else {
                for (int i=0; i<vi.size(); ++i) {
                    if (vi[i] > pos) {
                        pos = vi[i];
                        break;
                    }
                }
            }
        }

        return true;
    }
};
#endif
