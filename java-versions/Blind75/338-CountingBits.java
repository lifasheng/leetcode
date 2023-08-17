/*
very very good!

Given an integer n, return an array ans of length n + 1 such that for each i (0 <= i <= n), ans[i] is the number of 1's in the binary representation of i.

Example 1:
Input: n = 2
Output: [0,1,1]
Explanation:
0 --> 0
1 --> 1
2 --> 10

Example 2:
Input: n = 5
Output: [0,1,1,2,1,2]
Explanation:
0 --> 0
1 --> 1
2 --> 10
3 --> 11
4 --> 100
5 --> 101

Constraints:
0 <= n <= 105
 
Follow up:
It is very easy to come up with a solution with a runtime of O(n log n). Can you do it in linear time O(n) and possibly in a single pass?
Can you do it without using any built-in function (i.e., like __builtin_popcount in C++)?
*/

class Solution {
    public int[] countBits(int n) {
        return solution2(n);
    }

    private int[] solution1(int n) {
        int[] res = new int[n+1];

        for (int i = 0; i <= n; ++i) {
            res[i] = count1Bits(i);
        }
        return res;
    }

    private int count1Bits(int n) {
        int count = 0;
        while (n != 0) {
            ++ count;
            n = n & (n-1);
        }
        return count;
    }


    /*
    如果一个数i是偶数，则它的bits中1的个数和i/2的bits中1的个数是一样的，而i/2已经计算过了
    比如i=4 (100) 和 i=2(10)中1的个数是一样的, i=6(110)和i=3(11)中1的个数是一样的。
    如果i是奇数，则i-1为偶数， i-1/2中1的个数也已经计算过了，而对奇数来说 i/2 == （i-1)/2。
    所以这是一个dp问题：
    res[num] = num%2 + res[num/2] ; // 最低位是否为1 加上 自身一半的数字中1的个数
    */

    private int[] solution2(int n) {
        int[] res = new int[n+1];

        for (int i = 0; i <= n; ++i) {
            res[i] = res[i/2] + i % 2;
        }
        return res;
    }
}

