/*
Medium
A message containing letters from A-Z can be encoded into numbers using the following mapping:

'A' -> "1"
'B' -> "2"
...
'Z' -> "26"
To decode an encoded message, all the digits must be grouped then mapped back into letters using the reverse of the mapping above (there may be multiple ways). For example, "11106" can be mapped into:
"AAJF" with the grouping (1 1 10 6)
"KJF" with the grouping (11 10 6)
Note that the grouping (1 11 06) is invalid because "06" cannot be mapped into 'F' since "6" is different from "06".
Given a string s containing only digits, return the number of ways to decode it.
The test cases are generated so that the answer fits in a 32-bit integer.

Example 1:
Input: s = "12"
Output: 2
Explanation: "12" could be decoded as "AB" (1 2) or "L" (12).

Example 2:
Input: s = "226"
Output: 3
Explanation: "226" could be decoded as "BZ" (2 26), "VF" (22 6), or "BBF" (2 2 6).

Example 3:
Input: s = "06"
Output: 0
Explanation: "06" cannot be mapped to "F" because of the leading zero ("6" is different from "06").

Constraints:
1 <= s.length <= 100
s contains only digits and may contain leading zero(s).
*/

class Solution {
    
    public int numDecodings(String s) {
        return numDecodings_dp(s);
    }
    
    // 定义：dp[i] 表示 s[i..N] 的解码方式数量
    // dp[i] = dp[i + 1] + dp[i + 2]
    public int numDecodings_dp(String s) {
        Map<String, Character> encodingMap = new HashMap<>();
        for (int i = 1; i <= 26; ++i) {
            encodingMap.put(String.valueOf(i), (char)('A' + i - 1));
        }
        
        int n = s.length();
        int[] dp = new int[n + 1];
        dp[n] = 1;
        for (int i = n - 1; i >= 0; --i) {
            if (s.charAt(i) == '0') {
                dp[i] = 0;
            } else {
                dp[i] = dp[i + 1];
                if (i + 2 <= n && encodingMap.containsKey(s.substring(i, i + 2))) {
                   dp[i] += dp[i + 2];
                }
            }
        }
        return dp[0];
    }
    
    // 定义：dp[i] 表示 s[0..i-1] 的解码方式数量
    // dp[i] = dp[i - 1] + dp[i-2]
    // 注意这里dp[0] 相当于空字符串,dp[1] 对应第一个字符
    public int numDecodings_dp2(String s) {
        Map<String, Character> encodingMap = new HashMap<>();
        for (int i = 1; i <= 26; ++i) {
            encodingMap.put(String.valueOf(i), (char)('A' + i - 1));
        }
        
        int n = s.length();
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = (s.charAt(0) == '0') ? 0 : 1;
        for (int i = 2; i <= n; ++i) {
            if (s.charAt(i - 1) == '0') {
                if (encodingMap.containsKey(s.substring(i-2, i))) {
                    dp[i] = dp[i - 2];
                }
            } else {
                dp[i] = dp[i - 1];
                if (encodingMap.containsKey(s.substring(i-2, i))) {
                    dp[i] += dp[i - 2];
                }
            }
        }
        
        return dp[n];
    }
    
    
    // O(N), 每个index只处理一遍
    public int numDecodings_memo(String s) {
        Map<String, Character> encodingMap = new HashMap<>();
        for (int i = 1; i <= 26; ++i) {
            encodingMap.put(String.valueOf(i), (char)('A' + i - 1));
        }
        int[] memo = new int[s.length()];
        Arrays.fill(memo, -1);
        return dfs(s, 0, memo, encodingMap);
    }
    
    private int dfs(String s, int i, int[] memo, Map<String, Character> encodingMap) {
        if (i == s.length()) {
            return 1;
        }
        
        if (s.charAt(i) == '0') {
            return 0;
        }
        
        if (memo[i] == -1) {
            memo[i] = dfs(s, i + 1, memo, encodingMap);
            if (i + 2 <= s.length()) {
                String substr = s.substring(i, i + 2);
                if (encodingMap.containsKey(substr)) {
                    memo[i] += dfs(s, i + 2, memo, encodingMap);
                }
            }
        }
        
        return memo[i];
    }
}

