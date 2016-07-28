#include <iostream>
#include <cassert>
#include <string>
using namespace std;

/*
Given an integer, write a function to determine if it is a power of two. 
*/


class Solution {
public:
    bool isPowerOfTwo(int n) {
        if (n<=0) return false;
        return (n & (n-1)) == 0;
    }
};

int main() {
    Solution solution;

    assert(solution.isPowerOfTwo(0) == false);
    assert(solution.isPowerOfTwo(1) == true);
    assert(solution.isPowerOfTwo(2) == true);
    assert(solution.isPowerOfTwo(3) == false);
    assert(solution.isPowerOfTwo(4) == true);
    assert(solution.isPowerOfTwo(5) == false);
    assert(solution.isPowerOfTwo(6) == false);
    assert(solution.isPowerOfTwo(8) == true);
    assert(solution.isPowerOfTwo(12) == false);
    assert(solution.isPowerOfTwo(16) == true);

    return 0;
}
