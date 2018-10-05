/*
Count the number of segments in a string, where a segment is defined to be a contiguous sequence of non-space characters.

Please note that the string does not contain any non-printable characters.

Example:

Input: "Hello, my name is John"
Output: 5

*/

#include <iostream>
#include <vector>
#include <unordered_map>
#include <cassert>
#include <cmath>
#include <string>
#include<stdio.h>
using namespace std;

class Solution {
public:
    int countSegments(string s) {
        s += " "; // to help to process the last segment easily.
        int n = 0;
        bool isSeg = false;
        for(int i=0; i<s.size(); ++i) {
            if (std::isspace(s[i])) {
                if (isSeg) {
                    ++n;
                }
                isSeg = false;
            } else {
                isSeg = true;
            }
        }
        return n;
    }
};


int main() {
    Solution s;
    cout <<s.countSegments("Hello,,  myname is John")<<endl;
    return 0;
}
