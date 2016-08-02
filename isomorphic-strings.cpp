#include <iostream>
#include <cassert>
#include <string>
#include <unordered_map>
using namespace std;

/*
Given two strings s and t, determine if they are isomorphic.

Two strings are isomorphic if the characters in s can be replaced to get t.

All occurrences of a character must be replaced with another character while preserving the order of characters. No two characters may map to the same character but a character may map to itself.

For example,
Given "egg", "add", return true.

Given "foo", "bar", return false.

Given "paper", "title", return true.

Note:
You may assume both s and t have the same length.
*/


// Note that this problem is very similar with 'word-pattern'
class Solution {
public:
    bool isIsomorphic(string s, string t) {
        if (s.size() != t.size()) return false;

        unordered_map<char, char> mst;
        unordered_map<char, char> mts;
        for(size_t i=0; i<s.size(); ++i) {
            int c1 = mst.count(s[i]);
            int c2 = mts.count(t[i]);
            if (c1 == 0 && c2 == 0) {
                mst[s[i]] = t[i];
                mts[t[i]] = s[i];
            }
            else if (c1 && c2) {
                if (mst[s[i]] != t[i] || mts[t[i]] != s[i]) {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        return true;
    }
};

int main() {
    Solution solution;

    string s = "egg", t = "add";
    assert(solution.isIsomorphic(s, t) == true);

    s = "foo", t = "bar";
    assert(solution.isIsomorphic(s, t) == false);

    s = "paper", t = "title";
    assert(solution.isIsomorphic(s, t) == true);

    s = "ab", t = "aa";
    assert(solution.isIsomorphic(s, t) == false);

    return 0;
}
