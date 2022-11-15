/*
Hard

Given a string s and a dictionary of strings wordDict, add spaces in s to construct a sentence where each word is a valid dictionary word. Return all such possible sentences in any order.
Note that the same word in the dictionary may be reused multiple times in the segmentation.

Example 1:
Input: s = "catsanddog", wordDict = ["cat","cats","and","sand","dog"]
Output: ["cats and dog","cat sand dog"]

Example 2:
Input: s = "pineapplepenapple", wordDict = ["apple","pen","applepen","pine","pineapple"]
Output: ["pine apple pen apple","pineapple pen apple","pine applepen apple"]
Explanation: Note that you are allowed to reuse a dictionary word.

Example 3:
Input: s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]
Output: []
 
Constraints:
1 <= s.length <= 20
1 <= wordDict.length <= 1000
1 <= wordDict[i].length <= 10
s and wordDict[i] consist of only lowercase English letters.
All the strings of wordDict are unique.
*/

class Solution {
    public List<String> wordBreak(String s, List<String> wordDict) {
        return wordBreak_memo(s, wordDict);
    }
    
    public List<String> wordBreak_memo(String s, List<String> wordDict) {
        Set<String> wordSet = new HashSet<>(wordDict);
        
        List<String> result = new ArrayList<>();
        List<String> path = new ArrayList<>();
        dfs(s, 0, wordSet, path, result);
        return result;
    }
    
    private void dfs(String s, int i, Set<String> wordSet, List<String> path, List<String> result) {
        if (i == s.length()) {
            result.add(String.join(" ", path));
            return;
        }
        
        for (int j = i + 1; j <= s.length(); ++j) {
            String substr = s.substring(i, j);
            if (wordSet.contains(substr)) {
                path.add(substr);
                dfs(s, j, wordSet, path, result);
                path.remove(path.size() - 1);
            }
        }
    }
}

