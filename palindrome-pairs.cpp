/*
Given a list of unique words, find all pairs of distinct indices (i, j) in the given list, so that the concatenation of the two words, i.e. words[i] + words[j] is a palindrome.

Example 1:
Given words = ["bat", "tab", "cat"]
Return [[0, 1], [1, 0]]
The palindromes are ["battab", "tabbat"]
Example 2:
Given words = ["abcd", "dcba", "lls", "s", "sssll"]
Return [[0, 1], [1, 0], [3, 2], [2, 4]]
The palindromes are ["dcbaabcd", "abcddcba", "slls", "llssssll"]
*/


// 思路：
// 如果A串长度小于B串，则AB是否为回文，只需翻转A，然后到B后面去匹配
// 如果A串长度大于B串，则AB是否为回文，只需翻转B，然后到A前面去匹配
// 可惜超时了！
// test case: ["a",""] => [[0,1],[1,0]]
class Solution {
public:
    vector<vector<int>> palindromePairs(vector<string>& words) {
        vector<vector<int>> result;
        for (int i=0; i<words.size(); ++i) {
            string w = words[i];
            for(int j=0; j<words.size(); ++j) {
                if (i!=j) {
                    string w2 = words[j];
                    if (w2.size() >= w.size()) {
                        string rw = w;
                        std::reverse(rw.begin(), rw.end());
                        auto p = w2.rfind(rw); 
                        if (p != string::npos && p+rw.size() == w2.size()) {
                            if (isPalindrome(w2, 0, p-1)) {
                                result.push_back({i,j});
                            }
                        }
                    } else { // w2.size() < w.size()
                        string rw = w2;
                        std::reverse(rw.begin(), rw.end());
                        auto p = w.find(rw); 
                        if (p != string::npos && p == 0) {
                            if (isPalindrome(w, p+rw.size(), w.size()-1)) {
                                result.push_back({i,j});
                            }
                        }
                    }
                }
            }
        }
        return result;
    }
    
    bool isPalindrome(string &s, int left, int right) {
        while(left<=right) {
            if (s[left]!=s[right]) return false;
            ++left;
            --right;
        }
        return true;
    }
};
