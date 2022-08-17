/*
Given a string s and an integer k, reverse the first k characters for every 2k characters counting from the start of the string.

If there are fewer than k characters left, reverse all of them. If there are less than 2k but greater than or equal to k characters, then reverse the first k characters and leave the other as original.

Example 1:
Input: s = "abcdefg", k = 2
Output: "bacdfeg"

Example 2:
Input: s = "abcd", k = 2
Output: "bacd"

Constraints:
1 <= s.length <= 104
s consists of only lowercase English letters.
1 <= k <= 104
*/

class Solution {
    public String reverseStr(String s, int k) {
        int n = s.length();
        int m = n / (2 * k);
        
        char[] arr = s.toCharArray();
        
        for (int i = 0; i < m; ++i) {
            int start = i * 2 * k;
            reverse(arr, start, start + k - 1);
        }
        
        if (n - m * 2 * k > k) {
            int start = m * 2 * k;
            reverse(arr, start, start + k - 1);
        } else {
            reverse(arr, m * 2 * k, n - 1);
        }
        
        return new String(arr);
    }
    
    private void reverse(char[] arr, int i, int j) {
        while (i < j) {
            swap(arr, i, j);
            ++ i;
            -- j;
        }
    }
    
    private void swap(char[] arr, int i, int j) {
        char tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}

