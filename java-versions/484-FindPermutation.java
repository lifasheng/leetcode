/*
Mediu

A permutation perm of n integers of all the integers in the range [1, n] can be represented as a string s of length n - 1 where:

s[i] == 'I' if perm[i] < perm[i + 1], and
s[i] == 'D' if perm[i] > perm[i + 1].
Given a string s, reconstruct the lexicographically smallest permutation perm and return it.

Example 1:
Input: s = "I"
Output: [1,2]
Explanation: [1,2] is the only legal permutation that can represented by s, where the number 1 and 2 construct an increasing relationship.

Example 2:
Input: s = "DI"
Output: [2,1,3]
Explanation: Both [2,1,3] and [3,1,2] can be represented as "DI", but since we want to find the smallest lexicographical permutation, you should return [2,1,3]
 
Constraints:
1 <= s.length <= 105
s[i] is either 'I' or 'D'.
*/

class Solution {
    // 想要找到所有permutation再来比较，肯定会超时。
    // very good!!!
    public int[] findPermutation(String s) {
        return findPermutation_useTwoPointers(s);
    }
    
    private int[] findPermutation_useStack(String s) {
        int n = s.length() + 1;
        int[] result = new int[n];
        int j = 0;
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (c == 'D') {
                stack.push(i + 1);
            } else {
                stack.push(i + 1);
                while (!stack.isEmpty()) {
                    result[j++] = stack.pop();
                }
            }
        }
        
        stack.push(n);
        while (!stack.isEmpty()) {
            result[j++] = stack.pop();
        }
        return result;
    }
    
    // very very good! awesome!
    private int[] findPermutation_useTwoPointers(String s) {
        int n = s.length() + 1;
        int[] result = new int[n];
        
        for (int i = 0; i < n; ++i) {
            result[i] = i + 1;
        }
        
        int i = 0;
        while (i < s.length()) {
            int j = i;
            while (i < s.length() && s.charAt(i) == 'D') ++ i;
            
            reverse(result, j, i);
            ++ i;
        }
        return result;
    }
    
    private void reverse(int[] arr, int start, int end) {
        while (start < end) {
            int tmp = arr[start];
            arr[start] = arr[end];
            arr[end] = tmp;
            ++ start;
            -- end;
        }
    }
}

