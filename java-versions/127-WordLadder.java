/*
Hard
A transformation sequence from word beginWord to word endWord using a dictionary wordList is a sequence of words beginWord -> s1 -> s2 -> ... -> sk such that:

Every adjacent pair of words differs by a single letter.
Every si for 1 <= i <= k is in wordList. Note that beginWord does not need to be in wordList.
sk == endWord
Given two words, beginWord and endWord, and a dictionary wordList, return the number of words in the shortest transformation sequence from beginWord to endWord, or 0 if no such sequence exists.

Example 1:
Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
Output: 5
Explanation: One shortest transformation sequence is "hit" -> "hot" -> "dot" -> "dog" -> cog", which is 5 words long.

Example 2:
Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
Output: 0
Explanation: The endWord "cog" is not in wordList, therefore there is no valid transformation sequence.
 
Constraints:
1 <= beginWord.length <= 10
endWord.length == beginWord.length
1 <= wordList.length <= 5000
wordList[i].length == beginWord.length
beginWord, endWord, and wordList[i] consist of lowercase English letters.
beginWord != endWord
All the words in wordList are unique.
*/

class Solution {
    // BFS
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordDict = new HashSet<>();
        for (String word : wordList) {
            wordDict.add(word);
        }
        
        Set<String> visited = new HashSet<>();
        
        Deque<String> queue = new ArrayDeque<>();
        queue.offer(beginWord);
        visited.add(beginWord);
        int step = 0;
        while (!queue.isEmpty()) {
            ++ step; // 这里不是说多少步，而是步数 + 1， 看看例子就知道了，如果是步数，则将这一行放在while循环的最后即可。
            int size = queue.size();
            for (int i = 0; i < size; ++i) {
                String curWord = queue.poll();
                
                if (curWord.equals(endWord)) return step;
                
                List<String> transformedWords = transform(curWord, wordDict, visited);
                for (String w : transformedWords) {
                    queue.offer(w);
                }
            }
        }
        
        return 0;
    }
    
    // visited 必须在扩展的时候去重，因为如果queue中当前的单词都有可能扩展到同一个词，则可能会出现重复的情况
    private List<String> transform(String curWord, Set<String> wordDict, Set<String> visited) {
        List<String> result = new ArrayList<>();
        char[] chars = curWord.toCharArray();
        for (int i = 0; i < chars.length; ++i) {
            char ci = chars[i];
            for (char c = 'a'; c <= 'z'; ++c) {
                if (ci == c) continue;
                
                chars[i] = c;
                String newWord = new String(chars);
                if (wordDict.contains(newWord) && !visited.contains(newWord)) {
                    result.add(newWord);
                    visited.add(newWord);
                }
                chars[i] = ci;
            }
        }
        return result;
    }
}

