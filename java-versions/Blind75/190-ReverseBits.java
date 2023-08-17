/*
Reverse bits of a given 32 bits unsigned integer.

Note:
Note that in some languages, such as Java, there is no unsigned integer type. In this case, both input and output will be given as a signed integer type. They should not affect your implementation, as the integer's internal binary representation is the same, whether it is signed or unsigned.
In Java, the compiler represents the signed integers using 2's complement notation. Therefore, in Example 2 above, the input represents the signed integer -3 and the output represents the signed integer -1073741825.
 
Example 1:
Input: n = 00000010100101000001111010011100
Output:    964176192 (00111001011110000010100101000000)
Explanation: The input binary string 00000010100101000001111010011100 represents the unsigned integer 43261596, so return 964176192 which its binary representation is 00111001011110000010100101000000.

Example 2:
Input: n = 11111111111111111111111111111101
Output:   3221225471 (10111111111111111111111111111111)
Explanation: The input binary string 11111111111111111111111111111101 represents the unsigned integer 4294967293, so return 3221225471 which its binary representation is 10111111111111111111111111111111.
 

Constraints:
The input must be a binary string of length 32
 
Follow up: If this function is called many times, how would you optimize it?
*/


public class Solution {
    // you need treat n as an unsigned value
    public int reverseBits(int n) {
        return solution1(n);
    }

    /*
    原数字 1011 ，res = 0

    res 左移一位，res = 0，
    得到 1011 的最低位 1 加过来, res = 1
    1011 右移一位变为 101

    res = 1 左移一位，res = 10，
    得到 101 的最低位 1 加过来, res = 11
    101 右移一位变为 10 

    res = 11 左移一位，res = 110，
    得到 10 的最低位 0 加过来, res = 110
    10 右移一位变为 1 

    res = 110 左移一位，res = 1100，
    得到 1 的最低位 1 加过来, res = 1101
    1 右移一位变为 0, 结束
    */
    private int solution1(int n) {
        int res = 0;

        for (int i = 1; i <= 32; ++i) {
            res = (res << 1) + (n&1);

            n >>= 1;
        }

        return res;
    }
}



