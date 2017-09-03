/*
Given a string which consists of lowercase or uppercase letters, find the length of the longest palindromes that can be built with those letters.

This is case sensitive, for example "Aa" is not considered a palindrome here.

Note:
Assume the length of given string will not exceed 1,010.

Example:

Input:
"abccccdd"

Output:
7

Explanation:
One longest palindrome that can be built is "dccaccd", whose length is 7.
*/


class Solution {
public:
    int longestPalindrome(string s) {
        unordered_map<char, int> m;
        for(auto c:s) {
            m[c]++;
        }
        int numOfTwo = 0;
        int numOfOne = 0;
        for(auto p:m) {
            if (p.second % 2 == 0) {
                numOfTwo += p.second;
            } else {
                ++numOfOne;
                numOfTwo += p.second - 1;
            }
        }
 
        return numOfTwo + (numOfOne>0?1:0); //注意 ?:的优先级比+要低，所以要加括号！
    }
};

