/*
Easy

Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
An input string is valid if:

Open brackets must be closed by the same type of brackets.
Open brackets must be closed in the correct order.

Example 1:
Input: s = "()"
Output: true

Example 2:
Input: s = "()[]{}"
Output: true

Example 3:
Input: s = "(]"
Output: false

Constraints:
1 <= s.length <= 104
s consists of parentheses only '()[]{}'.
*/

class Solution {
    private static final Map<Character, Character> rigthToLeftMap = new HashMap<>();
    static {
        rigthToLeftMap.put(')', '(');
        rigthToLeftMap.put(']', '[');
        rigthToLeftMap.put('}', '{');
    }
    public boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (isLeft(c)) {
                stack.push(c);
            } else {
                if (stack.isEmpty()) return false;
                
                if (rigthToLeftMap.get(c) != stack.peek()) return false;
                else stack.pop();
            }
        }
        return stack.isEmpty();
    }
    
    private boolean isLeft(char c) {
        return c == '(' || c == '{' || c == '[';
    }
}

