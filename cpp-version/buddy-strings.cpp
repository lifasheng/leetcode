/*
Given two strings A and B of lowercase letters, return true if and only if we can swap two letters in A so that the result equals B.

 

Example 1:

Input: A = "ab", B = "ba"
Output: true
Example 2:

Input: A = "ab", B = "ab"
Output: false
Example 3:

Input: A = "aa", B = "aa"
Output: true
Example 4:

Input: A = "aaaaaaabc", B = "aaaaaaacb"
Output: true
Example 5:

Input: A = "", B = "aa"
Output: false
 

Note:

0 <= A.length <= 20000
0 <= B.length <= 20000
A and B consist only of lowercase letters.
*/

#include <iostream>
#include <vector>
#include <unordered_map>
#include <cassert>
#include <cmath>
#include <numeric>
#include <string>
#include<stdio.h>
using namespace std;


class Solution {
public:
    bool buddyStrings(string A, string B) {
        int lenA = A.length(), lenB = B.length();
        if (lenA != lenB) return false;

        int diffChars = 0;
        string diffPartOfA = "";
        string diffPartOfB = "";
        for(int i=0; i<lenA; ++i) {
            if (A[i] != B[i]) {
                ++diffChars;
                diffPartOfA += A[i];
                diffPartOfB += B[i];
            }
        }

        if (diffChars > 2 || diffChars == 1) return false;
        
        //abcaa != abcbb, abcde != abced
        if (diffChars == 2) {
            return diffPartOfA[0] == diffPartOfB[1] 
            && diffPartOfA[1] == diffPartOfB[0];
        }

        // diffChars == 0
        // aa == aa, ab == ab, abc == abc, aaab == aaab
        int m[26];
        fill_n(m, 26, 0);
        for(auto c : A) {
            if (++m[c-'a'] > 1) {
                return true;
            }
        }

        return false;
    }
};

int main() {
    Solution s;
    cout <<s.buddyStrings("", "ab")<<endl;
    cout <<s.buddyStrings("ab", "ab")<<endl;
    cout <<s.buddyStrings("aa", "aa")<<endl;
    cout <<s.buddyStrings("aab", "aab")<<endl;
    cout <<s.buddyStrings("abcaa", "abcbb")<<endl;

    return 0;
}

