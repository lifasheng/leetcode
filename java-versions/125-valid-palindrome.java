/*
125. Valid Palindrome
Given a string, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases.
Note: For the purpose of this problem, we define empty string as valid palindrome.

Example 1:
Input: "A man, a plan, a canal: Panama"
Output: true

Example 2:
Input: "race a car"
Output: false

Constraints:
s consists only of printable ASCII characters.
*/


/*
1. String.length();
2. String.charAt(int);
3. Character.toUpperCase(char);
4. String.toCharArray() => char[]
5. String.toUpperCase() 对整个字符串转换
*/
class Solution {
    private boolean isAlphaNumeric(char c) {
        return ('A' <= c && c <= 'Z')
            || ('0' <= c && c <= '9');
    }
    
    public boolean isPalindrome(String s) {
        char[] array = s.toCharArray(); // 这个会调用native System.arraycopy
        int n = array.length;
        
        int i = 0, j = n-1;
        while (i<j) {
            char ci = Character.toUpperCase(array[i]);
            char cj = Character.toUpperCase(array[j]);
            if (!isAlphaNumeric(ci)) {
                ++i;
            } else if (!isAlphaNumeric(cj)) {
                --j;
            } else {
                if (ci != cj) return false;
                ++i;
                --j;
            }
        }
        return true;
    }
}

class Solution {
    // two pointer:
    // Character.isLetterOrDigit and Character.toLowerCase
    public boolean isPalindrome(String s) {
        int i = 0, j = s.length() - 1;
        while (i < j) {
            if (!Character.isLetterOrDigit(s.charAt(i))) ++ i;
            else if (!Character.isLetterOrDigit(s.charAt(j))) -- j;
            else {
                if (lowerCase(s.charAt(i)) != lowerCase(s.charAt(j))) return false;
                ++ i;
                -- j;
            }
        }
        return true;
    }
    
    private char lowerCase(char c) {
        return Character.toLowerCase(c);
    }
}
