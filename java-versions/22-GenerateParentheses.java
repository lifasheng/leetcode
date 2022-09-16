/*
Medium

Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.

Example 1:
Input: n = 3
Output: ["((()))","(()())","(())()","()(())","()()()"]

Example 2:
Input: n = 1
Output: ["()"]
 
Constraints:
1 <= n <= 8
*/

class Solution {
     // very very good!
    public List<String> generateParenthesis(int n) {
        return generateParenthesis_backtrack(n);
    }
    /*
    https://www.cnblogs.com/grandyang/p/4444160.html
    这种方法是 CareerCup 书上给的方法，感觉也是满巧妙的一种方法，这种方法的思想是找左括号，每找到一个左括号，就在其后面加一个完整的括号，
    最后再在开头加一个 ()，就形成了所有的情况，需要注意的是，有时候会出现重复的情况，所以用set数据结构去重，最后我们再把set转为List即可.
    */
    public List<String> generateParenthesis_recursive(int n) {
        if (n == 1) return Arrays.asList("()");
        Set<String> set = new HashSet<>();
        List<String> subResult = generateParenthesis_recursive(n - 1);
        for (String str : subResult) {
            for (int i = 0; i < str.length(); ++i) {
                if (str.charAt(i) == '(') {
                    set.add(str.substring(0, i + 1) + "()" + str.substring(i + 1, str.length()));
                }
            }
            set.add("()" + str);
        }
        return new ArrayList<>(set);
    }
    
    // https://houbb.github.io/2020/06/08/algorithm-07-generate-parentheses
    /*
    现在有 2n 个位置，每个位置可以放置字符 ( 或者 )，组成的所有括号组合中，有多少个是合法的？

    这就是典型的回溯算法提醒，暴力穷举就行了。

    不过为了减少不必要的穷举，我们要知道合法括号串有以下性质：

    1、一个「合法」括号组合的左括号数量一定等于右括号数量，这个很好理解。

    2、对于一个「合法」的括号字符串组合 p，必然对于任何 0 <= i < len(p) 都有：子串 p[0..i] 中左括号的数量都大于或等于右括号的数量。

    因为从左往右算的话，肯定是左括号多嘛，到最后左右括号数量相等，说明这个括号组合是合法的。

    用 left 记录还可以使用多少个左括号，用 right 记录还可以使用多少个右括号，就可以直接套用 回溯算法套路框架 了。
    */
    public List<String> generateParenthesis_backtrack(int n) {
        List<String> result = new ArrayList<>();
        StringBuilder path = new StringBuilder();
        // 可用的左括号和右括号数量初始化为 n
        backtrack(n, n, result, path);
        return result;
    }
    
    // left 和right分别表示剩余的左右括号的数量
    private void backtrack(int left, int right, List<String> result, StringBuilder path) {
        if (left == 0 && right == 0) {
            result.add(path.toString());
            return;
        }

        // 剪枝
        // 数量小于 0 肯定是不合法的
        if (left < 0 || right < 0) return;
        // 若左括号剩下的多，说明不合法
        if (left > right) return;
        
        // 尝试放一个左括号
        path.append('('); // 选择
        backtrack(left - 1, right, result, path);
        path.setLength(path.length() - 1); // 撤消选择
        
        // 尝试放一个右括号
        path.append(')'); // 选择
        backtrack(left, right - 1, result, path);
        path.setLength(path.length() - 1); // 撤消选择
    }
}

