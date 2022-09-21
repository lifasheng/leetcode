/*
Easy
Given a non-negative integer x, compute and return the square root of x.

Since the return type is an integer, the decimal digits are truncated, and only the integer part of the result is returned.

Note: You are not allowed to use any built-in exponent function or operator, such as pow(x, 0.5) or x ** 0.5.

Example 1:
Input: x = 4
Output: 2

Example 2:
Input: x = 8
Output: 2
Explanation: The square root of 8 is 2.82842..., and since the decimal part is truncated, 2 is returned.
 
Constraints:
0 <= x <= 231 - 1
*/

class Solution {
    public int mySqrt(int x) {
        if (x == 0 || x == 1) return x;
        
        int low = 2, high = x;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            long n = (long)mid * mid;
            if (n == x) return mid;
            else if (n < x) low = mid + 1;
            else high = mid - 1;
        }
        return high;
    }
    
    // TLE
    public int mySqrt_1(int x) {
        if (x == 0 || x == 1) return x;
        
        int i;
        for (i = 2; i <= x / 2; ++i) {
            int a = i * i;
            if (a == x) return i;
            if (a > x) break;
        }
        return i - 1;
    }
}

