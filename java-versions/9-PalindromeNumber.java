/*
Easy

Given an integer x, return true if x is a palindrome, and false otherwise.

Example 1:
Input: x = 121
Output: true
Explanation: 121 reads as 121 from left to right and from right to left.

Example 2:
Input: x = -121
Output: false
Explanation: From left to right, it reads -121. From right to left, it becomes 121-. Therefore it is not a palindrome.

Example 3:
Input: x = 10
Output: false
Explanation: Reads 01 from right to left. Therefore it is not a palindrome.
 
Constraints:
-231 <= x <= 231 - 1
Follow up: Could you solve it without converting the integer to a string?
*/

class Solution {
    public boolean isPalindrome(int x) {
        return isPalindrome_noStringConversion(x);
    }
    
    public boolean isPalindrome_convertToString(int x) {
        if (x < 0) {
            return false;
        }
        
        if (x < 10) {
            return true;
        }
        
        if (x % 10 == 0) {
            return false;
        }
        
        String s = new Integer(x).toString();
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            ++ left;
            -- right;
        }
        return true;
    }
    
    // 直接翻转数字可能会溢出，所以考虑翻转一半数字
    public boolean isPalindrome_noStringConversion(int x) {
        if (x < 0) {
            return false;
        }
        
        if (x < 10) {
            return true;
        }
        
        if (x % 10 == 0) {
            return false;
        }
        
        int revertedNum = 0;
        while (x > revertedNum) {
            revertedNum = revertedNum * 10 + x % 10;
            x /= 10;
        }
        
        // 考虑奇数个数字和偶数个数字的情况。
        // 如：1221， 12321
        return (x == revertedNum) || (x == revertedNum/10);
    }
}

