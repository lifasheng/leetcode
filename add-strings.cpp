/*
Given two non-negative integers num1 and num2 represented as string, return the sum of num1 and num2.

Note:

The length of both num1 and num2 is < 5100.
Both num1 and num2 contains only digits 0-9.
Both num1 and num2 does not contain any leading zero.
You must not use any built-in BigInteger library or convert the inputs to integer directly.
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
    string addStrings(string num1, string num2) {
        std::reverse(num1.begin(), num1.end());
        std::reverse(num2.begin(), num2.end());
        string result;
        auto it1 = num1.begin(), it2 = num2.begin();
        int r = 0;
        while(it1 != num1.end() && it2 != num2.end()) {
            int x = *it1 - '0';
            int y = *it2 - '0';
            int z = x + y + r;
            result += z%10 + '0';
            r = z/10;
            ++it1;
            ++it2;
        }
        while (it1 != num1.end()) {
            int x = *it1 - '0';
            int z = x + r;
            result += z%10 + '0';
            r = z/10;
            ++it1;
        }
        while (it2 != num2.end()) {
            int y = *it2 - '0';
            int z = y + r;
            result += z%10 + '0';
            r = z/10;
            ++it2;
        }
        if (r) {
            result += r + '0';
        }
        std::reverse(result.begin(), result.end());
        return result;
    }
};


int main() {
    Solution s;
    cout <<s.addStrings("497", "986")<<endl;
    cout <<s.addStrings("1023", "99")<<endl;
    return 0;
}


