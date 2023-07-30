#include <iostream>
#include <cassert>
#include <string>
using namespace std;

/*
vowels: aeiou

Write a function that takes a string as input and reverse only the vowels of a string.

Example 1:
Given s = "hello", return "holle".

Example 2:
Given s = "leetcode", return "leotcede".

Note:
The vowels does not include the letter "y". 
*/

class Solution {
private:
public:
    bool isVowel(char c) {
        // note lower and upper case
        static string vowels="aeiouAEIOU";
        return vowels.find(c) != std::string::npos;
    }
    string reverseVowels(string s) {
        int i=0, j=s.length()-1;
        while(i < j) {
            if (!isVowel(s[i])) {
                ++i;
            }
            else if (!isVowel(s[j])) {
                --j;
            }
            else {
                std::swap(s[i], s[j]);
                ++i;
                --j;
            }
        }
        return s;
    }
};

int main() {
    Solution solution;

    string s1 = "hello", s2 = "leetcode", s3 = "aA";
    cout << solution.reverseVowels(s1) << endl;
    cout << solution.reverseVowels(s2) << endl;
    cout << solution.reverseVowels(s3) << endl;

    return 0;
}
