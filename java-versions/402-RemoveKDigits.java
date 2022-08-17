/*
Medium

Given string num representing a non-negative integer num, and an integer k, return the smallest possible integer after removing k digits from num.

Example 1:
Input: num = "1432219", k = 3
Output: "1219"
Explanation: Remove the three digits 4, 3, and 2 to form the new number 1219 which is the smallest.

Example 2:
Input: num = "10200", k = 1
Output: "200"
Explanation: Remove the leading 1 and the number is 200. Note that the output must not contain leading zeroes.

Example 3:
Input: num = "10", k = 2
Output: "0"
Explanation: Remove all the digits from the number and it is left with nothing which is 0.
 
Constraints:
1 <= k <= num.length <= 105
num consists of only digits.
num does not have any leading zeros except for the zero itself.
*/

class Solution {
    // test case: 1432219, 1023, 110345, 110, 1123, 112345
    
    // MonolithicStack, very good!!
    public String removeKdigits(String num, int k) {
        return removeKdigits_usingMonolithicStack(num, k);
    }
    
    // 思路： 维护一个单调递增stack，可以算出每个数字被remove的顺序，然后去除前k个就行。
    private String removeKdigits_usingMonolithicStack(String num, int k) {
        if (k >= num.length()) return "0";
        
        List<Integer> removeOrder = new ArrayList<>();
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < num.length();) {
            if (stack.isEmpty() || num.charAt(i) >= num.charAt(stack.peek())) { // please note that >=, not >
                stack.push(i);
                ++ i;
            } else {
                removeOrder.add(stack.pop());
            }
        }
        while (!stack.isEmpty()) {
            removeOrder.add(stack.pop());
        }
        
        Set<Integer> toBeRemoved = new HashSet<>();
        for (int i = 0; i < k; ++i) {
            toBeRemoved.add(removeOrder.get(i));
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num.length(); ++i) {
            if (!toBeRemoved.contains(i)) {
                sb.append(num.charAt(i));
            }
        }
        num = sb.toString();
        // 去除开头的0
        for (int i = 0; i < num.length(); ++i) {
            if (num.charAt(i) != '0') {
                return num.substring(i);
            }
        }
        return "0";
    }
    
    // 思路： 从头遍历string，找到第一个递减的位置，删除之前的那个字符，如1023，0是第一个递减的，删除1，会超时
    private String removeKdigits_bruteforce(String num, int k) {
        if (k >= num.length()) return "0";
        
        // 循环k次，每次尝试删除一个字符
        for (int i = 0; i < k; ++i) {
            if (num.length() <= 1) return "0";
            boolean found = false;
            for (int j = 1; j < num.length(); ++j) {
                if (num.charAt(j) < num.charAt(j - 1)) {
                    num = num.substring(0, j - 1) + num.substring(j);
                    found = true;
                    break;
                }
            }
            if (!found) {
                num = num.substring(0, num.length() - 1);
            }
        }
    
        // 去除开头的0
        for (int i = 0; i < num.length(); ++i) {
            if (num.charAt(i) != '0') {
                return num.substring(i);
            }
        }
        return "0";
    }
}

