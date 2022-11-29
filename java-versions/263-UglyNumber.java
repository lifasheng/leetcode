/*
Easy
An ugly number is a positive integer whose prime factors are limited to 2, 3, and 5.
Given an integer n, return true if n is an ugly number.

Example 1:
Input: n = 6
Output: true
Explanation: 6 = 2 × 3

Example 2:
Input: n = 1
Output: true
Explanation: 1 has no prime factors, therefore all of its prime factors are limited to 2, 3, and 5.

Example 3:
Input: n = 14
Output: false
Explanation: 14 is not ugly since it includes the prime factor 7.
 
Constraints:
-231 <= n <= 231 - 1
*/

class Solution {
    /*
    这道题主要考察算术基本定理（正整数唯一分解定理），即：任意一个大于 1 的自然数，要么它本身就是质数，要么它可以分解为若干质数的乘积。
    那么题目所说的丑数当然也可以被分解成若干质数的乘积，且这些质数只包括 2, 3, 5。那么解题思路就很显然了，只要判断 n 是不是只有这三种质因子即可。
    */
    public boolean isUgly(int n) {
        if (n <= 0) {
            return false;
        }
        
        while (n % 3 == 0) {
            n /= 3;
        }
        
        while (n % 5 == 0) {
            n /= 5;
        }
        
        // while (n % 2 == 0) {
        //     n /= 2;
        // }
        
        //return n == 1;
        
        // 判断是否为2的x次方
        return (n & (n - 1)) == 0;
    }
}
