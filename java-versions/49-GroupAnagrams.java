/*
Given an array of strings strs, group the anagrams together. You can return the answer in any order.

An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.

 

Example 1:

Input: strs = ["eat","tea","tan","ate","nat","bat"]
Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
Example 2:

Input: strs = [""]
Output: [[""]]
Example 3:

Input: strs = ["a"]
Output: [["a"]]
 

Constraints:

1 <= strs.length <= 104
0 <= strs[i].length <= 100
strs[i] consists of lowercase English letters.
*/

class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String s : strs) {
            String key = encodeStringUsingCount(s);
            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(s);
        }
        
        List<List<String>> result = new ArrayList<>();
        for (List<String> values : map.values()) {
            result.add(values);
        }
        return result;
    }
    
    // O(KlogK)
    private String encodeStringUsingSort(String s) {
        char[] arr = s.toCharArray();
        Arrays.sort(arr);
        return new String(arr);
    }
    
    // O(K)
    private String encodeStringUsingCount(String s) {
        int[] count = new int[26];
        Arrays.fill(count, 0);
        for (int i = 0; i < s.length(); ++i) {
            count[s.charAt(i) - 'a'] ++; 
        }
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < 26; ++i) {
            sb.append('#');
            sb.append(count[i]);
        }
        return sb.toString();
    }
}

