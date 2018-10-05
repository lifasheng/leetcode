/*
Find the nth digit of the infinite integer sequence 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, ...

Note:
n is positive and will fit within the range of a 32-bit signed integer (n < 231).

Example 1:

Input:
3

Output:
3
Example 2:

Input:
11

Output:
0

Explanation:
The 11th digit of the sequence 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, ... is a 0, which is part of the number 10.

*/

/*
Basic idea：

1-9： 9 of 1 digit
10-99： 90 of 2 digits
100-999： 900 of 3 digits

so flatten each number from 1 to n digit by digit, we will get:

9 * 10^(1-1) * 1 + 9 * 10^(2-1) * 2 + 9 * 10^(3-1) * 3 + 9 * 10^(4-1) * 4 + ...    this is the number of digits.

let's take n=203 as example,
in below code, 
x will be 9 * 10^(1-1) * 1 + 9 * 10^(2-1) * 2 + 9 * 10^(3-1) * 3 
y will be 9 * 10^(1-1) * 1 + 9 * 10^(2-1) * 2 
digitLen will be 3.

so remain = n-y = 203 - (9+180) = 14, which means it is the 14th digit in all normal numbers with digitLen = 3.
it starts from 10^(digitLen-1) = 10^(3-1) = 100, 100 is the first number of digitLen=3, then 101, 102, 103, 104
a = 14 / 3 = 4, this is the kth number in all numbers with digitLen = 3;
b = 14 % 3 = 2, this is the digit position in the specific number;
since b != 0, a ++ will be 5;
we can get the target = 100 + 5-1 = 104
we know the position is b = 2, so return 0.
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
    int findNthDigit(int n) {
        long x = 0, y = 0;
        int digitLen = 1;
        while(x<n) {
            y = x;
            x += 9 * pow(10, digitLen-1) * digitLen;
            ++digitLen;
            // cout << "y: " << y << ", x: " << x << ", digitLen: " << digitLen << endl;
        }
        -- digitLen;

        int remain = n - y;

        int a = remain / digitLen;
        int b = remain % digitLen;
        if (b) {
            ++a;
        }

        int target = pow(10, digitLen-1) + a-1;

        cout << "n: " << n << ", x: " << x << ", y: " << y << ", digitLen: " << digitLen;
        cout << ", a: " << a << ", b: " << b << ", target: " << target << endl;

        std::string s = std::to_string(target);
        if (b) {
            return s[b-1] - '0'; // !!! remember - '0'
        } 
        return s[s.size()-1] - '0'; // !!! remember - '0'
    }
};


int main() {
    Solution s;
    cout <<s.findNthDigit(11)<<endl;
    return 0;
}

