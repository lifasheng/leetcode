/*
Medium

Implement pow(x, n), which calculates x raised to the power n (i.e., xn).

Example 1:
Input: x = 2.00000, n = 10
Output: 1024.00000

Example 2:
Input: x = 2.10000, n = 3
Output: 9.26100

Example 3:
Input: x = 2.00000, n = -2
Output: 0.25000
Explanation: 2-2 = 1/22 = 1/4 = 0.25
 
Constraints:
-100.0 < x < 100.0
-231 <= n <= 231-1
n is an integer.
-104 <= xn <= 104
*/

class Solution {
    public double myPow(double x, int n) {
        return myPow_recursive(x, n);
    }
    
    // 这里用long主要是防止n = Integer.MIN_VALUE， -n == n，这样会死循环
    // O(logN)
    private double myPow_recursive(double x, long n) {
        if (n == 0) return 1;
        
        if (n < 0) return 1.0 / myPow_recursive(x, -n);
        
        if (n % 2 == 1) {
            return x * myPow_recursive(x, n - 1);
        }
        
        double r = myPow_recursive(x, n / 2);
        return r * r;
    }
}

