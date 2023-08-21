/*
very very very good!


///////
LeetCode Hint:
You would need to optimize your backtracking to pass the larger test. Could you stop backtracking earlier?

If the current candidate does not exist in all words' prefix, you could stop backtracking immediately. What kind of data structure could answer such query efficiently? Does a hash table work? Why or why not? How about a Trie? If you would like to learn how to implement a basic trie, please work on this problem: Implement Trie (Prefix Tree) first.
///////


Given an m x n board of characters and a list of strings words, return all words on the board.

Each word must be constructed from letters of sequentially adjacent cells, where adjacent cells are horizontally or vertically neighboring. The same letter cell may not be used more than once in a word.

Example 1:
Input: board = [["o","a","a","n"],["e","t","a","e"],["i","h","k","r"],["i","f","l","v"]], words = ["oath","pea","eat","rain"]
Output: ["eat","oath"]

Example 2:
Input: board = [["a","b"],["c","d"]], words = ["abcb"]
Output: []
 
Constraints:
m == board.length
n == board[i].length
1 <= m, n <= 12
board[i][j] is a lowercase English letter.
1 <= words.length <= 3 * 104
1 <= words[i].length <= 10
words[i] consists of lowercase English letters.
All the strings of words are unique.
*/

class TrieNode {
    TrieNode[] children;
    String word;

    public TrieNode() {
        this.word = null;
        this.children = new TrieNode[26];
    }

    public boolean containsKey(char c) {
        return children[c - 'a'] != null;
    }

    public TrieNode get(char c) {
        return children[c - 'a'];
    }

    public void put(char c, TrieNode node) {
        children[c - 'a'] = node;
    }

    public void setWord(String w) {
        word = w;
    }

    public boolean isWord() {
        return word != null;
    }

    public String getWord() {
        return word;
    }
    
}

class Solution {
    char[][] board;
    String[] words;
    int m;
    int n;
    boolean[][] visited;

    TrieNode root;
    public void buildTrie(String word) {
        TrieNode p = root;
        for (int i = 0; i < word.length(); ++i) {
            char c = word.charAt(i);
            if (!p.containsKey(c)) {
                p.put(c, new TrieNode());
            }
            p = p.get(c);
        }
        p.setIsWord(true);
        p.setWord(word);
    }


    /*
    这道题是在之前那道 Word Search 的基础上做了些拓展，之前是给一个单词让判断是否存在，
    现在是给了一堆单词，让返回所有存在的单词，用 brute force 会超时。
    出题者其实是想考察字典树的应用，LeetCode 中有关字典树的题还有 Implement Trie (Prefix Tree) 
    和 Add and Search Word - Data structure design，
    那么我们在这题中只要实现字典树中的 insert 功能就行了，查找单词和前缀就没有必要了，
    然后 DFS 的思路跟之前那道 Word Search 基本相同，请参见代码如下：

    具体的变化时：
    之前的word search，dfs时是在board中来找word[index]；
    而这道题dfs时时在board中找当前trie tree的node，只要想明白了这个对应关系，代码就容易理解了。

    还有一个细微的区别是，word search时，dfs 返回true，因为它处理的是一个word。
    而这道题不能遇到一个word就返回，而是要继续递归下去，比如words中有两个word： app， apple，我们在
    遇到了app时，需要把它加入结果集中，但是不能马上返回，而是继续递归。
    
    为什么我们要使用Trie呢？一个原因是因为搜索完一个单词之后，可以继续搜索下一个单词，
    而不用向Brute force搜索完以后要回头重新查找。举个例子，假如单词为sea，seafood，那么搜索到sea后，我们可以继续搜索seafood。
    另一个原因是上面leetcode hint里面说的，如果当前board的candidate不在all words‘ prefix，那肯定不用往下搜索了, trie tree的prefix可以快速回答这个问题。
    中文翻译是：如果当前单词不存在于所有单词的前缀中，则可以立即停止回溯。

    时间复杂度O(mn * 3 ^ l), 空间复杂度O(lk), 其中 m和 n分别是 board数组的长宽, l是 words数组中的单词长度, k为 words中的单词数

    */
    public List<String> findWords(char[][] board, String[] words) {
        this.board = board;
        this.words = words;
        this.m = board.length;
        this.n = board[0].length;
        this.visited = new boolean[m][n];
        this.root = new TrieNode();


        // 将words插入trie tree中。
        for (String s : words) {
            buildTrie(s);
        }

        List<String> result = new ArrayList<>();
        

        // 这里代码结构上和word search的很相似
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (root.containsKey(board[i][j])) {
                    dfs(root, i, j, result);
                }
            }
        }
        return result;
    }

    private static final int[][] DIRECTIONS = new int[][] {
        {-1, 0}, 
        {1, 0}, 
        {0, -1}, 
        {0, 1}
    };

    private void dfs(TrieNode node, int i, int j, List<String> result) {
        // 遇到了一个word，加入结果集，为了避免重复，将它标记为不是一个word。
        if (node.isWord()) {
            result.add(node.getWord());

            // 已经加到结果中了，下次不用再加了。
            node.setWord(null);
        }

        if (i < 0 || j < 0 || i >= m || j >= n 
            || visited[i][j] 
            || !node.containsKey(board[i][j])) {
            return ;
        }

        visited[i][j] = true;

        for (int[] direction : DIRECTIONS) {
            int newI = i + direction[0];
            int newJ = j + direction[1];

            dfs(node.get(board[i][j]), newI, newJ, result);
        }

        visited[i][j] = false;
    }
}


