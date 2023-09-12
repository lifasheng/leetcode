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
        Map<String, List<String>> encodedStrToStrings = new HashMap<>();
        for (String s : strs) {
            String encodedStr = encodingString(s);
            encodedStrToStrings.putIfAbsent(encodedStr, new ArrayList<>());
            encodedStrToStrings.get(encodedStr).add(s);
        }

        List<List<String>> res = new ArrayList<>();
        for (List<String> list : encodedStrToStrings.values()) {
            res.add(list);
        }
        return res;
    }

    private String encodingString(String s) {
        char[] arr = s.toCharArray();
        Arrays.sort(arr);
        return new String(arr);
    }

}

