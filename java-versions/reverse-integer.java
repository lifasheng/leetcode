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
    public int reverse(int x) {
        if (x == 0 || x == Integer.MIN_VALUE || x == Integer.MAX_VALUE) {
            return 0;
        }
        if (x<0) {
            return -1 * reverse(-x);
        }
        final String maxIntegerStr = new Integer(Integer.MAX_VALUE).toString();

        final String sx = new Integer(x).toString();
        final String rsx = new StringBuilder(sx).reverse().toString();
        //System.out.println("rsx:" + rsx);
        if (rsx.length() >= maxIntegerStr.length() && rsx.compareTo(maxIntegerStr) > 0) {
            return 0;
        }
        return new Integer(rsx);
    }


    public int reverse(int x) {
        int maxDiv10 = Integer.MAX_VALUE/10;
        int minDiv10 = Integer.MIN_VALUE/10;
        int result = 0;
        while (x != 0) {
            int y = x%10;
            x /= 10;
            result = result * 10 + y;
            if ((result > maxDiv10  || result < minDiv10) && x != 0) {
                return 0;
            }
            
        }
        return result;
    }

}


