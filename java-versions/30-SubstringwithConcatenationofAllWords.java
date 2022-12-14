/*
Hard

You are given a string s and an array of strings words. All the strings of words are of the same length.
A concatenated substring in s is a substring that contains all the strings of any permutation of words concatenated.
For example, if words = ["ab","cd","ef"], then "abcdef", "abefcd", "cdabef", "cdefab", "efabcd", and "efcdab" are all concatenated strings. "acdbef" is not a concatenated substring because it is not the concatenation of any permutation of words.
Return the starting indices of all the concatenated substrings in s. You can return the answer in any order.

Example 1:
Input: s = "barfoothefoobarman", words = ["foo","bar"]
Output: [0,9]
Explanation: Since words.length == 2 and words[i].length == 3, the concatenated substring has to be of length 6.
The substring starting at 0 is "barfoo". It is the concatenation of ["bar","foo"] which is a permutation of words.
The substring starting at 9 is "foobar". It is the concatenation of ["foo","bar"] which is a permutation of words.
The output order does not matter. Returning [9,0] is fine too.

Example 2:
Input: s = "wordgoodgoodgoodbestword", words = ["word","good","best","word"]
Output: []
Explanation: Since words.length == 4 and words[i].length == 4, the concatenated substring has to be of length 16.
There is no substring of length 16 is s that is equal to the concatenation of any permutation of words.
We return an empty array.

Example 3:
Input: s = "barfoofoobarthefoobarman", words = ["bar","foo","the"]
Output: [6,9,12]
Explanation: Since words.length == 3 and words[i].length == 3, the concatenated substring has to be of length 9.
The substring starting at 6 is "foobarthe". It is the concatenation of ["foo","bar","the"] which is a permutation of words.
The substring starting at 9 is "barthefoo". It is the concatenation of ["bar","the","foo"] which is a permutation of words.
The substring starting at 12 is "thefoobar". It is the concatenation of ["the","foo","bar"] which is a permutation of words.
 
Constraints:
1 <= s.length <= 104
1 <= words.length <= 5000
1 <= words[i].length <= 30
s and words[i] consist of lowercase English letters.
*/

class Solution {
    public List<Integer> findSubstring(String s, String[] words) {
        return findSubstring_usingMap(s, words);
    }
    
    public List<Integer> findSubstring_bruteforce(String s, String[] words) {
        List<Integer> result = new ArrayList<>();
        int m = words.length;
        int n = words[0].length();
        if (s.length() < m * n) {
            return result;
        }
        
        int[] count = new int[26];
        for (String w : words) {
            for (int i = 0; i < w.length(); ++i) {
                count[w.charAt(i) - 'a'] ++;
            }
        }
        
        for (int i = 0; i < s.length(); ++i) {
            count[s.charAt(i) - 'a'] --;
        }
        for (int i = 0; i < count.length; ++i) {
            if (count[i] > 0) {
                return result;
            }
        }
        
        Set<String> concatStrings = getConcatStrings(words);
        for (String subString : concatStrings) {
            int index = s.indexOf(subString);
            while (index >= 0) {
                result.add(index);
                index = s.indexOf(subString, index + 1);
            }
        }
        return result;
    }
    
    private Set<String> getConcatStrings(String[] words) {
        List<List<Integer>> permutations = new ArrayList<>();
        int[] indexes = new int[words.length];
        for (int i = 0; i < words.length; ++i) {
            indexes[i] = i;
        }
        getAllPermutations(indexes, permutations, 0);
        Set<String> concatStrings = new HashSet<>();
        for (List<Integer> list : permutations) {
            StringBuilder sb = new StringBuilder();
            for (int index : list) {
                sb.append(words[index]);
            }
            concatStrings.add(sb.toString());
        }
        return concatStrings;
    }
    
    private void getAllPermutations(int[] indexes, List<List<Integer>> permutations, int start) {
        if (start == indexes.length) {
            List<Integer> list = new ArrayList<>();
            for (int i : indexes) {
                list.add(i);
            }
            permutations.add(list);
            return;
        }
        
        for (int i = start; i < indexes.length; ++i) {
            swap(indexes, start, i);
            getAllPermutations(indexes, permutations, start + 1);
            swap(indexes, start, i);
        }
    }
    
    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    
    //////////////////////////////////////////////////
    /*
    very good!
    思路：
    wn：words的大小，wl：words中每个word的长度
    先统计words中每个word出现的次数，放到countmap中。
    对s进行遍历和比较，比较的长度为wordlen * wordnum
    比较的方法是：一个词一个词地扫描，如果该词在countmap中，并且次数没超过，meet+1
    如果meet == wordnum，则该字串满足条件。
    
    时间复杂度：
    O( （n - wn * wl) * (wn * wl) )
    (n - wn * wl) 为外层循环次数
    (wn * wl) 为内层循环次数 * 操作复杂度（substring）
    
    空间复杂度：
    O(wn)， map里面存了最多wn个元素。
    */
    public List<Integer> findSubstring_usingMap(String s, String[] words) {
        List<Integer> result = new ArrayList<>();
        int sl = s.length();
        int wn = words.length;
        int wl = words[0].length();
        int substringLen = wl * wn;
        if (s.length() < substringLen) {
            return result;
        }
        
        Map<String, Integer> count = new HashMap<>();
        for (String w : words) {
            count.put(w, count.getOrDefault(w, 0) + 1);
        }
        
        for (int i = 0; i <= sl - substringLen; ++i) {
            int meet = 0;
            Map<String, Integer> expected = new HashMap<>(count); 
            for (int j = i; j < i + substringLen; j += wl) {
                String w = s.substring(j, j + wl);
                if (expected.getOrDefault(w, 0) > 0) {
                    expected.put(w, expected.get(w) - 1);
                    ++ meet;
                } else {
                    break;
                }
            }
            
            if (meet == wn) {
                result.add(i);
            }
        }
        return result;
    }
    
    
    // todo
    public List<Integer> findSubstring_slidingWindow(String s, String[] words) {
        return null;
    }
}

