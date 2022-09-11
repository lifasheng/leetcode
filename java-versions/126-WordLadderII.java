/*
Hard

A transformation sequence from word beginWord to word endWord using a dictionary wordList is a sequence of words beginWord -> s1 -> s2 -> ... -> sk such that:

Every adjacent pair of words differs by a single letter.
Every si for 1 <= i <= k is in wordList. Note that beginWord does not need to be in wordList.
sk == endWord
Given two words, beginWord and endWord, and a dictionary wordList, return all the shortest transformation sequences from beginWord to endWord, or an empty list if no such sequence exists. Each sequence should be returned as a list of the words [beginWord, s1, s2, ..., sk].

Example 1:
Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
Output: [["hit","hot","dot","dog","cog"],["hit","hot","lot","log","cog"]]
Explanation: There are 2 shortest transformation sequences:
"hit" -> "hot" -> "dot" -> "dog" -> "cog"
"hit" -> "hot" -> "lot" -> "log" -> "cog"

Example 2:
Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
Output: []
Explanation: The endWord "cog" is not in wordList, therefore there is no valid transformation sequence.

Constraints:
1 <= beginWord.length <= 5
endWord.length == beginWord.length
1 <= wordList.length <= 500
wordList[i].length == beginWord.length
beginWord, endWord, and wordList[i] consist of lowercase English letters.
beginWord != endWord
All the words in wordList are unique.
The sum of all shortest transformation sequences does not exceed 105.
*/

class Solution {
    // very very good!!!
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        Map<String, Set<String>> parentWord = new HashMap<>();
        Set<String> wordDict = new HashSet<>(wordList);
        
        Deque<String> queue = new ArrayDeque<>();
        queue.add(beginWord);
        wordDict.remove(beginWord);
        boolean found = false;
        while (!queue.isEmpty()) {
            int size = queue.size();
            
            Set<String> transformedWordsInNextLevel = new HashSet<>();
            for (int i = 0; i < size; ++i) {
                String curWord = queue.poll();
                
                // 一旦到达endword，就不用再扩展该词了，但是同一层中其他词可能还能变成endword，所以还要继续循环。
                if (curWord.equals(endWord)) {
                    found = true;
                    continue;
                }
                
                List<String> transformedWords = transform(curWord, wordDict, parentWord);
                transformedWordsInNextLevel.addAll(transformedWords);
            }
            
            /*
                这个思路很好，可以避免出现往回变换的情况。
                We will then add all the directed edges from the words present in the current layer 
                and once all words in this layer have been traversed, we will remove them from the wordList. 
                This way we will avoid adding any edges that point towards beginWord.
            */
            for (String word : transformedWordsInNextLevel) {
                queue.offer(word); // 这一步是优化，我们在当前层全部扩展完之后，存到set中，这样可以去重，比如 ted和rex都可以变成tex。
                wordDict.remove(word); // 这一步很关键，是为了防止往回扩展，或者同层直接互相转换，比如dot不能再变回hot，dot也不能变成lot。
            }
        }
        
        List<List<String>> result = new ArrayList<>();
        LinkedList<String> path = new LinkedList<>();
        path.addFirst(endWord);
        if (found) {
            findPath(beginWord, endWord, parentWord, result, path);
        }
        return result;
    }
    
    // DFS to find path from endWord
    private void findPath(String beginWord, 
                          String endWord, 
                          Map<String, Set<String>> parentWord,
                          List<List<String>> result,
                          LinkedList<String> path) {
        if (endWord.equals(beginWord)) {
            result.add(new ArrayList<>(path));
            return;
        }
        Set<String> parents = parentWord.get(endWord);
        for (String parent : parents) {
            path.addFirst(parent);
            findPath(beginWord, parent, parentWord, result, path);
            path.remove(0);
        }
    }
    
    private List<String> transform(String curWord, 
                                   Set<String> wordDict, 
                                   Map<String, Set<String>> parentWord) {
        List<String> result = new ArrayList<>();
        char[] chars = curWord.toCharArray();
        for (int i = 0; i < chars.length; ++i) {
            char ci = chars[i];
            
            for (char j = 'a'; j <= 'z'; ++j) {
                if (ci == j) continue;
                
                chars[i] = j;
                String newWord = new String(chars);
                // 这里我们不能用visited来判断，否则只能找到一条路径，而实际上dog和log都能到达cog
                if (wordDict.contains(newWord)) {
                    result.add(newWord);
                    parentWord.putIfAbsent(newWord, new HashSet<>());
                    parentWord.get(newWord).add(curWord);
                }
                chars[i] = ci;
            }
        }
        return result;
    }
}

