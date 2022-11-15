/*
Medium
Given a string s and a dictionary of strings wordDict, return true if s can be segmented into a space-separated sequence of one or more dictionary words.
Note that the same word in the dictionary may be reused multiple times in the segmentation.

Example 1:
Input: s = "leetcode", wordDict = ["leet","code"]
Output: true
Explanation: Return true because "leetcode" can be segmented as "leet code".

Example 2:
Input: s = "applepenapple", wordDict = ["apple","pen"]
Output: true
Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
Note that you are allowed to reuse a dictionary word.

Example 3:
Input: s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]
Output: false

Constraints:

1 <= s.length <= 300
1 <= wordDict.length <= 1000
1 <= wordDict[i].length <= 20
s and wordDict[i] consist of only lowercase English letters.
All the strings of wordDict are unique.
*/

class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        return wordBreak_memo(s, wordDict);
    }
    
    // f(i) 表示[0..i]是否可以切分
    // f(i) = f(j) && wordDict.contains(s.substring(j+1, i)), j = 0 .. i - 1
    // Time complexity : O(n^3) There are two nested loops, and substring computation at each iteration. Overall that results in O(n^3).
    public boolean wordBreak_dp(String s, List<String> wordDict) {
        Set<String> wordSet = new HashSet<>(wordDict);
        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;
        
        for (int i = 1; i <= n; ++i) {            
            for (int j = 0; j < i; ++j) {
                if (dp[j] && wordSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        
        return dp[n];
    }
    
    // 这个time complexity是 O(N^3)? // TODO
    public boolean wordBreak_memo(String s, List<String> wordDict) {
        Set<String> wordSet = new HashSet<>(wordDict);
        
        int[] memo = new int[s.length()];
        Arrays.fill(memo, - 1);
        return dfs(s, 0, memo, wordSet);
    }
    
    private boolean dfs(String s, int i, int[] memo, Set<String> wordSet) {
        if (i == s.length()) {
            return true;
        }
        
        if (memo[i] != -1) {
            return memo[i] == 1;
        }
        
        boolean res = false;
        for (int j = i + 1; j <= s.length(); ++j) {
            String substr = s.substring(i, j);
            if (wordSet.contains(substr)) {
                res = dfs(s, j, memo, wordSet);
                if (res) {
                    break;
                }
            }
        }
        
        memo[i] = res ? 1 : 0;
        return res;
    }
}

