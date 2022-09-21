/*
Medium

A parentheses string is a non-empty string consisting only of '(' and ')'. It is valid if any of the following conditions is true:

It is ().
It can be written as AB (A concatenated with B), where A and B are valid parentheses strings.
It can be written as (A), where A is a valid parentheses string.
You are given a parentheses string s and a string locked, both of length n. locked is a binary string consisting only of '0's and '1's. For each index i of locked,

If locked[i] is '1', you cannot change s[i].
But if locked[i] is '0', you can change s[i] to either '(' or ')'.
Return true if you can make s a valid parentheses string. Otherwise, return false.

Example 1:
Input: s = "))()))", locked = "010100"
Output: true
Explanation: locked[1] == '1' and locked[3] == '1', so we cannot change s[1] or s[3].
We change s[0] and s[4] to '(' while leaving s[2] and s[5] unchanged to make s valid.

Example 2:
Input: s = "()()", locked = "0000"
Output: true
Explanation: We do not need to make any changes because s is already valid.

Example 3:
Input: s = ")", locked = "0"
Output: false
Explanation: locked permits us to change s[0]. 
Changing s[0] to either '(' or ')' will not make s valid.

Constraints:
n == s.length == locked.length
1 <= n <= 105
s[i] is either '(' or ')'.
locked[i] is either '0' or '1'.
*/


class Solution {
    public boolean canBeValid(String s, String locked) {
        return canBeValid_iterative(s, locked);
    }
    
    /*
    这里有两个hint：
    hint 1: From left to right, if a locked ')' is encountered, it must be balanced with either a locked '(' 
    or an unlocked index on its left. If neither exist, what conclusion can be drawn? 
    If both exist, which one is more preferable to use?
    
    hint 2: After the above, we may have locked indices of '(' and additional unlocked indices. 
    How can you balance out the locked '(' now? What if you cannot balance any locked '('?
    
    hint 1 告诉我们，从左到右扫描一遍，遇到固定的右括号，则其前面必须有固定的左括号或者可以flip的位置
    hint 2 告诉我们同理，如果固定的左括号多怎么办，那就从右往左扫描一遍。
    */
    public boolean canBeValid_iterative(String s, String locked) {
        if(s.length()%2!=0)
            return false;
        
        int open = 0, close = 0, flip_allowed = 0;
        for(int i = 0;i < s.length(); ++i){
            if(locked.charAt(i) == '0')
                ++ flip_allowed;
            else{
                if(s.charAt(i) == '(')
                    ++ open;
                else
                    ++ close;
            }
            // close - open表示我们先拿固定的左右括号进行匹配，剩下的如果固定的右括号多，则和可以flip的括号进行匹配。
            if(flip_allowed < close - open)
                return false;
        }
        open = 0;
        close = 0;
        flip_allowed=0;
        for(int i = s.length() - 1; i >= 0; --i){
            if(locked.charAt(i) == '0')
                ++ flip_allowed;
            else{
                if(s.charAt(i)=='(')
                    ++ open;
                else
                    ++ close;
            }
            if(flip_allowed < open - close)
                return false;
        }
        return true;
    }
    
    // TLE
    private boolean canBeValid_bruteForce(String s, String locked) {
        if (s.length() % 2 == 1) return false;
        
        char[] arr = new char[s.length()];
        for (int i = 0; i < locked.length(); ++i) {
            arr[i] = (locked.charAt(i)  == '0') ? ' ' : s.charAt(i);
        }
        
        return backtrack(arr, 0, 0, 0);
    }
    
    private boolean backtrack(char[] arr, int start, int left, int right) {
        if (left < right) return false;
        if (left > arr.length / 2) return false;
        if (start == arr.length) {
            return left == right ;
        }
        
        if (arr[start] != ' ') {
            if (arr[start] == '(') ++ left;
            else ++ right;
            return backtrack(arr, start + 1, left, right);
        }
            
        arr[start] = '(';
        boolean found = backtrack(arr, start + 1, left + 1, right);
        arr[start] = ' ';
        
        if (found) return found;
        
        arr[start] = ')';
        found = backtrack(arr, start + 1, left, right + 1);
        arr[start] = ' ';
        return found;
    }
    
    private boolean isValid(char[] arr) {
        int left = 0, right = 0;
        for (int i = 0; i < arr.length; ++i) {
            if (arr[i] == '(') {
                ++ left;
            } else {
                ++ right;
            }
            
            if (left < right) return false;
        }
        return left == right;
    }
}

