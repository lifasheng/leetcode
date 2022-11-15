/*

Given a 32-bit signed integer, reverse digits of an integer.

Note:
Assume we are dealing with an environment that could only store integers within the 32-bit signed integer range: [−231,  231 − 1]. For the purpose of this problem, assume that your function returns 0 when the reversed integer overflows.

 

Example 1:

Input: x = 123
Output: 321
Example 2:

Input: x = -123
Output: -321
Example 3:

Input: x = 120
Output: 21
Example 4:

Input: x = 0
Output: 0
 

Constraints:

-231 <= x <= 231 - 1


*/

/*
Integer.MIN_VALUE: -2147483648
Integer.MAX_VALUE: 2147483647
*/

class Solution {
    // Integer.MAX_VALUE: 2147483647
    // Integer.MIN_VALUE: -2147483648
    public int reverse(int x) {        
        // 我们要单独处理这种情况，因为-1 * Integer.MIN_VALUE == Integer.MIN_VALUE，会死循环。
        if (x == Integer.MIN_VALUE) {
            return 0;
        }
        
        if (x < 0) {
            return -1 * reverse(-x);
        }
        
        int result = 0;
        while (x > 0) {
            int lastDigit = x % 10;
            if ((result > Integer.MAX_VALUE / 10) || (result == Integer.MAX_VALUE / 10 && lastDigit > Integer.MAX_VALUE % 10)) {
                return 0;
            }
            result = result * 10 + lastDigit;
            x /= 10;
        }
        return result;
    }
}

