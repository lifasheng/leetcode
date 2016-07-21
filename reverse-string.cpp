#include <iostream>
#include <cassert>
#include <string>
using namespace std;

/*
Write a function that takes a string as input and returns the string reversed.

Example:
Given s = "hello", return "olleh". 
*/

class Solution {
public:
    string reverseString(string s) {
        int i=0, j = s.length()-1;
        while(i <= j ) {
            std::swap(s[i], s[j]);
            ++i;
            --j;
        }
        return s;
    }
};

int main() {
    Solution solution;
    string s1 = "hello";
    cout <<s1 << " => " << solution.reverseString(s1) << endl;
    string s2 = "hello world!";
    cout <<s2 << " => " << solution.reverseString(s2) << endl;

    return 0;
}
