/*

Given a string and an integer k, you need to reverse the first k characters for every 2k characters counting from the start of the string. If there are less than k characters left, reverse all of them. If there are less than 2k but greater than or equal to k characters, then reverse the first k characters and left the other as original.
Example:
Input: s = "abcdefg", k = 2
Output: "bacdfeg"
Restrictions:
The string consists of lower English letters only.
Length of the given string and k will in the range [1, 10000]

*/

#include<string>
#include<iostream>
using namespace std;

class Solution {
public:
    string reverseStr(string s, int k) {
        if (k<=1) return s;
        int size = s.size();
        int i=0;
        while(i<size) {
            reverseStr(s, i, min(size, i+k));
            i += 2*k;
        }
        return s;
    }
    
    void reverseStr(string &s, int beg, int end) {
        int i=beg, j=end-1;
        while(i<j) {
            std::swap(s[i], s[j]);
            ++i;
            --j;
        }
    }
};


int main() {
    Solution s;
    string str = "krmyfshbspcgtesxnnljhfursyissjnsocgdhgfxubewllxzqhpasguvlrxtkgatzfybprfmmfithphckksnvjkcvnsqgsgosfxc";
    string expected = "jlnnxsetgcpsbhsfymrkhfursyissjnsocgdhgfxtxrlvugsaphqzxllwebukgatzfybprfmmfithphccxfsogsgqsnvckjvnskk";
    int k = 20;
    cout << s.reverseStr(str, 20) << endl;
    cout << expected << endl;
    return 0;
}
