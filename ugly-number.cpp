#include <iostream>
#include <cassert>
#include <string>
#include <sstream>
#include <vector>
#include <unordered_map>
using namespace std;

/*
 Write a program to check whether a given number is an ugly number.

Ugly numbers are positive numbers whose prime factors only include 2, 3, 5. For example, 6, 8 are ugly while 14 is not ugly since it includes another prime factor 7.

Note that 1 is typically treated as an ugly number. 
*/

#if 1
// after submiting to leetcode, i found thta the recursive version is faster.
class Solution {
public:
    bool isUgly(int num) {
        if (num <= 0) return false;
        if (num == 1) return true;
        if (num % 2 == 0) return isUgly(num/2);
        if (num % 3 == 0) return isUgly(num/3);
        if (num % 5 == 0) return isUgly(num/5);
        return false;
    }
};
#else
class Solution {
public:
    bool isUgly(int num) {
        if (num <= 0) return false;
        while (num % 2 == 0) num >>= 1;
        while (num % 3 == 0) num /= 3;
        while (num % 5 == 0) num /= 5;
        return num == 1;
    }
};
#endif

int main() {
    Solution solution;

    assert(solution.isUgly(-2) == false);
    assert(solution.isUgly(0) == false);
    assert(solution.isUgly(2) == true);
    assert(solution.isUgly(3) == true);
    assert(solution.isUgly(5) == true);
    assert(solution.isUgly(7) == false);
    assert(solution.isUgly(8) == true);
    assert(solution.isUgly(14) == false);

    return 0;
}
