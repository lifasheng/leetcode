/*
Medium
Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent. Return the answer in any order.
A mapping of digits to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.

Example 1:
Input: digits = "23"
Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]

Example 2:
Input: digits = ""
Output: []

Example 3:
Input: digits = "2"
Output: ["a","b","c"]
 
Constraints:
0 <= digits.length <= 4
digits[i] is a digit in the range ['2', '9'].
*/

class Solution {
    private static final String[] numToChars = new String[] {
        " ", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"
    };
    
    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if (digits.isEmpty()) return result;
        
        StringBuilder sb = new StringBuilder();
        
        backtrack(digits, result, sb, 0);
        return result;
    }
    
    private void backtrack(String digits, List<String> result, StringBuilder sb, int start) {
        if (start == digits.length()) {
            result.add(sb.toString());
            return;
        }
        
        String chars = numToChars[digits.charAt(start) - '0'];
        for (int i = 0; i < chars.length(); ++i) {
            sb.append(chars.charAt(i));
            backtrack(digits, result, sb, start + 1);
            sb.setLength(sb.length() - 1);
        }
    }
}

