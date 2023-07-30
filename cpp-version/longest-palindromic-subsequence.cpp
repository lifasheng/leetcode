/*
Given a string s, find the longest palindromic subsequence's length in s. You may assume that the maximum length of s is 1000.

Example 1:
Input:

"bbbab"
Output:
4
One possible longest palindromic subsequence is "bbbb".
Example 2:
Input:

"cbbd"
Output:
2
One possible longest palindromic subsequence is "bb".
*/

// 递归方法： 超时了。
// test case: "abab", "bcbd", "abcdef"
class Solution {
public:
    int longestPalindromeSubseq(string s) {
        unordered_map<string, int> m1; // s的最长palindrome子串
        unordered_map<string, bool> m2; // s是否为palindrome
        
        return longestPalindromeSubseq(s,m1, m2);
    }
    
    int longestPalindromeSubseq(string &s, unordered_map<string, int> &m1, unordered_map<string, bool> &m2) {
        if (s.size()<=1) return s.size();
        if (m1.find(s) == m1.end()) {
            if (isPalindrome(s, m2)) {
                m1[s] = s.size();
            } else {
                int maxSubLen = 0;
                for(int i=0; i<s.size();++i) {
                    string s2 = s.substr(0, i) + s.substr(i+1);
                    if (isPalindrome(s2, m2)) {
                        int size = s2.size();
                        maxSubLen = max(maxSubLen, size);
                    } else {
                        maxSubLen = max(maxSubLen, longestPalindromeSubseq(s2,m1,m2));
                    }
                }
                m1[s] = maxSubLen;
            }
        }
        return m1[s];
    }
    
    bool isPalindrome(string &s, unordered_map<string, bool> &m2) {
        if (m2.find(s) == m2.end()) {
            int left = 0;
            int right = s.size()-1;
            bool result = true;
            while(left <= right) {
                if (s[left] != s[right]) {
                    result = false;
                    break;
                }
                ++left;
                --right;
            }
            m2[s] = result;
        }
        return m2[s];
    }
};



//===================================

// test case: "abab", "bcbd", "abcdef"
// 动态规划：
// f[i][j] 表示i到j直接的最长回文子串
// f[i][j] = max(f[i+1][j], f[i][j-1])  when s[i]!=s[j]
// f[i][j] = max(f[i+1][j], f[i][j-1], f[i+1][j-1]+2)  when s[i]==s[j]
class Solution {
public:
    int longestPalindromeSubseq(string s) {
        const size_t n = s.size();
        if (n<=1) return n;
        
        vector<vector<int>> f(n,vector<int>(n,0));
        int result = 1;
        for(int i=n-1;i>=0;--i) {
            f[i][i]=1;
            for(int j=i+1;j<n;++j) {
                f[i][j] = max(f[i+1][j], f[i][j-1]);
                if (s[i] == s[j]) {
                    f[i][j] = max(f[i][j], f[i+1][j-1]+2);
                }
                result = max(result, f[i][j]);
            }
        }
        
        return result;
    }
};


