/*
Write a function that takes the binary representation of an unsigned integer and returns the number of '1' bits it has (also known as the Hamming weight).

Note:

Note that in some languages, such as Java, there is no unsigned integer type. In this case, the input will be given as a signed integer type. It should not affect your implementation, as the integer's internal binary representation is the same, whether it is signed or unsigned.
In Java, the compiler represents the signed integers using 2's complement notation. Therefore, in Example 3, the input represents the signed integer. -3.

Example 1:
Input: n = 00000000000000000000000000001011
Output: 3

Explanation: The input binary string 00000000000000000000000000001011 has a total of three '1' bits.
Example 2:
Input: n = 00000000000000000000000010000000
Output: 1
Explanation: The input binary string 00000000000000000000000010000000 has a total of one '1' bit.

Example 3:
Input: n = 11111111111111111111111111111101
Output: 31
Explanation: The input binary string 11111111111111111111111111111101 has a total of thirty one '1' bits.

Constraints:
The input must be a binary string of length 32.
 
Follow up: If this function is called many times, how would you optimize it?
*/

public class Solution {
    // you need to treat n as an unsigned value
    public int hammingWeight(int n) {
        return solution2(n);
    }

    private int solution1(int n) {
        int count = 0;
        while (n != 0) {
            if ((n & 0x1) == 1) {
                ++ count;
            }
            n = n >>> 1; // 注意这里需要用到java的无符号右移
        }
        return count;
    }

    // 核心逻辑就是，n - 1 一定可以消除最后一个 1，同时把其后的 0 都变成 1，这样再和 n 做一次 & 运算，就可以仅仅把最后一个 1 变成 0 了。
    private int solution2(int n) {
        int count = 0;
        while (n != 0) {
            ++ count;
            n = n&(n-1);
        }
        return count;
    }
}


