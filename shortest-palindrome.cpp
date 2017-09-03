/*
Given a string S, you are allowed to convert it to a palindrome by adding characters in front of it. Find and return the shortest palindrome you can find by performing this transformation.

For example:

Given "aacecaaa", return "aaacecaaa".

Given "abcd", return "dcbabcd".

*/

/*
这道题的思路很直观:

如果首字母和尾字母不同，则只能在前面加上尾字母。
比如：abcd => d(abc)d => d(c(ab)c)d => d(c(b(a)b)c)d
所以这是一个递归的过程，而且也符合在前面加字符的要求。

如果首字母和尾字母相同，则有两种情况：
1. 如果中间部分是palindrome，则这个字符串是palindrome，直接返回。
比如：abcba
2. 如果中间部分不是palindrome，则和上面首尾字符不同的情况一样处理。
比如：abca => a(abc)a => a(c(ab)c)a => a(c(b(a)b)c)a

实现细节上：
1. 避免重复传递string参数，所以用引用；
2. 对于首尾相同，且中间是palindrome的情况，不能直接返回s，因为这有可能是中间一部分是palindrome
比如：abad => d(aba)d， aba是palindrome，所以递归时只能返回aba，不能返回abad，不然结果就是d(abad)d了。
*/
class Solution {
public:
    string shortestPalindrome(string s) {
        if (s.size()<=1) return s;
        return shortestPalindrome(s, 0, s.size()-1);
    }

    string shortestPalindrome(string &s, int left, int right) {        
        if (s[left] == s[right] && isPalindrome(s,left+1, right-1)) {
            return s.substr(left, right-left+1);  // 注意这里不能直接返回s，因为s是原始字符串
        }

        return s[right] + shortestPalindrome(s, left, right-1) + s[right];
    }

    bool isPalindrome(string &s, int left, int right) {
        while(left<=right) {
            if (s[left] != s[right]) return false;
            ++left;
            --right;
        }
        return true;
    }
};



