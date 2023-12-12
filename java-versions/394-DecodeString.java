/*
Given an encoded string, return its decoded string.

The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is being repeated exactly k times. Note that k is guaranteed to be a positive integer.

You may assume that the input string is always valid; there are no extra white spaces, square brackets are well-formed, etc. Furthermore, you may assume that the original data does not contain any digits and that digits are only for those repeat numbers, k. For example, there will not be input like 3a or 2[4].

The test cases are generated so that the length of the output will never exceed 105.

Example 1:
Input: s = "3[a]2[bc]"
Output: "aaabcbc"

Example 2:
Input: s = "3[a2[c]]"
Output: "accaccacc"

Example 3:
Input: s = "2[abc]3[cd]ef"
Output: "abcabccdcdcdef"

Constraints:
1 <= s.length <= 30
s consists of lowercase English letters, digits, and square brackets '[]'.
s is guaranteed to be a valid input.
All the integers in s are in the range [1, 300].
*/

class Solution {
    public String decodeString(String s) {
        Deque<String> stack = new ArrayDeque<>();
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (c != ']') {
                stack.push("" + c);
            } else {
                String temp = "";
                while (!stack.peek().equals("[")) {
                    temp = stack.pop() + temp;
                }
                stack.pop();
                String numStr = "";
                while (!stack.isEmpty() && Character.isDigit(stack.peek().charAt(0))) {
                    numStr = stack.pop() + numStr;
                }

                int num = Integer.valueOf(numStr);
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < num; ++j) {
                    sb.append(temp);
                }

                stack.push(sb.toString());
            }
        }
        
        String res = "";
        while (!stack.isEmpty()) {
            res = stack.pop() + res;
        }
        return res;
    }
}

/*
// 3[a2[b2[c]]]]

abccbcc abccbcc abccbcc

3
abccbcc
*/
/*
class Solution {
    public String decodeString(String s) {
        // User StringBuilder, be sure not to use string+, the performance is terrible there
        StringBuilder decoded = new StringBuilder();
        int num = 1;
        while(p != s.length()) {
            char ch = s.charAt(p);
            String sub = "";
            switch (ch) {
                case '[':
                    p++;
                    sub = decodeString(s);
                    while (num-- > 0) {
                        decoded.append(sub);
                    }
                    num = 1;
                    break;
                case ']':
                    p++;
                    return decoded.toString();
                default:
                    if(isDigit(ch)) {
                        num = readNum(s);
                    } else {
                        decoded.append(ch);
                        p++;
                    }
                    break;
            }
        }

        return decoded.toString();
    }

    private int readNum(String s) {
        int num = 0;
        while(isDigit(s.charAt(p))) {
            num = num * 10 + (s.charAt(p) - '0');
            p++;
        }
        return num;
    }

    private boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }

    int p = 0;
}
*/


