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
// 改进空间：
// 1. reverse这一步可以提前对每个string做一遍，存到hash中
// 2. isPalindrome 也可以缓存起来？

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





////////////////////////////////////
// https://discuss.leetcode.com/topic/40654/easy-to-understand-ac-c-solution-o-n-k-2-using-map

Assumption: No duplicated string in the given dictionary

Steps:

Traverse the array, build map. Key is the reversed string, value is index in array (0 based)

Edge case - check if empty string exists. It's interesting that for given words {"a", ""}, it's expected to return two results [0,1] and [1,0]. Since my main logic can cover [0, 1] concatenate("a", ""), so as to cover the other situation concatenate("", "a"), I need to traverse the words array again, find the palindrome word candidate except "" itself, and add pair("", palindrome word) to the final answer.

Main logic part. Partition the word into left and right, and see 1) if there exists a candidate in map equals the left side of current word, and right side of current word is palindrome, so concatenate(current word, candidate) forms a pair: left | right | candidate. 2) same for checking the right side of current word: candidate | left | right.

    class Solution {
    public:
        vector<vector<int>> palindromePairs(vector<string>& words) {
            unordered_map<string, int> dict;
            vector<vector<int>> ans;
            // build dictionary
            for(int i = 0; i < words.size(); i++) {
                string key = words[i];
                reverse(key.begin(), key.end());
                dict[key] = i;
            }
            // edge case: if empty string "" exists, find all palindromes to become pairs ("", self)
            if(dict.find("")!=dict.end()){
                for(int i = 0; i < words.size(); i++){
                    if(i == dict[""]) continue;
                    if(isPalindrome(words[i])) ans.push_back({dict[""], i}); // 1) if self is palindrome, here ans covers concatenate("", self) 
                }
            }

            for(int i = 0; i < words.size(); i++) {
                for(int j = 0; j < words[i].size(); j++) {
                    string left = words[i].substr(0, j);
                    string right = words[i].substr(j, words[i].size() - j);

                    if(dict.find(left) != dict.end() && isPalindrome(right) && dict[left] != i) {
                        ans.push_back({i, dict[left]});     // 2) when j = 0, left = "", right = self, so here covers concatenate(self, "")
                    }

                    if(dict.find(right) != dict.end() && isPalindrome(left) && dict[right] != i) {
                        ans.push_back({dict[right], i});
                    }
                }
            }

            return ans;
        }

        bool isPalindrome(string str){
            int i = 0;
            int j = str.size() - 1; 

            while(i < j) {
                if(str[i++] != str[j--]) return false;
            }

            return true;
        }

    };





///////////////////////
/*
Thanks for your detailed explanation! The idea of establishing dict and reversing words[i] at the same time is faster compared with that of reversing left and right, which I find in some other similar posts.

What's more, here is a little improvement for the edge case: when there exists empty string, instead of using a separate loop for traversal, we can handle it in the "magic" part. When left is empty and right is a palindrome, apart from {i, dict[left]}, we can also add {dict[left], i} to result so that no valid pair is missing.

Following is my implementation, just for the reference.

*/

class Solution {
public:
    vector<vector<int>> palindromePairs(vector<string>& words) {
        vector<vector<int> > result;
        unordered_map<string, int> dict;
        int i, j, size = words.size();
        string left, right, tmp;
        for(i = 0; i < size; i++) {
            tmp = words[i];
            reverse(tmp.begin(), tmp.end());
            dict[tmp] = i;
        }

        for(i = 0; i < size; i++) {
            for(j = 0; j < words[i].size(); j++) {
                left = words[i].substr(0, j);
                right = words[i].substr(j);
                if(dict.find(left) != dict.end() && dict[left] != i && isPalindrome(right)) {
                    result.push_back({i, dict[left]});
                    if(left.empty())
                        result.push_back({dict[left], i});
                }
                if(dict.find(right) != dict.end() && dict[right] != i && isPalindrome(left))
                    result.push_back({dict[right], i});
            }
        }
        return result;
    }

private:
    bool isPalindrome(string s) {
        int start, end, size = s.size();
        for(start = 0, end = size - 1; start < end; start++, end--) {
            if(s[start] != s[end])
                return false;
        }
        return true;
    }
};
