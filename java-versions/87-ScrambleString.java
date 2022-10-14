/*
Hard
We can scramble a string s to get a string t using the following algorithm:

If the length of the string is 1, stop.
If the length of the string is > 1, do the following:
Split the string into two non-empty substrings at a random index, i.e., if the string is s, divide it to x and y where s = x + y.
Randomly decide to swap the two substrings or to keep them in the same order. i.e., after this step, s may become s = x + y or s = y + x.
Apply step 1 recursively on each of the two substrings x and y.
Given two strings s1 and s2 of the same length, return true if s2 is a scrambled string of s1, otherwise, return false.

Example 1:
Input: s1 = "great", s2 = "rgeat"
Output: true
Explanation: One possible scenario applied on s1 is:
"great" --> "gr/eat" // divide at random index.
"gr/eat" --> "gr/eat" // random decision is not to swap the two substrings and keep them in order.
"gr/eat" --> "g/r / e/at" // apply the same algorithm recursively on both substrings. divide at random index each of them.
"g/r / e/at" --> "r/g / e/at" // random decision was to swap the first substring and to keep the second substring in the same order.
"r/g / e/at" --> "r/g / e/ a/t" // again apply the algorithm recursively, divide "at" to "a/t".
"r/g / e/ a/t" --> "r/g / e/ a/t" // random decision is to keep both substrings in the same order.
The algorithm stops now, and the result string is "rgeat" which is s2.
As one possible scenario led s1 to be scrambled to s2, we return true.

Example 2:
Input: s1 = "abcde", s2 = "caebd"
Output: false

Example 3:
Input: s1 = "a", s2 = "a"
Output: true

Constraints:
s1.length == s2.length
1 <= s1.length <= 30
s1 and s2 consist of lowercase English letters.
*/

/*
check whether the resulted substrings of s1 equal or "swap-equal" of the corresponding substrings of s2.
For example, given "great" and "rgate", the splits are
("great") vs. ("rgate")
("g", "reat") vs. ("r", "gate") or ("e", "rgat")
("gr", "eat") vs. ("rg", "ate") or ("te", "rga")
("gre", "at") vs. ("rga", "te") or ("ate", "rg")
("grea", "t") vs. ("rgat", "e") or ("gate", "r")
时间复杂度分析：
In the worst situation, for any recursion f(n), we will check all the combinations twice, 
i.e., f(n) = 2[f(1) + f(n-1)] +2[f(2) + f(n-2)] … + 2[f(n/2) + f(n/2+1)], thus, f(n+1)=3*f(n), we have f(n)=3^n.
比如n=4：
子串长度：
1 3
2 2
3 1
n = 5
子串长度：
1 4
2 3
3 2
4 1
f(n)   = 2[f(1) + f(2) + f(3) + ... + f(n-2) + f(n-1)]
f(n+1) = 2[f(1) + f(2) + f(3) + ... + f(n-1) + f(n)]
f(n+1) - f(n) = 2[f(n)]
f(n+1) = 3f(n)
f(n) = 3f(n-1) = 3[3f(n-2)] = 3[3[3f(n-3)]] = 3^3f(n-3) = ... = 3^(n-1)f(1) = 3^(n-1)
*/


class Solution {
    public boolean isScramble(String s1, String s2) {
        HashMap<String, Boolean> memo = new HashMap<>();
        return dp(s1, s2, memo);
    }
    
    private boolean dp(String s1, String s2, HashMap<String, Boolean> memo) {
        if (s1.length() != s2.length()) {
            return false;
        }
        
        if (s1.length() == 1) {
            return s1.equals(s2);
        }
        
        String s = s1 + "_" + s2;
        
        //--------------------------
        // 这一段剪枝可以大大提高速度
        int[] map = new int[26];
        int n = s1.length();
        for (int i = 0; i < n; i++) {
            map[s1.charAt(i) - 'a']++;
            map[s2.charAt(i) - 'a']--;
        }
        for (int i = 0; i < 26; i++) {
            if (map[i] != 0) {
                memo.put(s, false);
                return false;
            }
        }
        //--------------------------
        
        if (memo.containsKey(s)) {
            return memo.get(s);
        }
        
        if (s1.equals(s2)) {
            memo.put(s, true);
            return true;
        }
        
        boolean res = false;
        for (int i = 0; i < s1.length() - 1; ++i) {
            boolean xy = dp(s1.substring(0, i + 1), s2.substring(0, i + 1), memo)
                && dp(s1.substring(i + 1), s2.substring(i + 1), memo);
            
            boolean yx = dp(s1.substring(0, i + 1), s2.substring(s2.length() - i - 1), memo)
                && dp(s1.substring(i + 1), s2.substring(0, s2.length() - i - 1), memo);
            if (xy || yx) {
                res = true;
                break;
            }
        }
        
        memo.put(s, res);
        return res;
    }
}


// class Solution {
//     public boolean isScramble(String s1, String s2) {
//         if (s1.length() != s2.length()) {
//             return false;
//         }
//         TreeMap<int[], Boolean> memo = new TreeMap<>(Arrays::compare);
//         return dp(s1, 0, s1.length(), s2, 0, memo);
//     }
    
//     private boolean dp(String s1, int start1, int end1, String s2, int start2, TreeMap<int[], Boolean> memo) {
//         if (end1 - start1 == 1) {
//             return s1.charAt(start1) == s2.charAt(start2);
//         }
        
//         int[] key = new int[]{start1, end1, start2};
//         if (memo.containsKey(key)) {
//             return memo.get(key);
//         }
        
//         int len = end1 - start1;
//         int end2 = len + start2;
//         boolean res = false;
//         for (int i = 1; i < len; ++i) {
//             boolean xy = dp(s1, start1, start1 + i, s2, start2, memo)
//                 && dp(s1, start1 + i, end1, s2, start2 + i, memo);
            
//             boolean yx = dp(s1, start1, start1 + i, s2, end2 - i, memo)
//                 && dp(s1, start1 + i, end1, s2, start2, memo);
//             if (xy || yx) {
//                 res = true;
//                 break;
//             }
//         }
        
//         memo.put(key, res);
//         return res;
//     }
// }


