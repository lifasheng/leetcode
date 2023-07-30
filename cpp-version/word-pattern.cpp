#include <iostream>
#include <cassert>
#include <string>
#include <sstream>
#include <vector>
#include <unordered_map>
using namespace std;

/*
Given a pattern and a string str, find if str follows the same pattern.

Here follow means a full match, such that there is a bijection between a letter in pattern and a non-empty word in str.

Examples:

    pattern = "abba", str = "dog cat cat dog" should return true.
    pattern = "abba", str = "dog cat cat fish" should return false.
    pattern = "aaaa", str = "dog cat cat dog" should return false.
    pattern = "abba", str = "dog dog dog dog" should return false.

Notes:
You may assume pattern contains only lowercase letters, and str contains lowercase letters separated by a single space. 
*/

class Solution {
public:
    bool wordPattern(string pattern, string str) {
        stringstream ss(str);
        vector<string> words;
        string word;
        while(ss >> word) {
            words.push_back(word);
        }

        if (pattern.size() != words.size()) {
            return false;
        }

        unordered_map<char, string> dictCS;
        unordered_map<string, char> dictSC;
        for(size_t i=0; i<pattern.size(); ++i) {
            int c1 = dictCS.count(pattern[i]);
            int c2 = dictSC.count(words[i]);
            if (c1 == 0 && c2 == 0) {
                dictCS[pattern[i]] = words[i];
                dictSC[words[i]] = pattern[i];
            }
            else if (c1 != 0 && c2 != 0) {
                if (!( (dictCS[pattern[i]] == words[i]) && (dictSC[words[i]] == pattern[i]) )) {
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

    string pattern, str;

    pattern = "abba"; str = "dog cat cat dog";
    assert(solution.wordPattern(pattern, str)  == true);

    pattern = "abba"; str = "dog cat cat fish";
    assert(solution.wordPattern(pattern, str) ==  false);

    pattern = "aaaa"; str = "dog cat cat fish";
    assert(solution.wordPattern(pattern, str) == false);

    pattern = "abba"; str = "dog dog dog dog";
    assert(solution.wordPattern(pattern, str) == false);

    return 0;
}
