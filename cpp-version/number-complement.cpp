/*
Given a positive integer, output its complement number. The complement strategy is to flip the bits of its binary representation.

Note:
The given integer is guaranteed to fit within the range of a 32-bit signed integer.
You could assume no leading zero bit in the integer’s binary representation.
Example 1:
Input: 5
Output: 2
Explanation: The binary representation of 5 is 101 (no leading zero bits), and its complement is 010. So you need to output 2.
Example 2:
Input: 1
Output: 0
Explanation: The binary representation of 1 is 1 (no leading zero bits), and its complement is 0. So you need to output 0.
*/

class Solution {
public:
    // 先找到这个正数有几位，然后构造mask
    // 比如101，有3位，则mask=0000..0000111=(1<<3)-1
    int findComplementForPositive(int a) {
        int n = 0;
        int b=a;
        while (b) {
            ++n;
            b >>= 1;
        }

        unsigned int a2 = ~a;
        int mask = (1<<n)-1;
        a2 &= mask;

        return a2;
    }
    int findComplement(int num) {
        if (num==0) return 1;
        if (num<0) return ~num;
        return findComplementForPositive(num);
    }
};
