/*
Medium - Hard

Given a string s and an integer k, return the length of the longest substring of s such that the frequency of each character in this substring is greater than or equal to k.

Example 1:
Input: s = "aaabb", k = 3
Output: 3
Explanation: The longest substring is "aaa", as 'a' is repeated 3 times.

Example 2:
Input: s = "ababbc", k = 2
Output: 5
Explanation: The longest substring is "ababb", as 'a' is repeated 2 times and 'b' is repeated 3 times.
 
Constraints:
1 <= s.length <= 104
s consists of only lowercase English letters.
1 <= k <= 105
*/

class Solution {
    // very very good!!!
    public int longestSubstring(String s, int k) {
        return longestSubstring_slidingwindow(s, k);
    }
    
    // 这里的重点是用array 而不是map，用map会超时，用array就不会,估计也快超时了。
    private int longestSubstring_bruteForce(String s, int k) {
        if (s == null || s.isEmpty()) return 0;
        if (k == 1) return s.length();
        
        int maxLen = 0;
        
        for (int i = 0; i < s.length(); ++i) {
            int[] countMap = new int[26];
            for (int j = i; j < s.length(); ++j) {
                ++ countMap[s.charAt(j) - 'a'];
                if (isValid(countMap, k)) {
                    maxLen = Math.max(maxLen, j - i + 1);
                }
            }
        }
        return maxLen;
    }
    
    private boolean isValid(int[] countMap, int k) {
        int charCount = 0;
        int charAtLeastK = 0;
        for (int freq : countMap) {
            if (freq > 0) {
                ++ charCount;
            }
            
            if (freq >= k) {
                ++ charAtLeastK;
            }
        }
        return charCount == charAtLeastK;
    }
    
    
    // solution2: divide and conque， worst case:O(N^2)
    private int longestSubstring_recursive(String s, int k) {
        if (s.length() < k) return 0;
        
        int[] countMap = new int[26];
        for (int i = 0; i < s.length(); ++i) {
            countMap[s.charAt(i) - 'a'] ++;
        }
        
        int mid = 0;
        while (mid < s.length() && countMap[s.charAt(mid) - 'a'] >= k) ++ mid;
        if (mid == s.length()) return s.length();
        
        int left = longestSubstring_recursive(s.substring(0, mid), k);
        int right = longestSubstring_recursive(s.substring(mid + 1), k);
        return Math.max(left, right);
    }
    
    // Solution 3: sliding window: very good!  
    // https://www.cielyang.com/post/leetcode-395-%E8%87%B3%E5%B0%91%E6%9C%89-k-%E4%B8%AA%E9%87%8D%E5%A4%8D%E5%AD%97%E7%AC%A6%E7%9A%84%E6%9C%80%E9%95%BF%E5%AD%90%E4%B8%B2/
    /*
    滑动窗口
    本题不可以直接套用滑动窗口的模板代码，因为使用不定长度的滑动窗口的模板代码的前提是答案与窗口长度有单调关系：随着窗口的右端点值增加，最优解的左端点位置单调不减。单调性保证了我们的算法可以在  时间复杂度遍历所有决策，得出每个右端点对应的最优解，也就能得到数组或字符串的总体最优解。但是本题如果直接套用模板代码，在右端点增加时，却无法保证单调性成立，比如:对于字符串 abba 且 k = 2，窗口如果是字串 bb，那么字串向右扩张时左端点必须向左扩张才能得到正确的最优解。此时，我们甚至无法确定滑动窗口的左端该如何收缩。 但是，我们注意到题目的输入数据只有小写字母，范围极小，我们不妨为滑动窗口增加一个条件：窗口内只能刚好包含 x 种字符。显然，x 的取值范围是 ，要得到原题答案我们只需依次遍历所有可能的 26 个值即可。这样，上述反例类型就不再困扰我们了因为 bb 和 abba 对应 x 为 1 和 2 的两种情况，各自情况下的滑动窗口右端点对应了不同的左端点。实际上，因为窗口的约束条件刚好包含 x 种字符，而且此时：

    右端点扩张一定不会减少子串的字符种类
    左端点收缩一定不会增加子串的字符种类
    
    所以，我们只要在字符串的字符种类大于 x 的时候收缩窗口即可。 另外，不是所有的窗口都符合要求，必须窗口内部所有的字符数量都是大于 k 时才可以。我们可以使用一个长度 26 的数组计数窗口内部字符的数量：

    如果字符数量由 0 变为 1，则新增了字符种类，反之则减少了字符种类
    如果字符数量由 k - 1 变为 k，则新增了合法字符种类，反之则减少了合法字符种类
    这样，只有合法字符种类和总的字符种类相等时，才意味着这个窗口对应着合法子串。

    分治法
    可以看出如果字符串内的所有字符出现次数都不小于 k 的时候，本题的答案就是字符串的长度。而如果字符串内可能有某些字符出现的总数小于 k 的时候，这些字符一定在合法子串中，那么它们就把原来的字符串分解为一系列子串，我们只要在这些子串中递归寻找它们各自的合法子串，然后确定最长的合法子串长度即可，显然可以使用多路分治法求解本题。 另外，注意本题的递归可以进行剪枝处理以减少时间消耗，如：

    需要处理的字符串（子串）长度小于 k 时，直接返回 0
    需要处理的字符串（子串）的字符出现次数都不小于 k 时，直接返回其长度
    另外，所有字符出现次数都小于 k 时答案显然为 0，但是本题的输入数据并不全面，暂时无需对此情况进行剪枝处理。 本题每次递归如果要继续深入则至少会移除一种字符，而字符最多也只有 26 种，所以总的时间复杂度为O(N) 
    */
    private int longestSubstring_slidingwindow(String s, int k) {
        int n = s.length();
        if (n < k) return 0;
        
        int maxLen = 0;
        // window 中只包含charNum种字符，这是一个收缩条件
        for (int charNum = 1; charNum <= 26; ++charNum) {
            int[] window = new int[26];
            
            int charType = 0;
            int charAtLeastK = 0;
            int left = 0, right = 0;
            while (right < n) {
                // right expanding
                char r = s.charAt(right);
                ++ right;
                ++ window[r - 'a'];
                if (window[r - 'a'] == 1) ++ charType;
                if (window[r - 'a'] == k) ++ charAtLeastK;
                
                // shrink left
                while (charType > charNum) {
                    char l = s.charAt(left);
                    ++ left;
                    if (window[l - 'a'] == 1) -- charType;
                    if (window[l - 'a'] == k) -- charAtLeastK;
                    -- window[l - 'a'];
                }
                
                if (charType == charAtLeastK) {
                    maxLen = Math.max(maxLen, right - left);
                }
            }
        }
        return maxLen;
    }
    
}

