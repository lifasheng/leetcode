/*
Medium
Given a string s, partition s such that every substring of the partition is a palindrome. Return all possible palindrome partitioning of s.

A palindrome string is a string that reads the same backward as forward.

Example 1:
Input: s = "aab"
Output: [["a","a","b"],["aa","b"]]

Example 2:
Input: s = "a"
Output: [["a"]]

Constraints:
1 <= s.length <= 16
s contains only lowercase English letters.
*/

// very good!
class Solution {
    public List<List<String>> partition(String s) {
        return partition_dfs(s);
    }
    
    // solution 1: dp
    private List<List<String>> partition_dp(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        
        for (int i = n - 1; i >= 0; --i) {
            for (int j = i; j < n; ++j) {
                dp[i][j] = (s.charAt(i) == s.charAt(j)) && ((j - i < 2) || dp[i+1][j-1]);
            }
        }
        
        List<List<String>> result = new ArrayList<>();
        List<String> path = new ArrayList<>();
        dfs(s, 0, result, path, dp);
        return result;
    }
    
    private void dfs(String s, int start, List<List<String>> result, List<String> path, boolean[][] dp) {
        if (start == s.length()) {
            result.add(new ArrayList<>(path));
            return;
        }
        
        for (int end = start; end < s.length(); ++end) {
            if (dp[start][end]) {
                path.add(s.substring(start, end + 1));
                dfs(s, end + 1, result, path, dp);
                path.remove(path.size() - 1);
            }
        }
    }
    
    // solution 2: 
    private List<List<String>> partition_dfs(String s) {
        List<List<String>> result = new ArrayList<>();
        List<String> path = new ArrayList<>();
        dfs(s, 0, result, path);
        return result;
    }
    
    private void dfs(String s, int start, List<List<String>> result, List<String> path) {
        if (start == s.length()) {
            result.add(new ArrayList<>(path));
            return;
        }
        
        for (int end = start; end < s.length(); ++end) {
            if (isPalindrome(s, start, end)) {
                path.add(s.substring(start, end + 1));
                dfs(s, end + 1, result, path);
                path.remove(path.size() - 1);
            }
        }
    }
    
    private boolean isPalindrome(String s, int start, int end) {
        while (start < end) {
            if (s.charAt(start) != s.charAt(end)) return false;
            ++ start;
            -- end;
        }
        return true;
    }
}

