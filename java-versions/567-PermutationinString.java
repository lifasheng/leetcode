/*
Medium

Given two strings s1 and s2, return true if s2 contains a permutation of s1, or false otherwise.
In other words, return true if one of s1's permutations is the substring of s2.

Example 1:
Input: s1 = "ab", s2 = "eidbaooo"
Output: true
Explanation: s2 contains one permutation of s1 ("ba").

Example 2:
Input: s1 = "ab", s2 = "eidboaoo"
Output: false

Constraints:
1 <= s1.length, s2.length <= 104
s1 and s2 consist of lowercase English letters.
*/

class Solution {
    public boolean checkInclusion(String s1, String s2) {
        return checkInclusion_slidingwindow(s1, s2);
    }
    
    private boolean checkInclusion_sort(String s1, String s2) {
        if (s2.length() < s1.length()) return false;
        
        s1 = sort(s1);
        for (int i = 0; i <= s2.length() - s1.length(); ++i) {
            if (s1.equals(sort(s2.substring(i, i + s1.length())))) return true;
        }
        return false;
    }
    
    private String sort(String s) {
        char[] arr = s.toCharArray();
        Arrays.sort(arr);
        return new String(arr);
    }
    
    // sliding window
    private boolean checkInclusion_slidingwindow(String s1, String s2) {
        if (s2.length() < s1.length()) return false;
        
        Map<Character, Integer> need = new HashMap<>();
        for (int i = 0; i < s1.length(); ++i) {
            char c = s1.charAt(i);
            need.put(c, need.getOrDefault(c, 0) + 1);
        }
        
        Map<Character, Integer> window = new HashMap<>();
        int meetCount = 0;
        
        int left = 0, right = 0;
        while (right < s2.length()) {
            char r = s2.charAt(right);
            ++ right;
            window.put(r, window.getOrDefault(r, 0) + 1);
            
            if (window.get(r).intValue() == need.getOrDefault(r, -1).intValue()) {
                ++ meetCount;
            }
            
            // test case: "hello", ooolleoooleh"
            // shrink left
            
            /*
            boolean checked = false;
            while (meetCount == need.size()) {
                // check if permutation is found
                if (!checked) { // optimization, avoid unnecessary compare because the size must be s1.length() and must be the right most l1 size;
                    Map<Character, Integer> countMap = new HashMap<>();
                    for (int i = right - 1; i >= right - s1.length(); --i) {
                        char c = s2.charAt(i);
                        countMap.put(c, countMap.getOrDefault(c, 0) + 1);
                    }
                    if (match(countMap, need)) return true;
                    checked = true;
                }
                
                char l = s2.charAt(left);
                ++ left;
                window.put(l, window.get(l) - 1);
                
                // 这里我之前都是用 window.get(l) < need.getOrDefault(l, -1)，
                // 这种逻辑在我们已经找到一个可行解的情况下是对的，但是对于下面的优化来说就不对了，
                // 而判断他们是不是刚好相等就总是对的
                if (window.get(l) + 1 == need.getOrDefault(l, -1)) {
                    -- meetCount;
                }
            }
            */
            
            // 优化： 不用比较了，因为meetCount 和 长度保证了是permutation。
            while (right - left >= s1.length()) { // 因为window的大小应该总是s1.length()
                if (meetCount == need.size()) return true;
                
                char l = s2.charAt(left);
                ++ left;
                
                window.put(l, window.get(l) - 1);
                // 注意这里不能用 window.get(l) < need.getOrDefault(l, -1)
                // 因为这种优化并不能保证在收缩左侧的时候已经找到了一个可行解，而这时--meetCount 就不对了
                // 因为++meetCount的时候是出现在window.get(r) == need.get(r)的，所以--meetCount我们要保持一致的逻辑。
                // 比如下面这个test case， a字符一开始是出现了1次，此时meetCount==0, 但是如果是用window.get(l) < need.getOrDefault(l, -1), 会导致收缩第一个a的时候，meetCount == -1，从而导致逻辑错误。
                // "abca", "adefacba"
                if (window.get(l) + 1 == need.getOrDefault(l, -1)) {
                    -- meetCount;
                }
            }
        }
        return false;
    }
    
    
    // note intValue is important when its value > 128, because no cache 
    private boolean match(Map<Character, Integer> count, Map<Character, Integer> need) {
        if (count.size() != need.size()) return false;
        if (!count.keySet().equals(need.keySet())) return false;
        
        for (Character c : count.keySet()) {
            if (count.get(c).intValue() != need.get(c).intValue()) return false;
        }
        return true;
    }
}


