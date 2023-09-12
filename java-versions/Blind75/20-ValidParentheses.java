/*
Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.

An input string is valid if:

Open brackets must be closed by the same type of brackets.
Open brackets must be closed in the correct order.
Every close bracket has a corresponding open bracket of the same type.
 
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
    private static final Map<Character, Character> bracketMap = new HashMap<>() {{
        put(')', '(');
        put('}', '{');
        put(']', '[');
    }};

    public boolean isValid(String s) {
        int n = s.length();
        if (n % 2 != 0) return false;

        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0 ; i < n; ++i) {
            char c = s.charAt(i);
            if (isOpenBracket(c)) {
                stack.push(c);
            } else {
                if (stack.isEmpty() || stack.peek() != bracketMap.get(c)) {
                    return false;
                }

                stack.pop();
            }
        }
        return stack.isEmpty();
    }
    
    private boolean isOpenBracket(char c) {
        return c == '(' || c == '{' || c == '[';
    }
}


