/*
very good!

Given two integers a and b, return the sum of the two integers without using the operators + and -.

Example 1:
Input: a = 1, b = 2
Output: 3

Example 2:
Input: a = 2, b = 3
Output: 5
 
Constraints:
-1000 <= a, b <= 1000
*/

class Solution {
    public int getSum(int a, int b) {
        return solution1(a, b);
    }

    /*
    xor的重要特性是不进位加法
    0+0=0
    0+1=1
    1+0=1
    1+1=0（xor)

    那么我们只有找到进位，加上xor的结果就是最终结果。
    注意到只有两个位都是1的时候才会进位，所以我们可以通过&来找到1的位置。
    同时由于是进位，我们需要将&的结果往左移动一位，再和xor的结果相加。
    这里是一个递归的过程，只有进位的部分还有值，就一直这么递归下去，直到没有进位了，也就是b=0为止。
    终止条件是当进位为0时，直接返回第一步的结果。
    因为进位会一直往左边移动，直到到最左边的位置，此时a&b就会得到0.

    比如5 + 11 = 16

    a= 0101
    b= 1011
    a^b=1110
    a&b=0001
    (a&b)<<1 = 0010

    1110+0010 = 14+2

    a=1110
    b=0010
    a^b=1100
    a&b=0010
    (a&b)<<1=0100

    1100+0100=12+4

    a=1100
    b=0100
    a^b=1000
    a&b=0100
    (a&b)<<1=1000

    1000+1000=8+8

    a=1000
    b=1000
    a^b=0000
    a&b=1000
    (a&b)<<1=10000

    0000 + 10000=0+16

    a=0000
    b=10000
    a^b=10000
    a&b=0
    (a&b)<<1=0


    */
    // 迭代版本
    private int solution1(int a, int b) {
        while (b != 0) {
            int carry = ((a & b) << 0x1);
            a = a ^ b;
            b = carry;
        }
        return a;
    }

    // 递归版本
    private int solution2(int a, int b) {
        if (b == 0) return a;
        return solution2(a^b, (a&b) << 0x1);
    }
}

