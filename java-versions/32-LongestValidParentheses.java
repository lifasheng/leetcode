/*
Hard
Given a string containing just the characters '(' and ')', find the length of the longest valid (well-formed) parentheses substring.

Example 1:
Input: s = "(()"
Output: 2
Explanation: The longest valid parentheses substring is "()".

Example 2:
Input: s = ")()())"
Output: 4
Explanation: The longest valid parentheses substring is "()()".

Example 3:
Input: s = ""
Output: 0

Constraints:
0 <= s.length <= 3 * 104
s[i] is '(', or ')'.
*/

class Solution {
    // test case: "()(()"    ")()(())())"  "()(())"   "())()()(()" 这个case最复杂，最有代表性
    // very very good!!!
    public int longestValidParentheses(String s) {
        return longestValidParentheses_dp(s);
    }
        
    // O(N), 思路很巧妙，左右扫描两遍，只有left == right时才更新
    public int longestValidParentheses_twoscan(String s) {
        int maxLen = 0;
        int left = 0, right = 0;
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (c == '(') {
                ++ left;
            } else {
                ++ right;
            }
            
            if (left == right) {
                maxLen = Math.max(maxLen, 2 * right);
            } else if (right > left) {
                left = 0;
                right = 0;
            }
        }
        
        left = 0;
        right = 0;
        for (int i = s.length() - 1; i >= 0; --i) {
            char c = s.charAt(i);
            if (c == ')') {
                ++ right;
            } else {
                ++ left;
            }
            
            if (left == right) {
                maxLen = Math.max(maxLen, 2 * right);
            } else if (left > right) {
                left = 0;
                right = 0;
            }
        }
        return maxLen;
    }
    
    // O(N), 很巧妙，stack保存下标，一开始压入-1做锚
    private int longestValidParentheses_useStack(String s) {
        int maxLen = 0;
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(-1);
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (c == '(') {
                stack.push(i);
            } else {
                stack.pop(); // 弹出配对的左括号
                if (stack.isEmpty()) { // 之前stack里面是右括号下标, 更新为当前右括号的下标
                    stack.push(i);
                } else {
                    maxLen = Math.max(maxLen, i - stack.peek());
                }
            }
        }
        return maxLen;
    }
    
    // O(N) dp + stack
    // dp[i] 表示以s[i]结尾的字符串的最长合法括号子串长度， stack同样是存下标
    private int longestValidParentheses_dp(String s) {
        int[] dp = new int[s.length()];
        int maxLen = 0;
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (c == '(') {
                stack.push(i);
                dp[i] = 0;  // 左括号不可能是合法括号子串的结尾
            } else {
                if (stack.isEmpty()) { // 没有配对的左括号
                    dp[i] = 0;
                } else {
                    int leftIndex = stack.pop(); // 配对的左括号对应索引
                    
                    // 以这个右括号结尾的最长子串长度
                    dp[i] = i + 1 - leftIndex + ((leftIndex - 1 >= 0) ? dp[leftIndex - 1] : 0);
                    maxLen = Math.max(maxLen, dp[i]);
                }
            }
        }
        return maxLen;
    }
}

