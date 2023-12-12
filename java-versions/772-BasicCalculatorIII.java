/*
very very good!

Implement a basic calculator to evaluate a simple expression string.
The expression string contains only non-negative integers, '+', '-', '*', '/' operators, and open '(' and closing parentheses ')'. The integer division should truncate toward zero.

You may assume that the given expression is always valid. All intermediate results will be in the range of [-231, 231 - 1].
Note: You are not allowed to use any built-in function which evaluates strings as mathematical expressions, such as eval().

Example 1:
Input: s = "1+1"
Output: 2

Example 2:
Input: s = "6-4/2"
Output: 4

Example 3:
Input: s = "2*(5+5*2)/3+(6/2+8)"
Output: 21
 
Constraints:
1 <= s <= 104
s consists of digits, '+', '-', '*', '/', '(', and ')'.
s is a valid expression.
*/

// https://labuladong.github.io/algo/di-san-zha-24031/jing-dian--a94a0/ru-he-shi--24fe4/
class Solution {
    public int calculate(String s) {
        char[] arr = s.replaceAll(" ", "").toCharArray();
        Queue<Character> queue = new LinkedList<>();
        for (char c : arr) {
            queue.offer(c);
        }
        return calculate(queue);
    }

    private int calculate(Queue<Character> queue) {
        char sign = '+';
        int num = 0;

        Deque<Integer> stack = new ArrayDeque<>();
        while (!queue.isEmpty()) {
            char c = queue.poll();

            if (Character.isDigit(c)) {
                num = num * 10 + (c - '0');
            }
            
            if (c == '(') {
                num = calculate(queue);
                // 这里是和labuladong的解法不同之处，我觉得这样才能让sign始终是valid，
                // 比如对与表达式 2*(5+5*2)/3+(6/2+8)，第一个(处理完了，num应该是15，sign应该是*，这样才能统一。
                // 如果没有这个语句，则会出现sign='(' 这种情况，感觉很奇怪，虽然它结果也是对的。
                if (!queue.isEmpty()) {
                    continue;
                }
            }

            // 注意queue.isEmpty()很重要，考虑以下情况：
            // s = "12";  此时读取完数字queue为空，需要将其入栈
            // s = "(1+2)" 此时处理完（）之后，queue为空，需要将（）内的结果入栈
            if (!Character.isDigit(c) || queue.isEmpty()) {
                switch (sign) {
                    case '+':
                        stack.push(num);
                        break;
                    case '-':
                        stack.push(-num);
                        break;
                    case '*':
                        stack.push(stack.pop() * num);
                        break;
                    case '/':
                        stack.push(stack.pop() / num);
                }

	        sign = c;
	        num = 0;
            }

            if (c == ')') {
                break;
            }
            
        }

        int res = 0;
        while (!stack.isEmpty()) {
            res += stack.pop();
        }
        return res;
    }
}


