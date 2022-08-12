/*
Write a function to find the longest common prefix string amongst an array of strings.

If there is no common prefix, return an empty string "".

Example 1:
Input: strs = ["flower","flow","flight"]
Output: "fl"

Example 2:
Input: strs = ["dog","racecar","car"]
Output: ""
Explanation: There is no common prefix among the input strings.

Constraints:
1 <= strs.length <= 200
0 <= strs[i].length <= 200
strs[i] consists of only lowercase English letters.
*/

class Solution {
    public String longestCommonPrefix(String[] strs) {
        return longestCommonPrefix_horizontal(strs);
    }
    
    private String longestCommonPrefix_horizontal(String[] strs) {
        if (strs.length == 0) return "";
        
        String prefix = strs[0];
        for (int i = 1; i < strs.length; ++i) {
            while (strs[i].indexOf(prefix) != 0) { // important != 0
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) return "";
            } 
        }
        return prefix;
    }
    
    private String longestCommonPrefix_vertical(String[] strs) {
        if (strs.length == 0) return "";
        
        int index = strs[0].length();
        for (int i = 1; i < strs.length; ++i) {
            int n = Math.min(strs[0].length(), strs[i].length());
            index = Math.min(index, n);
            for (int j = 0; j < n; ++j) {
                if (strs[i].charAt(j) != strs[0].charAt(j)) {
                    index = Math.min(index, j);
                    break;
                }
            }
        }
        return strs[0].substring(0, index);
    }
}

