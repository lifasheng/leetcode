#include <iostream>
#include <cassert>
#include <string>
using namespace std;

/*
Given an integer (signed 32 bits), write a function to check whether it is a power of 4.

Example:
Given num = 16, return true. Given num = 5, return false.

Follow up: Could you solve it without loops/recursion? 
*/

#if 0
class Solution {
public:
    bool isPowerOfFour(int num) {
        if (num <=0) return false;
        if (num == 1) return true;
       return num%4 == 0 && isPowerOfFour(num/4); 
    }
};
#else
class Solution {
public:
    /*
     The basic idea is from power of 2, We can use "n&(n-1) == 0" to determine if n is power of 2. 
     For power of 4, the additional restriction is that in binary form, the only "1" should always located at the odd position. 
     For example, 4^0 = 1, 4^1 = 100, 4^2 = 10000. So we can use "num & 0x55555555==num" to check if "1" is located at the odd position.


     if num <0, then (num & (num-1)) will < 0 because the left most bit is 1.
     */
    bool isPowerOfFour(int num) {
        return ((num & (num - 1)) == 0) && (num & 0x55555555);
    }
};
#endif

int main() {
    Solution solution;

    for (int i=-20; i< 50; ++i) {
        cout <<i << " => " <<  solution.isPowerOfFour(i) << endl;
    }

    return 0;
}
