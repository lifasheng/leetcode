/*
Hard

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

class Solution {
    // very very good!
    // brute force solution: use for loop and word search's DFS solution, will TLE.
    // how to improve? use Trie tree for words, and use DFS for board traversal.
    // Time complexity: O(M(4⋅3^L−1)), where M is the number of cells in the board and L is the maximum length of words.
    // very similar with word search time compleixty
    // Space Complexity: O(N), where N is the total number of letters in the dictionary.
    class TrieNode {
        private Map<Character, TrieNode> children;
        private String word;
        
        public TrieNode() {
            children = new HashMap<>();
        }
        
        public boolean isEmpty() {
            return children.isEmpty();
        }
        
        public boolean isWord() {
            return word != null;
        }
        
        public String getWord() {
            return word;
        }
        
        public void setWord(String word) {
            this.word = word;
        }
        
        public boolean containsKey(char c) {
            return children.containsKey(c);
        }
        
        public TrieNode get(char c) {
            return children.get(c);
        }
        
        public void put(char c, TrieNode node) {
            children.put(c, node);
        }
    }
    
    private static final int[][] DIRECTIONS = {
        {-1, 0},
        {1, 0},
        {0, -1},
        {0, 1}
    };
    
    private int ROWS;
    private int COLS;
    public List<String> findWords(char[][] board, String[] words) {
        ROWS = board.length;
        COLS = board[0].length;
        
        List<String> result = new ArrayList<>();
        TrieNode root = buildTrie(words);
        boolean[][] visited = new boolean[ROWS][COLS];
        
        for (int i = 0; i < ROWS; ++i) {
            for (int j = 0; j < COLS; ++j) {
                if (root.containsKey(board[i][j])) {
                    dfs(board, root, i, j, visited, result);
                }
            }
        }
        
        return result;
    }
    
    private TrieNode buildTrie(String[] words) {
        TrieNode root = new TrieNode();
        for (String word : words) {
            TrieNode node = root;
            for (int i = 0; i < word.length(); ++i) {
                char c = word.charAt(i);
                if (!node.containsKey(c)) {
                    node.put(c, new TrieNode());
                }
                node = node.get(c);
            }
            node.setWord(word);
        }
        return root;
    }
    
    private void dfs(char[][]board, TrieNode parent,
                     int i, int j, 
                     boolean[][] visited,
                     List<String> result) {
        TrieNode currNode = parent.get(board[i][j]);
        
        if (currNode.isWord()) {
            result.add(currNode.getWord());
            currNode.setWord(null); // to avoid duplicate
        }
        
        visited[i][j] = true;
        
        for (int[] direction : DIRECTIONS) {
            int newI = i + direction[0];
            int newJ = j + direction[1];
            
            if (newI < 0 || newI >= ROWS || newJ < 0 || newJ >= COLS 
            || !currNode.containsKey(board[newI][newJ])
            || visited[newI][newJ]) continue;
            
            dfs(board, currNode, newI, newJ, visited, result);
        }
        
        visited[i][j] = false;
        
        // 优化: 如果当前节点是叶子节点，则它不会再被访问到，可以从trie树中删除掉该节点了。
        // Optimization: incrementally remove the leaf nodes
        if (currNode.isEmpty()) {
            parent.children.remove(board[i][j]);
        }
    }
}

