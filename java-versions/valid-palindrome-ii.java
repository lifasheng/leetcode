/*
680. Valid Palindrome II
Easy
Given a non-empty string s, you may delete at most one character. Judge whether you can make it a palindrome.

Example 1:
Input: "aba"
Output: True
Example 2:
Input: "abca"
Output: True
Explanation: You could delete the character 'c'.
Note:
The string will only contain lowercase characters a-z. The maximum length of the string is 50000.
*/


/*
思路，如果s[i]==s[j]，则判断s[i+1] ... s[j-1]之间不是palindrome。
否则，就判断s[i+1] ... s[j] 和 s[i] ... s[j-1] 这两者是不是palindrome。

为什么说如果s[i]==s[j]，我们只需要判断s[i+1] ... s[j-1]之间不是palindrome，
而不用判断s[i+1] ... s[j] 和 s[i] ... s[j-1] 这两者是不是palindrome呢？

假设字符串s[i], s[i+1], ..., s[j-1], s[j]，s[i] == s[j]，
如果s[i], s[i+1], ..., s[j-1]是palindrome， 则由于我们比较s[i]和s[j]时用掉了s[i]，
剩下的字符串是s[i+1], ..., s[j-1]，它去掉s[j-1]之后就会是一个palindrome了。

比如cabacc, 本来左边的部分cabac是一个palindrome，由于我们用最右边的c和第一个c比较，占用了第一个c，我们认为直接判断abac就可以了。
因为cabac是palindrome，去掉第一个c之后abac不是palindrome，但是此时去掉最后的c（也就是s[j-1])之后，它又是palindrome，符合题目去掉一个字符即可的要求。
*/
class Solution {
    
    private boolean isValidPalindrome(String s, int i, int j) {
        while(i<j) {
            if (s.charAt(i) != s.charAt(j)) return false;
            ++i;
            --j;
        }
        return true;
    }
    
    public boolean validPalindrome(String s) {
        int i=0, j=s.length()-1;
        while(i<j) {
            if (s.charAt(i) == s.charAt(j)) {
                ++i;
                --j;
            } else {
                // startIndex is includsive, endIndex is exclusive
                return isValidPalindrome(s, i+1, j) 
                    || isValidPalindrome(s, i, j-1);
            }
        }
        return true;
    }
    
}


